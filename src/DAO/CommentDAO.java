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
	
	//close 시작
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
			System.out.println("회선 종료 중 오류 발생");
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
			System.out.println("회선 종료 중 오류 발생");
		}
	}
	
	
	//close 끝
	
	
	//댓글 삽입 시작
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
			
			if(cos==0)	//댓글을 처음 만들 땐 cos가 당연히 존재하지 않음으로 0으로 반환된다. 그래서 이 경우가 필요하다.
			{
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, comment);
				pstmt.setInt(3, comment_num);
			}
			else	//댓글이 한 번이라도 써졌을 경우 cos는 0이 아닌 양수임으로 이 경우에 해당된다. 이것으로 숫자를 하나씩 늘려준다.
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
			System.out.println("--------------CommentDAO CommentInsert() 오류 발생"+e);
		}
		finally
		{
			CommentDAO.close(conn, pstmt);
		}
		

		return result;
	}
	
	//댓글 삽입 끝
	
	
	
	//대댓글 삽입 시작
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
			System.out.println("--------------CommentDAO CommentInsert() 오류 발생"+e);
		}
		finally
		{
			CommentDAO.close(conn, pstmt);
		}
		

		return result;
	}
	
	
	
	//대댓글 삽입 끝
	


	//댓글 순서도 시작
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
			System.out.println("cos 갯수 확인 : "+ cos );
			
		}
		catch(Exception e)
		{
			System.out.println(pstmt.toString());
			System.out.println("-------------- 여기서 ? CommentDAO cos 오류 발생"+e);
			System.out.println("cos 갯수 확인 : "+ cos );
		}
		finally
		{
			CommentDAO.close(conn, pstmt, rs);
		}
		return cos;
	}
	
	//댓글 순서도 끝
	
	
	
	//대댓글 순서도 시작
	
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
			System.out.println("ccos 갯수 확인 : "+ ccos );
			
		}
		catch(Exception e)
		{
			System.out.println(pstmt.toString());
			System.out.println("--------------CommentDAO cos 오류 발생"+e);
		}
		finally
		{
			CommentDAO.close(conn, pstmt, rs);
		}
		return ccos;
	}
	//대댓글 순서도 끝
	
	
	
	
	public List<Comment> cs(int num)//댓글 보기 시작
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
				
				cmt.setCount(count++);	//이걸 넣어주는 이유는 답글 보고 감추기의 클래스를 for문으로 잡기 위함 
				list.add(cmt);
			}
			
			System.out.println(pstmt.toString());
		}
		catch(Exception e)
		{
			System.out.println("--------------CommentDAO cs() 오류 발생"+e);
			System.out.println(pstmt.toString());
		}
		finally
		{
			CommentDAO.close(conn, pstmt, rs);
		}
		
		return list;
				
	}//댓글 보기 끝
	
	
	//답글 쓰기 자바스트립드 hidden을 적용하기 위한 댓글 개수 세기
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
			System.out.println("357 번째 줄 대댓글 개수 세기에서 문제 발생");
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
			System.out.println("commentUpdate 에서 문제 발생");
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
			System.out.println("commentDelete 에서 문제 발생");
		}
		finally
		{
			CommentDAO.close(conn, pstmt);
		}
	}
	
	
	
}















