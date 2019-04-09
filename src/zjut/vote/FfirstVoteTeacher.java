package zjut.vote;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import zjut.vote.dao.StudentDao;
import zjut.vote.person.Student;
import zjut.vote.person.Teacher;

/**
 * Servlet implementation class FfirstVoteTeacher
 */
@WebServlet("/FfirstVoteTeacher")
public class FfirstVoteTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FfirstVoteTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StudentDao dao=new StudentDao();
		HttpSession session=request.getSession();
		Student stu=(Student)session.getAttribute("Student");
		if(!stu.getFirstVoteInCollege().equals("")){
			RequestDispatcher dispatcher = request .getRequestDispatcher("/voteSuccess.jsp"); 
			dispatcher.forward(request, response);
		}
		else {
			ArrayList<Teacher> list=new ArrayList<Teacher>();
			list=dao.getFfirstVoteTeacher();
			session.setAttribute("collegeTeacehrList", list);
			response.sendRedirect("firstVote/FfirstVote.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
