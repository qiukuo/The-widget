// time: 14/11/15;
// by  : litkk;
// for : huang
$(document).ready(function () {
	var type=0;
	$(".bu1").click(function(){
		$("h1").html("直接映像")
 		type=0
 		init(zushu)
	})
	$(".bu2").click(function(){
		$("h1").html("全相联映像")
 		type=1
 		init(zushu)
	})
	$(".bu3").click(function(){
		$("h1").html("组相联映像")
 		type=2
 		init(zushu)
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
		var ye_l=$("#con_right ul li").length
		for(var i=0;i<ye_l;i++){
			$("#con_right ul li:eq("+i+")").removeClass("shoot")
		}
		var shu=$(".in2").val()
		$($("#con_right ul li:eq("+shu+")")).addClass("shoot")
		if(shu>zushu*16-1){
			alert("地址不能超过"+(zushu*16-1));
			$(".in2").val(zushu*16-1)
			shu=(zushu*16-1)
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

		var cacheshua=shu2.split("")
		if(type==0){
			var cacheshua0=parseInt(cacheshua.slice(7,11).join(""),2).toString(10)
			var cacheshua0flag=parseInt(cacheshua.slice(0,7).join(""),2).toString(2)
			var nowflag=parseInt($(".flag")[cacheshua0].innerHTML,2).toString(2)

			if(nowflag==cacheshua0flag){
				alert("在第"+cacheshua0+"页缓存，以找出")
			}
			else{
				$(".flag")[cacheshua0].innerHTML=cacheshua0flag
				alert("在第"+cacheshua0+"页没有找出，已更新到该页")
			}
		}

		if(type==1){
			var cacheshua0flag=parseInt(shu2,2).toString(2)
			for(var i=0;i<16;i++){
				var nowflag=$(".flag:eq("+i+")").html()
				if(nowflag==cacheshua0flag){
					alert("在第"+cacheshua0+"页缓存，以找出")
					break;
				}
				if(i==15){
					for(var j=0;j<16;j++){
						var upflag=$(".flag:eq("+j+")").html()
						if(upflag==-1){
							$(".flag:eq("+j+")").html(cacheshua0flag)
							break
						}
						if(j==15){
							for(var k=0;k<15;k++){
								var k1=k+1
								$(".flag:eq("+k+")").html($(".flag:eq("+k1+")").html())
								$(".flag:eq(15)").html(cacheshua0flag)
							}
						}
					}
					alert("在缓存中未找到,以更新cache")
				}
			}
		}
		if(type==2){
			var cacheshua0=parseInt(cacheshua.slice(8,11).join(""),2).toString(10)
			var cacheshua0flag=parseInt(cacheshua.slice(0,8).join(""),2).toString(2)
			var cacheshua1=cacheshua0*2
			var cacheshua2=cacheshua0*2+1
 			if($(".flag:eq("+cacheshua1+")").html()==cacheshua0flag){
 				alert("在第"+cacheshua1+"页缓存，以找出")
 			}
 			else if($(".flag:eq("+cacheshua2+")").html()==cacheshua0flag){
 				alert("在第"+cacheshua2+"页缓存，以找出")
 			}
 			else{
 				if($(".flag:eq("+cacheshua1+")").html()==-1){
 					$(".flag:eq("+cacheshua1+")").html(cacheshua0flag)
 				}
 				else if($(".flag:eq("+cacheshua2+")").html()==-1){
					$(".flag:eq("+cacheshua2+")").html(cacheshua0flag)
                }
                else{
                	var upflag0=$(".flag:eq("+cacheshua2+")").html()
                	$(".flag:eq("+cacheshua1+")").html(upflag0);
                	$(".flag:eq("+cacheshua2+")").html(cacheshua0flag)
                }
 				alert("在缓存中未找到,以更新cache")
 			}
		}
	})
})