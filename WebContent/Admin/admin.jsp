<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我最喜爱的老师投票后台</title>
    <link rel="shortcout icon" href="../pi/icon.ico.jpg"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/><title>主页</title>
    
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
	

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">我最喜爱的老师</a>
    </div>
    <div>
        <ul class="nav navbar-nav">
            <li class="active" id="uploadTeacher"><a href="#">一轮教师信息上传</a></li>
            <li id="uploadTeacher2"><a href="#">二轮教师信息上传</a></li>
            <li id="uploadStudent"><a href="#">学生信息上传</a></li>
            <li id="firstVote"><a href="#">一轮投票结果</a></li>
            <li id="notVoteInFirst"><a href="#">一轮未投学生</a></li>
            <li id="secondVote"><a href="#">二轮投票结果</a></li>
            <li id="notVoteInSecond"><a href="#">二轮未投学生</a></li>
            <li id="clear" class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown">清空数据
        <span class="caret"></span></li>
	        <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">
		        <li role="presentation" id="clearOne">
		            <a role="menuitem" tabindex="-1" href="#">清空一轮教师数据</a>
		        </li>
		        <li role="presentation" id="clearTwo">
		            <a role="menuitem" tabindex="-1" href="#">清空二轮教师数据</a>
		        </li>
		        <li role="presentation" id="clearStudent">
		            <a role="menuitem" tabindex="-1" href="#">清空学生数据</a>
		        </li>
	    	</ul>
        
        </ul>
    </div>
    </div>
</nav>
<iframe src="uploadTeacher.jsp" style="width:100%;margin-top:60px;border:1px solid white;"></iframe>
<script>
	var windowHeight=$(window).height();
	$("iframe").height(windowHeight-65);
	$(document).ready(function(){
		
		$("#clearOne").on("click",function(){
			$.ajax({
	    		url:"../clearOne",
	    		type:"post",
	    		cache:false,
	    		success:function(data){
	    			if(data!=null){
	    				alert(data);
	    			}
	    		}
			});
		});

		$("#clearTwo").on("click",function(){
			$.ajax({
	    		url:"../clearTwo",
	    		type:"post",
	    		cache:false,
	    		success:function(data){
	    			if(data!=null){
	    				alert(data);
	    			}
	    		}
			});
		});

		$("#clearStudent").on("click",function(){
			$.ajax({
	    		url:"../clearStudent",
	    		type:"post",
	    		cache:false,
	    		success:function(data){
	    			if(data!=null){
	    				alert(data);
	    			}
	    		}
			});
		});
		$("#uploadTeacher").on("click",function(){
			$("iframe").attr("src","uploadTeacher.jsp");
			$("li[class='active']").removeAttr("class");
			$(this).attr("class","active");
		});
		

		$("#uploadTeacher2").on("click",function(){
			$("iframe").attr("src","uploadTeacher2.jsp");
			$("li[class='active']").removeAttr("class");
			$(this).attr("class","active");
		});

		$("#uploadStudent").on("click",function(){
			$("iframe").attr("src","uploadStudent.jsp");
			$("li[class='active']").removeAttr("class");
			$(this).attr("class","active");
		});

		$("#secondVote").on("click",function(){
			$("iframe").attr("src","../SecondVoteResult");
			$("li[class='active']").removeAttr("class");
			$(this).attr("class","active");
		});

		$("#firstVote").on("click",function(){
			$("iframe").attr("src","../FirstVoteResult");
			$("li[class='active']").removeAttr("class");
			$(this).attr("class","active");
		});

		$("#notVoteInSecond").on("click",function(){
			$("iframe").attr("src","../notVoteInSecond");
			$("li[class='active']").removeAttr("class");
			$(this).attr("class","active");
		});
		$("#notVoteInFirst").on("click",function(){
			$("iframe").attr("src","../notVoteInFirst");
			$("li[class='active']").removeAttr("class");
			$(this).attr("class","active");
		});
	});
</script>
</body>
</html>