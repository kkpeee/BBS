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

import beans.Message;
import beans.User;
import service.MessageService;

@WebServlet(urlPatterns = { "/message" })
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();
		List<String> completion = new ArrayList<String>();

		if (isValid(request, messages) == true) {

			User user = (User) session.getAttribute("loginUser");

			Message message = new Message();
			message.setTitle(request.getParameter("title"));
			message.setText(request.getParameter("text"));
			message.setCategory(request.getParameter("category"));
			message.setUserId(user.getId());

			new MessageService().register(message);


			completion.add("新規投稿が完了しました");
			session.setAttribute("completion", completion);

			response.sendRedirect("home");
		} else {

			User user = (User) session.getAttribute("loginUser");

			Message errorContribution = new Message();
			errorContribution.setTitle(request.getParameter("title"));
			errorContribution.setText(request.getParameter("text"));
			errorContribution.setCategory(request.getParameter("category"));
			errorContribution.setUserId(user.getId());

			session.setAttribute("errorContribution", errorContribution);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("message");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if (StringUtils.isBlank(title) == true) {
			messages.add("件名を入力してください");
		}

		if (50 < title.length()) {
			messages.add("件名は50文字以下で入力してください");
		}


		if (StringUtils.isBlank(category) == true) {
			messages.add("カテゴリを入力してください");
		}

		if (10 < category.length()) {
			messages.add("カテゴリは10文字以下で入力してください");
		}

		if (StringUtils.isBlank(text) == true) {
			messages.add("本文を入力してください");
		}

		if (1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}


		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
