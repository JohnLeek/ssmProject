<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${title!"hello"}</title>
</head>

<body>
	<#include "first.ftl">
	<label>学号：</label>${student.id}<br>
	<label>姓名：</label>${student.name}<br>
	<label>住址：</label>${student.address}<br>
	学生列表：
	<table border="1">
	<#list students as s>
		<#if s_index % 2 == 0>
		<tr style="color:red">
		<#else>
		<tr>
		</#if>
			<td>${s_index}</td>
			<td>${s.id}</td>
			<td>${s.name}</td>
			<td>${s.address}</td>
		</tr>
	</#list>
	</table>
	<br>
	<#if curdate ??>
	当前日期：${curdate?string("yyyy/MM/dd HH:mm:ss")}
	<#else>
		curdate 为空
	</#if>
		
</body>
</html>
