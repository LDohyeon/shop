package pay;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.CartDAO;
import DAO.MemberDAO;
import DAO.PayDAO;
import DAO.ProductDAO;
import beans.CartDTO;
import beans.Member;
import beans.PayDTO;
import beans.ProductDTO;


@WebServlet("/payInsert.do")
public class PayInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		
		String id=request.getParameter("id");
		String payInserts = request.getParameter("payInsert");	//이게 cart의 primary key 값
		
		
		String gender= request.getParameter("gender");
		int age=Integer.parseInt(request.getParameter("age"));
				
		
		int ages=0;//나이대 정하기
		
		
		if(age<20)
		{
			ages=1;
		}
		else if(age<30)
		{
			ages=2;
		}
		else if(age < 40)
		{
			ages=3;
		}
		else if(age < 50)
		{
			ages=4;
		}
		else if(age >=50)
		{
			ages=5;
		}
		
		

		String url="payList.do?id="+id+"&&startPage=1&&listPage=20";
		
		

		//결제하기로 받은 input 값을 받아와 뭉쳐진 데이터 num값을 풀어준다.
		String[] payInsert=payInserts.split(",");

		
		CartDAO cDAO= CartDAO.getInstance();
		
		ProductDAO prDAO=ProductDAO.getInstance();
		ProductDTO prDTO=new ProductDTO();
		
		int priceHap=0;
		int pointHap=0;
		
		
		int flag=0;//재고 부족으로 인해 상품 결제가 안 되는 상품이 존재할시

		List<CartDTO> ponupList=new ArrayList<CartDTO>();
		
		//이 포문은 주문한 상품의 수량이 재고 수량을 넘는지 검사한다.
		for(int i=0; i<payInsert.length; i++)
		{
			//그 후 num을 where num select cartList로 받아온 후 
			CartDTO cDTO=cDAO.cartPayList(Integer.parseInt(payInsert[i]));
		

			prDTO=prDAO.productEach(cDTO.getProduct_num());	//상품 번호를 넘겨줘 총 상품 수량을 가져온다.
			
		
			if(prDTO.getQuantity()-cDTO.getPurchase_quantity()<0)
			{

				flag+=1;
				ponupList.add(cDTO);

			}	
			
		}
		
		int clik=0;//결제가 보류됐을 시 판업창을 자동으로 띄우기 위해 존재
		
		if(flag!=0)//재고 부족으로 인해 상품 결제가 안 되는 상품이 존재할시
		{
			
			request.setAttribute("ponupList", ponupList);
			request.setAttribute("payInserts" , payInserts);
			//받아온 값인 이걸 다시 보내는 이유는
			//이곳에 들어왔다가 상품이 품절되어 있어 판업창을 띄우고 나면 input checked가 초기화 되어 다 풀려있다.
			//check가 풀려있어도 받아온 값을 가지고 있기 위해서 보내는 것이다.
			
			
			url="cartList.do?id="+id;
			
			clik=1;//결제가 보류됐을 시 판업창을 자동으로 띄우기 위해 존재
			
		}
		else if(flag==0)//구매를 해도 재고 수량이 남거나 딱 맞아떨어질 경우
		{
			for(int i=0; i<payInsert.length; i++)
			{
				
				
				
				//그 후 num을 where num select cartList로 받아온 후 
				CartDTO cDTO=cDAO.cartPayList(Integer.parseInt(payInsert[i]));
				
				
				PayDTO pDTO= new PayDTO();
				PayDAO pDAO=PayDAO.getInstance();
				

				pDTO.setGender(gender);
				pDTO.setAge(age);
				pDTO.setAges(ages);
				
				
				pDTO.setId(cDTO.getId());
				pDTO.setProduct_name(cDTO.getName());
				pDTO.setMaker(cDTO.getMaker());
				pDTO.setCategory(cDTO.getCategory());
				pDTO.setPrice(cDTO.getPrice()+"");
				pDTO.setPoint(cDTO.getPoint());
				pDTO.setProduct_id(cDTO.getProduct_id());
				pDTO.setPictureurl(cDTO.getPictureurl());
				pDTO.setPurchase_quantity(cDTO.getPurchase_quantity()+"");
				
				//여기는 구매 했을 시 상품등록자가 넣어둔 재고 수량을 사용자가 구매한 수량에 맞춰 빼주는 역할을 한다.
				
				prDTO=prDAO.productEach(cDTO.getProduct_num());	//상품 번호를 넘겨줘 총 상품 수량을 가져온다.
				
		

				prDAO.quantityUpdate(cDTO.getPurchase_quantity(), cDTO.getProduct_num());
					
				pDAO.payInsert(pDTO);

				cDAO.cartDelete(Integer.parseInt(payInsert[i]));
					//결제를 완료했으면 결제한 장바구니 데이터들은 삭제하고 결제 안 한 것들만 남겨둔다.
				
				pointHap+=cDTO.getPoint();
				priceHap+=cDTO.getPrice();
			}
			
			
			MemberDAO mDAO=MemberDAO.getInstance();
			mDAO.memberPoint(pointHap, id);
			mDAO.memberPriceHap(priceHap, id);
			
		}
		




		request.setAttribute("clik", clik);

		
		//결제 처리한 장바구니 리스트를 결제 내역에 저장함과 동시에 회원이 거래한 금액 합과 포인트 합을 계산한다.

		//이거 안 받아오고 있다.



		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}





	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		request.setCharacterEncoding("utf-8");
		
		String id=request.getParameter("id");
		String payInserts = request.getParameter("payInsert");	//이게 cart의 primary key 값
		                                          
		String gender= request.getParameter("gender");
		int age= Integer.parseInt(request.getParameter("age"));
				
		int ages=0;
		
		if(age<20)
		{
			ages=1;
		}
		else if(age<30)
		{
			ages=2;
		}
		else if(ages < 40)
		{
			ages=3;
		}
		else if(ages < 50)
		{
			ages=4;
		}
		else if(ages >=50)
		{
			ages=5;
		}

		

		//결제하기로 받은 input 값을 받아와 뭉쳐진 데이터 num값을 풀어준다.
		String[] payInsert=payInserts.split(",");
		
		
		
		
		CartDAO cDAO= CartDAO.getInstance();
		
		ProductDAO prDAO=ProductDAO.getInstance();
		ProductDTO prDTO=new ProductDTO();
		
		int pointHap=0;
		int priceHap=0;


		for(int i=0; i<payInsert.length; i++)
		{

			//그 후 num을 where num select cartList로 받아온 후 
			CartDTO cDTO=cDAO.cartPayList(Integer.parseInt(payInsert[i]));
				
			PayDTO pDTO= new PayDTO();
			PayDAO pDAO=PayDAO.getInstance();
			
			pDTO.setGender(gender);
			pDTO.setAge(age);
			pDTO.setAges(ages);
				
		
			pDTO.setId(cDTO.getId());
			pDTO.setProduct_name(cDTO.getName());
			pDTO.setMaker(cDTO.getMaker());
			pDTO.setCategory(cDTO.getCategory());
			pDTO.setPrice(cDTO.getPrice()+"");
			pDTO.setPoint(cDTO.getPoint());
			pDTO.setProduct_id(cDTO.getProduct_id());
			pDTO.setPictureurl(cDTO.getPictureurl());
			pDTO.setPurchase_quantity(cDTO.getPurchase_quantity()+"");
				
			//여기는 구매 했을 시 상품등록자가 넣어둔 재고 수량을 사용자가 구매한 수량에 맞춰 빼주는 역할을 한다.
				
			prDTO=prDAO.productEach(cDTO.getProduct_num());	//상품 번호를 넘겨줘 총 상품 수량을 가져온다.
				
		
			if(prDTO.getQuantity()-cDTO.getPurchase_quantity()>=0)//수량 보다 낮으면 총 수량에서 계산한 수량을 빼주고 계산과 장바구니 지우기를 실행한다.
			{
				prDAO.quantityUpdate(cDTO.getPurchase_quantity(), cDTO.getProduct_num());
					
				pDAO.payInsert(pDTO);

				cDAO.cartDelete(Integer.parseInt(payInsert[i]));
				//결제를 완료했으면 결제한 장바구니 데이터들은 삭제하고 결제 안 한 것들만 남겨둔다.
				
				pointHap+=cDTO.getPoint();
				priceHap+=cDTO.getPrice();
				
			}

		}
		

		MemberDAO mDAO=MemberDAO.getInstance();
		mDAO.memberPoint(pointHap, id);
		mDAO.memberPriceHap(priceHap, id);
			
		
		response.sendRedirect("payList.do?id="+id+"&&startPage=1&&listPage=20");
		
	}

}














