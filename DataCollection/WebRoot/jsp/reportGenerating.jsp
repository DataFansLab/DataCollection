<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

    <style>
    html {
    font-size: 62.5%;
	}
	
	body {
	    margin: 0;
	    padding: 5rem 0;
	    background: #f2f6f8;
	}
	
	#header {
	    margin-bottom: 2rem;
	    text-align: center;
	}
	#header button {
	    transition-duration: .4s;
	    margin: 0 1rem;
	    color: #ffffff;
	    border: none;
	    border-radius: 4px;
	    padding: .8rem 2rem;
	    cursor: pointer;
	    background-color: #3b83c0;
	    font-weight: 700;
	    opacity: 1;
	}
	button:hover {
	    background-color: #458ac6 !important;
	    opacity: .6 !important;
	}
	
	#content {
	    box-sizing: border-box;
	    width: 595px;
	    height: 842px;
	    margin: auto;
	    padding: 6rem;
	    font-size: 1.4rem;
	    background-color: #ffffff;
	    box-shadow: 0 0 5px #666666;
	}
	
	.title {
	    text-align: center;
	    margin-bottom: 4rem;
	}
	
	#article {
	    text-indent: 2.8rem;
	    line-height: 1.8rem;
	    border: solid 1px #ffffff;
	}
	#article:focus {
	    outline: none;
	}
	.paramFocus {
	    border: solid 1px #8dceff !important;
	}
    
    </style>
<div class="ui segment" style="background-color: #F0F0F0;">
    <div id="header">
        <button id="edit">编辑</button>
        <button id="save">保存</button>
    </div>
    <div id="content">
        <h2 class="title">机器人自动发稿系统</h2>
        <p id="article">
            <span id="poster">中证网讯&nbsp;&nbsp;&nbsp;</span>
            <span id="companyName">&nbsp;&nbsp;</span>公司（代码
            <span id="companyId">&nbsp;&nbsp;</span>）发布
            <span id="postYear">&nbsp;&nbsp;</span>年
            <span id="bulletionType">年报</span>。报告期内，公司实现营业收入
            <span id="incoming" >_____</span>元，同比
            <span id="incomingTrend">上升（或下降）</span>
            <span id="incomingTrendRate">_____</span> %；利润总额
            <span id="benefit">_____</span>元，同比
            <span id="benefitTrend">上升（或下降）</span>
            <span id="benefitTrendRate">_____</span> %；归属于母公司股东的净利润
            <span id="superCompanyBenefit">_____</span>元；同比
            <span id="superCompanyBenefitTrend">上升（或下降）</span>
            <span id="superCompanyBenefitTrendRate">_____</span>%。基本每股收益为
            <span id="benefitPerStock">_____</span>元，同比
            <span id="benefitPerStockTrend">上升（或下降）</span>
            <span id="benefitPerStockTrendRate">_____</span>%。</p>
    </div>
</div>
    <script>

    var DATA = JSON.parse(window.localStorage["caiji_data"]);

    var TREND = {
        UP: 1,
        DOWN: 2
    };

    var contentIndex = "内容",
        bulletionType = "lirun",
        content = DATA[bulletionType][contentIndex];

    function $(id) {
        return document.getElementById(id);
    }

    $("edit").addEventListener("click", function(){
        $("article").setAttribute("contenteditable","true");
        $("article").setAttribute("class","paramFocus");
    });

    $("save").addEventListener("click", function(){
        $("article").setAttribute("contenteditable","false");
        $("article").setAttribute("class","");
    });

    /* @param {
     *        "项目": "归属于少数股东的综合收益总额",
     *        "2012年1-6月": "810,392,556.58",
     *        "附注七": "",
     *        "2013年1-6月": "779,586,391.70"
     *    }: object
     * @return { currentValue: "", trend: TREND.UP, trendRate: 34.24 } : object
     */
    var getChangeRate = function(obj){
        var startTime,
            endTime,
            foreignValue,
            currentValue;
        var change = {};

        for(var key in obj) {
            if(key.indexOf("年") != -1 || key.indexOf("月") != -1) {
                var value = key.replace(/[^0-9]/ig,""); 
                if(startTime == undefined){
                    startTime = endTime = value;
                    foreignValue = currentValue = obj[key];
                    continue;
                }
                if(endTime < value) {
                    endTime = value;
                    currentValue = obj[key];
                }
                if(startTime > value) {
                    startTime = value;
                    currentValue = obj[key];
                }
            }
        }
        change.currentValue = currentValue;
        currentValue = formatNumber(currentValue);
        foreignValue = formatNumber(foreignValue);

        if(currentValue >= foreignValue) {
            change.trend = TREND.UP;
            change.trendRate = (currentValue - foreignValue) / foreignValue;
            change.trendRate = formatRate(change.trendRate, 2);
        } else {
            change.trend = TREND.DOWN;
            change.trendRate = (foreignValue - currentValue) / foreignValue;
            change.trendRate = formatRate(change.trendRate, 2);
        }
        return change;
    }
    
    /*
     * @param "3,445,454,554.54" : string
     * @return 3445454554.54 : float
     */
    var formatNumber = function(str) {
        str = str.replace(/,/g,"");
        return parseFloat(str);
    }

    /*
     * @param 0.344545455454 : float
     * @param 3 :  integer
     * @return 0.344 : float
     */
    var formatRate = function(rate, length) {
        rate = rate * 100;
        return rate.toFixed(length);;
    }

    var getItemValues = function(item) {
        var change = getChangeRate(singleItem);
        $(item).textContent = change.currentValue;
        change.trend == TREND.UP ? $(item+ "Trend").textContent ="上升" : $(item + "Trend").textContent ="下降";
        $(item+ "TrendRate").textContent = change.trendRate;
    }

    if(DATA.company != null && DATA.company != "null")
        $("companyName").textContent = DATA.company;
    if(DATA.year != null && DATA.year != "null")
        $("postYear").textContent = DATA.year;
    if(DATA.code != null && DATA.code != "null")
        $("companyId").textContent = DATA.code;
    if(DATA.type != null && DATA.type != "null")
        $("bulletionType").textContent = DATA.type;

    if(content instanceof Array) {
        for(var index = 0; index < content.length; index ++) {

            var singleItem = content[index];
            for(var key in singleItem){
                if(singleItem[key].indexOf("母公司") != -1){
                    if(singleItem[key].indexOf("净利润") == -1)
                        continue;

                    getItemValues("superCompanyBenefit");
                    continue;
                }
                if(singleItem[key].indexOf("净利润") != -1){
                    getItemValues("benefit");
                    continue;
                }
                if(singleItem[key].indexOf("营业总收入") != -1){
                    getItemValues("incoming");
                    continue;
                }
                if(singleItem[key].indexOf("基本每股收益") != -1){
                    getItemValues("benefitPerStock");
                    continue;
                }
            }
        }
    }

    </script>


