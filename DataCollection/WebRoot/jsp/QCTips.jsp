<%@ page pageEncoding="utf-8"%> 

    <script type="text/javascript" src="../js/portlet/commonPortlet.js"> </script>
    
    <style type = "text/css">
    	.icon-add{
  			background-image:url(../image/add.gif);
  		}
  		.icon-delete{
  			background-image:url(../image/delete.gif);
  		}
  		.icon-save{
  			background-image:url(../image/save.gif);
  		}
    	
  	</style>
  	
  	<div id="search_panel" style="padding:20px; border-bottom:solid #ccc 1px; padding-left:40px;">          
    </div>
    
    <div id="grid_panel" style="padding:30px 50px 150px 100px; float:left">          
    </div>
    
    <script>    	
		Ext.Loader.setPath({
			'Ext.app':'../js/portlet'
		});

		//预加载 内容
		Ext.require(
    		[
        		'Ext.grid.*',  
        		'Ext.toolbar.Paging',  
        		'Ext.data.*',
        		'Ext.app.qc_rules'
    		]
		);
		
		Ext.onReady(function(){
			
			var search_panel = Ext.create('Ext.form.Panel',{
			    title:'按条件搜索',
			    x:60,
				width:900,
				defaultType: 'textfield',
            	frame: true,
            	//是否可折叠
                collapsible: false,
                layout: 'column',
                margin: '0 0 10 0',
                items: [{
                    fieldLabel: '文档ID',
                    labelWidth:45,
                    padding:'5 5 10 10',
                    id: 'doc_id'
                },{
                    fieldLabel: '公司ID',
                    labelWidth:45,
                    padding:'5 5 10 10',
                    id: 'comp_id'
                },{
         			fieldLabel:'QC提示时间',
         			padding:'5 5 10 10',
         			labelWidth: 70,
         			xtype: 'datefield',
         			format:'y-m-d',
         			minValue: '01/01/10',
         			id: 'start_time'      	
         		},{
         			fieldLabel:"至",
         			padding:'5 5 10 10',
         			labelWidth:20,
         			xtype:'datefield',
         			format:'y-m-d',
         			minValue: '01/01/10',
         			id: 'edd_time'
         		},{
                    xtype: 'button',
                    text: '查询',
                    margin: '5 5 10 10',
                    handler: function () {
                    }
                }],
			 renderTo:'search_panel'
			 
			});
			
			var store = Ext.create('Ext.data.Store',{
				fields:['docId','compId','stockCode','compName','time','errorType','tipDescription','tipPosition'],
				data:[
					{'docId':'DC00001','compId':'CID000001','stockCode':'600036','compName':'招商银行','time':'2013-3-9','errorType':'冗余',
				'tipDescription':'与文档D00024的hashcode不同，但meta信息相同','tipPosition':'流程位置：下载提示位置：NULL'},
				{'docId':'DC00004','compId':'CID000001',
				'stockCode':'600036','compName':'招商银行','time':'2013-3-9','errorType':'冗余',
				'tipDescription':'与文档D00024的hashcode不同，但meta信息相同','tipPosition':'流程位置：下载提示位置：NULL'},
				{'docId':'DC00034','compId':'CID000001',
				'stockCode':'600036','compName':'招商银行','time':'2013-3-9','errorType':'冗余',
				'tipDescription':'与文档D00024的hashcode不同，但meta信息相同','tipPosition':'流程位置：下载提示位置：NULL'},
				]
			});
			
			var qcTipsGrid = Ext.create('Ext.grid.Panel',{
				width:900,
				//height:300,
				//selModel:new Ext.selection.CheckboxModel,
				store:store,
				columns:[
					{header:'文档ID',width:80,dataIndex:'docId'},
					{header:'公司ID',width:80,dataIndex:'compId'},
					{header:'股票代码',width:80,dataIndex:'stockCode'},
					{header:'公司名称',width:80,dataIndex:'compName'},
					{header:'QC提示时间',width:80,dataIndex:'time'},
					{header:'错误类别',width:100,dataIndex:'errorType'},
					{
						header:'提示描述',width:220,
						dataIndex:'tipDescription',
						renderer: function(val,meta){
							meta.style = 'white-space:normal;';
							return val;
						}
					},
					{header:'提示位置',width:100,dataIndex:'tipPosition',
					renderer: function(val,meta){
							meta.style = 'white-space:normal;';
							return val;
						}},
					{
						header:'操作',
						width:80,
						dataIndex:'exchange',
						renderer:function(val,meta){
							meta.style = 'background-color:#eee; color:blue; cursor:pointer; text-align:center;';
							val = '关闭提示';
							return val;
						},
						listeners:{
							click: function(){
								Ext.Msg.show({
    								title:'提示',
        							msg:'是否关闭提示？',
        							buttons:Ext.Msg.YESNO,
        							fn:function(btnId,text,opt){
        								if(btnId == "yes"){
        									var cp = new CommonPortlet;
        									cp.deleteOnGrid(qcTipsGrid,store);
        								}
        								else{return 0;}
									}
								});
							}
						}}
					],
				renderTo:'grid_panel'
			});
			
		});
    </script>
