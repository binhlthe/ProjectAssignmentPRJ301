<%-- 
    Document   : assign
    Created on : Oct 31, 2024, 11:56:46 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            var count=1;
            function newEmp(){
                count++;
                var container= document.getElementById("container");
                var divEmp = document.createElement("div");
                var content='';
                content+= 'Employee name:  <select name="eid'+count+'">';
                    <c:forEach items="${emps}" var="e">
                            content+='<option value="${e.id}">${e.name}</option>';
                            </c:forEach>
                    content+='</select><br/>';
                content+= 'Quantity:  <input type="text" name="quantity'+count+'"><br>';
                
                divEmp.innerHTML = content;
                container.appendChild(divEmp);
                document.getElementById("indexes").value= count+","+ document.getElementById("indexes").value;
            }
        </script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/workassign.css">
    </head>
    <body>
        <h1> Assign Work For Employee</h1>
        <a href="filter"  class="button-back">&#8592;</a>
        <form action="assign" method="POST" >
            <div id="container">
                <div>
                    Employee name:  <select name="eid1">
                        <c:forEach items="${emps}" var="e">
                            <option value="${e.id}">${e.name}</option>
                        </c:forEach>
                    </select><br/>
                    
                    Quantity: <input type="text" name="quantity1"><br/>
                </div>
            </div>
            <input type="hidden" name="pdid" value="${detail.id}">
            <input type="hidden" id="indexes" name="indexes" value="1">
            <input type="button" value="+ Employee" onclick="newEmp();"/>
            <input type="submit" value="Assign">
        </form>
       
        
    </body>
</html>
