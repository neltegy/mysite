<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<!-- /header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		
		<!-- /navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="wrapper">
			<div id="content">
				<div id="guestbook">
					
					<form action="/mysite/gb" method="post">
						
						<table>
							<tr>
								<td>이름</td><td><input type="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" id="content"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input type="submit" VALUE=" 확인 " /></td>
							</tr>
						</table>
						<input type="hidden" name="a" value="add">
					</form>
					<c:forEach items="${list }" var="gblist" varStatus="status">
						<ul>
						<li>
							<table>
								<tr>
									<td>${gblist.no }</td>
									<td>${gblist.name }</td>
									<td>${gblist.reg_date }</td>
									<td><a href="/mysite/gb?a=deleteform&no=${gblist.no }">삭제</a></td>
								</tr>
								<tr>
									<td colspan=4>
										${gblist.content }
									</td>
								</tr>
							</table>
							<br>
						</li>
					</ul>
					</c:forEach>
					
				</div><!-- /guestbook -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<!-- /footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div> <!-- /container -->

</body>
</html>