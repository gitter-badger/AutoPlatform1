var contentPath='';

$(function () {
    initTable('table', '/maintain/queryByPagerMaintain'); // 初始化表格

    // 初始化select2, 第一个参数是class的名字, 第二个参数是select2的提示语, 第三个参数是select2的查询url
    initSelect2("Company", "请选择公司", "/company/queryAllCompany");
    initSelect2("AccessoriesType", "请选择配件类型", "/accType/queryAllAccType");
});


function showEdit() {
    var row = $('#table').bootstrapTable('getSelections');
    if (row.length > 0) {
        $("#editWindow").modal('show'); // 显示弹窗
        $("#editButton").removeAttr("disabled");
        var MaintainFixMap = row[0];
        $('#editcompany').html('<option value="' + MaintainFixMap.company.companyId + '">' + MaintainFixMap.company.companyName + '</option>').trigger("change");
        $("#editForm").fill(MaintainFixMap);
        validator('editForm');
    } else {
        swal({
            "title": "",
            "text": "请先选择一条数据",
            "type": "warning"
        })
    }
}


function showAdd() {
    $("#addWindow").modal('show');
    $("#addButton").removeAttr("disabled");
    validator('addForm'); // 初始化验证
}

function validator(formId) {
    $('#' + formId).bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            maintainName: {
                message: '保养项目名称验证失败',
                validators: {
                    notEmpty: {
                        message: '保养项目名称不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 10,
                        message: '保养项目名称长度必须在1到10位之间'
                    }
                }
            },
            maintainHour: {
                message: '保养项目工时验证失败',
                validators: {
                    notEmpty: {
                        message: '保养项目工时不能为空'
                    }
                }
            },
            maintainManHourFee: {
                message: '保养项目工时费验证失 败',
                validators: {
                    notEmpty: {
                        message: '保养项目工时费不能为空'
                    }
                }
            },
            maintainMoney: {
                message: '保养项目基础费用验证失败',
                validators: {
                    notEmpty: {
                        message: '保养项目基础费用不能为空'
                    }
                }
            },
            maintainDes :{
                message: '保养项目描述验证失败',
                validators: {
                    notEmpty: {
                        message: '保养项目描述不能为空'
                    }
                }
            },
            companyId: {
                message: '保养项目所属公司证失败',
                validators: {
                    notEmpty: {
                        message: '保养项目所属公司不能为空'
                    }
                }
            },
            accCount: {
                message: '配件个数验证失败',
                validators: {
                    notEmpty: {
                        message: '配件个数不能为空'
                    }
                }
            }
        }
    })
        .on('success.form.bv', function (e) {
            if (formId == "addForm") {
                formSubmit("/maintain/addMaintain", formId, "addWindow");

            } else if (formId == "editForm") {
                formSubmit("/maintain/update", formId, "editWindow");

            }else if (formId == "accForm") {
                formSubmit1("/maintain/accadd", formId, "accWindow");
            }
        })

}

function addSubmit() {
    $("#addForm").data('bootstrapValidator').validate();
    if ($("#addForm").data('bootstrapValidator').isValid()) {
        $("#addButton").attr("disabled", "disabled");
    } else {
        $("#addButton").removeAttr("disabled");
    }
}

function editSubmit() {
    $("#editForm").data('bootstrapValidator').validate();
    if ($("#editForm").data('bootstrapValidator').isValid()) {
        $("#editButton").attr("disabled", "disabled");
    } else {
        $("#editButton").removeAttr("disabled");
    }
}

function formSubmit1(url, formId, winId){
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
                if(formId == 'accForm'){
                    $("input[type=reset]").trigger("click"); // 移除表单中填的值
                    $('#accForm').data('bootstrapValidator').resetForm(true); // 移除所有验证样式
                    $("#accButton").removeAttr("disabled"); // 移除不可点击
                    $("#" + formId).data('bootstrapValidator').destroy(); // 销毁此form表单
                    $('#' + formId).data('bootstrapValidator', null);// 此form表单设置为空
                    // $("#addCompany").html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
                }
            } else if (data.result == "fail") {
                swal({title:"",
                    text:"添加失败",
                    confirmButtonText:"确认",
                    type:"error"})
                $("#"+formId).removeAttr("disabled");
            }
        }, "json");
}

function formSubmit(url, formId, winId) {
    $.post(url,
        $("#" + formId).serialize(),
        function (data) {
            if (data.result == "success") {
                $('#' + winId).modal('hide');
                swal({
                    title: "",
                    text: data.message,
                    confirmButtonText: "确定", // 提示按钮上的文本
                    type: "success"
                })// 提示窗口, 修改成功
                $('#table').bootstrapTable('refresh');
                if (formId == 'addForm') {
                    $("input[type=reset]").trigger("click"); // 移除表单中填的值
                    $('#addForm').data('bootstrapValidator').resetForm(true); // 移除所有验证样式
                    $("#addButton").removeAttr("disabled"); // 移除不可点击
                    $("#" + formId).data('bootstrapValidator').destroy(); // 销毁此form表单
                    $('#' + formId).data('bootstrapValidator', null);// 此form表单设置为空
                    $("#addCompany").html('<option value="' + '' + '">' + '' + '</option>').trigger("change");
                }
            } else if (data.result == "fail") {
                swal({
                    title: "",
                    text: "添加失败",
                    confirmButtonText: "确认",
                    type: "error"
                })
                $("#" + formId).removeAttr("disabled");
            }
        }, "json");
}

