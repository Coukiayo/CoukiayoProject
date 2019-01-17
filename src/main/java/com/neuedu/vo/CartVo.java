package com.neuedu.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
在前台中显示购物车信息对应的实体类 ，其中包含三个参数
 */
public class CartVo implements Serializable {

    /*
    该变量表示的是购物车中商品的数据信息，由于可能在购物车中存在多个商品，所以用集合表示
    并且在购物车中的商品信息通过创建CartProductVo类来对应，就可以只返回所需要的数据信息
    也是将product封装到CartProductVo
     */
    private List<CartProductVo> cartProductVoList;

    /*
    该变量表示购物车中的商品是否处在一个全选的状态
     */
    private boolean isAllCheck;

    /*
       该变量表示购物车中所有商品数量的总价格
     */
    private BigDecimal cartTotalPrice;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public boolean isAllCheck() {
        return isAllCheck;
    }

    public void setAllCheck(boolean allCheck) {
        isAllCheck = allCheck;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }
}
