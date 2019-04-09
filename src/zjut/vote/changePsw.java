package zjut.vote;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zjut.vote.dao.StudentDao;
import zjut.vote.person.Student;

/**
 * Servlet implementation class changePsw
 */
@WebServlet("/changePsw")
public class changePsw extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public changePsw() {
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
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		Student stu = new Student();
		stu = (Student) request.getSession().getAttribute("Student");
		String message = null;
		boolean flag = false;
		Pattern pattern = Pattern.compile("201\\w2982");
		Matcher matcher = pattern.matcher(stu.getNo());
		if (stu.getNo().startsWith("L") || stu.getNo().startsWith("Y") || matcher.find())
			flag = true;
		if (name.equals(stu.getName())) {
			StudentDao dao = new StudentDao();
			if (dao.changePsw(password, stu.getNo())) {
					response.sendRedirect("secondVoteTeacher");
			}
			else {
				if (flag)
					message = "SOMETHING WRONG IN SYSTEM";
				else
					message = "系统错误";
				request.getSession().setAttribute("wrongMsg", message);
				if (flag)
					response.sendRedirect("secondVote/changeF.jsp");
				else
					response.sendRedirect("secondVote/change.jsp");
			}
		} else {
			if (flag)
				message = "Your name dose not match your no,please input again.";
			else
				message = "姓名与学号不匹配，请重新输入";
			request.getSession().setAttribute("wrongMsg", message);
			if (flag)
				response.sendRedirect("secondVote/changeF.jsp");
			else
				response.sendRedirect("secondVote/change.jsp");
		}

	}

}
