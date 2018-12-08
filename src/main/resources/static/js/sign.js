/**
 * Created by 张晨 on 2018/11/28.
 */
window.onload = function () {
    $("#mask").fadeOut();
}
$("#log-in").click(function(){
    location.href = '/login.html'
});

$("#sign-in").click(function(){
    if(!$("#account").val()){
        alert('注意填写账号')
        return
    }
    if(!$("#password").val()){
        alert('注意填写密码')
        return
    }
    if(!$("#name").val()){
        alert('注意填写name')
        return
    }

    if($("#type").val()=='student'){
        if(-1 == $("#class_inf").val()){
            alert('请选择班级')
            return
        }
    }
    result=$.ajax({
        url:"/sign",
        type:"POST",
        data:{
            account:$("#account").val(),
            password:$("#password").val(),
            type:$("#type").val(),
            name:$("#name").val(),
            class_id:$("#class_inf").val()
        },
        async:false
    });
    if(result.responseText=="successful"){
        alert('注册成功，可以登陆了');
        location.href ="/"
    }
    else alert(result.responseText);
});

var changeType = function (src) {
    if($(src).val() == 'student'){
        //将隐藏的class-info div显示出来
        $("#class-info").show()
    }else{
        $("#class-info").hide()
    }
}
$(function () {//页面加载完成
    if($("#type").val() == 'student'){
        //将隐藏的class-info div显示出来
        $("#class-info").show()
    }
})