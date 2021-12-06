package DAO;

import java.sql.*;
import java.util.*;

import beans.CartDTO;

public class CartDAO {
	
	private CartDAO()
	{
		
	}
	
	private static CartDAO Instance=new CartDAO();
	
	public static CartDAO getInstance()
	{
		return Instance;
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
			System.out.println("CartDAO conn 연결 중 오류 발생 :"+ e);
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
	
	
	//장바구니 삽입 시작
	
	public void cartInsert(CartDTO cDTO)
	{
		String sql="insert into cart(id, product_num, name, price, point, maker, category, pictureurl, description, product_id, purchase_quantity)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, cDTO.getId());
			pstmt.setInt(2, cDTO.getProduct_num());
			pstmt.setString(3, cDTO.getName());
			pstmt.setInt(4, cDTO.getPrice());
			pstmt.setInt(5, cDTO.getPoint());
			pstmt.setString(6, cDTO.getMaker());
			pstmt.setString(7, cDTO.getCategory());
			pstmt.setString(8, cDTO.getPictureurl());
			pstmt.setString(9, cDTO.getDescription());
			pstmt.setString(10, cDTO.getProduct_id());
			pstmt.setInt(11, cDTO.getPurchase_quantity());
			
			
			System.out.println(pstmt);
			pstmt.execute();
		}
		catch(Exception e)
		{
			System.out.println("장바구니 삽입 중 문제 발생 : "+ e);
		}
		finally
		{
			CartDAO.close(conn, pstmt);
		}
	}
	//장바구니 삽입 끝
	
	
	//장바구니 리스트 보기 회원용 시작!
	
	public List<CartDTO> cartList(String id)
	{
		String sql="select * from cart where id=? order by num desc";
		List<CartDTO> list= new ArrayList<CartDTO>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
				
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			
			System.out.println(pstmt);
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				CartDTO cDTO=new CartDTO();
				
				cDTO.setId(rs.getString("id"));
				cDTO.setNum(rs.getInt("num"));
				cDTO.setProduct_num(rs.getInt("product_num"));
				cDTO.setName(rs.getString("name"));
				cDTO.setPrice(rs.getInt("price"));
				cDTO.setPoint(rs.getInt("point"));
				cDTO.setMaker(rs.getString("maker"));
				cDTO.setCategory(rs.getString("category"));
				cDTO.setPictureurl(rs.getString("pictureurl"));
				cDTO.setDescription(rs.getString("description"));
				cDTO.setProduct_id(rs.getString("product_id"));
				cDTO.setPurchase_quantity(rs.getInt("purchase_quantity"));
				cDTO.setProductQuantity(productQuantity(cDTO.getProduct_num()));	//317번째 줄 참조
				
				list.add(cDTO);
				
			
				
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println("장바구니 select 중 문제 발생 : "+ e);
		}
		finally
		{
			CartDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
		
	}
	
	//장바구니 리스트 보기 회원용 끝
	
	
	
	
	//장바구니 개별 삭제 시작!
	public void cartDelete(int num)
	{
		String sql="delete from cart where num=?";
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
			System.out.println("장바구니 개별 삭제 중 문제 발생 : "+ e);
		}
		finally
		{
			CartDAO.close(conn, pstmt);
		}
	}
	
	
	//장바구니 개별 삭제 끝!
	
	
	//장바구니 전체(사용자) 삭제 시작!
	
	public void cartAllDelete(String id)
	{
		String sql="delete from cart where id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("장바구니 전체(사용자) 삭제 중 문제 발생 : "+ e);
		}
		finally
		{
			CartDAO.close(conn, pstmt);
		}
	}
	
	//장바구니 전체(사용자) 삭제 끝!
	
	
	
	
	//장바구니 payInsertServlet에서 사용할 선택 결제를 위한 장바구니 리스트 보기 시작!
	
	
	public CartDTO cartPayList(int payInsert)
	{
		String sql="select * from cart where num=?";
	
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
			
		CartDTO cDTO=null;
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, payInsert);
			
			
			System.out.println(pstmt);
			rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				cDTO=new CartDTO();
				
				cDTO.setId(rs.getString("id"));
				cDTO.setNum(rs.getInt("num"));
				cDTO.setProduct_num(rs.getInt("product_num"));
				cDTO.setName(rs.getString("name"));
				cDTO.setPrice(rs.getInt("price"));
				cDTO.setPoint(rs.getInt("point"));
				cDTO.setMaker(rs.getString("maker"));
				cDTO.setCategory(rs.getString("category"));
				cDTO.setPictureurl(rs.getString("pictureurl"));
				cDTO.setDescription(rs.getString("description"));
				cDTO.setProduct_id(rs.getString("product_id"));
				cDTO.setPurchase_quantity(rs.getInt("purchase_quantity"));
				cDTO.setProductQuantity(productQuantity(cDTO.getProduct_num()));	//317번째 줄 참조
				
			}
			
		}
		catch(Exception e)
		{
			System.out.println("장바구니 선택 결제 select 중 문제 발생 : "+ e);
		}
		finally
		{
			CartDAO.close(conn, pstmt, rs);
		}
		
		
		return cDTO;
		
	}
	
	
	//장바구니 payInsertServlet에서 사용할 선택 결제를 위한 장바구니 리스트 보기 끝!
	
	
	
	
	//장바구니 상품 재고가 떨어졌을 때 0값을 꺼내기 위한 select 보기 시작!
	
	public int productQuantity(int product_num)
	{
		String sql="select quantity from product where num=?";	
		
		
		int productQuantity=0;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			
			
			
			pstmt.setInt(1, product_num);
			
			System.out.println(pstmt);
			rs=pstmt.executeQuery();
			
			rs.next();
	
			productQuantity=rs.getInt(1);
			
		}
		catch(Exception e)
		{
			System.out.println("장바구니 productQuantity 중 문제 발생 : "+ e);
		}
		finally
		{
			CartDAO.close(conn, pstmt, rs);
		}
		
		return productQuantity;
	}
	
}







