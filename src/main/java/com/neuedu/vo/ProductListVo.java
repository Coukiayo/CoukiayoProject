package com.neuedu.vo;

import java.io.Serializable;
import java.math.BigDecimal;
/*
在前台中返回商品数据信息对应的实体类
由于在前台显示数据时并不需要显示所有的字段，因此
就只需要将想要显示的字通过ProductListVo（视图模型）进行封装即可
 */

public class ProductListVo implements Serializable {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private Integer status;
    private String mainImage;
    private BigDecimal price;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductListVo{" +
                "id=" + id +
                ", categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", status=" + status +
                ", mainImage='" + mainImage + '\'' +
                ", price=" + price +
                '}';
    }

    public ProductListVo(Integer id, Integer categoryId, String name, String subtitle, Integer status, String mainImage, BigDecimal price) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.subtitle = subtitle;
        this.status = status;
        this.mainImage = mainImage;
        this.price = price;
    }

    public ProductListVo() {
    }
}
