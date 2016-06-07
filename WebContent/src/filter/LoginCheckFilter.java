package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns={"/home","/UserControl","/settings","/message","/signup"})
public class LoginCheckFilter implements Filter{
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		String servletPath = ((HttpServletRequest) request).getServletPath();

		if(session.getAttribute("loginUser") == null && servletPath.indexOf("login") == -1){
			List<String> loginMessage = new ArrayList<String>();
			loginMessage.add("ログインしてください");
			session.setAttribute("loginMessage", loginMessage);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {}

}