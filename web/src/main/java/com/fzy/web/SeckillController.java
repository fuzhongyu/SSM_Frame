package com.fzy.web;

import com.fzy.entity.ErrorsMsg;
import com.fzy.entity.ResponseEntity;
import com.fzy.exception.ServiceException;
import com.fzy.shop.Seckill;
import com.fzy.shop.SeckillService;
import com.fzy.shop.dto.Exposer;
import com.fzy.shop.dto.SeckillExcution;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by fuzhongyu on 2017/4/19.
 */
@Controller
@RequestMapping("/shop/seckill")
public class SeckillController extends BasicController{

    private Logger logger= LoggerFactory.getLogger(SeckillController.class);

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "list")
    public String list(Model model,HttpServletRequest request){
        List<Seckill>  list=seckillService.getSeckillList();
//        int a=1/0;
        model.addAttribute("list",list);
        return "/modules/shop/list";
    }

    @RequestMapping(value = "page")
    public String page(Model model){

        logger.info("-------------");
        //这边将页码和页数写死了，实际需要从前端传回这两个值
        PageInfo<Seckill> page=seckillService.getSeckillPage(1,4,new Seckill());
        model.addAttribute("page",page);
        return "/modules/shop/page";
    }



    @RequestMapping(value = "{seckillId}/detail")
    public String detail(@PathVariable("seckillId") String seckillId,Model model){
        if(StringUtils.isBlank(seckillId)){
            return "redirect:/shop/seckill/list";
        }
        Seckill seckill=seckillService.getById(seckillId);
        if(seckill==null){
            return "redirect:/shop/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "/modules/shop/detail";
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public ResponseEntity exposer(@PathVariable("seckillId") String seckillId){
        Seckill seckill= seckillService.getById(seckillId);
        if(seckill==null){
            throw new ServiceException(ErrorsMsg.ERR_9999);
        }

        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        return new ResponseEntity(ErrorsMsg.SUCC_1,exposer);
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public ResponseEntity execute(@PathVariable("seckillId") String seckillId,@PathVariable("md5")String md5,@CookieValue(value = "userPhone",required = false) String userPhone){

        if(StringUtils.isBlank(userPhone)){
           throw new ServiceException(ErrorsMsg.ERR_1004);
        }
        SeckillExcution seckillExcution=seckillService.executeSeckill(seckillId,userPhone,md5);
        return new ResponseEntity(ErrorsMsg.SUCC_1,seckillExcution);

    }

    @ResponseBody
    @RequestMapping(value = "/time/now")
    public ResponseEntity time(){
        return new ResponseEntity(ErrorsMsg.SUCC_1,new Date().getTime());
    }

}
