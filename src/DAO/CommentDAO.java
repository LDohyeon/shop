package DAO;

import java.sql.*;
import java.util.*;

import beans.Comment;

public class CommentDAO {

	private CommentDAO()
	{
		
	}
	
	public static CommentDAO instance = new CommentDAO();
	
	public static CommentDAO getinstance()
	{
		return instance;
	}
	
	public Connection getConnection() throws Exception
	{
		Connection conn=null;
		String url="jdbc:mysql://127.0.0.1:3306/shop";
		String db_id="root";
		String db_pw="iotiot";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn=DriverManager.getConnection(url, db_id, db_pw);
		
		return conn;
	}
	
	//close ����
	public static void close(Connection conn, Statement stmt, ResultSet rs)
	{
		try
		{
			if(rs !=null)
			{
				rs.close();
			}
			if(stmt !=null)
			{
				stmt.close();
			}
			if(conn !=null)
			{
				conn.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("ȸ�� ���� �� ���� �߻�");
		}
	}
	public static void close(Connection conn, Statement stmt)
	{
		try
		{
			if(stmt !=null)
			{
				stmt.close();
			}
			if(conn !=null)
			{
				conn.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("ȸ�� ���� �� ���� �߻�");
		}
	}
	
	
	//close ��
	
	
	//��� ���� ����
	public int insertComment(String comment, String id, int comment_num)
	{
		int result=0;
		int cos =cos(comment_num);
		

		String sql="insert into comment(id, comment, create_date, comment_num) values(?, ?, NOW(), ?)";
		String sql2="insert into comment(id, comment, create_date, comment_num, comment_order) values(?, ?, NOW(), ? ,?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		
		
		try
		{
			conn=getConnection();
			
			if(cos==0)	//����� ó�� ���� �� cos�� �翬�� �������� �������� 0���� ��ȯ�ȴ�. �׷��� �� ��찡 �ʿ��ϴ�.
			{
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, comment);
				pstmt.setInt(3, comment_num);
			}
			else	//����� �� ���̶� ������ ��� cos�� 0�� �ƴ� ��������� �� ��쿡 �ش�ȴ�. �̰����� ���ڸ� �ϳ��� �÷��ش�.
			{
				pstmt=conn.prepareStatement(sql2);
				pstmt.setString(1, id);
				pstmt.setString(2, comment);
				pstmt.setInt(3, comment_num);
				pstmt.setInt(4, cos+1);
			}
			
	
			System.out.println(pstmt.toString());
			
			result=pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("--------------CommentDAO CommentInsert() ���� �߻�"+e);
		}
		finally
		{
			CommentDAO.close(conn, pstmt);
		}
		

		return result;
	}
	
	//��� ���� ��
	
	
	
	//���� ���� ����
	public int insertCcomment(String comment, String id, int comment_num, int comment_order)
	{
		int result=0;
		int ccos =ccos(comment_num, comment_order);
		

		String sql="insert into comment(id, comment, create_date, comment_num, comment_order, ccomment_order) values(?, ?, NOW(), ? ,?, ?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
			
		try
		{
			conn=getConnection();
			

			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, comment);
			pstmt.setInt(3, comment_num);
			pstmt.setInt(4, comment_order);
			pstmt.setInt(5, ccos+1);
			
			
	
			System.out.println(pstmt.toString());
			
			result=pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("--------------CommentDAO CommentInsert() ���� �߻�"+e);
		}
		finally
		{
			CommentDAO.close(conn, pstmt);
		}
		

		return result;
	}
	
	
	
	//���� ���� ��
	


	//��� ������ ����
	public int cos(int comment_num)
	{
		int cos=0;
		String sql="select comment_order from comment where comment_num =? order by comment_order desc";
		

		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, comment_num);
			
			rs=pstmt.executeQuery();
			
			rs.next();
			cos=Integer.parseInt(rs.getString(1));
			
			
			
