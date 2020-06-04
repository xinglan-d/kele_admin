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
@Table(name = "sys_block_list")
@Setter
@Getter
public class BlockListDO extends BusinessBaseDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String blockListId;

    @Column(length = 64)
    private String name;

    @Column()
    private String url;

}
