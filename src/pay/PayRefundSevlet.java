package pay;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.MemberDAO;
import DAO.PayDAO;



@WebServlet("/payRefund.do")
public class PayRefundSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int num=Integer.parseInt(request.getParameter("payNum"));
		String id=request.getParameter("id");
		PayDAO pDAO= PayDAO.getInstance();	
		
		pDAO.payRefund(num);	//ȯ��ó�� ��
		
		
		
		//���⼭���� ȸ���� �� �����ݾװ� ������ ����Ʈ�� �����Ѵ�.
		MemberDAO mDAO=MemberDAO.getInstance();
		int mPrice = mDAO.selectPriceHapMember(id);
		int mPoint = mDAO.selectPointMember(id);
		
		System.out.println("------------mPrice : "+mPrice);
		System.out.println("------------mPoint : "+mPoint);
		
		int payProductPrice = pDAO.payProductPrice(num);
		
		System.out.println("------------payProductPrice : "+payProductPrice);
		
		mPrice -=payProductPrice;
		mPoint -=(payProductPrice/100);
		
		
		mDAO.memberRefundPriceHap(mPrice, id);
		mDAO.memberRefundPoint(mPoint, id);
		
		
		response.sendRedirect("adminPayList.do?id="+id+"&&startPage=1&&listPage=20");
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
