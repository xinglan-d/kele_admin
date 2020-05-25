package com.kele.system.dao.dto;

import com.kele.base.dao.data.BusinessBaseDO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @description:用户表
 * @author: dzy
 * @createDate: 2020/1/20 14:04
 * @version: 1.0
 */
@Entity
@Table(name = "sys_user")
@Setter
@Getter
public class LoginUserDO extends BusinessBaseDO {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String userId;

    @Column(length = 24)
    private String account;

    @Column(length = 56)
    private String password;

    @Override
    public String getPrimaryKey() {
        return userId;
    }
}
