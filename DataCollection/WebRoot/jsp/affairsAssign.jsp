<%@ page pageEncoding="utf-8"%> 
 

	<script type="text/javascript" src = "/DataCollection/js/affairsAssign.js"></script>
	
	
	<style type = "text/css">
		#cover_div{
			background:#333;
			width:100%;
			height:100%;
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
	
	<div  class="content">
		<div id ="search_panel"></div>
		<div id="upload_form" style="position:fixed; top:300px; left:35%; width:30%;  z-index:999; box-shadow:0 0 8px #fff;"></div>
		<div id = "affairs_grid_div"></div>
	</div>
    <div id = "cover_div"></div>
    