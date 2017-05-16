<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/11
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>维修保养提醒</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap-table.css">
    <link rel="stylesheet" href="/static/css/select2.min.css">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
    <link rel="stylesheet" href="/static/js/plugins/layui/css/layui.css" media="all">
</head>
<body>
<%@include file="../backstage/contextmenu.jsp" %>

<div class="container">
    <div class="panel-body" style="padding-bottom:0px;">
        <!--show-refresh, show-toggle的样式可以在bootstrap-table.js的948行修改-->
        <!-- table里的所有属性在bootstrap-table.js的240行-->
        <table id="table">
            <thead>
            <tr>
                <th data-checkbox="true"></th>
                <th data-field="user.userName">用户名</th>
                <th data-field="lastMaintainTime" data-formatter="formatterDate">上次维修保养时间</th>
                <th data-field="lastMaintainMileage">上次汽车行驶里程</th>
                <th data-field="remindMsg">维修保养提醒消息</th>
                <th data-field="remindTime" data-formatter="formatterDate">维修保养提醒时间</th>
                <th data-field="remindType">维修保养提醒方式</th>
                <th data-field="remindCreatedTime" data-formatter="formatterDate">提醒记录创建时间</th>
            </tr>
            </thead>
        </table>
        <div id="toolbar" class="btn-group">
            <shiro:hasAnyRoles name="系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽车公司接待员">
                <button type="button" class="btn btn-success" onclick="showRemindUser()">
                    查看需要维修保养提醒的车主
                </button>
            </shiro:hasAnyRoles>
            <%--<button type="button" class="btn btn-w-m btn-info" onclick="showAdd();">保养提醒用户</button>--%>
            <%--<button id="btn_edit" type="button" class="btn btn-default" onclick="showEdit();">--%>
            <%--<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改--%>
            <%--</button>--%>
        </div>
    </div>
</div>

<!-- 添加弹窗 -->
<div class="modal fade" id="addWindow" aria-hidden="true" style="overflow:auto; ">
    <div class="modal-dialog" style="width: 790px;height: auto;">
        <div class="modal-content" style="overflow:hidden;">
            <form class="form-horizontal" id="addForm" method="post">
                <input id="addRemindId" type="text" name="remindId">
                <input id="addLastMaintainTime" type="text" name="lastMaintainTime" >
                <input id="addLastMaintainMileage" type="text" name="lastMaintainMileage" >
                <input id="addUserId" type="text" name="userId">
                <div class="modal-header" style="overflow:auto;">
                    <h4>请填写维修维修保养提醒信息</h4>
                </div>
                <br/>
                <div class="form-group">
                    <label class="col-sm-3 control-label">用户名：</label>
                    <div class="col-sm-7">
                        <input id="addUserName" type="text" readonly class="form-control" style="width: 315px;">
                            <button type="button" class="btn btn-default" onclick="showCheckUser();">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>请选择用户
                        </button>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">维修保养提醒内容：</label>
                    <div class="col-sm-7">
                        <textarea type="text" name="remindMsg" placeholder="请输入维修保养提醒内容" style="height: 100px;"
                                  class="form-control"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">维修保养提醒时间：</label>
                    <div class="col-sm-7">
                        <input id="addRemindTime" name="remindTime" readonly class="layui-input">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">维修保养提醒方式：</label>
                    <div class="col-sm-7">
                        <select name="remindType" class="form-control js-data-example-ajax">
                            <option value="短信提醒">短信提醒</option>
                            <option value="邮箱提醒">邮箱提醒</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">维修保养记录创建时间：</label>
                    <div class="col-sm-7">
                        <input id="addRemindCreatedTime" name="remindCreatedTime" readonly class="layui-input">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-8">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button id="addButton" class="btn btn-sm btn-success" type="button" onclick="addSubmit()">保 存</button>
                    </div>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!-- 修改弹窗 -->
