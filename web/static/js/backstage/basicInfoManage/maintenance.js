$(function () {
    $('#table').bootstrapTable('hideColumn', 'id');
    $("#addSelect").select2({
            language: 'zh-CN'
        }
    );

    //绑定Ajax的内容
    $.getJSON("/table/queryType", function (data) {
        $("#addSelect").empty();//清空下拉框
        $.each(data, function (i, item) {
            $("#addSelect").append("<option value='" + data[i].id + "'>&nbsp;" + data[i].name + "</option>");
        });
    })
//            $("#addSelect").on("select2:select",
//                    function (e) {
//                        alert(e)
//                        alert("select2:select", e);
//            });
});

//显示弹窗
function showEdit() {
    var row = $('table').bootstrapTable('getSelections');
    if (row.length > 0) {
//                $('#editId').val(row[0].id);
//                $('#editName').val(row[0].name);
//                $('#editPrice').val(row[0].price);
        $("#editWindow").modal('show'); // 显示弹窗
        var ceshi = row[0];
        $("#editForm").fill(ceshi);
    } else {
        swal({
            "title": "",
            "text": "请先选择一条数据",
            "type": "warning"
        })
    }
}

//显示添加
function showAdd() {
    $("#addWindow").modal('show');
}

function formatRepo(repo) {
    return repo.text
}
function formatRepoSelection(repo) {
    return repo.text
}

//显示删除
function showDel() {

    var row = $('table').bootstrapTable('getSelections');
    if (row.length > 0) {
        $("#del").modal('show');
        var ceshi = row[0];
        $("#tanchuang").fill(ceshi);
    } else {
        swal({
            "title": "",
            "text": "请先选择一条数据",
            "type": "warning"
        })
    }

}

//检查添加
function checkAdd() {
    var id = $('#addId').val();
    var name = $('#addName').val();
    var price = $('#addPrice').val();
    var reslist = $("#addSelect").select2("data"); //获取多选的值
    if (id != "" && name != "" && price != "") {
        return true;
    } else {
        var error = document.getElementById("addError");
        error.innerHTML = "请输入正确的数据";
        return false;
    }
}

//检查修改
function checkEdit() {
    $.post("/table/edit",
        $("#editForm").serialize(),
        function (data) {
            if (data.result == "success") {
                $("#editWindow").modal('hide'); // 关闭指定的窗口
                $('#table').bootstrapTable("refresh"); // 重新加载指定数据网格数据
                swal({
                    title: "",
                    text: data.message,
                    type: "success"
                })// 提示窗口, 修改成功
            } else if (data.result == "fail") {
                //$.messager.alert("提示", data.result.message, "info");
            }
        }, "json"
    );
}
//添加
$('#addDateTimePicker').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#addDateTimePicker2').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#addDateTimePicker3').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#addDateTimePicker4').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#addDateTimePicker5').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});

//修改
$('#editDateTimePicker').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#editDateTimePicker2').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#editDateTimePicker3').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#editDateTimePicker4').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});
$('#editDateTimePicker5').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd hh:ii'
});




// //日期时间控件初始化
// $(document).ready(function () {
//     // 带时间的控件
//     // if ($(".iDate.full").length > 0) {
//     //     $(".iDate.full").datetimepicker({
//     //         locale: "zh-cn",
//     //         format: "YYYY-MM-DD a hh:mm",
//     //         dayViewHeaderFormat: "YYYY年 MMMM"
//     //     });
//     // }
//
//     //不带时间的控件
//     if ($(".iDate.date").length > 0) {
//         $(".iDate.date").datetimepicker({
//             locale: "zh-cn",
//             format: "YYYY-MM-DD",
//             dayViewHeaderFormat: "YYYY年 MMMM"
//         });
//     }
// })
//前段验证
$(document).ready(function () {
    jQuery.validator.addMethod("isPhone", function (value, element) {
        var length = value.length;
        return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
    }, "请正确填写进度编号");

    $("#showAddFormWar").validate({
        errorElement: 'span',
        errorClass: 'help-block',

        rules: {
            checkinId: {
                required: true,
                minlength: 2
            },
            // startTime: {
            //     required: true,
            //     date: true
            // },
            // endTime: {
            //     required: true,
            //     date: true
            // },
            // actualEndTime: {
            //     required: true,
            //     date: true
            // },
            // recordCreatedTime: {
            //     required: true,
            //     date: true
            // },
            // pickupTime: {
            //     required: true,
            //     date: true
            // },
             recordDes: {
                 required: true,
                 minlength: 5
             }
        },
        messages: {
            checkinId: "请输入登记编号",
            // startTime: "请选择时间",
            // endTime: "请选择预估结束时间",
            // actualEndTime: "请选择实际结束时间",
            // recordCreatedTime: "创建时间",
            // pickupTime: "请选择车主提车时间",
            recordDes: "进度描述"
        },
        errorPlacement: function (error, element) {
            element.next().remove();
            element.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
            element.closest('.form-group').append(error);
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error has-feedback');
        },
        success: function (label) {
            var el = label.closest('.form-group').find("input");
            el.next().remove();
            el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
            label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
            label.remove();
        },
        submitHandler: function (form) {
            alert("submitted!");
        }
    })
    $("#showEditFormWar").validate({
        errorElement: 'span',
        errorClass: 'help-block',

        rules: {
            checkinId: {
                required: true,
                minlength: 2
            },
            // startTime: {
            //     required: true,
            //     date: true
            // },
            // endTime: {
            //     required: true,
            //     date: true
            // },
            // actualEndTime: {
            //     required: true,
            //     date: true
            // },
            // recordCreatedTime: {
            //     required: true,
            //     date: true,
            // },
            // pickupTime: {
            //     required: true,
            //     date: true,
            // },
            recordDes: {
                required: true,
                minlength: 5,
            }

        },
        messages: {
            checkinId: "请输入登记编号",
            startTime: "请选择时间",
            endTime: "请选择预估结束时间",
            actualEndTime: "请选择实际结束时间",
            recordCreatedTime: "创建时间",
            pickupTime: "请选择车主提车时间",
            recordDes: "进度描述"
        },
        errorPlacement: function (error, element) {
            element.next().remove();
            element.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
            element.closest('.form-group').append(error);
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error has-feedback');
        },
        success: function (label) {
            var el = label.closest('.form-group').find("input");
            el.next().remove();
            el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
            label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
            label.remove();
        },
        submitHandler: function (form) {
            alert("submitted!");
        }
    })
});
