<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー登録</title>
	<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/bbs/favicon.ico"">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">
<div class="header">
	<a href="UserControl">戻る</a><br />
</div>
	<a href="home">
<IMG src="http://group.baristride.co.jp/wp-content/uploads/site-logo.png"width="400" height="60">
</a>

<font color="red"><h2>◆◆ユーザー新規登録◆◆</h2></font>
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

<c:if test="${ empty errorUser }">
	<form action="signup" method="post">

		<label for="name">名前</label><br/>
		<input name="name" id="name"/>(10文字以下) <br />

		<label for="loginId">ログインID</label><br/>
		<input name="loginId" id="	loginId"/>(半角英数字、6文字以上20文字以下)<br />

		<label for="password">パスワード</label><br/>
		<input name="password" type="password" id="password"/> (記号を含む全ての半角文字、6文字以上255文字以下) <br />
	  	<label for="passwordConfirm">パスワード（確認）</label><br/>
	  	<input name="passwordConfirm" type="password" id="password"/><br />

		<label for = "branch">所属支店</label><br/>

			 <select name="branch">
			 	<option value="0"></option>
				<c:forEach items="${ branchList }" var="branch">
					<option value="${ branch.id }">${ branch.name }</option>
				</c:forEach>
			 </select><br />


	<label for = "department">所属部署・役職</label><br/>

			<select name="department">
				<option value="0"></option>
					<c:forEach items="${ departmentList }" var="department">
						<option value="${ department.id }">${ department.name }</option>
					</c:forEach>
			</select><br /><br />


		<input type="submit" value="登録" />
	</form>
</c:if>


<c:if test="${ not empty errorUser }">
	<form action="signup" method="post">

		<label for="name">名前</label><br/>
		<input name="name" value="${ errorUser.name }"id="name"/>(10文字以下) <br />

		<label for="loginId">ログインID</label><br/>
		<input name="loginId" value="${ errorUser.getLoginId() }"id="	loginId"/>(半角英数字、6文字以上20文字以下)<br />

		<label for="password">パスワード</label><br/>
		<input name="password" type="password" id="password"/> (記号を含む全ての半角文字、6文字以上255文字以下) <br />
	  	<label for="passwordConfirm">パスワード（確認）</label><br/>
	  	<input name="passwordConfirm" type="password" id="password"/><br />

			<label for = "branch">所属支店</label><br/>

			 	<select name="branch">
			 		<option value="0"></option>
				<c:forEach items="${ branchList }" var="branch">
					<c:if test ="${branch.getId() != errorUser.getBranchId() }">
				<option value="${ branch.getId() }">${ branch.name }</option>
	</c:if>
				<c:if test ="${branch.getId() == errorUser.getBranchId() }">
					<option value="${ branch.getId() }"selected>${ branch.name }</option>
				</c:if>

				</c:forEach>
				</select><br />


	<label for = "department">所属部署・役職</label><br/>

			<select name="department">
						<option value="0"></option>
				<c:forEach items="${ departmentList }" var="department">

					<c:if test ="${department.getId() != errorUser.getDepartmentId() }">
						<option value="${ department.getId() }">${ department.name }</option>
					</c:if>

					<c:if test ="${department.getId() == errorUser.getDepartmentId() }">
						<option value="${ department.getId() }"selected>${ department.name }</option>
					</c:if>
				</c:forEach>
			</select><br /><br />


		<input type="submit" value="登録" /><br />
	</form>
</c:if>
<c:remove var="errorUser" scope="session"/>
</div>
</body>
</html>
