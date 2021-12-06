package comment;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.CommentDAO;


@WebServlet("/comment.do")
public class CommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		int num=Integer.parseInt(request.getParameter("num"));
		String comment =request.getParameter("comment");
		String id=request.getParameter("id");
		//int comment_order=Integer.parseInt(request.getParameter("comment_order"));
		
		
		System.out.println("cNum="+num+", comment = "+comment+", id = "+id);
		
		
		CommentDAO cDAO=CommentDAO.getinstance();
		
		int result=cDAO.insertComment(comment, id, num);
		
		if(result==1)
		{
			System.out.println("��� ���ε� ����");
		}
		else
		{
			System.out.println("��� ���ε� ����");
		}
		

		request.setAttribute("num", num);
		
		
		response.sendRedirect("boardContent.do?num="+num);
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
	
		
		int num=Integer.parseInt(request.getParameter("num"));
		String comment =request.getParameter("comment2");
		String id=request.getParameter("id");
		int comment_order=Integer.parseInt(request.getParameter("comment_order"));
		
		
		System.out.println("cNum="+num+", comment = "+comment+", id = "+id +" comment_order = "+comment_order);
		
		
		CommentDAO cDAO=CommentDAO.getinstance();
		
	
		cDAO.insertCcomment(comment, id, num, comment_order);
		

		
		
		request.setAttribute("num", num);
		
		
//		request.setAttribute("result", result);
		// �̰� boardContent.jsp ����
		//�̰� ���� boardContent���� c:if������ ���� ������
		//�̰� post�̱� ������ num�� �Ѿ�� �ʾ� �ٸ� ����� ����� �ߴ�.
		//�̷��� �ϱ� ������ ������ �ϳ� �� ����� �ȴ�.
		

		response.sendRedirect("boardContent.do?num="+num);
		//+"&&result="+result
	}

}











