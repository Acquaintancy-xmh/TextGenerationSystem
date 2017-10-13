<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>添加图书信息</title>
<script type="text/javascript">
	function check(form) {
		if (form.price.value == "") {
			alert("定价不能为空");
			return false;
		}
		if (form.author.value == "") {
			alert("作者不能为空");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form action="addBook" method="post" onsubmit=" return check(this)">
		<ul>
			<li>ISBN：<input type="text" name="ISBN" /></li>
			<li>图书名称：<input type="text" name="title" /></li>
			<li>出版者：<input type="text" name="publisher" /></li>
			<li>价　　格：<input type="text" name="price" /></li>
			<li>出版时间：<input type="date" name="publishdate" /></li>
			<li>作　　者：<input type="text" name="authorid" /></li>
			<li><input type="submit" value="添　加"></li>
		</ul>
	</form> 
</body>
</html>