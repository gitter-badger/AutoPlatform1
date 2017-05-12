<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap-table.css">
    <link rel="stylesheet" href="/static/css/select2.min.css">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
    <link rel="stylesheet" href="/static/css/table/table.css">
    <link rel="stylesheet" href="/static/css/bootstrap-switch/bootstrap-switch.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap-validate/bootstrapValidator.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap-dateTimePicker/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet/less" href="/static/css/bootstrap-dateTimePicker/datetimepicker.less">

    <title>维修保养预约电话管理</title>
</head>
<body>
<%@include file="../backstage/contextmenu.jsp"%>
<div class="container">
    <div class="panel-body" style="padding-bottom:0px;"  >
        <!--show-refredata-single-sesh, show-toggle的样式可以在bootstrap-table.js的948行修改-->
        <!-- table里的所有属性在bootstrap-table.js的240行-->
        <table id="table">
            <thead>
            <tr>
                <th data-radio="true"></th>
                <th data-width="100" data-field="userName" >
                    车主姓名
                </th>
                <th data-width="100" data-field="userPhone">
                    车主电话
                </th>
                <th data-width="100" data-field="brand.brandName">
                    汽车品牌
                </th>
                <th data-width="100" data-field="color.colorName">
                    汽车颜色
                </th>
                <th data-width="100" data-field="model.modelName">
                    汽车车型
                </th>
                <th data-width="180" data-field="arriveTime" data-formatter="formatterDate">
                    到店时间
                </th>
                <th data-width="100" data-field="plate.plateName">
                    汽车车牌
                </th>
                <th data-width="100" data-field="carPlate">
                    车牌号码
                </th>
                <th data-width="100" data-hide="all" data-field="maintainOrFix">
                    保养&nbsp;|&nbsp;维修
                </th>
                <th data-width="180" data-hide="all" data-field="appCreatedTime" data-formatter="formatterDate">
                    登记时间
                </th>
                <th data-width="100" data-hide="all" data-field="company.companyName">
                    汽修公司
                </th>
                <th data-width="100" data-hide="all" data-field="currentStatus">
                    已预约&nbsp;|&nbsp;未预约
                </th>
                <th data-width="100" data-hide="all" data-field="appoitmentStatus" data-formatter="statusFormatter">
                    记录状态
                </th>
            </tr>
            </thead>
        </table>
        <div id="toolbar" class="btn-group">
            <shiro:hasAnyRoles name="系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽修公司接待员">
            <button id="btn_available" type="button" class="btn btn-success" onclick="showAvailable();">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>可用预约记录
            </button>
            </shiro:hasAnyRoles>
            <shiro:hasAnyRoles name="系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽修公司接待员">
            <button id="btn_disable" type="button" class="btn btn-danger" onclick="showDisable()">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>禁用预约记录
            </button>
            </shiro:hasAnyRoles>
            <shiro:hasAnyRoles name="公司超级管理员,公司普通管理员,汽修公司接待员">
            <button id="btn_add" type="button" class="btn btn-default" onclick="showAdd();">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            </shiro:hasAnyRoles>
            <shiro:hasAnyRoles name="公司超级管理员,公司普通管理员,汽修公司接待员">
            <button id="btn_edit" type="button" class="btn btn-default" onclick="showEdit();">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
            </button>
            </shiro:hasAnyRoles>
            <div class="input-group" style="width:450px;float:left;padding:0;margin:0 0 0 -1px;">
                <div class="input-group-btn">
                    <button type="button" id="ulButton" class="btn btn-default" style="border-radius:0px;" data-toggle="dropdown">车主姓名/车主电话/汽车公司<span class="caret"></span></button>
                    <ul class="dropdown-menu pull-right">
                        <li><a onclick="onclikLi(this)">车主姓名/车主电话/汽车公司</a></li>
                        <li class="divider"></li>
                        <li><a onclick="onclikLi(this)">车主姓名</a></li>
                        <li class="divider"></li>
                        <li><a onclick="onclikLi(this)">车主电话</a></li>
                        <li class="divider"></li>
                        <li><a onclick="onclikLi(this)">汽车公司</a></li>
                        <li class="divider"></li>
                        <li><a onclick="onclikLi(this)">汽车车牌号</a></li>
                    </ul>
                </div><!-- /btn-group -->
                <input id="ulInput" class="form-control" onkeypress="if(event.keyCode==13) {blurredQuery();}">
                <a href="javaScript:;" onclick="blurredQuery()"><span class="glyphicon glyphicon-search search-style"></span></a>
                </input>
            </div><!-- /input-group -->
        </div>
    </div>
