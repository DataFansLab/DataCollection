/**
 * DataCollection/bulletin_missing.js
 * 2014-01-15 下午3:13:47 by nano
 */

Ext.require([ 'Ext.window.Window', 'Ext.panel.Panel', 'Ext.toolbar.*',
		'Ext.form.*', 'Ext.button.*', 'Ext.grid.*', 'Ext.data.*',
		'Ext.util.*']);

var commonGrid;

function getForm() {
	var form = Ext.create('Ext.form.Panel', {
		renderTo: Ext.get('conditions'),
		title : '搜索',
		width : '100%',
		height : 120,
		layout : 'hbox',
		padding:'5 5 10 10',

		frame : true,
		collapsible : true,

		bodyPadding : '10 20',
		
		defaults : {
			anchor : '98%',
			msgTarget : 'side',
			allowBlank : true
		},
		
	    items: [
	            {	
            		fieldLabel: '代码/名称',
	                xtype:	'combo',
	                labelWidth: 100,
	                width:320,
	                store: ComboData.stock_info_query_store,
	                displayField:'stock_info',
	                typeAhead: false,
	                name:'stock_id',
	                id:'stock_id',
	                model:'remote', 
	                minChars:2,
					enableKeyEvents:true, //允许键盘输入事件
	                hideTrigger: true, 
	                listeners:{
	                	keyup:function(textField,e){
			                //如果键盘输入不是方向键，
			                if(!e.isNavKeyPress()){
			                	ComboData.stock_info_query_store.load({params:{params:Ext.encode(textField.getRawValue())}});
			                }
			            },
			            select: function(combo,record,index){
							var stockInfo = record[0].get('stock_info').split("|");
							Ext.getCmp('stock_id').setValue(stockInfo[1].replace(/(^\s*)|(\s*$)/g, ""));
						}
	                }
	            		
	            		
	            },
	            {
	            	xtype: 'datefield',
	            	fieldLabel: '会计结算日',
	            	width: 220,
	            	labelWidth: 80,
	            	name: 'accounting_date',
	            	id: 'accounting_date',
	            	value: new Date(),
					maxValue: new Date(),
					format: 'Y-m-d'
	            },
	            {
	            	xtype: 'combobox',
	            	fieldLabel: '文档类型',
	            	store: Ext.create('Ext.data.Store', {
	            		fields: ['code', 'name'],
	            		data: [
	            		       {'code': '全选', 'name': '全选'},
	            		       {'code': '年报', 'name': '年报'},
	            		       {'code': '第一季度季报', 'name': '第一季度季报'},
	            		       {'code': '半年报', 'name': '半年报'},
	            		       {'code': '第三季度季报', 'name': '第三季度季报'}]
	            	}),
	            	width: 220,
	            	labelWidth: 80,
	            	queryModel: 'local',
	            	displayField: 'name',
	            	valueField: 'code',
	            	layout: 'hbox',
	            	name: 'bulletin_type',
	            	value: '全选'
	            	
	            }, {
	            	xtype: 'button',
	            	text : '查询',
	            	margin: '7 auto 0 10',
	            	width: 80,
	            	id: 'queryButton',
	            	listeners: {
	            		'click': function() {
		    				var values = form.getForm().getValues();
		    				if (values.stock_id == '')
		    					delete values.stock_id;
		    				if (values.accounting_date == '')
		    					delete values.accounting_date;
		    				if (values.bulletin_type == '全选')
		    					delete values.bulletin_type;
		    				commonGrid.query("/DataCollection/query/bulletin/missing", Ext.encode(JSON.stringify(values)));
		    			}
	            	}
	            }]
	});
}

function createComponents(model, flag) {
	Ext.onReady(function() {
		getForm();
		var jsonModel = JSON.parse(model);
		var json = {
				renderTo : Ext.get('results'),
				title : '缺失文档',
				fields : jsonModel.fields,
				headers : jsonModel.headers,
				flex: jsonModel.flex
			};
		commonGrid = new CommonGrid(json, [{
			xtype : 'button',
			text : '删除',
			iconCls : 'icon-delete-filled',
			handler: function(){
				var operateHandler = new GridOperate();
     			var data = commonGrid.getGrid().getSelectionModel().getSelection();
     			if (data.length != 0) {
         			var url ="/DataCollection/delete/bulletin_missing";
        			operateHandler.deleteOnGrid(data,commonGrid.getGrid().store,url,'id');
     			} else Ext.Msg.alert('提示', '未选择数据');
			}
		}, {
			xtype : 'button',
			text : '保存',
			iconCls : 'icon-copy',
			handler: function(){
				var operateHandler = new GridOperate();
     			var grid = commonGrid.getGrid();
     			operateHandler.alterOnGrid(grid, grid.store, '/DataCollection/update/bulletin_missing');
			}
		}]);
		commonGrid.init();
		
		if (flag == '1') {
			Ext.getCmp('accounting_date').setValue('');
			Ext.getCmp('queryButton').fireEvent('click');
		}
	});
}