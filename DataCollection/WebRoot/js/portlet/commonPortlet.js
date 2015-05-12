CommonPortlet = function(){
	
	//表格中数据高亮显示
	this.highLightTheCell = function(val,metadata){
		if(val > 6){
			metadata.style = 'background-color: #FFFFCC !important; color:red; cursor: pointer';
			return '<a href = "upload" target = "blank"><span style="width:60px; color:red;">'+val+'</span></a>';
		}
		else
			return val;
	}

	this.deleteOnGrid = function(grid,store,url){		
		var data = grid.getSelectionModel().getSelection();
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
						var doc_id=record.get('doc_id');
						if(doc_id) ids.push(doc_id);
					});
					Ext.Array.each(data, function(record) {
						store.remove(record);  
					});
                }  
            });
		}            	
	}
	
	this.commitUpdate = function(){
		Ext.Msg.alert('提示信息','更改已提交');
	}
	
	function sendRequest(req_url){
		Ext.Ajax.request({
			url: req_url
		});
	}	
}