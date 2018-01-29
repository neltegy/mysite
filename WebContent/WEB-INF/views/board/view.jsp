<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">

		<!-- /header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		
		<!-- /navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<!-- ----------------------- -->
					<tr>
						<td class="label">제목</td>
						<td>${boardvo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${boardvo.content }
							</div>
						</td>
					</tr>
					<!-- ----------------------------- -->
				</table>
				
				<c:choose>
					<c:when test="${empty authUser }"> <!-- authUser가 비어있음경우 true가 나옴 -->
						<div class="bottom">
						<a href="/mysite/board?a=list">글목록</a>
						</div>
					</c:when>
					<c:otherwise> <!-- 로그인한 사용자들 -->
						<c:choose>
							<c:when test="${authUser.no eq boardvo.user_no }"> 
								<div class="bottom">
								<a href="/mysite/board?a=list">글목록</a>
								<a href="/mysite/board?a=modifyform&no=${boardno }">글수정</a>
								</div>
							</c:when>
							<c:otherwise>
								<div class="bottom">
								<a href="/mysite/board?a=list">글목록</a>
								</div>
							</c:otherwise>
						</c:choose>
						
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>

		<!-- /footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div>
</body>
</html>