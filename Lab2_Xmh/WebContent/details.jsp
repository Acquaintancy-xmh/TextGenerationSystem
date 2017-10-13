<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="action.BookBean"%>
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link
	href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>

<title>图书详细信息</title>
</head>

<%
	List<BookBean> Books = (ArrayList<BookBean>) session.getAttribute("result");
%>

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
			<div class="span2"></div>
			<div class="span8">
				<div class="page-header">
					<h1>
						图书具体信息<small>点按修改图书信息</small>
					</h1>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>ISBN</th>
							<th>图书名称</th>
							<th>作者</th>
							<th>出版方</th>
							<th>出版时间</th>
							<th>价格</th>
						</tr>
					</thead>
					<%
				BookBean book = Books.get(0);
				%>
				<tbody>
						<tr class="info">
							<td><%=book.getISBN() %></td>
							<td><%=book.getTitle() %></td>
							<td><%=book.getAuthorID() %></td>
							<td><%=book.getPublisher() %></td>
							<td><%=book.getPublishDate() %></td>
							<td><%=book.getPrice() %></td>
							<td>
								<s:url id="url" action="editBook">
									<s:param name="ISBN"><%=book.getISBN() %></s:param>
								</s:url> <s:a href="%{url}" onclick="return confirm('确定修改？')">修改</s:a></td>
						</tr>
					</tbody>
				</table>
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
				<table class="table">
					<thead>
						<tr>
							<th>作者ID</th>
							<th>作者姓名</th>
							<th>年龄</th>
							<th>国籍</th>
						</tr>
					</thead>
					<%
				BookBean book1 = Books.get(1);
				%>
				<tbody>
						<tr class="info">
							<td><%=book1.getAuthorID() %></td>  <!-- 作者ID -->
							<td><%=book1.getName() %></td>     <!-- 作者姓名 -->
							<td><%=book1.getAge() %></td>      <!-- 年龄 -->
							<td><%=book1.getCountry() %></td> <!-- 国籍 -->
						</tr>
					</tbody>
				</table> 
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
				<button type="button" class="btn btn-info btn-lg btn-block" onClick="location.href='find_and_add.jsp'">返回继续查询</button>
			</div>
			<div class="span2"></div>
		</div>
	</div>
</body>
</html>