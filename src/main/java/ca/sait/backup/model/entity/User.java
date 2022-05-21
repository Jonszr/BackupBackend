package ca.sait.backup.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.hibernate.annotations.Table;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
 *   `name` varchar(128) DEFAULT NULL COMMENT 'name',
 *   `pwd` varchar(124) DEFAULT NULL COMMENT 'password',
 *   `head_img` varchar(524) DEFAULT NULL COMMENT 'head image',
 *   `phone` varchar(64) DEFAULT '' COMMENT 'phone number',
 *   `create_time` datetime DEFAULT NULL COMMENT 'time created',
 */

public class User {

    private Integer id;

    @NotEmpty(message = "Name is mandatory")

    private String name;

    @JsonIgnore
    @NotEmpty(message = "password is mandatory")
    @Size(min = 4, max = 15,message = "Password must be between 4 to 15 characters")
    private String pwd;

    @JsonProperty("head_img")
    private String headImg;
    @NotEmpty(message = "password is mandatory")
    private String phone;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", headImg='" + headImg + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
