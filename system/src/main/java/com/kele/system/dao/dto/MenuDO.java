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
 * @createDate: 2020/03/30 07:38
 * @version: 1.0
 */
@Entity
@Table(name = "sys_menu")
@Getter
@Setter
public class MenuDO extends BusinessBaseDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String menuId;
    @Column(length = 32)
    private String parentId;
    @Column(length = 255)
    private String name;
    @Column(length = 1)
    private String type;
    @Column(length = 12)
    private String icon;
    @Column(length = 6)
    private String color;
    @Column(length = 255)
    private String url;
    @Column(length = 2)
    private Integer buttonType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private List<MenuDO> menus;

}
