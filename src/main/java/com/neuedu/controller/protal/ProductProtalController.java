package com.neuedu.controller.protal;


import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product/")
public class ProductProtalController {

    @Autowired
    IProductService productService;

    /*
    前台查询商品详细信息，但是注意的是与后台不同
    前台只能看到商品状态为在售的商品（即status=1)
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse protal_detail_shopinfo(Integer productId){

        /*
        因为是在前台查看商品，所以不需要进行登录以及权限的判断
         */

        return productService.protalDetailShopinfo(productId);

    }

    /*
    前台通过类别ID或者关键字的模糊查询，但是注意的是在逻辑判断的时候至少要包含
    ID或者关键字的其中一个，之后通过分页查询返回查询的数据，并且要进行排序
    所以参数都是可以为空的
     */
    @RequestMapping(value = "list.do")
    public ServerResponse findProductByCategoryIdOrKeyWord(@RequestParam(required = false) Integer categoryId,
                                                           @RequestParam(required = false) String keyWord,
                                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                           @RequestParam(defaultValue = "") String orderBy){


        return productService.protalFindProductByCategoryIdOrKeyWord(categoryId,keyWord,pageNum,pageSize,orderBy);
    }



}
