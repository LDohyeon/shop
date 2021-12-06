package pay;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.PayDAO;

@WebServlet("/payQuantity.do")
public class PayQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("payQuantity.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String year=request.getParameter("year");
		int monCheck=Integer.parseInt(request.getParameter("monCheck"));
				
		
		PayDAO pDAO=PayDAO.getInstance();
		
		String sqlValue="";
		
		if(monCheck==1)//월별 검색을 클릭 했을 때
		{
			int monthSelect=Integer.parseInt(request.getParameter("monthSelect"));
			String monthSelect2="";
			
			if(monthSelect<10)
			{
				monthSelect2="0"+monthSelect;
			}
			else
			{
				monthSelect2=monthSelect+"";
			}
			
					
			for(int i=0; i<2; i++)
			{
				for(int j=1; j<6; j++)
				{
					sqlValue += pDAO.YearQuantity(year, i, j, monthSelect2)+",";	
				}
			}
					
		}
		else//월별 검색을 클릭 안 했을 때
		{
			for(int i=0; i<2; i++)
			{
				for(int j=1; j<6; j++)
				{
					sqlValue += pDAO.YearQuantity(year, i, j)+",";	
				}
			}
		}
		

		response.getWriter().write(sqlValue);
		
	}

}










