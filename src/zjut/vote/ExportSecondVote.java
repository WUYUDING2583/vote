package zjut.vote;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import zjut.vote.dao.AdminDao;

/**
 * Servlet implementation class ExportSecondVote
 */
@WebServlet("/ExportSecondVote")
public class ExportSecondVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportSecondVote() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/application/vnd.ms-excel");
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Content-disposition",
		    "attachment;filename=" + URLEncoder.encode("二轮投票结果.xls", "UTF-8"));
		AdminDao dao=new AdminDao();
		HSSFWorkbook excel=dao.ExportSecondVote();
        OutputStream outputStream = response.getOutputStream();
        excel.write(outputStream);
        excel.close();
        outputStream.flush();
        outputStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
