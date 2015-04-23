package video.app.com.websocket;

import video.app.autobahn.WebSocketConnection;
import video.app.autobahn.WebSocketConnectionHandler;

public class WebSocketMain {
	private static WebSocketMain webSocketMain;
	private WebSocketConnection wsConnection;
	private WebSocketConnectionHandler handler;
	
	public WebSocketMain(){
		wsConnection = new WebSocketConnection();
	}
	
	public static WebSocketMain getInstance(){
		if(webSocketMain==null){
			webSocketMain = new WebSocketMain();
		}
		return webSocketMain;
	}

	public WebSocketConnection getWSConnection() {
		return wsConnection;
	}
	
}
