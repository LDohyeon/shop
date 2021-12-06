package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class MainPictureurlDAO {

		private MainPictureurlDAO()
		{
			
		}
		
		private static MainPictureurlDAO instance=new MainPictureurlDAO();
		
		public static MainPictureurlDAO getInstance()
		{
			return instance;
		}
		
		public Connection getConnection()
		{
			Connection conn=null;
			String url="jdbc:mysql://127.0.0.1:3306/shop";
			String db_id="root";
			String db_pw="iotiot";
			
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn=DriverManager.getConnection(url, db_id, db_pw);		
			}
			catch(Exception e)
			{
				System.out.println("product conn 연결 중 오류 발생 :"+ e);
			}
			return conn;
		}
		
		public static void close(Connection conn, Statement stmt, ResultSet rs)
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("회선 종료 중 문제 발생 : "+ e);
			}
		}
		public static void close(Connection conn, Statement stmt)
		{
			try
			{
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("회선 종료 중 문제 발생 : "+ e);
			}
		}
		
		public void imageInsert(String pictureurl)
		{
			String sql="insert into main_priture(pictureurl) values(?)";
								
			Connection conn=null;
			PreparedStatement pstmt=null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, pictureurl);

				pstmt.execute();
			}
			catch(Exception e)
			{
				System.out.println("StartMainPicture Servlet 오류 발생"+ e);
			}
			finally
			{
				close(conn, pstmt);
			}
			

		}
		
		public String[] imageSelect()
		{
			String sql="select * from main_priture where notice=0 limit 3";
		
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String[] image = new String[3];
			int i=0;
				
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
					
				
				rs=pstmt.executeQuery();
				
				while(rs.next())
				{
					image[i++] = rs.getString("pictureurl");
				}

			}
			catch(Exception e)
			{
				System.out.println("StartMainPicture Servlet 오류 발생 : "+ e);
			}
			finally
			{
				CartDAO.close(conn, pstmt, rs);
			}
			
			
			return image;
			
		}
		
		
		
		public void imageDelete()
		{
			String sql="delete from main_priture where notice=0";
		
			Connection conn=null;
			PreparedStatement pstmt=null;

			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.executeUpdate();

			}
			catch(Exception e)
			{
				System.out.println("StartMainPicture Servlet 오류 발생 : "+ e);
			}
			finally
			{
				CartDAO.close(conn, pstmt);
			}

		}
		
		
		//공지사항 입력
		public void imageDelete(int numNotice)
		{
			String sql="delete from main_priture where notice=?";
		
			Connection conn=null;
			PreparedStatement pstmt=null;

			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, numNotice);
				pstmt.executeUpdate();

			}
			catch(Exception e)
			{
				System.out.println("StartMainPicture Servlet 오류 발생 : "+ e);
			}
			finally
			{
				CartDAO.close(conn, pstmt);
			}

		}
		
	
		public void noticeInsert(String fileNotice, int numNotice)
		{
			String sql="insert into main_priture(pictureurl , notice) values(?, ?)";
								
			Connection conn=null;
			PreparedStatement pstmt=null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, fileNotice);
				pstmt.setInt(2, numNotice);

				pstmt.execute();
			}
			catch(Exception e)
			{
				System.out.println("StartMainPicture Servlet 오류 발생"+ e);
			}
			finally
			{
				close(conn, pstmt);
			}
			

		}
		
		public String noticeSelect()
		{
			String sql="select * from main_priture where notice=1";
		
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String notice=null;
				
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
					
				
				rs=pstmt.executeQuery();
				
				rs.next();
				
				notice= rs.getString("pictureurl");
				

			}
			catch(Exception e)
			{
				System.out.println("공지사항 이미지 안 넣어 삭제 처리 오류 아님 : "+ e);
			}
			finally
			{
				CartDAO.close(conn, pstmt, rs);
			}
			
			
			return notice;
			
		}
		
		
		
		//공지사항 입력 끝
}
















