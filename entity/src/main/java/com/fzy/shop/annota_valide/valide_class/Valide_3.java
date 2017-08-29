package com.fzy.shop.annota_valide.valide_class;

import com.fzy.shop.annota_valide.ValParam_3;
import com.fzy.shop.annota_valide.ValideParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义约束验证器实现
 * Created by fuzhongyu on 2017/8/24.
 */
public class Valide_3 implements ConstraintValidator<ValParam_3,String> {


    /**
     *  方法 initialize 对验证器进行实例化，它必须在验证器的实例在使用之前被调用，并保证正确初始化验证器，它的参数是约束注解
     */
    @Override
    public void initialize(ValParam_3 valParam_3) {

    }

    /**
     * 方法 isValid 是进行约束验证的主体方法，其中 value 参数代表需要验证的实例，context 参数代表约束执行的上下文环境。与该注解对应的验证器的实现。
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s!=null&&s.length()>3){
            return true;
        }
        return false;
    }
}
