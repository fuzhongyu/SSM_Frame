<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>jQuery File Upload Example</title>
    <style>
        .bar {
            height: 18px;
            background: green;
        }
    </style>
</head>
<body>
<input id="fileupload" type="file" name="files[]" data-url="/shop/seckill/uploadFile" multiple>
<script src="statics/jquery/jquery-1.9.0.min.js"></script>
<script src="statics/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script src="statics/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script src="statics/jquery-file-upload/js/jquery.fileupload.js"></script>
<script>
    $(function () {
        $('#fileupload').fileupload({
            dataType: 'json',

            // 上传完成后的执行逻辑
            done: function (e, data) {
                $.each(data.result.files, function (index, file) {
                    $('<p/>').text(file.name).appendTo(document.body);
                });
            },

            // 上传过程中的回调函数
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $(".bar").text(progress + '%');
                $('#progress .bar').css(
                    'width',
                    progress + '%'
                );
            }
        });
    });
</script>

<!-- 进度条 -->
<div id="progress">
    <div class="bar" style="width: 0%;"></div>
</div>
</body>
</html>