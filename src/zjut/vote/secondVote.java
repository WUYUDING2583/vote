package zjut.vote;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import zjut.vote.dao.StudentDao;
import zjut.vote.person.Msg;
import zjut.vote.person.Student;
import zjut.vote.person.Teacher;

/**
 * Servlet implementation class secondVote
 */
@WebServlet("/secondVote")
public class secondVote extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public secondVote() {
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
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
		String no = request.getParameter("no");
		Student stu = (Student) request.getSession().getAttribute("Student");
		ArrayList<Teacher> list = (ArrayList<Teacher>) request.getSession().getAttribute("teacherList");
		int index = Integer.parseInt(request.getParameter("index"));
		ArrayList<String> done=stu.getSecondVoteNo();

		Msg msg = new Msg();
		StudentDao dao = new StudentDao();
		PrintWriter out = response.getWriter();
		stu = dao.secondVote(stu, no);
		if (stu.getSecondVote() >= 10) {
			msg.setCode("100");
		} 
		else if (stu.getSecondVote() < 10 && stu.getSecondVote() >= 0) {
			msg.setCode("200");
			list.remove(index);
			int t = stu.getSecondVote();
			msg.setNum(10 - t);
			/*boolean t1=false;
			for(int i=0;i<done.size();i++) {
				if(no.equals(done.get(i))) {
					msg.setCode("300");
					msg.setMessage("您已投过该老师");
					t1=true;
				}
			}
			if(!t1) {

				
			}*/
		} else {
			msg.setCode("400");
			msg.setMessage("系统错误");
			
		}
		Gson gson = new Gson();
		request.getSession().setAttribute("json", gson.toJson(list));
		request.getSession().setAttribute("teacherList", list);
		request.getSession().setAttribute("Student", stu);
		System.out.print(gson.toJson(msg));
		String data = gson.toJson(msg);
		out.println(data);
	}

}
