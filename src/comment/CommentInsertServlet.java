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
			System.out.println("댓글 업로드 성공");
		}
		else
		{
			System.out.println("댓글 업로드 실패");
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
		// 이건 boardContent.jsp 참초
		//이걸 쓰고 boardContent에서 c:if문으로 받은 이유는
		//이게 post이기 때문에 num이 넘어가질 않아 다른 방법을 써줘야 했다.
		//이렇게 하기 싫으면 서블릿을 하나 더 만들면 된다.
		

		response.sendRedirect("boardContent.do?num="+num);
		//+"&&result="+result
	}

}











