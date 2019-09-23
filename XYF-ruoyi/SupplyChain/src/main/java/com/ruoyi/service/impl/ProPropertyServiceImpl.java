package com.ruoyi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.domain.GroupNoDTO;
import com.ruoyi.domain.ProPropertyValue;
import com.ruoyi.mapper.ProPropertyValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProPropertyMapper;
import com.ruoyi.domain.ProProperty;
import com.ruoyi.service.IProPropertyService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品属性 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProPropertyServiceImpl implements IProPropertyService
{
	@Autowired
	private ProPropertyMapper proPropertyMapper;
	@Autowired
    private ProPropertyValueMapper valueMapper;

	/**
     * 查询商品属性信息
     *
     * @param propertyId 商品属性ID
     * @return 商品属性信息
     */
    @Override
	public ProProperty selectProPropertyById(Long propertyId)
	{
	    return proPropertyMapper.selectProPropertyById(propertyId);
	}

	/**
     * 查询商品属性列表
     *
     * @param proProperty 商品属性信息
     * @return 商品属性集合
     */
	@Override
	public List<ProProperty> selectProPropertyList(ProProperty proProperty)
	{
	    return proPropertyMapper.selectProPropertyList(proProperty);
	}

    /**
     * 新增商品属性
     *
     * @param proProperty 商品属性信息
     * @return 结果
     */
	@Override
	public int insertProProperty(ProProperty proProperty)
	{
	    return proPropertyMapper.insertProProperty(proProperty);
	}

	/**
     * 修改商品属性
     *
     * @param proProperty 商品属性信息
     * @return 结果
     */
	@Override
	public int updateProProperty(ProProperty proProperty)
	{
	    return proPropertyMapper.updateProProperty(proProperty);
	}

	/**
     * 删除商品属性对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProPropertyByIds(String ids)
	{
		return proPropertyMapper.deleteProPropertyByIds(Convert.toStrArray(ids));
	}

//    /**
//     * 根据商品id查询属性和属性值
//     * @param proId
//     * @return
//     */
//    @Override
//    public List<GroupNoDTO> queryGroupNoDTOByProId(Long proId) {
//        List<List<GroupNoDTO>> list=new ArrayList<>();
//        //根据商品id查询属性list
//        List<ProProperty> proProperties=proPropertyMapper.selectProPropertyByProId(proId);
//        //遍历属性list
//        for (ProProperty proProperty:proProperties
//             ) {
//            String pId=proProperty.getPropertyId().toString();
//            String pName=proProperty.getPropertyName();
//            //根据属性id查询属性值list
//            List<ProPropertyValue> values=valueMapper.selectValueByPropertyId(proProperty.getPropertyId());
//            //遍历属性值list
//            List<GroupNoDTO> list1=new ArrayList<>();
//            for (ProPropertyValue value:values
//            ) {
//                pId=pId+":"+value.getValueId()+",";
//                pName=pName+":"+value.getValueText()+",";
//                GroupNoDTO groupNoDTO=new GroupNoDTO(pId,pName);
//                list1.add(groupNoDTO);
//            }
//            list.add(list1);
//        }
//        for (List<GroupNoDTO> groupNoDTOs:list
//             ) {
//
//        }
//
//
//    }
//    /**
//     * 根据商品id查询属性和属性值
//     * @param proId
//     * @return
//     */
//    @Override
//    public List<String> queryGroupNoByProId(Long proId) {
//        List<ProProperty> proProperties = proPropertyMapper.selectProPropertyByProId(proId);
//        List<List<String>> list = new ArrayList<>();
//        //遍历属性list
//        for (ProProperty proProperty : proProperties
//        ) {
//            List<ProPropertyValue> values = valueMapper.selectValueByPropertyId(proProperty.getPropertyId());
//            List<String> groupNos = new ArrayList<>();
//            for (ProPropertyValue value : values
//            ) {
//                String groupNo = proProperty.getPropertyId() + ":" + value.getValueId();
//                groupNos.add(groupNo);
//            }
//            list.add(groupNos);
//        }
//        for (List<String> ss : list
//        ) {
//
//        }
//    }
}
