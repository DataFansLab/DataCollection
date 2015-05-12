<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>


<div class="ui fluid seven item large inverted blue menu">
	<div class="item" style="color: rgb(244, 241, 241); font-weight: bold; font-size: 20px !important;">下载主页</div>
	<a class="item" href="/DataCollection/page/index-download" target='_blank'>主页</a>
	<div class="ui dropdown item">
		工作平台 <i class="dropdown icon"></i>
		<div class="menu">
			<a class="item" href="/DataCollection/page/upload" target='_blank'>上传文档</a>
			<a class="item" href="/DataCollection/page/bulletinMissing"
				target='_blank'>文档缺失</a> <a class="item"
				href="/DataCollection/page/companyInfo" target='_blank'>公司综合详情</a> <a
				class="item" href="/DataCollection/page/companyList/download"
				target='_blank'>公司代码列表</a> <a class="item"
				href="/DataCollection/page/qcPrompts/download" target='_blank'>QC提示</a>
		</div>
	</div>
	<div class="ui dropdown item">
		工具箱 <i class="dropdown icon"></i>
		<div class="menu">
			<a class="item" id="downloadProcess" href="#">下载程序</a> <a
				class="item" href="/DataCollection/page/taskLogs/download"
				target='_blank'>下载日志</a> <a class="item"
				href="/DataCollection/page/qcLog/download" target='_blank'>QC日志</a>
			<a class="item" href="/DataCollection/page/affairsAssign/download"
				target='_blank'>任务分配</a>
		</div>
	</div>
	<div class="ui dropdown item">
		系统规则 <i class="dropdown icon"></i>
		<div class="menu">
			<a class="item" href="#" target='_blank'>流程图</a> <a class="item"
				href="/DataCollection/page/download/QCRules" target='_blank'>QC规则</a>
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
	$(document)
			.ready(
					function() {
						Ext
								.onReady(function() {
									$("#downloadProcess")
											.click(
													function(e) {
														e.preventDefault();
														Ext
																.create(
																		'Ext.window.Window',
																		{
																			title : '下载程序',
																			height : 280,
																			width : 500,
																			layout : 'fit',
																			resizable : false,
																			html : '<iframe src="/DataCollection/page/downloadProcess" frameborder="no" style="width: 100%; height: 100%"></iframe>'
																		})
																.show();
													});
								});

					});
$('.ui.dropdown')
  .dropdown()
;
</script>
<!-- #nav end -->