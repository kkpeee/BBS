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

import beans.User;
import beans.UserComment;
import beans.UserMessage;
import service.CommentService;
import service.MessageService;

@WebServlet(urlPatterns = { "/home" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		String category = request.getParameter("category");
		String startDate = request.getParameter("start_date");
		String endDate = request.getParameter("end_date");
		User user = (User) request.getSession().getAttribute("loginUser");

		HttpSession session = request.getSession();
		session.setAttribute("loginUser", user);

		if(user.getLocked() == 1){

			List<String> userMessages = new ArrayList<String>();
			userMessages.add("アカウントが停止されています");

			session.setAttribute("errorUserMessages", userMessages);
			response.sendRedirect("index");
		}
		// startDateがNullまたは殻の時にDBから日付を取得する
		if(startDate == null || startDate.isEmpty()) {
			String startdate = new MessageService().getStartDate();
			startDate = startdate;
		}

		if(endDate == null || endDate.isEmpty()) {
			String enddate = new MessageService().getEndDate();
			endDate = enddate;
		}

		if(((!startDate.isEmpty()) && (!endDate.isEmpty()) && startDate.compareTo(endDate) > 0)){
			String tmp = startDate;
			startDate = endDate;
			endDate = tmp;
		}

		String[] categoryDates = new String[3];
		categoryDates[0] = category;
		categoryDates[1] = startDate;
		categoryDates[2] = endDate;

		request.setAttribute("categoryDates", categoryDates);

		if (category != null && !category.isEmpty()) {
			List<UserMessage> categories = new MessageService().getMessages(categoryDates);
			request.setAttribute("categories", categories);

		}else{
			List<UserMessage> categories = new MessageService().getNoMessages(categoryDates);
			request.setAttribute("categories", categories);
		}

		boolean isShowMessageForm;
		if (user != null) {
			isShowMessageForm = true;
		} else {
			isShowMessageForm = false;
		}

		List<UserComment> comment = new CommentService().getComment();
		List<beans.Message> messageList = new MessageService().select();

		request.setAttribute("messageList", messageList);
		request.setAttribute("comments", comment);
		request.setAttribute("user", user);
		request.setAttribute("isShowMessageForm", isShowMessageForm);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

}