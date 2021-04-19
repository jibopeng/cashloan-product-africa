function  htmlfont(){
   
	var font_size	=	 Math.round(window.innerWidth*0.05333333333333);

	if(font_size>25){
		font_size = 25;
	}
	font_size = font_size+'px';
	
	document.getElementsByTagName("html")[0].style.fontSize = font_size;
}
htmlfont();

window.onresize = function(){
htmlfont();
}

window.onload = function(){
	  FastClick.attach(document.body);
}