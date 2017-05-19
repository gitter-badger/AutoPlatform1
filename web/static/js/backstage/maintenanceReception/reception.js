$(function () {
    var roles = "系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽车公司接待员";
    $.post("/user/isLogin/"+roles, function (data) {
        if(data.result == 'success'){
            initTable('table', '/checkin/queryByPager'); // 初始化表格

            initSelect2("carColor", "请选择颜色", "/carColor/queryAllCarColor"); // 初始化select2, 第一个参数是class的名字, 第二个参数是select2的提示语, 第三个参数是select2的查询url
            initSelect2("carBrand", "请选择品牌", "/carBrand/queryAllCarBrand");
            initSelect2("carPlate", "请选择车牌", "/carPlate/queryAllCarPlate");

            $("#app").bootstrapSwitch({
                onText:"是",
                offText:"否",
                onColor:"success",
                offColor:"danger",
                size:"small",
                onSwitchChange:function(event,state){
                    if(state==true){
                        app = true;
                        initTableNotTollbar("appTable", "/appointment/queryByCurrentStatus");
                        $("#appWindow").modal('show');
                    }else if(state==false){
                        app = false;
                    }
                }
            })
            $("#appWindow").on("hide.bs.modal", function () {
                $("#addWindow").modal('show')
                $('#app').bootstrapSwitch('state', false);
            });
            $("#customer").bootstrapSwitch({
                onText:"是",
                offText:"否",
                onColor:"success",
                offColor:"danger",
                size:"small",
                onSwitchChange:function(event,state){
                    if(state==true){
                        customer = true;
                        initTableNotTollbar("customerTable", "/userBasicManage/queryByPager");
                        $("#customerWindow").modal('show');
                    }else if(state==false){
                        customer = false;
                    }
                }
            })
            $("#customerWindow").on("hide.bs.modal", function () {
                $("#customerWindow").modal('show')
                $('#customer').bootstrapSwitch('state', false);
            });
        }else if(data.result == 'notLogin'){
            swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"}
                ,function(isConfirm){
                    if(isConfirm){
                        top.location = "/user/loginPage";
                    }else{
                        top.location = "/user/loginPage";
                    }
                })
        }else if(data.result == 'notRole'){
            swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"})
        }
    });
});

// 这个方法别看
$("#addCarBrand").change(function(){
    var div = $("#addModelDiv");
    var select = $("#addCarBrand").select2("val");
    div.css("display","block");
    $('#addCarModel').html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
    initSelect2("carModel", "请选择车型", "/carModel/queryByBrandId/"+ select);
});
// 这个别看
$("#editCarBrand").change(function(){
    var div = $("#editModelDiv");
    var select = $("#editCarBrand").select2("val");
    div.css("display","block");
    $('#editCarModel').html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
    initSelect2("carModel", "请选择车型", "/carModel/queryByBrandId/"+select);
});

// 激活或禁用
function statusFormatter(value, row, index) {
            if(value == 'Y') {
                return "&nbsp;&nbsp;<button type='button' class='btn btn-danger' onclick='inactive(\""+'/checkin/statusOperate?id='+row.checkinId+'&status=Y'+"\")'>禁用</a>";
            } else {
                return "&nbsp;&nbsp;<button type='button' class='btn btn-success' onclick='active(\""+'/checkin/statusOperate?id='+ row.checkinId+'&status=N'+ "\")'>激活</a>";
            }
}

