<%@ page pageEncoding="utf-8"%>
<div class="content">
	<div id="conditions"></div>
	<div id="results"></div>
</div>
<script type="text/javascript" src="/DataCollection/js/task_logs_${phase}.js?time=20140618"></script>
<script type="text/javascript" src="/DataCollection/js/util/commonGrid.js?time=20140618"></script>
<script type="text/javascript">
	function getModel() {
		createComponents('${model}');
	}
	window.onload = getModel;
</script>