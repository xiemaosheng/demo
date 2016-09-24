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
        function submitData(obj){
            $.ajax({
                cache: true,
                type: "POST",
                url:"/word/batch",
                data:$(obj.form).serialize(),
                async: false,
                error: function(request) {
                    alert("内部错误");
                },
                success: function(data) {
                    if(data=="success"){
                        alert("已成功发送邮件！")
                    }
                    else alert("发送失败！");
                }
            });
        }

        function addData(data, status) {
            if (status != "success") {
                alert("无法获取模板数据！");
                return;
            }

            data = data.substring(1,data.length-1);
            var obj = document.getElementById("template");
            var template = data.split(",");

            for (var index = 0; index < template.length; index++) {
                obj.innerHTML += "<option>" + template[index] + "</option>"
            }
        }
        $(function () {
            $.get("/word/viewTemplate", addData);
        });
    </script>
</head>
<body>
    <%@include file="../nav.jsp"%>
    <div class="page-header text-center">
        <h1>批量生成word文档</h1>
    </div>
    <div class="col-md-8 col-md-offset-2 col-lg-8 col-lg-offset-2">
        <form role="form" action="/word/batch" method="post">

            <div class="form-group">
                <label>请输入原始数据：</label>
                <textarea name="data" class="form-control" rows="20"></textarea>
            </div>
            <div class="form-group">

                <select class="form-control" id="template" name="template">
                </select>
            </div>
            <div class="form-group">
                <label>请输入目标文件名：</label>
                <input name="fileName" class="form-control" />
            </div>

            <div class="form-group">
                <input class="btn btn-success text-center col-lg-4 col-md-4 col-lg-offset-4 col-md-offset-4" onclick="submitData(this)" value="提交"/>
            </div>
        </form>
    </div>

</body>
</html>
