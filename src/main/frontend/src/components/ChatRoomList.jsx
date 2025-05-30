import React, { useEffect, useState } from 'react';
import api from '../api/axiosClient';
import '../styles/ChatRoomList.css';

/**
 * 채팅방 목록 컴포넌트
 * onSelectRoom: 방 선택 시 호출 (roomId, roomTitle)
 * selectedRoomId: 현재 선택된 방 ID
 */
export default function ChatRoomList({ onSelectRoom, selectedRoomId }) {
    const [rooms, setRooms] = useState([]);

    useEffect(() => {
        api.get('/api/chat/rooms/')
            .then((res) => {
                const roomsData = res.data.data;
                setRooms(Array.isArray(roomsData) ? roomsData : []);
            })
            .catch(console.error);
    }, []);

    return (
        <div className="chat-room-list">
            {rooms.map((room) => (
                <div
                    key={room.roomId}
                    className={room.roomId === selectedRoomId ? 'room-item selected' : 'room-item'}
                    onClick={() =>
                        onSelectRoom(
                            room.roomId,
                            room.postTitle,
                            room.authorNickname)}
                >
                    <div className="room-title">{room.postTitle}</div>
                    <div className="room-meta">작성자: {room.authorNickname}</div>
                </div>
            ))}
        </div>
    );
}