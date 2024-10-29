<%-- 
    Document   : create
    Created on : Oct 18, 2024, 4:33:48 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/test.css">
        
        
    </head>
    <body>
        <h1>Production Plan Creation</h1>
        <form action="create" method="POST">
            Plan Name: <input id="plan" type="text" name="name"/> <br/>
            From: <input id="plan" type="date" name="from"/> To: <input type="date" name="to"/> <br/>
            Workshop: <select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}">${d.name}</option>
                </c:forEach>
            </select>
            <br/>
            
            <table border="1px">
                <tr>
                    <td>Product</td>
                    <td>Quantity</td>
                    <td>Estimated Effort</td>
                </tr>
                <c:forEach items="${requestScope.products}" var="p">
                    <tr>
                        <td>${p.name}<input  type="hidden" name="pid" value="${p.id}"></td>
                        <td><input  type="text" name="quantity${p.id}"></td>
                        <td><input  type="text" name="estimatedeffort${p.id}"></td>
                    </tr>
                </c:forEach>
            </table>
            <input id="submit" type="submit" value="Save">
        </form>
        
      
        
    </body>
</html>
