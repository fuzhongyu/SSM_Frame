package com.fzy.modules.shop.web;

import com.fzy.common.entity.ErrorsMsg;
import com.fzy.common.entity.Page;
import com.fzy.common.entity.ResponseEntity;
import com.fzy.common.exception.ServiceException;
import com.fzy.common.utils.StringUtils;
import com.fzy.common.web.BasicController;
import com.fzy.modules.shop.entity.Seckill;
import com.fzy.modules.shop.entity.dto.Exposer;
import com.fzy.modules.shop.entity.dto.SeckillExcution;
import com.fzy.modules.shop.service.SeckillService;
import com.fzy.modules.shop.service.imp.SeckillServiceImp;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fuzhongyu
 * @date 2017/4/19
 */
@Controller
@RequestMapping("/shop/seckill")
public class SeckillController extends BasicController{

    @Autowired
    private SeckillService seckillService;

//    @Autowired
//    private HttpServletResponse response;

    @RequestMapping(value = "list")
    public String list(Model model,HttpServletRequest request){
//        System.out.println("**"+response);

        List<Seckill>  list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "/modules/shop/list";
    }

    @RequestMapping(value = "page")
    public String page(Model model){
        //这边将页码和页数写死了，实际需要从前端传回这两个值
        Page<Seckill> page=seckillService.getSeckillPage(new Page<>(1,4),new Seckill());
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

    @RequestMapping(value = "/uploadFile")
    public void uploadFile(Model model,HttpServletRequest request,HttpServletResponse response,MultipartFile file){
        final String upload="/Users/fuzhongyu/Downloads/pic/";
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = sf.format(new Date());
        String PATH=upload+stringDate;
        File dir=new File(PATH);
        if(!dir.exists()) dir.mkdir();
        String[] tmp=file.getOriginalFilename().split("\\.");
        String suffix=tmp[tmp.length-1]!=null?tmp[tmp.length-1]:"";
        String filename=file.hashCode()+"."+suffix;
        File img=new File(PATH,filename);

        if(!img.exists()) {
            try {
                img.createNewFile();
            } catch (IOException e) {
             e.printStackTrace();
            }
        }

        try {
            file.transferTo(img);
        } catch (IllegalStateException | IOException e) {
           e.printStackTrace();
        }
        String re="{\"FileUrl\":\""+PATH+"/"+filename+"\"}";
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        try {
            response.getWriter().print(re);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }


}
