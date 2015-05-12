/**
 * DataCollection/newTheme.js
 * 2014-02-17下午3:53:16
 */

Ext.require([
    'Ext.window.Window',
    'Ext.panel.Panel',
    'Ext.toolbar.*',
    'Ext.tree.Panel',
    'Ext.container.Viewport',
    'Ext.container.ButtonGroup',
    'Ext.form.*',
    'Ext.tab.*',
    'Ext.slider.*',
    'Ext.layout.*',
    'Ext.button.*',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',

    'Ext.perf.Monitor'
]);

function getGrid () {
    var store = getStore(),
        pagingBar = Ext.createWidget('pagingtoolbar', {
            store      : store,
            displayInfo: true,
            displayMsg : 'Displaying topics {0} - {1} of {2}'
        });

    return {
        xtype: 'gridpanel',
        renderTo: Ext.get('results'),

        height: 200,
        width : 450,
        rtl: rtl,

        x: 660, y: 560,

        tools: [{
            type: 'maximize'
        }],
        title: 'GridPanel',
        collapsible: true,

        store: store,

        columns: [
            {header: "Company",      flex: 1, sortable: true, dataIndex: 'company'},
            {header: "Price",        width: 75,  sortable: true, dataIndex: 'price'},
            {header: "Change",       width: 75,  sortable: true, dataIndex: 'change'},
            {header: "% Change",     width: 75,  sortable: true, dataIndex: 'pctChange'},
            {header: "Last Updated", width: 85,  sortable: true, xtype: 'datecolumn', dataIndex: 'lastChange'}
        ],
        loadMask: true,

        viewConfig: {
            stripeRows: true
        },

        bbar: pagingBar,
        tbar: [
            {text: 'Toolbar'},
            '->',
            {
                xtype: 'triggerfield',
                trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
                trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger'
            }
        ]
    };
}