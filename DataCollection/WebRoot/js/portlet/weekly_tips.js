


Ext.define('Ext.app.weekly_tips', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.weekly_tips',
    uses: [
        'Ext.data.ArrayStore'
    ],
    width:350,

    myData: [
        ['1',    2, 8,  2,  5, 8],
        ['2',    2, 0,  2,  5, 9],
        ['3',    2, 8,  0,  5, 10],
        ['4',    2, 0,  2,  5, 2],
        ['5',    2, 8,  2,  5, 0]
    ],
    

    addHref:function(val,metadata){
    	if(typeof(val)=='number'){
    		if(val == 0){
    			metadata.style = 'background-color: #FFFFCC !important; color:red; cursor:pointer';
    			return val;
    		}
    		else
    			return val;
    	}
    	else
    		return '<span>'+val+'</span>';
    },
    
    showCloseTip:function(){
    	alert('biu');
    	Ext.Msg.show({
    		title:'提示',
        	msg:'是否关闭提示？',
        	buttons:Ext.Msg.YESNO,
        	fn:function(btnId,text,opt){
        		if(btnId == "yes"){ return 1;}
        		else{return 0;}
			}
		});
    },
    

    initComponent: function(){

        var store = Ext.create('Ext.data.ArrayStore', {
            fields: [
               {name: 'col1'},
               {name: 'col2'},
               {name: 'col3'},
               {name: 'col4'},
               {name: 'col5'}
            ],
            data: this.myData
        });

        Ext.apply(this, {
            height: this.height,
            hideHeaders:true,
            store: store,
            stripeRows: true,
            columnLines: true,
            
            columns: [{
                width: 60,
                flex: 1,
                dataIndex: 'col1',
                renderer: this.addHref,
        		listeners:{
    					'click':this.showCloseTip
    				}
            },{
                width    : 60,
                //click:this.href,
                dataIndex: 'col1',
                renderer: this.addHref
                
            },{
                width    : 60,
                renderer : this.addHref,
                dataIndex: 'col3'
            },{
                width    : 60,
                renderer : this.addHref,
                dataIndex: 'col4'
            },{
                width    : 60,
                renderer : this.addHref,
                dataIndex: 'col5'
            }
        ]});

        this.callParent(arguments);
    }
});