<div class="modal fade" id="editWindow" aria-hidden="true">
    <div class="modal-dialog" style="width: 790px;height: auto;">
        <div class="modal-content">
            <form class="form-horizontal" id="editForm" method="post">
                <input type="hidden" name="remindId" define="MaintainRemind.remindId">
                <input id="editLastMaintainTime" type="hidden" name="lastMaintainTime" define="MaintainRemind.lastMaintainTime">
                <input id="editLastMaintainMileage" type="hidden" name="lastMaintainMileage" define="MaintainRemind.lastMaintainMileage">
                <input id="editUserId" type="hidden" name="userId" define="MaintainRemind.user.userId">
                <div class="modal-header" style="overflow:auto;">
                    <h4>请修改维修维修保养提醒信息</h4>
                </div>
                <br/>
                <div class="form-group">
                    <label class="col-sm-3 control-label">用户名称：</label>
                    <div class="col-sm-7">
                        <input id="editUserName" type="text" readonly define="MaintainRemind.user.userName" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">维修保养提醒内容：</label>
                    <div class="col-sm-7">
                        <textarea type="text"  name="remindMsg" define="MaintainRemind.remindMsg"  placeholder="请输入维修保养提醒内容" style="height: 100px;"
                                  class="form-control"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">维修保养提醒时间：</label>
                    <div class="col-sm-7">
                        <input id="editRemindTime" name="remindTime" define="MaintainRemind.remindTime" readonly class="layui-input">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">维修保养提醒方式：</label>
                    <div class="col-sm-7">
                        <select id="editRemindType" name="remindType" define="MaintainRemind.remindType" class="form-control js-data-example-ajax">
                            <option value="短信提醒">短信提醒</option>
                            <option value="邮箱提醒">邮箱提醒</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">提醒记录创建时间：</label>
                    <div class="col-sm-7">
                        <input id="editRemindCreatedTime" name="remindCreatedTime" define="MaintainRemind.remindCreatedTime" readonly class="layui-input">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-8">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button id="editButton" class="btn btn-sm btn-success" type="button" onclick="editSubmit()">保 存</button>
                    </div>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="showUserWindow" class="modal fade" aria-hidden="true" style="overflow-y:scroll" data-backdrop="static"
     keyboard:false>
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-body">
                <span class="glyphicon glyphicon-remove closeModal" onclick="closeUserWin()"></span>
                <h3>选择车主</h3>
                <table class="table table-hover" id="addUserTable" style="table-layout: fixed">
                    <thead>
                    <tr>
                        <th data-checkbox="true"></th>
                        <th data-field="user.userName">
                            用户名称
                        </th>
                        <th data-field="lastMaintainTime" data-formatter="formatterDate">
                            上次维修保养时间
                        </th>
                        <th data-field="lastMaintainMileage">
                            上次汽车行驶里程
                        </th>
                    </tr>
                    </thead>
                </table>
                <div class="modal-footer" style="overflow:hidden;">
                    <button type="button" class="btn btn-default" onclick="closeUserWin()">关闭
                    </button>
                    <input type="button" class="btn btn-primary" onclick="checkUser()" value="确定">
                    </input>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="RemindUserWindow" class="modal fade" aria-hidden="true" style="overflow-y:scroll" data-backdrop="static"
     keyboard:false>
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-body">
                <span class="glyphicon glyphicon-remove closeModal" onclick="closeRemindUserWin()"></span>
                <h3>查看需要维修保养提醒的车主</h3>
                <table class="table table-hover" id="showRemindUserTable" style="table-layout: fixed">
                    <thead>
                    <tr>
                        <th data-checkbox="true"></th>
                        <th data-field="user.userName">
                            用户名称
                        </th>
                        <th data-field="actualEndTime" data-formatter="formatterDate">
                            上次维修保养时间
                        </th>
                        <th data-field="checkin.carMileage">
                            上次汽车行驶里程
                        </th>
                    </tr>
                    </thead>
                </table>
                <div id="remindToolbar" class="btn-group">
                    <shiro:hasAnyRoles name="公司超级管理员,公司普通管理员,汽车公司接待员">
                        <button type="button" class="btn btn-w-m btn-info" onclick="showAddRemindUser();">保养提醒用户</button>
                    </shiro:hasAnyRoles>
                </div>
                <div class="modal-footer" style="overflow:hidden;">
                    <button type="button" class="btn btn-default" onclick="closeRemindUserWin()">关闭
                    </button>
                    <%--<input type="button" class="btn btn-primary" onclick="checkUser()" value="确定">--%>
                    <%--</input>--%>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/bootstrap-table/bootstrap-table.js"></script>
<script src="/static/js/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="/static/js/jquery.formFill.js"></script>
<script src="/static/js/select2/select2.js"></script>
<script src="/static/js/sweetalert/sweetalert.min.js"></script>
<script src="/static/js/contextmenu.js"></script>
<script src="/static/js/backstage/custManage/maintainremind.js"></script>
<script src="/static/js/bootstrap-validate/bootstrapValidator.js"></script>
<script src="/static/js/plugins/layui/layui.js" charset="utf-8"></script>
<script src="/static/js/backstage/main.js"></script>
<script>
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        var addRemindTime = {
            format: 'yyyy-MM-dd hh:mm:ss',
            min: laydate.now(), //设定最小日期为当前日期
            max: '2099-12-30 23:59:59', //最大日期
            istime: true,
            istoday: false,
            festival: true
        };

        document.getElementById('addRemindTime').onclick = function () {
            addRemindTime.elem = this;
            laydate(addRemindTime);
        }

        var addRemindCreatedTime = {
            format: 'yyyy-MM-dd hh:mm:ss',
            max: '2099-12-30 23:59:59', //最大日期
            istime: true,
            istoday: false,
            festival: true
        };

        document.getElementById('addRemindCreatedTime').onclick = function () {
            addRemindCreatedTime.elem = this;
            laydate(addRemindCreatedTime);
        }

        var editRemindTime = {
            format: 'yyyy-MM-dd hh:mm:ss',
            min: laydate.now(), //设定最小日期为当前日期
            max: '2099-12-30 23:59:59', //最大日期
            istime: true,
            istoday: false,
            festival: true
        };

        document.getElementById('editRemindTime').onclick = function () {
            editRemindTime.elem = this;
            laydate(editRemindTime);
        }

        var editRemindCreatedTime = {
            format: 'yyyy-MM-dd hh:mm:ss',
            max: '2099-12-30 23:59:59', //最大日期
            istime: true,
            istoday: false,
            festival: true
        };

        document.getElementById('editRemindCreatedTime').onclick = function () {
            editRemindCreatedTime.elem = this;
            laydate(editRemindCreatedTime);
        }
    });
</script>
</body>
</html>
