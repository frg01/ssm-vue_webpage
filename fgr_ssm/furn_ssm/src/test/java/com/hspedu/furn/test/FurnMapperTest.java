package com.hspedu.furn.test;

import com.hspedu.furn.bean.Furn;
import com.hspedu.furn.dao.FurnMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class FurnMapperTest {

    @Test
    public void insertSelective(){
        //获取容器
        ApplicationContext ioc
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取到Mapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);
//        System.out.println("furnMapper" + furnMapper);

        //3.添加对象
        Furn furn = new Furn(null, "北欧风格沙发~~~", "顺平家居~~~", new BigDecimal(180), 666, 7, "assets/imags/product-image/5.jpg");
        int affected = furnMapper.insertSelective(furn);
        System.out.println("affected = " + affected + "操作成功");

    }

    @Test
    public void deleteByPrimaryKey(){
        //获取容器
        ApplicationContext ioc
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取到Mapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);

        int affected = furnMapper.deleteByPrimaryKey(6);
        System.out.println("affected= " + affected + "操作成功");
    }

    @Test
    public void updateByPrimaryKey(){
        //获取容器
        ApplicationContext ioc
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取到Mapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);
        Furn furn = new Furn();
        furn.setId(5);
        furn.setName("顺平风格家居");

        //updateByPrimaryKey会修改所有的字段 未修改默认为null
        //updateByPrimaryKeySelective设置属性对应字段 生成sql语句
        int affected = furnMapper.updateByPrimaryKeySelective(furn);
        System.out.println("affected= " + affected + "操作成功");
    }

    @Test
    public void selectByPrimaryKey(){
        //获取容器
        ApplicationContext ioc
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取到Mapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);

        Furn furn = furnMapper.selectByPrimaryKey(1);
        System.out.println("furn= " + furn + "操作成功");
    }
}
