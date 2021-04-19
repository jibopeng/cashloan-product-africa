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



		//弹出消息框
		function shownew(i){
			
			if($('#tck').length > 0 ){
				
				$('#tck span').html(i);
				$('#tck').removeClass('dis-none');
				
				setTimeout(function(){
					$('#tck').addClass('dis-none');
					
					},2000)
					
				}else{
					
				$('body').before('<section id="tck" class="dis-none"><span></span></section>');
				$('#tck span').html(i);
				$('#tck').removeClass('dis-none');
				
				setTimeout(function(){
					$('#tck').addClass('dis-none');
					
					},2000)
				
				}
			}


window.onload = function(){
			/*弹出框*/
			try {
				$(document).on('click','.btn-close,.pupup-leavl',function(){
					$('.pupup').removeClass('active')

				})
			} catch(e) {
			}

}