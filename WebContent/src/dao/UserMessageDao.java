package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beans.UserMessage;
import exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_contributions ");
			sql.append("ORDER BY created_at ASC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs)
			throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {{}
			while (rs.next()) {
				int contribution_id = rs.getInt("contribution_id");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp created_at = rs.getTimestamp("created_at");

				UserMessage message = new UserMessage();
				message.setContributionId(contribution_id);
				message.setName(name);
				message.setUserId(userId);
				message.setBranchId(branchId);
				message.setDepartmentId(departmentId);
				message.setTitle(title);
				message.setCategory(category);
				message.setText(text);
				message.setCreated_at(created_at);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<UserMessage> getMessageCategory(Connection connection, int num,String[] categoryDates) {

		PreparedStatement ps = null;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_contributions ");
			sql.append("where category = ?  ");
			sql.append("and created_at >= ?  ");
			sql.append("and created_at <= ?  ");
			sql.append("ORDER BY created_at ASC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1,categoryDates[0]);
			ps.setString(2,categoryDates[1]);
			ps.setString(3,categoryDates[2]);


			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

		public List<UserMessage> getNoMessageCategory(Connection connection, int num,String[] categoryDates) {

			PreparedStatement ps = null;

			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM user_contributions ");
				sql.append("where created_at >= ?  ");
				sql.append("and created_at <= ?  ");
				sql.append("ORDER BY created_at ASC limit " + num);

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1,categoryDates[1]);
				ps.setString(2,categoryDates[2]);


				ResultSet rs = ps.executeQuery();
				List<UserMessage> ret = toUserMessageList(rs);
				return ret;
			} catch (SQLException e) {
				throw new SQLRuntimeException(e);
			} finally {
				close(ps);
			}
		}

		public String getStartDate(Connection connection, int num) {

			PreparedStatement ps = null;
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT created_at FROM contributions ");
				sql.append("ORDER BY created_at ASC limit " + num);

				ps = connection.prepareStatement(sql.toString());

				ResultSet rs = ps.executeQuery();
				List<UserMessage> ret = toDateList(rs);

				return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(ret.get(0).getCreated_at());

			} catch (SQLException e) {
				throw new SQLRuntimeException(e);
			} finally {
				close(ps);
			}
		}

		public String getEndDate(Connection connection, int num) {

			PreparedStatement ps = null;
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT created_at FROM contributions ");
				sql.append("ORDER BY created_at DESC limit " + num);

				ps = connection.prepareStatement(sql.toString());

				ResultSet rs = ps.executeQuery();
				List<UserMessage> ret = toDateList(rs);

				return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(ret.get(0).getCreated_at());

			} catch (SQLException e) {
				throw new SQLRuntimeException(e);
			} finally {
				close(ps);
			}
		}

		private List<UserMessage> toDateList(ResultSet rs)
				throws SQLException {

			List<UserMessage> ret = new ArrayList<UserMessage>();
			try {{}
				while (rs.next()) {
					Timestamp created_at = rs.getTimestamp("created_at");

					UserMessage message = new UserMessage();
					message.setCreated_at(created_at);

					ret.add(message);
				}
				return ret;
			} finally {
				close(rs);
			}
		}
}
