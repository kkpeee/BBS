<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>トップ画面</title>
	<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/bbs/favicon.ico"">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">


<c:if test="${ not empty errorUserMessages }">
	<div class="errorUserMessages">
		<ul>
			<c:forEach items="${errorUserMessages}" var="userMessages">
				<font color="red">エラー： <c:out value="${userMessages}" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorUserMessages" scope="session"/>
</c:if>

<div class="header">
	<c:if test="${ empty loginUser }">
		<a href="login">ログイン</a>
	</c:if>
	<c:if test="${ not empty loginUser }">

		<a href="home">ホーム</a>
		<a href="logout">ログアウト</a><br/><br/>
	</c:if>
</div>

<center><a href="./">
<IMG src="http://group.baristride.co.jp/wp-content/uploads/site-logo.png"width="400" height="60">
</a></center>

<c:if test="${ not empty errorUserMessages }">
	<div class="errorUserMessages">
		<ul>
			<c:forEach items="${errorUserMessages}" var="userMessages">
				<font color="red">エラー： <c:out value="${userMessages}" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorUserMessages" scope="session"/>
</c:if>

<center><h1>掲示板システム</h1></center>

<center><IMG src="https://scontent-nrt1-1.xx.fbcdn.net/v/t1.0-9/13062518_1023627077673979_
5554755474087758378_n.jpg?oh=732f28069c0a73bb9b416c2e8595a310&oe=580F5926"width="700" height="450"></center><br/><br/>

</div>
</body>
</html>