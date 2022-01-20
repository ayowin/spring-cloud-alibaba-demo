package com.seangull.producer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seangull.dao.entity.Product;
import com.seangull.dao.mapper.ProductMapper;
import com.seangull.producer.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductMapper productMapper;

    public String produce(String requestJson) {
        String response = "";

        try {
            JSONObject jsonObject = JSONObject.parseObject(requestJson);
            Integer id = jsonObject.getInteger("id");
            Integer quantity = jsonObject.getInteger("quantity");

            QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq("id",id);
            Product product = productMapper.selectOne(productQueryWrapper);
            product.setQuantity(product.getQuantity() + quantity);
            LocalDateTime now = LocalDateTime.now();
            product.setUpdateTime(now);
            productMapper.updateById(product);

            jsonObject = new JSONObject();
            jsonObject.put("result","success");
            response = jsonObject.toJSONString();
        } catch (Exception e){
            logger.error(e.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result","failed");
            jsonObject.put("exception",e.toString());
            response = jsonObject.toJSONString();
        }

        return response;
    }
}
