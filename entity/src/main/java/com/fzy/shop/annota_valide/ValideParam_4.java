package com.fzy.shop.annota_valide;

import com.fzy.shop.annota_valide.valide_class.Valide_4;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 定义验证注解(多值约束)
 * Created by fuzhongyu on 2017/8/24.
 */
//@Target约束注解应用的目标元素:  METHOD-约束相关的 getter 方法、FIELD-约束相关的属性、TYPE-约束具体的 Java Bean、
//              LOCAL_VARIABLE-用于描述局部变量、ANNOTATION_TYPE-用在组合约束中、PARAMETER-参数约束、CONSTRUCTOR-构造器约束、PACKAGE-用于描述包
@Target({ElementType.PARAMETER,ElementType.TYPE,ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.PACKAGE,ElementType.LOCAL_VARIABLE,ElementType.FIELD})
//@Retention()约束注解应用的时机：SOURCE-在源文件中有效（即源文件保留）、CLASS-在class文件中有效（即class保留）、RUNTIME:在运行时有效（即运行时保留）
@Retention(RetentionPolicy.RUNTIME)
//与约束注解关联的验证器(自定义验证方式)
@Constraint(validatedBy = {Valide_4.class})
//@Documented  注解表明这个注解应该被 javadoc工具记录. 默认情况下,javadoc是不包括注解的. 但如果声明注解时指定了@Documented,
//              则它会被 javadoc 之类的工具处理, 所以注解类型信息也会被包括在生成的文档中
@Documented
//@Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的,如果一个使用了@Inherited修饰的
//           annotation类型被用于一个class，则这个annotation将被用于该class的子类。
//@Inherited

public @interface ValideParam_4 {

    // 约束注解验证时的输出消息
    String message() default "this string maybe empty -4";

    // 约束注解在验证时所属的组别
    Class<?> []groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

    String forName();


    @Target({ElementType.PARAMETER,ElementType.TYPE,ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.PACKAGE,ElementType.LOCAL_VARIABLE,ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List{
        ValideParam_4[] value();
    }
}
