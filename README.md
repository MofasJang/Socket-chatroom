java多人socket聊天室

> 作者：潘江涛

### 一. 项目简介

​		使用java作为开发语言开发一款基于socket技术的多人聊天应用，该程序由服务端和客户端两个界面组成，其中客户端由聊天用户使用，发送聊天消息并接受服务端和其他客户端的消息；服务端由管理员使用，接受客户端的socket连接，转发各客户端发送的消息，也能够向客户端发送消息，并将所有消息存入数据库中，在界面上实时更新聊天记录。



### 二. 项目技术栈

1. java中Socket的使用
2. 多线程接口Runnable的使用
3. SQL语言及JDBC的使用
4. swing窗体程序的建立



### 三. 项目参考资料

1. [java中的Socket的使用](https://blog.csdn.net/a78270528/article/details/80318571)

2. [java 多线程之 implements Runnable](https://blog.csdn.net/qq_38428623/article/details/85868358)

3. [使用JAVA编程实现多人聊天室](https://blog.csdn.net/qq_29606255/article/details/78679815)
4. [Eclipse搭建Java Swing可视化开发环境](https://blog.csdn.net/renwudao24/article/details/51864323)
5. [Java MySQL 连接](https://www.runoob.com/java/java-mysql-connect.html)



### 四. 项目文件结构

* socketchat     			 (package)
  * client.java			(客户端的socket连接，消息接收和发送)
  * clientapp.java 	(客户端界面，显示其他客户端和服务端的消息)
  * server.java		  (服务端的连接客户端socket，消息接收和转发)
  * serverapp.java	(服务端界面，显示所有客户端的消息和所有聊天记录)
  * sqltext.java		  (连接数据库，存入和读取聊天记录)



### 五. 项目代码描述

1. server.java 服务器转发线程

```java
public class server implements Runnable {// 服务端
    static List<Socket> socketList=new ArrayList<Socket>(); //多个socket实现多人聊天
    static Socket socket = null;
    static ServerSocket serverSocket = null;
    static JTextArea textArea1=null;
	static String buffer="";
    public server() {  // 构造方法
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
        while (true) {   //循环等待客户端连接       
            try {
                socket = serverSocket.accept();
                count++;
                System.out.println("第" + count + "个客户已连接");
                textArea1.append("第" + count + "个客户已连接\n");
                socketList.add(socket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //开启转发线程和发消息线程
            Print p = new Print(socket);
            Thread read = new Thread(t);
            Thread print = new Thread(p);
            read.start();
            print.start();
        }
    }
    public static void setwin(JTextArea area1) {//将服务器界面的消息打印框赋值到server类中
    	textArea1=area1;
    }
    
    @Override
    public void run() {		// 重写run方法
    	Socket current = null;  
        try {
            Thread.sleep(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            current=socket;
            while (true) {
                //将接收的字符串保存到数据库中
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
                //将消息转发给所有用户
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
```

2. server.java 服务器发送消息线程

```java
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
            	while(server.buffer=="") {  //如没有消息需要发送，则循环等待
            		try {
        				Thread.sleep(1000);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
            	}
            	String msg = server.buffer.replaceAll("(\r\n|\r|\n|\n\r)", "");
            	System.out.println("客户端数量："+socketList.size());
                //向所有客户端发送服务器消息
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
```

3. client.java 客户端连接socket、发送消息线程

```java
public class client implements Runnable {// 客户端
    static Socket socket = null;
    static Scanner input = new Scanner(System.in);
    static String name=null;
    static JTextArea textArea=null;
	protected static String buffer="";
    public static void main(String[] args) {
    	clientapp.WinMain();
    	while(buffer=="") { //等待客户输入名字
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
        try {//连接服务器socket
            socket = new Socket("127.0.0.1", 9999);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(name+":我已上线");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //开启发送、接收消息线程
        client t = new client();
        Read r = new Read(socket);
        Thread print = new Thread(t);
        Thread read = new Thread(r);
        print.start();
        read.start();
    }
    public static void setwin(JTextArea a1) {//将客户端界面的消息打印框赋值到client类中
    	textArea=a1;
    }
    @Override
    public void run() {
    	PrintWriter out = null;
        try {
            Thread.sleep(1000);         
            out = new PrintWriter(socket.getOutputStream());
            
            while (true) {//等待用户输入消息
            	while(buffer=="") {
            		try {
        				Thread.sleep(1000);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
            	}
                //发送消息
        		String msg = buffer.replaceAll("(\r\n|\r|\n|\n\r)", "");
        		out.println(name+":"+msg);
        		out.flush();
        		buffer="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {//用户下线，向服务器发送下线消息，关闭socket
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
```

4. client.java 客户端接收消息线程

```java
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
            while (true) {	//接收到消息，打印在界面上
            	String text=in.readLine() ;
            	client.textArea.append(text+"\n");
            	System.out.println(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

5. sqltext.java 连接数据库

```java
public class sqltest {  //连接数据库

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/chatroom?useUnicode=true&characterEncoding=UTF-8";
    
    static final String USER = "root";
    static final String PASS = "1234";
    static JTextArea textArea=null;
    
	public static void insert(String buf1,String buf2) {
		// TODO Auto-generated method stub
		Connection conn = null;
        Statement stmt = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String curtime=df.format(new Date());// new Date()为获取当前系统时间
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        
            // 打开链接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            // 执行查询
            stmt = conn.createStatement();
            String sql1;
            //将聊天人的名字、聊天内容和时间记入数据库
            sql1 = "insert into record (name,buf,time) values (\'"+buf1+"\', \'"+buf2+"\',\'"+curtime+"\')";
            stmt.executeUpdate(sql1);
            String sql2;
            //查询数据库中所有聊天记录
            sql2 = "SELECT id,name, buf,time FROM record";
            ResultSet rs = stmt.executeQuery(sql2);
            textArea.setText("");
            
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                String name  = rs.getString("name");
                String buf = rs.getString("buf");
                String time = rs.getString("time");
                
                textArea.append(time+"\n"+name+" : "+buf+"\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}
	
	public static void setwin(JTextArea area) {
    	textArea=area;
    }

}
```

6. serverapp.java , clientapp.java 客户端和服务器界面主要由AWT/SWING可视化插件生成界面代码

### 六. 项目成果截图

1. 服务器初始化，调出所有聊天记录

![服务端初始](./项目截图/服务端初始.jpg)

2. 客户端初始化，需要输入名字

![客户端初始](./项目截图/客户端初始.jpg)

3. 客户上线

![客户上线](./项目截图/客户上线.jpg)

4. 客户和服务器分别发送消息

![发送消息](./项目截图/发送消息.jpg)

5. 客户端下线

![客户端下线](./项目截图/客户端下线.jpg)
