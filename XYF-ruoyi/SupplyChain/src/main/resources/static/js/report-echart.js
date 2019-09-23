
$(function() {
    //初始化加载用户注册量图表
    getuserRegistData();
});

var prefix = ctx + "proxyService";
//用户注册量option
var userRegistoptions = {
    url: prefix + "/queryUserRegistPage",
    modalName: "用户注册量列表",
    // queryParams :queryParams,
    showExport: true,
    columns: [{
        checkbox: true
    },
        {
            field : 'createTime',
            title : '日期',
            // visible: false
        },
        {
            field : 'registNum',
            title : '用户注册量',
            sortable: true
        },
    ]
};
//订单购买option
var orderBuyoptions = {
    url: prefix + "/queryOrderBuyPage",
    modalName: "订单购买列表",
    showExport: true,
    columns: [{
        checkbox: true
    },
        {
            field : 'createdDate',
            title : '日期',
            // visible: false
        },
        {
            field : 'orderBuyNum',
            title : '订单购买量',
            sortable: true
        },
    ]
};
//出入账option
var ebankFlowoptions = {
    url: prefix + "/queryTransferPage",
    modalName: "出入账金额列表",
    showExport: true,
    columns: [{
        checkbox: true
    },
        {
            field : 'createTime',
            title : '日期',
            // visible: false
        }, {
            field : 'flowType',
            title : '类型',
            // visible: false
        },
        {
            field : 'amount',
            title : '总金额',
            sortable: true
        },
    ]
};
//列表参数获取
// function  queryParams(params) {
//     debugger
//     var createTime=$("#createTime").val()
//     var dates=createTime.split(" ~ ");
//     var startDate=dates[0]
//     var endDate=dates[1]
//     var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//         startDate
//     };
//     return temp;
// }
//查询按钮
// function getTableData() {
//     changeTableType()
// }
//改变表类型改变数据填充
function changeTableType() {
    var id=$("select[name='tableType']").val()
    $("#bootstrap-table").show()
    if(id==0){
        getuserRegistData()
        $("#user-regist-line").show()
        $("#order-buy-line").hide()
        $("#transfer-line").hide()
        $("#fansrefer-bar").hide()
        $.table.destroy()
        $.table.init(userRegistoptions);
    }else if(id==1){
        getOrderBuyData()
        $("#user-regist-line").hide()
        $("#order-buy-line").show()
        $("#transfer-line").hide()
        $("#fansrefer-bar").hide()
        $.table.destroy()
        $.table.init(orderBuyoptions);
    }else if(id==2){
        getFansReferData()
        $("#user-regist-line").hide()
        $("#order-buy-line").hide()
        $("#transfer-line").hide()
        $("#fansrefer-bar").show()
        $.table.destroy()
        $("#bootstrap-table").hide()
    }else if(id==3){
        getTransferData()
        $("#user-regist-line").hide()
        $("#order-buy-line").hide()
        $("#transfer-line").show()
        $("#fansrefer-bar").hide()
        $.table.destroy()
        $.table.init(ebankFlowoptions);
    }
}

//用户注册量
function getuserRegistData() {
    var userline = echarts.init(document.getElementById('user-regist-line'));
    //指定图表的配置项和数据
    var lineres;
    var pieres;
    $.ajax({
        cache: true,
        type: "POST",
        url: ctx + "proxyService/userRegistLine",
        async: true,
        error: function (request) {
            $.modal.alertError("系统错误");
        },
        success: function (data) {
            // $.operate.successCallback(data);
            lineres = data;
            var xAxis = lineres.xAxis;
            var legend = lineres.legend;
            var series = lineres.series;
            var option = {
                /* 鼠标悬浮时显示数据 */
                tooltip: {},
                legend: {
                    data: legend
                },
                xAxis: {
                    type: 'category',
                    name: '日期',
                    data: xAxis
                },
                yAxis: {
                    type: 'value',
                    name: '用户注册量'
                },
                series: [{
                    data: series[0].data,
                    type: 'line',
                    name: series[0].name,
                }]
            };
            //使用刚指定的配置项和数据显示图表
            userline.setOption(option);
            // userpie.setOption(pieoption)
        }
    });
}


//订单购买量
function getOrderBuyData(){
    var orderbuyline = echarts.init(document.getElementById('order-buy-line'));
    //指定图表的配置项和数据
    var lineres;
    $.ajax({
        cache : true,
        type : "POST",
        url : ctx + "proxyService/orderBuyLine",
        async : true,
        error : function(request) {
            $.modal.alertError("系统错误");
        },
        success : function(data) {
            // $.operate.successCallback(data);
            lineres=data;

            var xAxis=lineres.xAxis;
            var legend=lineres.legend;
            var series=lineres.series;
            var option = {
                /* 鼠标悬浮时显示数据 */
                tooltip: {},
                legend:{
                    data:legend
                },
                xAxis: {
                    type: 'category',
                    name:'日期',
                    data: xAxis
                },
                yAxis: {
                    type: 'value',
                    name:'订单购买量'
                },
                series: [{
                    data: series[0].data,
                    type: 'line',
                    name:series[0].name,
                }]
            };
            //使用刚指定的配置项和数据显示图表
            orderbuyline.setOption(option);
        }
    });
}

//出入帐金额
function getTransferData(){
    var transferline = echarts.init(document.getElementById('transfer-line'));
    //指定图表的配置项和数据
    var lineres;
    $.ajax({
        cache : true,
        type : "POST",
        url : ctx + "proxyService/transferLine",
        async : true,
        error : function(request) {
            $.modal.alertError("系统错误");
        },
        success : function(data) {
            // $.operate.successCallback(data);
            lineres=data;

            var xAxis=lineres.xAxis;
            var legend=lineres.legend;
            var series=lineres.series;
            var option = {
                /* 鼠标悬浮时显示数据 */
                tooltip: {},
                legend:{
                    data:legend
                },
                xAxis: {
                    type: 'category',
                    name:'日期',
                    data: xAxis
                },
                yAxis: {
                    type: 'value',
                    name:'入账 出账金额'
                },
                series: [{
                    data: series[0].data,
                    type: 'line',
                    name:series[0].name,
                },{
                    data: series[1].data,
                    type: 'line',
                    name:series[1].name,
                }
                ]
            };
            //使用刚指定的配置项和数据显示图表
            transferline.setOption(option);
        }
    });
}

//粉丝推荐
function getFansReferData() {
    var fansreferbar = echarts.init(document.getElementById('fansrefer-bar'));
    //指定图表的配置项和数据
    var lineres;
    $.ajax({
        cache : true,
        type : "POST",
        url : ctx + "proxyService/fansReferBar",
        async : true,
        error : function(request) {
            $.modal.alertError("系统错误");
        },
        success : function(data) {
            // $.operate.successCallback(data);
            lineres=data;

            var xAxis=lineres.xAxis;
            var legend=lineres.legend;
            var series=lineres.series;
            var option = {
                /* 鼠标悬浮时显示数据 */
                tooltip: {},
                legend:{
                    data:legend
                },
                xAxis: {
                    type: 'category',
                    name:'用户昵称',
                    data: xAxis
                },
                yAxis: {
                    type: 'value',
                    name:'粉丝推荐量'
                },
                series: [{
                    data: series[0].data,
                    type: 'bar',
                    name:series[0].name,
                }
                ]
            };
            //使用刚指定的配置项和数据显示图表
            fansreferbar.setOption(option);
        }
    });
}