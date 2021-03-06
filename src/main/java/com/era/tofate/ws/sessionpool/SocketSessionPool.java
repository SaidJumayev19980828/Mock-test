package com.era.tofate.ws.sessionpool;

import com.era.tofate.entities.user.User;
import com.era.tofate.ws.exception.AuthSocketException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketSessionPool {
    private static final String ACCOUNT_NOT_AUTHORIZE = "Пройдите процедуру авторизации";
    private final Map<WebSocketSession, User> sessions = new ConcurrentHashMap<>();
    private final Map<Long, Boolean> userOnline = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public void openSession(WebSocketSession session, User account) {
        sessions.put(session, account);
        userOnline.put(account.getId(), true);
    }

    public void closeSession(WebSocketSession session, CloseStatus closeStatus) {
        boolean isOnline = false;

        try {
            Long accountId = getSessionAccount((session)).getId();

            for (Map.Entry<WebSocketSession, User> entry : sessions.entrySet()) {
                if (accountId.equals(entry.getValue().getId())) {
                    if (entry.getKey().isOpen()) {
                        isOnline = true;
                    }
                }
            }
        } catch (NullPointerException ignored) {
        } finally {
//            userOnline.put(getSessionAccount((session)).getId(), isOnline);
            sessions.remove(session);
        }

        try {
            session.close(closeStatus);
        } catch (IOException ignored) {
        }
    }

    public User getSessionAccount(WebSocketSession session) {
        return sessions.get(session);
    }

    public Map<WebSocketSession, User> getSessions() {
        return sessions;
    }

    public Map<Long, Boolean> getUserOnline() {
        return userOnline;
    }

    public void checkAuth(WebSocketSession session) {
        if (getSessionAccount(session) == null) {
            throw new AuthSocketException(ACCOUNT_NOT_AUTHORIZE);
        }
    }
}
