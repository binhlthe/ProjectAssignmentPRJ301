<%-- 
    Document   : listdetail
    Created on : Oct 18, 2024, 11:52:51 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/detaillist.css">
    </head>
    <body>
        <h1>${requestScope.plan.name} Detail</h1>
        <a href="list" class="button-back">&#8592;</a>
        
        
        <form action="detail" method="POST">
            <table border="1px">
                <thead style="font-weight: bold">
                    <tr>
                        <td colspan="2">Product</td>
                        <c:forEach items="${requestScope.plan.headers}" var="h">
                            <td>${h.product.name}</td>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.datePlan}" var="d">
                    <input type="hidden" name="date" value="${d}">
                    <tr>
                        <td rowspan="3">${d}</td>
                        <td>K1<input type="hidden" name="sid${d}" value="1"></td>
                            <c:forEach items="${plan.headers}" var="h">
                        <input type="hidden" name="hid${d}" value="${h.id}"> 
                            <td><input type="text" name="quantity${h.id}1${d}" 
                                       <c:forEach items="${details}" var="detail">
                                           <c:if test="${(h.id eq detail.header.id) and (detail.date eq  d) and (detail.sid eq 1)}"> value="${detail.quantity}" </c:if>
                                       </c:forEach>></td>
                        </c:forEach>
                    </tr>
                        <tr>
                            <td>K2<input type="hidden" name="sid${d}" value="2"></td>
                            <c:forEach items="${requestScope.plan.headers}" var="h">
                           
                                
                                           <td><input type="text" name="quantity${h.id}2${d}"
                                       <c:forEach items="${requestScope.details}" var="detail">
                                           <c:if test="${(detail.header.id eq h.id) and (detail.date eq d)  and (detail.sid eq 2)}"> value="${detail.quantity}"</c:if>
                                       </c:forEach>></td>
                            </c:forEach>      
                        </tr>
                        <tr>
                            <td>K3<input type="hidden" name="sid${d}" value="3"></td>
                            <c:forEach items="${requestScope.plan.headers}" var="h">
                           
                                <td><input type="text" name="quantity${h.id}3${d}"
                                       <c:forEach items="${requestScope.details}" var="detail">
                                           <c:if test="${(detail.header.id eq h.id) and (detail.date eq d)  and (detail.sid eq 3)}"> value="${detail.quantity}"</c:if>
                                       </c:forEach>></td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
        </table>
            <input type="submit" value="Save">
        </form>
        
       
        
        
    </body>
</html>
