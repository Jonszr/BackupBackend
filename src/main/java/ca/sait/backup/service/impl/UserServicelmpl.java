package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.UserMapper;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.CommonUtils;
import ca.sait.backup.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
@Validated
public class UserServicelmpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Validator validator;

    /**
     * Create a user
     * @param userInfo
     * @return
     */
    @Override
    public int create(Map<String, String> userInfo) {

        User user = parseToUser(userInfo);

        if( user != null){

            return userMapper.create( user);
        }else {
            return -1;
        }

    }




    @Override
    public String findByEmailAndPwd(String email, String pwd) {

        User user = userMapper.findByEmailAndPwd(email, CommonUtils.MD5(pwd));

        if(user == null){
            return null;

        }else {
            String token = JWTUtils.geneJsonWebToken(user);
            return token;
        }

    }

    @Override
    public User findByUserId(Integer userId) {

        User user = userMapper.findByUserId(userId);
        return user;
    }

    /**
     * Parse the User object
     * @param userInfo
     * @return
     */
    private User parseToUser(Map<String,String> userInfo) {

        if(userInfo.containsKey("email") && userInfo.containsKey("pwd") && userInfo.containsKey("name")){

            User user = new User();
            user.setName(userInfo.get("name"));
            user.setHeadImg(getRandomImg());
            user.setCreateTime(new Date());
            user.setEmail(userInfo.get("email"));
            String pwd = userInfo.get("pwd");
            //MD5加密
            user.setPwd(CommonUtils.MD5(pwd));
            Set<ConstraintViolation<User>> violations = validator.validate(user);

            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();


                for (ConstraintViolation<User> constraintViolation : violations) {
                    sb.append(constraintViolation.getMessage());
                    break;
                }
                throw new ConstraintViolationException(sb.toString(), violations);
            }

            return user;
        }else {
            return null;
        }

    }

    /**
     * 放在CDN上的随机头像
     */
    private static final String [] headImg = {
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };

    private String getRandomImg(){
        int size =  headImg.length;
        Random random = new Random();
        int index = random.nextInt(size);
        return headImg[index];
    }

}