<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,zjut.vote.person.Teacher,zjut.vote.person.Student" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>一轮投票</title>
    <link rel="shortcout icon" href="../pi/icon.ico.jpg"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0"/>
    
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>


	<style>
		@media(max-width:767px) {
			#user {
				position: absolute;
				top:0px;
				right: 10px;
			} 
		}
		
		/*.nav {
			margin-top:60px;
		}*/
		
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
		
		
		
		#window,#center,#bottom,html{
			background-color:#ECECEC;
		}
		
		#window {
			border-left:1px solid #F4F4F4;
			border-right:1px solid #F4F4F4;
			padding:0px;
		}
		body {

 padding-right: 0px !important

}

.modal-open {

 overflow-y: auto;

}
		
	/*	@media (min-width: 767px){
		
			#window {
				overflow:hidden;
			}
			
			.tab-content {
				position: relative;
				overflow-x: hidden;
			}
			
			#myCollege,#sports {
				position: absolute;
				left: 0;
				top: 0;
				right: -17px;
				bottom: 0;
				overflow-x: hidden;
				overflow-y: scroll;
				overflow-x:-moz-scrollbars-none;
			}
			
			.tab-content::-webkit-scrollbar {
	  			display: none;
			}
		}*/
	</style>
</head>
<body >
	<div class="col-sm-6 col-sm-offset-3" id="window" style="height:auto;">
	<div id="top">
	 <img src="../pi/头.png" class="img-responsive">
	</div>
	<div id="center" style="margin-right:15px;margin-left:15px;height:auto;">
	<div id="navbar-example">
		<ul class="nav nav-tabs">
		    <li class="active"><a data-toggle="tab" href="#myCollege">本学院</a></li>
	  	</ul>
	</div>
  	<div class="tab-content"  data-spy="scroll" data-target="#navbar-example" data-offset="0"
     style="position: relative;height:auto;">
	    <div id="myCollege" class="tab-pane fade in active" >
	    <% Student stu=(Student)session.getAttribute("Student");
	    	if(!stu.getFirstVoteInCollege().equals("")){
	    		%>
	    		<h4>您已完成本学院老师投票</h4>
	    	<%}
	    	else{%>
	    	<table class="table table-bordered" id="collegeTable" style="height:auto;">
	    	<tbody>
	       <% ArrayList<Teacher> list=(ArrayList<Teacher>)session.getAttribute("collegeTeacehrList"); 
	       for(int i=0;i<list.size()/3+1;i++){
		       %>
		       <tr>
		       <% 
		       for(int k=i*3;k<i*3+3&&k<list.size();k++){
		    %>
		    <td class="text-center">
			      <img src="../<%=list.get(k).getPicture() %>" class="img-circle" style="width:60px;height:60px;">
			      <input type="hidden" value="<%=list.get(k).getNo() %>">
			      <button class="btn btn-info btn-xs">READ MORE</button>
			</td>
			<%}%>
		</tr>
		<% }}%>
		</tbody>
		</table>
	    </div>
	<div style="width:100%;height:0;clear:both;"></div>
	</div>
	<div style="width:100%;height:0;clear:both;"></div>
	</div>
	<div id="bottom">
	 <img src="../pi/Pb.png" class="img-responsive" >
	</div>
	<div style="width:100%;height:0;clear:both;"></div>
	</div>
	<div class="modal fade" id="teacherInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
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
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
	</div>
	<script src="../js/FfirstVote.js"></script>
</body>
</html>