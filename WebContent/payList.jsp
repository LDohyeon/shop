<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>결제 내역 보기</title>
		<link rel="stylesheet" href="style.css">

		<style>
			#wrap
			{
				border:1px solid lightgray;
				margin: 25px auto;
			}
			#wrap > h2
			{
				padding-left:20px;
				padding-top:20px;
			}
			#wrap2
			{
				width: 96%;
    			margin: 2%;
				border:1px solid lightgray;
			}
			#wrap2 div:first-child
			{
				border:1px solid lightgray;
				margin-bottom:50px;
			}
			#wrap2 div ul:first-child
			{
				background-color:lightgray;
			}
			#wrap2 div ul li
			{
				padding:10px;
			}

			#wrap2 div:last-child div
			{
				display:inline-block;
			}
			table
			{
				width: 100%;
    			margin-top: 30px;
    			margin-bottom: 60px;
    			border:1px solid lightgray;
    			border-collapse:collapse;
			}
			th, td
			{
				border:1px solid lightgray;
				text-align:center;
			}
			
		</style>

	</head>
	<body>
		<jsp:include page="header.jsp"/>
		
		
		
		<div id="wrap">
			<h2> 마이페이지</h2>
			
			<div id="wrap2">
				<div>
					<ul>
						<li>&nbsp;&nbsp;회원님의 아이디 : ${loginUser.getId() }</li>
						<li>&nbsp;&nbsp;별명 : ${loginUser.getName() }</li>	
					</ul>
					<ul>
						<li>&nbsp;&nbsp;포인트 : 
							<fmt:formatNumber type="number" value="${pointHap }"/>p</li>
						<li>&nbsp;&nbsp;총 결제금액 : 
							<fmt:formatNumber type="number" value="${priceHap }"/>원</li>
					</ul>
				</div>
				
				<p><b>&nbsp;&nbsp;최근 주문 내역</b></p>

				<table>
					<tr>
						<th style="width:100px; height:100px;">이미지</th>
						<th>상품명</th>
						<th>주문일시</th>
						<th>주문수량</th>
						<th>상품가격</th>
						<th>포인트</th>
						<th>구매한 시간</th>
						<th>구매 여부</th>
					</tr>
				<c:if test="${payPage ==0 }">
					<tr>
						<td colspan='8'>구매한 상품이 없습니다.</td>
					</tr>
				</c:if>
					<c:forEach items="${payList }" var="pay">
						<tr>
							<td><img style="width:100px; height:100px;" src=".${pay.getPictureurl() }"></td>
							<td>${pay.getProduct_name() }</td>
							<td>${pay.getCreate_date() }</td>
							<td>${pay.getPurchase_quantity() }개</td>
							<td>
								<fmt:formatNumber type="number" value="${pay.getPrice() }"/>원
							</td>
							<td>
								<fmt:formatNumber type="number" value="${pay.getPoint() }"/>p
							</td>
							<td>${pay.getCreate_date() }</td>
							<c:choose>
								<c:when test="${pay.getPay_delete() ==0 }">
									<td>결제완료</td>
								</c:when>
								<c:otherwise>
									<td>환불완료 ${pay.getDelete_date() }</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				
				
				
				
				
				
				<ul>
					<c:if test="${startPage != 1 }">
						<li><a href="payList.do?id=${loginUser.id }&&startPage=${1 }&&listPage=20">맨 처음</a></li>
						<li><a href="payList.do?id=${loginUser.id }&&startPage=${startPage-1 }&&listPage=20">이전 페이지</a></li>
					</c:if>
					<c:forEach begin="1" end="${payPage }" var="i">
						<c:choose>
							<c:when test="${startPage ==i }">
								<li>현재 ${i }</li>
							</c:when>
							<c:otherwise>
								<li>
									<a href="payList.do?id=${loginUser.id }&&startPage=${i }&&listPage=20">${i }</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${startPage <payPage }">
						<li><a href="payList.do?id=${loginUser.id }&&startPage=${startPage+1 }&&listPage=20">다음 페이지</a></li>
						<li><a href="payList.do?id=${loginUser.id }&&startPage=${payPage }&&listPage=20">끝</a></li>
					</c:if>
				</ul>
				
				
				
			</div>
		</div>
		
		
		<jsp:include page="footer.jsp"/>

	</body>
</html>