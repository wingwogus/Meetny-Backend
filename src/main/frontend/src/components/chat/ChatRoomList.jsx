// ChatRoomList.jsx
import React from 'react';
import '../../styles/ChatRoomList.css';
import logo from '../../assets/logo.png';


/**
 * props:
 *   - rooms: Array<{
 *        roomId,
 *        postTitle,
 *        authorNickname,
 *        otherUserName,
 *        otherUserAvatar,
 *        latestMessage,
 *        latestTime,
 *        unreadCount
 *     }>
 *   - selectedRoomId: string | null
 *   - onSelectRoom: (roomId, postTitle, authorNickname) => void
 */
export default function ChatRoomList({ rooms, selectedRoomId, onSelectRoom }) {
    const formatTime = (isoString) =>
        new Date(isoString).toLocaleTimeString('ko-KR', {
            hour: '2-digit',
            minute: '2-digit',
            hour12: true,
        });

    return (
        <div className="chat-room-list">
            {rooms.map((room) => {
                const isSelected = room.roomId === selectedRoomId;
                // 백엔드에서 내려준 필드명에 맞춰서 사용하세요.
                const nickname = localStorage.getItem('nickname');

                let isMine = room.authorNickname === nickname;

                const otherUserName =
                    isMine ? room.userNickname : room.authorNickname || room.postTitle;
                const authorNickname = room.authorNickname;
                const latestMessage = room.latestMessage || '대화 없음';
                const latestTime = room.latestTime || '';
                const unreadCount = room.unreadCount || 0;
                const avatarUrl =
                    isMine ? room.userImage : room.authorImage || logo;

                return (
                    <div
                        key={room.roomId}
                        className={`room-item ${isSelected ? 'selected' : ''}`}
                        onClick={() =>
                            onSelectRoom(room.roomId, room.postId, room.postTitle, otherUserName, authorNickname, room.postImage)
                        }
                    >
                        {/* 왼쪽 아바타 */}
                        <img
                            src={avatarUrl}
                            alt={`${otherUserName} 프로필`}
                            className="room-avatar"
                        />

                        {/* 중앙: 이름 · 최신 메시지 */}
                        <div className="room-info">
                            <div className="room-title">{otherUserName}</div>
                            <div className="room-snippet">{latestMessage}</div>
                        </div>

                        {/* 우측: 시간 · 읽지 않은 개수 */}
                        <div className="room-meta">
                            {latestTime && <div className="room-time">{formatTime(latestTime)}</div>}
                            {unreadCount > 0 && (
                                <div className="unread-badge">{unreadCount}</div>
                            )}
                        </div>
                    </div>
                );
            })}
        </div>
    );
}