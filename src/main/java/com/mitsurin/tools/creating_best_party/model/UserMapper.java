package com.mitsurin.tools.creating_best_party.model;

import javax.persistence.Table;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT user_pwd FROM user WHERE user_id = #{user_id}")
    public String getUserCryptedPwd(String user_id);

    @Insert("INSERT INTO user (user_id, user_pwd) VALUES (#{user_id}, #{user_pwd})")
    public boolean registNewUser(String user_id, String user_pwd);

    @Select("SELECT COUNT(*) FROM user WHERE user_id = #{user_id}")
    public int isExist(String user_id);
}
