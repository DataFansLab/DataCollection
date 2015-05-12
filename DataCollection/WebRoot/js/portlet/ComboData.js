/**
 * @author zq
 * 2014/3/31
 */

ComboData = {
	 //定义文档类型下拉列表中的数据。
	doc_type_store: Ext.create('Ext.data.Store', {
		fields: ['id', 'name'], 
		data : [
			{"id":"","name":"全选"},
			{"id":"1","name":"第一季度季报"},
			{"id":"2","name":"第三季度季报"},
			{"id":"3","name":"半年报"},
			{"id":"4","name":"年报"}
		]
	}),
	
	//定义股票类型下拉列表中的数据
	stock_type_store: Ext.create('Ext.data.Store', {
		fields: ['id', 'name'], 
		data : [ 
			{"id":"全选","name":"全选"},
			{"id":"A","name":"A股"},
			{"id":"B","name":"B股"}
		]
	}),
	
	//定义文档状态下拉列表中的数据
	doc_status_store: Ext.create('Ext.data.Store', {
		fields: ['id', 'name'], 
		data : [ 
			{"id":"","name":"全选"},
			{"id":"3","name":"已删除"},
			{"id":"2","name":"已上传"},
			{"id":"1","name":"上传中"},
			{"id":"4","name":"上传失败"},
			{"id":"5","name":"缺失"},
			{"id":"6","name":"下载失败"}
		]
	}),
	
	exchange_type_store: Ext.create('Ext.data.Store',{
		fields:['id','name'],
		data:[{'id':'全选','name':'全选'},
			  {'id':'深交','name':'深交'},
			  {'id':'上交','name':'上交'}]
	}),
	
	stock_info_query_store:Ext.create('Ext.data.Store',{
        proxy: {
            type: 'ajax',
            url : "/DataCollection/query/getStockInfo",
            reader: {
                type: 'json',  
                root: 'rows'
            }  
        },
        fields:['stock_info','id'],
        pageSize:100000,
        autoLoad:false
	})
}