package es.deusto.client.main;

import javax.swing.JFrame;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.LogInWindow;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Controller c = new Controller("localhost", "8080");
		LogInWindow login = new LogInWindow(c);
		login.setVisible(true);
		login.setSize(375, 422);
		login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		login.setResizable(false);

	}

}
