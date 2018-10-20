<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	查看<a href="<%=request.getContextPath()%>/user/get/name" target="_blank">2号</a>用户名  
    <br/>  
    <br/>  
        更新<a href="<%=request.getContextPath()%>/user/update/name" target="_blank">3号</a>用户名  
    <br/>  
    <br/>  
         移除<a href="<%=request.getContextPath()%>/user/removeAll" target="_blank">所有</a>用户名  
     <br/>
</body>
</html>