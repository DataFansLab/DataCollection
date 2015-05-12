<%@ page pageEncoding="utf-8"%>
<%String bulletinId = request.getParameter("bulletin_id");
String qcPhase = request.getParameter("type"); %>
<div class="content">
	<div id="conditions"></div>
	<div id="results"></div>
</div>
<script type="text/javascript" src="/DataCollection/js/qc_prompts_${phase}.js?time=20140326"></script>
<script type="text/javascript"
	src="/DataCollection/js/util/commonGrid.js?time=20140225"></script>
<script type="text/javascript">
	function getModel() {
		createComponents('${model}', '<%=bulletinId %>', '<%=qcPhase %>');
	}
	window.onload = getModel;
</script>
<script type="text/javascript" src = "/DataCollection/js/portlet/GridOperate.js"></script>
<script type="text/javascript" src = "/DataCollection/js/portlet/ComboData.js"></script>