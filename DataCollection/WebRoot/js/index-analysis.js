
Ext.Loader.setPath({
	'Ext.app':'../js/portlet'
});


Ext.Loader.setConfig({enabled:true});

//预加载 内容
Ext.require(  
    [  
        'Ext.grid.*',  
        'Ext.toolbar.Paging',  
        'Ext.data.*',
        'Ext.app.weekly_tips'
    ]
);  

Ext.onReady(function(){

	$("#menu-item-989").children().removeClass("has-submenu current");
	$("#menu-item-989").children().addClass("has-submenu");
	$("#analysis_index").children().removeClass("current");
	
	// basic tabs 1, built from existing content
	Ext.widget('tabpanel', {
        renderTo: 'affairs',
        plain:true,
       	width:950,
        selType: 'cellmodel',
        margin: '20 0 20 0',
        activeTab: 0,
        defaults :{
            bodyStyle: 'padding:0px;'
        },
        items: [{
        	xtype: 'grid',
            title: '待处理文档',
            margin: '10',
            id: 'daichuli',
            store: Ext.create('Ext.data.Store', {
            	fields: ['delay', 'p0', 'p1', 'p2', 'p3', 'p4', 'p5', 'p6', 'p7', 'p8', 'p9'],
            	proxy: {
            		type: 'ajax',
            		url: '/DataCollection/query/analysisHome/0',
            		reader: {
            			type: 'json',
            			root: 'row'
            		}
            	},
            	autoLoad: true,
            	id: '0'
            }),
            columns: [
                {text: 'Delay(day)', dataIndex: 'delay', flex: 1, renderer: cellRenderer},
                {text: 'P0', dataIndex: 'p0', flex: 1, renderer: cellRenderer},
                {text: 'P1', dataIndex: 'p1', flex: 1, renderer: cellRenderer},
                {text: 'P2', dataIndex: 'p2', flex: 1, renderer: cellRenderer},
                {text: 'P3', dataIndex: 'p3', flex: 1, renderer: cellRenderer},
                {text: 'P4', dataIndex: 'p4', flex: 1, renderer: cellRenderer},
                {text: 'P5', dataIndex: 'p5', flex: 1, renderer: cellRenderer},
                {text: 'P6', dataIndex: 'p6', flex: 1, renderer: cellRenderer},
                {text: 'P7', dataIndex: 'p7', flex: 1, renderer: cellRenderer},
                {text: 'P8', dataIndex: 'p8', flex: 1, renderer: cellRenderer},
                {text: 'P9', dataIndex: 'p9', flex: 1, renderer: cellRenderer}
            ],
            height: 200,
            selType: 'cellmodel'
        },{
        	xtype: 'grid',  
            title: '已处理文档',
            margin: '10',
            id: 'yichuli',
            store: Ext.create('Ext.data.Store', {
            	fields: ['delay', 'p0', 'p1', 'p2', 'p3', 'p4', 'p5', 'p6', 'p7', 'p8', 'p9'],
            	proxy: {
            		type: 'ajax',
            		url: '/DataCollection/query/analysisHome/1',
            		reader: {
            			type: 'json',
            			root: 'row'
            		}
            	},
            	autoLoad: true,
            	id: '1'
            }),
            columns: [
                {text: 'Delay(day)', dataIndex: 'delay', flex: 1, renderer: cellRenderer},
                {text: 'P0', dataIndex: 'p0', flex: 1, renderer: cellRenderer},
                {text: 'P1', dataIndex: 'p1', flex: 1, renderer: cellRenderer},
                {text: 'P2', dataIndex: 'p2', flex: 1, renderer: cellRenderer},
                {text: 'P3', dataIndex: 'p3', flex: 1, renderer: cellRenderer},
                {text: 'P4', dataIndex: 'p4', flex: 1, renderer: cellRenderer},
                {text: 'P5', dataIndex: 'p5', flex: 1, renderer: cellRenderer},
                {text: 'P6', dataIndex: 'p6', flex: 1, renderer: cellRenderer},
                {text: 'P7', dataIndex: 'p7', flex: 1, renderer: cellRenderer},
                {text: 'P8', dataIndex: 'p8', flex: 1, renderer: cellRenderer},
                {text: 'P9', dataIndex: 'p9', flex: 1, renderer: cellRenderer}
            ],
            height: 200,
            selType: 'cellmodel'
        }]
    });
	
	new Ext.LoadMask(Ext.getCmp('daichuli'), {
		msg: '正在加载，请稍候...',
		store: Ext.getCmp('daichuli').store
	});
    new Ext.LoadMask(Ext.getCmp('yichuli'), {
		msg: '正在加载，请稍候...',
		store: Ext.getCmp('yichuli').store
	});
});

function cellRenderer(value, cellmeta, record, rowIndex, colIndex, store) {
	if ((rowIndex == 0 || rowIndex == 1) && colIndex == 1) {
		cellmeta.tdCls = 'home-grid-important-cell';
	}
	if (rowIndex == 2 && (colIndex >= 1 && colIndex <= 2)) {
		cellmeta.tdCls = 'home-grid-important-cell';
	}
	if (rowIndex == 3 && (colIndex >= 1 && colIndex <= 3)) {
		cellmeta.tdCls = 'home-grid-important-cell';
	}
	if (rowIndex == 4 && (colIndex >= 1 && colIndex <= 5)) {
		cellmeta.tdCls = 'home-grid-important-cell';
	}
	if (rowIndex == 5 && (colIndex >= 1 && colIndex <= 10)) {
		cellmeta.tdCls = 'home-grid-important-cell';
	}
	if (colIndex == 0) {
		cellmeta.tdCls = 'delay-day';
	}
	
	if (!isNaN(value) && value != 0)
		return '<a href="/DataCollection/page/analysis/taskList?p=' + (colIndex - 1) + '&day=' + rowIndex + '&qcState=' + store.storeId + '" target="_blank">' + value + '</a>';
	
	return value;
}