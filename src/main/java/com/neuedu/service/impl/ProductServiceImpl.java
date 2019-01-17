package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtils;
import com.neuedu.utils.FtpUtils;
import com.neuedu.utils.PropertiesUtils;
import com.neuedu.vo.ProdcutDetailVo;
import com.neuedu.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ICategoryService categoryService;
    /*
    商品信息的更新或添加方法的实现类
     */
    @Override
    public ServerResponse insertOrUpdate(Product product) {

        //step1:参数的非空校验
        if (product==null){
            return ServerResponse.createServerResponseByError("商品信息不能为空");
        }

        /*
        step2：向商品信息中添加主图，在该项目中商品主图的确立是用子图集合中的
        第一张，而子图在数据库的存取方式是字符串形式，并且当存在多个子图时用“，”
        隔开，例如：sub_images:1.jsp,2.jsp,3.png，相当于从一个数组中获取第一个数据
         */

        //获取商品的子图集
        String subImages = product.getSubImages();
        if (subImages!=null&&subImages.equals("")){
            //判断如果子图集存在的话，就根据“,”截取得到一个字符串数组
            String[] split = subImages.split(",");
            if (split.length>0){
            //之后判断如果得到的字符串数组长度大于0（非空），则将第一个子图集图片
            //作为主图
                product.setMainImage(split[0]);
            }
        }

        /*
        step3:判断对商品的操作是添加还是更新，判断依据是通过在返回数据时
        商品的数据信息中是否包含了商品ID，如果包含，则证明是更新，如果不包含
        则证明是添加（因为在数据库中的商品表中，创建时就规定商品的ID为主键，且自增）
         */
        //step4:返回结果,注意在添加参数的过程中要使用商品对应的实体类中的变量名
        //如果不使用则可能造成接收不到数据（当实体类变量名与字段名一致时可以忽略）
        if (product.getId()==null){
            //表明如果不存在商品ID，那么就是添加操作，则使用添加方法
            int insert = productMapper.insert(product);
            if (insert>0){
                return ServerResponse.createServerResponseBySuccess("添加成功");
            }
            return ServerResponse.createServerResponseByError("添加失败");
        }else {
            //此时表示存在的商品ID，这是就要使用更新方法
            int i = productMapper.updateByPrimaryKey(product);
            if (i>0){
                return ServerResponse.createServerResponseBySuccess("更新成功");
            }
            return ServerResponse.createServerResponseByError("更新失败");
        }

    }


    /*
    更新商品上下架状态的的具体实现类
     */
    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        //step1:参数的非空校验
        if (productId==null||productId.equals("")){
            return ServerResponse.createServerResponseByError("商品ID不能为空");
        }
        if (status==null||status.equals("")){
            return ServerResponse.createServerResponseByError("商品状态参数不能为空");
        }

        //step2:更新商品状态
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        //step3：返回结果集
        int result = productMapper.updateStatusByProductId(product);
        if (result>0){
            return ServerResponse.createServerResponseBySuccess("更新商品状态成功");
        }
        return ServerResponse.createServerResponseByError("更新商品状态失败");
    }


    /*
    通过商品ID查看商品详细信息
     */
    @Override
    public ServerResponse detailByProductId(Integer productId) {
        //step1:参数的非空校验
        if (productId==null||productId.equals("")){
            return ServerResponse.createServerResponseByError("商品ID不能为空");
        }

        //step2:通过商品ID查询商品信息
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createServerResponseByError("商品不存在");
        }
        /*
        step3:将商品类转换为商品视图类，因为是在页面上返回的
        即：product-->productdetailvo
         */
        ProdcutDetailVo prodcutDetailVo = assembleProdcutDetailVo(product);

        //step4：返回结果
        return ServerResponse.createServerResponseBySuccess(null,prodcutDetailVo);
    }


    /**
     * 创建一个productdetailvo类，用以接收product，并进行转换
     * 其中将product中创建时间以及更新时间（date类型）通过工具类
     * 转换成字符串（date--> String）
     */
    public ProdcutDetailVo assembleProdcutDetailVo(Product product){

        ProdcutDetailVo prodcutDetailVo = new ProdcutDetailVo();
        prodcutDetailVo.setId(product.getId());
        prodcutDetailVo.setCategoryId(product.getCategoryId());
        prodcutDetailVo.setName(product.getName());
        prodcutDetailVo.setSubtitle(product.getSubtitle());
        prodcutDetailVo.setMainImage(product.getMainImage());
        prodcutDetailVo.setSubImages(product.getSubImages());
        prodcutDetailVo.setImageHost(PropertiesUtils.readPropertiesByKey("imageHost"));
        prodcutDetailVo.setDetail(product.getDetail());
        prodcutDetailVo.setPrice(product.getPrice());
        prodcutDetailVo.setStock(product.getStock());
        prodcutDetailVo.setStatus(product.getStatus());
        prodcutDetailVo.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        prodcutDetailVo.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
        prodcutDetailVo.setIsNew(product.getIsNew());
        prodcutDetailVo.setIsHot(product.getIsHot());
        prodcutDetailVo.setIsBanner(product.getIsBanner());
        /**
         * 设置商品的父类ID(商品表中没有),这时就通过category的mapper文件中的方法
         * 通过该商品类别id，在商品类别表中查询到类别，之后就返回它的父类别ID即可
         */
        Category category = categoryMapper.selectByPrimaryKey(product.getId());
        if (category!=null){
            prodcutDetailVo.setParentCategoryId(category.getParentId());
        }else {
            prodcutDetailVo.setParentCategoryId(0);
        }

        return prodcutDetailVo;
    }

    @Override
    public ServerResponse checkInfoByPage(Integer pageNum, Integer pageSize) {

        /*
        因为在这个方法中，是有默认值的，所以是不需要进行非空判断
         */

        /*
        这一步是使用分页插件。来直接对数据进行分页
        注意：这一步必须要写在查询语句的前面，运用Spring中的AOP（面向切面编程）
        如果放在查询语句的后面是不起作用的,就是相当于在sql语句后面添加limit
         */
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectAll();
        List<ProductListVo>productListVos = Lists.newArrayList();
        if (products!=null&&products.size()>0){
            for (Product product:products){
                ProductListVo productListVo = assembleProductList(product);
                productListVos.add(productListVo);
            }
        }
        PageInfo pageInfo = new PageInfo(productListVos);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }



    /*
    将product类型转换成ProductListVo类型
    因为在前台中返回商品数据信息的类型是ProductListVo
    是因为在前台显示数据时并不需要显示所有的字段，因此用该方法接收product
    就只需要将想要显示的字通过ProductListVo（视图模型）进行封装即可
     */

    private ProductListVo assembleProductList(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setName(product.getName());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        return productListVo;
    }


    /*
        通过商品名称以及商品ID进行查询方法接口的实现类 ，但也是可以省略不写的，如果
   只有商品ID就是进行精准查询，如果是写上商品名称则是通过模糊查询
     */
    @Override
    public ServerResponse findProductByProductIdOrProductName(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        //因为这些参数都是可以不用强制书写的，所以就不用进行非空判断

        PageHelper.startPage(pageNum,pageSize);
        if (productName!=null&&!productName.equals("")){
            productName = "%"+productName+"%";
        }

        List<Product> productBySelectIdOrProductName = productMapper.findProductBySelectIdOrProductName(productId, productName);
        List<ProductListVo>productListVos = Lists.newArrayList();
        if (productBySelectIdOrProductName!=null&&productBySelectIdOrProductName.size()>0){
            for (Product product:productBySelectIdOrProductName){
                ProductListVo productListVo = assembleProductList(product);
                productListVos.add(productListVo);
            }
        }else {
            return ServerResponse.createServerResponseByError("查询的结果为空");
        }
            PageInfo pageInfo = new PageInfo(productListVos);

        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }



        /*
     商品图片上传的方法的接口
     其中file表示的是图片的文件对应的实体类
     path表示图片存放的路径
      */
    @Override
    public ServerResponse uploadPicture(MultipartFile file, String path) {

        //step1:首先对图片文件进行一个非空判断
        if (file==null){
            return ServerResponse.createServerResponseByError("上传的图片为空");
        }
        //step2:获取图片以及图片的拓展名，并且为图片更改一个唯一的图片名
        //因为在多个用户上传数据库时，很有可能上传的名称相同的的图片，为了防止
        //旧的图片被新的图片覆盖，所以在数据库中生成一个新的图片名

        //首先获取文件（图片）的名称
        String originalFilename = file.getOriginalFilename();
        //截取文件的后缀，因为图片的后缀都是以.进行分隔
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        /*
        为图片生成一个新的图片名（随机且唯一的代码+文件的后缀名
        其中substring包含了文件名中的"."
         */
        String s = UUID.randomUUID().toString() + substring;

        File file1 = new File(path);
        //判断这个路径是否存在
        if (!file1.exists()){
            //判断如果不存在，则为创建一个该路径，并且为该路径设置读写功能
            file1.setWritable(true);
            file1.mkdirs();
        }

        //这时将文件写入到目录下
        File file2 = new File(path, s);
        try {
            file.transferTo(file2);
            FtpUtils.uploadFile(Lists.newArrayList(file2));
            Map<String,String> map = Maps.newHashMap();
            map.put("uri",s);
            map.put("URL",PropertiesUtils.readPropertiesByKey("imageHost")+"/"+s);
            //删除应用服务器（本地服务器）上的图片，因为已经上传到ftp服务器上了，所以就可以删除了
           file2.delete();
            return ServerResponse.createServerResponseBySuccess("上传成功",map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
    前台查询商品详细信息方法的具体实现类，但是注意的是与后台不同
    前台只能看到商品状态为在售的商品（即status=1)
     */
    @Override
    public ServerResponse protalDetailShopinfo(Integer productId) {
        //step1:对参数进行非空校验
        if (productId==null){
            return ServerResponse.createServerResponseByError("查询的商品ID不能为空");
        }

        //step2:查询product，并对商品进行非空校验
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createServerResponseByError("查询的商品不存在");
        }

        //step3:检验商品的状态（即在前台中只显示商品状态为1的商品的数据信息）
        if (product.getStatus()!= Const.protalProductStatus.PRODUCT_ONLINE.getCode()){
            return ServerResponse.createServerResponseByError("查询的商品已经下架或被删除");
        }

        //step4:返回ProdcutListlVo
        ProductListVo productListVo = assembleProductList(product);

        //step5:返回结果
        return ServerResponse.createServerResponseBySuccess(null,productListVo);
    }


    /*
    前台通过类别ID或者关键字的模糊查询的方法具体的实现类，但是注意的是在逻辑判断的时候至少要包含
    ID或者关键字的其中一个，之后通过分页查询返回查询的数据，并且要进行排序
     */
    @Override
    public ServerResponse protalFindProductByCategoryIdOrKeyWord(Integer categoryId, String keyWord, Integer pageNum, Integer pageSize, String orderBy) {
        //step1:参数的非空校验（确保categoryID和keyword不能同时为空）

        if (categoryId==null&&(keyWord==null||keyWord.equals(""))){
            return ServerResponse.createServerResponseByError("类别ID和关键字不能同时为空");
        }

        //step2:通过categoryId进行查询
        Set<Integer> integerSet = Sets.newHashSet();
        if (categoryId!=null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category==null &&(keyWord==null||keyWord.equals(""))){
                //此时的情况说明我这时只通过categoryId进行查询，相当于精确查询，结果
                //为空的话，说明在数据库中不存在这种商品类别，但是也要返回一个
                // ProductListVo类型的Json字符串，因为返回数据的标准格式都是ProductListVo类型
                //的json字符串
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo>productListVos = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVos);
                return ServerResponse.createServerResponseBySuccess(null,pageInfo);

            }
            ServerResponse deepCategory = categoryService.getDeepCategory(categoryId);
            if (deepCategory.isSuccess()){
               integerSet = (Set<Integer>) deepCategory.getData();
            }
        }
        //step3:通过keyWord进行模糊查询
        if (keyWord!=null&&!keyWord.equals("")){
            keyWord = "%"+keyWord+"%";
        }
        //step4:添加分页插件的java代码
        if (orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else {
            String[] s = orderBy.split("_");
            if (s.length>1){
                PageHelper.startPage(pageNum,pageSize,s[0]+" "+s[1]);
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }
        //step5:将查询出来的结果转为ProductListVo类型，用到实现类中的assembleProductList的方法
        List<Product> productByCategoryIdOrKeyWordAndOrderBy = productMapper.findProductByCategoryIdOrKeyWordAndOrderBy(integerSet,keyWord);
        List<ProductListVo> productListVos = Lists.newArrayList();
        if (productByCategoryIdOrKeyWordAndOrderBy!=null&&productByCategoryIdOrKeyWordAndOrderBy.size()>0){
            for (Product product:productByCategoryIdOrKeyWordAndOrderBy){
                ProductListVo productListVo = assembleProductList(product);
                productListVos.add(productListVo);
            }
        }

        //step6:返回结果
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(productListVos);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

}
