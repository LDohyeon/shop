package DAO;

import java.sql.*;

import java.util.*;

import beans.PayDTO;

public class PayDAO {

	private PayDAO()
	{
		
	}
	
	private static PayDAO instance =new PayDAO();
	
	public static PayDAO getInstance()
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
			System.out.println("payDAO getConnection ����");
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
			System.out.println("ȸ�� ���� �� ���� �߻� : "+ e);
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
			System.out.println("ȸ�� ���� �� ���� �߻� : "+ e);
		}
	}
	
	
	
	//���� ���� ���� ����
	
	public void payInsert(PayDTO pDTO)
	{
		String sql="insert into pay(id, product_name, maker, category, price, point, product_id, pictureurl, purchase_quantity, create_date, gender, age, ages) "
				+ "values(?, ? ,? , ?, ?, ?, ?, ?, ?, NOW(), ? ,? ,?)";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, pDTO.getId());
			pstmt.setString(2, pDTO.getProduct_name());
			pstmt.setString(3, pDTO.getMaker());
			pstmt.setString(4, pDTO.getCategory());
			pstmt.setString(5, pDTO.getPrice());
			pstmt.setInt(6, pDTO.getPoint());
			pstmt.setString(7, pDTO.getProduct_id());
			pstmt.setString(8, pDTO.getPictureurl());
			pstmt.setString(9, pDTO.getPurchase_quantity());
			
			pstmt.setString(10, pDTO.getGender());
			pstmt.setInt(11, pDTO.getAge());
			pstmt.setInt(12, pDTO.getAges());
					
			
			//System.out.println(pstmt);
			
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("--------------pDAO payInsert()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, pstmt);
		}
		
	}
	
	//���� ���� ���� ��
	
	
	
	
	//���� ����Ʈ ����
	
	public List<PayDTO> payList(String id, int startPage, int lastPage)
	{
		List<PayDTO> list =new ArrayList<PayDTO>();
		
		int start=startPage*lastPage-lastPage;
		
		//String sql="select * from pay where id=? order by num desc limit ?, ?";
		
		String sql="select pay.num, product.pictureurl, product.name, product.id, product.maker, pay.purchase_quantity,"
				+ " pay.price, pay.point, pay.create_date, pay.delete_date, pay.pay_delete, pay.gender, pay.age, pay.ages"
				+ " from pay left join product on pay.product_name = product.name where pay.id = ? order by pay.num desc limit ?, ?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, lastPage);
			
			rs=pstmt.executeQuery();

			while(rs.next())
			{
				PayDTO DTO = new PayDTO();
				DTO.setNum(rs.getString("pay.num"));
				DTO.setId(rs.getString("product.id"));
				DTO.setProduct_name(rs.getString("product.name"));
				DTO.setMaker(rs.getString("product.maker"));
				DTO.setPrice(rs.getString("pay.price"));
				DTO.setProduct_id(rs.getString("product.id"));
				DTO.setPictureurl(rs.getString("product.pictureurl"));
				DTO.setCreate_date(rs.getString("pay.create_date"));
				DTO.setDelete_date(rs.getString("pay.delete_date"));
				DTO.setPay_delete(rs.getString("pay.pay_delete"));
				
				DTO.setPoint(Integer.parseInt(rs.getString("pay.point")));
				
				DTO.setPurchase_quantity(rs.getString("pay.purchase_quantity"));
				
				DTO.setGender(rs.getString("pay.gender"));
				DTO.setAge(rs.getInt("pay.age"));
				DTO.setAges(rs.getInt("pay.ages"));
				list.add(DTO);
			}

		}
		catch(Exception e)
		{
			System.out.println("--------------pDAO selectPay()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//���� ����Ʈ ���� ��
	
	
	//���� ����Ʈ ������ ����
	
	public int pageList(String id)
	{
		int payPage=0;
		String sql="select count(*) from pay where id=?";
		
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
			
			payPage=Integer.parseInt(rs.getString(1));
			
		}
		catch(Exception e)
		{
			System.out.println("--------------pDAO pageList()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, pstmt, rs);
		}
		
		return payPage;
	}
	
	//���� ����Ʈ ������ ��
	
	
	//���� ȯ�� ó�� ����
	public void payRefund(int num)
	{
		String sql="update pay set pay_delete=1, delete_date=NOW() where num=?";
		
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
			System.out.println("--------------pDAO payRefund()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, pstmt);
		}
	}
	
	//���� ȯ�� ó�� ��
	
	
	
	
	//����� �ݾװ� ����Ʈ�� �����ϱ� ���� ��ǰ�� �ݾ� ã�� ����
	
	public int payProductPrice(int num)
	{
		int ppp=0;
		String sql="select * from pay where num=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			rs.next();
			
			ppp=rs.getInt("price");
		}
		catch(Exception e)
		{
			System.out.println("--------------pDAO payProductPrice()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, pstmt, rs);
		}
		
		
		return ppp;
	}
	
	//����� �ݾװ� ����Ʈ�� �����ϱ� ���� ��ǰ�� �ݾ� ã�� ��
	
	
	
	
	
	
	//�����ڰ� 1�Ⱓ ���� ���� ���� ����
	
	public int YearsPrice(String years)
	{
		int priceYearHap=0;
		String sql="select price from pay where create_date like '"+years+"%' and pay_delete=0;";

		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();

			rs=stmt.executeQuery(sql);
			
			while(rs.next())
			{
				priceYearHap+=rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			System.out.println("--------------pDAO YearsPrice()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, stmt, rs);
		}
		
		return priceYearHap;
	}
	
	//�����ڰ� 1�Ⱓ ���� ���� ���� ��
	
	
	
	
	
	//�����ڰ� 1�Ⱓ ���� ���� ���� Ȯ�� ����
	
	public int YearQuantity(String year, int i, int j)
	{
		int yearQuantityHap=0;
		String sql="select purchase_quantity from pay where create_date like '"+year+"%' && pay_delete=0 && gender="+i+" && ages="+j+";";
		
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
				yearQuantityHap+=rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			System.out.println("--------------pDAO YearQuantity()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, stmt, rs);
		}
		

		return yearQuantityHap;
	}
	
	//�����ڰ� 1�Ⱓ ���� ���� ���� Ȯ�� ��
	
	//�ٷ� ������ �����ε� ����
	
	public int YearQuantity(String year, int i, int j, String monthSelect)
	{
		int yearQuantityHap=0;
		String sql="select purchase_quantity from pay where create_date like '"+year+"-"+monthSelect+"%' && pay_delete=0 && gender="+i+" && ages="+j+";";
		
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
				yearQuantityHap+=rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			System.out.println("--------------pDAO YearQuantity()���� ���� �߻�"+e);
		}
		finally
		{
			PayDAO.close(conn, stmt, rs);
		}
		

		return yearQuantityHap;
	}
	
	//�ٷ� ������ �����ε� ����
}
















