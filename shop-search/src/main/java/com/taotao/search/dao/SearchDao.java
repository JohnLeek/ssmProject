package com.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import com.taotao.search.pojo.SearchResult;

public interface SearchDao {
	SearchResult search (SolrQuery query) throws SolrServerException ;
}
