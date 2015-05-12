
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

var main = function(){
	
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
            		url: '/DataCollection/query/downloadHome/0',
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
            		url: '/DataCollection/query/downloadHome/1',
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
    
    
    var missingDocTip = Ext.create('Ext.grid.Panel',{
    	renderTo:'sys_tips2',
    	width:285,
    	selType: 'cellmodel',
    	title: '缺失文档的系统提示', 
    	margins: '5 5 5 5',
    	store: {
    		fields: ['id', 'data'],
    		proxy: {
    			type: 'ajax',
    			url: '/DataCollection/query/homeMissing',
    			reader: {
    				type: 'json',
    				root: 'row'
    			}
    		},
    		autoLoad: true
    	},
    	columns: [
    	    {header: '类别', dataIndex: 'id', flex: 1, renderer: missingCell},
    	    {header: '统计', dataIndex: 'data', flex: 1, renderer: missingCell}
    	],
    	height: 120
    });
    
    new Ext.LoadMask(missingDocTip, {
		msg: '正在加载，请稍候...',
		store: missingDocTip.store
	});
    
    missingDocTip.addListener('cellclick',cellclick); 
	function cellclick(sysTips2, rowIndex, columnIndex, e) {
		//sysTips2.getView().getCell(0,0).style.backgroundColor="#FF9999";
	}
	
	var webDataTip = new Ext.grid.Panel({
    	renderTo:'sys_tips3',
    	width:285,
    	hideHeaders:true,
    	title: 'web非财务数据提示', 
    	margins: '5 5 5 5',
    	store: {
    		fields: ['id', 'data'],
    		data: [ 
    			{'id':'代码名称不匹配','data':'3'},
    			{'id':'字段值缺失','data':'8'},
    			{'id':'数值变动差异过大','data':'4'}
    		] }, 
    		columns: [
    			{   				
    				width:150,  				
    				dataIndex: 'id',
    				renderer:function(val,cellmeta){
    					cellmeta.style = 'background-color: #e3e4e6 !important;';
    					return val;
    				}
    			}, 
    			{
    				width:130,   				
    				dataIndex: 'data',
    				renderer:function(val,cellmeta){
    					cellmeta.style = 'background-color: #ffffff !important; cursor:pointer';
    					return val;
    				},
    				listeners:{
    					'click':function(){
    						Ext.Msg.show({
    						title:'提示',
        					msg:'是否关闭提示？',
        					buttons:Ext.Msg.YESNO,
        					fn:function(btnId,text,opt){
            					if(btnId == "yes"){
                					return 1;
            					}else{
            						return 0;
           						}
         					}
    						});
    					}
    				}
    			}
    		]
    });
    
    
    var addHref = function(val, metaData){
		if((typeof(val)=='number') && (val == 0)){
			return '<div style="width:130%; position:relative; right:6px; padding-left:6px; background-color: #FFFFCC ; color:red; cursor:pointer">' + val + '</div>';
			
    	}
    	return val;
    }
    

    /*
     * 用户选中单元格后
     * 判断单元格中的内容是否为高亮提示的内容
     * 如果是，则弹出是否关闭提示窗口 
     */
    function showCloseTip(table,TDele){
    	var cellselect = weeklyTips.getSelectionModel().getCurrentPosition();
    	var columnname = cellselect.columnHeader.dataIndex;
    	var record = weeklyTips.getStore().getAt(cellselect.row);
    	var value = record.get(columnname);
    	if(value == 0 && $(TDele).attr("title")!= "clicked") {
    		Ext.Msg.show({
    			title:'提示',
        		msg:'是否关闭提示？',
        		buttons:Ext.Msg.YESNO,
        		fn:function(btnId,text,opt){
        			if(btnId == "yes"){
        				//$(TDele).children().children().css("background-color","white");
        				$(TDele).children().remove();
        				$(TDele).append("<div style='padding-left:6px'>0<div>");
        				$(TDele).attr("title","clicked");
        				$(TDele).children().children().remove();
        				return 1;
        			}
        			else{return 0;}
				}
			});
    	}
    }
    
    var weeklyTips = new Ext.grid.Panel({
    	renderTo: 'sys_tips1',
    	width:305,
    	height: 120,
    	selType: 'cellmodel',
    	//hideHeaders:true,
    	title: '每周的系统提示', 
    	margins: '5 5 5 5',
    	store: Ext.create('Ext.data.Store', {    		
    		fields: [
    		    'exchange', 'w1', 'w2', 'w3', 'w4'
            ],
            proxy: {
            	type: 'ajax',
            	url: '/DataCollection/query/exchangeWeekly',
            	reader: {
            		type: 'json',
            		root: 'row'
            	}
            },
            autoLoad: true
    	}),
    	
    	columns: [{
    			header:'交易所',
                dataIndex: 'exchange',
                flex: 1,
                renderer: sysWeeklyCell
            },{
            	header:'w1',
                dataIndex: 'w1',
                flex: 1,
                renderer: sysWeeklyCell
            },{
            	header:'w2',
                dataIndex: 'w2',
                flex: 1,
                renderer: sysWeeklyCell
            },{
            	header:'w3',
                dataIndex: 'w3',
                flex: 1,
                renderer: sysWeeklyCell
            },{
            	header:'w4',
                dataIndex: 'w4',
                flex: 1,
                renderer: sysWeeklyCell
            }
        ]});
    weeklyTips.on("cellclick",showCloseTip,this);
    
    new Ext.LoadMask(weeklyTips, {
		msg: '正在加载，请稍候...',
		store: weeklyTips.store
	});
}
	Ext.onReady(main);
	window.setTimeout(function(){
		var arr2 = [1,1,2,3,5,9];
		var index = 0;
		$('#table_container').find('> div:eq(1)').find('td').each(
			function(i){
				if(i%10 == 0){
				}
				else{
					var row = parseInt(i/10);
					var col = parseInt(i%10);
					if(col <= arr2[row])
						$(this).find('div').css({background:"#FFFFCC"}).end().find('a').css("color","red");
				}
				
			}
		);
	}, 1000)

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
		return '<a href="/DataCollection/page/upload?p=' + (colIndex - 1) + '&day=' + rowIndex + '&qcState=' + store.storeId + '" target="_blank">' + value + '</a>';
	
	return value;
}

function sysWeeklyCell(value, cellmeta, record, rowIndex, colIndex, store) {
	if (value == 0) {
		cellmeta.tdCls = 'home-grid-important-cell';
		cellmeta.style = 'color: red !important';
	}
	if (colIndex == 0) {
		cellmeta.tdCls = 'delay-day';
	}
	return value;
}

function missingCell(value, cellmeta, record, rowIndex, colIndex, store) {
	if (value == 0) {
		cellmeta.tdCls = 'home-grid-important-cell';
		cellmeta.style = 'color: red !important';
	}
	if (colIndex == 0) {
		cellmeta.tdCls = 'delay-day';
	}
	
	if (!isNaN(value) && value != 0)
		return '<a href="/DataCollection/page/bulletinMissing?flag=1" target="_blank">' + value + '</a>';
	return value;
}