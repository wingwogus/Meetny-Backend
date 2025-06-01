import React, { useState, useEffect, useCallback  } from 'react';
import ChatRoomList from '../components/ChatRoomList';
import ChatRoom from '../components/ChatRoom';
import AppHeader from "../components/AppHeader";
import axios from '../api/axiosClient'
import '../styles/Chat.css';

export default function Chat() {
    // 1) 전체 방 목록을 상태로 관리
    const [rooms, setRooms] = useState([]);
    const [selectedRoomId, setSelectedRoomId] = useState(null);
    const [selectedRoomTitle, setSelectedRoomTitle] = useState('');
    const [authorNickname, setAuthorNickname] = useState('');
// --- 1) 컴포넌트 마운트 시, axios로 방 목록을 한 번만 불러옴 ---
    useEffect(() => {
        axios
            .get('/api/chat/rooms/') // 필요에 따라 베이스 URL을 지정해도 좋습니다.
            .then((response) => {
                const data = response.data;
                // API 응답 구조에 맞게 data.data 혹은 data 자체를 사용하세요.
                const roomsData = Array.isArray(data.data) ? data.data : [];
                setRooms(roomsData);

                if (roomsData.length > 0) {
                    // 초기 선택 방 설정 (옵션)
                    const first = roomsData[0];
                    setSelectedRoomId(first.roomId);
                    setSelectedRoomTitle(first.postTitle);
                    setAuthorNickname(first.authorNickname);
                }
            })
            .catch((error) => {
                console.error('채팅방 목록을 가져오는 중 오류 발생:', error);
            });
    }, []);

    // --- 2) 오른쪽 ChatRoom에서 새 메시지를 받을 때 호출될 콜백 ---
    //     이 콜백이 rooms 배열을 업데이트하여
    //     왼쪽 ChatRoomList가 자동으로 최신 메시지/시간/뱃지를 리렌더링함
    const handleIncomingMessage = useCallback(
        ({ roomId, message, sendAt, sender }) => {
            setRooms((prevRooms) =>
                prevRooms.map((rm) => {
                    if (rm.roomId !== roomId) return rm;

                    const formattedTime = new Date(sendAt).toLocaleTimeString('ko-KR', {
                        hour: '2-digit',
                        minute: '2-digit',
                        hour12: true,
                    });

                    // 현재 보고 있는 방이 아니면 unreadCount +1, 보고 있으면 0으로 유지
                    const isCurrent = roomId === selectedRoomId;

                    return {
                        ...rm,
                        latestMessage: message,
                        latestTime: formattedTime,
                        unreadCount: isCurrent ? 0 : (rm.unreadCount || 0) + 1,
                    };
                })
            );
        },
        [selectedRoomId]
    );

    const handleSelect = (roomId, roomTitle, authorNickname) => {
        setSelectedRoomId(roomId);
        setSelectedRoomTitle(roomTitle);
        setAuthorNickname(authorNickname);

        // 해당 방의 unreadCount를 0으로 초기화
        setRooms((prev) =>
            prev.map((rm) =>
                rm.roomId === roomId ? { ...rm, unreadCount: 0 } : rm
            )
        );
    };

    return (
        <>
            <AppHeader/>
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
                    {/* 오른쪽: 메시지 뷰 (방 선택 시) */}
                    {selectedRoomId ? (
                        <ChatRoom
                            roomId={selectedRoomId}
                            roomTitle={selectedRoomTitle}
                            authorNickname={authorNickname}
                            onIncomingMessage={handleIncomingMessage}
                        />
                    ) : (
                        <div className="placeholder">채팅방을 선택해주세요.</div>
                    )}
                </div>
            </div>
        </>
    );
}