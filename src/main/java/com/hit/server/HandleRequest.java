package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements Runnable {

	protected Socket m_Socket;
	protected CacheUnitController<T> m_Controller;
	
	public HandleRequest(Socket s, CacheUnitController<T> controller) 
	{
		this.m_Socket = s;
		this.m_Controller = controller;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
        	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        	try {
        		Request<DataModel<String>> o = (Request<DataModel<String>>)in.readObject();
                System.out.println("Read object: "+o);
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error handling client");
        } finally {
            try { socket.close(); } catch (IOException e) {}
            System.out.println("Connection with client  closed");
        }
	}

}
