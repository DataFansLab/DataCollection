<%@ page pageEncoding="utf-8"%> 

    <style type = "text/css">
    	.icon-add-ud{
  			background-image:url(../image/add.gif);
  		}
  		.icon-delete-ud{
  			background-image:url(../image/delete.gif);
  		}
  		.icon-save-ud{
  			background-image:url(../image/save.gif);
  		}
    	
  	</style>

	<div class="content">
		<div id="search_panel"></div>
		<div id="grid_panel"></div>	
		<div id="add_item_form" style="position:fixed; top:300px; left:30%; width:700px; z-index:999; box-shadow:0 0 8px #fff;"></div>
	</div>  	

	<script type="text/javascript" src = "/DataCollection/js/portlet/GridOperate.js"></script>
    <script type="text/javascript" src = "/DataCollection/js/portlet/ComboData.js"></script>
    <script>
		
		Ext.Loader.setConfig({enabled:true});

		//预加载 内容
		Ext.require(
    		[
        		'Ext.grid.*',  
        		'Ext.toolbar.Paging',  
        		'Ext.data.*'
    		]
		);
		
		var operateHandler = new GridOperate();
		
		Ext.onReady(function(){
			
			Ext.create('Ext.form.Panel',{
			    title:'按条件搜索',
				width:'100%',
				layout:'fit',
				defaultType: 'textfield',
            	frame: true,
            	//是否可折叠
                collapsible: false,
                layout: 'column',
                margin: '10 0 10 0',
                items: [{
                    fieldLabel: '代码/名称',
	                xtype:	'combo',
	                labelWidth: 100,
	                width:320,
	                padding:'5 5 10 10',
	                store: ComboData.stock_info_query_store,
	                displayField:'stock_info',
	                typeAhead: false,
	                name:'stockId',
	                id:'stockId',
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
							Ext.getCmp('stockId').setValue(stockInfo[1].replace(/(^\s*)|(\s*$)/g, ""));
						}
	                }
 
                },{
					fieldLabel:'交易所',
					labelWidth:50,
					padding:'5 5 10 10',
					name:'exchange',
					xtype:'combobox',
                    store:ComboData.exchange_type_store,
                    displayField:'name',
                    valueField:'id'
                },{
					fieldLabel:'股票类型',
					labelWidth:60,
					padding:'5 5 10 10',
					name:'stockType',
					xtype:'combobox',
                    store:ComboData.stock_type_store,
                    displayField:'name',
                    valueField:'id'
                },{
                    xtype: 'button',
                    text: '查询',
                    margin:'10 5 20 100',
         			padding: '3 20 3 20',
                    handler: function () {                        
                        var values = this.up("form").getForm().getValues();
                        if (values.stockId == '')
                        	delete values.stockId;
						if (values.exchange == '全选')
							delete values.exchange;
						if (values.stockType == '全选')
							delete values.stockType;
                    	store.proxy.url ='/DataCollection/query/company_list' + '?paramsList=' + Ext.encode(JSON.stringify(values));
                    	store.load();
           				
                    }
                }],
			 renderTo:'search_panel'
			 
			});
			
			Ext.create('Ext.form.Panel',{
	        	title:'添加',
	        	bodyPadding:5,
	        	width:'100%',
	        	closable:false,
	        	frame:true,
	        	layout : {
	                type : 'table',
	                columns : 2
	        	},
	        	renderTo:'add_item_form', //显示位置
	        	defaultType:'textfield', //name对应grid列中的detaIndex
	        	items:[{fieldLabel:'公司代码',name:'campany_id',allowBlank : false },
	        		   {fieldLabel:'股票代码',name:'stock_id',allowBlank : false },
		               {fieldLabel:'公司名称',name:'stock_name',allowBlank : false},
		               {fieldLabel:'industry',name:'industry',allowBlank : false,
		                xtype:'combo',
		                displayField:'name',
		                store:{
		                	 xtype:'Ext.data.Store',
		                	 fields:['name'],
		                	 data:[{'name':'K'},{'name':'H'},{'name':'S'},{'name':'C'},{'name':'G'}]
		                }
		               },
		               {fieldLabel:'交易所',
		                name:'exchange',
		                allowBlank : false,
		                xtype:'combo',
		                displayField : 'name',
		                store:{
		                	 xtype:'Ext.data.Store',
		                	 fields:['name'],
		                	 data:[{'name':'深交'},{'name':'上交'}]
		                 }
		               },
		               {fieldLabel:'主板',
		                name:'block',
		                allowBlank : false,
		                xtype:'combo',
		                displayField : 'name',
		                store:{
		                	 xtype:'Ext.data.Store',
		                	 fields:['name'],
		                	 data:[{'name':'主板'},{'name':'中小企业板'}]
		                 }
		               },
		               {fieldLabel:'股票类型',
		                name:'stock_type',
		                allowBlank : false,
		                xtype:'combo',
		                displayField : 'name',
		                store:{
		                	 xtype:'Ext.data.Store',
		                	 fields:['name'],
		                	 data:[{'name':'A股'},{'name':'B股'}]
		                 }
		                },
		                {fieldLabel:'listDate',name:'list_date',xtype: 'datefield',format:'Y-m-d',maxDate: new Date(),allowBlank: false},
		                {fieldLabel:'changeDate',name:'change_date',xtype: 'datefield',value:'0000-00-00',allowBlank : false},
		                {fieldLabel:'状态',name:'status',allowBlank : false, value:'上市'}
		                
	            ],
	        	
	            buttons: [
	            	{
	            		text: '确定',
	            		handler:function(){
	            			if(this.up("form").getForm().isValid()){
	            				var values = this.up("form").getForm().getValues();
	                    		this.up("form").getForm().submit({
	    							url:'/DataCollection/insert/companyList',
	                				timeout : 2000,
	                				params:{addedItem:Ext.encode(values)},
	                				success:function(fp,o){
	                					Ext.Msg.alert('Success','添加成功');
	                				}
	                			});
							}
	            			else
	            				Ext.Msg.alert('Warning','请把数据填写完整');
	            	}},{
	            	text:'取消',
	            	handler:function(){
	            		$("#add_item_form").css("display","none");
	            	}
	            }]
        	});
		
			$("#add_item_form").css("display","none");
			
			var store = Ext.create('Ext.data.Store',{
				fields:['id','stockCode','compName','stockType','exchange'],
				pageSize:10,  
				proxy:{
					type:'ajax',
					autoload:false,
					url:'/DataCollection/query/company_list',
					reader: {
						type: 'json',
						root: 'rows',
						totalProperty: 'totalCount'
					}  
				}
			});
			
			var compListGrid = Ext.create('Ext.grid.Panel',{
				title:'公司代码列表',
				width:'100%',
				margin:'20 0 0 0',
				selModel:new Ext.selection.CheckboxModel,
				store:store,
				selType: 'cellmodel',
				plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
					clicksToEdit: 1
				})],
				columns:[
					{header:'股票代码',flex:1,dataIndex:'stockCode'},
					{header:'公司ID',flex:1,dataIndex:'id',field: {xtype:'textfield'}},
					{header:'公司名称',flex:1,dataIndex:'compName',field: {xtype:'textfield'}},
					{header:'股票类型',flex:1,dataIndex:'stockType',field: {xtype:'textfield'}},
					{header:'交易所',flex:1,dataIndex:'exchange',field: {xtype:'textfield'}}
					],
				renderTo:'grid_panel',
				dockedItems:[
				{	xtype:'toolbar',
                    	items:[{
                    		iconCls:'icon-delete-filled',
                    		text:"删除",
                    		//disabled:true,
                    		itemId:'delete',
                    		scope:this,
           					handler: function(){
           						var data = compListGrid.getSelectionModel().getSelection();
           						url = '/DataCollection/delete/company_list';        						
           						operateHandler.deleteOnGrid(data,store,url,'stockCode');
           					}
                    	},{
                    		iconCls:'icon-add-filled',
                    		text:"添加",
                    		scope:this,
                    		handler: function(){
                    			$("#add_item_form").css("display","block");
                    		}
                    	},{
				     		iconCls:'icon-copy',
				     		text:"保存",
				     		scope:this,
				     		handler:function(){
				     			operateHandler.alterOnGrid(compListGrid,store,'/DataCollection/update/company_list');
				     		}
				     		
				     	}
				]}],
				bbar: Ext.create('Ext.PagingToolbar', {
					store:store,
					displayInfo: true,
					displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
					emptyMsg: "没有数据"
				}),
			});
			
		});
    </script>
