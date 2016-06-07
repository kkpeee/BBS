package service;


import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.branches;
import dao.BranchDao;

public class BranchService {

	public List<branches> select() {
		Connection connection = null;
		try {
			connection = getConnection();
			BranchDao branchDao = new BranchDao();
			List<branches> branch = branchDao.getBranchList(connection);

			commit(connection);
			return branch;
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