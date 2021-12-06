package admin;

import java.io.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.CartDAO;
import DAO.MainPictureurlDAO;
import DAO.ProductDAO;
import beans.CartDTO;


@WebServlet("/adminstartMainPicture.do")
public class AdminStartMainPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher=request.getRequestDispatcher("startMainpicture.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		ServletContext context = getServletContext();
		
		String path=context.getRealPath("/files");
		
		System.out.println(path);
		String encType="utf-8";
		int sizeLimit=20*1024*1024;
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		
		
		MainPictureurlDAO main=MainPictureurlDAO.getInstance();
		
		
		String pictureurl1=multi.getFilesystemName("pictureurl1");
		String pictureurl2=multi.getFilesystemName("pictureurl2");
		String pictureurl3=multi.getFilesystemName("pictureurl3");
		
		
		if(pictureurl1 !=null || pictureurl2 !=null || pictureurl3 !=null)
		{
			String mainPictureurl1="/files/"+pictureurl1;
			String mainPictureurl2="/files/"+pictureurl2;
			String mainPictureurl3="/files/"+pictureurl3;
			
			List<String> list=new ArrayList<String>();
			
			list.add(mainPictureurl1);
			list.add(mainPictureurl2);
			list.add(mainPictureurl3);
			
			
			main.imageDelete();
			
			for(int i=0; i<list.size(); i++)
			{
				main.imageInsert(list.get(i));
			}
			
		}
		else if(pictureurl1 ==null && pictureurl2 ==null && pictureurl3 ==null)
		{
			String notice=multi.getFilesystemName("notice");

			int numNotice=1;
			
			main.imageDelete(numNotice);
			if(notice !=null)
			{
				String fileNotice ="/files/"+notice;
				main.noticeInsert(fileNotice, numNotice);
			}
		}
	
		response.sendRedirect("adminMain.do");		

	}
}




















