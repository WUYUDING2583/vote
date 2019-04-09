package zjut.vote.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class LogFilter
 */
@WebFilter(filterName="logFilter",urlPatterns= {"/*"})
public class LogFilter implements Filter {
	
	private FilterConfig config;

    /**
     * Default constructor. 
     */
    public LogFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		this.config=null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//��������Ķ���
		ServletContext context=config.getServletContext();
		//�����������
		HttpServletRequest hrequest=(HttpServletRequest)request;
		//��¼��ʼ����ʱ��
		long start=System.currentTimeMillis();
		System.out.println("用户地址:"+hrequest.getRemoteAddr());
		System.out.println("用户请求URI:"+hrequest.getRequestURI());
		context.log("用户地址:"+hrequest.getRequestURI());
		context.log("用户请求URI:"+hrequest.getRemoteAddr());
		//����ת����һ��Դ����һ������
		chain.doFilter(request, response);
		//��¼���ص���������ʱ��
		long end=System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");
		context.log("耗时:"+(end-start)+"毫秒");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.config=fConfig;
	}

}
