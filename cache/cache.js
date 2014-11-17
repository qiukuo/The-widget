$(document).ready(function () {
	var type=0;
	$(".bu1").click(function(){
		$("h1").html("直接映像")
 		type=0
	})
	$(".bu2").click(function(){
		$("h1").html("全相联映像")
 		type=1
	})
	$(".bu3").click(function(){
		$("h1").html("组相联映像")
 		type=2
	})

	var cache=new Array;
	var cun=new Array;
	var zushu;
	$(".bu4").click(function(){
		zushu=($(".in1")[0]).value
		if(zushu>127){
			alert("可显示的组数最多为127")
			zushu=127;
			($(".in1")[0]).value=127
		}
		init(zushu)
	})

    function init(zushu){
    for(var i=0;i<16;i++){
    	$(".flag:eq("+i+")").html("-1")
    }
    	$("#con_right").empty()
    	var inita=0;
    	var str="";
    	for(var i=0;i<zushu;i++){
    		str+="<ul>";
    		for(var j=0;j<16;j++){
    			str+='<li value='+inita+'><span class="cun" >'+inita+'</span></li>'
    			inita++;
    		}
    		str+="</ul>";
    	}
    	$("#con_right").prepend(str)
    }

	function shoot(a,b){
		var ye_l=$("#con_right ul li").length
		for(var i=0;i<ye_l;i++){
			var ye=$("#con_right ul ").children(":eq("+i+")")[0].value
			if(ye>-0.5&&ye%a==b){
				$("#con_right ul li:eq("+i+")").addClass("shoot")
			}
		}
	}

	$(".cache").click(function(){
		var ye_l=$("#con_right ul li").length
		var a,b;
		for(var i=0;i<ye_l;i++){
			$("#con_right ul li:eq("+i+")").removeClass("shoot")
		}
		if(type==0){
			 a=16;b=$(this)[0].title;
		}
		else if(type==1){
			 a=1;b=0;
		}
		else if(type==2){
			 a=8;
			 if(($(this)[0].title)%2==1){
			 	b=(($(this)[0].title)-1)/2
			 }else{
			 	b=(($(this)[0].title))/2
			 }
		}
		shoot(a,b)
	})

	$(".bu5").click(function(){
		var shu=$(".in2").val()
		if(shu>2047){
			alert("地址不能超过2047");
			$(".in2").val("2047")
			shu=2047
		}
		var shu2=parseInt(shu,10).toString(2);
		var shu3=shu2.split("")
		var shua=new Array()
		for(var i=0;i<(11-shu3.length);i++){
			shua[i]="0"
		}

		shu2=shua.concat(shu3).join("")
		$("#vi span:eq(1)").html(shu)
		$("#vi span:eq(3)").html(shu2)
	})
})