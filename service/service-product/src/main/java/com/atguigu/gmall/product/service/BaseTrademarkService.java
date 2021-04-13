package com.atguigu.gmall.product.service;


import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.SpuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseTrademarkService extends IService<BaseTrademark> {//自动实现泛型这个实体类的CRUD方法
    /**
     * 品牌无条件分页展示数据
     * @param baseTrademarkPage
     * @return
     */
    IPage getBaseTradeMarkList(Page<BaseTrademark> baseTrademarkPage);

}
