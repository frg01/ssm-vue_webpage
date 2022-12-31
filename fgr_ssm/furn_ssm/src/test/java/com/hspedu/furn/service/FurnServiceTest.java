package com.hspedu.furn.service;

import com.hspedu.furn.bean.Furn;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class FurnServiceTest {

    private ApplicationContext ioc;//属性
    private FurnService furnService;//spring容器中的接口

    @Before
    public void init(){
        ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        furnService = ioc.getBean(FurnService.class);
        System.out.println(".....");
        System.out.println("furnService= " + furnService.getClass());
    }

    @Test
    public void save(){
        Furn furn = new Furn(null, "风扇", "顺平家居~~~", new BigDecimal(50), 44, 7, "assets/imags/product-image/7.jpg");
        furnService.save(furn);
        System.out.println("添加成功");
    }

    @Test
    public void findAll(){
        List<Furn> furns = furnService.findAll();
        for (Furn furn : furns) {
            System.out.println("furn-"  + furn);
        }
    }

    @Test
    public void update(){
        Furn furn = new Furn();
        furn.setId(1);
        furn.setName("北欧风格小桌子");
        furn.setMaker("大象家具");
        furn.setImgPath(null);//ImgPath有默认值 所以将其置为null就不会在对在update语句中进行数据修改
        furnService.update(furn);

        System.out.println("修改成功");
    }

    @Test
    public void del(){
        furnService.del(11);

        System.out.println("删除成功");
    }

    @Test
    public void findByCondition(){

        List<Furn> furns = furnService.findByCondition("风格");
        for (Furn furn : furns) {
            System.out.println("furn-- " + furn);
        }
    }
}
