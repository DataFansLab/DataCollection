
//预加载 内容
Ext.require(  
    [  
        'Ext.grid.*',  
        'Ext.toolbar.Paging',  
        'Ext.data.*'  
    ]
);  

function emailConverter(v, record) {
	if (record.raw.emailAddr == 'null')
		return '空邮箱';
}

Ext.onReady(function(){
            
    //用户邮箱下拉列表中的数据。
    var doc_type_store = Ext.create('Ext.data.Store', {
    	fields: ['id', 'email_addr'], 
    	data : [
    		{"id":"0","email_addr":"空邮箱"},
        	{"id":"1","email_addr":"meizhou@gmail.com"},
        	{"id":"2","email_addr":"tongweigang@gmail.com"},
        	{"id":"3","email_addr":"全部"}
    	]
	});
	
	//操作状态下拉列表中的数据
	var act_status_store = Ext.create('Ext.data.Store',{
		fields:['id','status'],
		data:[
			{'id':'1',status:'全选'},
			{'id':'2',status:'已修改'},
			{'id':'3',status:'已关闭'}
		]
	});
	
	
	Ext.define('Record',
	{
		extend:'Ext.data.Model',
		fields: [
		{name:'id',type:String},
		{name:'companyId'},
		{name:'stockCode'},
		{name:'emailAddr'}
		]
	});

	
	var gridStore = Ext.create('Ext.data.Store',{
		model:'Record',
		autoLoad:false,
		pageSize:10,
		proxy:{
			type:'ajax',
			url:'/DataCollection/query/affairs_assign',
			reader: {
				//数据格式为json
				type: 'json',
				root: 'rows',
				//获取数据总数
				totalProperty: 'totalCount'
			}  
		}
	});
	//gridStore.loadPage(1);
	
	
	/* 
	 * 定义搜索框格式和内容
	 * store会将搜索条件以参数形式用ajax请求传给服务器
	 * 服务器返回的结果会在加载该store的grid中显示
	 * 注意：查询操作是在数据库中进行，并不是在页面中进行的
	 * 
	 */
	var search_panel = Ext.create('Ext.form.Panel',{
		title:'按条件搜索',
		width:'100%',
		margin:'10 0 0 0',
		defaultType: 'textfield',
        frame: true,
        //是否可折叠
        collapsible: false,
        layout: 'column',
        items: [{
            fieldLabel: '股票代码',
            padding:'5 5 10 10',
            labelWidth: 60,
            id: 'stock_id'
         },{
         	fieldLabel: '邮箱',
         	padding:'5 5 10 10',
         	labelWidth: 40,
            xtype:'combobox',
            width:250,
            store:doc_type_store,
            displayField:'email_addr',
            selectOnTab: true,
            id: 'email_addr',
            listeners:{
            	afterrender: function(combo){
            		var firstValue = doc_type_store.getAt(0).get('email_addr');
            		combo.setRawValue(firstValue);
            	}
            }
         },{
         	xtype: 'button',
         	margin:'10 5 20 100',
         	padding: '3 20 3 20',
         	text:'搜索',
         	handler: function () {
            	//获取文本框值
                var stockCode = Ext.getCmp('stock_id').getValue(); 
                var emailAddr = Ext.getCmp('email_addr').getValue();
                
                if(emailAddr == "空邮箱"){
                	emailAddr = "null";
                }
                if(emailAddr == "全部"){
                	emailAddr = "";
                }
                gridStore.proxy.url = '/DataCollection/query/affairs_assign';
					+Ext.encode(emailAddr);

				gridStore.load({
					params:{
						stock_code:Ext.encode(stockCode),
						email_addr:Ext.encode(emailAddr)
					}
				})
				gridStore.proxy.url = '/DataCollection/query/affairs_assign?stock_code='
					+Ext.encode(stockCode)+"&email_addr="
					+Ext.encode(emailAddr);
            }
         }],
		renderTo:'search_panel'			 
	});
	
	
	
	var form = new Ext.form.Panel({
        title: '手动上传Excel',
        bodyPadding: '10',
		frame:true,
		width:'100%',
		height:"100%",
		renderTo: 'upload_form',
		items: [
			{
				//fieldLabel:'本地链接',
				xtype:'filefield',
				labelWidth:60,
				anchor:'100%',
				buttonText:'选择Excel文件...'
        	}
        ],
        buttons:[{
        	text:'上传',
			handler:function(){
				form.submit({
					url:'/DataCollection/excelAnalysis',
        			waitMsg:'文件上传中',
        			success:function(fp,o){
        				Ext.Msg.alert('Success','你的文件"' + o.result.file + '"已经上传成功');               				
        			}
        		})
        	}
        },{
        	text:'取消',
        	handler:function(){
        		$("#cover_div").css("display","none");
        		$("#upload_form").css("display","none");                		
        	}
        }]
	});
	document.getElementById('upload_form').style.display='none';
	
	var affairs_grid = Ext.create('Ext.grid.Panel',{
		width:'100%',
		margin:'20 0 0 0',
		title:'任务分配',
		store:gridStore,
		selModel:new Ext.selection.CheckboxModel(),
		columns: [{
			header :'ID',
			width:100,
			dataIndex:'id'
		},{
			header :'公司ID',
			width:200,
			dataIndex:'companyId'
		},{
			header:'股票代码',
			width:300,
			dataIndex:'stockCode'
		},{
			header:'邮箱',
			width:450,
			dataIndex:'emailAddr'
		}],
		//定义表格的操作栏
		dockedItems:[
			{
				xtype:'toolbar',
				items:[{
					iconCls:'icon-delete-filled',
					text:"删除",
					itemId:'delete',
					scope:this,
					handler:deleteAction
				},{
					iconCls:'icon-add-filled',
					text:"上传",
					itemId:'upload',
					scope:this,
					handler: function (){
						$("#cover_div").css("display","block");
						$("#upload_form").css("display","block");
					}
				}
				]
			}],
			bbar: Ext.create('Ext.PagingToolbar', {
				store:gridStore,
				displayInfo: true,
				displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
				emptyMsg: "没有数据"
			}),
		renderTo:'affairs_grid_div'
	});
	
	function deleteAction(){
		//var cp = new CommonPortlet();
		//cp.deleteOnGrid(affairs_grid,gridStore);
	}
	
});
			