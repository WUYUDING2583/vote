package zjut.vote.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import zjut.vote.person.Admin;

/**
 * Servlet implementation class adminIn
 */
@WebServlet("/adminIn")
public class adminIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminIn() {
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
		HttpSession session=request.getSession();
		String message=null;
		if("zjut".equals(no)) {
			if("zjut".equals(password)) {
				Admin admin=new Admin();
				admin.setNo(no);
				request.getSession().setAttribute("Admin", admin);
				response.sendRedirect("Admin/admin.jsp");
			}
			else {
				message="密码错误";
				session.setAttribute("wrongMsg", message);
				response.sendRedirect("Admin/index.jsp");
			}
		}
		else {
			message="用户名错误";
			session.setAttribute("wrongMsg", message);
			response.sendRedirect("Admin/index.jsp");
		}
	}

}
