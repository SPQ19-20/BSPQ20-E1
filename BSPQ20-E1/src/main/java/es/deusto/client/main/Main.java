package es.deusto.client.main;

import javax.swing.JFrame;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.ForgottenPassword;
import es.deusto.client.windows.LogInWindow;
import es.deusto.client.windows.UserEventsWindow;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Controller c = new Controller("localhost", "8080");
		new LogInWindow(c);
	}

}