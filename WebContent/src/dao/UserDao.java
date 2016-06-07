package dao;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import exception.NoRowsUpdatedRuntimeException;
import exception.SQLRuntimeException;

public class UserDao {

	public User getUser(Connection connection, String login_id,
			String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user WHERE login_id = ? AND password = ? AND locked = 0";

			ps = connection.prepareStatement(sql);
			ps.setString(1, login_id);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String login_id = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				int locked = rs.getInt("locked");
				Timestamp insertDate = rs.getTimestamp("created_at");
				Timestamp updateDate = rs.getTimestamp("updated_at");

				User user = new User();
				user.setId(id);
				user.setLoginId(login_id);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setlocked(locked);
				user.setCreated_at(insertDate);
				user.setUpdated_at(updateDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private List<User> toUsersList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String login_id = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				int locked = rs.getInt("locked");
				Timestamp insertDate = rs.getTimestamp("created_at");
				Timestamp updateDate = rs.getTimestamp("updated_at");

				User users = new User();
				users.setId(id);
				users.setLoginId(login_id);
				users.setPassword(password);
				users.setName(name);
				users.setBranchId(branchId);
				users.setDepartmentId(departmentId);
				users.setlocked(locked);
				users.setCreated_at(insertDate);
				users.setUpdated_at(updateDate);

				ret.add(users);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO user ( ");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", locked");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append("?"); // login_id
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // department_id
			sql.append(", 0"); // locked
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(", CURRENT_TIMESTAMP"); // updated_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getDepartmentId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public static boolean isExist(String loginId) {
		PreparedStatement ps = null;
		Connection connection = getConnection();
		int result = 0;
		try{
			String sql = "select count(login_id) as result from user where login_id = ?;";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getInt("result");
			if(ps != null){
				ps.close();
			}
		}catch(Exception e){
		}
		if(result == 1){
			return true;
		}
		return false;
	}

	public String getLoginId(Connection connection, int id) {
		PreparedStatement ps = null;
		String loginId = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select login_id from user where id = ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			loginId = rs.getString("login_id");

		}catch(Exception e){
			e.printStackTrace();
		}
		return loginId;
	}

	public User getUserControl(Connection connection, int branchId,
			int departmentId) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user WHERE branch_id = ? AND department_id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, branchId);
			ps.setInt(2, departmentId);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
//			} else if (2 <= userList.size()) {
//				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getUserControlList(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM bbs.user");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUsersList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getUserList(Connection connection) {
		PreparedStatement ps = null;
		List<User> userList = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("order by branch_id, department_id, id;");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			userList = toUserList(rs);
			if(userList.isEmpty()){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}
		}catch(Exception e){

		}finally{
			close(ps);
		}
		return userList;
	}

	public void update(Connection connection, User user, boolean passwordModify) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			if(passwordModify == true){
				sql.append("UPDATE bbs.user SET");
				sql.append(" login_id = ?");
				sql.append(", password= ?");
				sql.append(", name= ?");
				sql.append(", branch_id= ?");
				sql.append(", department_id= ?");
				sql.append(" WHERE");
				sql.append(" id = ?");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLoginId());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getName());
				ps.setInt(4, user.getBranchId());
				ps.setInt(5, user.getDepartmentId());
				ps.setInt(6, user.getId());
			}else{
				sql.append("UPDATE bbs.user SET");
				sql.append(" login_id = ?");
				sql.append(", name= ?");
				sql.append(", branch_id= ?");
				sql.append(", department_id= ?");
				sql.append(" WHERE");
				sql.append(" id = ?");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLoginId());
				ps.setString(2, user.getName());
				ps.setInt(3, user.getBranchId());
				ps.setInt(4, user.getDepartmentId());
				ps.setInt(5, user.getId());
			}
			System.out.println(ps.toString());

			int count = ps.executeUpdate();

			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM bbs.user WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public int getLocked(Connection connection, int id){
		PreparedStatement ps = null;
		int ret = 0;
		try{
			String sql = "select locked from bbs.user where id = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			rs.next();
			ret = rs.getInt("locked");

		}catch(Exception e){

		}finally{
			close(ps);
		}
		return ret;
	}

	public void deleteUser(Connection connection, int id){
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("delete from bbs.user where id = ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			if((ps.executeUpdate()) == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void deleteContribution(Connection connection, int id){
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("delete from bbs.contributions where contributions.contribution_id = ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			if((ps.executeUpdate()) == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void deleteComment(Connection connection, int id){
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("delete from bbs.comments where comments.comments_id = ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			if((ps.executeUpdate()) == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}


	public void changeLocked(Connection connection, int id,int locked){
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("update bbs.user set locked = ? where id = ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, locked);
			ps.setInt(2, id);
			if((ps.executeUpdate()) == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

}
