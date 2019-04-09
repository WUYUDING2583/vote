<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,zjut.vote.person.Teacher,zjut.vote.person.Student" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>一轮投票</title>
    <link rel="shortcout icon" href="pi/icon.ico.jpg"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/><title>主页</title>
    
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<style>
		
	</style>
</head>
<body >
	<div class="text-center" id="center" style="margin-right:15px;margin-left:15px;">
		<img src="pi/2/29.png" class="img-responsive">
	</div>
	
	<script>
		var width=$(window).width();
		var height=$(window).height();
		$("div").css("height",height+'px');
		if(width<767){
			$("div").css("margin-top",height*0.3+'px');
			$("img").attr("src","pi/2/29.png")
		}
	</script>
</body>
</html>