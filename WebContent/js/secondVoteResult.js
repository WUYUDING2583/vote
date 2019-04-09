
		var windowHeight=$(window).height();
		var l=$("table").offset().top;
		$("table").height(windowHeight-150);
		$(document).ready(function(){
			$("#search").on("click",function(){
				var type=$("select").val();
				var award="";
				switch(type){
				case "secondVote":
					award="我最喜爱的老师";
					break;
				case "secondOne":
					award="桃李满园";
					break;
				case "secondTwo":
					award="德才兼备";
					break;
				case "secondThree":
					award="和蔼可亲";
					break;
				case "secondFour":
					award="言传身教";
					break;
				case "secondFive":
					award="兢兢业业";
					break;
				case "secondSix":
					award="最佳人气";
					break;
				}
				$.ajax({
		    		url:"../SecondVoteResult",
		    		type:"post",
		    		cache:false,
		    		data:{
		    			'type':type
		    		},
		    		success:function(data){
		    			if(data!=null){
		    				var detail=eval("("+data+")");
		    				var $str="";
		    				for(var i=0;i<detail.length;i++){
		    					$str+="<tr><td>"+detail[i].name+"</td><td>"+detail[i].college+"</td><td>"+detail[i].firstVote+"</td></tr>";
		    				}
		    				$("tbody").html($str);
		    				$("caption").text(award+"投票排名");
		    			}
		    		}
				});
			});
			
			$("#export").on("click",function(){
				var $str="<iframe src='../ExportSecondVote?type="+$("select").val()+"' style='display:none;'></iframe>";
				$("#frame").html($str);
			});
		});
	