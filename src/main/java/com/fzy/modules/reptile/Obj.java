package com.fzy.modules.reptile;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * @author fuzhongyu
 * @date 2017/12/23
 */

public class Obj implements HtmlBean{

    @Text
    @HtmlField(cssPath="table tr:gt(0)>td:eq(1)")
    private String td1;


    @Text
    @HtmlField(cssPath="table tr:gt(0)>td:eq(2)")
    private String td2;

    public String getTd1() {
        return td1;
    }

    public void setTd1(String td1) {
        this.td1 = td1;
    }

    public String getTd2() {
        return td2;
    }

    public void setTd2(String td2) {
        this.td2 = td2;
    }
}
