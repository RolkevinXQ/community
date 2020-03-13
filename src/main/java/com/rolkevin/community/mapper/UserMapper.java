package com.rolkevin.community.mapper;

import com.rolkevin.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modify) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModify})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User selectUserByToken(String token);
    @Select("select * from user where accountid = #{accountId}")
    User selectUserByAccountid(Long accountId);
}
