package com.kele.system.dao.dto;

import com.kele.base.dao.data.BusinessBaseDO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/05/09 11:26
 * @version: 1.0
 */
@Entity
@Table(name = "sys_role_auth")
@Getter
@Setter
public class RoleAuthDO extends BusinessBaseDO {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String authId;
    @Column(length = 32)
    private String roleId;
    @Column(length = 32)
    private String menuId;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "menuId", insertable = false, updatable = false)
    private ServiceDO services;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "menuId", insertable = false, updatable = false)
    private MenuDO menus;
}
