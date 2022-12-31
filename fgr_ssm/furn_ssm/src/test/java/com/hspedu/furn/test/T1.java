package com.hspedu.furn.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class T1 {

    @Test
    public void t1(){
        //spring 配置的bean是否可以获取到
        //1.获取到容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

        //获取Bean
        System.out.println(ioc.getBean("pooledDataSource"));
        System.out.println(ioc.getBean("sqlSessionFactory"));
    }
}
