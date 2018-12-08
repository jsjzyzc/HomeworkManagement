/**
 * Created by 张晨 on 2018/11/28.
 */
window.onload = function () {
    $("#mask").fadeOut();
}
$("#log-in").click(function(){
    result=$.ajax({
        url:"/login",
        type:"POST",
        data:{
            account:$("#account").val(),
            password:$("#password").val()
        },
        async:false
    });
    if(result.responseText=="successful"){
        $("html").fadeOut();
        setTimeout("window.location.reload();",500);
    }
    else alert(result.responseText);
});

$("#sign-in").click(function(){
    location.href = "/toSign"
});

$("#Unsubscribe").click(function(){
    location.href = "/toUnsubscribe"
});