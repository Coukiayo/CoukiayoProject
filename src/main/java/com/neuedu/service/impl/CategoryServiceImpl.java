package com.neuedu.service.impl;

import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /*
    通过商品ID查询子类的ID
     */

    @Override
    public ServerResponse getCategory(Integer categoryId) {

        //setp1:非空校验

        if (categoryId==null){
            return ServerResponse.createServerResponseByError("类别不能为空");
        }

        //setp2:根据categoryId查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null){

            return ServerResponse.createServerResponseByError("查询类别不存在");
        }

        //setp3:查询子类别
        List<Category> childCategory = categoryMapper.findChildCategory(categoryId);

        //setp4:返回结果

        return ServerResponse.createServerResponseBySuccess(null,childCategory);
    }



    /*
    增加新的节点
     */
    @Override
    public ServerResponse addCategory(Integer parentId, String categoryName) {

        //setp1:参数校验
        if (categoryName==null){
            return ServerResponse.createServerResponseByError("类别名称不能为空");
        }

        //setp2:添加节点
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(1);
        int insert = categoryMapper.insert(category);
        //setp3:返回结果
        if (insert>0){
            return ServerResponse.createServerResponseBySuccess("添加成功");
        }
        return ServerResponse.createServerResponseByError("添加失败");

    }

    @Override
    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {

        //step1:参数非空校验
        if (categoryId==null||categoryId.equals("")){
            return ServerResponse.createServerResponseByError("类别ID不能空");
        }
        if (categoryName==null||categoryName.equals("")){
            return ServerResponse.createServerResponseByError("类别名称不能空");
        }

        //step2:根据categoryId查询
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null){
            return ServerResponse.createServerResponseByError("修改的类别不存在");
        }

        //step3:修改
        category.setName(categoryName);
        int resoult = categoryMapper.updateByPrimaryKey(category);

        //step4:返回结果
        if (resoult>0){
            return ServerResponse.createServerResponseBySuccess("修改类别名称成功");
        }
        return ServerResponse.createServerResponseByError("修改类别名称失败");

    }

    @Override
    public ServerResponse getDeepCategory(Integer categoryId) {

        //step1:参数非空校验
        if (categoryId==null||categoryId.equals("")){
            return ServerResponse.createServerResponseByError("类别ID不能空");
        }

        //step2:查询
        Set<Category>categorySet= Sets.newHashSet();
        categorySet = findAllChileCategory(categorySet,categoryId);
        Set<Integer>integerSet=Sets.newHashSet();
        Iterator<Category> iterator = categorySet.iterator();
        while (iterator.hasNext()){
            Category next = iterator.next();
            integerSet.add(next.getId());
        }

        return ServerResponse.createServerResponseBySuccess(null,integerSet);
    }

    private Set<Category>findAllChileCategory(Set<Category> categories,Integer categoryId){

        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category!=null){
            categories.add(category);
        }

        //查找categoryId下的子节点（平级）
        List<Category> categoryList = categoryMapper.findChildCategory(categoryId);
        //然后遍历子节点，查询字节点下的所有child节点
        if (categoryList!=null&&categoryList.size()>0){
            for (Category category1:categoryList){
                findAllChileCategory(categories,category1.getId());
            }
        }

        return categories;
    }
}
