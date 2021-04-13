package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BaseTrademarkServiceImpl
//    自动实现各种类的CRUD方法，和IService配套使用
//    第一个参数是Mapper对象，第二个参数是实体类对象
        extends ServiceImpl<BaseTrademarkMapper,BaseTrademark>
        implements BaseTrademarkService {
    @Resource
    private BaseTrademarkMapper baseTrademarkMapper;

    @Override
    public IPage getBaseTradeMarkList(Page<BaseTrademark> baseTrademarkPage) {
        QueryWrapper<BaseTrademark> baseTrademarkQueryWrapper = new QueryWrapper<>();
//        添加排序规则
        baseTrademarkQueryWrapper.orderByDesc("id");
        return baseTrademarkMapper.selectPage(baseTrademarkPage,baseTrademarkQueryWrapper);
    }
}
