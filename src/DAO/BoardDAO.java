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
			System.out.println("BoardDAO conn ���� �� ���� �߻�"+e);
		}

		return conn;
	}
	
	
	
	public int bdInsert(Board bd)//�Խ��� �� ���� ����
	{
		int result=-1;
		Connection conn=null;
		Statement stmt=null;
		
		String sql="insert into board(num, id, title, content, date) values("+bdNum()+", '"+bd.getId()+"', '"+bd.getTitle()+"', '"+bd.getContent()+"', NOW());";
		
		System.out.println(sql);
		
		if(bd.getTitle()==null) {
			System.out.println("=========================nullȮ��Ȯ��Ȯ��==============================");
		}
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			result=stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			System.out.println("-------------------BoardDAO�� dbInsert() ����" +e);
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
				System.out.println("BoardDAO dbInsert() ȸ�� ���� �߻�"+e);
			}
		}
		
		return result;
	}//�Խ��� �� ���� ��
	
	
	public int bdNum()//bdNum() ����
	//��ȣ�� �� �����ϱ� ���� �Լ� �׳� auto �� �� �׷��⵵ ������ auto�� ���� �߰��� �� ���ڴ� �׳� �ǳʶٴ� �װ� ���� ����
	//�̰͵� �߰� ��ȣ�� �����ϸ� �׷��� �� ������ ��ȣ�� �� �� ���̴�.
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
			System.out.println("-----BoardDAO�� dbNum() ����" +e);
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
				System.out.println("BoardDAO dbInsert() ȸ�� ���� �߻�"+e);
			}
		}
		
		
		return num;//����
	}//bdNum() ��
	

	//Board �Խ��� select ó�� �� ������ ����
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
			System.out.println("���� �� ���� �߻� : "+e);
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
				System.out.println("���� ���� �� ���� �߻� : "+e);
			}
		}
		
		
		return list;
	}//Board �Խ��� select ó�� �� ������ ���� ��
	
	//Board �Խ��� select ó�� �� ������ ���� ��ư ����!
	
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
			System.out.println("������ ���� ��ư ����"+e);
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
				System.out.println("���� ���� �� ���� �߻� : "+e);
			}
		}

		return pagebtn;
	}//Board �Խ��� select ó�� �� ������ ���� ��ư ��!
	
	
	
	
	//Board �Խ��� title Ŭ�� �� ���̴� content ���� ���� ����!
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
			System.out.println("���� �� ���� �߻� : "+e);
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
				System.out.println("���� ���� �� ���� �߻� : "+e);
			}
		}
		
		
		return list;
	}//Board �Խ��� title Ŭ�� �� ���̴� content ���� ���� ��!

	
	
	//Board �Խ��� title Ŭ�� �� ���̴� content ���� ������ ��ȸ�� ó�� ����
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
			System.out.println("���� �� ���� �߻� : "+e);
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
				System.out.println("���� ���� �� ���� �߻� : "+e);
			}
		}
		
		return result;
		
	}//Board �Խ��� title Ŭ�� �� ���̴� content ���� ������ ��ȸ�� ó�� ��
	
	
	//Board �Խ��� �����ϱ� ����
	
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
			System.out.println("�Խ��� ���� �� ���� �߻� : "+e);
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
				System.out.println("���� ���� �� ���� �߻� : "+e);
			}
		}
	}
	//Board �Խ��� �����ϱ� ��
	
	//board �Խ��� ���� ����!
	
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
			System.out.println("�Խ��� ���� �� ���� �߻� : "+e);
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
				System.out.println("���� ���� �� ���� �߻� : "+e);
			}
		}
		
	}
	//board �Խ��� ���� ��!
	
	
}












