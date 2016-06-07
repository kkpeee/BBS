package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.departments;
import exception.SQLRuntimeException;

public class DepartmentDao {

	public List<departments> getDepartmentList(Connection connection) {

		String sql = "select * from departments";

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<departments> departmentList = toDepartmentList(rs);
			if (departmentList.isEmpty() == true) {
				return null;
			} else {
				return departmentList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	private List<departments> toDepartmentList(ResultSet rs) throws SQLException {

		List<departments> departments = new ArrayList<departments>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				departments department = new departments();
				department.setId(id);
				department.setName(name);

				departments.add(department);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();


		}
		return departments;

	}
}