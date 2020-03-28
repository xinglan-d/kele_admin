package com.kele.system.dao.dto;

import com.kele.base.dao.data.BusinessBaseDO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/03/27 18:54
 * @version: 1.0
 */
@Entity
@Table(name = "sys_service")
@Getter
@Setter
public class SysServiceDO extends BusinessBaseDO {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String serviceId;
    @Column(length = 64)
    private String name;
    @Column(length = 128)
    private String servicePath;
    @Column(length = 12)
    private String icon;

}
