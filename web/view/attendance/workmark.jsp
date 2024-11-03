<%-- 
    Document   : workmark
    Created on : Nov 3, 2024, 2:50:51 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/workmark.css">
    </head>
    <body>
        
        <form action="workmark" method="POST">
            <table border="1px">
                <thead>
                    <tr>
                        <td style="font-weight: bold">Work ID</td>
                        <td style="font-weight: bold">Employee Name</td>
                        <td style="font-weight: bold">Actual Quantity</td>
                        <td style="font-weight: bold">Alpha</td>
                    </tr>
                </thead>
                <tbody>
                    
                        <tr>
                            <td><input type="text" value="${wa.id}" readonly="">
                                <input type="hidden" name="waid" value="${wa.id}">
                            </td>
                            <td><input type="text" value="${wa.employee.name}" readonly=""></td>
                            <td><input type="text" name="actualquantity" value="${wa.attendance.actualquantity}"></td>
                            <td><input type="text" name="alpha" value="${wa.attendance.alpha}"></td>
                            
                        </tr>
                    
                </tbody>
            </table>
                            <input type="submit" value="Save">
        </form>
    </body>
</html>