</div>

<!-- 添加弹窗 -->
<div id="addWindow" class="modal fade" style="overflow-y:scroll" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <span class="glyphicon glyphicon-remove closeModal" data-dismiss="modal"></span>
                    <form role="form" class="form-horizontal" id="addForm">
                        <div class="modal-header" style="overflow:auto;">
                            <h4>添加电话预约信息</h4>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否为本店会员：</label>
                            <div class="col-sm-7">
                                <input id="app" type="checkbox" onchange="appOnChange()"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">车主姓名：</label>
                            <div class="col-sm-7">
                                <input type="text" id="addUserName" placeholder="请输入车主姓名" name="userName" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">车主电话：</label>
                            <div class="col-sm-7">
                                <input type="number" id="addUserPhone" placeholder="请输入车主电话" name="userPhone" class="form-control" style="width:100%"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">汽车品牌：</label>
                            <div class="col-sm-7">
                                <select  id="addCarBrand" class="js-example-tags carBrand" name="brandId" style="width:100%">
                                </select>
                            </div>
                        </div>
                        <div id="addModelDiv" style="display: none" class="form-group">
                            <label class="col-sm-3 control-label">汽车车型：</label>
                            <div class="col-sm-7">
                                <select   id="addCarModel"class="js-example-tags carModel" name="modelId" style="width:100%">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">到店时间：</label>
                            <div class="col-sm-7">     <!-- 当设置不可编辑后, 会修改颜色, 在min.css里搜索.form-control{background-color:#eee;opacity:1} -->
                                <input id="addArriveTime" placeholder="请选择到店时间" onclick="getDate('addArriveTime')" readonly="true" type="text" name="arriveTime"
                                       class="form-control datetimepicker"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">汽车颜色：</label>
                            <div class="col-sm-7">
                                <select  class="js-example-tags carColor" name="colorId" style="width:100%">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">汽车车牌：</label>
                            <div class="col-sm-7">
                                <select  class="js-example-tags carPlate" name="plateId" style="width:100%">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">车牌号码：</label>
                            <div class="col-sm-7">
                                <input  type="number" name="carPlate" placeholder="请输入车牌号码" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">已预约&nbsp;|&nbsp;未预约：</label>
                            <div class="col-sm-7">
                                <select  class="js-example-tags form-control" name="currentStatus">
                                    <option value="已预约">已预约</option>
                                    <option value="未预约">未预约</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">保养&nbsp;|&nbsp;维修：</label>
                            <div class="col-sm-7">
                                <select  class="js-example-tags form-control" name="maintainOrFix">
                                    <option value="保养">保养</option>
                                    <option value="维修">维修</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-8">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button class="btn btn-sm btn-success" id="addButton" onclick="addSubmit();" type="button">保 存</button>
                                <input type="reset" name="reset" style="display: none;"/>
                            </div>
                        </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 修改弹窗 -->
<div class="modal fade" id="editWindow" style="overflow-y:scroll" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <form role="form" class="form-horizontal" id="editForm">
                <div class="modal-header" style="overflow:auto;">
                    <h4>修改电话预约信息</h4>
                </div>
                <input type="hidden" define="appointment.userId" name="userId" />
                <input type="hidden" define="appointment.appointmentId" name="appointmentId"/>
                <div class="form-group ">
                    <label class="col-sm-3 control-label">车主姓名：</label>
                    <div class="col-sm-7">
                        <input type="text"  name="userName" placeholder="车主姓名" define="appointment.userName" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">车主电话：</label>
                    <div class="col-sm-7">
                        <input type="number" name="userPhone" placeholder="车主电话" define="appointment.userPhone" class="form-control" style="width:100%"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">汽车品牌：</label>
                    <div class="col-sm-7">
                        <select id="editCarBrand" class="js-example-tags carBrand" define="appointment.brandId" name="brandId" style="width:100%">
                        </select>
                    </div>
                </div>
                <div id="editModelDiv" style="display: none" class="form-group">
                    <label class="col-sm-3 control-label">汽车车型：</label>
                    <div class="col-sm-7">
                        <select id="editCarModel" class="js-example-tags carModel" define="appointment.modelId" name="modelId" style="width:100%">
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">汽车颜色：</label>
                    <div class="col-sm-7">
                        <select id="editCarColor" class="js-example-tags carColor" define="appointment.colorId" name="colorId" style="width:100%">
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">汽车车牌：</label>
                    <div class="col-sm-7">
                        <select id="editCarPlate" class="js-example-tags carPlate" define="appointment.plateId" name="plateId" style="width:100%">
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">车牌号码：</label>
                    <div class="col-sm-7">
                        <input type="text" name="carPlate" placeholder="车牌号码" define="appointment.carPlate" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">到店时间：</label>
                    <div class="col-sm-7">     <!-- 当设置不可编辑后, 会修改颜色, 在min.css里搜索.form-control{background-color:#eee;opacity:1} -->
                        <input id="editArriveTime" placeholder="请选择到店时间" readonly="true" type="text" name="arriveTime" define="appointment.arriveTime"
                               class="form-control datetimepicker"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">保养&nbsp;|&nbsp;维修：</label>
                    <div class="col-sm-7">
                        <select  define="appointment.maintainOrFix" class="js-example-tags form-control" name="maintainOrFix">
                            <option value="保养">保养</option>
                            <option value="维修">维修</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">已预约&nbsp;|&nbsp;未预约：</label>
                    <div class="col-sm-7">
                        <select  define="appointment.currentStatus" class="js-example-tags form-control" name="currentStatus">
                            <option value="已预约">已预约</option>
                            <option value="未预约">未预约</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-8">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button class="btn btn-sm btn-success" onclick="editSubmit();" type="button">添加</button>
                    </div>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="appWindow" class="modal fade" aria-hidden="true" style="overflow-y:scroll" data-backdrop="static" keyboard:false>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 b-r">
                        <h3 class="m-t-none m-b">选择预约记录</h3>
                        <table class="table table-hover" id="appTable">
                            <thead>
                            <tr>
                                <th data-checkbox="true"></th>
                                <th data-field="userEmail">
                                    用户邮箱
                                </th>
                                <th data-field="userPhone">
                                    用户电话
                                </th>
                                <th data-field="userIdentity">
                                    用户身份证
                                </th>
                                <th data-field="userPwd">
                                    用户密码
                                </th>
                                <th data-field="userNickname">
                                    用户昵称
                                </th>
                                <th data-field="userName">
                                    用户姓名
                                </th>
                                <th data-field="userGender">
                                    用户性别
                                </th>
                                <th data-field="userBirthday">
                                    用户生日
                                </th>
                                <th data-field="userAddress">
                                    用户地址
                                </th>
                                <th data-field="qqOpenId">
                                    用户qq
                                </th>
                                <th data-field="weiboOpenId">
                                    用户微博
                                </th>
                                <th data-field="wechatOpenId">
                                    用户微信
                                </th>
                                <th data-field="userIcon">
                                    用户头像
                                </th>
                                <th data-field="userDes">
                                    用户描叙
                                </th>
                                <th data-field="companyId">
                                    用户所属公司
                                </th>
                                <th data-field="userSalary">
                                    用户基本工资
                                </th>
                                <th data-field="userCreatedTime">
                                    用户创建时间
                                </th>
                                <th data-field="userLoginedTime">
                                    用户最近一次登录时间
                                </th>
                                <th data-field="userStatus">
                                    用户状态
                                </th>
                            </thead>
                            <tbody>
                            </tbody>

                        </table>
                        <div style="height: 100px;"></div>
                        <div class="modal-footer" style="overflow:hidden;">
                            <button type="button" class="btn btn-default" onclick="closeAppWin()">关闭
                            </button>
                            <input type="button" class="btn btn-primary" onclick="checkApp()" value="确定">
                            </input>
                        </div>
                    </div>

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
<script src="/static/js/bootstrap-select/bootstrap-select.js"></script>
<script src="/static/js/bootstrap-dateTimePicker/bootstrap-datetimepicker.min.js"></script>
<script src="/static/js/bootstrap-dateTimePicker/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="/static/js/backstage/main.js"></script>
<script src="/static/js/bootstrap-switch/bootstrap-switch.js"></script>
<script src="/static/js/bootstrap-validate/bootstrapValidator.js"></script>
<script src="/static/js/backstage/customerBooking/phoneResrvation.js"></script>
</body>
</html>
