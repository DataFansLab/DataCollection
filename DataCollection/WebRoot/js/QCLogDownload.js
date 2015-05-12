//预加载 内容
Ext.require(  
    [  
        'Ext.grid.*',  
        'Ext.toolbar.Paging',  
        'Ext.data.*'  
    ]
);  

var baseUrl = '/DataCollection/query/download/qclog';

function operateStatusConverter(v, record) {
	switch (Number(record.raw.status)) {
	case 0: return '未处理';
	case 1: return '已修改';
	case 2: return '已关闭';
	}
}

Ext.onReady(function(){
	//操作状态下拉列表中的数据
	var act_status_store = Ext.create('Ext.data.Store',{
		fields:['id','status'],
		data:[
			{id:'',status:'全选'},
			{id:'0',status:'未处理'},
			{id:'1',status:'已修改'},
			{id:'2',status:'已关闭'}
		]
	});
	
	//创建表格数据的Model  
    Ext.define(
		'Model',
		{
			extend:'Ext.data.Model',
			fields:[
				{name:'doc_id'},
				{name:'company_id'},
				{name:'stock_code'},
				{name:'company_name'},
				{name:'prompte_time'},
				{name:'excute_time'},
				{name:'error_type'},
				{name:'error_details'},
				{name:'status', convert: operateStatusConverter}
			]
		}
	);
	
	
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
		height: 100,
		margin:'10 0 0 0',
		defaultType: 'textfield',
		layout:'fit',
        frame: true,
        //是否可折叠
        collapsible: false,
        layout: 'column',
        items: [
        {
            fieldLabel: '代码/名称',
            xtype:	'combo',
            labelWidth: 70,
            width:300,
            padding:'5 5 10 10',
            store: ComboData.stock_info_query_store,
            displayField:'stock_info',
            typeAhead: false,
            name:'stock_id',
            id:'stock_name',
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
					Ext.getCmp('stock_name').setValue(stockInfo[1].replace(/(^\s*)|(\s*$)/g, ""));
				}
            }
        }, {
         	fieldLabel:'QC提示时间',
         	padding:'5 5 10 10',
         	labelWidth: 80,
         	xtype: 'datefield',
         	format:'Y-m-d',
         	value: new Date(),
         	//minValue: '01/01/10',
         	name: 'start_date'      	
         },{
         	fieldLabel:"至",
         	padding:'5 5 10 10',
         	labelWidth:20,
         	xtype:'datefield',
         	format:'Y-m-d',
         	value: new Date(),
         	//minValue: '01/01/10',
         	name: 'end_date'
         },{
         	fieldLabel: '操作状态',
         	padding:'5 5 10 10',
         	labelWidth: 60,
            xtype:'combobox',
            store:act_status_store,
            displayField:'status',
            valueField:'id',
            selectOnTab: true,
            name:'status',
            listeners:{
            	afterrender: function(combo){
            		var firstValue = act_status_store.getAt(0).get('status');
            		combo.setRawValue(firstValue);
            	}
            }
         }, {
        	 xtype: 'button',
        	 margin:'13 10 10 10',
        	 width: 60,
             text: '搜索',
             handler: function () {
                 //获取文本框值
             	var values = search_panel.getForm().getValues();
             	if (values.stock_id == '')
 					delete values.stock_id;
             	if (values.start_date == '')
 					delete values.start_date;
             	if (values.end_date == '')
 					delete values.end_date;
             	if (values.status == '全选' || values.status == '')
 					delete values.status;
             	store.proxy.url = baseUrl + "?paramsList=" + Ext.encode(JSON.stringify(values));
             	store.load();
             }
         }],
		renderTo:'search_panel'			 
	});
	
	/*
	 * store,在添加后台之后补充
	 * var QCLog_store = Ext.create('Ext.data.Store',{
		model:'Record',
		proxy:{
			...
		}
	});*/
	    
	    var store = Ext.create(
	            'Ext.data.Store',  
	            {
	                model:'Model',                         
	                pageSize:10,  
	                proxy: {
	                    type: 'ajax',  
	                    url : '/DataCollection/query/download/qclog',  
	                    reader: {
	                        //数据格式为json  
	                        type: 'json',  
	                        root: 'rows',  
	                        //获取数据总数  
	                        totalProperty: 'totalCount'
	                    }  
	                },
	                autoLoad:false
	            }  
	    );

	
	var QCLog_grid = Ext.create('Ext.grid.Panel',{
		width:'100%',
		title:'QC日志',
		store: store,
		margin:'20 0 0 0',
		columns:[
			{
				header:'文档ID',
				field: 'textfield',
				width:100,
				dataIndex:'doc_id',
				sortable:true,
				renderer: function(value) {
					return '<a href="/DataCollection/resources/pdfviewer/web/viewer.html?file=' + value + 
					'" target="_blank">' + value + '</a>';
				}
			},{
        		text:'公司ID',
        		field: 'textfield',
        		width:100,
        		dataIndex:'company_id',
        		sortable:true
        	},{
        		text:'股票代码',
        		field: 'textfield',
        		width:100,
        		dataIndex:'stock_code',
        		sortable:true
        	},{
        		text:'公司名称',
        		field: 'textfield',
        		width:100,
        		dataIndex:'company_name',
        		sortable:true
        	},{
        		text:'QC提示时间',       		
        		width:100,
        		dataIndex:'prompte_time',
        		sortable:true
        	},{
        		text:'操作时间',       		
        		width:100,
        		dataIndex:'excute_time',
        		sortable:true
        	},{
        		text:'错误类型',       		
        		width:100,
        		dataIndex:'error_type',
        		sortable:true
        	},{
        		text:'提示描述',       		
        		width:400,
        		sortable:false,
        		dataIndex:'error_details',
        		sortable:true
        	},{
        		text:'操作状态',       		
        		width:100,
        		dataIndex:'status',
        		sortable:true
        	}
		],
		bbar: Ext.create('Ext.PagingToolbar', {   
            store: store,   
            displayInfo: true,   
            displayMsg: '显示 {0} - {1} 条，共计 {2} 条',   
            emptyMsg: "没有数据"   
		}),
		renderTo:'QCLog_grid'
	});
})
			