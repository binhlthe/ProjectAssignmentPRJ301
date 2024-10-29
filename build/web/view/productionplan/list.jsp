<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Production Plan List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/abcxyz.css">
    <script>
        function removePlan(id) {
            var result = confirm("Are you sure?");
            if (result) {
                document.getElementById("formRemove" + id).submit();
            }
        }

        function editPlan(id) {
            var row = document.getElementById("row" + id);
            var cells = row.querySelectorAll("td[data-editable]");

            for (var i = 0; i < cells.length; i++) {
                var cell = cells[i];
                var input = document.createElement("input");
                input.type = "text";
                input.value = cell.innerHTML.trim();
                cell.innerHTML = "";
                cell.appendChild(input);
            }

            document.getElementById("saveBtn" + id).style.display = "inline-block";
            document.getElementById("editBtn" + id).style.display = "none";
        }

        function savePlan(id) {
            var row = document.getElementById("row" + id);
            var inputs = row.querySelectorAll("td[data-editable] input");

            for (var i = 0; i < inputs.length; i++) {
                var input = inputs[i];
                var value = input.value.trim();
                input.parentElement.innerHTML = value;
            }

            // Submit the updated data to the server (using AJAX or form submission)
            document.getElementById("saveForm" + id).submit();
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
            <td style="font-weight: bold">Quantity</td>
            <td style="font-weight: bold">Product</td>
            <td style="font-weight: bold">Estimation</td>
            <td></td>
            <td></td>
        </tr>
        <c:forEach items="${requestScope.plans}" var="p">
            <tr id="row${p.id}">
                <td>${p.id}</td>
                <td data-editable>${p.name} </td>
                <td data-editable>${p.start}</td>
                <td data-editable>${p.end}</td>
                <td>
                    <c:forEach items="${p.headers}" var="h">
                        <span data-editable>${h.quantity}</span><br/>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${p.headers}" var="h">
                        ${h.product.name}<br/>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${p.headers}" var="h">
                        ${h.estimatedeffort}<br/>
                    </c:forEach>
                </td>
                <td>
<!--                    <button id="editBtn${p.id}" onclick="editPlan(${p.id})">Edit</button>
                    <button id="saveBtn${p.id}" style="display:none" onclick="savePlan(${p.id})">Save</button>-->
<!--                    <form id="saveForm${p.id}" action="../productionplan/update" method="POST">
                        <input type="hidden" name="id" value="${p.id}">
                         Add hidden inputs to capture updated fields if needed 
                    </form>-->
                </td>
                <td>
                    <input type="button" value="Delete" onclick="removePlan(${p.id})">
                    <form id="formRemove${p.id}" action="../productionplan/delete" method="POST">
                        <input type="hidden" name="id" value="${p.id}">
                    </form>
                </td>
                    
                    

            </tr>
        </c:forEach>
    </table>
</body>
</html>
