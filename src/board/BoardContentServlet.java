package board;

import java.io.IOException;

import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BoardDAO;
import beans.Board;

import beans.Comment;
import DAO.CommentDAO;


@WebServlet("/boardContent.do")
public class BoardContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		int num=Integer.parseInt(request.getParameter("num"));
				

	
		
		
		BoardDAO bDAO=BoardDAO.getInstance();
		List<Board> data=bDAO.Content(num);
		
		HttpSession session=request.getSession();
		
		
		session.setAttribute("data" , data);
		session.setAttribute("num", num);
		

		int result=bDAO.hits(num);
		
		if(result==1)
		{
			System.out.println("��ȸ�� �ø��� ����");
		}
		else
		{
			System.out.println("����");
		}
			
		System.out.println(data);
		
		
		
		//������� comment
		CommentDAO cDAO=CommentDAO.getinstance();
		List<Comment> cs= cDAO.cs(num);
		
		session.setAttribute("cs", cs);
		
		
		//����� ��� ���� ����
		int count=cDAO.count_comment(num);
		request.setAttribute("count", count);
		
		
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("boardContent.jsp");
		dispatcher.forward(request, response);
		//response.sendRedirect("boardContent.jsp");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

}
