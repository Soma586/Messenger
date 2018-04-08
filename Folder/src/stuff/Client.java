package stuff;



import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {

	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	
	
	
	public Client(String host) {
		super("client mofo");
		
		serverIP = host;
		
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						sendData(event.getActionCommand());
						userText.setText("");
					}
				}
				);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		setSize(300,150);
		setVisible(true);
		
	}
	
	public void startRun() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		
	}catch(EOFException eof) {
		showMessage("\n Client terminated connection");
	}catch(IOException io) {
		System.out.println(io.getMessage());
	}finally {
		closeCrap();
	}
		
		
}

	private void closeCrap() {
		showMessage("\n everything is closing...");
		ableToType(false);
		
		try {
			output.close();
			input.close();
			
			connection.close();
		}catch(IOException io) {
			System.out.println(io.getMessage());
		}
		
	}

	private void whileChatting() throws IOException {
		ableToType(true);
		
		do {
			try {
				message = (String)input.readObject();
				showMessage("\n" + message);
				
			}catch(ClassNotFoundException classNotFoundException) {
				showMessage("\n I don't know that object");
			}
		}while(!message.equals("Server -End"));
		
	}

	private void setupStreams() throws IOException{
		output= new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input= new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup :D \n");
		
		
	}

	private void connectToServer() throws IOException {
		// TODO Auto-generated method stub
		showMessage("Attempting connection...\n");
		
			connection = new Socket(InetAddress.getByName(serverIP), 777);
			showMessage("connection to:" + connection.getInetAddress().getHostName());
		
		
	}
	
	private void sendData(String data) {
		
		try {
			
		    
			output.writeObject("Client -" + message);
			output.flush();
			showMessage("\n client -" + message);
		}catch(IOException io) {
		chatWindow.append("\n Error: couldn't send message to host");
	}
		
	}

	private void showMessage(final String string) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						chatWindow.append(string);
					}
				});
		
	}
	
	
	
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						userText.setEditable(tof);
				}
		});
		
	}
	
		
	}
	
	
	
	
	
	
	
	
