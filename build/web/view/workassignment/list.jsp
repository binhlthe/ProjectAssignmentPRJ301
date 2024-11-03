<%-- 
    Document   : list
    Created on : Nov 1, 2024, 11:46:55 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/workassignmentlist.css">
        <script>
        function removeWA(id) {
            var result = confirm("Are you sure?");
            if (result) {
                document.getElementById("formRemove" + id).submit();
            }
        }

    </script>
    </head>
    <body>
        <h1>View Work Assignments</h1>
        <a href="filter"  class="button-back">&#8592;</a>
        <table border="1px">
            <thead>
            <td>Employee ID</td>
            <td>Employee Name</td>
            <td>Quantity</td>
            <td>Delete</td>
            </thead>
            <tbody>
                <c:forEach items="${was}" var="w">
                <tr>
                
                    <td> ${w.employee.id}</td>
                    <td> ${w.employee.name}</td>
                    <td>${w.quantity}</td>
                    <td>
                        <input type="button" value="Delete" onclick="removeWA(${w.id})">
                    <form id="formRemove${w.id}" action="../wa/delete" method="POST">
                        <input type="hidden" name="id" value="${w.id}">
                        <input type="hidden" name="pdid" value="${pdid}">
                    </form>
                    </td>
                    
                </tr>
            
            
                </c:forEach>
            </tbody>
            
        </table>
        
        
    </body>
</html>
