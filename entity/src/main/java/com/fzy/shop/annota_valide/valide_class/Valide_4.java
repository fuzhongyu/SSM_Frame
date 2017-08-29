package com.fzy.shop.annota_valide.valide_class;

import com.fzy.shop.annota_valide.ValideParam_4;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义约束验证器实现
 * Created by fuzhongyu on 2017/8/24.
 */
public class Valide_4 implements ConstraintValidator<ValideParam_4,String> {


    private String name;

    /**
     *  方法 initialize 对验证器进行实例化，它必须在验证器的实例在使用之前被调用，并保证正确初始化验证器，它的参数是约束注解
     */
    @Override
    public void initialize(ValideParam_4 valideParam_4) {

        this.name=valideParam_4.forName();

    }

    /**
     * 方法 isValid 是进行约束验证的主体方法，其中 value 参数代表需要验证的实例，context 参数代表约束执行的上下文环境。与该注解对应的验证器的实现。
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            if(name.equals(s)){
                return false;
            }
        return true;
    }
}
