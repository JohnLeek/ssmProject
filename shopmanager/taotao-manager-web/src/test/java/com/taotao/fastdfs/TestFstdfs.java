package com.taotao.fastdfs;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class TestFstdfs {
	@Test
	public void testUpload() throws IOException, MyException {
		// 初始化全局配置 加载配置文件
		ClientGlobal.init(
				"E:\\web_Workspace\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
		// 创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		// 创建一个TrackServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		// 声明一个StorageServer对象为null
		StorageServer storageServer = null;
		// 获得StorageClient对象
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 调用StorageClient上传图片
		String str = "D:\\1.jpg";
		String location = str.replace("\\\\", "/");
		String[] strings = storageClient.upload_file(location, "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	@Test
	public void testFastDFSClient() throws Exception{
		FastDFSClient client = new FastDFSClient("E:\\web_Workspace\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
		String upload = client.uploadFile("D:\\2.jpg", "jpg");
		System.out.println(upload); 
	}
}
