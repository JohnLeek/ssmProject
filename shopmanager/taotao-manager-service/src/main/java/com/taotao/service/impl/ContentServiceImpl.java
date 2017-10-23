package com.taotao.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
/**<p>Description：内容管理service</p>
 *@author: Li Yinqiang
 *@date 2017年10月5日 下午1:24:26  
 */
 
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Override
	//插入数据
	public TaotaoResult insertContentService(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		//插入数据
		contentMapper.insert(tbContent);
		return TaotaoResult.ok();
	}

}
