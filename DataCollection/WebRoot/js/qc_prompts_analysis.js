/**
 * DataCollection/qc_prompts.js 2014/1/7/1:54:20 pm. by nano
 */
Ext.require([ 'Ext.window.Window', 'Ext.panel.Panel', 'Ext.toolbar.*',
		'Ext.tree.Panel', 'Ext.container.Viewport',
		'Ext.container.ButtonGroup', 'Ext.form.*', 'Ext.tab.*', 'Ext.slider.*',
		'Ext.layout.*', 'Ext.button.*', 'Ext.grid.*', 'Ext.data.*',
		'Ext.util.*',

		'Ext.perf.Monitor' ]);

function docIdConverter(v, record) {
	return '<a href="/DataCollection/resources/pdfviewer/web/viewer.html?file=' + v + 
	'" target="_blank">' + v + '</a>';
}

function qcPhaseConverter(v, record) {
	switch (Number(v)) {
	case 0: return '解析';
	case 1: return '别名匹配';
	}
}

function qcTypeConverter(v, record) {
	var qcPhase = record.raw.qc_phase;
	var qcType = v;
	if (qcPhase == '1') {
		var separator = qcType.indexOf(',');
		if (qcType.substring(0, separator) != '0') {
			return '缺失信息';
		}
		if (qcType.substring(separator + 1) != '0')
			return '冲突';
		return '--';
	} else {
		switch (Number(qcType)) {
		case 1: return '缺失信息';
		case 2: return '冲突';
		default: return '--';
		}
	}
}

function promptsLocationConverter(v, record) {
	var location = v;
	var separator = location.indexOf(',');
	var loc = '';
	switch (Number(location.substring(0, separator))) {
	case 0: loc += '现金流量表'; break;
	case 1: loc += '资产负债表'; break;
	case 2: loc += '利润表'; break;
	case 4: loc += '合并现金流量表'; break;
	case 5: loc += '合并资产负债表'; break;
	case 6: loc += '合并例利润表'; break;
	default: loc += '其他附注表';
	}
	loc += (' | PDF文档页码：' + location.substring(separator + 1));
	return loc;
}

var commonGrid;

var analysisStore = Ext.create('Ext.data.Store', {
	fields : [ 'name', 'value' ],
	data : [ {
		"value" : "全选",
		"name" : "全选"
	}, {
		"value" : "0",
		"name" : "缺失信息"
	}, {
		"value" : "1",
		"name" : "冲突"
	}]
});

var nameCompareStore = Ext.create('Ext.data.Store', {
	fields : [ 'name', 'value' ],
	data : [ {
		"value" : "全选",
		"name" : "全选"
	}, {
		"value" : "0",
		"name" : "缺失信息"
	}, {
		"value" : "1",
		"name" : "会计等式"
	}]
});

var qcCombo = Ext.create('Ext.form.ComboBox', {
	fieldLabel: 'QC类别',
	store: analysisStore,
	queryMode : 'local',
	displayField : 'name',
	valueField : 'value',
	width : 180,
	labelWidth : 70,
	margin: '10',
	value: '全选',
	name: 'qc_type',
	disabled: true
});

