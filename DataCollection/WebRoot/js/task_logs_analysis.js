/**
 * DataCollection/task_logs_analysis.js
 * 2013-01-13 pm 2:18:08 by nano
 */

Ext.require(  
    [  
        'Ext.grid.*',  
        'Ext.toolbar.Paging',  
        'Ext.data.*'
    ]
); 

function analysisStatusConverter(v, record) {
	switch (Number(record.raw.status)) {
	case 1: return '完全解析';
	case 2: return '部分解析';
	case 3: return '已处理但出错';
	}
}

function getForm() {
	var form = Ext.create('Ext.form.Panel', {
		renderTo : Ext.get('conditions'),
		title : '搜索',
		height : 100,
		layout : 'hbox',
		
		frame : true,
		collapsible : true,
		
		bodyPadding : '10 20',
		
		items : [{
			xtype : 'combo',
			fieldLabel : '解析状态',
			store : {
				xtype : 'Ext.data.Store',
				fields : [ 'name', 'value' ],
				data : [ {
					"value" : "全选",
					"name" : "全选"
				}, {
					"value" : "1",
					"name" : "完全解析"
				}, {
					"value" : "2",
					"name" : "部分解析"
				}, {
					"value" : "3",
					"name" : "已处理但出错"
				} ]
			},
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			width : 220,
			labelWidth : 70,
			name : 'analysis_status',
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
				format: 'Y-m-d'
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
				format: 'Y-m-d'
			} ]
		}, {
			xtype: 'button',
			text: '查询',
			margin: '7 auto 0 30',
			width: 80,
			handler: function() {
				var values = form.getForm().getValues();
				if (values.analysis_status == '全选')
					delete values.analysis_status;
				if (values.start_date == '')
					delete values.start_date;
				if (values.end_date == '')
					delete values.end_date;
				commonGrid.query("/DataCollection/query/analysis/logs", Ext.encode(JSON.stringify(values)));
			}
		} ]
	});
}

function createComponents(model) {
	Ext.onReady(function() {
		getForm();
		jsonModel = JSON.parse(model);
		var fields = jsonModel.fields;
		for (var item in fields) {
			if (fields[item].name == 'status')
				fields[item].convert = analysisStatusConverter;
		}
		var json = {
				renderTo: Ext.get('results'),
				title: '解析日志',
				fields: jsonModel.fields,
				headers: jsonModel.headers,
				flex: jsonModel.flex
			};
		commonGrid = new CommonGrid(json);
		commonGrid.init();
	});
}