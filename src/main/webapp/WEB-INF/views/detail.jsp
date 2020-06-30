<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>Spike item detail</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${spike.name}</h1>
        </div>

        <div class="panel-body">
            <h2 class="text-danger">
                <%--Show time icon--%>
                <span class="glyphicon glyphicon-time"></span>
                <%--Show countdown--%>
                <span class="glyphicon" id="spike-box"></span>
            </h2>
        </div>
    </div>
</div>

<div id="userPhoneModal" class="modal fade">

    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"> </span>User's phone number:
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="userPhone" id="userPhoneKey"
                               placeholder="Fill in phone number" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--Verify information--%>
                <span id="userPhoneMessage" class="glyphicon"> </span>
                <button type="button" id="userPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span> Submit
                </button>
            </div>

        </div>
    </div>

</div>

</body>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery.cookie.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery.countdown.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/javascript/spike.js"></script>

<script type="text/javascript">
    $(function() {
        spike.detail.init({
            spikeId:${spike.spikeId},
            startTime:${spike.startTime.time},
            endTime:${spike.endTime.time}
        })
    })
</script>
</html>