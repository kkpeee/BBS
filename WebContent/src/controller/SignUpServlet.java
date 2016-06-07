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
import beans.branches;
import beans.departments;
import dao.UserDao;
import service.BranchService;
import service.DepartmentService;
import service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
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

		if (branchId == 1 || departmentId == 1) {

			request.getRequestDispatcher("signup.jsp").forward(request, response);

		} else {

			List<String> messages = new ArrayList<String>();
			messages.add("本社総務部ではないため、ユーザー権限がありません");

			session.setAttribute("errorMessages", messages);
			response.sendRedirect("home");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		List<String> completion = new ArrayList<String>();

		HttpSession session = request.getSession();
		if (isValid(request, messages) == true) {

			User user = new User();
			user.setName(request.getParameter("name"));
			user.setLoginId(request.getParameter("loginId"));
			user.setPassword(request.getParameter("password"));
			user.setBranchId(Integer.parseInt(request.getParameter("branch")));
			user.setDepartmentId(Integer.parseInt(request.getParameter("department")));

			new UserService().register(user);

			completion.add("ユーザーの登録が完了しました");
			session.setAttribute("completion", completion);

			response.sendRedirect("UserControl");
		} else {

			User errorUser = new User();
			errorUser.setName(request.getParameter("name"));
			errorUser.setLoginId(request.getParameter("loginId"));
			errorUser.setPassword(request.getParameter("password"));
			errorUser.setBranchId(Integer.parseInt(request.getParameter("branch")));
			errorUser.setDepartmentId(Integer.parseInt(request.getParameter("department")));

			session.setAttribute("errorUser", errorUser);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		 String login_id = request.getParameter("loginId");
		 String password = request.getParameter("password");
		 String password_Confirm = request.getParameter("passwordConfirm");
		 int branch =(Integer.parseInt(request.getParameter("branch")));
		 int department =(Integer.parseInt(request.getParameter("department")));
		 String name = request.getParameter("name");

		if(StringUtils.isBlank(name) == true){
			messages.add("名前を入力してください");
		}
		if(name.length() > 10){
			messages.add("名前は10文字以内で入力してください");
		}

		if(StringUtils.isBlank(login_id) == true){
			messages.add("ログインIDを入力してください");

		} else if(UserDao.isExist(request.getParameter("loginId"))){
			messages.add("ログインIDが既に使われています");

		} else if(!login_id.matches("^[0-9a-zA-Z]{6,20}")){

			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");
		}

		if(StringUtils.isBlank(password) == true
				|| StringUtils.isBlank(password_Confirm) == true){
			messages.add("パスワードを入力してください");
		}

		if( password.length() > 255){
			messages.add("パスワードは255文字以内で入力してください");
		}


		if(password.getBytes().length > password.length()
				|| password_Confirm.getBytes().length > password_Confirm.length()
				|| password.matches("{6,255}")|| password_Confirm.matches("{6,255}")){
			messages.add("パスワードは半角文字6文字以上255文字以下で入力してください");
		} else if(!password.equals(password_Confirm)){
			messages.add("入力されたパスワードが一致しません");
		}

		if(branch == 0){
			messages.add("所属支店を選択してください");
		}

		if(department == 0){
			messages.add("所属部署・役職を選択してください");
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}

}
