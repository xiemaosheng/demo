<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/19 0019
  Time: 下午 9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <script src="/js/jquery-3.1.0.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
    <script>
        $(function(){
            $("#upload").modal("show");
        });
    </script>
</head>
<body>
    <%@include file="/nav.jsp"%>
    <%@include file="upload.jsp"%>
</body>
</html>
