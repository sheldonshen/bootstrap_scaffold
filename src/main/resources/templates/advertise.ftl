<html>
<head>
    <title>广告上传</title>
    <script src="../static/js/jquery-3.4.1.min.js"></script>
    <script src="../static/js/form.js"></script>
    <link rel="stylesheet"  href="../static/css/index.css">
</head>
<body>
<h1>广告上传</h1>
<form id="form"  enctype="multipart/form-data"  class="one">
    一级标题:<input type="text" name="firstClassTitle" required minlength="1" maxlength="40"><br>
    二级标题:<input type="text" name="secondClassTitle" required minlength="1" maxlength="40"><br>
    图标文件:<input type="file" name="file" required><br>
    广告链接:<input type="text" name="adLink"  required maxlength="255" ><br>
    <input type="submit" value="提交" onclick="submitForm();return false"> <input type="reset" value="取消">
</form>
</body>
</html>