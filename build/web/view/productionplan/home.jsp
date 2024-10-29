<%-- 
    Document   : home
    Created on : Oct 22, 2024, 8:10:45 AM
    Author     : ASUS
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
    <div class="container">
        <h1>Welcome to the Production Plan Management</h1>
        <div class="button-container">
            <a href="productionplan/create" class="button">Create Plan</a>
            <a href="pp/list" class="button">View Plans</a>
        </div>
    </div>
</body>
</html>
