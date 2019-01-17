package com.neuedu.vo;

import java.io.Serializable;
import java.math.BigDecimal;
/*
用于接收product类型的对象
 */

public class ProdcutDetailVo implements Serializable {

    private Integer id;//商品ID
    private Integer categoryId;//商品类别ID
    private Integer parentCategoryId;//商品父类别ID
    private String name;//商品名称
    private String subtitle;//商品副标题
    private String imageHost;//商品图片存放路径
    private String mainImage;//商品主图片
    private String subImages;//商品子图片
    private String detail;//商品描述
    private BigDecimal price;//商品价格
    private Integer stock;//商品库存
    private Integer status;//商品状态
    private String createTime;//商品创建时间
    private String updateTime;//商品更新时间
    private Integer isNew;//是否为新商品
    private Integer isHot;//是否为热销商品
    private Integer isBanner;

    public ProdcutDetailVo(Integer id, Integer categoryId, Integer parentCategoryId, String name, String subtitle, String imageHost, String mainImage, String subImages, String detail, BigDecimal price, Integer stock, Integer status, String createTime, String updateTime, Integer isNew, Integer isHot, Integer isBanner) {
        this.id = id;
        this.categoryId = categoryId;
        this.parentCategoryId = parentCategoryId;
        this.name = name;
        this.subtitle = subtitle;
        this.imageHost = imageHost;
        this.mainImage = mainImage;
        this.subImages = subImages;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isNew = isNew;
        this.isHot = isHot;
        this.isBanner = isBanner;
    }

    @Override
    public String toString() {
        return "ProdcutDetailVo{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", parentCategoryId=" + parentCategoryId +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", imageHost='" + imageHost + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", subImages='" + subImages + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isNew=" + isNew +
                ", isHot=" + isHot +
                ", isBanner=" + isBanner +
                '}';
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsBanner() {
        return isBanner;
    }

    public void setIsBanner(Integer isBanner) {
        this.isBanner = isBanner;
    }

    public ProdcutDetailVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


}
