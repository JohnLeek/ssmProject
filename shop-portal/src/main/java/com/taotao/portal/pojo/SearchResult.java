package com.taotao.portal.pojo;

import java.util.List;

public class SearchResult {
	private List<SearchItem> itemList;
	private Long recordConunt;
	private int pageCount;
	private int curPage;
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Long getRecordConunt() {
		return recordConunt;
	}
	public void setRecordConunt(Long recordConunt) {
		this.recordConunt = recordConunt;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
	
}
