package com.xinyunfu.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.product.controller.ProductController;
import com.xinyunfu.product.mapper.ProPropertyMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductManageApplicationTests {

    @Autowired
    private ProPropertyMapper proPropertyMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private ProductController productController;

    @Autowired
    private RedisCommonUtil redis;
//    @Test
//    public void Test() {
//        ProProperty proProperty = new ProProperty();
//        proProperty.setProId(1000006L).setPropertyName("111");
//        proPropertyMapper.addProperty(proProperty);
//        log.info(proProperty.getKeyId().toString());
//        System.out.println(proProperty.getPropertyId());
//    }
//
//    @Test
//    public void TestSaveProduct() {
//        Product product = new Product();
//        product.setProNo("2222222").setProName("222222");
//        productMapper.insert(product);
//        log.info(proProperty.getKeyId().toString());
//        System.out.println("proId=" + product.getProId());
//    }

    /**
     * 测试新增product
     */
    @Test
    public void tset() {
        Product product=new Product();
        product.setProName("皮卡丘到底有没有毛");
        productService.saveProduct(product);
        System.out.println("=============proId==============="+product.getProId());
    }
    @Test
    public void testPage(){
        Product product=new Product();
        product.setStoreId(1L);
        IPage<Product> page=productController.findProductByPage(product,1,3).getData();
        System.out.println(page.toString());
    }


    @Test
    public void  testRedis()
    {
        String key ="cache_key";
         if(redis.setCache(key,23232332)){
             System.out.println(redis.exists(key));
         }
    }








}
