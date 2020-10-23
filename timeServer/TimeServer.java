package timeServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		
		//Store the threads being created:
        ArrayList<TimeServerThread> clients = new ArrayList<>();
        //State the number of threads we want:
        ExecutorService pool = Executors.newFixedThreadPool(4);
		
		//Wait for client connection and accept it:
		Socket clientSocket = null;
        try {
        	while(true){
        		clientSocket = serverSocket.accept();
                System.out.println("200 OK");
                TimeServerThread clientThread = new TimeServerThread(clientSocket);
                clients.add(clientThread);
                pool.execute(clientThread);
                //System.out.println(TimeServerThread.activeCount());
        	}
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
	}
}
