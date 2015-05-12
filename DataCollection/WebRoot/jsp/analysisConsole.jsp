<%@ page language="java" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>解析程序</title>

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
			var analysisState = '${analysisState}';
			$("#analysisState").append(analysisState);
			var packageJsonState = '${packageJsonState}';
			$("#packageJsonState").append(packageJsonState);
			if (analysisState == '停止') {
				Ext.create('Ext.Button', {
					text: '启动解析程序',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						Ext.Ajax.request({
							url: '/DataCollection/util/analysisProcess/startAnalysis',
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
					text: '停止解析程序',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						Ext.Ajax.request({
							url: '/DataCollection/util/analysisProcess/stopAnalysis',
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
			if (packageJsonState == '停止') {
				Ext.create('Ext.Button', {
					text: '打包JSON',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						Ext.Ajax.request({
							url: '/DataCollection/util/analysisProcess/startJSON',
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
					text: '停止打包JSON',
					height: 35,
					width: 150,
					renderTo: Ext.get('container'),
					handler: function() {
						Ext.Ajax.request({
							url: '/DataCollection/util/analysisProcess/stopJSON',
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
		});
	});
	</script>

  </head>
  
  <body style="text-align: center; font-size: 18px; font-family: 'Microsoft YaHei'">
  	<p id="analysisState">解析程序：</p>
  	<p id="packageJsonState">打包JSON程序：</p>
    <div id="container"/>
  </body>
</html>
