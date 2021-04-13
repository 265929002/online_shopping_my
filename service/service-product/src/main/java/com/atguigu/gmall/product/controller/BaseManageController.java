package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.ManageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/product")
@Api(description = "平台属性和123级分类")

public class BaseManageController {
    @Autowired
    private ManageService manageService;

    /**
     * 获取一级分类数据
     * @return
     */
    @GetMapping("getCategory1")
    public Result getCategory1(){

        return Result.ok(manageService.getBaseCategory1());
    }
//getCategory2/{category1Id}      get
@GetMapping("getCategory2/{category1Id}")
public Result getCategory2(@PathVariable Long category1Id){

    return Result.ok(manageService.getBaseCategory2ById(category1Id));
}
//    getCategory3/{category2Id}   get

    @GetMapping("getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable Long category2Id){

        return Result.ok(manageService.getBaseCategory3ById(category2Id));
    }


//    attrInfoList/{category1Id}/{category2Id}/{category3Id}    get

    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable Long category1Id,
                               @PathVariable Long category2Id,
                               @PathVariable Long category3Id
                               ){
//返回数据
        return Result.ok(manageService.getBaseAttrInfoList(category1Id,category2Id,category3Id));
    }
//保存平台数据
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        manageService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }


//
    @GetMapping("getAttrValueList/{attrId}")
//    getAttrValueList/{attrId}
    public Result getAttrValueList(@PathVariable Long attrId){
        BaseAttrInfo baseAttrInfo = manageService.getbaseAttrInfo(attrId);
        if (baseAttrInfo!=null){
//            通过属性拿属性值集合
            return Result.ok(baseAttrInfo.getAttrValueList());
        }

//        List<BaseAttrValue> baseAttrValues = manageService.getAttrValueList(attrId);
        return Result.ok();
    }
}
