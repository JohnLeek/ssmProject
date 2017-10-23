package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.HttpClientUtil;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BAR_URL}")
	private String REST_ITEM_BAR_URL;
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;

	@Override
	// 取商品的基本信息
	public TbItem getTbItemById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BAR_URL + itemId + "");
		// 转化成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, PortalItem.class);
		TbItem item = (TbItem) taotaoResult.getData();
		return item;
	}

	// 取商品的描述信息
	public String getTbDescById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId + "");
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
		TbItemDesc itemDesc = (TbItemDesc) result.getData();
		return itemDesc.getItemDesc();

	}

	// 取商品的规格参数
	@Override
	public String getTbItemParamItemById(Long itemId) {
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId + "");
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
		TbItemParamItem itemParamItem = (TbItemParamItem) result.getData();
		String paramJson = itemParamItem.getParamData();
		List<Map> mapList = JsonUtils.jsonToList(paramJson, Map.class);
		// 遍历list生成html
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			// 取规格项
			List<Map> mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();

	}
}
