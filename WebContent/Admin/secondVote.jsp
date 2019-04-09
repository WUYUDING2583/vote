<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,zjut.vote.person.Teacher" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>我最喜爱的老师投票后台</title>
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
	
	<table class="table table-striped">
	  <caption class="text-center">公投排名</caption>
	<button class="btn btn-info pull-right" id="export">导出投票数据</button>
	  <thead>
	    <tr>
	      <th>姓名</th>
	      <th>学院</th>
	      <th>票数</th>
	    </tr>
	  </thead>
	  <tbody>
	  	<% ArrayList<Teacher> list=(ArrayList<Teacher>)session.getAttribute("SecondResultList");
	  	 for(int i=0;i<list.size();i++){
	  	%>
	    <tr>
	      <td><%=list.get(i).getName() %></td>
	      <td><%=list.get(i).getCollege() %>
	      <td><%=list.get(i).getSecondVote() %></td>
	    </tr>
	    <%} %>
	  </tbody>
	</table>
	<div id="frame" style="display:none;"></div>
	<script src="../js/secondVoteResult.js"></script>
</body>
</html>