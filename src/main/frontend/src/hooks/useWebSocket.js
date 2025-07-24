// src/hooks/useWebSocket.js
import { useEffect, useRef, useCallback } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export const useWebSocket = (onMessage) => {
  const clientRef = useRef(null);
  const subscriptionsRef = useRef(new Map());
  const pendingRoomIdsRef = useRef([]); // 👈 구독 대기방 목록

  const subscribeToRooms = useCallback((roomIds) => {
    const client = clientRef.current;
    if (!client || !client.connected) {
      // 👇 STOMP 연결 전이면 대기 큐에 추가
      pendingRoomIdsRef.current.push(...roomIds);
      console.warn('STOMP not connected yet. Queued rooms:', roomIds);
      return;
    }

    roomIds.forEach((roomId) => {
      if (!subscriptionsRef.current.has(roomId)) {
        const sub = client.subscribe(
            `/exchange/chat.exchange/room.${roomId}`,
            (msg) => onMessage(JSON.parse(msg.body))
        );
        subscriptionsRef.current.set(roomId, sub);
        console.log(`Subscribed to room ${roomId}`);
      }
    });
  }, [onMessage]);

  useEffect(() => {
    const client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      connectHeaders: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
      },
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('STOMP connected ✅');
        clientRef.current = client;

        // ✅ 연결되면 대기 중이던 방들 구독 처리
        const pending = [...new Set(pendingRoomIdsRef.current)];
        pendingRoomIdsRef.current = [];
        if (pending.length > 0) {
          console.log('Subscribing pending rooms:', pending);
          subscribeToRooms(pending);
        }
      },
      onStompError: (err) => console.error('STOMP error:', err),
      onWebSocketError: (e) => console.error('WS error:', e),
    });

    client.activate();
    clientRef.current = client;

    return () => {
      subscriptionsRef.current.forEach((sub) => sub.unsubscribe());
      subscriptionsRef.current.clear();
      client.deactivate();
    };
  }, [subscribeToRooms]);

  const sendMessage = useCallback((payload) => {
    const client = clientRef.current;
    if (!client || !client.connected) {
      console.warn('STOMP not connected');
      return;
    }

    client.publish({
      destination: `/pub/chat.message.${payload.roomId}`,
      body: JSON.stringify(payload),
    });

    console.log(`Sent message to room ${payload.roomId}`);
  }, []);

  return { sendMessage, subscribeToRooms };
};