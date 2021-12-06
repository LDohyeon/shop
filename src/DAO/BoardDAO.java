package DAO;

import java.sql.*;



import java.util.*;

import beans.Board;

public class BoardDAO {

	
	private BoardDAO()
	{
		
	}
	
	private static BoardDAO instance =new BoardDAO();
	
	public static BoardDAO getInstance()
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
			System.out.println("BoardDAO conn 연결 중 오류 발생"+e);
		}

		return conn;
	}
	
	
	
	public int bdInsert(Board bd)//게시판 글 삽입 시작
	{
		int result=-1;
		Connection conn=null;
		Statement stmt=null;
		
		String sql="insert into board(num, id, title, content, date) values("+bdNum()+", '"+bd.getId()+"', '"+bd.getTitle()+"', '"+bd.getContent()+"', NOW());";
		
		System.out.println(sql);
		
		if(bd.getTitle()==null) {
			System.out.println("=========================null확인확인확인==============================");
		}
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			result=stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("-------------------BoardDAO의 dbInsert() 실패" +e);
		}
		finally
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
				System.out.println("BoardDAO dbInsert() 회선 오류 발생"+e);
			}
		}
		
		return result;
	}//게시판 글 삽입 끝
	
	
	public int bdNum()//bdNum() 시작
	//번호를 일 증가하기 위한 함수 그냥 auto 쓸 걸 그랬기도 하지만 auto를 쓰면 중간에 빈 숫자는 그냥 건너뛰니 그걸 막기 위해
	//이것도 중간 번호를 삭제하면 그렇게 될 테지만 번호가 덜 빌 것이다.
	{
		int num = 0;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="select max(num) from board;";
		System.out.println("00000     "+num);
		System.out.println(sql);
		
		
		try
		{
			conn=getConnection();			
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);

			if(rs.next())
			{

				num= rs.getInt("max(num)");
				num=num+1;
				
				System.out.println("111111   "+num);
				return num;
			}
			else
			{
				return 1;
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("-----BoardDAO의 dbNum() 실패" +e);
		}
		finally
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
				System.out.println("BoardDAO dbInsert() 회선 오류 발생"+e);
			}
		}
		
		
		return num;//오류
	}//bdNum() 끝
	

	//Board 게시판 select 처리 및 페이지 분할
	public List<Board> pageList(int startPage, int listPage)
	{
		List<Board> list = new ArrayList<Board>();
		
		int start=startPage*listPage-listPage;
		
		
		String sql="select num, title, id, date, hits from board order by num desc limit ?, ?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, listPage);
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				Board bod=new Board();
				bod.setNum(rs.getString("num"));
				bod.setTitle(rs.getString("title"));
				bod.setId(rs.getString("id"));
				bod.setDate(rs.getString("date"));
				bod.setHits(rs.getString("hits"));
				
				list.add(bod);
				
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println("접속 중 오류 발생 : "+e);
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("접속 종류 중 오류 발생 : "+e);
			}
		}
		
		
		return list;
	}//Board 게시판 select 처리 및 페이지 분할 끝
	
	//Board 게시판 select 처리 및 페이지 분할 버튼 시작!
	
	public int pagebtn()
	{
		String sql="select count(num) from board";
		
		int pagebtn=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			rs.next();
			
			pagebtn=Integer.parseInt(rs.getString(1));
			
			
		}
		catch(Exception e)
		{
			System.out.println("페이지 분할 버튼 실패"+e);
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("접속 종류 중 오류 발생 : "+e);
			}
		}

		return pagebtn;
	}//Board 게시판 select 처리 및 페이지 분할 버튼 끝!
	
	
	
	
	//Board 게시판 title 클릭 시 보이는 content 내용 보기 시작!
	public List<Board> Content(int num)
	{
		List<Board> list= new ArrayList<Board>();
		String sql="select id, title, content, date, hits from board where num= ?";
		
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);

			rs=pstmt.executeQuery();
			
			if(rs.next())
			{
				Board bod=new Board();
				bod.setId(rs.getString("id"));
				bod.setTitle(rs.getString("title"));
				bod.setContent(rs.getString("content"));
				bod.setDate(rs.getString("date"));
				bod.setHits(rs.getString("hits"));

				list.add(bod);
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("접속 중 오류 발생 : "+e);
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("접속 종류 중 오류 발생 : "+e);
			}
		}
		
		
		return list;
	}//Board 게시판 title 클릭 시 보이는 content 내용 보기 끝!

	
	
	//Board 게시판 title 클릭 시 보이는 content 내용 보기의 조회수 처리 시작
	public int hits(int num)
	{
		String sql="update board set hits = hits+1 where num= ?";
		
		int result = 0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			result=pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("접속 중 오류 발생 : "+e);
		}
		finally
		{
			try
			{
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("접속 종류 중 오류 발생 : "+e);
			}
		}
		
		return result;
		
	}//Board 게시판 title 클릭 시 보이는 content 내용 보기의 조회수 처리 끝
	
	
	//Board 게시판 수정하기 시작
	
	public void boardUpdate(String title, String content, String num)
	{
		String sql="update board set title =?, content =?, date=NOW() where num= ?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, num);

			System.out.println(pstmt);
			pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("게시판 수정 중 오류 발생 : "+e);
		}
		finally
		{
			try
			{
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("접속 종류 중 오류 발생 : "+e);
			}
		}
	}
	//Board 게시판 수정하기 끝
	
	//board 게시판 삭제 시작!
	
	public void bdDelete(String num)
	{
		String sql="delete from board where num=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, num);
			
			System.out.println(pstmt);
			pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("게시판 삭제 중 오류 발생 : "+e);
		}
		finally
		{
			try
			{
				if(pstmt!=null)
				{
					pstmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("접속 종류 중 오류 발생 : "+e);
			}
		}
		
	}
	//board 게시판 삭제 끝!
	
	
}












