<%@ page pageEncoding="utf-8"%>
<%String flag = request.getParameter("flag"); %>
<div class="content">
	<div id="conditions"></div>
	<div id="results"></div>
</div>
<script type="text/javascript" src="/DataCollection/js/bulletin_missing.js?time=20140327"></script>
<script type = "text/javascript" src = "/DataCollection/js/portlet/ComboData.js"> </script>
<script type="text/javascript"
	src="/DataCollection/js/util/commonGrid.js?time=20140225"></script>
<script type="text/javascript">
	function getModel() {
		createComponents('${model}', '<%=flag%>');
	}
	window.onload = getModel;
</script>
<script type="text/javascript" src="/DataCollection/js/portlet/GridOperate.js"></script>