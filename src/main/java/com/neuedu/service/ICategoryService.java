package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public interface ICategoryService {

    /*
    获取品类的子节点
     */
    public ServerResponse getCategory(Integer categoryId);


    /*
    增加新的节点
     */

    public ServerResponse addCategory(Integer parentId,String categoryName);

    /*
    修改节点名称
     */
    public ServerResponse setCategoryName(Integer categoryId,String categoryName);

    /*
    获取当前分类ID以及递归子节点categoryID
     */

    public ServerResponse getDeepCategory(Integer categoryId);

}
