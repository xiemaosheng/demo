<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/18 0018
  Time: 下午 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="upload" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <form role="form" action="/word/upload" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="file" name="file">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="$('#upload').find('form').submit();">Save changes</button>
            </div>
        </div>
    </div>
</div>
