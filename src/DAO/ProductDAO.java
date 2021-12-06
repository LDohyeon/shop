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
	
	
	//상품 삽입 시작
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
			System.out.println("-----insertProduct에서 오류 발생 : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
	}
	
	//상품 삽입 끝
	
	
	//절대 관리자(0번)의 상품 리스트 보기 시작
	
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
			System.out.println("selectProduct에서 오류 발생 : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//절대 관리자(0번)의 상품 리스트 보기 끝
	
	//관리자(1번)의 상품 리스트 보기 시작 0번 오버로딩
	
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
			System.out.println("selectProduct에서 오류 발생 : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//관리자(1번)의 상품 리스트 보기 시작 0번 오버로딩 끝
	
	
	
	//페이지 분할 처리 버튼 관리자 번호 0 시작
	
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
			System.out.println("페이지 보기 중 오류 발생 : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return pageBtn;
	}
	

	//페이지 분할 처리 버튼 관리자 번호 0 끝
	
	
	//페이지 분할 처리 버튼 관리자 번호 1 시작
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
			System.out.println("페이지 보기 중 오류 발생 : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return pageBtn;
	}
	
	//페이지 분할 처리 버튼 관리자 번호 1 끝
	
	
	//상품 하나만 보기 시작
	
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
			System.out.println("상품 개별 조회 처리 중 오류발생 : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//상품 하나만 보기 끝
	
	
	//상품 수정 시작
	
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
			System.out.println("상품 업데이트 중 오류발생 : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
		
	}
	
	//상품 수정 끝
	

	//상품 삭제 시작
	
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
			System.out.println("상품 삭제 중 오류발생 : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
	}
	//상품 삭제 끝
	
	
	//상품 개별 조회 시작
	
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
			System.out.println("상품 개별 조회 중 오류발생 : " +e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return pDTO;
	}
	
	//상품 개별 조회 끝
	
	
	
	//상품 카테고리별 조회 시작
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
			System.out.println("selectProduct에서 오류 발생 : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	
	//상품 카테고리별 조회 끝

	
	//상품 검색 기능 시작
	
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
			System.out.println("selectProduct에서 오류 발생 : "+e);
		}
		finally
		{
			ProductDAO.close(conn, stmt, rs);
		}
		
		
		return list;
	}
	
	
	//상품 검색 기능 끝
	
	
	
	//상품 구매시 재고 수량 다운 시작
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
			
			
			//여따가 상품을 구매하고 봤더니 마이너스면 다시 플러스 해준다.
			//아니 여기 말고 서블릿에서 일단 계산을 해서 마이너스면 이걸 실행을 시키지 말자
		}
		catch(Exception e)
		{
			System.out.println("quantityUpdate에서 오류 발생 : "+e);
		}
		finally
		{
			ProductDAO.close(conn, pstmt);
		}
				
	}
	
	
	//상품 구매시 재고 수량 다운 끝
	
}










