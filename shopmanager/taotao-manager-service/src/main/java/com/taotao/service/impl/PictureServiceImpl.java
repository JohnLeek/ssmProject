package com.taotao.service.impl;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;

/**
 * <p>
 * Description:商品图片上传的方法
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年9月23日 下午6:34:02
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;

	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult reslut = new PictureResult();
		// 判断图片是否为空
		if (picFile.isEmpty()) {
			reslut.setError(1);
			reslut.setMessage("图片为空");
			return reslut;
		}
		// 上传到图片服务器
		try {
			// 取图片扩展名
			String originalFilename = picFile.getOriginalFilename();
			// 去掉.
			String extName = originalFilename.substring(originalFilename.indexOf(".") + 1);
			FastDFSClient client = new FastDFSClient(
					"E:\\web_Workspace\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
			String url = client.uploadFile(picFile.getBytes(), extName);
			// 设置result将url返回 
			url = IMAGE_SERVER_BASE_URL + url;
			System.out.println(url);
			reslut.setError(0);
			reslut.setUrl(url);
			reslut.setMessage("上传成功");
		} catch (Exception e) {
			reslut.setError(1);
			reslut.setMessage("上传失败");
			e.printStackTrace();
		}
		return reslut;
	}

}
