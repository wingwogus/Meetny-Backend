// src/components/ChatRoom.jsx
import React, { useCallback, useEffect, useState } from 'react';
import api from '../api/axiosClient';
import { useWebSocket } from '../hooks/useWebSocket';
import '../styles/ChatRoom.css';

export default function ChatRoom({ roomId, roomTitle, authorNickname }) {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');

    // 메시지 추가 헬퍼
    const addMessage = useCallback((msg) => {
        setMessages((prev) => [...prev, msg]);
    }, []);

    // 수신 메시지 핸들러
    const handleIncoming = useCallback((wsMsg) => {
        const formatted = {
            sender: wsMsg.sender,
            message: wsMsg.message,
            sendAt: wsMsg.sendAt
        };
        addMessage(formatted);
    }, [addMessage]);

    // WebSocket 연결 & 수신
    const { sendMessage } = useWebSocket(roomId, handleIncoming);

    // 초기 채팅 내역 로드
    useEffect(() => {
        if (!roomId) {
            setMessages([]);
            return;
        }
        setMessages([]);
        api.get(`/api/chat/rooms/history/${roomId}`)
            .then((res) => {
                const list = res.data.data || res.data;
                list.forEach((msg) =>
                    addMessage({
                        sender:  msg.sender,
                        message: msg.message,
                        sendAt:  msg.sendAt
                    })
                );
            })
            .catch(console.error);
    }, [roomId, addMessage]);

    // 메시지 전송
    const handleSend = () => {
        const trimmed = input.trim();
        if (!trimmed) return;
        sendMessage({ roomId, message: trimmed });
        setInput('');
    };

    return (
        <div className="chat-room">
            {roomTitle && (
                <div className="chat-room-header">
                    <div className="participant-name">{authorNickname}</div>
                    <div className="post-title">{roomTitle}</div>
                </div>
            )}
            <div className="message-list">
                {messages.map((msg, idx) => {
                    const date = new Date(msg.sendAt); // ISO 문자열을 Date 객체로 변환

                    // 'ko-KR' 로케일 + 12시간제 옵션 설정
                    const timeLabel = date.toLocaleTimeString('ko-KR', {
                        hour:   '2-digit',  // 두 자리 시
                        minute: '2-digit',  // 두 자리 분
                        hour12: true        // 12시간제 (오전/오후)
                    });

                    const isMine = msg.sender === localStorage.getItem('username');
                    return (
                        <div
                            key={idx}
                            className={`message-container ${isMine ? 'mine' : ''}`}
                        >
                            {isMine ? (
                                <>
                                    {/* 내 메시지: 시간 먼저 */}
                                    <span className="timestamp">{timeLabel}</span>
                                    <div className="message">{msg.message}</div>
                                </>
                            ) : (
                                <>
                                    {/* 상대 메시지: 말풍선 먼저 */}
                                    <div className="message">{msg.message}</div>
                                    <span className="timestamp">{timeLabel}</span>
                                </>
                            )}
                        </div>
                    );
                })}
            </div>

            <div className="input-area">
                <button className="btn-add">＋</button>
                <input
                    type="text"
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && handleSend()}
                    placeholder="메시지를 입력하세요..."
                />
                <button className="btn-send" onClick={handleSend}>⮕</button>
            </div>
        </div>
    );
}