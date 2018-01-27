package com.fzy.modules.reptile;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * @author fuzhongyu
 * @date 2017/12/23
 */
//@Gecco(matchUrl="http://zt.zjzs.net/xuanke2018/allcollege.html", pipelines="dataPipeline")
public class  FirstReptile implements HtmlBean {

    private static final long serialVersionUID = -7127412585200687225L;

//    @Text
//    @HtmlField(cssPath="#dis_1 td")
    private String content;

//    @Text
//    @HtmlField(cssPath="#dis_1 table tr:gt(0) td:eq(2)")
    private List<String> contentList;

    public List<String> getContentList() {
        return contentList;
    }

//    @Text
    @HtmlField(cssPath="#dis_1 ")
    private List<Obj> objList;


    public List<Obj> getObjList() {
        return objList;
    }

    public void setObjList(List<Obj> objList) {
        this.objList = objList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {


        String classpath = "com.fzy.modules.reptile";
        String url = "http://zt.zjzs.net/xuanke2018/allcollege.html";
        HttpRequest request = new HttpGetRequest(url);
        request.setCharset("utf-8");
        // 如果pipeline和htmlbean不在同一个包，classpath就要设置到他们的共同父包
        // 本引擎主要是把京东分类的页面手机板块给抓取过来封装成htmlbean
        GeccoEngine.create().classpath(classpath).start(request).thread(1).interval(2000).start();

        // 本引擎是负责抓取每一个细目对应的页面的第一页的所有商品列表的数据，开启5个线程同时抓取，提升效率
//        GeccoEngine.create().classpath(classpath).start(AllSortPipeline.cateRequests).thread(5)
//                .interval(2000).run();



        }
    }

