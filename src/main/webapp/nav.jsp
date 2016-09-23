<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/21 0021
  Time: 下午 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">智能化办公平台</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="/index.jsp">首页</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        WORD
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/word/uploadTemplate.jsp">上传word模板</a></li>
                        <li class="divider"></li>
                        <li><a href="/word/submitInfo.jsp">生成word文档</a></li>
                        <li><a href="/word/submitbatch.jsp">批量生成word文档</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        PDF
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">

                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
