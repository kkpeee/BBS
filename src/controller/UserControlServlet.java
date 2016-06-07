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
import beans.branches;
import beans.departments;
import service.BranchService;
import service.DepartmentService;
import service.LoginService;

@WebServlet(urlPatterns = { "/UserControl" })
public class UserControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		List<branches> branchList = new BranchService().select();
		request.setAttribute("branchList", branchList);
		List<departments> departmentList = new DepartmentService().select();
		request.setAttribute("departmentList", departmentList);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("loginUser");

		int branchId = (user.getBranchId());
		int departmentId = (user.getDepartmentId());

		LoginService loginService = new LoginService();
		List<User> userControlList =  loginService.UserCotrolList();


		request.setAttribute("userControlList", userControlList);

//		LoginService loginService = new LoginService();
//		User usercontrol = loginService.UserCotrolCheck(branchId, departmentId);

		if (branchId == 1 && departmentId == 1) {

			request.getRequestDispatcher("userControl.jsp").forward(request, response);
//			response.sendRedirect("UserControl");
		} else {

			List<String> userMessages = new ArrayList<String>();
			userMessages.add("本社総務部ではないため、ユーザー権限がありません");

			session.setAttribute("errorUserMessages", userMessages);
			response.sendRedirect("home");
		}
	}
}
