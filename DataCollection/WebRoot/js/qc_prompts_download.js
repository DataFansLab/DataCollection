/**
 * DataCollection/qc_prompts.js 2014/1/7/1:54:20 pm. by nano
 */
Ext.require([ 'Ext.window.Window', 'Ext.panel.Panel', 'Ext.toolbar.*',
		'Ext.tree.Panel', 'Ext.container.Viewport',
		'Ext.container.ButtonGroup', 'Ext.form.*', 'Ext.tab.*', 'Ext.slider.*',
		'Ext.layout.*', 'Ext.button.*', 'Ext.grid.*', 'Ext.data.*',
		'Ext.util.*', 'Ext.perf.Monitor' ]);

var commonGrid;

function docIdConverter(v, record) {
	return '<a href="/DataCollection/resources/pdfviewer/web/viewer.html?file=' + record.raw.bulletin_id + 
	'" target="_blank">' + record.raw.bulletin_id + '</a>';
}

(function($){
	$.getUrlParam = function(name)
	{
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
})(jQuery);

var BULLETIN_ID = $.getUrlParam('bulletin_id');

function getForm() {

	var form = Ext.create('Ext.form.Panel', {
		renderTo : Ext.get('conditions'),
		title : '搜索',
		width : '100%',
		height : 120,
		layout : 'hbox',
		frame : true,
		collapsible : true,
		bodyPadding : '10 5',
		defaults : {
			anchor : '98%',
			msgTarget : 'side',
			allowBlank : true
		},

		items : [ {
			fieldLabel : '文档ID',
			xtype : 'textfield',
			name : 'bulletin_id',
			width : 170,
			labelWidth : 50,
			emptyText : 'DGP514208'
		}, /*{
			fieldLabel : '公司ID',
			xtype : 'textfield',
			name : 'company_id',
			width : 170,
			labelWidth : 50,
			emptyText : 'CSZ000001'
		},*/
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
			fieldLabel : 'QC提示时间',
			xtype : 'fieldcontainer',
			labelWidth : 90,
			layout : 'hbox',
			margin : '0 auto 0 auto',
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
		}, {
			xtype: 'combo',
			fieldLabel: 'QC类别',
			name:"error_type",
			store: {
				xtype: 'Ext.data.Store',
				fields : [ 'name', 'value' ],
				data : [ {
					"value" : "全选",
					"name" : "全选"
				}, {
					"value" : "缺失信息",
					"name" : "缺失信息"
				}, {
					"value" : "冗余",
					"name" : "冗余"
				}, {
					"value" : "冲突",
					"name" : "冲突"
				} ]
			},
			queryMode : 'local',
			displayField : 'name',
			valueField : 'value',
			width : 180,
			labelWidth : 70,
			value: '全选'
		}],
		buttons : [ {
			text : '查询',
			handler : function() {
				var values = form.getForm().getValues();
				if (values.bulletin_id == '')
					delete values.bulletin_id;
				if (values.company_id == '')
					delete values.company_id;
				if (values.error_type == '全选')
					delete values.error_type;
				if (values.start_date == '')
					delete values.start_date;
				if (values.end_date == '')
					delete values.end_date;
				commonGrid.query("/DataCollection/query/qc_prompts/download", Ext.encode(JSON.stringify(values)));
			}
		} ],
		listeners: {
		    afterlayout: function(){
		    	if(BULLETIN_ID != null)
		    		commonGrid.query("/DataCollection/query/qc_prompts/download", Ext.encode(JSON.stringify({bulletin_id:BULLETIN_ID})));
		    }
		}
	});
}

function createComponents(model) {
	Ext.onReady(function() {
		
		var jsonModel = JSON.parse(model);
		var fields = jsonModel.fields;
		for (item in fields) {
			if (fields[item].name == 'bulletin_id')
				fields[item].convert = docIdConverter;
		} 
		var json = {
			renderTo : Ext.get('results'),
			title : 'QC提示',
			fields : fields,
			headers : jsonModel.headers,
			flex: jsonModel.flex
		};
		commonGrid = new CommonGrid(json, [ {
			xtype : 'button',
			text : '删除',
			iconCls : 'icon-delete-filled',
			/*handler: function(){
				var data = this.up("grid").getSelectionModel().getSelection();
				url = '/DataCollection/delete/qcPrompts';
				var operateHandler = new GridOperate();
				operateHandler.deleteOnGrid(data,this.up("grid").getStore(),url,'bulletin_id');
			}*/
			handler:function(){
     			var operateHandler = new GridOperate();
     			var data = commonGrid.getGrid().getSelectionModel().getSelection();
     			if (data.length != 0) {
     				Ext.Array.each(data,function(record){
     					var id_temp = record.data.bulletin_id.split(">")[1];
     					id_temp = id_temp.split("<")[0];
         				record.data.bulletin_id = id_temp;
         				console.log("bulletin_id : " + record.data.bulletin_id);
         			});
         			var url ="/DataCollection/delete/qcPrompts/download";
        			operateHandler.deleteOnGrid(data,commonGrid.getGrid().store,url,'bulletin_id');
     			} else Ext.Msg.alert('提示', '未选择数据');
     		}
			
		} ]);
		commonGrid.init();
		
		getForm();
	});
}