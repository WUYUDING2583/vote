package zjut.vote;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import zjut.vote.dao.AdminDao;
import zjut.vote.dao.StudentDao;
import zjut.vote.person.Student;

/**
 * Servlet implementation class notVoteInSecond
 */
@WebServlet("/notVoteInSecond")
public class notVoteInSecond extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public notVoteInSecond() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Student> list = new ArrayList<Student>();
		AdminDao dao = new AdminDao();
		list = dao.notVoteInSecond("所有学院");
		request.getSession().setAttribute("notVoteInSecondtList", list);
		response.sendRedirect("Admin/notVoteInSecond.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
		ArrayList<Student> list = new ArrayList<Student>();
		String college=request.getParameter("college");
		AdminDao dao = new AdminDao();
		list = dao.notVoteInSecond(college);
		PrintWriter out=response.getWriter();
		Gson gson=new Gson();
		String data=gson.toJson(list);
		out.println(data);
	}

}
