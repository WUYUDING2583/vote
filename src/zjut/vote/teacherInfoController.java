package zjut.vote;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import zjut.vote.dao.StudentDao;
import zjut.vote.person.Teacher;

/**
 * Servlet implementation class teacherInfoController
 */
@WebServlet("/teacherInfo")
public class teacherInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public teacherInfoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
        PrintWriter out=response.getWriter();
		String no=request.getParameter("no");
		System.out.println(no);
		StudentDao dao=new StudentDao();
		Teacher te=new Teacher();
		te=dao.getTeacher1Info(no);
		Gson gson=new Gson();
		String data=gson.toJson(te);
		System.out.println(gson.toJson(te));
		out.println(data);
	}

}
