package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.branches;
import exception.SQLRuntimeException;

public class BranchDao {

	public List<branches> getBranchList(Connection connection) {

		String sql = "select * from branches";

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<branches> branchList = toBranchList(rs);
			if (branchList.isEmpty() == true) {
				return null;
			} else {
				return branchList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	private List<branches> toBranchList(ResultSet rs) throws SQLException {

		List<branches> branchList = new ArrayList<branches>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				branches branch = new branches();
				branch.setId(id);
				branch.setName(name);

				branchList.add(branch);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();


		}
		return branchList;


	}
}
