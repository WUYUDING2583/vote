package zjut.vote.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
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
		request.setCharacterEncoding("UTF-8");
		String no=request.getParameter("no");
		String password=request.getParameter("password");
		Student stu=new Student();
		StudentDao dao=new StudentDao();
		String message=null;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//�������ڸ�ʽ
		synchronized(this) {
		stu=dao.Login(no, password);
		}
		//dao.log("0 "+stu.getNo()+" "+stu.getName()+" "+df.format(new Date())+"\r\n");
		Gson gson=new Gson();
		if(stu.getNo().equals("-1")) {
			message=stu.getName();
			request.getSession().setAttribute("wrongMsg", message);
			response.sendRedirect("firstVote/index.jsp");
		}
		else if(!stu.getFirstVoteInCollege().equals("")&&!stu.getFirstVoteInSport().equals("")) {
			request.getSession().setAttribute("Student", stu);
			response.sendRedirect("voteSuccess.jsp");
		}
		else {
			Pattern pattern=Pattern.compile("201\\w2982");
			Matcher matcher=pattern.matcher(no);
			request.getSession().setAttribute("Student", stu);
			if(no.startsWith("L")||no.startsWith("Y")||matcher.find())
				response.sendRedirect("FfirstVoteTeacher");
			else
				response.sendRedirect("firstVoteTeacher");
		}

		
	}

}
