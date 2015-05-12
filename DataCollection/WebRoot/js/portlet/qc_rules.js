    
Ext.define('Model',
{
	extend:'Ext.data.Model',
	fields: [
		{name:'id'},
		{name:'bus_type'},
		{name:'qc_type'},
		{name:'qcDes'},
		{name:'sysDes'}
		]
});

var operateHandler = new GridOperate();
        
var remoteStore = Ext.create('Ext.data.Store',{
	model:'Model',
	autoLoad:true,
	proxy:{
		type:'ajax',
		url:'/DataCollection/query/qc_rules?business_type=xzp',
		reader: {
			type: 'json',
			root: 'rows'
		}  
	}
});

var form = Ext.create('Ext.form.Panel',{
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
	items:[{fieldLabel:'id',name:'id',value:'QXP000006'},
	       {
			fieldLabel:'业务类型',name:'bus_type',
    	    xtype:'combobox',
    	    displayField:'name',
    	    store:Ext.create('Ext.data.Store', {
    			fields: ['name'], 
    			data : [
    				{"name":"xzp"},
    				{"name":"xzw"}
    			]})
	       },
		   {fieldLabel:'QC类型',name:'qc_type'},
		   {fieldLabel:'字段描述',name:'qcDes'},
		   {fieldLabel:'系统描述',name:'sysDes'}
    ],
	
    buttons: [{
    	text: '确定',
    	handler:function(){
    		var values = form.getForm().getValues();
    		console.log(values);
    		operateHandler.commitAdd(remoteStore,values,'/DataCollection/insert/qc_rules');
    	}
    },{
    	text:'取消',
    	handler:function(){
    		$("#add_item_form").css("display","none");
    	}
    }]
});
$("#add_item_form").css("display","none");     
        
var grid = Ext.define('Ext.app.qc_rules', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.qc_rules',
    uses: [
        'Ext.data.ArrayStore'
    ],
    width:900,
    addHref:function(val,metadata){
		if(val > 6){
			metadata.style = 'background-color: #FFFFCC !important; color:red; cursor: pointer';
			return '<a href = "upload" target = "blank"><span style="width:60px; color:red;">'+val+'</span></a>';
		}
		else
			return val;
	},

    initComponent: function(){
        Ext.apply(this, {
            height: this.height,
            selModel: new Ext.selection.CheckboxModel,
            selType: 'cellmodel',
            //hideHeaders:true,
            store: remoteStore,
            stripeRows: true,
            columnLines: true,
            plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
            			clicksToEdit: 1
        			})],
            columns: [{
            	header: 'id',
            	sortable: false,
            	width: 120,
                editable:false,
                dataIndex: 'id'
               
            },{
            	header: '业务类型',
            	field: 'textfield',
            	sortable: false,
            	width: 80,               
                dataIndex: 'bus_type'
            },{
            	header: 'QC类型',
            	field: 'textfield',
            	sortable: false,
            	width: 80,               
                dataIndex: 'qc_type'
            },{
            	header: '字段描述',
            	field: 'textfield',
            	width: 550,
            	sortable: false,
                dataIndex: 'qcDes',
                //在单元格内容超出单元格范围的时候自动换行
                renderer: function(value,meta,record){
                	meta.style = 'white-space:normal;';
                	return value;
                }
            },{
            	header: '系统描述',
            	field: 'textfield',
            	sortable: false,
            	width: 230,
                dataIndex: 'sysDes'
            }],
        	dockedItems:[
        	{	xtype:'toolbar',
            	items:[{
            		iconCls:'icon-add-filled',
            		text:'添加',
            		scope:this,
            		handler: function(){
            			$("#add_item_form").css("display","block");
            		}
            		
            	},{
            		iconCls:'icon-delete-filled',
            		text:"删除",
            		scope:this,
            		handler: function(){
            			var data = this.getSelectionModel().getSelection();
            			var url = '/DataCollection/delete/qc_rules'
            			operateHandler.deleteOnGrid(data,remoteStore,url,'id');
            		}
            	},{
            		iconCls:'icon-copy',
            		text:"保存",
            		scope:this,
            		handler:function (){
            			var url = '/DataCollection/update/qc_rules?business_type=xzp';
            			operateHandler.alterOnGrid(grid,remoteStore,url);
					}
            	}]
            }]
        });

        this.callParent(arguments);
    }
});
