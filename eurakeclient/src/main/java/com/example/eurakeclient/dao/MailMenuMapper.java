package com.example.eurakeclient.dao;

import com.example.eurakeclient.model.MailMenu;

public interface MailMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MailMenu record);

    int insertSelective(MailMenu record);

    MailMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MailMenu record);

    int updateByPrimaryKey(MailMenu record);
}