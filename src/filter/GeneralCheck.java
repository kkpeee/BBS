
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

@WebFilter(urlPatterns = {"/signup","/UserControl","/settings/*"})
public class GeneralCheck implements Filter{
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		User user = (User) session.getAttribute("loginUser");

		int departmentId = user.getDepartmentId();
		int branchId = user.getBranchId();
		if(departmentId != 1 && branchId != 1){
			List<String> messages = new ArrayList<String>();
			messages.add("本社総務部ではないため、ユーザー権限がありません");
			session.setAttribute("errorMessages", messages);
			((HttpServletResponse) response).sendRedirect("home");
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {}

}