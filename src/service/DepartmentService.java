package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.departments;
import dao.DepartmentDao;

public class DepartmentService {

	public List<departments> select() {
		Connection connection = null;
		try {
			connection = getConnection();
			DepartmentDao departmentDao = new DepartmentDao();
			List<departments> department = departmentDao.getDepartmentList(connection);

			commit(connection);
			return department;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}