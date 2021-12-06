package DAO;

import java.sql.*;
import java.util.*;

import beans.ProductDTO;

public class ProductDAO {

	private ProductDAO()
	{
		
	}
	
	private static ProductDAO instance=new ProductDAO();
	
	public static ProductDAO getInstance()
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
			System.out.println("product conn ���� �� ���� �߻� :"+ e);
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
	
	
	//��ǰ ���� ����
	public void insertProduct(ProductDTO pDTO)
	{
		String sql="insert into product(id, name, price, point, maker, type, category, pictureurl, description, quantity) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ? ,?, ?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, pDTO.getId());
			pstmt.setString(2, pDTO.getName());
			pstmt.setInt(3, pDTO.getPrice());
			pstmt.setInt(4, (pDTO.getPrice()/100));
			pstmt.setString(5, pDTO.getMaker());
			pstmt.setString(6, pDTO.getType());
			pstmt.setString(7, pDTO.getCategory());
			pstmt.setString(8, pDTO.getPictureurl());
			pstmt.setString(9, pDTO.getDescription());
			pstmt.setInt(10, pDTO.getQuantity());
			
			System.out.println(pstmt);
			
			pstmt.execute();
					
		}
		catch(Exception e)
		{
			System.out.println("-----insertProduct���� ���� �߻� : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
	}
	
	//��ǰ ���� ��
	
	
	//���� ������(0��)�� ��ǰ ����Ʈ ���� ����
	
	public List<ProductDTO> selectProduct(int startPage, int lastPage)
	{
		List<ProductDTO> list=new ArrayList<ProductDTO>();
		String sql="select * from product order by num desc limit ?, ?";
		
		int start=startPage*lastPage-lastPage;
		
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
				ProductDTO pDTO=new ProductDTO();
				pDTO.setId(rs.getString("id"));
				pDTO.setNum(rs.getInt("num"));
				pDTO.setName(rs.getString("name"));
				pDTO.setPrice(rs.getInt("price"));
				pDTO.setMaker(rs.getString("maker"));
				pDTO.setCategory(rs.getString("category"));
				pDTO.setPictureurl(rs.getString("pictureurl"));
				pDTO.setDescription(rs.getString("description"));
				pDTO.setQuantity(rs.getInt("quantity"));
				
				list.add(pDTO);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("selectProduct���� ���� �߻� : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//���� ������(0��)�� ��ǰ ����Ʈ ���� ��
	
	//������(1��)�� ��ǰ ����Ʈ ���� ���� 0�� �����ε�
	
	public List<ProductDTO> selectProduct(String id, int startPage, int lastPage)
	{
		List<ProductDTO> list=new ArrayList<ProductDTO>();
		String sql="select * from product where id= ? order by num desc limit ?, ?";
					
		
		int start=startPage*lastPage-lastPage;
		
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
				ProductDTO pDTO=new ProductDTO();
				pDTO.setId(rs.getString("id"));
				pDTO.setNum(rs.getInt("num"));
				pDTO.setName(rs.getString("name"));
				pDTO.setPrice(rs.getInt("price"));
				pDTO.setMaker(rs.getString("maker"));
				pDTO.setCategory(rs.getString("category"));
				pDTO.setPictureurl(rs.getString("pictureurl"));
				pDTO.setDescription(rs.getString("description"));
				pDTO.setQuantity(rs.getInt("quantity"));
				
				list.add(pDTO);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("selectProduct���� ���� �߻� : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//������(1��)�� ��ǰ ����Ʈ ���� ���� 0�� �����ε� ��
	
	
	
	//������ ���� ó�� ��ư ������ ��ȣ 0 ����
	
	public int pageBtn()
	{
		int pageBtn=0;
		String sql="select count(*) from product";
		
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			pageBtn=Integer.parseInt(rs.getString(1));
			
					
		}
		catch(Exception e)
		{
			System.out.println("������ ���� �� ���� �߻� : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return pageBtn;
	}
	

	//������ ���� ó�� ��ư ������ ��ȣ 0 ��
	
	
	//������ ���� ó�� ��ư ������ ��ȣ 1 ����
	public int pageBtn(String id)
	{
		int pageBtn=0;
		String sql="select count(*) from product where id=?";
		
		Connection conn=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			pageBtn=Integer.parseInt(rs.getString(1));
			
					
		}
		catch(Exception e)
		{
			System.out.println("������ ���� �� ���� �߻� : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return pageBtn;
	}
	
	//������ ���� ó�� ��ư ������ ��ȣ 1 ��
	
	
	//��ǰ �ϳ��� ���� ����
	
	public List<ProductDTO> selectProductByNum(String num)
	{
		List<ProductDTO> list=new ArrayList<ProductDTO>();
		String sql="select * from product where num=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(num));
			
			rs=pstmt.executeQuery();
			
			rs.next();
			
			ProductDTO pDTO= new ProductDTO();
			
			pDTO.setId(rs.getString("id"));
			pDTO.setNum(rs.getInt("num"));
			pDTO.setName(rs.getString("name"));
			pDTO.setPrice(rs.getInt("price"));
			pDTO.setMaker(rs.getString("maker"));
			pDTO.setCategory(rs.getString("category"));
			pDTO.setPictureurl(rs.getString("pictureurl"));
			pDTO.setDescription(rs.getString("description"));
			pDTO.setQuantity(rs.getInt("quantity"));
			
			list.add(pDTO);
		}
		catch(Exception e)
		{
			System.out.println("��ǰ ���� ��ȸ ó�� �� �����߻� : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//��ǰ �ϳ��� ���� ��
	
	
	//��ǰ ���� ����
	
	public void updateProduct(ProductDTO pDTO)
	{
		String sql="update product set id=?, name=?, price=?, point=?, maker=?, category=?, pictureurl=?, description=?, quantity=? where num=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, pDTO.getId());
			pstmt.setString(2, pDTO.getName());
			pstmt.setInt(3, pDTO.getPrice());
			pstmt.setInt(4, (pDTO.getPrice()/10));
			pstmt.setString(5, pDTO.getMaker());
			pstmt.setString(6, pDTO.getCategory());
			pstmt.setString(7, pDTO.getPictureurl());
			pstmt.setString(8, pDTO.getDescription());
			pstmt.setInt(9, pDTO.getQuantity());
			pstmt.setInt(10, pDTO.getNum());

			
			
			System.out.println(pstmt);
			
			pstmt.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println("��ǰ ������Ʈ �� �����߻� : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
		
	}
	
	//��ǰ ���� ��
	

	//��ǰ ���� ����
	
	public void deleteProduct(int num)
	{
		String sql="delete from product where num = ?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			System.out.println(pstmt);
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("��ǰ ���� �� �����߻� : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
	}
	//��ǰ ���� ��
	
	
	//��ǰ ���� ��ȸ ����
	
	public ProductDTO productEach(int num)
	{
		ProductDTO pDTO=new ProductDTO();
		
		String sql="select * from product where num= ?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn = getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			rs.next();
			
			
			pDTO.setId(rs.getString("id"));
			pDTO.setNum(rs.getInt("num"));
			pDTO.setName(rs.getString("name"));
			pDTO.setPrice(rs.getInt("price"));
			pDTO.setMaker(rs.getString("maker"));
			pDTO.setCategory(rs.getString("category"));
			pDTO.setPictureurl(rs.getString("pictureurl"));
			pDTO.setDescription(rs.getString("description"));
			pDTO.setPoint(Integer.parseInt(rs.getString("point")));
			pDTO.setQuantity(rs.getInt("quantity"));
			
		}
		catch(Exception e)
		{
			System.out.println("��ǰ ���� ��ȸ �� �����߻� : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return pDTO;
	}
	
	//��ǰ ���� ��ȸ ��
	
	
	
	//��ǰ ī�װ��� ��ȸ ����
	public List<ProductDTO> selectCategoryProduct(String category,int startPage, int lastPage)
	{
		List<ProductDTO> list=new ArrayList<ProductDTO>();
		String sql="select * from product where category=? order by num desc limit ?, ?";
		
	
		
		int start=startPage*lastPage-lastPage;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, category);
			pstmt.setInt(2, start);
			pstmt.setInt(3, lastPage);
			
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				ProductDTO pDTO=new ProductDTO();
				pDTO.setId(rs.getString("id"));
				pDTO.setNum(rs.getInt("num"));
				pDTO.setName(rs.getString("name"));
				pDTO.setPrice(rs.getInt("price"));
				pDTO.setMaker(rs.getString("maker"));
				pDTO.setCategory(rs.getString("category"));
				pDTO.setPictureurl(rs.getString("pictureurl"));
				pDTO.setDescription(rs.getString("description"));
				pDTO.setQuantity(rs.getInt("quantity"));
				
				list.add(pDTO);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("selectProduct���� ���� �߻� : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//��ǰ ī�װ��� ��ȸ ��

	
	//��ǰ �˻� ��� ����
	
	public List<ProductDTO> searchProduct(String search,int startPage, int lastPage)
	{
		List<ProductDTO> list=new ArrayList<ProductDTO>();
		
		int start=startPage*lastPage-lastPage;
		
		String sql= "select * from product where name like '%"+search+"%' order by num desc limit "+start+", "+lastPage+";";
		
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
		
			System.out.println(stmt);
			rs=stmt.executeQuery(sql);
			
			while(rs.next())
			{
				ProductDTO pDTO=new ProductDTO();
				pDTO.setId(rs.getString("id"));
				pDTO.setNum(rs.getInt("num"));
				pDTO.setName(rs.getString("name"));
				pDTO.setPrice(rs.getInt("price"));
				pDTO.setMaker(rs.getString("maker"));
				pDTO.setCategory(rs.getString("category"));
				pDTO.setPictureurl(rs.getString("pictureurl"));
				pDTO.setDescription(rs.getString("description"));
				pDTO.setQuantity(rs.getInt("quantity"));
				
				list.add(pDTO);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("selectProduct���� ���� �߻� : "+e);
		}
		finally
		{
			ProductDAO.close(conn, stmt, rs);
		}
		
		
		return list;
	}
	
	
	//��ǰ �˻� ��� ��
	
	
	
	//��ǰ ���Ž� ��� ���� �ٿ� ����
	public void quantityUpdate(int purchase_quantity, int num)
	{
		String sql="update product set quantity=quantity-? where num=?";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, purchase_quantity);
			pstmt.setInt(2, num);
			
			pstmt.executeUpdate();
			
			
			//������ ��ǰ�� �����ϰ� �ô��� ���̳ʽ��� �ٽ� �÷��� ���ش�.
			//�ƴ� ���� ���� �������� �ϴ� ����� �ؼ� ���̳ʽ��� �̰� ������ ��Ű�� ����
		}
		catch(Exception e)
		{
			System.out.println("quantityUpdate���� ���� �߻� : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
				
	}
	
	
	//��ǰ ���Ž� ��� ���� �ٿ� ��
	
}










