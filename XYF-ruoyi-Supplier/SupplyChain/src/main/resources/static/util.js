var localhostIndex;
//上传图片
function uploadFile(fileId,uploadType) {
    if(!fileId){
        fileId ="file";
    }

    var fileUrl;
    var files = document.getElementById(fileId);
    var filePath = $("#"+fileId).val(), // 获取到input的value，里面是文件的路径
        fileFormat = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    if (!/.(gif|jpg|jpeg|png|GIF|JPG|png)$/.test(fileFormat)) {
        $.modal.alertError("圖片必須是.gif,jpeg,jpg,png格式..");
        return false;
    }
    if(!uploadType){
        uploadType =1;
    }
    var formData = new FormData();
    formData.append("file", files.files[0]);
    formData.append("fileType", fileFormat);
    formData.append("updateType", uploadType);
    $.ajax({
        type : "POST",
        async:false,
        url : getRootPath() + "/../common/uploadToOSS",
        data : formData,
        type : "post",
        processData : false,
        contentType : false,
        success : function(result) {
            if (result.code == web_status.SUCCESS) {
                fileUrl = result.msg;
            } else {
                $.modal.alertError(result.msg);
            }
        },
        error : function(error) {
            $.modal.alertWarning("图片上传失败。");

        }
    });
    return fileUrl;
}
function uploadFileByFile(files,fileFormat,uploadType) {
    var formData = new FormData();
    if(!uploadType){
        uploadType =1;
    }
    formData.append("file", files);
    formData.append("fileType", fileFormat);
    formData.append("updateType", uploadType);

    $.ajax({
        type : "POST",
        async:false,
        url : getRootPath() + "/../common/uploadToOSS",
        data : formData,
        type : "post",
        processData : false,
        contentType : false,
        success : function(result) {
            if (result.code == web_status.SUCCESS) {
                fileUrl = result.msg;
            } else {
                $.modal.alertError(result.msg);
            }
        },
        error : function(error) {
            $.modal.alertWarning("图片上传失败。");

        }
    });
    return fileUrl;
}

/**
 * //获取当前项目根路径
 * @return {TypeName}
 */
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
//打开图片
function openImg(value){
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        area: '516px',
        skin: 'layui-layer-nobg', //没有背景色
        shadeClose: true,
        content: "<img  src='"+value+"' />"
    });

}
//打开页面
function openPage(title,url,width,hight){
    if(!width){
        width =1050;
    }
    if(!hight){
        hight =1050;
    }
    $.modal.open(title,url,width,hight);

}
function  checkOpen(title,url,width,hight) {
    var id =$.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
    var columns=$.table.selectFirstColumns();
    if (id.length == 0) {
        $.modal.alertWarning("请至少选择一条记录");
        return;
    }
    var userNo ;
    var userName;

     $.map($.btTable.bootstrapTable('getSelections'), function (row) {
         userNo = row.userNo;
         userName = row.userName;
         localStorage.setItem("userName", userName);
         localStorage.setItem("userNo", userNo);
         localStorage.setItem("accountNo", row.accountNo);

    });

    //缓存起来
    localStorage.setItem("tempId", id);
    openPage(title,url+"?tempId="+id,width,hight)
}

/*用户管理-新增-选择部门树*/
function selectChannelTree(id) {
    localhostIndex =id;
    console.log("====="+localhostIndex);
    var treeId = $("#treeId").val();
    var channelId = $.common.isEmpty(treeId) ? "1" : $("#treeId").val();
    var url = ctx + "system/proChannel/selectChannelTree/" + channelId;
    var options = {
        title: '选择分类',
        width: "380",
        url: url,
        callBack: doSubmit
    };
    $.modal.openOptions(options);
}

function doSubmit(index, layero) {
    var tree = layero.find("iframe")[0].contentWindow.$._tree;
    if ($.tree.allCheck(tree)) {
        var body = layer.getChildFrame('body', index);
        $("#treeId"+localhostIndex).val(body.find('#treeId').val());
        $("#treeName"+localhostIndex).val(body.find('#treeName').val());
        layer.close(index);
    }
}
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}