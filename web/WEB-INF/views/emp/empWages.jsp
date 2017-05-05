<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工工资查询</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap-table.css">
    <link rel="stylesheet" href="/static/css/select2.min.css">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
    <link rel="stylesheet" href="/static/css/bootstrap-validate/bootstrapValidator.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap-dateTimePicker/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet/less" href="/static/css/bootstrap-dateTimePicker/datetimepicker.less">

</head>
<body>
<%@include file="../backstage/contextmenu.jsp"%>

<div class="container">
    <div class="panel-body" style="padding-bottom:0px;">
        <!--show-refresh, show-toggle的样式可以在bootstrap-table.js的948行修改-->
        <!-- table里的所有属性在bootstrap-table.js的240行-->
        <table id="table" data-single-select="true">
            <thead>
                <tr>
                    <th data-checkbox="true"></th>
                    <th data-width="15%" data-field="user.userPhone">用户手机号</th>
                    <th data-width="10%" data-field="user.userName">姓名</th>
                    <th data-width="10%" data-field="prizeSalary">奖金</th>
                    <th data-width="10%" data-field="minusSalay">罚款</th>
                    <th data-width="10%" data-field="totalSalary">总工资</th>
                    <th data-width="15%" data-field="salaryDes">工资发放描述</th>
                    <th data-width="15%" data-field="salaryTime" data-formatter="formatterDateTime">工资发放时间</th>
                    <th data-width="15%" data-field="salaryCreatedTime" data-formatter="formatterDateTime">创建时间</th>
                </tr>
            </thead>
        </table>
            <%--
                只有超级管理员和汽修公司的管理员能对员工工资进行录入和修改,普通员工的话只能分页查询自己的工资
                添加员工工资 对工资的管理应该属于财务的管理员负责，即使是超级管理员应该也不能对工资进行录入或者修改，
                所以，这一块，就只用来查询员工自己的工资
            --%>
    </div>
</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/backstage/emp/jquery-ui.js"></script>
<script src="/static/js/backstage/emp/jquery.formFillTemp.js"></script>
<script src="/static/js/bootstrap-table/bootstrap-table.js"></script>
<script src="/static/js/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="/static/js/select2/select2.js"></script>
<script src="/static/js/sweetalert/sweetalert.min.js"></script>
<script src="/static/js/contextmenu.js"></script>
<script src="/static/js/backstage/main.js"></script>
<script src="/static/js/bootstrap-validate/bootstrapValidator.js"></script>
<script src="/static/js/bootstrap-dateTimePicker/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/bootstrap-dateTimePicker/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="/static/js/backstage/emp/empWages.js"></script>

</body>
<script>
    $(function () {
        initTable('table', '/salary/queryByPager'); // 初始化表格
    });

    function formatterDateTime(ele, row, index) {
        return new Date(ele).toLocaleString();
    }
</script>
</html>
