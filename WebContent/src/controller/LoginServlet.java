package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import beans.User;
import service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(login_id, password);

		HttpSession session = request.getSession();

			if (user != null) {

				session.setAttribute("loginUser", user);
				response.sendRedirect("home");
			} else {

				User errorLogin = new User();
				errorLogin.setLoginId(request.getParameter("login_id"));

				List<String> messages = new ArrayList<String>();
				messages.add("ログインに失敗しました");

				if(StringUtils.isBlank(login_id)){
					messages.add("ログインIDを入力してください");
				}

				if(StringUtils.isBlank(password)){
					messages.add("パスワードを入力してください");
				}

				session.setAttribute("errorLogin",login_id );
				System.out.println(errorLogin.getLoginId());
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("login");
			}
	}

}
