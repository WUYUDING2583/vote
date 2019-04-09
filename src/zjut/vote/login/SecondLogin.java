package zjut.vote.login;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import zjut.vote.dao.StudentDao;
import zjut.vote.person.Student;

/**
 * Servlet implementation class SecondLogin
 */
@WebServlet("/secondIn")
public class SecondLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SecondLogin() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		request.getSession().setAttribute("width", width);
		request.getSession().setAttribute("height", height);
		Student stu = new Student();
		StudentDao dao = new StudentDao();
		String message = null;
		stu = dao.Login(no, password);
		Pattern pattern = Pattern.compile("201\\w2982");
		Matcher matcher = pattern.matcher(no);
		request.getSession().setAttribute("num", stu.getSecondVote());
		request.getSession().setAttribute("Student", stu);
		boolean flag = false;
		if (no.startsWith("L") || no.startsWith("Y") || matcher.find())
			flag = true;
		if (stu.getNo().equals("-1")) {
			message = stu.getName();
			request.getSession().setAttribute("wrongMsg", message);
			response.sendRedirect("secondVote/index.jsp");
		} else if (stu.getIsChange() == 0) {
			request.getSession().setAttribute("num", stu.getSecondVote());
			request.getSession().setAttribute("Student", stu);
			if (flag)
				response.sendRedirect("secondVote/changeF.jsp");
			else
				response.sendRedirect("secondVote/change.jsp");
		} else if (stu.getSecondVote() >= 10) {
			response.sendRedirect("voteSuccess2.jsp");
		} else {
			response.sendRedirect("secondVoteTeacher");
		}

	}

}
