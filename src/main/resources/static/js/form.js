function submitForm(){
    var form = new FormData(document.getElementById("form"));
    $.ajax({
        url:"upload",
        type:"post",
        data:form,
        dataType:"json",
        processData:false,
        contentType:false,
        success:function(data){
            window.alert(data.message);
        }
    });
}