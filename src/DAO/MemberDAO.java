package DAO;

import java.sql.*;
import java.util.*;

import beans.Member;

public class MemberDAO {

	private MemberDAO()
	{
		
	}
	
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance()
	{
		return instance;
	}
	
	
	//Ŀ�ؼ� ���� ó��
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

	//�α��� ó��
	public int userCheck(String id, String pw)
	{
		int result =-1;
		String sql="select pw, admin from member where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs=pstmt.executeQuery();
			
			if(rs.next())
			{
				if(rs.getString("pw").equals(pw))
				{
					//�α��ν� �Է��� ��й�ȣ�� �����ͺ��̽� ���� ��й�ȣ�� null�� �ƴ� ��
					//null�� �ڹٽ�Ʈ��Ʈ�� ó���ߴ�.
					result=1;
					
					if(rs.getString("admin").equals("0"))
					{
						result=3;
					}
					if(rs.getString("admin").equals("1"))
					{
						result=2;
					}
				
				}
				else
				{
					result=0;
					//�Էµ� ���̵� �����ͺ��̽� �� �����ϴµ� �����ȣ�� ��ġ���� �ʰų� null�� ���
				}
			}
			else
			{
				result =-1;
			}
			
		}
		catch(Exception e)
		{
			System.out.println("747474747474747474747474747474MemberDAO.java���� �α��� ó���� ���� �߻�"+e);
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
				System.out.println("--------------------------------MemberDAO.java���� �α��� ó���� �ܼ� ó�� �� ���� �߻�"+e);
			}
		}
		
		return result;
		
	}//�α��� ó�� ��
	
	
	public Member getMember(String id)
	{
		Member m=null;
		String sql="select * from member where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next())
			{
				m=new Member();
				m.setAdmin(rs.getString("admin"));
				m.setEmail(rs.getNString("email"));
				m.setName(rs.getString("name"));
				m.setNum(rs.getString("num"));
				m.setPhone(rs.getString("phone"));
				m.setPw(rs.getString("pw"));
				m.setId(rs.getString("id"));
				m.setPoint(rs.getString("point"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setCreate_date(rs.getString("create_date"));
				m.setAges(rs.getInt("ages"));
						
			}
		}
		catch(Exception e)
		{
			System.out.println("135135135135135135135MemberDAO 135��° �� ȸ������ ��ȸ �� ���� �߻�" +e);
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
				System.out.println("ȸ������ ��ȸ �� ���� �߻� : "+e);
			}
			
		}
		
		return m;
		
	}///���� ȸ�� ������ ���� ��ȸ public Member getMember ��
	
	
	public int MemberInsert(Member m)//ȸ������ ����
	{
		int mResult=-1;
		String sql="insert into Member(id, pw, name, phone, email, gender, age, create_date, ages) values(?,?,?,?,?,?,?, NOW(), ?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPw());
			pstmt.setString(3, m.getName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getGender());
			
			pstmt.setInt(7, m.getAge());
			pstmt.setInt(8, m.getAges());
			
			
			System.out.println(pstmt);
			mResult=pstmt.executeUpdate();
			

			
		}
		catch(Exception e)
		{
			System.out.println("184184184184184184184184184184----MemberDAO�� MemberInsert���� ���� �߻�"+e);
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
				System.out.println("198198198198----MemberDAO�� MemberInsert ȸ������ ���� �߻�");
			}
			
		}

	
		return mResult;
	}//ȸ�� ���� ��
	
	
	
	//���̵� �ߺ�üũ ����
	public int idCheck(String id)
	{
		int result=-1;
		String sql="select id from member where id='"+id+"';";
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		System.out.println(sql);
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{	
				result=-1;
			}
			else
			{
				result=1;
			}
			System.out.println("240240240240240-----MemberDAO�� idCheck() �����");
			System.out.println(result);
			
		}
		catch(Exception e)
		{
			System.out.println("240240240240240-----MemberDAO�� idCheck()���� �����߻�"+e);
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
				System.out.println("���̵� �ߺ� ��ȸ �� ���� �߻� : "+e);
			}
			
		}
		
		return result;
	}//���̵� �ߺ�üũ ��
	
	
	//ȸ�� ������ ���� �ο��� ���� �˻� ����
		public int adMemUpdate(String id)
		{
			int result=-1;
			String sql="select id, admin from member where id='"+id+"';";
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			
			System.out.println(sql);
			try
			{
				conn=getConnection();
				stmt=conn.createStatement();
				rs=stmt.executeQuery(sql);
				if(rs.next())
				{
					System.out.println(result);
					result=1;
				}
				else
				{
					System.out.println(result);
					result=-1;
				}
				
				System.out.println(result);
				
			}
			catch(Exception e)
			{
				System.out.println("307307307307307307307-----MemberDAO��  adMemUpdate���� �����߻�"+e);
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
					System.out.println("���̵� �ߺ� ��ȸ �� ���� �߻� : "+e);
				}
				
			}
			
			return result;
		}//ȸ�� ������ ���� �ο��� ���� �˻� ��
		
		
		public int MemberUpdate(Member m)//ȸ������ ����
		{
			int result =-1;
			String sql="update member set pw='"+m.getPw()+"', name='"+m.getName()+"', phone='"+m.getPhone()+"', email='"+m.getEmail()+"', admin='"+m.getAdmin()+"' where id='"+m.getId()+"';";
			Connection conn=null;
			Statement stmt=null;
			
			try
			{
				conn=getConnection();
				stmt=conn.createStatement();
	
				result=stmt.executeUpdate(sql);
				
				System.out.println(sql);
				
				
			}
			catch(Exception e)
			{
				System.out.println(sql);
				System.out.println("362362362362362362362362 MemberDAO�� MemberUpdate()���� ���� �߻�"+e);
			}
			finally
			{
				try
				{
					if(stmt!= null)
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
					System.out.println("362362362362362362362362 MemberDAO�� MemberUpdate() ȸ�� ���� �� ���� �߻�"+e);
				}
			}

			
			return result;
		}//ȸ������ ���� ��
		
		
		//ȸ������ ����
		public int MemberDelete(String num)
		{
			int result=-1;
			String sql="delete from member where num=?";
			Connection conn=null;
			PreparedStatement pstmt=null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, num);
				result = pstmt.executeUpdate();
				
				System.out.println(pstmt.toString());
				
				
			}
			catch(Exception e)
			{
				
				System.out.println("406406406406406406406406406406406 MemberDAO�� MemberDelete() ���� �߻�"+e);
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
					System.out.println("406406406406406406406406406406406 MemberDAO�� MemberDelete() ȸ�� ���� �߻�"+e);
				}
			}
			
			return result;
		}//ȸ������ ���� ��
	

		
		//ȸ�� ���� ����Ʈ ����
		
		public List<Member> memberList()
		{
			List<Member> list=new ArrayList<Member>();
			String sql="select * from member";
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs =null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					Member m=new Member();
				
					m.setNum(rs.getString("num"));
					m.setId(rs.getString("id"));
					m.setPw(rs.getString("pw"));
					m.setName(rs.getString("name"));
					m.setPhone(rs.getString("phone"));
					m.setEmail(rs.getString("email"));
					m.setAdmin(rs.getString("admin"));
					m.setPoint(rs.getString("point"));
					m.setGender(rs.getString("gender"));
					m.setAge(rs.getInt("age"));
					m.setCreate_date(rs.getString("create_date"));
					m.setAges(rs.getInt("ages"));
					m.setPriceHap(rs.getInt("priceHap"));
					
					
					list.add(m);
				}
				
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� MemberList() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� MemberList() ȸ�� ���� �߻�"+e);
				}
			}
			
			
			
			return list;
		}
		
		//ȸ�� ���� ����Ʈ ��
		
		
		//ȸ�� ���� ����Ʈ �˻�â���� �˻� ����
		
		public List<Member> memberList(String search)
		{
			List<Member> list=new ArrayList<Member>();
			String sql="select * from member where id like '"+search+"%';";
			
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs =null;
			
			try
			{
				conn=getConnection();
				stmt=conn.createStatement();
				
				rs=stmt.executeQuery(sql);

				while(rs.next())
				{
					Member m=new Member();
				
					m.setNum(rs.getString("num"));
					m.setId(rs.getString("id"));
					m.setPw(rs.getString("pw"));
					m.setName(rs.getString("name"));
					m.setPhone(rs.getString("phone"));
					m.setEmail(rs.getString("email"));
					m.setAdmin(rs.getString("admin"));
					m.setPoint(rs.getString("point"));
					m.setGender(rs.getString("gender"));
					m.setAge(rs.getInt("age"));
					m.setCreate_date(rs.getString("create_date"));
					m.setAges(rs.getInt("ages"));
					
					
					list.add(m);
				}
				
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� MemberList() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� MemberList() ȸ�� ���� �߻�"+e);
				}
			}
			
			
			
			return list;
		}
		
		//ȸ�� ���� ����Ʈ �˻�â���� �˻� ��
		
		
		
		//���� �� ȸ�� ����Ʈ �����ϱ� ����
		
		public void memberPoint(int point, String id)
		{
			String sql="update member set point =point+? where id=?";
			
			Connection conn=null;
			PreparedStatement pstmt= null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, point);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate();
				
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� memberPoint() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� memberPoint() ȸ�� ���� �߻�"+e);
				}
			}
		}
		
		//���� �� ȸ�� ����Ʈ �����ϱ� ��
		
		
		
		//ȸ�� ����Ʈ ���� ����
		
		public int selectPointMember(String id)
		{
			int point=0;
			String sql="select point from member where id=?";
			
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
				
				rs=pstmt.executeQuery();
				
				rs.next();
				point=rs.getInt(1);
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� selectPointMember() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� selectPointMember() ȸ�� ���� �߻�"+e);
				}
			}
			
			return point;
		}
		
		//ȸ�� ����Ʈ ���� ��
		
		
		
		
		//���� �� ȸ�� ���� �ݾ� �����ϱ� ����
		
		public void memberPriceHap(int priceHap, String id)
		{
			String sql="update member set priceHap =priceHap+? where id=?";
			
			Connection conn=null;
			PreparedStatement pstmt= null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, priceHap);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate();
				
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� memberPrice() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� memberPriceHap() ȸ�� ���� �߻�"+e);
				}
			}
		}
		
		//���� �� ȸ�� ���� �ݾ� �����ϱ� ��
		
		
		
		//ȸ�� ����Ʈ ���� ����
		
		public int selectPriceHapMember(String id)
		{
			int priceHap=0;
			String sql="select priceHap from member where id=?";
			
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
				
				rs=pstmt.executeQuery();
				
				rs.next();
				priceHap=rs.getInt(1);
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� selectPointMember() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� selectPointMember() ȸ�� ���� �߻�"+e);
				}
			}
			
			return priceHap;
		}
		
		//ȸ�� ����Ʈ ���� ��
		

		
		
		
		
		//ȯ�� �� ����Ʈ ȯ���ϱ� ����
		
		public void memberRefundPoint(int point, String id)
		{
			String sql="update member set point =? where id=?";
			
			Connection conn=null;
			PreparedStatement pstmt= null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, point);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate();
				
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� memberPoint() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� memberPoint() ȸ�� ���� �߻�"+e);
				}
			}
		}
		
		//ȯ�� �� ����Ʈ ȯ���ϱ� ��
		
		
		//ȯ�� �� ȸ�� ���� �ݾ� ���� ����
		
		public void memberRefundPriceHap(int priceHap, String id)
		{
			String sql="update member set priceHap =? where id=?";
			
			Connection conn=null;
			PreparedStatement pstmt= null;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, priceHap);
				pstmt.setString(2, id);
				
				pstmt.executeUpdate();
				
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� memberPrice() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� memberPriceHap() ȸ�� ���� �߻�"+e);
				}
			}
		}
		
		//ȯ�� �� ȸ�� ���� �ݾ� ���� ��
		
		
		//ȸ�� ���� ���� �˻� ����
		
		public int createMember(String year, int i, int j)
		{
			String sql="select count(num) from member where create_date like '"+year+"%' && member_delete=0 && gender="+i+" && ages="+j+";";
			int createMember = 0;
			
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			
			try
			{
				conn=getConnection();
				stmt=conn.createStatement();
				
				rs=stmt.executeQuery(sql);
				
				rs.next();
				
				createMember=rs.getInt(1);
				
				System.out.println(sql);
				System.out.println(createMember);
						
			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� createMember() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� createMember() ȸ�� ���� �߻�"+e);
				}
			}
			
			return createMember;
			
		}
		
		//ȸ�� ���� ���� �˻� ��
		
		
		//ȸ�� ���� ���� �˻� �����ε� ����
		
		public int createMember(String year, int i, int j, String monthSelect2)
		{
			String sql="select count(num) from member where create_date like '"+year+"-"+monthSelect2+"%' && member_delete=0 && gender="+i+" && ages="+j+";";
			int createMember = 0;
			
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			
			try
			{
				conn=getConnection();
				stmt=conn.createStatement();
				
				rs=stmt.executeQuery(sql);
				
				rs.next();
				
				createMember=rs.getInt(1);

			}
			catch(Exception e)
			{
				System.out.println("MemberDAO�� createMember() ���� �߻�"+e);
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
					System.out.println("MemberDAO�� createMember() ȸ�� ���� �߻�"+e);
				}
			}
			
			return createMember;
			
		}
		
		
		//ȸ�� ���� ���� �˻� �����ε� ��
		
		
		
		//vip ȸ�� �˻� ����
		
		public List<Member> memberVip(int vip)
		{
			Member m=null;
			String sql="select * from member order by priceHap desc limit ?";
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			
			List<Member> list=new ArrayList<Member>();
			
			int rank=1;
			
			try
			{
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, vip);
				rs=pstmt.executeQuery();
				
				while(rs.next())
				{
					m=new Member();
					m.setAdmin(rs.getString("admin"));
					m.setEmail(rs.getNString("email"));
					m.setName(rs.getString("name"));
					m.setNum(rs.getString("num"));
					m.setPhone(rs.getString("phone"));
					m.setPw(rs.getString("pw"));
					m.setId(rs.getString("id"));
					m.setPoint(rs.getString("point"));
					m.setGender(rs.getString("gender"));
					m.setAge(rs.getInt("age"));
					m.setCreate_date(rs.getString("create_date"));
					m.setAges(rs.getInt("ages"));
					m.setPriceHap(rs.getInt("priceHap"));
					m.setRank(rank++);
							
					list.add(m);
				}
			}
			catch(Exception e)
			{
				System.out.println("vip ȸ�� ���� ���� �߻�" +e);
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
					System.out.println("vip ȸ�� ���� ���� �߻�" +e);
				}
				
			}
			
			return list;
			
		}//vip ȸ�� �˻� ��
		
		
}



















