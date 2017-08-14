<%--
  Created by IntelliJ IDEA.
  User: fuzhongyu
  Date: 2017/6/9
  Time: 上午12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<style type="text/css">
    .more{
        position: absolute;
        right: 1.5rem;
        top: 1rem;
        color: #999;
        font-size: .7rem;
    }
    .arrow-left{
        content: "";
        position: absolute;
        display: block;
        top: .7rem;
        left: 1rem;
        width: 12px;
        height: 12px;
        border: 1px solid #fff;
        border-width: 1px 0 0 1px;
        margin-left: 3px;
        margin-top: 1px;
        transform: rotate(315deg);
        -webkit-transform: rotate(315deg);
    }
    .love{
        position: absolute;
        right: .8rem;
        top:.4rem;
        width: 1.5rem;
        height: 1.5rem;
        background-size:100%;
    }
    .loveactive{
        position: absolute;
        right: .8rem;
        top:.4rem;
        width: 1.5rem;
        height: 1.5rem;
        background-size:100%;
        animation: love .5s ease-in-out;
    }

    .header-box{
        position: fixed;
        width: 100%;
        z-index: 1000;
    }
    .header-box header{
        position: relative;
        box-sizing: border-box;
        background-color: #61c0e2;
    }
    .header-box header h1 {
        margin: 0 88px;
        margin-left: 100px;
        line-height: 40px;
        text-align: center;
        height: 46px;
        font-size: 18px;
        font-weight: 400;
        width: auto;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #fff;
    }
    .list{
        position: relative;
        top: 46px;
        width: 100%;
        -webkit-overflow-scrolling : touch;
        overflow-y:auto;
    }
    .cell{
        position: relative;
        display: table;
        width: 100%;
        height: 1.5rem;
        font-size: .8rem;
        background-color: #fff;
        margin-bottom: 1px;
        padding-top: .5rem;
        padding-bottom: .5rem;
        text-decoration: none;
    }
    .cell img{
        width: 100%;
        bottom:0;
    }
    .cell .closeBtn{
        position: absolute;
        top: 5rem;
        right: 3rem;
        width: 7%;
        height: 6%;
    }
    .cell-left{
        display: table-cell;
        width: 5rem;
        line-height: 1.5rem;
        height: 1.5rem;
        padding-left: .6rem;
        color: #333;
    }
    .cell-right{
        display: table-cell;
        color: #61c0e2;
        line-height: 1rem;
        padding-right: 1rem;
    }
    .arrow-right{
        display: inline-block;
        vertical-align: middle;
        position: absolute;
        top: .5rem;
        right:.6rem;
        width: 10px;
        height: 10px;
        border: 1px solid #ccc;
        border-width: 0 1px 1px 0;
        transform: rotate(-45deg) translate(-15px, 4px);
        -webkit-transform: rotate(-45deg) translate(-15px, 4px);
    }
</style>

<body>

    <div class="majorDetail">
        <div class="header-box">
            <header>
                <i class="arrow-left"></i>
                <h1>专业</h1>
                <div v-if="ok" class="loveactive"></div>
                <div v-else class="love"></div>
            </header>
        </div>
        <div  class="list">
            <div class="cell"><label class="cell-left">专业名称</label><label class="cell-right">wefwre</label></div>
            <div class="cell"><label class="cell-left">专业代码</label><label class="cell-right">we</label></div>
            <div class="cell"><label class="cell-left">所属门类</label><label class="cell-right">we</label></div>
            <div class="cell"><label class="cell-left">培养目标</label><label class="cell-right">we</label></div>
            <div class="cell"><label class="cell-left">主要课程</label><label class="cell-right">we</label></div>
            <div class="cell"><a class="cell"><label class="cell-left">开设院校</label><label class="cell-right">we所</label><label class="more">查看详情</label><i class="arrow-right"></i></a></div>
            <div class="cell"><a class="cell" :href="adurl" v-show="hasad"><img src="./statics/img/lei.png"></a><img class="closeBtn" src="./statics/img/close_white.png" onclick="this.parentNode.style.display='none'">关闭</div>
        </div>
    </div>
</body>
</html>
