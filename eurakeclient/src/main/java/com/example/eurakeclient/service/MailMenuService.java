package com.example.eurakeclient.service;

import com.example.eurakeclient.mapper.MailMenuMapper;
import com.example.eurakeclient.model.MailMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: hyh
 * @Date: Created by 9:43 2019/5/13
 * @Description:
 */
@Service(value="mailMenuService")
public class MailMenuService implements MailMenuMapper{
    @Autowired
    public MailMenuMapper mailMenuMapper;

    @Override
    public List selectList() {
        return mailMenuMapper.selectList();
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mailMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MailMenu record) {
        return mailMenuMapper.insert(record);
    }

    @Override
    public int insertSelective(MailMenu record) {
        return mailMenuMapper.insertSelective(record);
    }

    @Override
    public MailMenu selectByPrimaryKey(Integer id) {
        return mailMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MailMenu record) {
        return mailMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MailMenu record) {
        return mailMenuMapper.updateByPrimaryKey(record);
    }
}
