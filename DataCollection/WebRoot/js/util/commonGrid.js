/**
 * DataCollection/commonGrid.js 2014-2-20 上午11:30:45 by nano
 */

function CommonGrid(json, topBar) {

	/* get params from the json object */
	var renderTo = json.renderTo;
	var title = json.title;
	var fields = json.fields;
	var headers = json.headers;
	var flex = json.flex;
	var dataStore;
	var pagingBar;
	var grid;
	var dockedToolbar;

	this.init = function() {

		/* create data store */
		dataStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : fields,
			pageSize : 10,
			proxy: {
				type : 'ajax',
				reader : {
					type : 'json',
					root : 'rows',
					totalProperty : 'totalCount'
				}
			}
		});

		/* create pagingBar */
		pagingBar = Ext.createWidget('pagingtoolbar', {
			store : dataStore,
			displayInfo : true,
			displayMsg : '当前显示 {0} - {1} 共 {2} 条记录',
			emptyMsg : '当前无数据显示',
			afterPageText : '共 {0} 页',
			beforePageText : '当前页',
			firstText : '最前',
			lastText : '最后',
			nextText : '下一页',
			prevText : '上一页',
			refreshText : '刷新'
		});
		
		/* set selection model */
		var selModel;
		if (topBar) {
			selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode : 'MULTI',
				showHeaderCheckbox : true,
				checkOnly: true
			});
		}
		

		/* create grid component */
		grid = Ext.create('Ext.grid.Panel', {
			id : 'common_grid',
			renderTo : renderTo,
			title : title,
			store : dataStore,
			columns : this.getGridCols(),
			selModel : selModel,
			selType: 'cellmodel',
			bbar : pagingBar,
			tbar : topBar,
			dockedItems: dockedToolbar,
			autoScroll: true,
			overflowX: 'auto',
			plugins: [
			          Ext.create('Ext.grid.plugin.CellEditing', {
			              clicksToEdit: 1
			          })
			      ]
		});
		
		/* create load mask */
		var loadMask = new Ext.LoadMask(grid, {
			msg: '正在加载，请稍候...',
			store: dataStore
		});
		
		/* create tooltip */
		var view = grid.getView();
		var tip = Ext.create('Ext.tip.ToolTip', {
			target: view.el,
			delegate: 'div.x-grid-cell-inner',
			trackMouse: true,
			renderTo: Ext.getBody(),
			listeners: {
				beforeshow: function updateTipBody(tip) {
					tip.update(tip.triggerElement.innerHTML);
				}
			}
		});
		
//		dataStore.load();
	};

	this.getGridCols = function() {
		var col = new Array();// col.push(new Ext.selection.CheckboxModel());
		var i = 0;
		for (; i < fields.length; i++) {
			if (fields[i].name == 'frequency')
				col.push({
					text : headers[i],
					dataIndex : fields[i].name,
					width : flex[i],
					menuDisabled: true,
					editor: 'textfield'
				});
			else 
				col.push({
					text : headers[i],
					dataIndex : fields[i].name,
					width : flex[i],
					menuDisabled: true
				});
		}

		return col;
	};

	this.query = function(url, params) {
		dataStore.proxy.url = (url + '?paramsList=' + params);
		dataStore.load({
			params : {
				start : 0,
				limit : 10
			}
		});
		pagingBar.moveFirst();
	};
	
	this.getGrid = function() {
		return grid;
	};
}