<%-- 
    Document   : login
    Created on : Oct 30, 2024, 10:02:55 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
    </head>
    <body>
        <form action="login" method="POST"> 
            <h2>Login</h2>
                <input type="text" name="username" required autofocus placeholder="Username"/> <br/>
              <input type="password" name="password" required autofocus placeholder="Password"/> <br/>
            <input type="submit" value="Login"/>
        </form>
    </body>
</html>
