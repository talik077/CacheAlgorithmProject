package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

/***
 * Handle the request and response of the server
 * @author Shahar and Tal
 *
 * @param <T>
 */
public class HandleRequest<T> implements Runnable {

	private static Gson gson = new Gson();

	protected Socket m_Socket;
	protected CacheUnitController<T> m_Controller;

	private ObjectInputStream in;

	/**
	 * 
	 * @param s - The Socket of the user
	 * @param controller - The Cache Unit controller that retrive data from the CacheUnit
	 */
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

	/**
	 * Handle the process of Getting the request, sending the response and closing the socket.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectOutputStream out = null;
		Request<DataModel<T>[]> request = this.GetRequest(m_Socket);
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
					in.close();
					out.close();
					this.m_Socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
	
	/**
	 * 
	 * @param socket
	 * @return The Request parsed with the data models.
	 */
	private Request<DataModel<T>[]> GetRequest(Socket socket) {
		// ObjectInputStream in = null;
		String jsonStr = null;
		
		try {
			jsonStr = (String) this.in.readObject().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    GsonBuilder gsonBldr = new GsonBuilder();
	    Request<DataModel<T>[]> request = gsonBldr.setLenient().create().fromJson(jsonStr, new TypeToken<Request<DataModel<T>[]>>() {}.getType());
		return request;
	}
	
	/**
	 * 
	 * @param request
	 * @return the response that the server will send to the client, depends on the action in the headers
	 *
	 */
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
		case "OPTIONS": 
			Map<String, Integer> CacheData = (Map<String, Integer>)this.m_Controller.options();
			Response<Map<String, Integer>> responseOptions = new Response<Map<String, Integer>>(request.getHeaders(), CacheData);
			jsonResult = gson.toJson(responseOptions, new TypeToken<Response<Map<String, Integer>>>() {}.getType());
			break;
		}

		return jsonResult;
	}

}
