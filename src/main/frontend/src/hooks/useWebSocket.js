// src/hooks/useWebSocket.js
import { useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export const useWebSocket = (roomId, onMessage) => {
  const clientRef       = useRef(null);
  const subscriptionRef = useRef(null);
  const roomRef         = useRef(roomId);

  useEffect(() => { roomRef.current = roomId; }, [roomId]);

  // 1) STOMP client 단일 생성
  useEffect(() => {
    const client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      connectHeaders:  { Authorization: `Bearer ${localStorage.getItem('accessToken')}` },
      reconnectDelay:  5000,    // 재연결 대기 짧게
      // heartbeatIncoming: 4000,
      // heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log('STOMP connected');
        if (roomRef.current) {
          subscriptionRef.current = client.subscribe(
              `/exchange/chat.exchange/room.${roomRef.current}`,
              (msg) => onMessage(JSON.parse(msg.body))
          );
          console.log(`Subscribed to room ${roomRef.current} on connect`);
        }
      },
      onStompError:    (err) => console.error('STOMP error:', err),
      onWebSocketError:(e)   => console.error('WS error:', e),
    });
    client.activate();
    clientRef.current = client;

    return () => {
      if (subscriptionRef.current) subscriptionRef.current.unsubscribe();
      client.deactivate();
    };
  }, []);

  // 2) roomId 변경 시 토픽만 전환
  useEffect(() => {
    const client = clientRef.current;
    if (!client) return;

    if (subscriptionRef.current) {
      subscriptionRef.current.unsubscribe();
      subscriptionRef.current = null;
    }
    if (!roomId) return;

    if (client.connected) {
      subscriptionRef.current = client.subscribe(
          `/exchange/chat.exchange/room.${roomId}`,
          (msg) => onMessage(JSON.parse(msg.body))
      );
      console.log(`Subscribed to room ${roomId}`);
    } else {
      console.log('Waiting for STOMP connection to subscribe');
    }

    return () => {
      if (subscriptionRef.current) {
        subscriptionRef.current.unsubscribe();
        subscriptionRef.current = null;
      }
    };
  }, [roomId, onMessage]);

  // 메시지 전송
  const sendMessage = (payload) => {
    const client = clientRef.current;
    if (!client || !client.connected) {
      console.warn('STOMP not connected');
      return;
    }
    client.publish({
      destination: `/pub/chat.message.${payload.roomId}`,
      body: JSON.stringify(payload),
    });
  };

  return { sendMessage };
};