package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IProductService {

    /*
    实现添加或者更新商品信息的项目接口
     */
    public ServerResponse insertOrUpdate(Product product);


    /*
    更新商品上下级状态的项目接口
     */
    public ServerResponse setSaleStatus(Integer productId,Integer status);


    /*
    通过商品ID查看商品详细信息
     */
    public ServerResponse detailByProductId(Integer productId);

    /*
    通过分页方式查询在前台显示商品信息
     */
    public ServerResponse checkInfoByPage(Integer pageNum,Integer pageSize);

    /*
    通过商品名称以及商品ID进行查询的方法接口，但也是可以省略不写的，如果
   只有商品ID就是进行精准查询，如果是写上商品名称则是通过模糊查询
     */
    public ServerResponse findProductByProductIdOrProductName(Integer productId,String productName,
                                                              Integer pageNum,Integer pageSize);


    /*
    商品图片上传的方法的接口
    其中file表示的是图片的文件对应的实体类
    path表示图片存放的路径
     */
    public ServerResponse uploadPicture(MultipartFile file,String path);


    /*
    前台查询商品详细信息的方法接口，但是注意的是与后台不同
    前台只能看到商品状态为在售的商品（即status=1)
     */

    public ServerResponse  protalDetailShopinfo(Integer productId);

    /*
   前台通过类别ID或者关键字的模糊查询的方法接口，但是注意的是在逻辑判断的时候至少要包含
    ID或者关键字的其中一个，之后通过分页查询返回查询的数据，并且要进行排序
     */
    public ServerResponse protalFindProductByCategoryIdOrKeyWord(Integer categoryId, String keyWord,Integer pageNum,Integer pageSize,String orderBy);



}
