package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hit.algorithm.*;
import com.hit.services.CacheUnitController;
import com.hit.util.StateEnum;
import com.hit.services.CacheUnitService;

public class Server implements Runnable, PropertyChangeListener, EventListener {
	private int m_Port;

	private StateEnum m_State = StateEnum.STOP;
	private String m_Algo = "LRU";
	private int m_Capcity = 5;

	public Server(int port) {
		this.m_Port = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub+
		Socket socket = null;
		ExecutorService pool = Executors.newFixedThreadPool(20);
		ServerSocket listener = null;
		try {
			listener = new ServerSocket(this.m_Port);
			// listener.setSoTimeout(100);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (this.m_State != StateEnum.STOP) {

			try {
				socket = listener.accept();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (socket != null) {
				HandleRequest<String> handleRequest = new HandleRequest<String>(socket,
						new CacheUnitController<String>());
				pool.execute(handleRequest);
				socket = null;
			}

		}
		this.terminateServer(pool, listener);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		Object newVal = evt.getNewValue();
		switch (name) {
		case "statechange":
			m_State = (StateEnum) newVal;

			if (m_State == StateEnum.START) {
				new Thread(this).start();
			}

			break;
		case "algochange":
			m_Algo = (String) newVal;
			switch (m_Algo.toLowerCase()) {
			case "lru":
				CacheUnitService.changeAlgo(new LRUAlgoCacheImpl(this.m_Capcity));
				break;
			case "random":
				CacheUnitService.changeAlgo(new RandomAlgoCacheImpl(this.m_Capcity));
				break;
			case "secondchance":
				CacheUnitService.changeAlgo(new SecondChance(this.m_Capcity));
				break;
			}
			break;
		case "capcitychange":
			m_Capcity = (Integer) newVal;
			CacheUnitService.changeCapcity(this.m_Capcity);
			break;
		}
	}

	private void terminateServer(ExecutorService executor, ServerSocket socket) {
		executor.shutdown();

		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
