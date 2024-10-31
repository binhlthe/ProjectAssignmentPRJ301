<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Production Plan List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/list.css">
    <script>
        function removePlan(id) {
            var result = confirm("Are you sure?");
            if (result) {
                document.getElementById("formRemove" + id).submit();
            }
        }

    </script>
</head>
<body>
    <h1>Production Plan List</h1>
    <table border="1px">
        <tr>
            <td style="font-weight: bold">ID</td>
            <td style="font-weight: bold">Name</td>
            <td style="font-weight: bold">StartDate</td>
            <td style="font-weight: bold">EndDate</td>
            <td style="font-weight: bold">Remained Amount</td>
            <td style="font-weight: bold">Total Amount</td>
            <td style="font-weight: bold">Product</td>
            <td style="font-weight: bold">Estimation</td>
            <td style="font-weight: bold">Update</td>
            <td style="font-weight: bold">Delete</td>
            
        </tr>
        <c:forEach items="${requestScope.plans}" var="p">
            <tr>
                <td rowspan="${p.headers.size()}">${p.id}</td>
                <td rowspan="${p.headers.size()}"><a href="detail?plid=${p.id}">${p.name}</a> </td>
                <td rowspan="${p.headers.size()}">${p.start}</td>
                <td  rowspan="${p.headers.size()}">${p.end}</td>
                <td>
                   
                       ${p.headers[0].remainedquantity}<br/>
                    
                </td>
                <td>
                   
                       ${p.headers[0].quantity}<br/>
                    
                </td>
                <td>
                    ${p.headers[0].product.name}<br/>
                </td>
                <td>
                    ${p.headers[0].estimatedeffort}<br/>
                </td> 
                
                <td rowspan="${p.headers.size()}">
                    <a class="update" href="update?plid=${p.id}">Update</a>
                </td>
                
                <td rowspan="${p.headers.size()}">
                    <input type="button" value="Delete" onclick="removePlan(${p.id})">
                    <form id="formRemove${p.id}" action="../productionplan/delete" method="POST">
                        <input type="hidden" name="id" value="${p.id}">
                    </form>
                </td>
                
                
            </tr>
            
            <c:forEach var="i" begin="1" end="${p.headers.size()-1}" >
                <tr>
                <td>
                   
                       ${p.headers[i].remainedquantity}<br/>
                    
                </td>
                <td>
                   
                       ${p.headers[i].quantity}<br/>
                    
                </td>
                <td>
                   
                       ${p.headers[i].product.name}<br/>
                    
                </td>
                <td>
                    ${p.headers[i].estimatedeffort}<br/>
                </td>
            </tr>
            </c:forEach>
            
        </c:forEach>
    </table>
</body>
</html>
