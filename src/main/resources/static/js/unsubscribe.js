/**
 * Created by 张晨 on 2018/11/28.
 */
window.onload = function () {
    $("#mask").fadeOut();
}

$("#Unsubscribe").click(function(){
    if(!$("#account").val()){
        alert('注意填写账号')
        return
    }
    if(!$("#password").val()){
        alert('注意填写密码')
        return
    }
    result=$.ajax({
        url:"/unsubscribe",
        type:"POST",
        data:{
            account:$("#account").val(),
            password:$("#password").val(),
        },
        async:false
    });
    if(result.responseText=="successful"){
        alert('消户成功');
        location.href ="/"
    }
    else alert(result.responseText);
});