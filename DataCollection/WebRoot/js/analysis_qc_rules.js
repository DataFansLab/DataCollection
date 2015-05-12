/**
 * DataCollection/analysis_qc_rules.js
 * 2014-2-27 上午10:43:00 by nano
 */

Ext.require(  
    [  
        'Ext.grid.*',  
        'Ext.toolbar.Paging',  
        'Ext.data.*',
        'Ext.layout.container.Card',
        'Ext.tab.Bar'
    ]
);

$(document).ready(function() {
	Ext.onReady(function(){
		Ext.create('Ext.tab.Panel', {
			renderTo: 'qc_rules',
			plain:true,
			width: 1000,
			selType: 'cellmodel',
			margin: '20 0 0 10',
			activeTab: 0,
			items: [{
	    		title: '解析阶段',
	   			xtype: 'grid',
	   			height: 300,
	   			width: '100%',
	    		loadMask: new Ext.LoadMask(document.body,{
	            	msg : 'Loading...',
	            }), 
	    		store: Ext.create('Ext.data.Store', {
	    			fields: ['id', 'bus_type', 'qc_type', 'qcDes', 'sysDes'],
	    			autoLoad: true,
	    			proxy: {
	    				type: 'ajax',
	    				url: '/DataCollection/query/qc_rules?business_type=jx',
	    				reader: {
	    					type : 'json',
	    					root : 'row'
	    				}
	    			}
	    		}),
	    		columns: [{
	    			text: '编号', dataIndex: 'id', flex: 1
	    		}, {
	    			text: '业务类型', dataIndex: 'bus_type', flex: 1
	    		}, {
	    			text: 'QC类型', dataIndex: 'qc_type', flex: 1
	    		}, {
	    			text: '字段描述', dataIndex: 'qcDes', flex: 4
	    		}, {
	    			text: '系统描述', dataIndex: 'sysDes', flex: 1
	    		}]
			},{
	    		//closable: true, 
	    		title: '别名匹配阶段',
	    		xtype: 'grid',
	    		height: 300,
	   			width: '100%',
	   			loadMask: new Ext.LoadMask(document.body,{
	            	msg : 'Loading...',
	            }), 
	    		store: Ext.create('Ext.data.Store', {
	    			fields: ['id', 'bus_type', 'qc_type', 'qcDes', 'sysDes'],
	    			autoLoad: true,
	    			proxy: {
	    				type: 'ajax',
	    				url: '/DataCollection/query/qc_rules?business_type=bmpp',
	    				reader: {
	    					type : 'json',
	    					root : 'row'
	    				}
	    			}
	    		}),
	    		columns: [{
	    			text: '编号', dataIndex: 'id', flex: 1
	    		}, {
	    			text: '业务类型', dataIndex: 'bus_type', flex: 1
	    		}, {
	    			text: 'QC类型', dataIndex: 'qc_type', flex: 1
	    		}, {
	    			text: '字段描述', dataIndex: 'qcDes', flex: 4
	    		}, {
	    			text: '系统描述', dataIndex: 'sysDes', flex: 1
	    		}]
			}]
		});    
	});
});