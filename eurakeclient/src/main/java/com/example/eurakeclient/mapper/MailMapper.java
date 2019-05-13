package com.example.eurakeclient.mapper;

import com.example.eurakeclient.model.Mail;

import java.util.List;

public interface MailMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Mail record);

    int insertSelective(Mail record);

    List selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKeyWithBLOBs(Mail record);

    int updateByPrimaryKey(Mail record);
}