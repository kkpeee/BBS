<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/bbs/favicon.ico"">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">
	<div class="header">
		<a href="./">戻る</a><br />
	</div>

<a href="home"">
	<IMG src="http://group.baristride.co.jp/wp-content/uploads/site-logo.png"width="400" height="60">
</a>

<font color="red"><h2>◆◆ログイン◆◆</h2></font>

<c:if test="${ not empty loginMessage }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${loginMessage}" var="message">
				<font color="red">エラー： <c:out value="${message}" /></br></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="loginMessage" scope="session"/>
</c:if>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<font color="red">エラー： <c:out value="${message}" /></br></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<c:if test="${ empty errorLogin }">

	<form action="login" method="post">
		<label for="login_id">ログインID</label><br />
		<input name="login_id" id="login_id"/> <br />

		<label for="password">パスワード</label><br />
		<input name="password" type="password" id="password"/> <br /><br />

		<input type="submit" value="ログイン" /> <br />
	</form>
</c:if>

<c:if test="${ not empty errorLogin }">

	<form action="login" method="post">
		<label for="login_id">ログインID</label><br />
		<input name="login_id" value="${ errorLogin }"id="login_id"/> <br />

		<label for="password">パスワード</label><br />
		<input name="password" type="password" id="password"/> <br /><br />

		<input type="submit" value="ログイン" /> <br />
	</form>

</c:if>

<c:remove var="errorLogin" scope="session"/>
</div>
</body>
</html>
