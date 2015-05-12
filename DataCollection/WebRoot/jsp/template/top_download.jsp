<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>

<div id="nav">
	<ul id="main-menu" class="sm sm-blue">
		<li id="home-title">下载主页</li>
		<li id="menu-item-8"><a
			href="/DataCollection/page/index-download" target='_blank'>主页</a></li>
		<li id="menu-item-155"><a href="javascript:void(0);">工作平台</a>
			<ul class="sub-menu">
				<li><a href="/DataCollection/page/upload" target='_blank'>上传文档</a></li>
				<li><a href="/DataCollection/page/bulletinMissing" target='_blank'>文档缺失</a></li>
				<li><a href="/DataCollection/page/companyInfo" target='_blank'>公司综合详情</a></li>
				<li><a href="/DataCollection/page/companyList/download" target='_blank'>公司代码列表</a></li>
				<li><a href="/DataCollection/page/qcPrompts/download" target='_blank'>QC提示</a></li>
			</ul></li>
		<li id="menu-item-144"><a href="javascript:void(0);">工具箱</a>
			<ul class="sub-menu">
				<li><a id="downloadProcess" href="#">下载程序</a></li>
				<li><a href="/DataCollection/page/taskLogs/download" target='_blank'>下载日志</a></li>
				<li><a href="/DataCollection/page/qcLog/download" target='_blank'>QC日志</a></li>
				<li><a href="/DataCollection/page/affairsAssign/download" target='_blank'>任务分配</a></li>
			</ul></li>
		<li id="menu-item-165"><a href="javascript:void(0);">系统规则</a>
			<ul class="sub-menu">
				<li><a href="#" target='_blank'>流程图</a></li>
				<sli><a href="/DataCollection/page/download/QCRules" target='_blank'>QC规则</a></li>
			</ul></li>
		<li id="menu-item-989"><a href="javascript:void(0);">导航页</a>
			<ul class="sub-menu">
				<li><a href="/DataCollection" target='_blank'>下载平台首页</a></li>
				<li ><a href="/DataCollection/page/index-analysis" target='_blank'>解析平台首页</a></li>
				<li><a href="http://disclosure.szse.cn/m/drgg.htm" target='_blank'>深交所文档下载页</a></li>
				<li><a
					href="http://www.sse.com.cn/assortment/stock/list/stockdetails/announcement/index.shtml" target='_blank'>上交所文档下载页</a></li>
				<li><a
					href="http://stockdata.stock.hexun.com/gszl/s002380.shtml" target='_blank'>Web数据下载页</a></li>
			</ul></li>
	</ul>
</div>
<script type="text/javascript">
$(document).ready(function(){
	Ext.onReady(function() {
		$("#downloadProcess").click(function(e) {
			e.preventDefault();
			Ext.create('Ext.window.Window', {
				title: '下载程序',
				height: 280,
				width: 500,
				layout: 'fit',
				resizable: false,
				html: '<iframe src="/DataCollection/page/downloadProcess" frameborder="no" style="width: 100%; height: 100%"></iframe>'
			}).show();
		});
	});
	
});
</script>
<!-- #nav end -->