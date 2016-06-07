package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import exception.NoRowsUpdatedRuntimeException;
import service.BranchService;
import service.DepartmentService;
import service.UserService;

@WebServlet(urlPatterns = { "/settings" })
@MultipartConfig(maxFileSize = 100000)
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


		if(request.getParameter("userId") != null){

			int userId =(Integer.parseInt(request.getParameter("userId")));

			HttpSession session = request.getSession();

			session.removeAttribute("editUser");

			if (session.getAttribute("editUser") == null) {
				User editUser = new UserService().getUser(userId);
				System.out.println(editUser);
				session.setAttribute("editUser", editUser);
			}

			List<branches> branchList = new BranchService().select();
			request.setAttribute("branchList", branchList);
			List<departments> departmentList = new DepartmentService().select();
			request.setAttribute("departmentList", departmentList);

			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}

		List<branches> branchList = new BranchService().select();
		request.setAttribute("branchList", branchList);
		List<departments> departmentList = new DepartmentService().select();
		request.setAttribute("departmentList", departmentList);

		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();
		boolean passwordModify = false;

		User editUser = (User) session.getAttribute("editUser");

		editUser.setName(request.getParameter("name"));
		editUser.setLoginId(request.getParameter("loginId"));

			if(isModifyPassword(request.getParameter("password"), request.getParameter("passwordConfirm"))){
			editUser.setPassword(request.getParameter("password"));
			passwordModify = true;
			}

		editUser.setBranchId(Integer.parseInt(request.getParameter("branch")));
		editUser.setDepartmentId(Integer.parseInt(request.getParameter("department")));
		editUser.setId(Integer.parseInt(request.getParameter("id")));
		session.setAttribute("editUser", editUser);

		if (isValid(request, messages, passwordModify) == true) {
			List<String> completion = new ArrayList<String>();

			try {
				new UserService().update(editUser,passwordModify);
			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("settings");
			}

			session.setAttribute("editUser", editUser);
//			session.removeAttribute("editUser");

			completion.add("ユーザーの編集が完了しました");
			session.setAttribute("completion", completion);

			response.sendRedirect("UserControl");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("settings");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages,boolean passwordModify) {


		 HttpSession session = request.getSession();

		 User editUser = (User) session.getAttribute("editUser");

		 String login_id = request.getParameter("loginId");
		 String password = request.getParameter("password");
		 String password_Confirm = request.getParameter("passwordConfirm");
		 String name = request.getParameter("name");

		if(StringUtils.isBlank(name) == true){
			messages.add("名前を入力してください");
		}
		if(name.length() > 10){
			messages.add("名前は10文字以内で入力してください");
		}

		if(StringUtils.isBlank(login_id) == true){
			messages.add("ログインIDを入力してください");

		} else if(!(new UserService().getLoginId(editUser.getId()).equals(login_id) && UserDao.isExist(login_id))){
			messages.add("ログインIDが既に使われています");

		} else if(!login_id.matches("^[0-9a-zA-Z]{6,20}")){

			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");
		}
		if(passwordModify){
			if(StringUtils.isBlank(password) == true
					|| StringUtils.isEmpty(password_Confirm) == true){
				messages.add("パスワードを入力してください");
			}

			if( password.length() < 6){
				messages.add("パスワードは6文字以上で入力してください");
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
		}
			if(messages.size() == 0){
				return true;
			}else{
				return false;
			}

	}

	private boolean isModifyPassword(String password, String password_Confirm){
		if(StringUtils.isBlank(password) && StringUtils.isBlank(password_Confirm)){
			return false;
		}else{
			return true;
		}
	}
}


