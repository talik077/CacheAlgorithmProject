package com.hit.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import com.hit.server.Server;

public class CLI implements Runnable {

    private PropertyChangeSupport pcs;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

	protected Scanner m_Scanner;
	protected PrintStream m_PrintStream;
	protected String[] m_AlgoNames;
	private StateEnum state;
	
	public CLI(InputStream in, OutputStream out) {
		this.m_Scanner = new Scanner(in);
		this.m_PrintStream = (PrintStream) out;
		this.m_AlgoNames = new String[] { "LRU", "SecondChance", "Random" };
		this.pcs = new PropertyChangeSupport(this);
		this.state = StateEnum.START;
	}

	public void write(String string) {
		this.m_PrintStream.println(string);
	}
	


	@Override
	public void run() {
		String algorithem = "LRU";
		Integer capacity = 5;
		// TODO Auto-generated method stub
		while (true) {

			write("Please enter your command");
			String input = this.m_Scanner.nextLine();
			if (input.equalsIgnoreCase("stop")) {
				write("Thank you");
				pcs.firePropertyChange("statechange", this.state, StateEnum.STOP);
				this.state =  StateEnum.STOP;
				return;
			} else if (input.contains("Cache_unit_config")) {
				while (true) {
					String[] splited = input.split("\\s+");
					if (splited.length != 3) {
						write("Worng params, Please enter your command");
						input = this.m_Scanner.nextLine();
						continue;
					}
					boolean found = false;
					for (String algo : this.m_AlgoNames) {
						if (splited[1].contains(algo)) {
							pcs.firePropertyChange("algochange", algorithem, algo);
							algorithem = algo;
							found = true;
						}
					}
					if (found == false) {
						write("Unknown alogrithem, Please enter your command");
						input = this.m_Scanner.nextLine();
						continue;
					}
					try {
						Integer cap = Integer.parseInt(splited[2]);
						pcs.firePropertyChange("capcitychange", capacity, cap);
						capacity = cap;
					} catch (NumberFormatException e) {
						write("Capcity is  alogrithem, Please enter your command");
						input = this.m_Scanner.nextLine();
						continue;
					}
					break;
				}
			} else if (input.equalsIgnoreCase("start")) {
				pcs.firePropertyChange("statechange", this.state,  StateEnum.START);
				this.state = StateEnum.START;
			}
		}
	}


}
