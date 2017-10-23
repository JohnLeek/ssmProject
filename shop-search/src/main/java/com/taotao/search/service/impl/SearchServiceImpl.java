package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws SolrServerException {
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 设置分页条件
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 设置默认搜索域
		query.set("df", "item_title");
		// 设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		query.setHighlightSimplePost("</font>");
		// 执行查询
		SearchResult searchResult = searchDao.search(query);
		// 得到总行数
		Long recordCount = searchResult.getRecordConunt();
		int pageCount = (int) (recordCount / rows);
		if (recordCount % rows > 0) {
			pageCount++;
		}
		searchResult.setCurPage(page);
		searchResult.setPageCount(pageCount);
		return searchResult;
	}

}
