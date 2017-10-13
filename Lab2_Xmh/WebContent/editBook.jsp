<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="action.BookBean" %>
<%@ page import="action.UserAction" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
<title>修改图书信息</title>

<script type="text/javascript">
	function check(form) {
		if (form.name.value == "") {
			alert("图书名称不能为空");
			return false;
		}
		if (form.ISBN.value == "") {
			alert("ISBN不能为空");
			return false;
		}
		if (form.title.value == "") {
			alert("图书名称不能为空");
			return false;
		}
		if (form.author.value == "") {
			alert("作者名称不能为空");
			return false;
		}
		if (form.price.value == "") {
			alert("价格不能为空");
			return false;
		}
		if (form.publisher.value == "") {
			alert("出版者不能为空");
			return false;
		}
		if (form.publishdate.value == "") {
			alert("出版时间不能为空");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span2">
		</div>
		<div class="span6">
			<div class="page-header">
				<h1>
					修改图书信息<small>输入新信息以修改</small>
				</h1>
			</div>
			<div class="span4">
<%
	List<BookBean> Books = (ArrayList<BookBean>) session.getAttribute("result");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	System.out.print(Books.get(0).getPublisher()+"jsp");
%>
			<form action="deleteandadd" method="post" onsubmit=" return check(this)">
				<fieldset>
					 <legend>填写信息修改图书</legend> 
					 <label>ISBN</label><input type="text" name = "ISBN" value=<%=Books.get(0).getISBN() %> readonly/>
					 <label>书名</label><input type="text" name = "title" value=<%=Books.get(0).getTitle() %> />
					 <label>作者ID</label><input type="text" name = "authorid" value=<%=Books.get(0).getAuthorID() %> pattern = "[0-9]+"/> 
					 <label>出版者</label><input type="text" name = "publisher" value="<%=Books.get(0).getPublisher() %>" />    
					 <label>出版时间</label><input type="date" name = "publishdate" value=<%=Books.get(0).getPublishDate() %> pattern = "[0-9]{4}-([0-9][1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])"/>  
					 <label>价格</label><input type="text" name = "price" value=<%=Books.get(0).getPrice() %> pattern = "[0-9]+(.)[0-9]+|[0-9]+"/> <br><br>  
					 <button type="submit" class="btn">提交</button>
				</fieldset>
			</form>

		</div>
		</div>
		<div class="span4">
		</div>
	</div>
</div>
</body>
</html>