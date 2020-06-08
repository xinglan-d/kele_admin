package com.kele.system.dao.dto;

import com.kele.base.dao.data.BusinessBaseDO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/05/09 11:26
 * @version: 1.0
 */
@Entity
@Table(name = "sys_role")
@Getter
@Setter
public class RoleDO extends BusinessBaseDO {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String roleId;
    @Column
    private String roleName;
    @Column
    private String deptSeq;


    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "roleId")                       //指定与本类主键所对应的外表的外键
    private List<RoleAuthDO> roleAuths;


}
