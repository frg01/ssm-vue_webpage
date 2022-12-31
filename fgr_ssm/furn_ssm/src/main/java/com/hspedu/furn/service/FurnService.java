package com.hspedu.furn.service;

import com.hspedu.furn.bean.Furn;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public interface FurnService {
    //添加
    public void save(Furn furn);
    //查询所有家居信息
    public List<Furn> findAll();
    //修改家居
    public void update(Furn furn);
    //删除家居
    public void del(Integer id);
    //通过id查询家居
    public Furn findById(Integer id);

    //根据家居名称进行查询
    public List<Furn> findByCondition(String name);
}
