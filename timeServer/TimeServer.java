package timeServer;

import java.io.*;
import java.net.*;
import java.util.Date;

public class TimeServer {
	public static void main(String[] args) throws IOException{
		//Create server socket:
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4444);
			
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
            System.exit(1);
		}
		System.out.println("Server up and running.");
		
		//Wait for client connection and accept it:
		Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("200 OK");
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
		
        //Used later to print date and time:
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		//So that we can read the client's messages:
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		//Loop the interaction 
		try{
			while(true){
				String request = in.readLine(); //Read client requests
				if(request.equalsIgnoreCase("What time is it?")){
					out.println((new Date()).toString());
				} 
				else{
					out.println("I only understand 'What time is it?'");
				}
			}
		}finally{
			out.close();
			in.close();
			clientSocket.close();
			serverSocket.close();
		}
	}
}
