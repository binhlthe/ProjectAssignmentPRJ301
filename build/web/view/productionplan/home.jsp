<%-- 
    Document   : home
    Created on : Oct 22, 2024, 8:10:45 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webhome.css">
</head>
<body>
    <div  class="logout">
        <c:if test="${account ne null}">
            <form action="logout" method="GET">
            <button type="submit" >Logout</button>
        </form> 
        </c:if>
        <c:if test="${account eq null}">
            <form action="login" method="GET">
            <button type="submit" >Login</button>
        </form> 
        </c:if>
        
    </div>
    
    <div class="container">
        <h1>Welcome to Home Page of ABC Company</h1>
        
        
        <div class="button-container">
            <a href="productionplan/create" class="button">Create Plan</a>
            <a href="pp/list" class="button">View Plan</a>
            <a href="wa/filter" class="button">Assign Work</a>
            <a href="at/worklist" class="button">Mark Work</a>
            
        </div>
    </div>
</body>
</html>
