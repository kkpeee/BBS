<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理</title>
	<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/bbs/favicon.ico"">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">

<div class="header">
	<a href="home">戻る</a><br />
</div>
<center>
	<a href="home"">
		<IMG src="http://group.baristride.co.jp/wp-content/uploads/site-logo.png"width="400" height="60">
	</a>
</center>
<center><font color="red"><h2>◆◆ユーザー管理◆◆</h2></div></font></center>

		<center><a href="signup">ユーザー新規登録</a></center><br/>

<c:if test="${ not empty completion }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${completion}" var="completion">
				<font color="red">成功：<c:out value="${completion}" /></br></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="completion" scope="session"/>
</c:if>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<font color="red">エラー：<c:out value="${message}" /></br></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

	<c:remove var="editUser" scope="session"/>

<div class="UsercontrolList">
	<c:forEach items="${userControlList}" var="user">
			<div class="setting">
				<div class="account-name">
					<span class="Login_id">ID：<c:out value="${user.getLoginId()}" /></span><br/>
					<span class="name">名前：<c:out value="${user.name}" /></span><br/>


					<c:forEach items="${ branchList }" var="branch">
						<c:if test = "${user.getBranchId() == branch.id }">
							<span class="name">支店：<c:out value="${branch.name}" /></span><br/>
						</c:if>
					</c:forEach>

					<c:forEach items="${ departmentList }" var="department">
						<c:if test = "${user.getDepartmentId() == department.id }">
							<span class="name">所属：<c:out value="${department.name}" /></span><br/>
						</c:if>
					</c:forEach>
				</div>

				<c:if test = "${user.id != loginUser.id }">
					<form action = "changeLocked" method = "post" style = "display:inline">
							<c:if test = "${user.locked == 0 }">
								<input type='hidden' value="1" name='locked'>
								<td><button type = 'submit' name = 'id' value = "${user.id }" onClick = "return confirm('このユーザを稼動します。よろしいですか？')">停止</button></td>
							</c:if>

							<c:if test = "${user.locked == 1 }">
								<input type='hidden' value="0" name='locked'>
								<td><button type = 'submit' name = 'id' value = "${user.id }" onClick = "return confirm('このユーザを停止します。よろしいですか？')">稼動</button></td>
							</c:if>
					</form>
				</c:if>
					<c:if test = "${user.id != loginUser.id }">
						<form action = "deleteUser" method = "post" style = "display:inline">
							<td><button type = 'submit' name = 'id' value = "${user.id }" onClick = "return confirm('このユーザを削除します。よろしいですか？')">削除</button></td>
						</form>
					</c:if>
					<a href="settings?userId=${user.getId()}">設定</a><br/>
			</div>
	</c:forEach>
</div>
</body>
</html>
