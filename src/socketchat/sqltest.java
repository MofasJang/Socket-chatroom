package socketchat;

import java.sql.*;
import java.util.Date;

import javax.swing.JTextArea;

import java.text.SimpleDateFormat;

public class sqltest {  //�������ݿ�

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/chatroom?useUnicode=true&characterEncoding=UTF-8";
    
    static final String USER = "root";
    static final String PASS = "1234";
    static JTextArea textArea=null;
    
	public static void insert(String buf1,String buf2) {
		// TODO Auto-generated method stub
		Connection conn = null;
        Statement stmt = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String curtime=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
        try{
            // ע�� JDBC ����
            Class.forName(JDBC_DRIVER);
        
            // ������
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            // ִ�в�ѯ
            stmt = conn.createStatement();
            String sql1;
            sql1 = "insert into record (name,buf,time) values (\'"+buf1+"\', \'"+buf2+"\',\'"+curtime+"\')";
            stmt.executeUpdate(sql1);
            String sql2;
            sql2 = "SELECT id,name, buf,time FROM record";
            ResultSet rs = stmt.executeQuery(sql2);
            textArea.setText("");
            
            // չ����������ݿ�
            while(rs.next()){
                // ͨ���ֶμ���
                String name  = rs.getString("name");
                String buf = rs.getString("buf");
                String time = rs.getString("time");
                
                textArea.append(time+"\n"+name+" : "+buf+"\n");
            }
            // ��ɺ�ر�
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
        }finally{
            // �ر���Դ
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// ʲô������
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
