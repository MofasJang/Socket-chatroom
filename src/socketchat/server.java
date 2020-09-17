package socketchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextArea;

public class server implements Runnable {// 服务端
    static List<Socket> socketList=new ArrayList<Socket>();
// 读取 In
    static Socket socket = null;
    static ServerSocket serverSocket = null;
    static JTextArea textArea1=null;
	static String buffer="";
    public server() {// 构造方法
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	serverapp.WinMain();
//        Scanner input = new Scanner(System.in);
        System.out.println("************服务端*************");
        try {
			Thread.sleep(200);
			sqltest.insert("服务器","已上线");
			textArea1.append("*********************服务端*********************\n");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        server t = new server();
        int count = 0;
        while (true) {          
            try {
//            	System.out.println("端口9999等待被连接......");
//            	textArea1.append("端口9999等待被连接......\n");
                socket = serverSocket.accept();
                count++;
                System.out.println("第" + count + "个客户已连接");
                textArea1.append("第" + count + "个客户已连接\n");
                socketList.add(socket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Print p = new Print(socket);
            Thread read = new Thread(t);
            Thread print = new Thread(p);
            read.start();
            print.start();
        }
    }
    public static void setwin(JTextArea area1) {
    	textArea1=area1;
    }
    
    @Override
    public void run() {
    	Socket current = null;
        // 重写run方法
        try {
            Thread.sleep(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            current=socket;
            while (true) {
                String jieshou = in.readLine();
                System.out.println(jieshou);
                textArea1.append(jieshou+"\n");
                String[] neirong=jieshou.split(":");
                String buf1=neirong[0],buf2;;
                if(neirong.length==1) { 	
                	buf2=" ";}
                else {
                	buf2=neirong[1];
                }
                
                sqltest.insert(buf1, buf2);
                for (int i = 0; i < socketList.size(); i++) {
                    Socket socket=socketList.get(i);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    if (socket!=current) {
                        out.println(jieshou);
                    }else{
                        out.println("(你)"+jieshou);
                    }
                    out.flush();
                }
            }
        } catch (Exception e) {
        	socketList.remove(current);
        	Print.socketList.remove(current);
    		textArea1.append("用户已下线"+"\n");
            e.printStackTrace();
        } 
    }
}
class Print implements Runnable {
    static List<Socket> socketList=new ArrayList<Socket>();
    Scanner input = new Scanner(System.in);
    public Print(Socket s) {// 构造方法
        try {
            socketList.add(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            while (true) {
            	while(server.buffer=="") {
            		try {
        				Thread.sleep(1000);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
            	}
            	String msg = server.buffer.replaceAll("(\r\n|\r|\n|\n\r)", "");
            	System.out.println("客户端数量："+socketList.size());
	            for (int i = 0; i < socketList.size(); i++) {
	                Socket socket=socketList.get(i);
	                PrintWriter out = new PrintWriter(socket.getOutputStream());
	                out.println("服务器："+msg);
	                out.flush();
	            }
	            System.out.println("服务器："+msg);
                server.textArea1.append("服务器："+msg+"\n");
                sqltest.insert("服务器", msg);
	            server.buffer="";
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}