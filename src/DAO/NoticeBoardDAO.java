package DAO;

import java.sql.*;
import java.util.*;

import beans.NoticeBoardDTO;

public class NoticeBoardDAO {
	
	NoticeBoardDAO()
	{
		
	}
	
	private static NoticeBoardDAO instance =new NoticeBoardDAO();
	
	public static NoticeBoardDAO getInstance()
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
			System.out.println("NoticeBoardDAO getConnection 오류");
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
	
	
	public void noticeBoardInsert(NoticeBoardDTO nDTO)
	{
		String sql="insert into noticeBoard(id, title, content, date, pictureurl, options) values(?,?,?, NOW(), ?, ?)";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, nDTO.getId());
			pstmt.setString(2, nDTO.getTitle());
			pstmt.setString(3, nDTO.getContent());
			pstmt.setString(4, nDTO.getPictureurl());
			pstmt.setString(5, nDTO.getOptions());
			
			System.out.println("pstmt : "+pstmt);
			
			pstmt.execute();
			
		}
		catch(Exception e)
		{
			System.out.println("noticeBoardInsert 중 문제 발생 : "+ e);
		}
		finally
		{
			NoticeBoardDAO.close(conn, pstmt);
		}
	}
	
	
	
	//필독 사항만 출력
	public List<NoticeBoardDTO> noticeList()
	{
		List<NoticeBoardDTO> list =new ArrayList<NoticeBoardDTO>();
		
		
		String sql="select * from noticeBoard where options=0 order by num desc";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				NoticeBoardDTO nDTO=new NoticeBoardDTO();
				nDTO.setNum(rs.getInt("num"));
				nDTO.setId(rs.getString("id"));
				nDTO.setTitle(rs.getString("title"));
				nDTO.setContent(rs.getString("content"));
				nDTO.setDate(rs.getString("date"));
				nDTO.setHits(rs.getInt("hits"));
				nDTO.setPictureurl(rs.getString("pictureurl"));
				nDTO.setOptions(rs.getString("options"));
				
				list.add(nDTO);
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println("noticeList 중 문제 발생 : "+ e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
			
		return list;
	}
	
	
	//noticeList 필독 끝
	
	public List<NoticeBoardDTO> noticeList(int startPage, int lastPage)
	{
		List<NoticeBoardDTO> list =new ArrayList<NoticeBoardDTO>();
		
		int start=startPage*lastPage-lastPage;
		
		String sql="select * from noticeBoard where not options=0 order by num desc limit ?, ?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, lastPage);
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				NoticeBoardDTO nDTO=new NoticeBoardDTO();
				nDTO.setNum(rs.getInt("num"));
				nDTO.setId(rs.getString("id"));
				nDTO.setTitle(rs.getString("title"));
				nDTO.setContent(rs.getString("content"));
				nDTO.setDate(rs.getString("date"));
				nDTO.setHits(rs.getInt("hits"));
				nDTO.setPictureurl(rs.getString("pictureurl"));
				nDTO.setOptions(rs.getString("options"));
				
				list.add(nDTO);
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println("noticeList 중 문제 발생 : "+ e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
			
		return list;
	}
	
	
	//noticeList 옵션 선택
	public List<NoticeBoardDTO> noticeList(int startPage, int lastPage, int op)
	{
		List<NoticeBoardDTO> list =new ArrayList<NoticeBoardDTO>();
		
		int start=startPage*lastPage-lastPage;
		
		String sql="select * from noticeBoard where options=? order by num desc limit ?, ?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, op);
			pstmt.setInt(2, start);
			pstmt.setInt(3, lastPage);
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				NoticeBoardDTO nDTO=new NoticeBoardDTO();
				nDTO.setNum(rs.getInt("num"));
				nDTO.setId(rs.getString("id"));
				nDTO.setTitle(rs.getString("title"));
				nDTO.setContent(rs.getString("content"));
				nDTO.setDate(rs.getString("date"));
				nDTO.setHits(rs.getInt("hits"));
				nDTO.setPictureurl(rs.getString("pictureurl"));
				nDTO.setOptions(rs.getString("options"));
				
				list.add(nDTO);
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println("noticeList 중 문제 발생 : "+ e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
			
		return list;
	}

	//noticeList 옵션 선택 끝
	
	
	
	
	//page분할 버튼 시작
	
	public int pageBtn()
	{
		String sql="select count(num) from noticeBoard";
		
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
			close(conn, pstmt, rs);
		}
		
		
		return pagebtn;
	}
	public int pageBtn(int op)
	{
		String sql="select count(num) from noticeBoard where options=?";
		
		int pagebtn=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, op);
			
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
			close(conn, pstmt, rs);
		}
		
		
		return pagebtn;
	}
	
	
	//page분할 버튼 끝
	
	
	
	
	
	
	
	//공지사항 옵션들 개수 새기 시작
	
	public int opctionCount()
	{
		String sql="select count(num) from noticeBoard";
		
		int Count=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			rs.next();
			
			Count=Integer.parseInt(rs.getString(1));

		}
		catch(Exception e)
		{
			System.out.println("페이지 분할 버튼 실패"+e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
		
		
		return Count;
	}
	public int opctionCount0()
	{
		String sql="select count(num) from noticeBoard where options=0";
		
		int Count=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			rs.next();
			
			Count=Integer.parseInt(rs.getString(1));

		}
		catch(Exception e)
		{
			System.out.println("페이지 분할 버튼 실패"+e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
		
		
		return Count;
	}
	
	public int opctionCount1()
	{
		String sql="select count(num) from noticeBoard where options=1";
		
		int Count=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			rs.next();
			
			Count=Integer.parseInt(rs.getString(1));

		}
		catch(Exception e)
		{
			System.out.println("페이지 분할 버튼 실패"+e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
		
		
		return Count;
	}
	
	public int opctionCount2()
	{
		String sql="select count(num) from noticeBoard where options=2";
		
		int Count=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			rs.next();
			
			Count=Integer.parseInt(rs.getString(1));

		}
		catch(Exception e)
		{
			System.out.println("페이지 분할 버튼 실패"+e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
		
		
		return Count;
	}
	
	//공지사항 옵션들 개수 새기 끝
	
	
	//공지사항 세부 내용 보기
	
	public NoticeBoardDTO noticeEachSelect(int num)
	{
		NoticeBoardDTO nDTO= new NoticeBoardDTO();
		
		String sql="select * from noticeBoard where num =?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		noticeHitsUp(num);
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			rs.next();
			nDTO.setNum(rs.getInt("num"));
			nDTO.setId(rs.getString("id"));
			nDTO.setTitle(rs.getString("title"));
			nDTO.setContent(rs.getString("content"));
			nDTO.setDate(rs.getString("date"));
			nDTO.setHits(rs.getInt("hits"));
			nDTO.setPictureurl(rs.getString("pictureurl"));
			nDTO.setOptions(rs.getString("options"));
			

		}
		catch(Exception e)
		{
			System.out.println("noticeEachSelect 실패"+e);
		}
		finally
		{
			close(conn, pstmt, rs);
		}
		
		
		
		return nDTO;
	}
	
	//공지 사항 세부 내용 보기 끝
	
	//공지사항 조회수 올리기 바로 위 noticeEachSelect와 연계 시작

	
	public void noticeHitsUp(int num)
	{
		String sql="update noticeBoard set hits=hits+1 where num=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);		
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("noticeHitUp 실패"+e);
		}
		finally
		{
			close(conn, pstmt);
		}
		
		
	}
	//공지사항 조회수 올리기 바로 위 noticeEachSelect와 연계 끝
	
	
	
	//공지 사항 수정
	
	public void noticeUpdate(NoticeBoardDTO nDTO)
	{
		String sql="update noticeBoard set title=?, content=?, options=?, pictureurl=? where num=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, nDTO.getTitle());
			pstmt.setString(2, nDTO.getContent());
			pstmt.setString(3, nDTO.getOptions());
			pstmt.setString(4, nDTO.getPictureurl());
			pstmt.setInt(5, nDTO.getNum());
			
			pstmt.executeUpdate();
			
			
		}
		catch(Exception e)
		{
			System.out.println("noticeUpdate 실패"+e);
		}
		finally
		{
			close(conn, pstmt);
		}

	}
	//공지사항 수정 끝
	
	public void noticeDelete(int num)
	{
		String sql="delete from noticeBoard where num=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("noticeDelete 실패"+e);
		}
		finally
		{
			close(conn, pstmt);
		}
		
		
	}
	
	
	
}












