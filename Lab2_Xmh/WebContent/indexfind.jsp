<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>图书查询系统</title>
</head>
<body>
	<center>
		<h1>图书查询系统</h1>
	    <form action="findBook" method = "post">
      <input type="text" name="Author"/><br>
      <input type="submit" value="查询图书"/>
<!--       <input type="submit" value="修改图书"/> -->
   </form>
   </center>
</body>

</html>