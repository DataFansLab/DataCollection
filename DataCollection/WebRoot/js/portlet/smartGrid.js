/**
 * @author zq
 * 2014/3/31
 */

var SmartGrid = function(arr,url,title,col,dockedToolbar){
	
	
	 /* 获取store中的fields */
	 
	this.getFileds = function(arr){
		var temp_fields = new Array();
		for(var item in arr){
			if (arr[item] == 'doc_id') {
				temp_fields.push({'name':arr[item], 'convert': docIdConverter});
			} else temp_fields.push({'name':arr[item]});
		}
		return temp_fields;
	},
	
	
    /* 创建表格中数据的数据源
     * 向后台发起ajax请求
     * 后台解析请求之后相应请求，将从数据库中读取出来的数据以json格式返回给前台
     * 前台由store对数据进行接收
     */
      
	this.getStore = function(){
		var Model = Ext.define('',{
			extend:'Ext.data.Model',
			fields: this.getFileds(arr)
		});
		
		store = Ext.create('Ext.data.Store', {
            model:Model,
            pageSize:10,  
            proxy: {
                type: 'ajax',
                url : url,
                reader: {
                    type: 'json',  
                    root: 'rows',
                    totalProperty: 'totalCount'
                }  
            },
            autoLoad:false
        });
	    return store;
	}
	
	getPageingToolBar = function(store){
		var pagingToolbar = Ext.create('Ext.PagingToolbar',{
	        store: store,   
	        displayInfo: true,   
	        displayMsg: '显示 {0} - {1} 条，共计 {2} 条',   
	        emptyMsg: "没有数据"
		});
		
		return pagingToolbar;
	} 
	
	
    /* 创建grid，定义表格的格式和内容
     * 表格中一些参数的含义：
     * store:定义数据来源
     * loacMask:是否加载提示
     * selType:用户选择的模式：行选择或者单元格选择，Ext.selection.CellModel为单元格选择
     * disableSelection:值为TRUE时表示禁止选择
     * renderTo:关联表格的展现位置，与body和html中的标签进行关联
     * columns:定义表格中每一行的显示形式、数据来源
     */
     
	this.getGrid = function(store){
		grid = Ext.create('Ext.grid.Panel',{
			width:'100%',
			title:title,
			store: store,
			selModel: new Ext.selection.CheckboxModel,
			selType: 'cellmodel',
			plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1
			})],
			disableSelection: false,
			renderTo: 'grid',
			columns:col,
			dockedItems:dockedToolbar,
			bbar: getPageingToolBar(store)
		});
		return grid;
	}
	
};