function getForm() {

	var form = Ext.create('Ext.form.Panel', {
		renderTo : Ext.get('conditions'),
		title : '搜索',
		width : '100%',
		height : 160,
		layout : 'vbox',

		frame : true,
		collapsible : true,

		bodyPadding : '10 20',

		defaults : {
			anchor : '98%',
			msgTarget : 'side',
			allowBlank : true
		},

		items : [ {
			xtype : 'fieldcontainer',
			labelWidth : 90,
			layout : 'hbox',
			margin : '0 auto 0 auto',
			labelStyle : 'margin-top: 6px;',
			items: [
				{
					fieldLabel : '文档ID',
					xtype : 'textfield',
					name : 'doc_id',
					width : 170,
					labelWidth : 50,
					margin: '10 20 auto',
					emptyText : '#DOC0000'
				}, {
					fieldLabel : '公司ID',
					xtype : 'textfield',
					name : 'com_id',
					width : 170,
					labelWidth : 50,
					margin: '10 20 auto',
					emptyText : '#COM0000'
				}, {
					fieldLabel : 'QC提示时间',
					xtype : 'fieldcontainer',
					labelWidth : 90,
					layout : 'hbox',
					margin: '5 20 auto',
					labelStyle : 'margin-top: 6px;',
					items : [ {
						xtype : 'datefield',
						name : 'start_date',
						width : 150,
						value: new Date(),
						maxValue: new Date(),
						format: 'Y-m-d'
					}, {
						xtype : 'label',
						text : '至',
						margin : '8 5 auto 5'
					}, {
						xtype : 'datefield',
						name : 'end_date',
						width : 150,
						value: new Date(),
						maxValue: new Date(),
						format: 'Y-m-d'
					} ]
				}
			]
		}, {
			xtype : 'fieldcontainer',
			labelWidth : 90,
			layout : 'hbox',
			margin : '10 auto 0 auto',
			items: [{
				xtype: 'combo',
				fieldLabel: 'QC阶段',
				store: {
					xtype: 'Ext.data.Store',
					fields : [ 'name', 'value' ],
					data : [ {
						"value" : "全选",
						"name" : "全选"
					}, {
						"value" : "0",
						"name" : "解析"
					}, {
						"value" : "1",
						"name" : "别名匹配"
					}]
				},
				queryMode : 'local',
				displayField : 'name',
				valueField : 'value',
				width : 200,
				labelWidth : 70,
				margin: '10 0 0 0',
				value: '全选',
				name: 'phase',
				listeners: {
					'select': function(combo, records) {
						var name = records[0].data.name;
						if (name == '全选') {
							qcCombo.setDisabled(true);
						} else if (name == '解析') {
							qcCombo.bindStore(analysisStore);
							qcCombo.setDisabled(false);
						} else if (name == '别名匹配') {
							qcCombo.bindStore(nameCompareStore);
							qcCombo.setDisabled(false);
						}
					}
				}
			}, qcCombo, {
				xtype: 'button',
				text : '查询',
				margin: '12 auto 0 30',
				width: 80,
				handler : function() {
					var values = form.getForm().getValues();
					if (values.doc_id == '')
						delete values.doc_id;
					if (values.com_id == '')
						delete values.com_id;
					if (values.start_date == '')
						delete values.start_date;
					if (values.end_date == '')
						delete values.end_date;
					if (values.phase == '全选') {
						delete values.phase;
						delete values.qc_type;
					} else if (values.qc_type == '全选')
						delete values.qc_type;
						
					commonGrid.query("/DataCollection/query/analysis/qcPrompts",
							Ext.encode(JSON.stringify(values)));
				}
			}]
		}]
	});

}

function createComponents(model, bulletinId, qcPhase) {
	Ext.onReady(function() {
		getForm();
		jsonModel = JSON.parse(model);
		var fields = jsonModel.fields;
		for (var item in fields) {
			if (fields[item].name == 'qc_phase')
				fields[item].convert = qcPhaseConverter;
			else if (fields[item].name == 'error_type')
				fields[item].convert = qcTypeConverter;
			else if (fields[item].name == 'qc_pos')
				fields[item].convert = promptsLocationConverter;
			else if (fields[item].name == 'bulletin_id')
				fields[item].convert = docIdConverter;
		}
		var json = {
			renderTo : Ext.get('results'),
			title : 'QC提示',
			fields : fields,
			headers : jsonModel.headers,
			flex: jsonModel.flex
		};
		
		commonGrid = new CommonGrid(json,[{
     		iconCls:'icon-delete-filled',
     		text:"删除",
     		itemId:'delete',
     		scope:this,
     		handler:function(){
     			var operateHandler = new GridOperate();
     			var data = commonGrid.getGrid().getSelectionModel().getSelection();
     			if (data.length != 0) {
     				Ext.Array.each(data,function(record){
     					var id_temp = record.data.bulletin_id.split(">")[1];
     					id_temp = id_temp.split("<")[0];
         				record.data.bulletin_id = id_temp + "_" + record.raw.qc_pos.split(",")[0];
         				console.log("bulletin_id : " + record.data.bulletin_id);
         			});
         			var url ="/DataCollection/delete/qc_prompts/analysis";
        			operateHandler.deleteOnGrid(data,commonGrid.getGrid().store,url,'bulletin_id');
     			} else Ext.Msg.alert('提示', '未选择数据');

     		}
     	}]);
		commonGrid.init();

		if (bulletinId != 'null' && qcPhase != 'null') {
			commonGrid.query("/DataCollection/query/analysis/qcPrompts",
					Ext.encode('{"doc_id": "' + bulletinId + '", "phase": "' + qcPhase + '"}'));
		}
	});
}