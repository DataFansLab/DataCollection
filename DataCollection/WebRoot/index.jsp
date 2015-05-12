<%@ page language="java" import="java.lang.*" pageEncoding="utf-8"%>
<%  
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); 
String newLocn = "page/index-download"; 
response.setHeader("Location",newLocn); 
%>
