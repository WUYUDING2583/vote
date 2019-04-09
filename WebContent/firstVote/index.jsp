<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>我最喜爱的老师</title>
    <link rel="shortcout icon" href="../pi/icon.ico.jpg"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/><title>主页</title>
   
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<style>
		/*@media (min-width: 767px){
			body {
				background-image:url("../pi/1.png");
				background-repeat:no-repeat;
			}
		}*/
		
		#backImg {
			position:absolute;
		}
		
		.form-group {
			opacity:0.8;
		}
		
	</style>
	

</head>
<body>
	<div id="backImg" style="width:100%;">
		<img src="../pi/1.png" class="img-responsive">
	</div>
	<div class="col-sm-4 col-sm-offset-4" id="window">
	<div id="myAlert" class="alert alert-danger hide col-sm-offset-1 col-sm-10" style="margin-top:30px;">
	    <a href="#" class="close" data-dismiss="alert">&times;</a>
	    <strong>${wrongMsg }</strong>
	</div>
	<form role="form" class="form-horizontal" action="../login" method="post">
		<div class="form-group" style="margin-top:30px;">
		    <div class="col-sm-offset-1 col-sm-10">
		      <input type="text" class="form-control input-lg" id="userNo" name="no" placeholder="请输入账号">
		    </div>
	  	</div>	
	  	<div class="form-group">
		    <div class="col-sm-offset-1 col-sm-10">
		      <input type="password" class="form-control input-lg" id="password" name="password" placeholder="请输入密码">
		    </div>
	  	</div>	
	  	<div class="form-group">
		    <div class="col-sm-10 col-sm-offset-1" style="margin-bottom:30px;">
		      <button type="button" class="btn btn-info btn-block btn-lg" id="login">登陆</button>
		    </div>
	  	</div>
	</form>
	</div>
	<script src="../js/index.js"></script>
	<%session.removeAttribute("wrongMsg"); %>
</body>
</html>