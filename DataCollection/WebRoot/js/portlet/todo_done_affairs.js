Ext.define('Ext.app.todo_done_affairs', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.todo_done_affairs',
    uses: [
        'Ext.data.ArrayStore'
    ],
    width:300,
    
    myData: [
        ['1天',              2, 8,  2,  5, 8],
        ['2天',              2, 8,  2,  5, 9],
        ['3天',              2, 8,  2,  5, 10],
        ['4天',              2, 8,  2,  5, 10],
        ['5天',              2, 8,  2,  5, 10],
        ['>5天',             2, 8,  2,  5, 2]
    ],
    
    addHref:function(val,metadata){		
			
			return '<a href = "/DataCollection/page/upload" target = "blank">'+val+'</a>';
	},
    
    initComponent: function(){

        var store = Ext.create('Ext.data.ArrayStore', {
            fields: [
               {name: 'delayDays'},
               {name: 'col2'},
               {name: 'col3'},
               {name: 'col4'},
               {name: 'col5'},
               {name: 'col6'}
            ],
            data: this.myData
        });

        Ext.apply(this, {
        	selType: 'cellmodel',
            height: this.height,
            id:"table_container",
            //hideHeaders:true,
            store: store,
            stripeRows: true,
            columnLines: true,
            columns: [{
            	header: 'Delay(day)',
            	sortable: false,
                width: 90,
                flex: 1,
                dataIndex: 'delayDays',
                renderer: function(val,metaData){
                	metaData.style = "background-color:#e3e4e6;";
                	return val;
                }
            },{
            	header: 'P1',
            	sortable: false,
                width: 90,
                click:this.href,
                renderer : this.addHref,
                dataIndex: 'col2'
            },{
            	header: 'P2',
            	sortable: false,
                width: 90,
                renderer : this.addHref,
                dataIndex: 'col3'
            },{
            	header: 'P3',
            	sortable: false,
                width: 90,
                renderer : this.addHref,
                dataIndex: 'col4'
            },{
            	header: 'P4',
            	sortable: false,
                width: 90,
                renderer : this.addHref,
                dataIndex: 'col5'
            },{
            	header: 'P5',
            	sortable: false,
                width: 90,
                renderer : this.addHref,
                dataIndex: 'col6'
            },{
            	header: 'P6',
            	sortable: false,
                width: 90,
                renderer : this.addHref,
                dataIndex: 'col6'
            },{
            	header: 'P7',
            	sortable: false,
                width: 90,
                renderer : this.addHref,
                dataIndex: 'col6'
            },{
            	header: 'P8',
            	sortable: false,
                width: 90,
                renderer : this.addHref,
                dataIndex: 'col6'
            },{
            	header: 'P9',
            	sortable: false,
                width: 90,
                dataIndex: 'col6'
            }]
        });

        this.callParent(arguments);
    }
});

