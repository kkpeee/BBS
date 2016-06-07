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

import beans.Comment;
import beans.User;
import service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> comments = new ArrayList<String>();

		if (isValid(request, comments) == true) {

			User user = (User) session.getAttribute("loginUser");

			Comment comment = new Comment();
			comment.setText(request.getParameter("text"));
			comment.setContributionId(Integer.parseInt(request.getParameter("contribution_id")));
			comment.setUserId(user.getId());

			new CommentService().register(comment);

			response.sendRedirect("home");
		} else {
			session.setAttribute("errorComments", comments);
			response.sendRedirect("home");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> comments) {

		String text = request.getParameter("text");


		if (StringUtils.isBlank(text) == true) {
			comments.add("コメントを入力してください");
		}
		if (500 < text.length()) {
			comments.add("コメントは500文字以下で入力してください");
		}

		if (comments.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
