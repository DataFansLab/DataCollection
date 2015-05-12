<%@ page pageEncoding="utf-8"%> 

    
    <style type = "text/css">
    	a{		
    		color:black;
    		text-decoration:none;
    	}
    	a:hover{   		
    		font-weight:bold;
    		color:rgb(255, 152, 52);
    	}

    	.green-row .x-grid-cell {
        	background-color: #99CC99 !important;
		}
    	
  	</style>	

    <div id="qc_rules" style="padding:20px 50px 300px;">          
    </div>
    
    <script>    	
		Ext.Loader.setPath({
			'Ext.app':'../js/portlet'
		});

		//预加载 内容
		Ext.require(  
    		[
        		'Ext.grid.*',  
        		'Ext.toolbar.Paging',  
        		'Ext.data.*',
        		'Ext.app.qc_rules'
    		]
		);
		Ext.onReady(function(){
			// basic tabs 1, built from existing content
		var tabs = Ext.widget('tabpanel', {
			renderTo: 'qc_rules',
			width: 1000,
			selType: 'cellmodel',
			margins: '5 5 5 5',
			activeTab: 0,
			defaults :{
				bodyPadding: 0
			},
			items: [
				{
					title: 'pdf下载',
					xtype: 'qc_rules',
					margin: '10',
					height: null,
					listeners:{
						activate: function(){ 
							remoteStore.proxy.url = '/DataCollection/qcRules/query_dl_pdf';
							remoteStore.load();
						}
					}
				},{
					title: 'web非财务',
					xtype: 'qc_rules',
					listeners:{
						activate: function(tab){
							remoteStore.proxy.url = '/DataCollection/qcRules/query_dl_web';
							remoteStore.load();
						}
					}
				}]
		});
	});
    </script>
