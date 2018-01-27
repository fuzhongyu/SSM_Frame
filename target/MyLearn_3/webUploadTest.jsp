
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>上传</title>
    <%@include file="/WEB-INF/views/include/head.jsp"%>
</head>

<link rel="stylesheet" type="text/css" href="statics/webupload/webuploader.css">
<script type="text/javascript" src="statics/jquery/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="statics/webupload/webuploader.js"></script>

<script type="text/javascript">

    $(function () {

        var uploader = WebUploader.create({

            // swf文件路径
            swf:'statics/webupload/Uploader.swf',

            // 文件接收服务端。
            server: '/shop/seckill/uploadFile',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick:'#picker',

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false
        });

        // 当有文件被添加进队列的时候
        uploader.on( 'fileQueued', function( file ) {
            $list.append( '<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>' );
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo( $li ).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');

            $percent.css( 'width', percentage * 100 + '%' );
        });


        uploader.on( 'uploadSuccess', function( file ) {
            $( '#'+file.id ).find('p.state').text('已上传');
        });

        uploader.on( 'uploadError', function( file ) {
            $( '#'+file.id ).find('p.state').text('上传出错');
        });

        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').fadeOut();
        });


    });

</script>

<body>
        <div id="uploader" class="wu-example">
        <!--用来存放文件信息-->
            <div id="list" class="uploader-list"></div>
            <div class="btns">
                <div id="picker">选择文件</div>
                <button id="ctlBtn" class="btn btn-default">开始上传</button>
            </div>
        </div>

</body>


</html>