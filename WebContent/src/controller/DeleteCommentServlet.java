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

@WebServlet(urlPatterns = {"/deleteComment"})
public class DeleteCommentServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		int id = Integer.parseInt(request.getParameter("comment_id"));
		new UserService().deleteComment(id);

		LoginService loginService = new LoginService();
		List<User> userControlList =  loginService.UserCotrolList();

		request.setAttribute("userControlList", userControlList);
		response.sendRedirect("./home");
	}
}