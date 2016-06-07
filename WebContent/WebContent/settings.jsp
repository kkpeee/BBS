<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${editUser.name}の設定</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">

	<div class="header">
		<a href="UserControl">戻る</a><br />
	</div>

	<a href="home">
		<IMG src="http://group.baristride.co.jp/wp-content/uploads/site-logo.png"width="400" height="60">
	</a>

	<font color="red"><h2>◆◆ユーザー設定◆◆</h2></font>

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

<form action="settings" method="post" >

	<label for="name">名前</label><br />
	<input name="name" value="${editUser.name}" id="name"/>(10文字以下) <br />

	<label for="loginId">ログインID</label><br />
	<input name="loginId" value="${editUser.getLoginId()}" id="	loginId"/>(半角英数字、6文字以上20文字以下)<br />

	<label for="password">パスワード</label><br />
	<input name="password" type="password"  id="password" > (記号を含む全ての半角文字、6文字以上255文字以下) <br />
  	<label for="passwordConfirm">パスワード（確認）</label><br />
  	<input name="passwordConfirm" type="password" id="password"/><br />


	<c:if test = "${editUser.id != loginUser.id }">

			<label for = "branch">所属支店</label><br />

			 	<select name="branch">
				<c:forEach items="${ branchList }" var="branch">
					<c:if test = "${editUser.getBranchId() == branch.id }">
						<option value="${branch.id}" selected>${ branch.name }</option>
					</c:if>
					<c:if test = "${editUser.getBranchId() != branch.id }">
						<option value="${branch.id}">${ branch.name }</option>
					</c:if>
				</c:forEach>
			</select>
	</c:if>
<br />

	<c:if test = "${editUser.id != loginUser.id }">
	<label for = "department">所属部署・役職</label><br />
			<select name="department">
				<c:forEach items="${ departmentList }" var="department">
					<c:if test = "${editUser.getDepartmentId() == department.id }">
						<option value="${ department.id }"selected>${ department.name }</option>
					</c:if>
					<c:if test = "${editUser.getDepartmentId() != department.id }">
						<option value="${ department.id }">${ department.name }</option>
					</c:if>
				</c:forEach>
			</select><br />
				</c:if>
	<input type='hidden' value="${editUser.getId()}" name='id'>
	<input type="submit" value="編集" />
	</form>
</div>
</body>
</html>
