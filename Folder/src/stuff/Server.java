package stuff;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame {
	
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ServerSocket server;
	private Socket connection;
	
	
	
	
	public Server() {
		super("TestField");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
				);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300,150);
		setVisible(true);
		
		
	}
	
	
	public void startRun() {
		
		try {
			server = new ServerSocket(777,100);
			
			while(true) {
				try {
					waitForConnection();
					setupStreams();
					whileChatting();
				
				
			}catch(EOFException eo) {
				showMessage("\n Server ended the connection!");
			}finally {
				closeCrap();
			}}}
			catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		}	
			
			private void closeCrap() {
		// TODO Auto-generated method stub
				showMessage("\n Closing connections...\n");
				ableToType(false);
				
				
				try {
					output.close();
					input.close();
					connection.close();
				
					
				}catch(IOException io) {
					System.out.println(io.getMessage());
				}
		
	}


			//wait for connection, then display connection
		private void waitForConnection()throws IOException {
			showMessage("waiting for someone to connect...  \n");
			connection = server.accept();
			showMessage("Now connected to " + connection.getInetAddress().getHostName());
		}
		
		
		//get stream to send and recieve data
		private void setupStreams() throws IOException{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			
			input= new ObjectInputStream(connection.getInputStream());
			showMessage("\n Streams are now setup \n");
			
		}
		
		
		private void whileChatting() throws IOException{
			String message = "you are now connected!";
			sendMessage(message);
			ableToType(true);
			
			do {
				
				try {
					message =(String) input.readObject();
					showMessage("\n " + message);
				}catch(ClassNotFoundException classNotFoundException) {
					showMessage("\n idk wtf was sent");
				}
			
				//having a conversation
			}while(!message.equals("CLIENT - END"));
			
		}


		private void ableToType(final boolean tof) {
			// TODO Auto-generated method stub
			SwingUtilities.invokeLater(
					new Runnable() {
						public void run() {
							userText.setEditable(tof);
					}
			});
			
		}


		private void sendMessage(String message) {
			// TODO Auto-generated method stub
			
			try {
			
			    
				output.writeObject("Server -" + message);
				output.flush();
				showMessage("\n Server -" + message);
			}catch(IOException io) {
			chatWindow.append("\n Error: couldn't send message");
		}
		
	}


		private void showMessage(final String text) {
			// TODO Auto-generated method stub
			
			SwingUtilities.invokeLater(
					new Runnable() {
						public void run() {
							chatWindow.append(text);
						}
					});
			
		}
	
	
	}	
	
	


