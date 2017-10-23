package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerTest {
	public class Student{
		private int id;
		private String name;
		private String address;
		public Student(int id, String name, String address) {
			super();
			this.id = id;
			this.name = name;
			this.address = address;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		
	}
	@Test
	public void testFreeMarker() throws IOException, TemplateException{
		//创建Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置configuration路径
		configuration.setDirectoryForTemplateLoading(new File("E:\\web_Workspace\\taotao-portal\\src\\main\\webapp\\WEB-INF\\ftl"));
		//设置编码
		configuration.setDefaultEncoding("utf-8");
		//获得模板对象 
		Template template  = configuration.getTemplate("student.ftl");
		List<Student>list = new ArrayList<>();
		list.add(new Student(1, "张三1", "北京1"));
		list.add(new Student(1, "张三2", "北京2"));
		list.add(new Student(1, "张三3", "北京3"));
		list.add(new Student(1, "张三4", "北京4"));
		list.add(new Student(1, "张三5", "北京5"));
		//创建模板对象数据集
		Map map = new HashMap<>();
		map.put("title", "hello");
		map.put("hello", "first.ftl中的hello");
		map.put("student", new Student(1, "张三", "北京"));
		map.put("students", list);
		map.put("curdate", new Date());
		//设置生成文件要存放的位置
		Writer out = new FileWriter("D:\\test\\student.html");
		//调用方法生成模板
		template.process(map, out);
		out.flush();
		out.close();
	}
}
