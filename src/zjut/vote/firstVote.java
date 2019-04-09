package zjut.vote;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import zjut.vote.dao.StudentDao;
import zjut.vote.person.Msg;
import zjut.vote.person.Student;

/**
 * Servlet implementation class firstVote
 */
@WebServlet("/firstVote")
public class firstVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public firstVote() {
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
		request.setCharacterEncoding("UTF-8");
		String no=request.getParameter("no");
		String college=request.getParameter("college");
		HttpSession session=request.getSession();
		PrintWriter out=response.getWriter();
		String data=null;
		Gson gson=new Gson();
		StudentDao dao=new StudentDao();
		Msg msg=new Msg();
		Student stu=(Student)session.getAttribute("Student");
			if(!stu.getFirstVoteInCollege().equals("")&&!stu.getFirstVoteInSport().equals("")) {
				msg.setCode("100");
			}
			else if(!stu.getFirstVoteInCollege().equals("")&&!college.equals("体育军训部")) {
				msg.setCode("400");
				msg.setMessage("您已完成本学院教师投票，请继续投体军部老师");
			}
			else if(!stu.getFirstVoteInSport().equals("")&&college.equals("体育军训部")) {
				msg.setCode("400");
				msg.setMessage("您以完成体军部老师投票，请投本学院老师");
			}
			else {
				synchronized(stu) {
					stu=dao.FirstVote(stu, no, college);
				}
				session.setAttribute("Student", stu);
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//�������ڸ�ʽ
				//dao.log("1 "+stu.getNo()+" "+stu.getName()+" "+no+" "+college+" "+df.format(new Date())+"\r\n");
				msg.setStudent(stu);
				msg.setCode("200");
				if(!stu.getFirstVoteInCollege().equals("")&&!stu.getFirstVoteInSport().equals("")) {
					msg.setCode("100");
				}
			}
		data=gson.toJson(msg);
		out.println(data);
	}

}
