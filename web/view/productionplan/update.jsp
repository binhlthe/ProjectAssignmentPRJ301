<%-- 
    Document   : update
    Created on : Oct 29, 2024, 9:04:48 PM
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
        <form action="update" method="POST">
            <input type="hidden" name="plid" value="${plan.id}">
            Plan Name: <input id="plan" type="text" name="name" value="${plan.name}"/> <br/>
            From: <input id="plan" type="date" name="from" value="${plan.start}"/> To: <input type="date" name="to" value="${plan.end}"/> <br/>
             Workshop: <select name="did">
                 <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}" ${d.id eq plan.dept.id ?"selected=\"selected\"":""}>${d.name}</option>
                </c:forEach>
            </select>
            <br/>
            
            <table border="1px">
                <tr>
                    <td>Product</td>
                    <td>Quantity</td>
                    <td>Estimated Effort</td>
                </tr>
                <c:forEach items="${requestScope.plan.headers}" var="h">
                    <tr>
                    <input type="hidden" name="hid${h.product.id}" value="${h.id}">
                        <td>${h.product.name}<input  type="hidden" name="pid" value="${h.product.id}"></td>
                        <td><input  type="text" name="quantity${h.product.id}" value="${h.quantity}"></td>
                        <td><input  type="text" name="estimatedeffort${h.product.id}" value="${h.estimatedeffort}"></td>
                    </tr>
                </c:forEach>
            </table>
            <input id="submit" type="submit" value="Update">
        </form>
    </body>
</html>
