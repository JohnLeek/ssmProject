package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

/**
 * <p>
 * Description:solr查询dao
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月15日 下午4:10:08
 */
@Repository
public class SearchDaoImpl implements SearchDao {
	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult search(SolrQuery query) throws SolrServerException {
		// 执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		List<SearchItem> itemList = new ArrayList<>();
		for(SolrDocument solrDocument:solrDocumentList){
			SearchItem item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((Long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if(list != null && list.size()>0){
				//取高亮后的结果
				itemTitle = list.get(0);
			}else{
				itemTitle = (String) solrDocument.get("item_title");
			}
			item.setTitle(itemTitle);
			//添加到列表
			itemList.add(item);
		}
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecordConunt(solrDocumentList.getNumFound());
		return result;
	}

}
