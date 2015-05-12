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

	<div class="content">
		<div id="qc_rules"></div>
		<div id="add_item_form" style="position:fixed; top:300px; left:30%; width:700px; z-index:999; box-shadow:0 0 8px #fff;"></div>
	</div>
	
	<script type="text/javascript" src = "/DataCollection/js/portlet/GridOperate.js"></script>
    
    <script>    	
		Ext.Loader.setPath({
			'Ext.app':'/DataCollection/js/portlet'
		});
		
		Ext.Loader.setConfig({enabled:true});

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
			var tabs = Ext.widget('tabpanel', {
				renderTo: 'qc_rules',
				plain:true,
				width: 1000,
				selType: 'cellmodel',
				margin: '20 0 0 10',
				activeTab: 0,
        		items: [{
            		title: 'pdf下载',
            		//closable: true,
           			 xtype: 'qc_rules',   
            		margin: '10',
            		loadMask: new Ext.LoadMask(document.body,{
                    	msg : 'Loading...',
                    }), 
            		listeners:{
            			'activate':function(){
            				remoteStore.proxy.url = '/DataCollection/query/qc_rules?business_type=xzp';
            				remoteStore.load();
            			}
            		}
	      
        		},{
            		//closable: true, 
            		title: 'web非财务',
            		xtype: 'qc_rules',   
            		margin: '10',
            		height: null,
            		listeners:{
            			'activate':function(){
            				remoteStore.proxy.url = '/DataCollection/query/qc_rules?business_type=xzw';
            				remoteStore.load();
            			}
            		}
        		}]
    		});    
		});
		

    </script>
