<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>

<div class="ui fluid seven item large inverted blue menu">
	<div class="item" style="color: rgb(244, 241, 241); font-weight: bold; font-size: 20px !important;">解析主页</div>
	<a class="item" href="/DataCollection/page/index-analysis" target='_blank'>主页</a>
	<div class="ui dropdown item">
		工作平台 <i class="dropdown icon"></i>
		<div class="menu">
			<a class="item" href="/DataCollection/page/analysis/taskList" target='_blank'>解析任务列表</a>
			<a class="item" href="/DataCollection/page/qcPrompts/analysis" target='_blank'>QC提示</a>
			<a class="item" href="/DataCollection/page/autoReport" target='_blank'>自动生成报告</a>
		</div>
	</div>
	<div class="ui dropdown item">
		工具箱 <i class="dropdown icon"></i>
		<div class="menu">
			<a class="item" id="analysisProcess" href="#">解析程序</a> 
			<a class="item" href="/DataCollection/page/taskLogs/analysis" target='_blank'>解析日志</a>
			<a class="item" href="#" target='_blank'>同步数据</a> 
			<a class="item"	href="/DataCollection/page/companyList/analysis" target='_blank'>公司代码列表</a>
			<a class="item" href="/DataCollection/page/qcLog/analysis" target='_blank'>QC日志</a>
			<a class="item" href="/DataCollection/page/affairsAssign/analysis" target='_blank'>任务分配</a>
		</div>
	</div>
	<div class="ui dropdown item">
		系统规则 <i class="dropdown icon"></i>
		<div class="menu">
			<a class="item" href="#" target='_blank'>流程图</a> <a class="item"
				href="/DataCollection/page/analysis/QCRules" target='_blank'>QC规则</a>
		</div>

	</div>
	<div class="ui dropdown item">
		导航页 <i class="dropdown icon"></i>
		<div class="menu">
			<a class="item" href="/DataCollection" target='_blank'>下载平台首页</a> <a
				class="item" href="/DataCollection/page/index-analysis"
				target='_blank'>解析平台首页</a> <a class="item"
				href="http://disclosure.szse.cn/m/drgg.htm" target='_blank'>深交所文档下载页</a>
			<a class="item"
				href="http://www.sse.com.cn/assortment/stock/list/stockdetails/announcement/index.shtml"
				target='_blank'>上交所文档下载页</a> <a class="item"
				href="http://stockdata.stock.hexun.com/gszl/s002380.shtml"
				target='_blank'>Web数据下载页</a>
		</div>

	</div>
</div>


<script type="text/javascript">
$(document).ready(function(){
	Ext.onReady(function() {
		$("#analysisProcess").click(function(e) {
			e.preventDefault();
			Ext.create('Ext.window.Window', {
				title: '解析程序',
				height: 280,
				width: 500,
				layout: 'fit',
				resizable: false,
				html: '<iframe src="/DataCollection/page/analysisProcess" frameborder="no" style="width: 100%; height: 100%"></iframe>'
			}).show();
		});
	});
	
});
$('.ui.dropdown')
  .dropdown()
;
</script>
<!-- #nav end -->