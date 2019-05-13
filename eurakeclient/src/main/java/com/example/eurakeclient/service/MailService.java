package com.example.eurakeclient.service;

import com.example.eurakeclient.mapper.MailMapper;
import com.example.eurakeclient.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: hyh
 * @Date: Created by 9:43 2019/5/13
 * @Description:
 */
public class MailService implements MailMapper {
    @Autowired
    private MailMapper mailMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Mail record) {
        return mailMapper.insert(record);
    }

    @Override
    public int insertSelective(Mail record) {
        return mailMapper.insertSelective(record);
    }

    @Override
    public Mail selectByPrimaryKey(Integer id) {
        return mailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Mail record) {
        return mailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Mail record) {
        return mailMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(Mail record) {
        return mailMapper.updateByPrimaryKey(record);
    }
}
