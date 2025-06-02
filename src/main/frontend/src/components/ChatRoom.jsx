// src/components/ChatRoom.jsx
import React, { useEffect, useRef } from 'react';
import '../styles/ChatRoom.css';
import logo from '../assets/logo.png';


export default function ChatRoom({
                                     roomId,
                                     postTitle,
                                     postImage,
                                     otherNickname,
                                     authorNickname,
                                     messages,
                                     inputValue,
                                     onInputChange,
                                     onSend,
                                     complete}) {
    const scrollRef = useRef(null);

    useEffect(() => {
        if (scrollRef.current) {
            scrollRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    }, [messages]);

    return (
        <div className="chat-room">
            {postTitle && (
                <div className="chat-room-header">
                    <div className="participant-name">{otherNickname}</div>
                    <div className="post-info">
                        <img
                            src={postImage || logo}
                            alt={`Post 사진`}
                            className="post-image"
                        />
                        <div>
                            <div className="post-title">[{postTitle}]</div>
                            {authorNickname === localStorage.getItem('nickname') &&
                                <div className="post-complete" onClick={complete(roomId)}>동행 완료</div>}
                        </div>
                    </div>
                </div>
            )}

            <div className="message-list">
                {messages.map((msg, idx) => {
                    const date = new Date(msg.sendAt);
                    const timeLabel = date.toLocaleTimeString('ko-KR', {
                        hour: '2-digit',
                        minute: '2-digit',
                        hour12: true,
                    });
                    const isMine = msg.sender === localStorage.getItem('nickname');

                    return (
                        <div
                            key={idx}
                            className={`message-container ${isMine ? 'mine' : ''}`}
                        >
                            {isMine ? (
                                <>
                                    <span className="timestamp">{timeLabel}</span>
                                    <div className="message">{msg.message}</div>
                                </>
                            ) : (
                                <>
                                    <div className="message">{msg.message}</div>
                                    <span className="timestamp">{timeLabel}</span>
                                </>
                            )}
                        </div>
                    );
                })}
                <div ref={scrollRef} />
            </div>

            <div className="input-area">
                <button className="btn-add">＋</button>
                <input
                    type="text"
                    value={inputValue}
                    onChange={onInputChange}
                    onKeyPress={(e) => e.key === 'Enter' && onSend()}
                    placeholder="메시지를 입력하세요..."
                />
                <button className="btn-send" onClick={onSend}>
                    ⮕
                </button>
            </div>
        </div>
    );
}