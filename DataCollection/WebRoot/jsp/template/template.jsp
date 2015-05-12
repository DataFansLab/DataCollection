<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<!-- by nano -->
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="Expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon"
	href="/DataCollection/resources/icon/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css"
	href="/DataCollection/resources/css/clifton-blue.css?time=20140218" />
<link rel="stylesheet" type="text/css"
	href="/DataCollection/resources/nav/css/style.css?time=20140220" />
<link rel="stylesheet" type="text/css"
	href="/DataCollection/resources/css/common-style.css?time=20140220" />
<script type="text/javascript"
	src="/DataCollection/resources/nav/js/jquery.min.js?time=20140218"></script>
<script type="text/javascript"
	src="/DataCollection/resources/nav/js/common.js?time=20140218"></script>
<script src="/DataCollection/js/ext-4.1.0-gpl/ext-all.js"
	charset="utf-8" type="text/javascript"></script>
<title><tiles:getAsString name="title" /></title>
<script type="text/javascript">
$(document).ready(function() {
	$(window).bind('beforeunload', function() {
		return '您可能有数据没有保存';
	});
});
</script>
</head>
<body>
	<div class="container">
		<div class="top">
			<img style = "width:250px;" alt="中软万维"
				src="/DataCollection/resources/images/logo.png" />
			<tiles:insertAttribute name="nav" />
		</div>
		<tiles:insertAttribute name="content" />
		<span style = "color:#777; font:13px Helvetica, arial, freesans, clean, sans-serif !important;">&copy; 2014 CSS</span>
	</div>
</body>
</html>