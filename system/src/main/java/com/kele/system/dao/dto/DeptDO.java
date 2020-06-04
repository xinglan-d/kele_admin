package com.kele.system.dao.dto;

import com.kele.base.dao.data.BusinessBaseDO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/05/09 11:26
 * @version: 1.0
 */
@Entity
@Table(name = "sys_dept")
@Getter
@Setter
public class DeptDO extends BusinessBaseDO {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String deptId;
    @Column
    private String deptName;
    @Column(length = 32)
    private String deptPid;

}
