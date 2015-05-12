
//预加载 内容
Ext.require(  
    [  
        'Ext.grid.*',  
        'Ext.toolbar.Paging',  
        'Ext.data.*'  
    ]
);  

Ext.onReady(
        function(){

        	//把date格式的数据格式化
        	function formatDate(value){
        		return value ? Ext.Date.dateFormat(value, 'Y-m-d') : '';
    		}
        	
            //创建表格数据的Model  
            Ext.define(
                    'Record',  
                    {  
                        extend:'Ext.data.Model',  
                        fields:[  
                                {name:'doc_id',mapping:'doc_id'},  
                                {name:'company_id',mapping:'company_id'},  
                                {name:'company_code',mapping:'company_code'},
                                {name:'company_name',mapping:'company_name'},
                                {name:'doc_type',mapping:'doc_type'},
                                {name:'account_date',mapping:'account_date'},
                                {name:'download_date',mapping:'download_date',type:'date',dateFormat:'Y-m-d'},
                                {name:'stock_type',mapping:'stock_type'},
                                {name:'exchange',mapping:'exchange'},
                                {name:'states',mapping:'states'},
                                {name:'QC_num',mapping:'QC_num'}
                        ]  
                    }  
            );
            
            /*
             * 创建表格中数据的数据源
             * 向后台发起ajax请求
             * 后台解析请求之后相应请求，将从数据库中读取出来的数据以json格式返回给前台
             * 前台由store对数据进行接收
             */  
            var store = Ext.create(  
                    'Ext.data.Store',  
                    {
                        model:'Record',  
                          
                        //设置分页大小  
                        pageSize:5,  
                        proxy: {
                            type: 'ajax',  
                            url : '/DataCollection/bulletin/query',  
                            reader: {  
                                //数据格式为json  
                                type: 'json',  
                                root: 'bugs',  
                                //获取数据总数  
                                totalProperty: 'totalCount'
                            }  
                        },
                        sorters:[{
                        	property:'doc_id',  //指定排序字段
                        	direction:'asc' //s顺序为asc
                        }],
                        autoLoad:true
                    }  
            );  
            
            //定义文档类型下拉列表中的数据。
    		var doc_type_store = Ext.create('Ext.data.Store', {
    			fields: ['id', 'name'], 
    			data : [
        			{"id":"1","name":"第一季度报"},
        			{"id":"2","name":"第三季度报"},
        			{"id":"3","name":"半年报"},
        			{"id":"4","name":"年报"}
    			]
			}); 
			
			//定义股票类型下拉列表中的数据
			var stock_type_store = Ext.create('Ext.data.Store', { 
    			fields: ['id', 'name'], 
    			data : [ 
        			{"id":"1","name":"A股"},
        			{"id":"2","name":"B股"},
        			{"id":"3","name":"中小型股"}
    			]
			}); 
			
			/* 
			 * 定义搜索框格式和内容
			 * store会将搜索条件以参数形式用ajax请求传给服务器
			 * 服务器返回的结果会在加载该store的grid中显示
			 * 注意：查询操作是在数据库中进行，并不是在页面中进行的
			 * 
			 */
			var search_panel = Ext.create('Ext.form.Panel',{
			    title:'按条件搜索',
				width:600,
				defaultType: 'textfield',
            	frame: true,
            	//是否可折叠
                collapsible: false,
                bodyPadding: 5,                
                layout: 'column',
                margin: '0 0 10 0',
                items: [{
                	iconCls:'icon-add',
                    fieldLabel: '文档代码',
                    labelWidth: 60,
                    id: 'doc_id'
                }, {
                    xtype: 'button',
                    text: '搜索',
                    margin: '0 0 0 5',
                    handler: function () {
                    	//获取文本框值
                        var doc_id = Ext.getCmp('doc_id').getValue(); 
						if (doc_id !="") {
							//传递参数
                            store.load({ params: { doc_id: doc_id} });
                        }
                    }
                }],
			 renderTo:'search_panel'
			 
			});
                          
            /*
             *  创建grid，定义表格的格式和内容
             * 表格中一些参数的含义：
             * store:定义数据来源
             * loacMask:是否加载提示
             * selType:用户选择的模式：行选择或者单元格选择，Ext.selection.CellModel为单元格选择
             * disableSelection:值为TRUE时表示禁止选择
             * renderTo:关联表格的展现位置，与body和html中的标签进行关联
             * columns:定义表格中每一行的显示形式、数据来源
             * 
             */
            var grid = Ext.create('Ext.grid.Panel',{
            		height:230,   
                    width:1000,   
                    x:0,   
                    y:0, 
                    fontSize:25,
                    title: '上传文档',
                    store:store,
                    loadMask: true, 
                	selModel: new Ext.selection.CheckboxModel,
                	selType: 'cellmodel',
                	//设置表格编辑的plugins,让用户单击之后就可对单元格进行编辑
                	plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
            			clicksToEdit: 1
        			})],
                	disableSelection: false,
                	renderTo: 'grid',   
                    
                	viewConfig: {
            			id: 'gv',
            			trackOver: false,
            			stripeRows: false
        			},
        			
                    columns:[  
                             {header:'文档代码',field: 'textfield',width:100,dataIndex:'doc_id',sortable:true},  
                             {text:'公司ID',field: 'textfield',width:100,dataIndex:'company_id',sortable:true},  
                             {text:'公司代码',field: 'textfield',width:100,dataIndex:'company_code',sortable:true},
                             {text:'公司名称',field: 'textfield',width:100,dataIndex:'company_name',sortable:true},
                             {
                             	text:'文档类型',
                             	width:100,
                             	dataIndex:'doc_type',
                             	sortable:false,
                             	field: {
                             		xtype:'combobox',
                					typeAhead: true,
                					triggerAction: 'all',
                					queryMode: 'local', 
                					selectOnTab: true,
                					store: doc_type_store,
                					lazyRender: true,
                					displayField:'name',
                					valueField:'name',
                					listClass: 'x-combo-list-small',
                					listeners:{    
                    					select : function(combo, record,index){
                        					isEdit = true;
                    					}
                					}	
                             	}                             	
                             },
                             {
                             	text:'会计结算日期',
                             	width:100,
                             	dataIndex:'account_date',
                             	sortable:true
                             },
                             {
                             	text:'下载日期',
                             	renderer: formatDate,
                             	field: {
                					xtype: 'datefield',
                					format: 'y-m-d',
                					minValue: '01/01/10'
            					},
                             	width:100,
                             	dataIndex:'download_date',
                             	sortable:true                            	
                             },
                             {
                             	text:'股票类型',
                             	width:100,
                             	dataIndex:'stock_type',
                             	sortable:true,
                             	field: {
                             		xtype:'combobox',
                					typeAhead: true,
                					triggerAction: 'all',
                					queryMode: 'local', 
                					selectOnTab: true,
                					store: stock_type_store,
                					lazyRender: true,
                					displayField:'name',
                					valueField:'name',
                					listClass: 'x-combo-list-small',
                					listeners:{
                    					select : function(combo, record,index){
                        					isEdit = true;
                    					}
                					}	
                             	}
                             },
                             {text:'交易所',width:100,dataIndex:'exchange',sortable:true},
                             {text:'文档状态',width:100,dataIndex:'states',sortable:true},
                             {text:'QC提示',width:100,dataIndex:'QC_num',sortable:true},
                             {
                             	header:'手动上传',
                             	sortable:false
                             	
                             	/*field: {
                             		xtype: 'button',
                             		dataIndex: '点击上传PDF',
                             		handler: function () {
                             			alert("点击上传PDF");
                             		}
                             	}*/
                             }
                    ],
                    //定义表格的操作栏
                    dockedItems:[
                    {
                    	xtype:'toolbar',
                    	items:[{
                    		iconCls:'icon-add',
                    		text:'添加',
                    		scope:this,
                    		handler:addOnPage
                    	},{
                    		iconCls:'icon-delete',
                    		text:"删除",
                    		//disabled:true,
                    		itemId:'delete',
                    		scope:this,
                    		handler:deleteOnPage
                    	},{
                    		iconCls:'icon-save',
                    		text:"保存",
                    		scope:this,
                    		handler:alterOnPage
                    	}]
                    }],
                    
                    /*listeners:{
                    	//监听单机事件records当前行对象
                    	itemclick:function(dv,records,item,index,e){
                    	
                    	},selectionchange:function(model,records){
                    		if(records[0]){
                    			//Panel.show();
                    			//Panel.loadRecord(records[0]);
                    		}
                    	}
                    },*/                  
                    //分页功能 的展示条
                    bbar: Ext.create('Ext.PagingToolbar', {   
                                    store: store,   
                                    displayInfo: true,   
                                    displayMsg: '显示 {0} - {1} 条，共计 {2} 条',   
                                    emptyMsg: "没有数据"   
                    })               
        	});
            //store.loadPage(2);
            //grid.on('edit', onEdit, this);
            
            //测试用
            var Panel = Ext.create('Ext.form.Panel',{
            	//x:20,
            	//y:250,
            	title:'添加',
            	width:300,
            	frame:true,
            	bodyPadding:5,
            	closable:true,
            	hidden:true,
            	margin:'10 0 0 0',
            	defaultType:'textfield', //name对应grid列中的detaIndex
            	items:[{fieldLabel:'文档代码',name:'doc_id'},
            		   {fieldLabel:'公司ID',name:'company_id'},
            		   {fieldLabel:'公司代码',name:'company_code'},
            		   {fieldLabel:'公司名称',name:'company_name'},
            		   {fieldLabel:'文档类型',name:'doc_type'},
            		   {fieldLabel:'会计结算日期',name:'account_date'},
            		   {fieldLabel:'下载日期',name:'download_date'},
            		   {fieldLabel:'股票类型',name:'stock_type'},
            		   {fieldLabel:'交易所',name:'exchange'},
            		   {fieldLabel:'文档状态',name:'states'},
            		   {fieldLabel:'QC提示',name:'QC_num'}
            		   ],
            	renderTo: Ext.getBody(), //显示位置
                buttons: [{ text: '确定' }, { text: '取消'}]
            });
            
            
            /* 
             * 定义进行删除操作的函数
             * 用户在页面对选中的行进行删除操作时
             * 页面先给后台发送Ajax请求
             * 后台操作成功是返回success参数
             * 页面接收到success参数再从页面上进行移除数据
             */
            function deleteOnPage(){
            	var data = grid.getSelectionModel().getSelection();
            	if (data.length == 0) {
            		Ext.MessageBox.show({  
                		title : "提示",  
                		msg : "请先选择您要操作的行!"  
                    	// icon: Ext.MessageBox.INFO  
                	});  
            		return;  
        		} else {
            		Ext.Msg.confirm("请确认", "是否真的要删除数据？", function(button, text) {  
                		if (button == "yes") {  
                    		var ids = [];  
                    		Ext.Array.each(data, function(record) {  
                                var doc_id=record.get('doc_id');  
                                if(doc_id){  
                                    ids.push(doc_id);  
                                }   
                            });
                            
                            //页面效果，移除被删除的项目，这一段再添加后台后需要移到后面去
                            //Ext.Array.each(data, function(record) {  
                                       // store.remove(record);  
                            //});
  
                            //向后台发送ajax请求，在后台把数据删除
                    		Ext.Ajax.request({  
                        		url : 'deleteRecords',  
                        		params : {  
                            		deleteIds : ids.join(',')
                        		},
                        		method : 'POST',
                        		//设置超时
                        		//timeout : 2000,
                        		success : function(response, opts) {
                            		//var success = Ext.decode(response.responseText).success; 
                        			var success = response.responseText; 
                            		//当后台数据同步成功时  
                            		if (success) {
                                		Ext.Array.each(data, function(record) {  
                                        	store.remove(record);// 页面效果  
                                    	});  
                            		} else {  
                                		Ext.MessageBox.show({  
                                    	title : "提示",  
                                   		msg : "数据删除失败!"  
                                    	});  
                            		}  
  
                        		}  
                    });
                }  
            });}            	
			}
            
			//定义进行删除操作的函数
			function addOnPage(){
				store.insert(0,new Record());
			}
            
			function alterOnPage(){
				var records = store.getUpdatedRecords();// 获取修改的行的数据，无法获取幻影数据  
        		var phantoms=store.getNewRecords( ) ;//获得幻影行  
        		records=records.concat(phantoms);//将幻影数据与真实数据合并  
        		if (records.length == 0) {  
            		Ext.MessageBox.show({  
                		title : "提示",  
                		msg : "没有任何数据被修改过!",
                    	icon: Ext.MessageBox.INFO  
                	});  
            		return;  
        		} else {  
            		Ext.Msg.confirm("请确认", "是否真的要修改数据？", function(button, text) {  
                		if (button == "yes") {  
                    		var data = [];  
                    		//alert(records[0]);  
                    		Ext.Array.each(records, function(record) {
                        		data.push(record.data);
                        		// 向store提交修改数据，页面效果 
                            	record.commit(); 
                        	});  
  
                    	/*Ext.Ajax.request({  
                        	url : 'alterUsers.action',  
                        	params : {  
                            	alterUsers : Ext.encode(data)  
                        	},  
                        	method : 'POST',  
                        	timeout : 2000,  
  
                        	success : function(response, opts) {  
                            	var success = Ext.decode(response.responseText).success;  
                            	// 当后台数据同步成功时  
                            	if (success) {  
                                	Ext.Array.each(records, function(record) {  
                                        // data.push(record.data);  
                                        record.commit();// 向store提交修改数据，页面效果  
                                    });  
                            	} else {  
                                	Ext.MessageBox.show({  
                                    title : "提示",  
                                    msg : "数据修改失败!"  
                                        // icon: Ext.MessageBox.INFO  
                                });  
                            }  
                        }  
                    });  */
                }  
            });
        }
				
	}
			
			//测试函数
			function onEdit(){
        		var record = grid.getSelectionModel().getSelection()[0];
        		//这里需要用ajax进行异步请求
        		var doc_id = record.get('doc_id');
        		var company_id = record.get('company_id');
        		var company_code = record.get('company_code');
        		var company_name = record.get('company_name');
        		var doc_type = record.get('doc_type');
        		var account_date = record.get('account_date');
        		var download_date = record.get('download_date');
        		var stock_type = record.get('stock_type');
        		var exchange = record.get('exchange');
        		var states = record.get('states');
        		var QC_num = record.get('QC_num');

        		Ext.MessageBox.show({
            		title:"修改的数据为",
            		msg:doc_id+"\r\n"+company_id+"\r\n"+company_code+"\r\n"+company_name+"\r\n"+doc_type+"\r\n",
            		icon: Ext.MessageBox.INFO,
            		buttons: Ext.Msg.OK
        		})
    		}
        });