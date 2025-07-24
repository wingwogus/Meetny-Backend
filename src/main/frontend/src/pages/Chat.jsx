// src/components/Chat.jsx
import React, { useState, useEffect, useCallback, useRef } from 'react';
import ChatRoomList from '../components/chat/ChatRoomList';
import ChatRoom from '../components/chat/ChatRoom';
import AppHeader from "../components/AppHeader";
import axios from '../api/axiosClient';
import { useWebSocket } from '../hooks/useWebSocket';
import '../styles/Chat.css';

const formatMessage = (msg) => ({
    sender: msg.sender,
    message: msg.message,
    sendAt: msg.sendAt,
});

export default function Chat() {
    const [rooms, setRooms] = useState([]);
    const [selectedRoomId, setSelectedRoomId] = useState(null);
    const [selectedPostId, setSelectedPostId] = useState(null);
    const [selectedRoomTitle, setSelectedRoomTitle] = useState('');
    const [selectedRoomPostImage, setSelectedRoomPostImage] = useState('');
    const [otherNickname, setOtherNickname] = useState('');
    const [authorNickname, setAuthorNickname] = useState('');
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const selectedRoomIdRef = useRef(null);

    useEffect(() => {
        selectedRoomIdRef.current = selectedRoomId;
    }, [selectedRoomId]);

    const handleIncomingMessage = useCallback(
        ({ roomId: incomingRoomId, message, sendAt, sender }) => {
            setRooms(prevRooms => {
                const updatedRooms = prevRooms.map(rm => {
                    if (rm.roomId !== incomingRoomId) return rm;
                    const isCurrent = incomingRoomId === selectedRoomIdRef.current;
                    return {
                        ...rm,
                        latestMessage: message,
                        latestTime: sendAt,
                        unreadCount: isCurrent ? 0 : (rm.unreadCount || 0) + 1,
                    };
                });
                // 재정렬: 메시지 받은 방을 맨 앞으로 이동
                const targetRoom = updatedRooms.find(r => r.roomId === incomingRoomId);
                const others = updatedRooms.filter(r => r.roomId !== incomingRoomId);
                return [targetRoom, ...others];
            });

            if (incomingRoomId === selectedRoomIdRef.current) {
                setMessages(prev => [...prev, { sender, message, sendAt }]);
            }
        },
        []
    );

    const { sendMessage, subscribeToRooms } = useWebSocket(handleIncomingMessage);

    useEffect(() => {
        axios.get('/api/chat/rooms')
            .then(({ data }) => {
                const roomsData = Array.isArray(data.data) ? data.data : [];
                setRooms(roomsData);

                const roomIds = roomsData.map(r => r.roomId);
                subscribeToRooms(roomIds);

                if (!roomsData.length) return;

                const firstRoom = roomsData[0];
                setSelectedRoomId(firstRoom.roomId);
                setSelectedPostId(firstRoom.postId);
                setSelectedRoomTitle(firstRoom.postTitle);
                setSelectedRoomPostImage(firstRoom.postImage);
                const nickname = localStorage.getItem('nickname');
                setOtherNickname(firstRoom.authorNickname === nickname ?
                    firstRoom.userNickname : firstRoom.authorNickname);
                setAuthorNickname(firstRoom.authorNickname);

                return axios.get(`/api/chat/rooms/history/${firstRoom.roomId}`);
            })
            .then(res => {
                if (!res) return;
                const list = res.data.data || [];
                setMessages(list.map(formatMessage));
            })
            .catch(error => {
                console.error('채팅방 목록을 가져오는 중 오류 발생:', error);
            });
    }, []);

    const handleSelect = (roomId, postId, postTitle, otherNickname, authorNickname, postImage) => {
        setSelectedRoomId(roomId);
        setSelectedPostId(postId);
        setSelectedRoomTitle(postTitle);
        setOtherNickname(otherNickname);
        setAuthorNickname(authorNickname);
        setSelectedRoomPostImage(postImage);

        axios.get(`/api/chat/rooms/history/${roomId}`)
            .then(res => {
                const list = res.data.data || [];
                setMessages(list.map(formatMessage));
            })
            .catch(error => {
                console.error(`방 ${roomId} 채팅 내역 조회 실패:`, error);
            });

        setRooms(prev =>
            prev.map(rm =>
                rm.roomId === roomId ? { ...rm, unreadCount: 0 } : rm
            )
        );
    };

    const handleSend = () => {
        const trimmed = input.trim();
        if (!selectedRoomId || trimmed === '') return;

        sendMessage({
            roomId: selectedRoomId,
            sender: otherNickname,
            message: trimmed,
            sendAt: new Date().toISOString(),
        });

        setInput('');
    };

    const handleComplete = (roomId) => {
        axios.post(`/api/chat/rooms/${roomId}/complete`)
            .then(res => {
                console.log(res.data.message);
            })
            .catch(error => {
                console.error(`방 ${roomId} 동행 완료 실패:`, error);
            });

        window.location.href = '/information';
    }

    return (
        <>
            <AppHeader />
            <h2 className="chat-title"></h2>
            <div className="chat">
                <div className="chat-room-list-wrapper">
                    <ChatRoomList
                        rooms={rooms}
                        selectedRoomId={selectedRoomId}
                        onSelectRoom={handleSelect}
                    />
                </div>
                <div className="chat-room-wrapper">
                    {selectedRoomId ? (
                        <ChatRoom
                            roomId={selectedRoomId}
                            postId={selectedPostId}
                            postTitle={selectedRoomTitle}
                            postImage={selectedRoomPostImage}
                            otherNickname={otherNickname}
                            authorNickname={authorNickname}
                            messages={messages}
                            inputValue={input}
                            onInputChange={e => setInput(e.target.value)}
                            onSend={handleSend}
                            onComplete={handleComplete}
                        />
                    ) : (
                        <div className="placeholder">채팅방을 선택해주세요.</div>
                    )}
                </div>
            </div>
        </>
    );
}