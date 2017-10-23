package com.taotao.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.ResponseResult;

public class HttpClientTest {
	@Test
	public void testHttpGet() throws ClientProtocolException, IOException{
		//创建一个CloseableHttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个HttpGet对象 指定一个url
		HttpGet httpGet = new HttpGet("http://www.itheima.com");
		//执行请求
		CloseableHttpResponse response = httpClient.execute(httpGet);
		//接收返回结果HttpEntity对象
		HttpEntity entity = response.getEntity();
		//取响应内容
		String html = EntityUtils.toString(entity);
		System.out.println(html);
		//关闭   7 
		httpClient.close();
		response.close();
	}
	@Test
	public void testPost() throws ClientProtocolException, IOException{
		//创建一个CloseableHttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个HttpPost对象 指定一个url
		HttpPost post = new HttpPost("http://localhost:8082/posttest.html");
		//创建一个list模拟表单
		List<NameValuePair> formList = new ArrayList<>();
		formList.add(new BasicNameValuePair("name", "张三"));
		formList.add(new BasicNameValuePair("pass", "1234"));
		//包装到StringEntity
		StringEntity entity = new UrlEncodedFormEntity(formList,"utf-8");
		post.setEntity(entity);
		//执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		//接收返回结果
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity);
		System.out.println(result);
		//关闭流
		response.close();
		httpClient.close();
	}
	@Test
	public void testCode() throws ClientProtocolException, IOException{
		//创建一个CloseableHttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建一个HttpPost对象 指定一个url
		HttpPost post = new HttpPost("http://www.runoob.com/api/compile.php");
		//创建一个list模拟表单
		List<NameValuePair> formList = new ArrayList<>();
		String codeResult = "hello world";
		String code = "public class HelloWorld {public static void main(String []args) { System.out.println(123456); }}";
		formList.add(new BasicNameValuePair("code", code));
		formList.add(new BasicNameValuePair("language", "8"));
		//包装到StringEntity
		StringEntity entity = new UrlEncodedFormEntity(formList,"utf-8");
		post.setEntity(entity);
		//执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		//接收返回结果
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity);
		ResponseResult responseResult = JsonUtils.jsonToPojo(result, ResponseResult.class);
		System.out.println(responseResult.getOutput());
		System.out.println(responseResult.getCode());
		System.out.println(responseResult.getLangid());
//		System.out.println(TaotaoResult.format(result));
		//关闭流
		response.close();
		httpClient.close();
	}
}
