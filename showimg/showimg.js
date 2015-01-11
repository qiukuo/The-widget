$(document).ready(function showimg () {
	$("#showimg").click(function(){
		var str="<img src='close.png' class='showimg_close'>";
			str+="<div class='showimg_div'>"
			str+="<img class='showimg_img'src="+this.src+">"
			str+="</div>"
			str+="<div class='showimg_con'>"
			str+="<input class='showimg_con_left' type='button' value='left'>"
			str+="<input class='showimg_con_right'type='button' value='right'>"
			str+="<input class='showimg_con_big' type='button' value='big'>"
			str+="<input class='showimg_con_small'type='button' value='small'>"
			str+="</div>"

		$("#f_showimg").append(str)
		$("#f_showimg").animate({height:"100%",width:"100%"})
		$(".showimg_img").animate({marginLeft:"10%"})
	})
	var nowdeg=0;
	$(document).on("click",".showimg_con_left",function(){
		$(".showimg_img").rotate({animateTo:nowdeg+90});
		nowdeg=nowdeg+90
	})
	$(document).on("click",".showimg_con_right",function(){
		$(".showimg_img").rotate({animateTo:nowdeg-90});
		nowdeg=nowdeg-90
	})
	$(document).on("click",".showimg_con_big",function(){
		var new_width=$(".showimg_img").width()*1.2
		if(new_width>600){
			new_width=new_width*5/6
		}
		$(".showimg_img").css("width",new_width)
	})
	$(document).on("click",".showimg_con_small",function(){
		var new_width=$(".showimg_img").width()*5/6
		if(new_width<100){
			new_width=new_width*1.2
		}
		$(".showimg_img").css("width",new_width)
	})
	$(document).on("click",".showimg_close",function(){
		$("#f_showimg").empty();
		$("#f_showimg").animate({height:"0%",width:"0%"})
	})
})


