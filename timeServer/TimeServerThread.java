package timeServer;

import java.net.*;
import java.util.Date;
import java.io.*;

public class TimeServerThread extends Thread{
	private Socket clientSocket = null;
	private BufferedReader in;
	private PrintWriter out;
	
	
	//Create constructor
	public TimeServerThread(Socket clientSocket) throws IOException{
		this.clientSocket = clientSocket;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream(), true);
	}
	
	//Does what the server used to do by itself:
	public void run(){
		try{
			while(true){
				String request = in.readLine(); //Read client requests
				if(request.equalsIgnoreCase("What time is it?")){
					out.println((new Date()).toString());
				}
				else if(request.equalsIgnoreCase("thanks!")){ //A bit of banter
					out.println("You're welcome :')");
				}
				else{
					out.println("I only understand 'What time is it?'");
				}
			}
		} catch (IOException e) {
			System.err.println("IO Exception in TimeServerThread.");
			System.err.println(e.getStackTrace());
		}finally{
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
