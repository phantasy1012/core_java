package swq;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try (ServerSocket s = new ServerSocket(9009)) {
			int i = 1;
			while (true) {
				Socket incoming = s.accept();
				System.out.println("Client Numbers: " + i);
				Runnable h = new EchoHandler(incoming);
				Thread t = new Thread(h);
				t.start();
				i++;
			}
		}
	}
}

class EchoHandler implements Runnable {
	private Socket incoming;
	
	public EchoHandler(Socket i) {
		incoming = i;
	}
	
	public void run() {
		try {
			InputStream inStream = incoming.getInputStream();
			OutputStream outStream = incoming.getOutputStream();
			
			try (Scanner in = new Scanner(inStream); PrintWriter out = new PrintWriter(outStream, true)) {
				out.println("Hello. Enter shit to exit.");
				
				boolean done = false;
				while (!done && in.hasNextLine()) {
					String line = in.nextLine();
					System.out.println(line);
					Thread.sleep(2000);
					out.println("Echo: " + line);
					if (line.trim().equals("shit")) {
						done = true;
						System.out.println("Exit");
					}
				}
			} catch (InterruptedException e) { 
				Thread.currentThread().interrupt(); 
			}  finally {
		    	incoming.close();
		    }
		} catch (IOException e) {  
			e.printStackTrace();
	    }					
	}
}
