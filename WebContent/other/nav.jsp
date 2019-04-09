<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,zjut.vote.person.Teacher,zjut.vote.person.Student" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>二轮投票</title>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/><title>主页</title>
	<style>
		@media(max-width:767px) {
			#user {
				position: absolute;
				top:0px;
				right: 10px;
			} 
		}
	</style>
</head>
<body >
	<nav class="navbar navbar-default navbar-fixed-top col-sm-6 col-sm-offset-3" role="navigation">
	    <div class="container-fluid">
		    <div class="navbar-header">
		        <a class="navbar-brand" href="#">我最喜爱的老师</a>
		    </div>
		    <div id="user">
		        <strong class="navbar-text pull-right">${Student.name }  ${Student.college }</strong>
		    </div>
	    </div>
	</nav>
	
</body>
</html>