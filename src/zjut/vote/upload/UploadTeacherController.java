package zjut.vote.upload;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import zjut.vote.dao.AdminDao;


/**
 * Servlet implementation class UploadTeacherController
 */
@WebServlet("/UploadTeacherController")
public class UploadTeacherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadTeacherController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		AdminDao dao=new AdminDao();
		String path = getServletContext().getRealPath("/") + File.separator;
		PrintWriter out=response.getWriter();
		int count=0;
		try {
			ArrayList<File> list=dao.fileUpload(request, response, path);
			count=dao.UploadTeacher(list,path);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("failure");
		}
		out.print(count+"个文件上传成功");
	}

}
