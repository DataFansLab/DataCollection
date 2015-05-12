/**
 * DataCollection/js/companyInfo.js
 * 下午4:44:22
 */

Ext.require(
    		[
        		'Ext.grid.*',  
        		'Ext.toolbar.Paging',  
        		'Ext.data.*'
    		]
		);

var baseUrl = '/DataCollection/query/companyInfo/';
var stockId = '';

Ext.onReady(function() {
	Ext.create('Ext.form.Panel',{
	    title:'按条件搜索',
	    x:20,
		width:1000,
		defaultType: 'textfield',
    	frame: true,
    	//是否可折叠
        collapsible: false,
        layout: 'column',
        margin: '10 0 10 0',
        items: [{
            fieldLabel: '代码/名称',
            xtype:	'combo',
            labelWidth: 70,
            width:320,
            padding:'5 5 10 10',
            store: ComboData.stock_info_query_store,
            displayField:'stock_info',
            typeAhead: false,
            name:'company_id',
            id:'company_id',
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
					Ext.getCmp('company_id').setValue(stockInfo[1].replace(/(^\s*)|(\s*$)/g, ""));
				}
            }
        }, {
            xtype: 'button',
            text: '搜索',
            margin:'13 5 20 100',
 			padding: '3 20 3 20',
            handler: function () {
//            	store.loadPage(1);
            	//获取文本框值
                var company_id = Ext.getCmp('company_id').getValue(); 
				if (company_id != null) {
					//传递参数
					stockId = company_id;
					gridStore.proxy.url = baseUrl + stockId + '/jichu';
					gridStore.load();
                }
            }
        }],
	 renderTo:'search_panel'			 
	});	
	
	var treeStore = Ext.create('Ext.data.TreeStore', {
		root: {
			//expanded: true,
			children: [
					{text: "公司资料", expanded: true, children: [
						{text: "基础信息", value: 'jichu', leaf: true },
						{text: "证券信息", value: 'zhengquan', leaf: true },
						{text: "工商信息", value: 'gongshang', leaf: true },
						{text: "联系方式", value: 'lianxi', leaf: true },
						{text: "经营范围", value: 'fanwei', leaf: true },
						{text: "公司简介", value: 'jianjie', leaf: true }]
    			}
//					,
//				{text: "高管信息", children: [
//   					{text: "历届董事会成员", value: 'lijiechengyuan', leaf: true },
//   					{text: "历届监事会成员", leaf: true}]
//					},
//					{text: "发行与分配", children:[
//						{text:"新股发行", leaf:true},
//						{text:"增发", leaf:true},
//						{text:"可转债发行",leaf:true},
//						{text:"分红配股",leaf:true},
//						{text:"募资投向",leaf:true}]
//					},
//					{text:"股权分析", children:[
//						{text:"股本结构",leaf:true},
//						{text:"十大股东",leaf:true},
//						{text:"十大流通股股东",leaf:true},
//						{text:"基金持股",leaf:true},
//						{text:"股东人数统计",leaf:true},
//						{text:"平均持股统计",leaf:true}]
//					},
//    			{text: "经营分析", children:[
//    				{text:"收入分布", leaf:true}
//    			]},
//    			{text:"盈利预测", children:[
//    				{text:"盈利预测", leaf:true}
//    			]}
    			
			]
		}
	});
	
	var gridStore = Ext.create('Ext.data.Store', {
		fields: ['key', 'value'],
		proxy: {
			type: 'ajax',
			url: '',
			reader: {
				type: 'json',
				root: 'row'
			}
		},
		autoLoad: false
	});
	
	
	var grid = Ext.create('Ext.grid.Panel',{
		width:730,
		height:400,
		hideHeaders:true,
		stripeRows: true,
		id: 'grid',
		selModel:Ext.create('Ext.selection.CheckboxModel'),
		selType: 'cellmodel',
		plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
   			clicksToEdit: 1
			})],
		columnLines: true,
		margin: '20 0 0 0',
		renderTo:'info_panel',
		store:gridStore,
		columns:[{
			width:200,
			dataIndex:'key',
			field:'textfield'
		},{
			width:480,
			dataIndex:'value',
			field:'textfield',
			renderer: function(value,meta,record){
            	meta.style = 'white-space:normal;';
            	return value;
			}
		}],
		dockedItems:[
		{
			xtype:'toolbar',
			items:[{
				iconCls:'icon-edit',
				text:"编辑",
				id:"edit",
				name:"unedit",
				scope:this,
				handler: function(){
					Ext.getCmp("edit").name = "editable";
				}
				
			},{
				iconCls:"icon-copy",
				text:"上传"
			}]
		}
		],
		listeners : {
			beforeedit: function(){
				if(Ext.getCmp("edit").name == "unedit")
					return false;
				else
					return true;
			}
		}
	});
	
	Ext.LoadMask(grid, {
		msg: '正在加载，请稍候...',
		store: gridStore
	});

	var tree = Ext.create('Ext.tree.Panel', {
		title: '综合详情',			
		width: 250,
		height: 400,
		store: treeStore,
		rootVisible: false,
		margin: '20 20 0 0',
		renderTo: 'tree_panel', 
		id: 'tree',
		listeners:{
			itemclick: function(view,record,item,index,e,eOpts){
				if (record.raw.value && stockId != '') {
					gridStore.proxy.url = baseUrl + stockId + '/' + record.raw.value;
					gridStore.load();
				}
				
				
//				Ext.getCmp("edit").name = "unedit";
//				if(record.raw.leaf){
//					var parentNode = record.parentNode.get('text');
//					var infoArray = [];
//					grid.getStore().loadData(infoArray);
//					for( item in jsonData[parentNode]){
//						if(item == record.get('text')){
//							for( line in jsonData[parentNode][item]){
//								infoArray.push([line,jsonData[parentNode][item][line]]);   									
//							}
//							grid.getStore().loadData(infoArray);
//							return;
//						}
//					}
//				}else{ }
			},
			itemexpand : function(){    					
			}
		}
	});
	
	
//	((Ext.getCmp('company_id').getValue() == '') ? )
});