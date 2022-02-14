package com.jdbc.project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//드라이버 로딩과 DB연결을 하는 클래스
public class DBUtil {
   static {
      //1. 드라이버 로딩
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (ClassNotFoundException e) {
         System.out.println("드라이버 로딩 실패!");
         e.printStackTrace();
      }      
   }//static 초기화 블럭
   
   public static Connection getConnection(String url ,String user, String pwd) 
         throws SQLException {
      Connection con=DriverManager.getConnection(url, user, pwd);
      return con;
   }
   
   public static Connection getConnection() throws SQLException {
      //2. 데이터베이스과 연결하기 위한 Connection객체 생성
      String url="jdbc:oracle:thin:@118.37.93.154:1521:xe";
      String user="javauser", pwd="javauser123";
      Connection con = getConnection(url, user, pwd);
      
      return con;
   }
      
   public static Connection getConnection(String user, String pwd) 
         throws SQLException {
      String url="jdbc:oracle:thin:@118.37.93.154:1521:xe";
      
      return getConnection(url, user, pwd);      
   }
   
   public static void dbClose(ResultSet rs, PreparedStatement ps,
         Connection con) throws SQLException {
      if(rs!=null) rs.close();
      if(ps!=null) ps.close();
      if(con!=null) con.close();
   }
   
   public static void dbClose(PreparedStatement ps,
         Connection con) throws SQLException {
      if(ps!=null) ps.close();
      if(con!=null) con.close();
   }
   
}