// src/components/Chat.jsx
import React, { useState, useEffect, useCallback, useRef } from 'react';
import ChatRoomList from '../components/ChatRoomList';
import ChatRoom from '../components/ChatRoom';
import AppHeader from "../components/AppHeader";
import axios from '../api/axiosClient';
import { useWebSocket } from '../hooks/useWebSocket';
import '../styles/Chat.css';

const formatMessage = (msg) => ({
    sender: msg.sender,
    message: msg.message,
    sendAt: msg.sendAt,
});

const formatTime = (isoString) =>
    new Date(isoString).toLocaleTimeString('ko-KR', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true,
    });

export default function Chat() {
    const [rooms, setRooms] = useState([]);
    const [selectedRoomId, setSelectedRoomId] = useState(null);
    const [selectedRoomTitle, setSelectedRoomTitle] = useState('');
    const [authorNickname, setAuthorNickname] = useState('');
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const selectedRoomIdRef = useRef(null);

    useEffect(() => {
        selectedRoomIdRef.current = selectedRoomId;
    }, [selectedRoomId]);

    const handleIncomingMessage = useCallback(
        ({ roomId: incomingRoomId, message, sendAt, sender }) => {
            setRooms(prevRooms =>
                prevRooms.map(rm => {
                    if (rm.roomId !== incomingRoomId) return rm;

                    const isCurrent = incomingRoomId === selectedRoomIdRef.current;

                    return {
                        ...rm,
                        latestMessage: message,
                        latestTime: formatTime(sendAt),
                        unreadCount: isCurrent ? 0 : (rm.unreadCount || 0) + 1,
                    };
                })
            );

            if (incomingRoomId === selectedRoomIdRef.current) {
                setMessages(prev => [...prev, { sender, message, sendAt }]);
            }
        },
        []
    );

    const { sendMessage, subscribeToRooms } = useWebSocket(handleIncomingMessage);

    useEffect(() => {
        axios.get('/api/chat/rooms/')
            .then(({ data }) => {
                const roomsData = Array.isArray(data.data) ? data.data : [];
                setRooms(roomsData);

                const roomIds = roomsData.map(r => r.roomId);
                subscribeToRooms(roomIds);

                if (!roomsData.length) return;

                const firstRoom = roomsData[0];
                setSelectedRoomId(firstRoom.roomId);
                setSelectedRoomTitle(firstRoom.postTitle);
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

    const handleSelect = (roomId, roomTitle, author) => {
        setSelectedRoomId(roomId);
        setSelectedRoomTitle(roomTitle);
        setAuthorNickname(author);

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
            sender: authorNickname,
            message: trimmed,
            sendAt: new Date().toISOString(),
        });

        setInput('');
    };

    return (
        <>
            <AppHeader />
            <h2 className="chat-title">메시지</h2>
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
                            roomTitle={selectedRoomTitle}
                            authorNickname={authorNickname}
                            messages={messages}
                            inputValue={input}
                            onInputChange={e => setInput(e.target.value)}
                            onSend={handleSend}
                        />
                    ) : (
                        <div className="placeholder">채팅방을 선택해주세요.</div>
                    )}
                </div>
            </div>
        </>
    );
}