<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>导入excel</title>
<script src="http://res.duandai.com/static/js/jquery.min.js"></script>
</head>
<body>
<form action="/modules/manage/division/import.htm" method="post" id="ii" name="ii" enctype="multipart/form-data">
	<table>
		<tr>
			<td>重新分案导入</td>
			<td><input type="file" id="file" name="file" required/></td>
			<td><input type="submit" value="导入"/></td>
		</tr>
	</table>
</form>

</body>
</html>