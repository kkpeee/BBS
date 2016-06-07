<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/i18n/jquery-ui-i18n.min.js"></script>
<script>

$(document).ready(function () {
	  hsize = $(window).height();
	  $("section").css("height", hsize + "px");
	});
	$(window).resize(function () {
	  hsize = $(window).height();
	  $("section").css("height", hsize + "px");
	});

$(function() {
    $.datepicker.setDefaults( $.datepicker.regional[ "ja" ] );
    $('.calendar').datepicker({
        minDate: '-5y', //今日から
        maxDate: '+5y', //5年後までが選択可能範囲
        changeYear: true, //表示年の指定が可能
        changeMonth: true, //表示月の指定が可能
        dateFormat: 'yy-mm-dd(D)' //年-月-日(曜日)
    });
});

</script>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ホーム画面</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">

<LINK REL="SHORTCUT ICON" HREF="http://localhost:8080/bbs/favicon.ico">
</head>
<body>
<div class="main-contents">

<a href="home"">
	<IMG src="http://group.baristride.co.jp/wp-content/uploads/site-logo.png"width="400" height="60">
</a>

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

<c:if test="${ not empty errorComments }">
	<div class="errorComments">
		<ul>
			<c:forEach items="${errorComments}" var="comments">
				<font color="red">エラー： <c:out value="${comments}" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorComments" scope="session"/>
</c:if>


	<p>		<div class="contributions">
		<span class="test3"><a href="message">新規投稿画面</a></span>
		<c:if test = "${user.getBranchId() == 1 && user.getDepartmentId() == 1 }">
		<span class="test3"><a href="UserControl">ユーザー管理画面</a></span>
		</c:if>
		<span class="test3"><a href="logout">ログアウト</a><br/></span>
	</p></div>

<form action="home" method="get">
	<table class="message_select">
		<tr>
			<td>カテゴリー</td>
			<td><select name="category">
			<option value=""></option>
				<c:forEach items="${ messageList }" var="message">
					<c:if test ="${message.category == categoryDates[0] }">
						<option value="${ message.category }"selected>${ message.category }</option>
					</c:if>

					<c:if test ="${message.category != categoryDates[0] }">
						<option value="${ message.category }">${ message.category }</option>
					</c:if>
				</c:forEach>
			</select></td>
		</tr>

		<tr>
			<td>投稿日</td><td>
			<input type="date" class="calendar" type="date" name="start_date" min="2000-01-01" value=${categoryDates[1] }> ～
			<input class="calendar" type="date" name="end_date" min="2000-01-01" value=${categoryDates[2] }></td>
			<td class="submit"><input type="submit" value="絞り込み"><input type="reset" value="リセット"></td>
		</tr>
	</table>
</form><br/>

<font color="red"><div class="sred">◆◆投稿一覧◆◆</div></font><br/>
<c:forEach items="${categories}" var="message">
			名前：<font color="green"><span class="sample800"><c:out value="${message.name}" /></span></font>
					<span class="date">：<fmt:formatDate value="${message.created_at}" pattern="yyyy/MM/dd(E) HH:mm:ss" /></span>

					<c:if test = "${user.getBranchId() == 1 && user.getDepartmentId() == 2 }">
						<form action = "deleteMessage" method = "post" style = "display:inline">
							<button type = 'submit' name = 'contribution_id' value = "${message.getContributionId()}" onClick = "return confirm('この投稿を削除します。よろしいですか？')">削除</button></td>
						</form>
					</c:if>

					<c:if test = "${user.getBranchId() == message.getBranchId() && message.getDepartmentId() > user.getDepartmentId() }">
						<form action = "deleteMessage" method = "post" style = "display:inline">
							<button type = 'submit' name = 'contribution_id' value = "${message.getContributionId()}" onClick = "return confirm('この投稿を削除します。よろしいですか？')">削除</button></td>
						</form>
					</c:if>

		<div class="contributions">
			<div class="namecategory">カテゴリ：<c:out value="${message.category}" /></div>
			<div class="namecategory">件名：<c:out value="${message.title}" /></div>
			<div class="namecategory">本文：<c:out value="${message.text}" /></div><br/>

			<input type='hidden' value="${message.getContributionId()}" name='contribution_id'>
			◆◆コメント一覧◆◆<br />

			<c:forEach items="${comments}" var="comment">
				<c:if test = "${message.getContributionId() == comment.getContributionId() }">
					名前：<font color="blue"><span class="sample800"><c:out value="${comment.getName()}" /></span></font>
					<span class="date">:<fmt:formatDate value="${comment.getCreated_at()}" pattern="yyyy/MM/dd(E) HH:mm:ss" /></span>

					<c:if test = "${user.getBranchId() == 1 && user.getDepartmentId() == 2 }">
						<form action = "deleteComment" method = "post" style = "display:inline">
							<button type = 'submit' name = 'comment_id' value = "${comment.getCommentsId()}" onClick = "return confirm('このコメントを削除します。よろしいですか？')">削除</button></td>
						</form>
					</c:if>

					<c:if test = "${user.getBranchId() == comment.getBranchId() && comment.getDepartmentId() > 3 }">
						<form action = "deleteComment" method = "post" style = "display:inline">
							<button type = 'submit' name = 'comment_id' value = "${comment.getCommentsId()}" onClick = "return confirm('このコメントを削除します。よろしいですか？')">削除</button></td>
						</form>
					</c:if>

				<div class="contributions">
						<span class="text">コメント：<c:out value="${comment.getText()}" /></span>
				</div><br/>
				</c:if>
			</c:forEach><br />
		<form action="	comment" method="post">
			<input type='hidden' value="${message.getContributionId()}" name='contribution_id'>
			【 コメントをする 】<br />
			<textarea name="text" cols="50" rows="4" class="tweet-box"></textarea><br />
			<input type="submit" value="コメント">（500文字まで）<br/>
		</form><br/><br />
		</div>
</c:forEach>

<center><p>
<b>BSG掲示板システム</b>
</p></center>

</div>
</body>
</html>