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
 * Servlet implementation class firstVoteTeacher
 */
@WebServlet("/firstVoteTeacher")
public class firstVoteTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public firstVoteTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StudentDao dao=new StudentDao();
		HttpSession session=request.getSession();
		Student stu=(Student)session.getAttribute("Student");
		if(!stu.getFirstVoteInCollege().equals("")&&!stu.getFirstVoteInSport().equals("")){
			RequestDispatcher dispatcher = request .getRequestDispatcher("/voteSuccess.jsp"); 
			dispatcher.forward(request, response);
		}
		else {
			ArrayList<Teacher> list1=new ArrayList<Teacher>();
			ArrayList<Teacher> list2=new ArrayList<Teacher>();
			list1=dao.getFirstVoteTeacherInCollege(stu.getCollege());
			list2=dao.getFirstVoteTeacherInSport();
			session.setAttribute("collegeTeacehrList", list1);
			session.setAttribute("sportList", list2);
			response.sendRedirect("firstVote/firstVote.jsp");
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
