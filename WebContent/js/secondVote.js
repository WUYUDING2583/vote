/**
 * 
 */
var height = $(window).height();
$("#window").height(height);
var width = $(window).width();
var page = 0;
var No = 1;
var json = $("#json").val();
var Done = $("#done").val();
var dat = eval("(" + json + ")");
var done = eval("(" + Done + ")");

$(document).ready(
		function() {
			if(location.href.indexOf("#reloaded")==-1){
		        location.href=location.href+"#reloaded";
		        location.reload();
		    }

			$("#pre").on("click", function() {
				if (No == 1) {
					alert("不能再往前翻啦");
					return;
				}

				No--;
				var $table = transfer();
				$("#main").html($table);
			});

			$("#next").on("click", function() {
				if (width < 767) {
					if (height > 650) {
						if (No >= dat.length / 9) {
							alert("这是最后一页咯");
							return; 
						}
					} else {
						if (No >= dat.length / 6) {
							alert("这是最后一页咯");
							return;
						}
					}

				} else {
					if (No >= dat.length / 8) {
						alert("这是最后一页咯");
						return;
					}
				}

				No++;
				var $table = transfer();
				$("#main").html($table);
			});

			$("#main").on(
					"click",
					"div[class='bt pull-right']",
					function() {
						var no = $(this).attr("id");
						$.ajax({
							url : "../teacherInfo2",
							type : "post",
							cache : false,
							data : {
								'no' : no
							},
							success : function(data) {
								if (data != null) {
									var detail = eval("(" + data + ")");
									$("#no").val(detail.no);
									$("#name").text(detail.name);
									$("#sex").text(detail.sex);
									$("#img").attr("src",
											"../" + detail.picture);
									$("#birth").text(detail.birth);
									$("#college").text(detail.college);
									$("#course").html(
											detail.course
													.replace(/\n/g, "<br>"));
									$("#introduction").html(
											detail.introduction.replace(/\n/g,
													"<br>"));
									history.pushState({
										page : "parkSeat"
									}, null, "#page=1");
									page = 1;
									$("#teacherInfo").modal();
									$("#teacherInfo").css("z-index", "9999");
								}
							}
						});

					});

			// 监听点击返回键
			window.addEventListener('popstate', function() {
				if (page == 1) {
					$("#teacherInfo").modal("hide");
					page = 0;
				}
			});

			$("button[class='close']").on("click", function() {
				history.back();
			});

			// 投票
			$("#teacherInfo").on("click", "#vote", function() {
				var no = $("#no").val();
				var college = $("#college").text();
				var $str = "<td class='text-center pio'></td>";
				var trNo = $("#" + no).parents("tr").attr("id");
				var tdNo = $("#" + no).parents("td").attr("id") - 1000;
				$.ajax({
					url : "../secondVote",
					type : "post",
					cache : false,
					data : {
						'no' : no,
						'college' : college,
						'index' : tdNo
					},
					success : function(data) {
						if (data != null) {
							var detail = eval("(" + data + ")");
							if (detail.code == "400"||detail.code == "300") {
								alert(detail.message);
							} else if (detail.code == "200") {
								alert("投票成功\n您还需要投" + detail.num + "票");
								$("#teacherInfo").modal("hide");
								dat.splice(tdNo, 1);

								$("#json").val(JSON.stringify(dat));
								var $table = transfer();
								var e=document.getElementById("main");
								e.innerHTML=$table;
								//$("#main").html($table);
							} else if (detail.code == "100") {
								alert("您已完成投票");
								window.location.href = "../voteSuccess2.jsp";
							}
							$("#bottom").css({
								"position" : "fixed",
								"bottom" : "0"
							});
						}
					}
				});
			});

			function transfer() {
				var str = "";
				if (width > 767) {
					var temp = (No - 1) * 8;
					for (var i = 0; i < 2; i++) {
						str += "<tr>"
						for (var j = 0; j < 4; j++) {
							if (temp < dat.length) {
								str += "<td class='text-center pi' id='"
										+ ((No - 1) * 8 + i * 4 + j + 1000)
										+ "'>" + "<div>"
										+ "<div class='number'>" + dat[temp].no
										+ "</div>" + "	<div>"
										+ "		<img class='pi2' src='../"
										+ dat[temp].picture + "' />"
										+ "	</div>"
										+ "<div class='bt pull-right' id='"
										+ dat[temp].no + "'></div>" + "</div>"
										+ "</td>";
							} else {
								str += "<td class='text-center pio'></td>";
							}
							temp++;
						}
					}
				} else {
					if (height > 650) {
						var temp = (No - 1) * 9;
						for (var i = 0; i < 3; i++) {
							str += "<tr>"
							for (var j = 0; j < 3; j++) {
								if (temp < dat.length) {
									str += "<td class='text-center pi' id='"
											+ ((No - 1) * 9 + i * 3 + j + 1000)
											+ "'>" + "<div>"
											+ "<div class='number'>"
											+ dat[temp].no + "</div>"
											+ "	<div>"
											+ "		<img class='pi2' src='../"
											+ dat[temp].picture + "' />"
											+ "	</div>"
											+ "<div class='bt pull-right' id='"
											+ dat[temp].no + "'></div>"
											+ "</div>" + "</td>";
								}
								if (temp >= dat.length) {
									str += "<td class='text-center pio'></td>";
								}

								temp++;

							}
						}
					} else {
						var temp = (No - 1) * 6;
						for (var i = 0; i < 2; i++) {
							str += "<tr>"
							for (var j = 0; j < 3; j++) {
								if (temp < dat.length) {
									str += "<td class='text-center pi' id='"
											+ ((No - 1) * 6 + i * 3 + j + 1000)
											+ "'>" + "<div>"
											+ "<div class='number'>"
											+ dat[temp].no + "</div>"
											+ "	<div>"
											+ "		<img class='pi2' src='../"
											+ dat[temp].picture + "' />"
											+ "	</div>"
											+ "<div class='bt pull-right' id='"
											+ dat[temp].no + "'></div>"
											+ "</div>" + "</td>";
								}
								if (temp >= dat.length) {
									str += "<td class='text-center pio'></td>";
								}

								temp++;

							}
						}
					}

				}

				return str;
			}

		});