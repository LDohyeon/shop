<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>샵 홈페이지 메인 상품 개별 조회</title>
		<link rel="stylesheet" href="style.css">
		<style>
			table, th, td
			{
				border:1px solid gray;
				border-collapse: collapse;
			}
			th
			{
			    width: 100px;
			}
			table
			{
				width: 459px;
			}

			#productContent
			{
				background-color:lightgray;
			}
			.btn
			{
				background-color:#d40c0c;
				color:white;
			}
			#content
			{
				height:220px;
				padding-top:190px;
			}
			#sold_out
			{
				background-color:lightgray;
				color:red;
				display:inline-block;
			}
			#body
			{
				width:836px;
				margin:0 auto;
			}
		</style>
	</head>
	<body>

		<jsp:include page="header.jsp"/>
		<br>
		<br>
		<div id="wrap">
			<h1>상품 개별 조회</h1>
				<c:choose>
					<c:when test="${pDTO.getQuantity() <1 }">
						<div id="sold_out">상품이 품절되었습니다.</div>
					</c:when>
					<c:otherwise>
						<input class="btn" type="button" value="구매">
						<input class="btn" name="cart" type="button" value="장바구니">
					</c:otherwise>
				</c:choose>
				
				<!-- 장바구니에 담기 -->
				<form id="frm" method="post" action="cartInsert.do">
					<c:if test="${pDTO.getQuantity() >0 }">
						<input type="number" name="quantity" min="0" max="999" value="1">개
					</c:if>
					<input type="hidden" name="product_num" value="${pDTO.getNum() }">
					<input type="hidden" name="product_name" value="${pDTO.getName() }">
					<input type="hidden" name="price" value="${pDTO.getPrice() }">
					<input type="hidden" name="maker" value="${pDTO.getMaker() }">
					<input type="hidden" name="category" value="${pDTO.getCategory() }">
					<input type="hidden" name="pictureurl" value="${pDTO.getPictureurl() }">
					<input type="hidden" name="description" value="${pDTO.getDescription() }">
					<input type="hidden" name="point" value="${pDTO.getPoint() }">
					<input type="hidden" name="product_id" value="${pDTO.getId() }">
					
				
					
					<c:if test="${loginUser.getId() == null }">
						<input type="hidden" id="id" name="id" value="@">
					</c:if>
					<c:if test="${loginUser.getId() != null }">
						<input type="hidden" id="id" name="id" value="${loginUser.getId() }">
					</c:if>	
					
				</form>
				<!-- 장바구니에 담기 -->
				
					<script>
						//장바구니 담기 시작
						var id=document.getElementById("id");
						var frm=document.getElementById("frm");
						var btn=document.getElementsByClassName("btn");
						btn[1].addEventListener("click", function(){
							if(id.value=="@")
							{
								alert("로그인을 하고 이용해주세요");
							}
							else
							{
								frm.submit();
							}
						});
					
						//장바구니 담기 끝
		
						//즉시 개별 구매 스트립트 시작
						
						btn[0].addEventListener("click", function(){					
							if(id.value=="@")
							{
								alert("로그인을 하고 이용해주세요");
							}
							else
							{
								frm.action="payEachInsert.do";
								frm.submit();
							}
						});
						//즉시 개별 구매 스트립트 끝			
					</script>
					
					




				
					<table>
						<tr>
							<td style="width:500px;">
								<c:choose>
									<c:when test="${pDTO.getPictureurl()== '/files/null'}">
										이미지가 없습니다.
									</c:when>
									<c:otherwise>
										<img src=".${pDTO.getPictureurl() }">

									</c:otherwise>
								</c:choose>								
							</td>
							<td>
								<table id="productContent">
									<tr>
										<th>상품명</th>
										<td style="width:500px;">${pDTO.getName() }</td>
									</tr>
									<tr>
										<th>상품가격</th>
										<td>${pDTO.getPrice() }원</td>
									</tr>
									<tr>
										<th>메이커</th>
										<td>${pDTO.getMaker() }</td>
									</tr>
									<tr>
										<th>카테고리</th>
										<td>${pDTO.getCategory() }</td>
									</tr>
									<tr>
										<th>상품 설명</th>
										<td><div id="content">${pDTO.getDescription() }</div></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

					<br>
				
			
		</div>
		
		<jsp:include page="./footer.jsp"/>
	</body>
</html>


