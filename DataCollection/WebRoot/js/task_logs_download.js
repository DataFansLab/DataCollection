/**
 * DataCollection/task_logs_download.js 2014-01-13 pm 2:12:40 by nano
 */

Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.*' ]);

var commonGrid;

function downloadStatusConverter(v, record) {
	switch (Number(record.raw.status)) {
	case 0: return '成功';
	case 1: return '失败';
	}
}

function dataTypeConverter(v, record) {
	switch (Number(record.raw.data_type)) {
	case 0: return 'PDF文档下载';
	case 1: return 'Web数据抓取';
	}
}

function getForm() {
	var form = Ext.create('Ext.form.Panel', {
		renderTo : Ext.get('conditions'),
		title : '搜索',
		height : 120,
		layout : 'hbox',
		
		frame : true,
		collapsible : true,
		
		bodyPadding : '10 20',
		
		items : [ {
			xtype : 'combo',
			fieldLabel : '数据类别',
			store : {
				xtype : 'Ext.data.Store',
				fields : [ 'name', 'value' ],
				data : [ {
					"value" : "全选",
					"name" : "全选"
				}, {
					"value" : "0",
					"name" : "PDF文档下载"
				}, {
					"value" : "1",
					"name" : "Web数据抓取"
				} ]
			},
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			width : 210,
			labelWidth : 70,
			name : 'data_type',
			value: '全选'
		}, {
			xtype : 'combo',
			fieldLabel : '下载状态',
			store : {
				xtype : 'Ext.data.Store',
				fields : [ 'name', 'value' ],
				data : [ {
					"value" : "全选",
					"name" : "全选"
				}, {
					"value" : "0",
					"name" : "成功"
				}, {
					"value" : "1",
					"name" : "失败"
				} ]
			},
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			width : 170,
			labelWidth : 70,
			name : 'download_status',
			value: '全选'
		}, {
			xtype : 'fieldcontainer',
			fieldLabel : '时间',
			labelWidth : 50,
			layout : 'hbox',
			margin: '0 auto 0 auto',
			labelStyle: 'margin-top: 6px;',
			items : [ {
				xtype : 'datefield',
				name : 'start_date',
				width: 150,
				value: new Date(),
				maxValue: new Date(),
				format:'Y-m-d'
			}, {
				xtype : 'label',
				text : '至',
				margin: '8 5 auto 5'
			}, {
				xtype : 'datefield',
				name : 'end_date',
				width: 150,
				value: new Date(),
				maxValue: new Date(),
				format:'Y-m-d'
			} ]
		}, {
			xtype: 'button',
			text: '查询',
			margin: '7 auto 0 30',
			width: 80,
			handler: function() {
				var values = form.getForm().getValues();
				if (values.data_type == '全选')
					delete values.data_type;
				if (values.download_status == '全选')
					delete values.download_status;
				if (values.start_date == '')
					delete values.start_date;
				if (values.end_date == '')
					delete values.end_date;
				commonGrid.query("/DataCollection/query/task_logs/download", Ext.encode(JSON.stringify(values)));
			}
		} 
		]
	});
}

function createComponents(model) {
	Ext.onReady(function() {
		getForm();
		jsonModel = JSON.parse(model);
		var fields = jsonModel.fields;
		for (var item in fields) {
			if (fields[item].name == 'status')
				fields[item].convert = downloadStatusConverter;
			else if (fields[item].name == 'data_type')
				fields[item].convert = dataTypeConverter;
		}
		var json = {
				renderTo: Ext.get('results'),
				title: '下载日志',
				fields: fields,
				headers: jsonModel.headers,
				flex: jsonModel.flex
			};
		commonGrid = new CommonGrid(json);
		commonGrid.init();
	});
}