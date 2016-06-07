<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿画面</title>
	<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/bbs/favicon.ico"">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">
<div class="header">
	<a href="home">戻る</a><br />
</div>
<a href="home">
<IMG src="http://group.baristride.co.jp/wp-content/uploads/site-logo.png"width="400" height="60">
</a>

<font color="red"><h2>◆◆新規投稿画面◆◆</h2></font>

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

<c:if test="${ empty errorContribution }">

	<form action="message" method="post">
		<div class="contribution">
				<label for="category">カテゴリ</label><br />
				<input name="category" id="category"/>(10文字以下で入力してください) <br />

				<label for="title">件名</label><br />
				<input name="title" id="title"/>(50文字以下で入力してください) <br />

				<label for="text">本文</label><br />
				<textarea name="text" cols="35" rows="5" id="text"></textarea>(1000文字以下で入力してください) <br /> <br />
		</div>

				<input type="submit" value="登録" /> <br />
				</form>
</c:if>

<c:if test="${ not empty errorContribution }">

	<form action="message" method="post">
		<div class="contribution">
				<label for="category">カテゴリ</label><br />
				<input name="category" value="${ errorContribution.category }"id="category"/>(10文字以下で入力してください) <br />

				<label for="title">件名</label><br />
				<input name="title" value="${ errorContribution.title }"id="title"/>(50文字以下で入力してください) <br />

				<label for="text">本文</label><br />
				<textarea name="text" cols="35" rows="5" id="text">${ errorContribution.text}</textarea>(1000文字以下で入力してください)  <br /> <br />
		</div>

				<input type="submit" value="登録" /> <br />
	</form>
</c:if>

<c:remove var="errorContribution" scope="session"/>
</div>
</body>
</html>
