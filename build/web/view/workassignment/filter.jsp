<%-- 
    Document   : filter
    Created on : Oct 31, 2024, 7:52:20 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/workfilter.css">
    </head>
    <body>
        <h1>Filter WorkAssignments</h1>
        <a href="../home"  class="button-back">&#8592;</a>
        <form action="filter" method="GET">
            <select name="plid">
                <c:forEach items="${plans}" var="p">
                    <option value="${p.id}">${p.name}</option>
                </c:forEach>
            </select><br/>
            <select name="pid">
                <c:forEach items="${products}" var="pr">
                    <option  value="${pr.id}">${pr.name}</option>
                </c:forEach>
            </select>
            <br/>
            <select name="sid">
                <c:forEach items="${shifts}" var="s">
                    <option  value="${s.id}">${s.name}</option>
                </c:forEach>
            </select>
            <br/>
            <input type="date" name="date">
            <br/>
            <input type="submit" value="Filter" class="filter">
        </form>
        
        <table border="1px">
            <c:forEach items="${planresults}" var="p">
                
            <c:forEach items="${p.headers}" var="h">
                <c:forEach items="${h.detail}" var="d">
                    <tr>
                    
                        <td>${p.name}</td>
                        <td>${h.product.name}</td>
                        <td>${d.shift.name}</td>
                        <td>${d.date}</td>
                        <td>
                            <form action="assign" method="GET">
                                <input type="hidden" name="pdid" value="${d.id}">
                                <input type="submit" class="assign" value="Choose">
                                
                            </form>
                                 <form action="view" method="GET">
                                <input type="hidden" name="pdid" value="${d.id}">
                                <input type="submit" class="assign" value="View" style="margin-top:10px">
                                
                            </form>
                                
                        </td>
                        
                   </tr> 
                </c:forEach>
                    
                
            </c:forEach>
                        
    </c:forEach>
        </table>
    </body>
</html>
