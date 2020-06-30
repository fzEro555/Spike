<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>Spike item list</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>Spike list</h2>
        </div>

        <div class="panel-body">
            <table class="table table-hover">
                <thread>
                    <tr>
                        <th>Name</th>
                        <th>Inventory</th>
                        <th>Start time</th>
                        <th>End time</th>
                        <th>Published time</th>
                        <th>Detail</th>
                    </tr>
                </thread>
                <tbody>
                <c:forEach items="${list}" var="s">
                    <tr>
                        <td>${s.name}</td>
                        <td>${s.quantity}</td>
                        <td>
                            <fmt:formatDate value="${s.startTime}" pattern="MM/dd/yyyy HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${s.endTime}" pattern="MM/dd/yyyy HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${s.createTime}" pattern="MM/dd/yyyy HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="btn btn-info" href="${s.spikeId}/detail" target="_blank">detail</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
</body>
</html>