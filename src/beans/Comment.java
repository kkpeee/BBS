package beans;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int comments_id;
	private String text;
	private int userId;
	private int contribution_id;
	private Date created_at;
	private Date updated_at;



	public int getCommnetsId() {
		return comments_id;
	}

	public void setCommnetsId(int comments_id) {
		this.comments_id = comments_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getContributionId() {
		return contribution_id;
	}

	public void setContributionId(int contribution_id) {
		this.contribution_id = contribution_id;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

}