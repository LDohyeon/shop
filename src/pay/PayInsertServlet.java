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
		String payInserts = request.getParameter("payInsert");	//�̰� cart�� primary key ��
		
		
		String gender= request.getParameter("gender");
		int age=Integer.parseInt(request.getParameter("age"));
				
		
		int ages=0;//���̴� ���ϱ�
		
		
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
		
		

		//�����ϱ�� ���� input ���� �޾ƿ� ������ ������ num���� Ǯ���ش�.
		String[] payInsert=payInserts.split(",");

		
		CartDAO cDAO= CartDAO.getInstance();
		
		ProductDAO prDAO=ProductDAO.getInstance();
		ProductDTO prDTO=new ProductDTO();
		
		int priceHap=0;
		int pointHap=0;
		
		
		int flag=0;//��� �������� ���� ��ǰ ������ �� �Ǵ� ��ǰ�� �����ҽ�

		List<CartDTO> ponupList=new ArrayList<CartDTO>();
		
		//�� ������ �ֹ��� ��ǰ�� ������ ��� ������ �Ѵ��� �˻��Ѵ�.
		for(int i=0; i<payInsert.length; i++)
		{
			//�� �� num�� where num select cartList�� �޾ƿ� �� 
			CartDTO cDTO=cDAO.cartPayList(Integer.parseInt(payInsert[i]));
		

			prDTO=prDAO.productEach(cDTO.getProduct_num());	//��ǰ ��ȣ�� �Ѱ��� �� ��ǰ ������ �����´�.
			
		
			if(prDTO.getQuantity()-cDTO.getPurchase_quantity()<0)
			{

				flag+=1;
				ponupList.add(cDTO);

			}	
			
		}
		
		int clik=0;//������ �������� �� �Ǿ�â�� �ڵ����� ���� ���� ����
		
		if(flag!=0)//��� �������� ���� ��ǰ ������ �� �Ǵ� ��ǰ�� �����ҽ�
		{
			
			request.setAttribute("ponupList", ponupList);
			request.setAttribute("payInserts" , payInserts);
			//�޾ƿ� ���� �̰� �ٽ� ������ ������
			//�̰��� ���Դٰ� ��ǰ�� ǰ���Ǿ� �־� �Ǿ�â�� ���� ���� input checked�� �ʱ�ȭ �Ǿ� �� Ǯ���ִ�.
			//check�� Ǯ���־ �޾ƿ� ���� ������ �ֱ� ���ؼ� ������ ���̴�.
			
			
			url="cartList.do?id="+id;
			
			clik=1;//������ �������� �� �Ǿ�â�� �ڵ����� ���� ���� ����
			
		}
		else if(flag==0)//���Ÿ� �ص� ��� ������ ���ų� �� �¾ƶ����� ���
		{
			for(int i=0; i<payInsert.length; i++)
			{
				
				
				
				//�� �� num�� where num select cartList�� �޾ƿ� �� 
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
				
				//����� ���� ���� �� ��ǰ����ڰ� �־�� ��� ������ ����ڰ� ������ ������ ���� ���ִ� ������ �Ѵ�.
				
				prDTO=prDAO.productEach(cDTO.getProduct_num());	//��ǰ ��ȣ�� �Ѱ��� �� ��ǰ ������ �����´�.
				
		

				prDAO.quantityUpdate(cDTO.getPurchase_quantity(), cDTO.getProduct_num());
					
				pDAO.payInsert(pDTO);

				cDAO.cartDelete(Integer.parseInt(payInsert[i]));
					//������ �Ϸ������� ������ ��ٱ��� �����͵��� �����ϰ� ���� �� �� �͵鸸 ���ܵд�.
				
				pointHap+=cDTO.getPoint();
				priceHap+=cDTO.getPrice();
			}
			
			
			MemberDAO mDAO=MemberDAO.getInstance();
			mDAO.memberPoint(pointHap, id);
			mDAO.memberPriceHap(priceHap, id);
			
		}
		




		request.setAttribute("clik", clik);

		
		//���� ó���� ��ٱ��� ����Ʈ�� ���� ������ �����԰� ���ÿ� ȸ���� �ŷ��� �ݾ� �հ� ����Ʈ ���� ����Ѵ�.

		//�̰� �� �޾ƿ��� �ִ�.



		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}





	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		request.setCharacterEncoding("utf-8");
		
		String id=request.getParameter("id");
		String payInserts = request.getParameter("payInsert");	//�̰� cart�� primary key ��
		                                          
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

		

		//�����ϱ�� ���� input ���� �޾ƿ� ������ ������ num���� Ǯ���ش�.
		String[] payInsert=payInserts.split(",");
		
		
		
		
		CartDAO cDAO= CartDAO.getInstance();
		
		ProductDAO prDAO=ProductDAO.getInstance();
		ProductDTO prDTO=new ProductDTO();
		
		int pointHap=0;
		int priceHap=0;


		for(int i=0; i<payInsert.length; i++)
		{

			//�� �� num�� where num select cartList�� �޾ƿ� �� 
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
				
			//����� ���� ���� �� ��ǰ����ڰ� �־�� ��� ������ ����ڰ� ������ ������ ���� ���ִ� ������ �Ѵ�.
				
			prDTO=prDAO.productEach(cDTO.getProduct_num());	//��ǰ ��ȣ�� �Ѱ��� �� ��ǰ ������ �����´�.
				
		
			if(prDTO.getQuantity()-cDTO.getPurchase_quantity()>=0)//���� ���� ������ �� �������� ����� ������ ���ְ� ���� ��ٱ��� ����⸦ �����Ѵ�.
			{
				prDAO.quantityUpdate(cDTO.getPurchase_quantity(), cDTO.getProduct_num());
					
				pDAO.payInsert(pDTO);

				cDAO.cartDelete(Integer.parseInt(payInsert[i]));
				//������ �Ϸ������� ������ ��ٱ��� �����͵��� �����ϰ� ���� �� �� �͵鸸 ���ܵд�.
				
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