// 查看全部可用
function showAvailable(){
    var roles = "系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽车公司接待员";
    $.post("/user/isLogin/"+roles, function (data) {
                if(data.result == 'success'){
                    initTable('table', '/checkin/queryByPager');
                }else if(data.result == 'notLogin'){
                    swal({title:"",
                            text:data.message,
                            confirmButtonText:"确认",
                            type:"error"}
                        ,function(isConfirm){
                            if(isConfirm){
                                top.location = "/user/loginPage";
                            }else{
                                top.location = "/user/loginPage";
                            }
                        })
                }else if(data.result == 'notRole'){
                    swal({title:"",
                        text:data.message,
                        confirmButtonText:"确认",
                        type:"error"})
                }
            });
}
// 查看全部禁用
function showDisable(){
    var roles = "系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽车公司接待员";
    $.post("/user/isLogin/"+roles, function (data) {
        if(data.result == 'success'){
            initTable('table', '/checkin/queryByPagerDisable');
        }else if(data.result == 'notLogin'){
            swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"}
                ,function(isConfirm){
                    if(isConfirm){
                        top.location = "/user/loginPage";
                    }else{
                        top.location = "/user/loginPage";
                    }
                })
        }else if(data.result == 'notRole'){
            swal({title:"",
                text:data.message,
                confirmButtonText:"确认",
                type:"error"})
        }
    });
}

// 模糊查询
function blurredQuery(){
    var roles = "系统超级管理员,系统普通管理员,公司超级管理员,公司普通管理员,汽车公司接待员";
    $.post("/user/isLogin/"+roles, function (data) {
        if(data.result == 'success'){
            var button = $("#ulButton");// 获取模糊查询按钮
            var text = button.text();// 获取模糊查询按钮文本
            var vaule = $("#ulInput").val();// 获取模糊查询输入框文本
            initTable('table', '/checkin/blurredQuery?text='+text+'&value='+vaule);
        }else if(data.result == 'notLogin'){
            swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"}
                ,function(isConfirm){
                    if(isConfirm){
                        top.location = "/user/loginPage";
                    }else{
                        top.location = "/user/loginPage";
                    }
                })
        }else if(data.result == 'notRole'){
            swal({title:"",
                text:data.message,
                confirmButtonText:"确认",
                type:"error"})
        }
    });
}

// 关闭预约
function closeAppWin() {
    $("#appWindow").modal('hide');
    $("#addWinow").modal('show')
}


// 关闭预约
function closeCustomerWin() {
    $("#customerWindow").modal('hide');
    $("#addWindow").modal('show')
}

var appointment;
var user;

//监听switch的监听事件
function appOnChange() {
    if ($('#app').bootstrapSwitch('state')) {
        if (appointment != null && appointment != "" && appointment != undefined) {
            setData(appointment);
        }
    } else {
        if (appointment != null && appointment != "" && appointment != undefined) {
            clearAddForm();
        }
    }
}

// 监听switch的监听事件
function appOnChange1() {
    if ($('#customer').bootstrapSwitch('state')) {
        if (user != null && user != "" && user != undefined) {
            setData1(user);
        }
    }else {
        if (user != null && user != "" && user != undefined) {
            clearAddForm1();
        }
    }
}

// 选择预约记录
function checkApp() {
    var row = $("#appTable").bootstrapTable('getSelections');
    if (row.length != 1) {
        swal({title:"",
            text:"请选择一条预约记录",
            confirmButtonText:"确认",
            type:"error"})
        return false;
    } else {
        appointment = row[0];
        setData(appointment);
        $("#appWindow").on("hide.bs.modal", function () {
            $('#app').bootstrapSwitch('state', true);
        });
        $("#appWindow").modal('hide');
    }
}

// 选择车主记录
function checkCustomer() {
    var row = $("#customerTable").bootstrapTable('getSelections');
    if (row.length != 1) {
        swal({title:"",
            text:"请先选择一位本店车主记录",
            confirmButtonText:"确认",
            type:"error"})
        return false;
    } else {
        user = row[0];
        setData1(user);
        $("#customerWindow").on("hide.bs.modal", function () {
            $('#customer').bootstrapSwitch('state', true);
        });
        $("#customerWindow").modal('hide');
    }
}

