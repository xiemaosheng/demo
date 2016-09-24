<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/18 0018
  Time: 下午 8:14
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
        function checkEmail(src) {
            var obj = document.getElementById("email");

            if (src.value === "true") {
                obj.disabled = "";
                obj.parentNode.style.display = "block";
            }
            else {
                obj.disabled = "disabled";
                obj.parentNode.style.display = "none";
            }
        }

        function addData(data, status) {
            if (status != "success") {
                alert("无法获取模板数据！");
                return;
            }

            data = data.replace("\"", "");
            var obj = document.getElementById("template");
            var template = data.split(",");

            for (var index = 0; index < template.length; index++) {
                obj.innerHTML += "<option>" + template[index] + "</option>"
            }
        }
        $(function () {
            $.get("/word/viewTemplate", addData);

            if(document.getElementsByName("send")[0].checked)
                checkEmail(document.getElementsByName("send")[0]);
            else checkEmail(document.getElementsByName("send")[1]);
        });
    </script>
</head>
<body>
    <%@include file="../nav.jsp"%>
    <div class="page-header text-center">
        <h1>生成word文档</h1>
    </div>
    <div class="col-md-8 col-md-offset-2 col-lg-8 col-lg-offset-2">
        <form role="form" action="/word/submit">

            <div class="form-group">
                <label>请输入原始数据：</label>
                <textarea name="data" class="form-control" rows="10"></textarea>
            </div>
            <div class="form-group">
                <label>选择模板：
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#upload">
                    上传模板
                    </button>
                </label>
                <select class="form-control" id="template" name="template">
                </select>
            </div>

            <div class="form-group">
                <label>发送邮件：</label>

                <div class="radio-inline">
                    <label>
                        <input type="radio" name="send" value="true" onchange="checkEmail(this);">
                        是
                    </label>
                </div>
                <div class="radio-inline">
                    <label>
                        <input type="radio" name="send" value="false" onchange="checkEmail(this);" checked>
                        否
                    </label>
                </div>
            </div>
            <div class="form-group" style="display:none">
                <label for="">邮箱地址：</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="" disabled>
            </div>
            <div class="form-group">
                <input type="submit" class="btn btn-success text-center col-lg-4 col-md-4 col-lg-offset-4 col-md-offset-4"/>
            </div>
        </form>
    </div>

    <%@include file="upload.jsp"%>

</body>
</html>