			System.out.println(pstmt.toString());
			System.out.println("cos ���� Ȯ�� : "+ cos );
			
		}
		catch(Exception e)
		{
			System.out.println(pstmt.toString());
			System.out.println("-------------- ���⼭ ? CommentDAO cos ���� �߻�"+e);
			System.out.println("cos ���� Ȯ�� : "+ cos );
		}
		finally
		{
			CommentDAO.close(conn, pstmt, rs);
		}
		return cos;
	}
	
	//��� ������ ��
	
	
	
	//���� ������ ����
	
	public int ccos(int comment_num, int comment_order)
	{
		int ccos=0;
		String sql="select ccomment_order from comment where comment_num =? and comment_order = ? order by ccomment_order desc";
		

		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, comment_num);
			pstmt.setInt(2, comment_order);
			
			rs=pstmt.executeQuery();
			
			rs.next();
			ccos=Integer.parseInt(rs.getString(1));
			
			
			
			
			System.out.println(pstmt.toString());
			System.out.println("ccos ���� Ȯ�� : "+ ccos );
			
		}
		catch(Exception e)
		{
			System.out.println(pstmt.toString());
			System.out.println("--------------CommentDAO cos ���� �߻�"+e);
		}
		finally
		{
			CommentDAO.close(conn, pstmt, rs);
		}
		return ccos;
	}
	//���� ������ ��
	
	
	
	
	public List<Comment> cs(int num)//��� ���� ����
	{
		List<Comment> list=new ArrayList<Comment>();
		String sql="select * from comment where comment_num= ? order by comment_order";
		

		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int count=0;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				Comment cmt=new Comment();
				cmt.setId(rs.getString("id"));
				cmt.setComment((rs.getString("comment")));
				cmt.setCreate_date((rs.getString("create_date")));
				cmt.setComment_order(rs.getInt("comment_order"));
				cmt.setCcomment_order(rs.getInt("ccomment_order"));
				
				cmt.setPk(rs.getInt("pk"));
				
				cmt.setCount(count++);	//�̰� �־��ִ� ������ ��� ���� ���߱��� Ŭ������ for������ ��� ���� 
				list.add(cmt);
			}
			
			System.out.println(pstmt.toString());
		}
		catch(Exception e)
		{
			System.out.println("--------------CommentDAO cs() ���� �߻�"+e);
			System.out.println(pstmt.toString());
		}
		finally
		{
			CommentDAO.close(conn, pstmt, rs);
		}
		
		return list;
				
	}//��� ���� ��
	
	
	//��� ���� �ڹٽ�Ʈ���� hidden�� �����ϱ� ���� ��� ���� ����
	public int count_comment(int num)
	{
		int count=0;
		String sql="select count(*) from comment where comment_num=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			System.out.println(pstmt);
			rs=pstmt.executeQuery();
			rs.next();
			
			count=Integer.parseInt(rs.getString(1));
			
			System.out.println(count);
			
			
		}
		catch(Exception e)
		{
			System.out.println("357 ��° �� ���� ���� ���⿡�� ���� �߻�");
		}
		finally
		{
			CommentDAO.close(conn, pstmt, rs);
		}
				
		return count;
	}
	
	
	public void commentUpdate(int pk, String comment)
	{
		String sql="update comment set comment= ? where pk=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, comment);
			pstmt.setInt(2, pk);
			
			System.out.println(pstmt);
			pstmt.executeUpdate();
	
		}
		catch(Exception e)
		{
			System.out.println("commentUpdate ���� ���� �߻�");
		}
		finally
		{
			CommentDAO.close(conn, pstmt);
		}
	}

	
	public void commentDelete(int pk)
	{
		String sql="delete from comment where pk=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, pk);
			
			System.out.println(pstmt);
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("commentDelete ���� ���� �߻�");
		}
		finally
		{
			CommentDAO.close(conn, pstmt);
		}
	}
	
	
	
}















