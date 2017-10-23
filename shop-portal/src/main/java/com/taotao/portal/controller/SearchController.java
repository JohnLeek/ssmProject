package com.taotao.portal.controller;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@RequestMapping("/search")
	public String search(@RequestParam("q") String keyword, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "60") Integer rows, Model model) {
		// 将keyword的编码改成utf-8
		try {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
			
		} catch (UnsupportedEncodingException e) {
			keyword = "";
			e.printStackTrace();
		}
		SearchResult search = searchService.search(keyword, page, rows);
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages", search.getPageCount());
		model.addAttribute("itemList", search.getItemList());
		model.addAttribute("page", search.getCurPage());
		return "search";

	}
}
