<%--
  Created by IntelliJ IDEA.
  User: fuzhongyu
  Date: 2017/4/19
  Time: 下午3:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情</title>
    <%@include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="pennal-heading text-center">
                <div class="panel-heading">
                    <h1>${seckill.name}</h1>
                </div>
                <div class="panel-body">
                    <h2 class="text-danger">
                        <span class="glyphicon glyphicon-time"></span>
                        <span class="glyphicon" id="seckill-box"></span>
                    </h2>
                </div>
            </div>
        </div>
    </div>

    <div id="userPhoneModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"></span>
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="userPhone" id="userPhoneKey" placeholder="填手机号" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="userPhoneMessage" class="glyphicon"></span>
                    <button type="button" id="userPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        submit
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>

<script src="http://cdn.bootcss.com/jquery.countdown/2.1.1/jquery.countdown.min.js"></script>

<script type="text/javascript">

    var seckill={
        URL:{
            now:function () {
                return '/shop/seckill/time/now';
            }
        },
        validatePhone:function (phone) {
          if(phone && phone.length==11 && !isNaN(phone)){
              return true;
          }  else {
              return false;
          }
        },
        handleSeckill: function (seckillId,node) {
            node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        },
        countdown:function (seckillId,nowTime,startTime,endTime) {
            var seckillBox=$("#seckill-box");
            if(nowTime>endTime){
                seckillBox.html("秒杀结束");
            }else if(nowTime<startTime){

                seckillBox.countdown(new Date(startTime),function (event) {
                    var format=event.strftime('秒杀倒计时 %D天 %H时 %M分 %S秒');
                    seckillBox.html(format);
                }).on('finish.countdown',function () {
                    seckill.handleSeckill(seckillId,seckillBox);
                });
            }else {
                seckill.handleSeckill();
            }
        },
        detail:{
            init:function(params){
                var userPhone=$.cookie('userPhone');
                var startTime=params['startTime'];
                var endTime=params['endTime'];
                var seckillId=params['seckillId'];

                if(!seckill.validatePhone(userPhone)){
                    var userPhoneModal=$('#userPhoneModal');
                    userPhoneModal.modal({
                        show:true,
                        backdrop:'static',
                        keyboard:false
                    });

                    $("#userPhoneBtn").click(function () {
                        var inputPhone=$('#userPhoneKey').val();
                        if(seckill.validatePhone(inputPhone)){
                            $.cookie('userPhone',inputPhone,{expires:7,path:'/shop/seckill'});
                            window.location.reload();
                        }else {
                            $('#userPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                        }
                    });
                }

                $.get(seckill.URL.now(),{},function (result) {
                    if(result && result['code']==1){
                        var nowTime=result['result'];
                        seckill.countdown(seckillId,nowTime,startTime,endTime);
                    }else {

                    }
                });
            }
        }

    }


    $(function () {
        seckill.detail.init({
            seckillId:${seckill.id},
            startTime:${seckill.startTime.time},
            endTime:${seckill.endTime.time}
        });
    });

</script>

</html>
