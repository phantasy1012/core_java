package rj;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Echo Client
 * 
 * @version		1.0 2016-4-7
 * @author 		suwenqiang@ruijie.com.cn
 */
public class EchoClient
{	
	/** main method */
    public static void main( String[] args )
    {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        
        Thread t = Thread.currentThread();
        t.setName("echo client main thread");
        
        Producer producer = new Producer(queue);
        Thread p = new Thread(producer);
        p.setName("echo client producer thread");
        p.start();
        
        Consumer consumer = new Consumer(queue);
        Thread c = new Thread(consumer);
        c.setName("echo client consumer thread");
        c.start();
    }
}

class Producer implements Runnable{
    private BlockingQueue<String> queue;
    
    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }
    
    public void run() {
        try(Scanner in = new Scanner(System.in)) {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("$ ");
                /* input.nextLine is blocking and non-interruptible */
                String message = in.nextLine();
                queue.put(message);
            }
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }        
    }    
}

class Consumer implements Runnable{
    private BlockingQueue<String> queue;
    
    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }
    
    public void run() {
        String message;
        String result;
        try (Socket s = new Socket("172.18.41.54", 9009)) {
            InputStream inStream = s.getInputStream();
            OutputStream outStream = s.getOutputStream();
            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream, true);
            if (in.hasNextLine()) { /* hasNextLine是阻塞的,怎么破？ */
                result = in.nextLine();
                System.out.println(result);
                System.out.print("$ ");
            }            
            while (true) {
                message = queue.take();
                out.println(message);
                if (in.hasNextLine()) {
					result = in.nextLine();
					System.out.println(result);
                    System.out.print("$ ");
                }
            }
        } catch (InterruptedException e) {
        } catch (IOException e) {
        }
    }    
}