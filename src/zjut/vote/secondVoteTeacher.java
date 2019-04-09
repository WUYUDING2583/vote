package zjut.vote;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import zjut.vote.dao.StudentDao;
import zjut.vote.person.Student;
import zjut.vote.person.Teacher;

/**
 * Servlet implementation class secondVoteTeacher
 */
@WebServlet("/secondVoteTeacher")
public class secondVoteTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public secondVoteTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Teacher> list=new ArrayList<Teacher>();
		StudentDao dao=new StudentDao();
		Student stu=(Student)request.getSession().getAttribute("Student");
		list=dao.getSecondVoteTeacher(stu);
		System.out.println(list.size());
		Gson gson=new Gson();
		String data=gson.toJson(list);
		request.getSession().setAttribute("json", data);
		request.getSession().setAttribute("teacherList", list);
		response.sendRedirect("secondVote/secondVote.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
