package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

/**<p>Description: 图片上传的方法</p>
 *@author: Li Yinqiang
 *@date 2017年9月23日 下午7:02:24  
 */
 @Controller
public class PictureController {
	 @Autowired
	 private PictureService pictureService;
	 @RequestMapping("/pic/upload")
	 //解决火狐浏览器的兼容问题返回字符串而不是json
	 @ResponseBody
	 public String uploadFile(MultipartFile uploadFile){
		PictureResult result = pictureService.uploadPic(uploadFile);
		String json = JsonUtils.objectToJson(result);
		return json;
		 
	 }
}
