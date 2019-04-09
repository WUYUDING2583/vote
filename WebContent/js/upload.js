/**
 * 
 */
$(document).ready(function(){
$("button").on("click",function(){
		var formData = new FormData();
        formData.append("myfile", document.getElementById("upload").files[0]);  
          $.ajax({
    		url:"uploadTeacher",
    		type:"post",
    		cache:false,
    		data:formData,
    		contentType: false,
            processData: false,
            dataType: "text",
    		success:function(data){
    			
    		}
          });
	});
});