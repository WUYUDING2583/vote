<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,zjut.vote.person.Teacher,zjut.vote.person.Student,java.awt.Dimension,java.awt.Toolkit"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>一轮投票</title>
<link rel="shortcout icon" href="../pi/icon.ico.jpg" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=2.0" />

<!-- 新 Bootstrap 核心 CSS 文件 -->
<link
	href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>


<style>
.btn-circle {
	width: 50px;
	height: 50px;
	text-align: center;
	padding: 6px 0;
	font-size: 12px;
	line-height: 1.428571429;
	border-radius: 50px;
}

.smallImg {
	width: 100px;
}

#window {
	border-left: 1px solid #F4F4F4;
	border-right: 1px solid #F4F4F4;
	padding: 0px;
	background: url("../pi/2/top.png") bottom no-repeat, url("../pi/2/top.png")
		top no-repeat;
	background-color: #B7D577;
	background-size: 100% 40px;
}

body {
	padding-right: 0px !important
}

td {
	position: relative;
}

@media ( min-width :767px) {
	#bo2 {
		position: fixed;
		bottom: 40px;
		width: 50%;
	}
	.bo {
		height: 90px;
	}
	table {
		margin-top: 10px;
		width: 100%;
	}
	.pi {
		width: 120px;
		height: 140px;
		background-image: url("../pi/2/bak.png");
		background-repeat: no-repeat;
		background-size: 120px 140px;
		background-origin: padding-box;
	}
	.pi2 {
		width: 60px;
		height: 80px;
	}
	.bt {
		position: absolute;
		bottom: 0;
		right: 0;
		width: 80%;
		height: 30px;
		cursor: pointer;
	}
}
.done {
		position: absolute;
		bottom: 0;
		right: 0;
		width: 80%;
		height: 30px;
		font-family: 隶书;
		color: black;
		background-color:white;
}
@media ( max-width :767px) {
	#bo2 {
		position: fixed;
		bottom: 40px;
		width: 100%;
	}
	.bo {
		height: 90px;
	}
	table {
		width: 100%;
	}
	.pi {
		width:100px;
		height: 130px;
		background-image: url("../pi/2/bak.png");
		background-repeat: no-repeat;
		background-size: 100px 130px;
		background-origin: border-box;
	}
	.pi2 {
		width: 60px;
		height: 80px;
	}
	.bt {
		position: absolute;
		bottom: 0;
		right: 0;
		width: 80%;
		height: 30px;
		cursor: pointer;
	}
}

.font {
	font-family: 隶书;
	color: white;
	font-size: large;
	font-bold: bold;
	cursor: pointer;
}

.modal-open {
	overflow-y: auto;
}

#bottom {
	position:fixed;
	bottom:0;
}
</style>
</head>
<body>
	<div class="col-sm-6 col-sm-offset-3" id="window">
		<div id="top" class="col-sm-8 col-sm-offset-2">
			<img src="../pi/2/font1.png" class="img-responsive">
		</div>
		<div id="center">
			<div class="col-xs-1"></div>
			<table class="col-sm-10 col-sm-offset-1 col-xs-10">
			<%ArrayList<Teacher> list=(ArrayList<Teacher>)session.getAttribute("teacherList");
			Student stu = (Student) session.getAttribute("Student");
			int length=list.size();
			 int width=(int)session.getAttribute("width");
			 int temp=0;
			 if(width<767){
				 for(int i=0;i<3;i++){
					 %>
					 <tr>
					 <%
					 for(int j=0;j<3;j++){
						 boolean flag=true;
					 %>
						 <td class="text-center pi">
						<div>
							<div>
								<img class="pi2" src="../<%=list.get(temp).getPicture() %>" />
							</div>
							<%for(int k=0;k<stu.getSecondVoteNo().size();k++){
								if(list.get(temp).getNo().equals(stu.getSecondVoteNo().get(k))){
									flag=false;
								}
							}
							if(flag){
									%>
									<div class="bt pull-right" id="<%=list.get(temp).getNo() %>"></div>
								<% 
								}
							else{%>
							<div class="done pull-right">DONE</div>
							<% }
							%>
						</div>
					</td>
					<% 
					temp++;
					if(temp==length){
						break;
					}
					 }%>
					 </tr>
				<%  
				if(temp==length){
					break;
				}
				 }
			 }
			 else{
				 for(int i=0;i<2;i++){
					 %>
					 <tr>
					 <%
					 for(int j=0;j<4;j++){
						 boolean flag=true;%>
						 <td class="text-center pi">
						<div>
							<div>
								<img class="pi2" src="../<%=list.get(temp).getPicture() %>" />
							</div><%for(int k=0;k<stu.getSecondVoteNo().size();k++){
								if(list.get(temp).getNo().equals(stu.getSecondVoteNo().get(k))){
									flag=false;
								}
							}
							if(flag){
									%>
									<div class="bt pull-right" id="<%=list.get(temp).getNo() %>"></div>
								<% 
								}
							else{%>
							<div class="done pull-right">DONE</div>
							<% }
							%>
						</div>
					</td>
					<% 
					temp++;
					if(temp==length){
						break;
					}
					 }%>
					 </tr>
				<%  
				if(temp==length){
					break;
				}
				 }
			 }
			%>
			</table>
			 <div>
				<div class="col-sm-6 col-xs-6 text-center font">
					<span>上一页</span>
				</div>
				<div class="col-sm-6 col-xs-6 text-center font">
					<span>下一页</span>
				</div>
			</div>

		</div>
		<div id="bo2">
			<table class="col-sm-11 col-sm-offset-1">
				<tr>
					<td class="col-sm-4"><img class="bo" src="../pi/2/bo1.png"></td>
					<td class="col-sm-4"><img class="bo" src="../pi/2/bo2.png"></td>
					<td class="col-sm-4"><img class="bo" src="../pi/2/bo3.png"></td>
				</tr>
			</table>
		</div>
		<div id="bottom" class="col-sm-4 col-sm-offset-1">
			<img src="../pi/2/font2.png" class="img-responsive">
		</div>
	</div>

	<div class="modal fade" id="teacherInfo" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body text-center">
					<input type="hidden" id="no">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td>姓名</td>
								<td id="name"></td>
								<td>性别</td>
								<td id="sex"></td>
								<td rowspan="3"><img class="smallImg" id="img"></td>
							</tr>
							<tr>
								<td>出生日期</td>
								<td id="birth"></td>
								<td>学院</td>
								<td id="college"></td>
							</tr>
							<tr>
								<td>教授课程</td>
								<td colspan="3" id="course"></td>
							</tr>
							<tr>
								<td>自我介绍</td>
								<td colspan="4" class="text-left" id="introduction"></td>
						</tbody>
					</table>

				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
							<button class="btn btn-info btn-block" id="vote">投票</button>
						</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<script src="../js/secondVote.js"></script>
</body>
</html>