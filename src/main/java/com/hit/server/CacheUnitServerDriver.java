package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.hit.util.CLI;

public class CacheUnitServerDriver {
	
	
	public static void main(String[] args) {
		CLI cli = new CLI(System.in, System.out);
		Server server = new Server(34568);
		cli.addPropertyChangeListener(server);
		new Thread(cli).start();
	}
	
}
	