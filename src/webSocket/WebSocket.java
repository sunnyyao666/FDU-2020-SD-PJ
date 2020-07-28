package webSocket;

import DAO.MessageDAO;
import domain.Message;
import domain.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@ServerEndpoint(value="/webSocket",configurator= GetHttpSessionConfigurator.class)
public class WebSocket {
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    private MessageDAO messageDAO = new MessageDAO();

    private Long uID;
    private static List<Long> uIDList = new ArrayList<Long>();

    private Session session;
    private static Map<Long, Session> routeTable = new HashMap<Long, Session>();

    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        webSocketSet.add(this);
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.uID = ((User) httpSession.getAttribute("user")).getUID();
        uIDList.add(uID);
        routeTable.put(uID, session);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        uIDList.remove(uID);
        routeTable.remove(uID);
    }

    @OnMessage
    public void onMessage(String jsonMessage) {
        Message message = new Message();
        //存储数据库
        JSONObject jsonObjectMessage = JSON.parseObject(jsonMessage);
        message.setFromUID(jsonObjectMessage.getLongValue("fromUID"));
        message.setToUID(jsonObjectMessage.getLongValue("toUID"));
        message.setContent(jsonObjectMessage.getString("content"));
        message.setType(jsonObjectMessage.getIntValue("type"));
        message.setCreatedTime(Timestamp.valueOf(jsonObjectMessage.getString("createdTime")));
        if (jsonObjectMessage.getIntValue("type") == 0) {
            Long self = jsonObjectMessage.getLongValue("fromUID");
            singleSend(jsonMessage, routeTable.get(self));
        }
        Session singleSession = routeTable.get(jsonObjectMessage.getLongValue("toUID"));
        if (singleSession != null) {
            singleSend(jsonMessage, singleSession);
        }
        messageDAO.save(message);
    }

    public void singleSend(String message, Session session) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
