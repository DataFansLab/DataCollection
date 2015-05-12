<%@ page pageEncoding="utf-8"%>
<%String p = request.getParameter("p");
String day = request.getParameter("day");
String qcState = request.getParameter("qcState"); %>
<div class="content">
	<div id="conditions"></div>
	<div id="results"></div>
</div>
<script type="text/javascript" src="/DataCollection/js/analysis_tasks.js?time=20140225"></script>
<script type="text/javascript"
	src="/DataCollection/js/util/commonGrid.js?time=20140225"></script>
<script type="text/javascript">
	function getModel() {
		createComponents('${model}', '<%=p%>', '<%=day%>', '<%=qcState%>');
	}
	window.onload = getModel;
</script>
<script type = "text/javascript" src = "/DataCollection/js/portlet/ComboData.js"></script>