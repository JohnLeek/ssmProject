package com.taotao.search.service.impl;


import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.service.ItemService;
/**<p>Description:商品导入到索引库</p>
 *@author: Li Yinqiang
 *@date 2017年10月14日 下午2:45:01  
 */
 
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ItemMapper ItemMapper;
	@Override
	public TaotaoResult importItems() throws SolrServerException, IOException {
		List<SearchItem> itemList = ItemMapper.getItemList();
		//遍历结果 将其放入SolrInputDocument中
		for(SearchItem item : itemList){
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			document.setField("item_desc", item.getItem_desc());
			solrServer.add(document);
		}
		solrServer.commit();
		return TaotaoResult.ok();
	}

}
