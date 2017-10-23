package com.taotao.portal.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
@Service
public class StaticPageServiceImpl implements StaticPageService{
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfig freeMarkerConfig;
	@Value("${STSTIC_PAGE_PATH}")
	private String STSTIC_PAGE_PATH;
	@Override
	public TaotaoResult genItemHtml(Long itemId) throws Exception {
		TbItem tbItem = itemService.getTbItemById(itemId);
		String itemDesc = itemService.getTbDescById(itemId);
		String itemParamItem = itemService.getTbItemParamItemById(itemId);
		Configuration configuration = freeMarkerConfig.getConfiguration();
		//获得模板
		Template template = configuration.getTemplate("item.ftl");
		//创建数据集
		Map map = new HashMap<>();
		map.put("item", tbItem);
		map.put("itemDesc", itemDesc);
		map.put("itemParam", itemParamItem);
		
		//指定生成文件的位置
		Writer out = new FileWriter(new File(STSTIC_PAGE_PATH+itemId+".html"));
		//生成静态文件
		template.process(map, out);
		out.flush();
		out.close();
		return TaotaoResult.ok();
	}
	
}
