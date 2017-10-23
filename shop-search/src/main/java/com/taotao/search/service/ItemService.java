package com.taotao.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {
	TaotaoResult importItems() throws SolrServerException, IOException;
}
