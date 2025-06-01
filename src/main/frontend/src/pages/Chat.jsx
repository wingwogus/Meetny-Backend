// src/components/Chat.jsx
import React, { useState, useEffect, useCallback } from 'react';
import ChatRoomList from '../components/ChatRoomList';
import ChatRoom from '../components/ChatRoom';
import AppHeader from "../components/AppHeader";
import axios from '../api/axiosClient';
import { useWebSocket } from '../hooks/useWebSocket';
import '../styles/Chat.css';

export default function Chat() {
    // 1) 전체 방 목록 상태
    const [rooms, setRooms] = useState([]);
    const [selectedRoomId, setSelectedRoomId] = useState(null);
    const [selectedRoomTitle, setSelectedRoomTitle] = useState('');
    const [authorNickname, setAuthorNickname] = useState('');

    // 2) 현재 방의 메시지 목록 상태
    const [messages, setMessages] = useState([]);

    // 3) 입력창 상태
    const [input, setInput] = useState('');

    // ─────────────────────────────────────────────────────────────
    // (1) 컴포넌트 마운트 시 → 전체 방 목록 조회
    useEffect(() => {
        axios
            .get('/api/chat/rooms/')
            .then((response) => {
                const data = response.data;
                const roomsData = Array.isArray(data.data) ? data.data : [];
                setRooms(roomsData);

                if (roomsData.length > 0) {
                    const first = roomsData[0];
                    setSelectedRoomId(first.roomId);
                    setSelectedRoomTitle(first.postTitle);
                    setAuthorNickname(first.authorNickname);

                    // 첫 번째 방 메시지 이력도 미리 불러오기 (옵션)
                    axios
                        .get(`/api/chat/rooms/history/${first.roomId}`)
                        .then((res) => {
                            const list = res.data.data || [];
                            setMessages(
                                list.map((msg) => ({
                                    sender: msg.sender,
                                    message: msg.message,
                                    sendAt: msg.sendAt,
                                }))
                            );
                        })
                        .catch(console.error);
                }
            })
            .catch((error) => {
                console.error('채팅방 목록을 가져오는 중 오류 발생:', error);
            });
    }, []);

    // ─────────────────────────────────────────────────────────────
    // (2) 방 선택 시 → 해당 방 ID/제목/작성자 상태 업데이트 + 메시지 이력 조회
    const handleSelect = (roomId, roomTitle, authorNickname) => {
        setSelectedRoomId(roomId);
        setSelectedRoomTitle(roomTitle);
        setAuthorNickname(authorNickname);

        // 해당 방 메시지 이력 불러오기
        axios
            .get(`/api/chat/rooms/history/${roomId}`)
            .then((res) => {
                const list = res.data.data || [];
                setMessages(
                    list.map((msg) => ({
                        sender: msg.sender,
                        message: msg.message,
                        sendAt: msg.sendAt,
                    }))
                );
            })
            .catch((error) => {
                console.error(`방 ${roomId} 채팅 내역 조회 실패:`, error);
            });

        // 해당 방의 unreadCount를 0으로 초기화 (ChatRoomList 용)
        setRooms((prev) =>
            prev.map((rm) =>
                rm.roomId === roomId ? { ...rm, unreadCount: 0 } : rm
            )
        );
    };

    // ─────────────────────────────────────────────────────────────
    // (3) WebSocket 수신 콜백: rooms 상태 업데이트 + 현재 보고 있는 방이면 messages에도 추가
    const handleIncomingMessage = useCallback(
        ({ roomId: incomingRoomId, message, sendAt, sender }) => {
            // 1) rooms 배열에서 latestMessage, latestTime, unreadCount 업데이트
            setRooms((prevRooms) =>
                prevRooms.map((rm) => {
                    if (rm.roomId !== incomingRoomId) return rm;

                    const formattedTime = new Date(sendAt).toLocaleTimeString('ko-KR', {
                        hour: '2-digit',
                        minute: '2-digit',
                        hour12: true,
                    });

                    const isCurrent = incomingRoomId === selectedRoomId;
                    return {
                        ...rm,
                        latestMessage: message,
                        latestTime: formattedTime,
                        unreadCount: isCurrent ? 0 : (rm.unreadCount || 0) + 1,
                    };
                })
            );

            // 2) 현재 보고 있는 방이면 messages에도 바로 추가
            if (incomingRoomId === selectedRoomId) {
                setMessages((prev) => [
                    ...prev,
                    { sender, message, sendAt },
                ]);
            }
        },
        [selectedRoomId]
    );

    // ─────────────────────────────────────────────────────────────
    // (4) WebSocket 훅: 현재 선택된 방에 대해 구독/구독해제 자동 처리
    const { sendMessage } = useWebSocket(selectedRoomId, handleIncomingMessage);

    // ─────────────────────────────────────────────────────────────
    // (5) 메시지 전송 핸들러: sendMessage 호출 + 로컬 messages에 바로 추가 + input 초기화
    const handleSend = () => {
        const trimmed = input.trim();
        if (!selectedRoomId || trimmed === '') return;

        const payload = {
            roomId: selectedRoomId,
            sender: authorNickname,
            message: trimmed,
            sendAt: new Date().toISOString(),
        };

        // WebSocket으로 발행
        sendMessage(payload);

        // 로컬 messages에도 즉시 추가
        setMessages((prev) => [...prev, payload]);

        // 입력창 초기화
        setInput('');
    };

    // ─────────────────────────────────────────────────────────────
    return (
        <>
            <AppHeader />
            <h2 className="chat-title">메시지</h2>

            <div className="chat">
                {/* 왼쪽: 채팅방 리스트 */}
                <div className="chat-room-list-wrapper">
                    <ChatRoomList
                        rooms={rooms}
                        selectedRoomId={selectedRoomId}
                        onSelectRoom={handleSelect}
                    />
                </div>

                {/* 오른쪽: 선택된 방의 ChatRoom */}
                <div className="chat-room-wrapper">
                    {selectedRoomId ? (
                        <ChatRoom
                            roomId={selectedRoomId}
                            roomTitle={selectedRoomTitle}
                            authorNickname={authorNickname}
                            messages={messages}                // ← 부모가 내려주는 메시지 배열
                            inputValue={input}                 // ← 부모가 내려주는 입력창 값
                            onInputChange={(e) => setInput(e.target.value)} // ← 부모가 내려주는 입력 변화 핸들러
                            onSend={handleSend}                // ← 부모가 내려주는 전송 핸들러
                        />
                    ) : (
                        <div className="placeholder">채팅방을 선택해주세요.</div>
                    )}
                </div>
            </div>
        </>
    );
}