<html>
<head>
    <title>广告上传</title>
    <script src="../static/js/jquery-3.4.1.min.js"></script>
  <#--  <script src="../static/js/form.js"></script>-->
</head>
<body>
<h1>广告上传</h1>
<form id="form"  enctype="multipart/form-data" style="background-color: aqua" >
    一级标题:<input type="text" name="firstClassTitle" required minlength="1" maxlength="40" class="one"><br>
    二级标题:<input type="text" name="secondClassTitle" required minlength="1" maxlength="40"><br>
    图标文件:<input type="file" name="file" required><br>
    广告链接:<input type="text" name="adLink"  required maxlength="255" ><br>
    <input type="submit" value="提交" onclick="submitForm();return false">
    <#--<input type="submit" value="提交" onclick="submitForm()">-->
    <input type="reset" value="取消">
</form>

<script>
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
</script>
</body>
</html>