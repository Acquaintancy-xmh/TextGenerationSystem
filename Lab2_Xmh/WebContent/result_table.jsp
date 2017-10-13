<%@page import="java.awt.print.Book"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="action.BookBean" %>
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
<title>查询结果</title>
<script type="text/javascript">
	function check(form) {
		if (form.name.value == "") {
			alert("作者名称不能为空");
			return false;
		}
		if (form.age.value == "") {
			alert("年龄不能为空");
			return false;
		}
		if (form.country.value == "") {
			alert("国籍不能为空");
			return false;
		}
		return true;
	}
</script>

</head>



<body>
<div class="row-fluid">
			<div class="span2"></div>
			<div class="span6">
				<div class="page-header">
					<h1>
						 <small>   </small>
					</h1>
					<h1>
						 <small>   </small>
					</h1>
					<h1>
						 <small>   </small>
					</h1>
				</div>
			</div>
			<div class="span4"></div>
		</div>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span2">
		</div>
		<div class="span6">
			<div class="page-header">
				<h1>
					图书查询结果<small>点按修改或删除</small>
				</h1>
			</div>
			<%
try{
	List<BookBean> Books = (ArrayList<BookBean>) session.getAttribute("result");
	System.out.print(Books.size());
%>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>
							ISBN
						</th>
						<th>
							图书名称
						</th>
					</tr>
				</thead>
				<%
				if(Books.get(0).getPrice()>=0){
				for(BookBean book : Books){
				%>
				<tbody>
						<tr class="info">
							<td><%=book.getISBN() %></td>
							<td><s:url id="url" action="details">
									<s:param name="ISBN"><%=book.getISBN() %></s:param>
								</s:url> <s:a href="%{url}" ><%=book.getTitle() %></s:a></td>
							<td><s:url id="url1" action="deleteBook">
									<s:param name="ISBN"><%=book.getISBN() %></s:param>
								</s:url> <s:a href="%{url1}" onclick="return confirm('确定删除？')">删除</s:a></td>
						</tr>
					</tbody>
				<%
					}
				}
				else if(Books.get(0).getPrice()==-2){
				%>
				<h3>
					查无此作者！请在页面右侧添加作者信息
					</h3>
				<%
				}
				else{%>
				<h3>
					此作者无图书记录！请返回添加图书信息
					</h3>
					<%
					}%>
								<%
} catch (Exception e) {
			%>
			<h3>
					<span style="color: red">请于页面右侧添加作者信息！</span>
					</h3>
					<%} %>
			</table>

		</div>
		<div class="span4">
			<div class="row-fluid">
				<div class="span2">
				</div>
				<div class="span6">
					<p>
						<legend>返回继续查询</legend>
					</p> <button class="btn btn-block" type="button" onClick="location.href='find_and_add.jsp'">返回</button>
				</div>
				<div class="row-fluid">
			<div class="span2"></div>
			<div class="span6">
				<div class="page-header">
					<h1>
						 <small>   </small>
					</h1>
					<h1>
						 <small>   </small>
					</h1>
					<h1>
						 <small>   </small>
					</h1>
				</div>
			</div>
			<div class="span4"></div>
		</div>
				<div class="span4">
				</div>
			</div>
			<div class="row-fluid">
				<div class="span2">
				</div>
				<form action="addAuthor" method="post" onsubmit=" return check(this)">
				<fieldset>
					 <legend>填写信息增加作者信息</legend> 
					 <label>作者ID</label><input type="text" name = "authorid" pattern = "[0-9]+"/>
					 <label>作者名称</label><input type="text" name = "name" />
					 <label>年龄</label><input type="text" name = "age" pattern = "[0-9]+"/> 
					 <label>国籍</label><input type="text" name = "country" />    
					 <br><br>  
					 <button type="submit" class="btn">提交</button>
				</fieldset>
			</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>