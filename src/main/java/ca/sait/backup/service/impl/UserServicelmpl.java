package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.UserMapper;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.CommonUtils;
import ca.sait.backup.utils.JWTUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
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

    /**
     * This method only can update [email, password, phone, head_image,name]
     * @param updatedUserInfo
     * @return
     */
    @Override
    public int update(Map<String, String> updatedUserInfo) {
        String id = updatedUserInfo.get("id");

        User user =  findByUserId(Integer.parseInt(id));


        if (user != null){
            for(Map.Entry<String,String> userinfo : updatedUserInfo.entrySet()){
                switch (userinfo.getKey()) {
                    case "name":
                        user.setName(userinfo.getValue());
                        break;
                    case "email":
                        user.setEmail(userinfo.getValue());
                        break;
                    case "pwd":
                        user.setPwd(CommonUtils.MD5(userinfo.getValue()));
                        break;
                    case "head_img":
                        user.setHeadImg(userinfo.getValue());
                        break;
                    case "phone":
                        user.setPhone(userinfo.getValue());
                        break;
                }
            }
            validate(user);
            return userMapper.update(user);


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
            user.setPwd(pwd);

            validate(user);
            user.setPwd(CommonUtils.MD5(pwd));



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

    /**
     * User validator
     * @param user
     */
    private void validate(User user){
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();


            for (ConstraintViolation<User> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
                break;
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
    }

}