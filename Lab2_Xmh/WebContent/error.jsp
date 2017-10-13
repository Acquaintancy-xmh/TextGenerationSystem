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
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script><title>注册成功</title>
</head>

<%
	String str = (String) session.getAttribute("result");
System.out.print(str);
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
		<div class="span2">
		</div>
		<div class="span6">
			<div class="page-header">
			<%if(str == "authorerror"){ %>
				<h1>
					作者不存在<small>请先添加作者</small>
				</h1>
				<%}
			else if(str == "authorerror_toedit"){ %>
				<h1>
					作者不存在<small>请先添加作者</small>
				</h1>
				<%}
			else if(str == "somethingerror"){%>
			<h1>
					图书信息输入错误<small>请返回检查信息</small>
				</h1>
				<%}
			else if(str == "somethingerror_toedit"){%>
			<h1>
					图书信息输入错误<small>请返回检查信息</small>
				</h1>
			<%}
			else if(str == "authorexist"){%>
			<h1>
					作者已经存在<small>请检查作者信息</small>
				</h1>
				<%
				}%>
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
		<%if(str == "somethingerror") {%>
			<button class="btn btn-large btn-warning btn-block" type="button" onClick="javascript:history.go(-1);">重新修改信息</button>
		<%}else if(str == "somethingerror_toedit"){ %>
			<button class="btn btn-large btn-warning btn-block" type="button" onClick="javascript:history.go(-1);">重新修改图书</button>
			<button class="btn btn-large btn-warning btn-block" type="button" onClick="location.href='result_table.jsp'">返回添加作者</button>
		<%}else if(str == "authorerror_toedit"){ %>
			<button class="btn btn-large btn-warning btn-block" type="button" onClick="javascript:history.go(-1);">重新修改图书</button>
			<button class="btn btn-large btn-warning btn-block" type="button" onClick="location.href='result_table.jsp'">返回添加作者</button>
		<%}
		else {%>
			<button class="btn btn-large btn-warning btn-block" type="button" onClick="javascript:history.go(-1);">返回修改信息</button>
		<%} %>
		</div>
		<div class="span4">
		</div>
	</div>
</div>
</body>
</html>