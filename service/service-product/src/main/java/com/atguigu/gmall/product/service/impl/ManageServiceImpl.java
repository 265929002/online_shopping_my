package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.ManageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)//捕获异常会回滚
public class ManageServiceImpl implements ManageService {

    @Resource
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Resource
    private BaseAttrValueMapper baseAttrValueMapper;
    @Resource
    private BaseCategory1Mapper baseCategory1Mapper;
    @Resource
    private BaseCategory2Mapper baseCategory2Mapper;
    @Resource
    private BaseCategory3Mapper baseCategory3Mapper;
    @Resource
    private SpuInfoMapper spuInfoMapper;


    @Override
    public List<BaseCategory1> getBaseCategory1() {
        return baseCategory1Mapper.selectList(null);
    }

    @Override
    public List<BaseCategory2> getBaseCategory2ById(Long category1Id) {
        return baseCategory2Mapper.selectList(new QueryWrapper<BaseCategory2>().eq("category1_id",category1Id));
    }

    @Override
    public List<BaseCategory3> getBaseCategory3ById(Long category2Id) {
        return baseCategory3Mapper.selectList(new QueryWrapper<BaseCategory3>().eq("category2_id",category2Id));
    }

    @Override
    public List<BaseAttrInfo> getBaseAttrInfoList(Long category1Id, Long category2Id, Long category3Id) {
        return baseAttrInfoMapper.selectBaseAttrInfoList(category1Id,category2Id,category3Id);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
//判断id是否为空，空就是添加非空就是修改
        if (baseAttrInfo.getId()!=null){
            baseAttrInfoMapper.updateById(baseAttrInfo);
        }else {
            baseAttrInfoMapper.insert(baseAttrInfo);
        }

        //先删除在新增 base_attr_value
        baseAttrValueMapper.delete(new QueryWrapper<BaseAttrValue>().eq("attr_id",baseAttrInfo.getId()));


//   先获取平台属性值集合
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
//        判断集合不为空
        if (!CollectionUtils.isEmpty(attrValueList)){
//            循环遍历当前集合，插入到平台属性表中
            for(BaseAttrValue baseAttrValue : attrValueList){
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {

        return baseAttrValueMapper.selectList(new QueryWrapper<BaseAttrValue>()
                .eq("attr_id",attrId));
    }

    @Override
    public BaseAttrInfo getbaseAttrInfo(Long attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectById(attrId);
        if (baseAttrInfo!=null){
            //  获取平台属性值集合数据
            List<BaseAttrValue> attrValueList = getAttrValueList(attrId);
            if (!CollectionUtils.isEmpty(attrValueList)){
                baseAttrInfo.setAttrValueList(attrValueList);
            }
        }
        //  返回数据
        return baseAttrInfo;
    }
    /**
     * 根据三级分类id分页查询商品表，
     * @param spuInfoPage 分页条件 page和limit
     * @param spuInfo   三级分类id
     * @return
     */
    @Override
    public IPage<SpuInfo> getSpuInfoList(Page<SpuInfo> spuInfoPage, SpuInfo spuInfo) {
        QueryWrapper<SpuInfo> spuInfoQueryWrapper = new QueryWrapper<>();
        spuInfoQueryWrapper.eq("category3_id",spuInfo.getCategory3Id());
//        设置一个排序
        spuInfoQueryWrapper.orderByDesc("id");//倒叙
        return spuInfoMapper.selectPage(spuInfoPage,spuInfoQueryWrapper);
    }


}