function setData(appointment) {
    alert("set");
    $("#addUserName").val(appointment.userName);
    $("#addUserPhone").val(appointment.userPhone);
    $("#addUserId").val(appointment.userId);
    $("#addPlate").val(appointment.carPlate);
    $("#addAppointmentId").val(appointment.appointmentId);
    $('#addCarBrand').html('<option value="' + appointment.brand.brandId + '">' + appointment.brand.brandName + '</option>').trigger("change");
    $('#addCarColor').html('<option value="' + appointment.color.colorId + '">' + appointment.color.colorName + '</option>').trigger("change");
    $('#addCarModel').html('<option value="' + appointment.model.modelId + '">' + appointment.model.modelName + '</option>').trigger("change");
    $('#addCarPlate').html('<option value="' + appointment.plate.plateId + '">' + appointment.plate.plateName + '</option>').trigger("change");
    $("#addMaintainOrFix").val(appointment.maintainOrFix);
}

function setData1(user) {
    $("#addUserName").val(user.userName);
    $("#addUserPhone").val(user.userPhone);
    $("#addUserId").val(user.userId);
}

/** 清除添加的form表单信息 */
function clearAddForm() {
    $('#addCarBrand').html('').trigger("change");
    $('#addCarColor').html('').trigger("change");
    $('#addCarModel').html('').trigger("change");
    $('#addCarPlate').html('').trigger("change");
    $("input[type=reset]").trigger("click");
}

/** 清除添加的form表单信息 */
function clearAddForm1() {
    $("input[type=reset]").trigger("click");
}

function showEdit(){
    var roles = "公司超级管理员,公司普通管理员,汽车公司接待员";
    $.post("/user/isLogin/"+roles, function (data) {
        if(data.result == 'success'){
            initDateTimePicker('editForm', 'arriveTime', 'editDatetimepicker'); // 初始化时间框
            var row =  $('#table').bootstrapTable('getSelections');
            if(row.length >0) {
                $("#editWindow").modal('show'); // 显示弹窗
                $("#editButton").removeAttr("disabled");
                var checkin = row[0];
                $('#editCarBrand').html('<option value="' + checkin.brand.brandId + '">' + checkin.brand.brandName + '</option>').trigger("change");
                $('#editCarColor').html('<option value="' + checkin.color.colorId + '">' + checkin.color.colorName + '</option>').trigger("change");
                $('#editCarModel').html('<option value="' + checkin.model.modelId + '">' + checkin.model.modelName + '</option>').trigger("change");
                $('#editCarPlate').html('<option value="' + checkin.plate.plateId + '">' + checkin.plate.plateName + '</option>').trigger("change");
                $('#editDatetimepicker').val(formatterDate(checkin.arriveTime));
                $('#checkinCreatedTime').val(formatterDate(checkin.checkinCreatedTime));
                $("#editForm").fill(checkin);
                validator('editForm');
            }else{
                swal({
                    title:"",
                    text: "请选择要修改的登记记录", // 主要文本
                    confirmButtonColor: "#DD6B55", // 提示按钮的颜色
                    confirmButtonText:"确定", // 提示按钮上的文本
                    type:"warning"}) // 提示类型
            }
        }else if(data.result == 'notLogin'){
            swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"}
                ,function(isConfirm){
                    if(isConfirm){
                        top.location = "/user/loginPage";
                    }else{
                        top.location = "/user/loginPage";
                    }
                })
        }else if(data.result == 'notRole'){
            swal({title:"",
                text:data.message,
                confirmButtonText:"确认",
                type:"error"})
        }
    });
}

function showAdd(){
    var roles = "公司超级管理员,公司普通管理员,汽车公司接待员";
    $.post("/user/isLogin/"+roles, function (data) {
        if(data.result == 'success'){
            initDateTimePicker('addForm', 'arriveTime', 'addDatetimepicker'); // 初始化时间框, 第一参数是form表单id, 第二参数是input的name, 第三个参数为input的id
            $("#addWindow").modal('show');
            $("#addButton").removeAttr("disabled");
            validator('addForm'); // 初始化验证
        }else if(data.result == 'notLogin'){
            swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"}
                ,function(isConfirm){
                    if(isConfirm){
                        top.location = "/user/loginPage";
                    }else{
                        top.location = "/user/loginPage";
                    }
                })
        }else if(data.result == 'notRole'){
            swal({title:"",
                text:data.message,
                confirmButtonText:"确认",
                type:"error"})
        }
    });
}

