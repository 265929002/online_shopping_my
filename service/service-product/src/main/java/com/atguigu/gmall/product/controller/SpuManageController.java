package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @ResponseBody + @Controller
@RequestMapping("admin/product")
public class SpuManageController {
    @Autowired
   private ManageService manageService;

//    http://api.gmall.com/admin/product/{page}/{limit}?category3Id=61  get
//page：第几页
//limit：每页数量
//category3Id：三级分类ID

    //String category3Id 名称一致，自动映射
    @GetMapping("{page}/{limit}")
    public Result getSpuInfoList(@PathVariable Long page,
                                 @PathVariable Long limit,
                                 SpuInfo spuInfo){//传递的参数被实体类所包含，可以用实体类来接受
//分页查询  ,泛型是spu商品表的实体类
        Page<SpuInfo> spuInfoPage = new Page<>(page,limit);
//        调用接口方法实现分页查询,将查询的数据进行返回
        IPage<SpuInfo> iPage = manageService.getSpuInfoList(spuInfoPage,spuInfo);
        return Result.ok(iPage);
    }

}
