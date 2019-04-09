/**
 * 
 */
var width=$(window).width();
var windowHeight=$(window).height();
var type=$("#tip").val();
var img=new Image();
	img.src=$("img").attr("src");
	var timer=setInterval(function(){
		if(img.complete){
			clearInterval(timer);
			if(width>767){
				if(type=="f"){
					$("img").attr("src","../pi/2/22f.png");
				}
				else{
					$("img").attr("src","../pi/2/22.png");
				}
				$("#backImg").attr("class","col-sm-10 col-sm-offset-1");
				$("#window").offset({top:windowHeight*0.35});
			}
			else{
				$("#backImg").offset({top:0});
				$("#window").offset({top:windowHeight*0.3});
			}
		}
	},50);
	
if ($("#myAlert strong").text() != "") {
	$("#myAlert").attr("class", "alert alert-danger col-sm-10 col-sm-offset-1");
}
$("input").on(
		"focus",
		function() {
			$("#myAlert").attr("class",
					"alert alert-danger hide col-sm-10 col-sm-offset-1");
		});
function check() {
	var name = $("#name").val();
	var password = $("#password").val();
	var repassword = $("#repassword").val();
	if (name.length == 0 || password.length == 0 || repassword.length == 0) {
		if(type=="f"){
			$("#myAlert strong").text("The input box can not be empty");
		}
		else{
			$("#myAlert strong").text("输入框不能为空");
		}
		$("#myAlert").attr("class",
				"alert alert-danger col-sm-10 col-sm-offset-1");
		return false;
	}
	if (password != repassword) {
		if(type=="f"){
			$("#myAlert strong").text("The two password do not match");
		}
		else{
			$("#myAlert strong").text("两次密码输入不一致");
		}
		$("#myAlert").attr("class",
				"alert alert-danger col-sm-10 col-sm-offset-1");
		return false;
	}

}