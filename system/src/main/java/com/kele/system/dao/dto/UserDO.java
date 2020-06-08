package com.kele.system.dao.dto;

import com.kele.base.dao.data.BusinessBaseDO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
public class UserDO extends BusinessBaseDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String userId;

    @Column(length = 32)
    private String deptId;

    @Column(length = 32)
    private String roleId;

    @Column(length = 24)
    private String account;

    @Column(length = 56)
    private String password;

    @Column(length = 11)
    private String phone;

    @Column(length = 1)
    private Boolean isAdmin;

    @OneToOne()
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "deptId", insertable = false, updatable = false)
    private DeptDO dept;
    @OneToOne()
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private RoleDO role;

}
