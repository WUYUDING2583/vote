/**
 * 
 */
var windowHeight = $(window).height();
var width=$(window).width();
$("#width").val(width);
$("#height").val(windowHeight);
$("#window").css("margin-top",windowHeight*0.2+'px')
if($("#myAlert strong").text()!=""){
	$("#myAlert").attr("class","alert alert-danger col-sm-10 col-sm-offset-1");
}
$(document).ready(function(){
	if(width>767){
		$("img").attr("src","../pi/2/21.png");
		//$("#backImg").height(windowHeight);
//		var imgWidth=$("img").width();
//		var offset=(width-imgWidth)/2;
//		$("#backImg").offset({left:offset});
		$("#window").offset({top:windowHeight*0.35});
	}
	else{
		$("#backImg").offset({top:0});
		$("#window").offset({top:windowHeight*0.3});
	}
	
	
	
	$("#login").on("click",function(){
		var username=$("#userNo").val();
		var password=$("#password").val();
		if(username==""){
			$("#myAlert").children("strong").text("用户名不能为空");
			$("#myAlert").attr("class","alert alert-danger col-sm-10 col-sm-offset-1");
			return;
		}
		if(password==""){
			$("#myAlert").children("strong").text("密码不能为空");
			$("#myAlert").attr("class","alert alert-danger col-sm-10 col-sm-offset-1");
			return;
		}
		$("form").submit();
		
	});
	
	$("input").on("focus",function(){
		$("#myAlert").attr("class","alert alert-danger hide col-sm-10 col-sm-offset-1");
	});
});