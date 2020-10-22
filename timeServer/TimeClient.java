package timeServer;

import java.io.*;
import java.net.*;

public class TimeClient {
	public static void main(String[] args) throws IOException{
		Socket clientSocket = null;
		PrintWriter output = null;
		BufferedReader input = null;
		
		try{
			clientSocket = new Socket("localhost", 4444);
			//So that we can both send and receive messages to/from the server:
			//Messages sent to server:
			output = new PrintWriter(clientSocket.getOutputStream(), true);
			//Messages received from server:
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch(UnknownHostException e){
			System.err.println("Can't find host");
			System.exit(1);
		}catch(IOException e){
			System.err.println("Couldn't get I/O for the connection: 4444");
			System.exit(1);
		}
		
		System.out.println("Client up and running!");
		
		
		//Get keyboard input (our message):
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		//Loop the interaction, so that it doesn't stop.
		boolean listening = true;
		while(listening){
			//Get our keyboard (client) messages:
			String fromClient = keyboard.readLine();
			System.out.println("CLIENT: "+ fromClient);
			
			//If we type "quit", it stops the loop.
			if(fromClient.equals("quit"))
				listening = false;
			output.println(fromClient); //Send the messages we type to the server.
			String fromServer = input.readLine(); //Read server replies
			System.out.println("SERVER: "+ fromServer);
		}
		
		//Tidy up
		output.close();
		input.close();
		keyboard.close();
		clientSocket.close();
	}
}
