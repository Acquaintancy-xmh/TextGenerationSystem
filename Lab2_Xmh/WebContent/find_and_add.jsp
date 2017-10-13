<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
<title>查询和添加图书</title>
<script type="text/javascript">
	function check(form) {
		var regISBN=/([0-9]+-){4}[0-9]/;
		var regAuthorID=/[0-9]+/;
		var regPublistDate=/[0-9]{4}-[0-9]+-[0-9]+/;
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
		if (form.authorid.value == "") {
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
					<span>输入作者名称</span><small>以查询所著全部图书</small>
				</h1>
			</div>
			<form action="findBook" method = "post">
				<input class="input-medium search-query" type="text" name = "Author" value="请输入作者进行查找"  
            onfocus='if(this.value=="请输入作者进行查找"){this.value="";}; '   
            onblur='if(this.value==""){this.value="请输入作者进行查找";};'/> <button type="submit" class="btn">查找</button>
			</form>
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
		<form action="showBook" method = "post">
			<button class="btn btn-large btn-success btn-block" type="submit" >显示全部图书</button>
		</form>
		</div>
		<div class="span4">
			<form action="addBook" method="post" onsubmit=" return check(this)">
				<fieldset>
					 <legend>填写信息增加图书</legend> 
					 <label>ISBN</label><input type="text" name = "ISBN" pattern = "([0-9]+-){4}[0-9]"/> 
					 <label>书名</label><input type="text" name = "title" />
					 <label>作者ID</label><input type="text" name = "authorid" pattern = "[0-9]+" /> 
					 <label>出版者</label><input type="text" name = "publisher" />    
					 <label>出版时间</label><input type="date" name = "publishdate" pattern = "[0-9]{4}-([0-9][1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])" />  
					 <label>价格</label><input type="text" name = "price" pattern = "[0-9]+(.)[0-9]+|[0-9]+"/> <br><br>  
					 <button type="submit" class="btn">提交</button>
				</fieldset>
			</form>
		</div>
	</div>
</div>
</body>
</html>