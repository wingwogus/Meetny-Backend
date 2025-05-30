import React, { useState } from 'react';
import ChatRoomList from '../components/ChatRoomList';
import ChatRoom from '../components/ChatRoom';
import AppHeader from "../components/AppHeader";
import logoImg from '../assets/logo.png';
import '../styles/Chat.css';

export default function Chat() {
    const [selectedRoomId, setSelectedRoomId] = useState(null);
    const [selectedRoomTitle, setSelectedRoomTitle] = useState('');
    const [authorNickname, setAuthorNickname] = useState('');

    const handleSelect = (roomId, roomTitle, authorNickname) => {
        setSelectedRoomId(roomId);
        setSelectedRoomTitle(roomTitle);
        setAuthorNickname(authorNickname);
    };

    return (
        <>
            <AppHeader/>
            <h2 className="chat-title">메시지</h2>
            <div className="chat">
                <div className="chat-room-list-wrapper">
                    <ChatRoomList
                        onSelectRoom={handleSelect}
                        selectedRoomId={selectedRoomId}
                    />
                </div>
                <div className="chat-room-wrapper">
                    {/* 오른쪽: 메시지 뷰 (방 선택 시) */}
                    {selectedRoomId ? (
                        <ChatRoom
                            roomId={selectedRoomId}
                            roomTitle={selectedRoomTitle}
                            authorNickname={authorNickname}
                        />
                    ) : (
                        <div className="placeholder">채팅방을 선택해주세요.</div>
                    )}
                </div>
            </div>
        </>
    );
}