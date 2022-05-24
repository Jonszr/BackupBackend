package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    int create( User user);

    User findByPhone(@Param("phone") String phone);

    User findByEmailAndPwd(@Param("email") String email, @Param("pwd") String pwd);

    User findByUserId(@Param("user_id") Integer userId);
    int update(User user);
}