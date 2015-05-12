
//预加载内容
Ext.require(  
    [  
        'Ext.grid.*',  
        'Ext.toolbar.Paging',  
        'Ext.data.*'  
    ]
); 

function formatDate(value){
	return value ? Ext.Date.dateFormat(value, 'Y-m-d') : '';
}

function setDisplayValue(combo,valueStore){
	var firstValue = valueStore.getAt(0).get('name');
	combo.setRawValue(firstValue);
}

function docIdConverter(v, record) {
	return '<a href="/DataCollection/resources/pdfviewer/web/viewer.html?file=' + record.raw.doc_id + 
	'" target="_blank">' + record.raw.doc_id + '</a>';
}


var getYear = function(period){
	var years = new Array();
	for(var index = 0; ++index < period ;){
		years.push({'name':2015-index});
	}
	return years;
}

function createComponents(p, day, qcState) {
	var array = ['doc_id','company_id','company_code','company_name',
	             'doc_type','account_date','release_date','stock_type',
	             'exchange','states','qc_error_num'];
	var col = [
       {header:'文档代码',width:160,dataIndex:'doc_id',sortable:true},
       {text:'公司ID',width:100,dataIndex:'company_id',sortable:true},  
       {text:'公司代码',width:80,dataIndex:'company_code',sortable:true},
       {text:'公司名称',width:80,dataIndex:'company_name',sortable:true},
       {
       	text:'文档类型',
       	width:120,
       	dataIndex:'doc_type',
       	sortable:false,
       	field: {
       		xtype:'combobox',
			typeAhead: true,
			triggerAction: 'all',
			queryMode: 'local', 
			selectOnTab: true,
			store: ComboData.doc_type_store,
			lazyRender: true,
			displayField:'name',
			valueField:'name',
			listClass: 'x-combo-list-small',
			listeners:{
					select : function(combo, record,index){
  					isEdit = true;
					}
			}	
        }                             	
       },
       {text:'会计结算日期',field: {xtype:'textfield'}, width:100,dataIndex:'account_date',sortable:true},
       {text:'披露日期',width:100,dataIndex:'release_date',sortable:true},
       {text:'股票类型',width:70,dataIndex:'stock_type',sortable:true},
       {text:'交易所',width:60,dataIndex:'exchange',sortable:true},
       {
    		text:'文档状态',
    		width:90,
    		dataIndex:'states',
    		sortable:true,
    		renderer: function(val,cellmeta){
    			var bulletin_status = ["全选","上传中","已上传","已删除","上传失败","缺失","下载失败"];
           		val = bulletin_status[parseInt(val)];
				return val;
		}},
		{
			text:'QC提示',width:60,dataIndex:'qc_error_num',sortable:true,
			renderer: function(val,cellmeta,record){
				if (val != 0 && val != '0'){
					val = ('<a href="/DataCollection/page/qcPrompts/download?bulletin_id='+record.raw.doc_id+'" target="_blank">' + val + '</a>');
				}
					
				return val;
			}
		},
       {
       	header:'手动上传',
       	width:85,
       	sortable:false,
       	renderer: function(val,cellmeta){
       		val = "点击上传";
			cellmeta.style = 'background-color: #e3e4e6 !important; text-align:center; color:blue; cursor:pointer';
				return val;
		},
		listeners: {
			click:function(){
				$("#cover_div").css("display","block");	
				$("#upload_form").css("display","block");
			}
		}
    }];
	
	var dockedToolbar = Ext.create('Ext.toolbar.Toolbar',{
		items:[{
     		iconCls:'icon-add-filled',
     		text:'添加',
     		scope:this,
     		handler:function(){
     			$('#add_item_form').css("display","block");
     		}
     	},{
     		iconCls:'icon-delete-filled',
     		text:"删除",
     		itemId:'delete',
     		scope:this,
     		handler:function(){
     			var operateHandler = new GridOperate();
     			var data = grid.getSelectionModel().getSelection();
    			var url = '/DataCollection/delete/upload_bulletin'
    			operateHandler.deleteOnGrid(data,store,url,'doc_id');
     		}
     		
     	},{
     		iconCls:'icon-copy',
     		text:"保存",
     		scope:this,
     		handler:function(){
     			var operateHandler = new GridOperate();
     			operateHandler.alterOnGrid(grid,store,'/DataCollection/update/upload_bulletin');
     		}
     	}]}
	);
	
	var url = '/DataCollection/query/uploadBulletin';
	var smartGrid = new SmartGrid(array,url,'上传文档',col,dockedToolbar);
    var store = smartGrid.getStore();
    store.on("load", function() {
    	if (store.getCount() == 0)
    		Ext.Msg.alert('提示', '没有查询到相关数据！');
    });

	/* 
	 * 定义搜索框格式和内容
	 * store会将搜索条件以参数形式用ajax请求传给服务�?
	 * 服务器返回的结果会在加载该store的grid中显�?
	 * 注意：查询操作是在数据库中进行，并不是在页面中进行的
	 * 
	 */		
	form = new Ext.form.Panel({
	    title:'按条件搜索',
		width:'100%',
		defaultType: 'textfield',
    	frame: true,
        layout: 'column',
        margin: '10 0 20 0', 
        items: [{
            fieldLabel: '代码/名称',
            xtype:	'combo',
            labelWidth: 70,
            width:320,
            padding:'5 5 10 10',
            store: ComboData.stock_info_query_store,
            displayField:'stock_info',
            typeAhead: false,
            name:'stock_name',
            id:'stock_name',
            model:'remote', 
            minChars:2,
			enableKeyEvents:true, //允许键盘输入事件
            hideTrigger: true, 
            listeners:{
            	keyup:function(textField,e){
	                //如果键盘输入不是方向键，
	                if(!e.isNavKeyPress()){
	                	ComboData.stock_info_query_store.load({params:{params:Ext.encode(textField.getRawValue())}});
	                }
	            },
	            select: function(combo,record,index){
					var stockInfo = record[0].get('stock_info').split("|");
					Ext.getCmp('stock_name').setValue(stockInfo[2].replace(/(^\s*)|(\s*$)/g, ""));
				}
            }
        }, {
        	fieldLabel: 'QC状态',
            xtype:	'textfield',
            labelWidth:60,
            padding:'5 5 10 10',
            name: 'qc_state',
            id: 'qc_state',
            hidden: true,
            value: ''
        }, {
        	fieldLabel: 'QC提示',
            xtype:	'textfield',
            labelWidth:60,
            padding:'5 5 10 10',
            name: 'qc',
            id: 'qc',
            hidden: true,
            value: '-1'
        }, {
        	fieldLabel: '市值',
            xtype:	'textfield',
            labelWidth:60,
            padding:'5 5 10 10',
            name: 'priority',
            id: 'priority',
            hidden: true,
            value: ''
        },{
            fieldLabel: '文档ID',
            xtype:	'textfield',
            labelWidth:60,
            padding:'5 5 10 10',
            name: 'bulletin_id'
        }, {
            fieldLabel: '文档类型',
            labelWidth:60,
            padding:'5 5 10 10',
            name: 'bulletin_type',
			xtype:'combo',
			store:ComboData.doc_type_store,
			displayField:'name',
			selectOnTab: true,
			listeners:{
				afterrender: function(combo){setDisplayValue(combo,ComboData.doc_type_store);},
			}
        },{
            fieldLabel: '文档状态',
            labelWidth:60,
            padding:'5 5 10 10',
            name: 'download_status',
			xtype:'combobox',
			store:ComboData.doc_status_store,
			displayField:'name',
			valueField:'id',
			selectOnTab: true,
			listeners:{
				afterrender: function(combo){setDisplayValue(combo,ComboData.doc_status_store);}
			}
        },{
            fieldLabel: '下载日期',
            labelWidth:60,
            padding:'5 5 10 10',
            name: 'start_date',
            id: 'start_date',
			xtype: 'datefield',
			format:'Y-m-d'
        },{
            fieldLabel: '至',
            labelWidth:20,
            padding:'5 5 10 10',
            name: 'end_date',
            id: 'end_date',
			xtype: 'datefield',
			format:'Y-m-d'
        },{
            xtype: 'button',
            text: '搜索',
            width:60,
            margin: '10 5 10 10',
            id: 'queryButton',
            listeners: {
            	'click': function () {
                	var values = form.getForm().getValues();
                	if (values.stock_name == '')
    					delete values.company_id;
                	if (values.bulletin_id == '')
    					delete values.bulletin_id;
                	if (values.bulletin_type == '全选')
    					delete values.bulletin_type;
                	if (values.download_status == '' || values.download_status == '全选')
    					delete values.download_status;
                	if (values.start_date == '')
    					delete values.start_date;
                	if (values.end_date == '')
    					delete values.end_date;
                	store.proxy.url = url + '?paramList=' + Ext.encode(JSON.stringify(values));
                	store.load();
                }
            }
        }],
	 renderTo:'search_panel'
	});

	/*
	 * 创建上传文件的表�?
	 */
	var uploadPDFForm = new Ext.form.Panel({
        title: '手动上传PDF',
        bodyPadding: '10',
		frame:true,
		width:'100%',
		layout : 'hbox',	
		fileUpload:true,
		renderTo: 'upload_form',
		items: [
			{
				fieldLabel:'本地链接',
				xtype:'filefield',
				labelWidth:60,
				anchor:'100%',
				buttonText:'选择PDF文件...',
				name:'bulletin'
        	}/*,
        	{
        		fieldLabel:'网络链接',
        		xtype:'textfield',
        		anchor:'80%',
        		labelWidth:60
        	}*/
        ],
        buttons:[{
        	text:'上传',
			handler:function(){
				var selected = grid.getSelectionModel().getSelection();
				var pdfId = selected[0].get('doc_id').split(">")[1].split("<")[0];
				uploadPDFForm.getForm().submit({
					url:'/DataCollection/uploadFile/pdf?bulletin_id='+pdfId,
        			waitMsg:'文件上传',
        			timeout : 5000,
        			success:function(fp,o){
        				Ext.Msg.alert('Success','你的文件' +  '已经上传成功');
        			}
        		})
        	}
        },{
        	text:'取消',
        	handler:function(){
        		$("#upload_form").css("display","none");	
        		$("#cover_div").css("display","none");
        	}
        }]
	});
	
	var upload_form = Ext.create('Ext.form.Panel',{
    	title:'添加',
    	bodyPadding:5,
    	width:'100%',
    	closable:false,
    	frame:true,
    	fileUpload:true,
    	layout : {
            type : 'table',
            columns : 2
    	},
    	renderTo:'add_item_form', //显示位置
    	defaultType:'textfield', //name对应grid列中的detaIndex
    	items:[
    		   {fieldLabel:'股票代码',
    			name:'stock_id',
    			id:'stock_id',
    			allowBlank : false ,
    			xtype:'combo',
                labelWidth: 70,
                width:320,
                store: ComboData.stock_info_query_store,
                displayField:'stock_info',
                typeAhead: false,
                model:'remote', 
                minChars:2,
				enableKeyEvents:true, //允许键盘输入事件
                hideTrigger: true, 
                listeners:{
                	keyup:function(textField,e){
		                if(!e.isNavKeyPress()){
		                	ComboData.stock_info_query_store.load({params:{params:Ext.encode(textField.getRawValue())}});
		                }
		            },
    				select: function(combo,record,index){
    					var stockInfo = record[0].get('stock_info').split("|");
    					Ext.getCmp('stock_id').setValue(stockInfo[1].replace(/(^\s*)|(\s*$)/g, ""));
    					Ext.getCmp('bulletin_name').setValue(stockInfo[2].replace(/(^\s*)|(\s*$)/g, "") + ":");
    				}
                }},
                {fieldLabel:'文档名称',name:'bulletin_name',id:'bulletin_name',allowBlank : false,},
                {fieldLabel:'文档类型',
                 name:'bulletin_type',
                 allowBlank : false,
                 xtype:'combo',
                 store:{
                	 xtype:'Ext.data.Store',
                	 fields:['name'],
                	 data:[{'name':'第一季度季报'},{'name':'半年报'},{'name':'第三季度季报'},{'name':'年报'}]
                 },
                 queryMode : 'local',
     			 displayField : 'name'
                },
                {fieldLabel:'文档年份',
                 name:'bulletin_year',
                 allowBlank : false,
                 xtype:'combo',
                 displayField : 'name',
                 store:{
                	 xtype:'Ext.data.Store',
                	 fields:['name'],
                	 data:getYear(10)
                 }},
                {fieldLabel:'文档披露日期',name:'release_date',xtype: 'datefield',format:'Y-m-d',maxValue:new Date(),allowBlank : false},
                {
                	fieldLabel:'文档URL',
                	name:'url',
                	id:"url",
                },
                {
					fieldLabel:'本地文档',
					xtype:'filefield',
					labelWidth:60,
					width:320,
					buttonText:'选择PDF文件...',
					name:'bulletin'
            	}
        ],
    	
        buttons: [{ text: '确定',
        	handler:function(){
        		//if(this.up("form").getForm().isValid()){
        		if(true){
        			var values = upload_form.getForm().getValues();
                	//store.proxy.url = url + '?paramList=' + Ext.encode(JSON.stringify(values));
        			upload_form.submit({
						url:'/DataCollection/uploadFile/bulletinDetails',
						params:{paramsList:Ext.encode(values)},
            			success:function(fp,o){
            				upload_form.getForm().reset();
            				Ext.Msg.alert('Success','你的文件已经上传成功');
            				setTimeout(function(){Ext.MessageBox.hide(); $("#add_item_form").css("display","none");},2000);
            			},
            			failure:function(){
            				Ext.Msg.alert('Success','你的文件上传失败');
            				setTimeout(function(){Ext.MessageBox.hide()},2000);
            			}
            		});
				}
        		else{
        			Ext.Msg.alert('Warning','请把数据填写完整');
        			setTimeout(function(){Ext.MessageBox.hide()},2000);
        		}
        	}},{
        	text:'取消',
        	handler:function(){
        		$("#add_item_form").css("display","none");
        	}
        }]
    });
	
	$('#upload_form').css("display","none");
	$("#add_item_form").css("display","none");

    var grid = smartGrid.getGrid(store);

    var loadMask = new Ext.LoadMask(grid, {
		msg: '正在加载，请稍后...',
		store: store
	});
    
    if (p != 'null' && day != 'null' && qcState != 'null') {
    	Ext.getCmp('priority').setValue(p);
    	Ext.getCmp('qc').setValue('0');
    	Ext.getCmp('qc_state').setValue(qcState);
    	var date = new Date();
    	var queryDate = new Date();
		queryDate.setTime(date.getTime() - 1000*60*60*24*Number(day));
		Ext.getCmp('end_date').setValue(queryDate);
    	if (day != '5') {
    		Ext.getCmp('start_date').setValue(queryDate);
    	}
    		
    	Ext.getCmp('queryButton').fireEvent('click');
    	
    	/* reset query conditions */
    	Ext.getCmp('qc').setValue('-1');
    }
}