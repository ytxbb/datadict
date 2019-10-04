package com.jf.datadict.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 菜单
 */
@Getter
@Setter
public class DictMenu {

    private Integer uid;
    private String version;
    private String menuName;
    private Integer parentUid;

    private String typeName;
    private String tableName;
    // 子菜单
    private List<DictMenu> childMenus;

    public DictMenu() {
    }

    public DictMenu(Integer uid, String version, String menuName, Integer parentUid) {
        this.uid = uid;
        this.version = version;
        this.menuName = menuName;
        this.parentUid = parentUid;
    }
}
