package socketchat;

import java.sql.*;
import java.util.Date;

import javax.swing.JTextArea;

import java.text.SimpleDateFormat;

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
            sql1 = "insert into record (name,buf,time) values (\'"+buf1+"\', \'"+buf2+"\',\'"+curtime+"\')";
            stmt.executeUpdate(sql1);
            String sql2;
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
