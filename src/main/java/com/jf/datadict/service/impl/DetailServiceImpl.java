package com.jf.datadict.service.impl;

import com.jf.datadict.dao.DetailMapper;
import com.jf.datadict.entity.DataBaseName;
import com.jf.datadict.entity.DictMenu;
import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.DetailService;
import com.jf.datadict.util.MyStringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DetailServiceImpl implements DetailService {

    @Resource
    private DetailMapper detailMapper;

    @Override
    public JSONResult queryAllDataBase() {
        List<DataBaseName> dataBaseNames;
        try {
            dataBaseNames = detailMapper.queryAllDataBase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询数据库列表出错："+e.getMessage());
        }
        return JSONResult.ok(dataBaseNames);
    }

    @Override
    public JSONResult queryMenuList(String dbName) {
        if (MyStringUtil.isEmpty(dbName)) {
            return JSONResult.error500("传入参数为空");
        }

        DataBaseName dataBaseName;
        try {
            dataBaseName = detailMapper.queryAllDataBaseByName(dbName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("根据查询数据库列表时出错："+e.getMessage());
        }

        List<DictMenu> dictMenus;
        try {
            dictMenus = detailMapper.queryMenuList(dataBaseName.getUid());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("查询虚拟菜单时出错："+e.getMessage());
        }

        Set<String> versionSet = new HashSet<>();
        for (DictMenu menu : dictMenus) {
            versionSet.add(menu.getVersion());
        }
        // 所有版本List
        List<String> versionList = new ArrayList<>(versionSet);

        // 最后的结果
        List<DictMenu> resMenuList = new ArrayList<>();
        Map<String, Integer> uidMap = new HashMap<>();
        Map<Integer, List<DictMenu>> thirdChildMenusMap = new HashMap<>();
        // 设置版本作为一级菜单
        for (String v : versionList) {
            int uid = (int) (Math.random()*(10999-1000));
            checkUidOnly(uidMap, v, uid);

            List<DictMenu> tempMenuListOfVersion = new ArrayList<>();
            for (DictMenu menu : dictMenus) {
                if (menu.getVersion().equals(v)) {
                    tempMenuListOfVersion.add(menu);
                }
            }

            // 设置三级菜单(即表名)
            for (DictMenu d : tempMenuListOfVersion) {
                DictMenu thirdDictMenu = new DictMenu(d.getUid(), d.getVersion(), d.getTableName(), d.getParentUid());

                if (thirdChildMenusMap.containsKey(d.getParentUid())) {
                    thirdChildMenusMap.get(d.getParentUid()).add(thirdDictMenu);
                }else {
                    List<DictMenu> thirdChildMenus = new ArrayList<>();
                    thirdChildMenus.add(thirdDictMenu);
                    thirdChildMenusMap.put(d.getParentUid(), thirdChildMenus);
                }
            }

            List<DictMenu> secondChildMenus = new ArrayList<>();
            Set<Integer> secondUidSet = new HashSet<>();
            // 给二级菜单(即分类类目)赋值父ID为生成的uid
            Iterator<Map.Entry<Integer, List<DictMenu>>> iterator = thirdChildMenusMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, List<DictMenu>> entry = iterator.next();
                for (DictMenu sec : tempMenuListOfVersion) {
                    Integer secUid = sec.getParentUid();
                    if (entry.getKey().equals(secUid) && !secondUidSet.contains(secUid)) {
                        secondUidSet.add(secUid);
                        DictMenu secDictMenu = new DictMenu(secUid, sec.getVersion(), sec.getTypeName(), uid);
                        secDictMenu.setChildMenus(entry.getValue());
                        secondChildMenus.add(secDictMenu);
                    }
                }
            }

            DictMenu firstDictMenu = new DictMenu(uid, v, v, null);
            firstDictMenu.setChildMenus(secondChildMenus);
            resMenuList.add(firstDictMenu);
        }

        return JSONResult.ok(resMenuList);
    }

    /**
     * 递归判断生成的uid不唯一
     */
    private void checkUidOnly(Map<String, Integer> uidMap, String v, Integer uid){
        if (uidMap.containsValue(uid)) {
            uid = (int) (Math.random()*(10999-1000));
            checkUidOnly(uidMap, v, uid);
        }
        uidMap.put(v, uid);
    }

    /**
     * 递归查找子菜单
     *
     * @param id 当前菜单id
     * @param rootMenu 要查找的列表
     * @return list
     */
    private List<DictMenu> getChild(Integer id, List<DictMenu> rootMenu) {
        // 子菜单
        List<DictMenu> childList = new ArrayList<>();
        for (DictMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentUid().equals(id)) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (DictMenu menu : childList) {// 没有url子菜单还有子菜单
            // 递归
            menu.setChildMenus(getChild(menu.getUid(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
