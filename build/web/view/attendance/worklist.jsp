<%-- 
    Document   : worklist
    Created on : Nov 3, 2024, 1:59:02 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/listwork.css">
    </head>
    <body>
        <h1>List Of Work</h1>
        <a href="../home"  class="button-back">&#8592;</a>
        <table border="1px">
            <thead>
                <tr>
                    <td>Detail ID</td>
                    <td>Work ID</td>
                    <td>Employee</td>
                    <td>Quantity</td>
                    <td>Option</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${was}" var="w">
                    <tr>
                        <td>${w.detail.id}</td>
                        <td>${w.id}</td>
                        <td>${w.employee.name}</td>
                        <td>${w.quantity}</td>
                        <td><form action="workmark" method="GET">
                                <input type="hidden" name="waid" value="${w.id}">
                                <input type="submit" class="worklist" value="Mark">
                                
                            </form></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
    </body>
</html>
