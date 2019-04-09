/**
 * 
 */
var windowHeight = $(window).height();
var width=$(window).width();

$("#window").css("margin-top",windowHeight*0.2+'px')
if($("#myAlert strong").text()!=""){
	$("#myAlert").attr("class","alert alert-danger col-sm-10 col-sm-offset-1");
}
$(document).ready(function(){
	var img=new Image();
	img.src=$("img").attr("src");
	var timer=setInterval(function(){
		if(img.complete){
			clearInterval(timer);
			var imgWidth=$("img").width();
			var imgHeight=$("img").height();
			var offset=(width-imgWidth)/2;
			if(width>767){
				$("#backImg").offset({left:offset,top:windowHeight*0.1});
			}
			else{
				$("#backImg").offset({left:offset,top:windowHeight*0.13});
			}
		}
	},50);
	
	
	
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