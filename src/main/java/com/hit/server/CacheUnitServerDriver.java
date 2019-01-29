package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.hit.util.CLI;

public class CacheUnitServerDriver {
	
	class ServerFieldListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// TODO Auto-generated method stub
			 Object source = evt.getSource();
			
		}
	}
	
	public static void main(String[] args) {
		CLI cli = new CLI(System.in, System.out);
		Server server = new Server(34567);
		cli.addPropertyChangeListener(server);
		new Thread(cli).start();
	}
	
}
