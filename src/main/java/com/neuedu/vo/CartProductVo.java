package com.neuedu.vo;


/*
购物车中商品数据信息对应的实体类
只需要将product通过方法封装到CartProductVo类上即可
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class CartProductVo implements Serializable {

    private Integer id;//ID
    private Integer userId;//某个购物车对应的用户ID（因为购物车是和用户绑定的）
    private Integer productId;//商品ID
    private Integer quantity;//商品加入购物车中的数量
    private String productName;//商品名称
    private String productSubtitle;//商品描述
    private String productMainImage;//商品主图
    private BigDecimal productPrice;//商品单价
    private Integer productStatus;//商品状态
    private BigDecimal productTotalPrice;//商品的总价格：商品单价×商品数量
    private Integer productStock;//商品库存
    private Integer productChecked;//商品是否被传中
    /*
    商品数量限制，表示当所要购买的商品数量大于库存时就会返回数量不够，当
    小于库存时就会返回库存充足
     */
    private String limitQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