// 激活或禁用
function statusFormatter(value, row, index) {
    if(value == 'Y') {
        return "&nbsp;&nbsp;<button type='button' class='btn btn-danger' onclick='inactive(\""+'/maintain/statusOperate?id='+row.maintainId+'&status=Y'+"\")'>禁用</a>";
    } else {
        return "&nbsp;&nbsp;<button type='button' class='btn btn-success' onclick='active(\""+'/maintain/statusOperate?id='+ row.maintainId+'&status=N'+ "\")'>激活</a>";
    }
}

// 查看全部可用
function showAvailable(){
    initTable('table', '/maintain/queryByPagerMaintain');
}
// 查看全部禁用
function showDisable(){
    initTable('table', '/maintain/queryByPagerDisable');
}

function queryByTypeId(obj){
    initTableNotTollbar("table1", "/accInv/queryByIdAcc?id=" + obj.value);
}
// 添加配件窗口
function showAddacc(){
    var row =  $('#table').bootstrapTable('getSelections');
    if(row.length >0) {
        $("#accButton").removeAttr("disabled");
        var MaintainFixMap = row[0];
        $("#accForm").fill(MaintainFixMap);
        $("#accWindow").modal('show');
        validator('accForm'); // 初始化验证
    }else{
        swal({
            title:"",
            text: "请选择要添加配件的维修项目", // 主要文本
            confirmButtonColor: "#DD6B55", // 提示按钮的颜色
            confirmButtonText:"确定", // 提示按钮上的文本
            type:"warning"}) // 提示类型
    }
}
function showAcc(windowId){
    $("#"+ windowId).modal('hide');
    $("#accAllWindow").modal('show');
    $("#closeButton").addClass(windowId);
}

//所有配件窗口关闭按钮
function accAllcloseWindow(){
    $("#accAllWindow").modal('hide');
    $("#accWindow").modal('show');
}

//所有配件窗口关闭图标
function closeWindow(){
    $("#accAllWindow").modal('hide');
    $("#accWindow").modal('show');
}

function accaddSubmit(){
    $("#accForm").data('bootstrapValidator').validate();
    if ($("#accForm").data('bootstrapValidator').isValid()) {
        $("#accButton").attr("disabled","disabled");
    } else {
        $("#accButton").removeAttr("disabled");
    }
}

// 在所有项目中点击确定
function itemSubmit(){
    var row =  $('#table1').bootstrapTable('getSelections');
    if(row.length >0) {
        $("#accAllWindow").modal('hide');
        if($("#closeButton").hasClass('accWindow')){
            $("#addaccId").val(row[0].accId);
            $("#addacc").val(row[0].accName);
            $("#accWindow").modal('show');
            $("#closeButton").removeClass('accWindow');
        }else if($("#closeButton").hasClass('editWindow')){
            $("#editItemId").val(row[0].maintainId);
            $("#editItem").val(row[0].maintainName);
            $("#editWindow").modal('show');
            $("#closeButton").removeClass('editWindow');
        }
    }else{
        swal({
            title:"",
            text: "请先选择维修保养项目", // 主要文本
            confirmButtonColor: "#DD6B55", // 提示按钮的颜色
            confirmButtonText:"确定", // 提示按钮上的文本
            type:"warning"}) // 提示类型
    }
}

var maintainId = "";

// 显示所有明细
function showDetail(){
    var row =  $('#table').bootstrapTable('getSelections');
    if(row.length >0) {
        maintainId = row[0].maintainId;
        $("#detailWindow").modal('show');
        initDetailTable('detailTable', '/maintain/queryByDetailsByPager?maintainId='+row[0].maintainId);
    }else{
        swal({
            title:"",
            text: "请选择要查看明细的维修保养记录", // 主要文本
            confirmButtonColor: "#DD6B55", // 提示按钮的颜色
            confirmButtonText:"确定", // 提示按钮上的文本
            type:"warning"}) // 提示类型
    }
}
function initDetailTable(tableId, url) {
    //先销毁表格
    $('#' + tableId).bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $("#" + tableId).bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: url, //获取数据的Servlet地址
        striped: false,  //表格显示条纹
        pagination: true, //启动分页
        pageSize: 10,  //每页显示的记录数
        pageNumber:1, //当前第几页
        pageList: [10, 15, 20, 25, 30],  //记录数可选列表
        showColumns: true,  //显示下拉框勾选要显示的列
        showRefresh: true,  //显示刷新按钮
        showToggle: true, // 显示详情
        strictSearch: true,
        clickToSelect: true,  //是否启用点击选中行
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        toolbar : "#detailToolbar",// 指定工具栏
        sidePagination: "server", //表示服务端请求

        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType : "undefined",
        queryParams: function queryParams(params) {   //设置查询参数
            var param = {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                orderNum : $("#orderNum").val()
            };
            return param;
        },
    });
}