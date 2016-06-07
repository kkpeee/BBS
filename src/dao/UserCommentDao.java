package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.UserComment;
import exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> getUserComments(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_comments ");
			sql.append("ORDER BY created_at ASC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs)
			throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				int comments_id = rs.getInt("comments_id");
				int contribution_id = rs.getInt("contribution_id");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				String name = rs.getString("name");
				String text = rs.getString("text");
				Timestamp created_at = rs.getTimestamp("created_at");

				UserComment comment = new UserComment();
				comment.setCommentsId(comments_id);
				comment.setUserId(userId);
				comment.setContributionId(contribution_id);
				comment.setBranchId(branchId);
				comment.setDepartmentId(departmentId);
				comment.setName(name);
				comment.setText(text);
				comment.setCreated_at(created_at);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
