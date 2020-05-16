package es.deusto.client.main;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import es.deusto.client.controller.Controller;
import es.deusto.client.windows.CreateEvent;
import es.deusto.client.windows.EventWindow;
import es.deusto.client.windows.ForgottenPassword;
import es.deusto.client.windows.LogInWindow;

import es.deusto.client.windows.UserEventsWindow;
import es.deusto.serialization.EventInfo;
import es.deusto.serialization.OrganizerInfo;
import es.deusto.serialization.PostInfo;
import es.deusto.serialization.TopicInfo;

/**
 * This class is the one that starts the aplication.
 * 
 * It creates a new controller for the user to connect to the
 * server and opens a new LogInWindow where the user can start
 * using the application by login in or creating a new user.
 */

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		String host, port;
		host = args[0];
		port = args[1];
		System.err.println(host);
		Controller c = new Controller(host, port); 

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		new LogInWindow(c);
	}

}