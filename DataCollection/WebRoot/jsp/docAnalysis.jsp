<%@ page pageEncoding="utf-8"%>

	<style type = "text/css">
		.btn{
			padding:4px 10px 4px 10px;
			color:#959595;
		}
		
		.btn:hover{
			font-weight:bold;
			border-bottom-width:3px;
			border-bottom-style:solid;
			border-bottom-color:#cbdbef;
			cursor: pointer;
		}
		
	</style>

	<div class="content">
		<div style = "float:right; margin-right:30px; padding-bottom:20px;">
			<span class="btn">删除当前文档</span>
			<span class="btn">下一步</span>
		</div>
		<div id="tab_panel"></div>
	</div>
    
    <script>
    	(function($){
			$.getUrlParam = function(name)
			{
				var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
				var r = window.location.search.substr(1).match(reg);
				if (r != null) return unescape(r[2]); return null;
				
			}
		})(jQuery);
		
	 	function getContent(url,dom_id,table_type,table_name){
			$.getJSON(url,function(jsonData){
				arrayData.length = 0;
				stockName = jsonData["stockName"];
				companyId = jsonData["companyId"];
				industry = jsonData["industry"];
				exchange = jsonData["exchange"];
				bulletinType = jsonData["bulletinType"];
				var tempArray = [];
				var content;
				$.each(jsonData["content"],function(key,val){
					if(key == "内容")
						content = jsonData["content"]["内容"];
					else{
						$.each(val,function(key1,val1){
							switch(key1){
								case "内容":
									content = val1;
									break;
								case "单位":
									CURRENCY_PRE = val1;
									break;
								case "币种":
									CURRENCY = val1;
									break;
							}		
						});
					}
					
					$.each(content,function(index,value){
						var rowValue = [];
						$.each(value,function(index1,value1){
							if(index1 == "表格列顺序"){
								COLUMN_INDEX.length = 0;
								$.each(value1,function(i,key){
									if((jQuery.inArray(key,tempArray)) == -1){
										tempArray.push(key);
										COLUMN_INDEX.push({name:key});
									}
								})
							}else
								rowValue[jQuery.inArray(index1,tempArray)] = value1;
						});
						arrayData.push(rowValue);
					});
					arrayData.shift();
					getGridInTab(COLUMN_INDEX,dom_id,table_type,table_name);
				});
				
			});	
		} 

		Ext.Loader.setConfig({enabled:true});
		Ext.require(
    		[
        		'Ext.grid.*', 
        		'Ext.data.*',
        		'Ext.panel.Panel',
        		'Ext.toolbar.*',
        		'Ext.tab.*'
    		]
		);
		
		var arrayData = [];
		var CURRENCY,
			CURRENCY_PRE,
			RELEASE_DATE,
			stockName,
			companyId,
			industry,
			exchange,
			bulletinType,
			stockName;
		var COLUMN_INDEX = [];
		var BULLETIN_ID = $.getUrlParam('bulletin_id');
		var URL = "/DataCollection/query/table_content_byId";

		var main = function(){
        	getGridInTab = function(FIELDS,dom_id,table_type,table_name){
        		var Model = Ext.define('',{
					extend:'Ext.data.Model',
					fields: FIELDS
				}); 
				
				var store = Ext.create('Ext.data.ArrayStore', {
					data:arrayData,
					model:Model
	        	});
	        	
	        	store.load();
	        	
	        	function getColumns(){
	        		var columns = [];
	        		for(var i=0;i < FIELDS.length ;i++){
	        			columns.push({sortable: false,flex:1,header:FIELDS[i]['name'],dataIndex:FIELDS[i]['name'],editor:'textfield'});
	        		}
	        		return columns;
	        	}
	        	
        		var grid = Ext.create('Ext.grid.Panel',{
					store: store,
	            	stripeRows: true,
	            	columnLines: true,
	            	width:1020,
	            	height:460,
					selModel:new Ext.selection.CheckboxModel,
					selType: 'cellmodel',
					plugins : [{ptype: 'cellediting', clicksToEdit: 1}],
					renderTo:dom_id,
	            	columns: getColumns(),
	            	dockedItems:[{
	      				xtype:'toolbar',
	      				height:50,
	                   	dock:'top',
	                   	items: [
	                   		'<span id = "stockName" style = "font-size:25px !important;">' + stockName 
	                   			+ '<span style = "font-size:15px">&emsp;文档类型:' + bulletinType +'</span>'
	                   			+ '<span style = "font-size:15px">&emsp;交易所:' + exchange +'</span>'
	                   			+ '<span style = "font-size:15px">&emsp;行业:' + industry +'</span>'
	                   			+ '<span style = "font-size:15px">&emsp;公司代码:' + companyId +'</span>'
	                   			+ '<span style = "font-size:15px">&emsp;<a href = "/DataCollection/resources/pdfviewer/web/viewer.html?file=' + BULLETIN_ID + '" target = "_blank">原文档</a></span>'
	                   			+ '</span>',
	                   		'->',
	                   		'<span id = "currency">货币类型：<input style = "width:80px" id = "ID_CURRENCY" type = "textArea" value =' + CURRENCY + '></input></span>','-',
	                   		'<span id = "currencyPre">单位：<input style = "width:80px" id = "ID_CURRENCY_PRE" type = "textArea" value =' + CURRENCY_PRE +'></input></span>'
						]						
	      			},{
	      				xtype:'toolbar',
	      				height:35,
	      				dock:'top',
	      				items:[{
	      					iconCls:'icon-add-filled',
	      					text:'添加行',
	      					handler: function(){
	      						var data = this.up("grid").getSelectionModel().getSelection();
	      						console.log(data);
	      						var index = data.length;
	      						Ext.Array.each(data,function(record){
	      							console.log(record.index);
	      							store.insert(record.index+1+data.length-index,new Model());
	      							index--;
	      						});
	      					}
	      				},{
	      					iconCls:'icon-delete-filled',
	      					text:'删除行',
	      					handler:function(){
	      						var data = this.up("grid").getSelectionModel().getSelection();
	      						if (data.length == 0) {
									Ext.MessageBox.show({title : "提示", msg : "请先选择您要操作的行!"});
									return;
								} else {
									Ext.Msg.confirm("请确认", "是否真的要删除数据？", function(button, text) {
										if (button == "yes") Ext.Array.each(data, function(record) {store.remove(record);});
									});
						       }      
	      					}   					
	      				},{
	      					iconCls:'icon-copy',
	      					text:'保存',
	      					handler:function(){
	      						var contentData = [];
	      						var count = store.getCount();
	      						var record;
	      						var content = '{"' + table_name +'":{"单位":"' + $("#ID_CURRENCY_PRE").val() + '","币种":"' + $("#ID_CURRENCY").val() + '","内容":[';
	      						var index;
	      						for(var i = 0; i<count; i++){
	      							record = store.getAt(i);
	      							content += "{";
	      							for(index = 0 ;index < FIELDS.length-1;index++){
	      								content = content +'"'+ FIELDS[index]['name'] + '"' + ":" + '"' + record.get(FIELDS[index]['name']) + '",';						
	      							}
	      							content = content +'"'+ FIELDS[index]['name'] + '"' + ":" + '"' + record.get(FIELDS[index]['name']) + '"},';
	      						}
	      						content = content.substr(0,(content.length-1));
      							content += '],';
      							content = content + '"公告时间":"' + RELEASE_DATE + '"}}';
	      						Ext.Ajax.request({
	      							url:'/DataCollection/update/table_content',
	      							params:{
	      								changed_data:content,
	      								bulletin_id:BULLETIN_ID,
	      								table_type:table_type
	      							},
	      							success : function(response, opts) {
										var success = response.responseText;
										// 当后台数据同步成功时
										if (success == '1'){
											Ext.MessageBox.show({title : "提示",msg : "数据修改成功!" });
											var records = store.getUpdatedRecords();
											Ext.Array.each(records,function(record){
												 record.commit();  
											});
										}
								    	else
								        	Ext.MessageBox.show({title : "提示",msg : "数据修改失败!" });  
								      	
								    }
	      						});
	      					}
	      				}]
	      			}],
					
				});
        	}
			
			var tabs = Ext.create('Ext.tab.Panel',{
				renderTo: 'tab_panel',
				plain:true,
				//y:20,
				width: 1050,
				height:520,
				selType: 'cellmodel',
				margin: '0 0 0 10',
				activeTab: 0,
        		items: [{
            		title: '合并现金流量表',
            		margin: '10',
            		id:'sheet_type_0',
            		//selModel: new Ext.selection.CheckboxModel,
            		listeners:{
						'activate':function(){
							getContent(URL + "?bulletin_id="+BULLETIN_ID+"&table_type=0",'sheet_type_0-body','0','合并现金流量表');
						}}
        		},{
					title: '合并资产负债表',
					margin: '10',
					id:'sheet_type_1',
					selModel: new Ext.selection.CheckboxModel,
					listeners:{
						'activate':function(){
							getContent(URL + "?bulletin_id="+BULLETIN_ID+"&table_type=1",'sheet_type_1-body','1','合并资产负债表');
						}
					}
        		},{
        			title:'合并利润表',
        			margin: '10',
					id:'sheet_type_2',
					selModel: new Ext.selection.CheckboxModel,
					listeners:{
						'activate':function(){
							getContent(URL + "?bulletin_id="+BULLETIN_ID+"&table_type=2",'sheet_type_2-body','2','合并利润表');
						}
					}
        		},{
        			title:'附注'
        		},{
        			title:'QC(*)'
        		}]
    		});
    		
    		$("#title").parent().css("line-height","30px");
		}();		
		

    </script>
