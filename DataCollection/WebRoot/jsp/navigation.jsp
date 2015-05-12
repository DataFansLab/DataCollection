<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
  
<!DOCTYPE html>
<head>
	<base href="<%=basePath%>">
	
	<meta http-equiv="Content-Type" content="text/html; charset=gbk"> 
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="This is my page">  
     
    <link rel="stylesheet" type="text/css" href="css/common-navigation.css" />  
    <script type="text/javascript" src="js/ocscript.js" ></script> 
</head>
<body>
<div class = "top_container">
    <div class = "title" >
    	数据下载
  	</div>
  	
  	<div class="imrcmain0 imgl navigation_pos">
    <div  class="imcm imde" id="imouter0">
        <ul id="imenus0">
            <li class="imatm">
                <a class="" href="#" style="height:25px; text-align:center"><span class="imea imeam"><span></span></span>主页 </a>
            </li>
            <li class="imatm">
                <a class="" href="#" style="height:25px; text-align:center"><span class="imea imeam"><span></span></span>工作平台</a>

	            <div class="imsc">
                    <div class="imsubc" style="padding:5px 0px; width:100%;top:0px;left:0px;">
                        <ul>
	                        <li><a href="http://www.veeqi.com">上传文档</a></li>
	                        <li><a href="http://www.veeqi.com">文档缺失</a></li>
	                        <li><a href="http://www.veeqi.com" >公司综合详情</a></li>
	                        <li><a href="http://www.veeqi.com">公司代码列表</a></li>
	                        <li><a href="http://www.veeqi.com">QC提示</a></li>
	                    </ul>
                    </div>
                </div>
            </li>

            <li class="imatm">
                <a href="http://www.veeqi.com" style="height:25px; text-align:center"><span class="imea imeam"><span></span></span>工具箱</a>

	            <div class="imsc">
                    <div class="imsubc" style="padding:5px 0px; width:100%;top:0px;left:0px;">
                        <ul >
	                        <li><a href="http://www.veeqi.com">启动下载程序</a></li>
	                        <li><a href="http://www.veeqi.com">下载日志</a></li>
	                        <li><a href="http://www.veeqi.com">Pdf合并</a></li>
	                        <li><a href="http://www.veeqi.com">QC日志</a></li>
	                        <li><a href="http://www.veeqi.com">任务分配</a></li>
	                    </ul>
                    </div>
                </div>
            </li>

            <li class="imatm">
                <a class="" href="#" style="height:25px; text-align:center"><span class="imea imeam"><span></span></span>系统规则</a>

                <div class="imsc">
                    <div class="imsubc" style="padding:5px 0px; width:100%;top:0px;left:0px;">
                        <ul style="">
                            <li><a href="http://www.veeqi.com">流程图</a></li>
                            <li><a href="http://www.veeqi.com">QC规则</a></li>
                        </ul>
                    </div>
                </div>
            </li>

            <li class="imatm">
                <a class="" href="#" style="height:25px; text-align:center"><span class="imea imeam"><span></span></span>导航页</a>

                <div class="imsc">
                    <div class="imsubc" style="padding:5px 0px; width:105%; left:0px;">
                        <ul>
                            <li><a href="http://www.veeqi.com">下载平台首页</a></li>
                            <li><a href="http://www.veeqi.com">解析平台首页</a></li>
                            <li><a href="http://disclosure.szse.cn/m/drgg.htm" target="_blank">深交所文档下载页</a></li>
                            <li><a href="http://www.sse.com.cn/assortment/stock/list/stockdetails/announcement/index.shtml?COMPANY_CODE=600000&FULLNAME=%E4%B8%8A%E6%B5%B7%E6%B5%A6%E4%B8%9C%E5%8F%91%E5%B1%95%E9%93%B6%E8%A1%8C%E8%82%A1%E4%BB%BD%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8" target="_blank">上交所文档下载页</a></li>
                            <li><a href="http://stockdata.stock.hexun.com/gszl/s002380.shtml" target="_blank">web数据下载页</a></li>
                        </ul>
                    </div>
                </div>
            </li>

        </ul>
        <div class="imclear">&nbsp;</div>
    </div>
	</div>
  </div>

</body>
</html>