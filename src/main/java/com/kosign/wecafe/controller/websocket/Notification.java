package com.kosign.wecafe.controller.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/notification")
public class Notification {

	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	@OnMessage
	public String onMessage(String message) {
		sendMessageToAll(message);
		return null;
	}

	@OnOpen
	public void onOpen(Session peer) {
		peers.add(peer);
	}

	@OnClose
	public void onClose(Session peer) {
		peers.remove(peer);
	}

	private void sendMessageToAll(String message) {
		for (Session s : peers) {
			try {
				System.out.println("MESSAGE=" + message);
				s.getBasicRemote().sendText(message);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
