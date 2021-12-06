package notice;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.NoticeBoardDAO;
import beans.NoticeBoardDTO;

@WebServlet("/noticeBoardUpdate.do")
public class NoticeBoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int num=Integer.parseInt(request.getParameter("num"));
		
		NoticeBoardDAO nDAO=NoticeBoardDAO.getInstance();
		
		NoticeBoardDTO nDTO=nDAO.noticeEachSelect(num);
		
		
		request.setAttribute("list", nDTO);
		request.setAttribute("num", num);
		
		RequestDispatcher dispatcher=request.getRequestDispatcher("noticeBoardWriting.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		ServletContext context=getServletContext();
		String path=context.getRealPath("/files");
		
		System.out.println(path);
		String encType="utf-8";
		int sizeLimit=20*1024*1024;
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		System.out.println(path);
		
		int num=Integer.parseInt(multi.getParameter("num"));
		String title=multi.getParameter("title");
		String content=multi.getParameter("content");
		String pictureurl=multi.getFilesystemName("pictureurl");
		String options=multi.getParameter("options");
		
		
		NoticeBoardDTO nDTO=new NoticeBoardDTO();
		
		nDTO.setNum(num);
		nDTO.setTitle(title);
		nDTO.setContent(content);
		nDTO.setOptions(options);
		
		if(pictureurl!=null)
		{
			nDTO.setPictureurl("/files/"+pictureurl);
		}
		if(pictureurl==null)
		{
			pictureurl=multi.getParameter("hiddenPictureurl");
			nDTO.setPictureurl(pictureurl);
			
		}
		
		NoticeBoardDAO nDAO= NoticeBoardDAO.getInstance();
		nDAO.noticeUpdate(nDTO);
		

		response.sendRedirect("noticeBoardSelect.do?startPage=1&&lastPage=10");
		
	}

}















