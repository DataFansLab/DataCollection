<%@ page language="java" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>下载程序</title>

	<script type="text/javascript" src="/DataCollection/resources/nav/js/jquery.min.js"></script>
	<script type="text/javascript" src="/DataCollection/js/ext-4.1.0-gpl/ext-all.js"></script>
	<link rel="stylesheet" type="text/css" href="/DataCollection/resources/css/clifton-blue.css" />
	<style type="text/css">
		span {
			font-family: 'Microsoft YaHei' !important;
			font-size: 16px !important
		}
		p {
			margin: 30px auto 30px auto;
			font-weight: bold;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		Ext.onReady(function() {
			var todayState = '${todayState}';
			$("#todayState").append(todayState);
			var beforeTodayState = '${beforeTodayState}';
			$("#beforeTodayState").append(beforeTodayState);
			if (todayState == '停止') {
				Ext.create('Ext.Button', {
					text: '下载今日文档',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						Ext.Ajax.request({
							url: '/DataCollection/util/downloadProcess/startToday',
							success: function(response) {
								Ext.Msg.alert('提示', response.responseText, function(btn, text) {
									if (btn == 'ok') {
										window.location.reload();
									}
								});
							}
						});
					}
				});
			} else {
				Ext.create('Ext.Button', {
					text: '停止下载今日文档',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						Ext.Ajax.request({
							url: '/DataCollection/util/downloadProcess/stopToday',
							success: function(response) {
								Ext.Msg.alert('提示', response.responseText, function(btn, text) {
									if (btn == 'ok') {
										window.location.reload();
									}
								});
							}
						});
					}
				});
			} 
			if (beforeTodayState == '停止') {
				Ext.create('Ext.Button', {
					text: '下载历史文档',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						
					}
				});
			} else {
				Ext.create('Ext.Button', {
					text: '停止下载历史文档',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						
					}
				});
			}
		});
	});
	</script>

  </head>
  
  <body style="text-align: center; font-size: 18px; font-family: 'Microsoft YaHei'">
  	<p id="todayState">今日文档：</p>
  	<p id="beforeTodayState">历史文档：</p>
    <div id="container"/>
  </body>
</html>
