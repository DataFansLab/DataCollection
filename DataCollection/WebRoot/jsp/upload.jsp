	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%String p = request.getParameter("p");
	String day = request.getParameter("day");
	String qcState = request.getParameter("qcState"); %>
	
	<style type = "text/css">
  		#cover_div{
			background:#333;
			width:100%;
			height:1000px;
			position:absolute;
			top:0px;
			left:0px;
			filter:Alpha(Opacity=30);
			-moz-opacity:0.5;
			opacity:0.5;
			z-index:100;
			display:none;		
		}  		
  	</style>
  	
  	<div class = "content">
		<div id="search_panel"></div>
    	<div id="grid"></div>
    	<div id="upload_form" style="position:fixed; top:300px; left:30%; width:700px; z-index:999; box-shadow:0 0 8px #fff;"></div>
    	<div id="add_item_form" style="position:fixed; top:300px; left:30%; width:700px; z-index:999; box-shadow:0 0 8px #fff;"></div>	
  	</div>
  	
  	<div id = "cover_div"></div>
  	<script type="text/javascript" src="/DataCollection/js/portlet/smartGrid.js"></script>
  	<script type = "text/javascript" src = "/DataCollection/js/portlet/ComboData.js"></script>
  	<script type="text/javascript" src="/DataCollection/js/portlet/GridOperate.js"></script>
  	<script type="text/javascript" src = "/DataCollection/js/uploadBulletin.js"></script>
  	<script type="text/javascript">
  	Ext.onReady(createComponents('<%=p %>', '<%=day %>', '<%=qcState %>'));
</script>
  	
  	