package beans;

import java.io.Serializable;
import java.util.Date;

public class UserComment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int comments_id;
	private int contribution_id;
	private int userId;
	private int branch_id;
	private int department_id;
	private String name;
	private Date created_at;
	private String text;


	public int getContributionId() {
		return contribution_id;
	}

	public void setContributionId(int contribution_id) {
		this.contribution_id = contribution_id;

	}

	public int getCommentsId() {
		return comments_id;
	}

	public void setCommentsId(int comments_id) {
		this.comments_id = comments_id;

	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBranchId() {
		return branch_id;
	}

	public void setBranchId (int branch_id) {
		this.branch_id = branch_id;
	}

	public int getDepartmentId() {
		return department_id;
	}

	public void setDepartmentId (int department_id) {
		this.department_id = department_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}



}
