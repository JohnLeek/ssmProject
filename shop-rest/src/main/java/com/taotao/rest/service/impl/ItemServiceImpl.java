package com.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ItemService;

/**
 * <p>
 * Description:商品详细信息，购买页面
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月17日 下午4:30:24
 */

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${ITEM_BASE_INFO_KEY}")
	private String ITEM_BASE_INFO_KEY;
	@Value("${TIME_EXPIRE_SECOND}")
	private Integer TIME_EXPIRE_SECOND;
	@Value("${ITEM_DESC_KEY}")
	private String ITEM_DESC_KEY;
	@Value("${ITEM_PARAM_KEY}")
	private String ITEM_PARAM_KEY;

	@Override
	// 根据id查询商品的详细信息
	public TbItem getItemById(Long id) {
		// 对redis，如果redis有缓存下次再查询就查redis没有就先查数据库然后保存到redis
		// 查redis
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_BASE_INFO_KEY);
			// 判断是否有数据
			if (!StringUtils.isEmpty(json)) {
				return JsonUtils.jsonToPojo(json, TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem item = itemMapper.selectByPrimaryKey(id);
		// 存redis
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_BASE_INFO_KEY, JsonUtils.objectToJson(item));
			// 设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_BASE_INFO_KEY, TIME_EXPIRE_SECOND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public TbItemDesc getItemDescById(Long id) {
		// 先查询redis 有就直接返回 没有就查询数据库把值存入redis
		// 查redis
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_DESC_KEY);
			if (!StringUtils.isEmpty(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询数据库
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		// 存redis
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_DESC_KEY, JsonUtils.objectToJson(itemDesc));
			// 设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_DESC_KEY, TIME_EXPIRE_SECOND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

	@Override
	public TbItemParamItem getItemParamItem(Long id) {
		// 先查询redis 有就直接返回 没有就查询数据库把值存入redis
		// 查redis
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_PARAM_KEY);
			if (!StringUtils.isEmpty(json)) {
				TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return itemParamItem;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询数据库
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			//存redis
			try {
				jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_PARAM_KEY, JsonUtils.objectToJson(list.get(0)));
				// 设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":" + ITEM_PARAM_KEY, TIME_EXPIRE_SECOND);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list.get(0);
		}
		return null;
	}

}
