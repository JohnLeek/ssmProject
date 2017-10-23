package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;

	@Override
	public List<TbContent> getContentList(Long cid) {
		// 查询数据库前先 查询缓存 如果有就直接返回
		try {
			String json = jedisClient.hget(REDIS_CONTENT_KEY, cid + "");
			if (!StringUtils.isBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		// 添加到缓存
		try {
			// 为了规范key可以使用hash
			// 定义一个保存内容的key，hash中每个项就是cid
			// value是list，需要把list转换成json数据。
			jedisClient.hset(REDIS_CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public TaotaoResult syncContent(Long cid) {
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaotaoResult.ok();
	}

}
