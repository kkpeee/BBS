package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Message;
import exception.SQLRuntimeException;

public class MessageDao {

	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO contributions ( ");
			sql.append("title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", user_id");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(") VALUES (");
			sql.append("?"); // title
			sql.append(", ?"); // text
			sql.append(", ?"); // category
			sql.append(", ?"); // user_id
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getTitle());
			ps.setString(2, message.getText());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getUserId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public static List<Message> getMessageList(Connection connection) {

		String sql = "select * from contributions";

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Message> messageList = toMessageList(rs);
			if (messageList.isEmpty() == true) {
				return null;
			} else {
				return messageList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	private static List<Message> toMessageList(ResultSet rs) throws SQLException {

		List<Message> messageList = new ArrayList<Message>();
		try {
			while (rs.next()) {
				String category = rs.getString("category");
				Timestamp created_at = rs.getTimestamp("created_at");

				Message message = new Message();
				message.setCategory(category);
				message.setCreated_at(created_at);

				messageList.add(message);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();


		}
		return messageList;


	}

}
