package com.hspedu.furn.service.impl;

import com.hspedu.furn.bean.Furn;
import com.hspedu.furn.bean.FurnExample;
import com.hspedu.furn.dao.FurnMapper;
import com.hspedu.furn.service.FurnService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
@Service
public class FurnServiceImpl implements FurnService {

    //注入FurnMapper接口对象（代理对象）
    @Resource
    private FurnMapper furnMapper;

    @Override
    public void save(Furn furn) {
        //insertSelective  表单的id是自增的
        furnMapper.insertSelective(furn);
    }

    @Override
    public List<Furn> findAll() {
        //FurnMapper.xml 传入是null，返回所有家具信息
        return furnMapper.selectByExample(null);
    }

    @Override
    public void update(Furn furn) {
        furnMapper.updateByPrimaryKeySelective(furn);
    }

    @Override
    public void del(Integer id) {
        furnMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Furn findById(Integer id) {
        return furnMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Furn> findByCondition(String name) {
        FurnExample furnExample = new FurnExample();
        //通过Criteria对象设置查询的条件
        FurnExample.Criteria criteria = furnExample.createCriteria();

        //判断name是有具体值
        if (StringUtils.hasText(name)) {
            criteria.andNameLike("%"+name+"%");
        }
//        如果name没有传值null，"","  ",  依然是查询所有的记录
        return furnMapper.selectByExample(furnExample);
    }
}
