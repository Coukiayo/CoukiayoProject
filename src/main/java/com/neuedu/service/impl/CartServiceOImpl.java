package com.neuedu.service.impl;

import com.google.common.collect.Lists;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;

import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.service.ICategoryService;
import com.neuedu.utils.ProductPriceByBigDecimal;
import com.neuedu.vo.CartProductVo;
import com.neuedu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class CartServiceOImpl implements ICartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;
    /*
    执行购物车中添加商品数据或者更新商品购买的方法接口对应的实体类
    其中userId为选中的购物车对应的用户ID
    productId为商品的ID
    count表示为要添加的数量
    如果添加的商品id是已经在数据库中存在的话，那么就只更新商品的购买数量即可
     */
    @Override
    public ServerResponse addOrUpdateCartProduct(Integer userId, Integer productId, Integer count) {

        //step1:参数的非空校验
        if (productId==null){
            return ServerResponse.createServerResponseByError("商品ID不能为空");
        }
        if (userId==null){
            return ServerResponse.createServerResponseByError("用户ID不能为空");
        }

        //step2：根据userId以及productId查询购物车中商品数据信息
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart==null){
            //此时表示查询不到该购物车信息，所以进行添加操作
            Cart cart1 = new Cart();
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(count);
            cart1.setChecked(Const.cartCheckStatus.PRODUCT_CHECKED.getCode());//默认条件下购物车的勾选状态是1(已勾选的)
            cartMapper.insert(cart1);

        }else {
            //此时表示在购物车的表中查到了数据信息，可以进行更新商品数量的操作
            Cart cart1 = new Cart();
            cart1.setId(cart.getId());
            cart1.setProductId(cart.getProductId());
            cart1.setUserId(cart.getUserId());
            cart1.setQuantity(count);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);
        }

        CartVo cartVoByCartInfo = getCartVoByCartInfo(userId);
        return ServerResponse.createServerResponseBySuccess(null,cartVoByCartInfo);
    }


    //查看某个用户的购物车列表的接口对应的实现类
    @Override
    public ServerResponse showCartProductInfo(Integer userId) {
        CartVo cartVoByCartInfo = getCartVoByCartInfo(userId);

        return ServerResponse.createServerResponseBySuccess(null,cartVoByCartInfo);
    }

    @Override
    public ServerResponse updateCartProductCout(Integer userId, Integer productId, Integer count) {

        //step1:参数的非空校验
        if (productId==null){
            return ServerResponse.createServerResponseByError("商品ID不能为空");
        }
        if (userId==null){
            return ServerResponse.createServerResponseByError("用户ID不能为空");
        }

        //step2:查询购物车中的要更改商品信息
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart!=null){
            //step3:更改选中商品的数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);

        }

        //step4:返回结果
        return ServerResponse.createServerResponseBySuccess(null,getCartVoByCartInfo(userId));
    }

    @Override
    public ServerResponse deleteProductsByProductIds(Integer userId, String productIds) {
        //step1：参数的非空校验
        if (productIds==null&&productIds.equals("")){
            return ServerResponse.createServerResponseByError("商品ID不能为空");
        }
        /*
        将字符串形式的productIds通过方法用“，”进行性分割，得到多个字符串（数字）
        并返回一个字符串数组，之后遍历数组，将每个元素都转换成Interage形式
        之后在放到一个Interage类型的数组中
         */
        String[] split = productIds.split(",");
        List<Integer>integerList = Lists.newArrayList();
        if (split!=null&&split.length>0){
            for (String productIdStr:split){
                Integer productId = Integer.parseInt(productIdStr);
                integerList.add(productId);
            }
        }


        //step2：调用dao层并执行sql语句
        cartMapper.deleteProductsByUserIdAndProductIds(userId,integerList);

        //step3：将信息转换成CartVo
        CartVo cartVoByCartInfo = getCartVoByCartInfo(userId);

        //step4：返回结果
        return ServerResponse.createServerResponseBySuccess(null,cartVoByCartInfo);
    }

    /*
    购物车选中或者取消选中某个商品方法接口的具体实现类
    也可是实现购物车的全选与取消全选，这时只需要将productId为空（null）的shiho就可以
    这时就不需要对productId进行非空判断
    在购物车中checked字段中1：选中；0：未选中
     */
    @Override
    public ServerResponse selectCheckerByUserIdAndProductId(Integer userId, Integer productId,Integer checked) {

        //step2:调用dao层接口并执行sql语句
        cartMapper.updateProductCheckedByUserIdAndProductId(userId,productId,checked);

        //step3:将信息转换成CartVo
        CartVo cartVoByCartInfo = getCartVoByCartInfo(userId);

        //step4:返回结果
        return ServerResponse.createServerResponseBySuccess(null,cartVoByCartInfo);
    }


    /*
    查询在购物车里的产品数量
     */
    @Override
    public ServerResponse getCartProductCount(Integer userId) {

        Integer cartProductCountByUserId = cartMapper.getCartProductCountByUserId(userId);
        return ServerResponse.createServerResponseBySuccess(null,cartProductCountByUserId);
    }


    /*
    因为在前台页面中返回的购物车信息是以CartVo类型接收的
    所以创建一个方法来接收用户ID所对应的购物车中的商品信息已
    CartVo形式返回(该方法就是显示用户对应的购物车下的商品信息)
     */
    private CartVo getCartVoByCartInfo(Integer userId){
        CartVo cartVo = new CartVo();
        //step1:根据用户ID查询到对应的购物车信息，注意查询到的结果类型为List<Cart>
        List<Cart> cartByUser = cartMapper.findCartByUser(userId);

        //step2:将返回的结果转换成CartProductVo即：List<Cart>-->List<CartProductVo>
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        //初始化一个BigDecimal类型的变量，该变量表示的是购物车的总价格
        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (cartByUser!=null&&cartByUser.size()>0){
            for (Cart cart:cartByUser){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setQuantity(cart.getQuantity());
                cartProductVo.setProductChecked(cart.getChecked());
                cartProductVo.setUserId(cart.getUserId());
                //查询商品
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product!=null){
                    cartProductVo.setProductId(cart.getProductId());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    /*
                    此时要对商品库存以及要购买的数量两者之间进行一个判断，如果
                    数量<库存，那么表示库存充足，可以随意购买，如果数量>库存，这时就
                    表示库存量不支持购买量，那么最多就只能买库存量。
                     */
                    int stock = product.getStock();
                    //初始化一个int类型变量，表示最大购买的数量
                    int limitProductCount = 0;
                    if (stock>=cart.getQuantity()){
                        //此时表示库存充足
                        limitProductCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity("库存充足");
                    }else {
                        //此时表示库存不足，最大的购买量就只能是库存数
                        limitProductCount = stock;
                        //并且此时要更新一下购买的数量（更新数据，但是实际上只更新购物车中购买的数量）
                        Cart cart1 = new Cart();
                        cart1.setId(cart.getId());
                        cart1.setUserId(cart.getUserId());
                        cart1.setProductId(cart.getProductId());
                        cart1.setChecked(cart.getChecked());
                        cart1.setQuantity(stock);
                        cartMapper.updateByPrimaryKey(cart1);
                        cartProductVo.setLimitQuantity("库存不足");
                    }
                    cartProductVo.setQuantity(limitProductCount);
                    cartProductVo.setProductTotalPrice(ProductPriceByBigDecimal.productPricceMultiply(product.getPrice().doubleValue(),Double.valueOf(cartProductVo.getQuantity())));
                }

                //在循环中计算商品的总价格
                cartTotalPrice = ProductPriceByBigDecimal.productPriceAdd(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());

                cartProductVoList.add(cartProductVo);
            }
        }
        //将cartProductVoList（List<CartProductVo>）放入到cartVo中
        cartVo.setCartProductVoList(cartProductVoList);

        //step3:计算购物车中总商品价格
        cartVo.setCartTotalPrice(cartTotalPrice);

        //step4:判断购物车是否处在全选状态
        int notCheckQuantity = cartMapper.isNotCheckQuantity(userId);
        if (notCheckQuantity>0){
            cartVo.setAllCheck(false);
        }else {
            cartVo.setAllCheck(true);
        }
        //step5:返回结果
        return cartVo;
    }

}
