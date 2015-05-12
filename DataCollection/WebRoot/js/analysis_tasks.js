/**
 * DataCollection/analysis_tasks.js
 * 2014-2-25 下午5:22:23 by nano
 */

Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.*' ]);

var commonGrid;

function analysisStatusConverter(v, record) {
	switch (Number(record.raw.analysis_state)) {
	case 0: return '未解析';
	case 1: return '完全解析';
	case 2: return '部分解析';
	case 3: return '已处理未解析';
	}
}

function bulletinAnalysisPageLinkConverter(v, record) {
	if (record.raw.analysis_state != 0 && record.raw.analysis_state != 3)
		return '<a href="/DataCollection/page/docAnalysis?bulletin_id=' + record.raw.doc_id + '" target="_blank">查看</a>';
	else return '--';
}

function nameComparePageLinkConverter(v, record) {
	//return '<a href="#" target="_blank">查看</a>';
	return '--';
}

function aorQcNumConverter(v, record) {
	if (v != '0')
		return '<a href="/DataCollection/page/qcPrompts/analysis?type=0&bulletin_id=' + record.raw.doc_id + '" target="_blank">' +
		v + '</a>';
	return v;
}

function cssQcNumConverter(v, record) {
	if (v != '0')
		return '<a href="/DataCollection/page/qcPrompts/analysis?type=1&bulletin_id=' + record.raw.doc_id + '" target="_blank">' +
		v + '</a>';
	return v;
}

function docIdConverter(v, record) {
	return '<a href="/DataCollection/resources/pdfviewer/web/viewer.html?file=' + record.raw.doc_id + 
	'" target="_blank">' + record.raw.doc_id + '</a>';
}

function getForm() {
	var form = Ext.create('Ext.form.Panel', {
		renderTo : Ext.get('conditions'),
		title : '搜索',
		layout : 'hbox',
		
		frame : true,
		collapsible : true,
		
		bodyPadding : '10 5',
		
		items: [{
			xtype : 'textfield',
			fieldLabel : '文档代码',
			width: 160,
			labelWidth: 70,
			emptyText: 'DOC0000',
			name: 'doc_id'
		}, {
			xtype : 'textfield',
			fieldLabel : '市值',
			width: 160,
			labelWidth: 70,
			hidden: true,
			id: 'priority',
			name: 'priority',
			value: ''
		}, {
			xtype : 'textfield',
			fieldLabel : 'home',
			width: 160,
			labelWidth: 70,
			hidden: true,
			id: 'home',
			name: 'home',
			value: '0'
		}, {
			xtype : 'textfield',
			fieldLabel : 'qc_state',
			width: 160,
			labelWidth: 70,
			hidden: true,
			id: 'qc_state',
			name: 'qc_state',
			value: ''
		}, 
		{
            fieldLabel: '代码/名称',
            xtype:	'combo',
            labelWidth: 70,
            width:200,
            store: ComboData.stock_info_query_store,
            displayField:'stock_info',
            typeAhead: false,
            name:'stock_id',
            id:'stock_id',
            model:'remote', 
            minChars:2,
			enableKeyEvents:true, 
            hideTrigger: true, 
            listeners:{
            	keyup:function(textField,e){
	                if(!e.isNavKeyPress()){
	                	ComboData.stock_info_query_store.load({params:{params:Ext.encode(textField.getRawValue())}});
	                }
	            },
	            select: function(combo,record,index){
					var stockInfo = record[0].get('stock_info').split("|");
					Ext.getCmp('stock_id').setValue(stockInfo[1].replace(/(^\s*)|(\s*$)/g, ""));
				}
            }
        },{
			xtype : 'combo',
			fieldLabel : '文档状态',
			store : {
				xtype : 'Ext.data.Store',
				fields : [ 'name', 'value' ],
				data : [ {
					"value" : "-1",
					"name" : "全选"
				}, {
					"value" : "0",
					"name" : "未解析"
				}, {
					"value" : "1",
					"name" : "完全解析"
				}, {
					"value" : "2",
					"name" : "部分解析"
				}, {
					"value" : "3",
					"name" : "已处理未解析"
				} ]
			},
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			width : 200,
			labelWidth : 70,
			name : 'bulletin_status',
			value: '-1',
			listeners: {
				'select': function(combo, records) {
					var value = records[0].data.value;
					if (value == '0') {
						Ext.getCmp('start_date').setDisabled(true);
						Ext.getCmp('end_date').setDisabled(true);
					} else {
						Ext.getCmp('start_date').setDisabled(false);
						Ext.getCmp('end_date').setDisabled(false);
					}
				}
			}
		}, {
			xtype : 'fieldcontainer',
			fieldLabel : '解析日期',
			labelWidth : 70,
			layout : 'hbox',
			margin: '0 auto 0 auto',
			labelStyle: 'margin-top: 6px;',
			items : [ {
				xtype : 'datefield',
				name : 'start_date',
				id: 'start_date',
				width: 130,
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
				id: 'end_date',
				width: 130,
				value: new Date(),
				maxValue: new Date(),
				format: 'Y-m-d'
			}]
		}, {
			xtype: 'button',
			text: '查询',
			margin: '7 auto 0 18',
			width: 60,
			id: 'queryButton',
			listeners: {
				'click': function() {
					var values = form.getForm().getValues();
					if (values.doc_id == '')
						delete values.doc_id;
					if (values.stock_id == '')
						delete values.stock_id;
					if (values.bulletin_status == '全选')
						delete values.bulletin_status;
					if (values.start_date == '')
						delete values.start_date;
					if (values.end_date == '')
						delete values.end_date;
					commonGrid.query("/DataCollection/query/analysis/taskList", Ext.encode(JSON.stringify(values)));
				}
			}
		}]
	});
}

function createComponents(model, p, day, qcState) {
	Ext.onReady(function(){
		getForm();
		jsonModel = JSON.parse(model);
		var fields = jsonModel.fields;
		for (var item in fields) {
			if (fields[item].name == 'analysis_state')
				fields[item].convert = analysisStatusConverter;
			else if (fields[item].name == 'aor_link')
				fields[item].convert = bulletinAnalysisPageLinkConverter;
			else if (fields[item].name == 'css_link')
				fields[item].convert = nameComparePageLinkConverter;
			else if (fields[item].name == 'doc_id')
				fields[item].convert = docIdConverter;
			else if (fields[item].name == 'aor_qc_num')
				fields[item].convert = aorQcNumConverter;
			else if (fields[item].name == 'css_qc_num')
				fields[item].convert = cssQcNumConverter;
		}
		var json = {
				renderTo: Ext.get('results'),
				title: '解析任务',
				fields: fields,
				headers: jsonModel.headers,
				flex: jsonModel.flex
			};
		commonGrid = new CommonGrid(json);
		commonGrid.init();
		
		if (p != 'null' && day != 'null' && qcState != 'null') {
	    	Ext.getCmp('priority').setValue(p);
	    	Ext.getCmp('home').setValue('1');
	    	Ext.getCmp('qc_state').setValue(qcState);
	    	var date = new Date();
	    	var queryDate = new Date();
			queryDate.setTime(date.getTime() - 1000*60*60*24*Number(day));
			Ext.getCmp('end_date').setValue(queryDate);
	    	if (day != '5') {
	    		Ext.getCmp('start_date').setValue(queryDate);
	    	} else Ext.getCmp('start_date').setValue('');
	    		
	    	Ext.getCmp('queryButton').fireEvent('click');
	    /* reset query conditions */
	    	Ext.getCmp('home').setValue('0');
	    }
	});
}