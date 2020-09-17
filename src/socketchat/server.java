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

public class server implements Runnable {// �����
    static List<Socket> socketList=new ArrayList<Socket>();
// ��ȡ In
    static Socket socket = null;
    static ServerSocket serverSocket = null;
    static JTextArea textArea1=null;
	static String buffer="";
    public server() {// ���췽��
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	serverapp.WinMain();
//        Scanner input = new Scanner(System.in);
        System.out.println("************�����*************");
        try {
			Thread.sleep(200);
			sqltest.insert("������","������");
			textArea1.append("*********************�����*********************\n");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        server t = new server();
        int count = 0;
        while (true) {          
            try {
//            	System.out.println("�˿�9999�ȴ�������......");
//            	textArea1.append("�˿�9999�ȴ�������......\n");
                socket = serverSocket.accept();
                count++;
                System.out.println("��" + count + "���ͻ�������");
                textArea1.append("��" + count + "���ͻ�������\n");
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
        // ��дrun����
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
                        out.println("(��)"+jieshou);
                    }
                    out.flush();
                }
            }
        } catch (Exception e) {
        	socketList.remove(current);
        	Print.socketList.remove(current);
    		textArea1.append("�û�������"+"\n");
            e.printStackTrace();
        } 
    }
}
class Print implements Runnable {
    static List<Socket> socketList=new ArrayList<Socket>();
    Scanner input = new Scanner(System.in);
    public Print(Socket s) {// ���췽��
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
            	System.out.println("�ͻ���������"+socketList.size());
	            for (int i = 0; i < socketList.size(); i++) {
	                Socket socket=socketList.get(i);
	                PrintWriter out = new PrintWriter(socket.getOutputStream());
	                out.println("��������"+msg);
	                out.flush();
	            }
	            System.out.println("��������"+msg);
                server.textArea1.append("��������"+msg+"\n");
                sqltest.insert("������", msg);
	            server.buffer="";
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}