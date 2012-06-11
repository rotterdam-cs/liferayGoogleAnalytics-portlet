<%-- 
    Document   : sense chart IE
    Created on : Aug 31, 2011, 10:42:53 AM
    Author     : chuqui
--%>
<%@page contentType="text/javascript" pageEncoding="UTF-8"%>
<%--
It dynamically loads the required IE chart js only if the browser used is IE.
--%>

<% 
        String browser = request.getParameter("browserId");
        
        if(browser!=null && "ie".equalsIgnoreCase(browser)){
%>
<jsp:include page="excanvas.js"  />
<%
        }
%>

