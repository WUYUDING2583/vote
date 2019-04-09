package zjut.vote.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FirstFilter
 */
@WebFilter(
	filterName="FirstFilter",
	urlPatterns= {"/firstVote/*"},
	initParams= {
			@WebInitParam(name="encoding",value="UTF-8"),
			@WebInitParam(name="loginPage",value="/firstVote/index.jsp"),
			@WebInitParam(name="proLogin",value="/login")
	})
public class FirstFilter implements Filter {

	private FilterConfig config;
    /**
     * Default constructor. 
     */
    public FirstFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		config=null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//��ȡ�ù����������ò���
		String encoding=config.getInitParameter("encoding");
		String loginPage=config.getInitParameter("loginPage");
		String proLogin=config.getInitParameter("proLogin");
		//��������request�ı����ַ���
		request.setCharacterEncoding(encoding);
		HttpServletRequest hrequest=(HttpServletRequest)request;
		HttpSession session=hrequest.getSession(true);
		//����û�����ҳ��
		String requestPath=hrequest.getServletPath();
		//���session�������StudentΪnull����ʾû�е�½
		//����û������ҳ�治�ǵ�½ҳ��
		if(session.getAttribute("Student")==null&&!requestPath.equals(loginPage)&&!requestPath.equals(proLogin)) {
			//ת������½ҳ��
			request.setAttribute("wrongMsg", "您还没有登陆");
			request.getRequestDispatcher(loginPage).forward(request, response);
		}
		else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		config=fConfig;
	}

}
