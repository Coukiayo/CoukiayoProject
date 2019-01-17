package com.neuedu.common;

public class Const {

    public static final String CURRENTUSER = "currentuser";
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    /*
       创建商品的权限码
     */
    public enum ReponseCodeEnum{
        NEED_LOGIN(2,"需要登录"),
        NO_PRIVILEGE(3,"无权限操作")
        ;
        private int code;
        private String desc;
        private ReponseCodeEnum(int code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }



    /*
    创建用户权限对应的权限码
     */
    public enum RoleEnum{
        ROLE_ADMIN(0,"管理员"),
        ROLE_CUSTOMER(1,"普通用户")
        ;


        private int code;
        private String desc;
        private RoleEnum(int code,String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


    //创建前台商品的上下架的一个状态码
    public enum protalProductStatus{

        PRODUCT_ONLINE(1,"在售"),
        PRODUCT_OFFLINE(2,"下架"),
        PRODUCT_DELETE(3,"删除")
        ;

        protalProductStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    //创建购物车中商品是否被勾选的一个状态码
    public enum cartCheckStatus{
        PRODUCT_CHECKED(1,"已勾选"),
        PRODUCT_UNCHECKED(0,"未勾选")
        ;


        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        cartCheckStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }


    //创建一个订单状态的状态码
    public enum OrderStatusEnum{
        ORDER_CANCELED(0,"已取消"),
        ORDER_UN_PAY(10,"未付款"),
        ORDER_PAYED(20,"已付款"),
        ORDER_SEND(40,"已发货"),
        ORDER_SUCCESS(50,"交易成功"),
        ORDER_CLOSER(60,"交易失败")
        ;

        private Integer code;
        private String desc;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        OrderStatusEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static OrderStatusEnum codeOf(Integer code){
            for (OrderStatusEnum orderStatusEnum:values()){
                if (code==orderStatusEnum.getCode()){
                    return orderStatusEnum;
                }
            }
            return null;
        }

    }

    /*
    创建一个支付平台的状态码
     */
    public enum PaymentPlatformEnum{
        ALIPY(1,"支付宝")
        ;

        private Integer code;
        private String desc;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        PaymentPlatformEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /*
    创建一个支付类型的状态码
     */
    public enum PaymentType{
        ONLINE(1,"线上支付")
        ;


        private Integer code;
        private String desc;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        PaymentType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static PaymentType codeOf(Integer code){
            for (PaymentType paymentType:values()){
                if (code==paymentType.getCode()){
                    return paymentType;
                }
            }
            return null;
        }
    }
}
