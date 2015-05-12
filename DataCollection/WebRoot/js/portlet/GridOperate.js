/**
 * 2014-3-2 上午11:30:45 by zq
 */

GridOperate = function(){

	this.deleteOnGrid = function(data,store,url,id_title){
		if (data.length == 0) {
			Ext.MessageBox.show({
			title : "提示",  
			msg : "请先选择您要操作的行!"
			});
			return;
		} else {
			Ext.Msg.confirm("请确认", "是否真的要删除数据？", function(button, text) {
				if (button == "yes") {
					var ids = [];
					Ext.Array.each(data, function(record) {
						var id = record.get(id_title);
						if(id) ids.push(id);
						console.log("id:" + id);
					});
					if(ids.length >0 ){
						Ext.Ajax.request({
							url:url,
							params:{
								oids: ids
							},
							methods:'POST',
							success:function(response){
								var success = response.responseText;
								if (success == '1') {
									Ext.Array.each(data, function(record) {
										store.remove(record);  
									});
									Ext.MessageBox.show({
						            	title : "提示",
						            	msg : "数据删除成功!" 
						        	});
									setTimeout(function(){Ext.MessageBox.hide()},2000);
								}else{
									Ext.MessageBox.show({
						            	title : "提示",
						            	msg : "数据删除失败!" 
						        	});
									setTimeout(function(){Ext.MessageBox.hide()},2000);
								}
							}
						});
					}
                }  
            });
		}            	
	}
	
	this.alterOnGrid = function(grid,store,urlStr){
		var records = store.getUpdatedRecords();// 获取修改的行的数据，无法获取幻影数据
		var phantoms = store.getNewRecords( ) ;//获得幻影行  
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
            		
            		Ext.Array.each(records, function(record) {
                		data.push(record.data);
                	});
                	
                	Ext.Ajax.request({
						url : urlStr,  
						params : {
					    	changed_data : Ext.encode(data)
						},  
						method : 'POST',
						timeout : 2000,
						success : function(response, opts) {
							var success = response.responseText;
							// 当后台数据同步成功时
							if (success == '1') {
								Ext.MessageBox.show({
					            	title : "提示",
					            	msg : "数据修改成功!" ,
					        	});
								
								setTimeout(function(){Ext.MessageBox.hide()},2000);
								Ext.Array.each(records, function(record) {
									// 向store提交修改数据，显示页面效果
					                record.commit();  
					            });  
					    	} else {
					        	Ext.MessageBox.show({
					        		title : "提示",
					        		msg : "数据修改失败!" 
					        	}); 
					        	setTimeout(function(){Ext.MessageBox.hide()},2000);
					      	}  
					    }
					});
	            }  
            });
        }
	}
	
	this.commitAdd = function(store,addedItem,urlStr){	
		Ext.Msg.confirm("请确认", "是否保存数据？", function(button, text) {
			if (button == "yes") {
            	Ext.Ajax.request({
					url : urlStr, 
					params : {
						addedItem : Ext.encode(addedItem)
					},  
					method : 'POST',
					timeout : 2000,
					success : function(response, opts) {
						var success = response.responseText;
						// 当后台数据同步成功时
						if (success == '1') {
							Ext.MessageBox.show({
				            	title : "提示",
				            	msg : "数据添加成功!" 
				        	});
							store.insert(0,addedItem);
							setTimeout(function(){Ext.MessageBox.hide()},2000);
							 
				    	} else {
				        	Ext.MessageBox.show({
				            title : "提示",
				            msg : "数据修改失败!" 
				        	}); 
				        	setTimeout(function(){Ext.MessageBox.hide()},2000);
				      	}  
				    }
				});
            }  
        });
	}
	
	
	this.addOnPage = function(store,Model){
		store.insert(0,new Model());
	}
	
	this.commitUpdate = function(){
		Ext.Msg.alert('提示信息','更改已提交');
		setTimeout(function(){Ext.MessageBox.hide()},2000);
	}
	
	function sendRequest(req_url){
		Ext.Ajax.request({
			url: req_url
		});
	}	
}