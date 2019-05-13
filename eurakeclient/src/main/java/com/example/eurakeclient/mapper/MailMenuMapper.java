package com.example.eurakeclient.mapper;

import com.example.eurakeclient.model.MailMenu;

import java.util.List;

public interface MailMenuMapper {

    List selectList();

    int deleteByPrimaryKey(Integer id);

    int insert(MailMenu record);

    int insertSelective(MailMenu record);

    MailMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MailMenu record);

    int updateByPrimaryKey(MailMenu record);
}