function validator(formId) {
    $('#' + formId).bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userName: {
                message: '车主姓名验证失败',
                validators: {
                    notEmpty: {
                        message: '车主姓名不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 6,
                        message: '车主姓名长度必须在1到6位之间'
                    }
                }
            },
            userPhone: {
                message: '车主手机验证失败',
                validators: {
                    notEmpty: {
                        message: '车主电话不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '车主电话必须为11位'
                    },
                    regexp: {
                        regexp: /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/,
                        message: '请输入正确的手机号'
                    }
                }
            },
            brandId: {
                message: '汽车品牌验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车品牌不能为空'
                    }
                }
            },
            colorId: {
                message: '汽车颜色验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车颜色不能为空'
                    }
                }
            },
            modelId: {
                message: '汽车车型验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车车型不能为空'
                    }
                }
            },
            plateId: {
                message: '汽车车牌验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车车牌不能为空'
                    }
                }
            },
            carMileage: {
                message: '汽车当前行驶里程验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车当前行驶里程不能为空'
                    }
                }
            },
            carPlate: {
                message: '汽车车牌号验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车车牌号不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 6,
                        message: '车牌号码必须为6位'
                    },
                }
            },
            arriveTime: {
                message: '汽车到店时间验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车到店时间不能为空'
                    }
                }
            },
            nowOil: {
                message: '汽车当前油量验证失败',
                validators: {
                    notEmpty: {
                        message: '汽车当前油量不能为空'
                    }
                }
            }
        }
    })

        .on('success.form.bv', function (e) {
            if (formId == "addForm") {
                formSubmit("/checkin/add", formId, "addWindow");

            } else if (formId == "editForm") {
                formSubmit("/checkin/edit", formId, "editWindow");

            }
        })

}

function addSubmit(){
    $("#addForm").data('bootstrapValidator').validate();
    if ($("#addForm").data('bootstrapValidator').isValid()) {
        $("#addButton").attr("disabled","disabled");
    } else {
        $("#addButton").removeAttr("disabled");
    }
}

function editSubmit(){
    $("#editForm").data('bootstrapValidator').validate();
    if ($("#editForm").data('bootstrapValidator').isValid()) {
        $("#editButton").attr("disabled","disabled");
    } else {
        $("#editButton").removeAttr("disabled");
    }
}

function formSubmit(url, formId, winId){
    $.post(url,
        $("#" + formId).serialize(),
        function (data) {
            if (data.result == "success") {
                $('#' + winId).modal('hide');
                swal({
                    title:"",
                    text: data.message,
                    confirmButtonText:"确定", // 提示按钮上的文本
                    type:"success"})// 提示窗口, 修改成功
                $('#table').bootstrapTable('refresh');
                if(formId == 'addForm'){
                    $("input[type=reset]").trigger("click"); // 移除表单中填的值
                    $("#addButton").removeAttr("disabled"); // 移除不可点击
                    // 设置select2的值为空
                    $("#addCarBrand").html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
                    $("#addCarModel").html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
                    $("#addCarColor").html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
                    $("#addCarPlate").html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
                }
                $("#" + formId).data('bootstrapValidator').destroy(); // 销毁此form表单
                $('#' + formId).data('bootstrapValidator', null);// 此form表单设置为空
            } else if (data.result == "fail" ) {
                swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"})
                if(formId == 'addForm') {
                    $("#addButton").removeAttr("disabled");
                }else if(formId == 'editForm'){
                    $("#editButton").removeAttr("disabled");
                }
            }else if (data.result == "notLogin") {
                swal({title:"",
                    text:data.message,
                    confirmButtonText:"确认",
                    type:"error"}
                    ,function(isConfirm){
                        if(isConfirm){
                            top.location = "/user/loginPage";
                        }else{
                            top.location = "/user/loginPage";
                        }
                    })
                if(formId == 'addForm') {
                    $("#addButton").removeAttr("disabled");
                }else if(formId == 'editForm'){
                    $("#editButton").removeAttr("disabled");
                }
            }
        }, "json");
}