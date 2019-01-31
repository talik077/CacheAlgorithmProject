package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements Runnable {

	private static Gson gson = new Gson();

	protected Socket m_Socket;
	protected CacheUnitController<T> m_Controller;

	private ObjectInputStream in;

	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		this.m_Socket = s;
		this.m_Controller = controller;
		try {
			this.in = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectOutputStream out = null;
		Request<DataModel<T>[]> request = this.Request(m_Socket);
		if (request != null) {

			String jsonResponse = this.Response(request);

			try {
				out = new ObjectOutputStream(this.m_Socket.getOutputStream());
				out.writeObject(jsonResponse);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					this.m_Socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private Request<DataModel<T>[]> Request(Socket socket) {
		// ObjectInputStream in = null;
		String jsonStr = null;
		
		try {
			jsonStr = (String) this.in.readObject().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String myString = jsonStr.substring(1, jsonStr.length());
		myString = myString.replaceAll("(\\r|\\n)", "");
	    GsonBuilder gsonBldr = new GsonBuilder();
	    Request<DataModel<T>[]> request = gsonBldr.setLenient().create().fromJson(myString, new TypeToken<Request<DataModel<T>[]>>() {}.getType());

		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return request;
	}

	private String Response(Request<DataModel<T>[]> request) {
		String action = request.getHeaders().get("action");
		DataModel<T>[] dataModels = request.getBody();

		String jsonResult = null;

		switch (action) {
		case "UPDATE":
			Boolean res = this.m_Controller.update(dataModels);
			Response<Boolean> response = new Response<Boolean>(request.getHeaders(), res);
			jsonResult = gson.toJson(response);
			break;
		case "GET":
			DataModel<T>[] resGet = this.m_Controller.get(dataModels);
			Response<DataModel<T>[]> responseGet = new Response<DataModel<T>[]>(request.getHeaders(), resGet);
			jsonResult = gson.toJson(responseGet);
			break;
		case "DELETE":
			Boolean resDel = this.m_Controller.delete(dataModels);
			Response<Boolean> responseDelete = new Response<Boolean>(request.getHeaders(), resDel);
			jsonResult = gson.toJson(responseDelete);
			break;
		}

		return jsonResult;
	}

}
