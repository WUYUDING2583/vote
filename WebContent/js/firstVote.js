/**
 * 
 */
var num=$(".img-responsive").length;
var contentWidth=$(".tab-content").width();
var width=$(window).width();
var height=$(window).height();
var top;
var bottom;
//$("#top").css({"position":"absolute","top":"0"});

var page=0;
$(document).ready(function(){
//	var doHeight=$("#bottom").height()+$("#bottom").offset().top;
//	if(doHeight<height){
//		$("#bottom").css({"position":"fixed","bottom":"0"});
//	}
//	else{
//		$("#bottom").css({"position":"","bottom":""});
//	}
	$("li").on("click",function(){
		var coll=$(this).find("a").attr("href");
		var top=$("#top").height();
		var bottom=$("#bottom").height();
		$(document).ready(function(){
			var doHeight=top+bottom+$(coll).height();
			if(doHeight<height){
				$("#bottom").css({"position":"fixed","bottom":"0"});
			}
			else{
				$("#bottom").css({"position":"","bottom":""});
			}
		})
		
	});
	
	var timer=setInterval(function(){
		$(".img-responsive").each(function(){
			var img=new Image();
			img.src=$(this).attr("src");
			if(img.complete){
				num--;
				if(num==0){
					clearInterval(timer)
					//$("#window").height(windowHeight);
					var topHeight=$("#top").height();
					top=topHeight;
					var bottomHeight=$("#bottom").height();
					bottom=bottomHeight;
					var navHeight=$("#navbar-example").height();
					//$("#center").height(windowHeight-topHeight-bottomHeight);
					$("#navbar-example").offset({top:topHeight+5});
					$(".tab-content").offset({top:topHeight+navHeight+5+5});
					//$("#bottom").offset({top:windowHeight-bottomHeight});
					//$(".tab-content").height(windowHeight-topHeight-bottomHeight-navHeight);
					if($("#myCollege > h4").length>0){
						$("#bottom").css({"position":"fixed","bottom":"0"});
					}
					var doHeight=$("#bottom").height()+$("#bottom").offset().top;
					if(doHeight<height){
						$("#bottom").css({"position":"fixed","bottom":"0"});
					}
					else{
						$("#bottom").css({"position":"","bottom":""});
					}
				}
			}
		});
	},200);
	
	$("button[class='btn btn-info btn-xs']").on("click",function(){
		var no=$(this).prev().val();
		$.ajax({
    		url:"../teacherInfo",
    		type:"post",
    		cache:false,
    		data:{
    			'no':no
    		},
    		success:function(data){
    			if(data!=null){
    				var detail=eval("("+data+")");
    				$("#no").val(detail.no);
    				$("#name").text(detail.name);
    				$("#sex").text(detail.sex);
    				$("#img").attr("src","../"+detail.picture);
    				$("#birth").text(detail.birth);
    				$("#college").text(detail.college);
    				$("#course").html(detail.course.replace(/\n/g,"<br>"));
    				$("#introduction").html(detail.introduction.replace(/\n/g,"<br>"));
    				history.pushState({page:"parkSeat"},null,"#page=1");
    				page=1;
    				$("#teacherInfo").modal();
    				$("#teacherInfo").css("z-index","9999");
    			}
    		}
		});
		
	});

	//监听点击返回键
    window.addEventListener('popstate', function () {
    	if(page==1){
    		$("#teacherInfo").modal("hide");
    		page=0;
    	}
	});
    
    $("button[class='close']").on("click",function(){
    	history.back();
    });
    
    //投票
    $("#vote").on("click",function(){
    	var no=$("#no").val();
    	var college=$("#college").text();
    	$.ajax({
    		url:"../firstVote",
    		type:"post",
    		cache:false,
    		data:{
    			'no':no,
    			'college':college
    		},
    		success:function(data){
    			if(data!=null){
    				var detail=eval("("+data+")");
    				if(detail.code=="400"){
    					alert(detail.message);
    				}
    				else if(detail.code=="200"){
    					alert("投票成功");
    					$("#teacherInfo").modal("hide");
    					if(college=="体育军训部"){
    						$("#sports").html("<h4>您已完成当前投票</h4><h4>请投另一部分</h4>");
        				}
    					else{
    						$("#myCollege").html("<h4>您已完成当前投票</h4><h4>请投另一部分</h4>");
    					}
    				}
    				else if(detail.code=="100"){
    					alert("您已完成投票");
    					window.location.href="../voteSuccess.jsp";
    				}
    				$("#bottom").css({"position":"fixed","bottom":"0"});
    			}
    		}
		});
    });
    
});