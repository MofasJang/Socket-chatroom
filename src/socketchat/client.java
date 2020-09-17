package socketchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JTextArea;
public class client implements Runnable {// 客户端
    static Socket socket = null;
    static Scanner input = new Scanner(System.in);
    static String name=null;
    static JTextArea textArea=null;
	protected static String buffer="";
    public static void main(String[] args) {
    	clientapp.WinMain();
    	while(buffer=="") {
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	name=buffer.replaceAll("(\r\n|\r|\n|\n\r)", "");
    	System.out.println("************客户端:"+client.name+"*************");
    	textArea.append("**************************客户端:"+client.name+"***************************\n");
    	buffer="";
        try {
            socket = new Socket("127.0.0.1", 9999);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(name+":我已上线");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        client t = new client();
        Read r = new Read(socket);
        Thread print = new Thread(t);
        Thread read = new Thread(r);
        print.start();
        read.start();
    }
    public static void setwin(JTextArea a1) {
    	textArea=a1;
    }
    @Override
    public void run() {
    	PrintWriter out = null;
        try {
            Thread.sleep(1000);         
            out = new PrintWriter(socket.getOutputStream());
            
            while (true) {
            	while(buffer=="") {
            		try {
        				Thread.sleep(1000);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
            	}
        		String msg = buffer.replaceAll("(\r\n|\r|\n|\n\r)", "");
        		out.println(name+":"+msg);
        		out.flush();
        		buffer="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	out.println(name+":已下线");
        		out.flush();
        		Thread.sleep(1000);
                socket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Read implements Runnable {
    Socket socket = null;
    public Read(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            while (true) {
            	String text=in.readLine() ;
            	client.textArea.append(text+"\n");
            	System.out.println(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

