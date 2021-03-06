package controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import service.LoginService;
import service.UserService;

@WebServlet(urlPatterns = {"/deleteMessage"})
public class DeleteMessageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		int id = Integer.parseInt(request.getParameter("contribution_id"));
		new UserService().deleteContribution(id);

		LoginService loginService = new LoginService();
		List<User> userControlList =  loginService.UserCotrolList();

		request.setAttribute("userControlList", userControlList);
		response.sendRedirect("./home");
	}
}