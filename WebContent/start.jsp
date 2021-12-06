<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>샵 홈페이지 메인</title>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<link rel="stylesheet" href="style.css">
		<style>
			#ponup
			{
				position:absolute;
				left:100px;
				top:0;
				display:none;
			}
			#ponup > img
			{
				width:400px;
				height:660px;
			}
			#ponup > div
			{
				margin-top:-4px;
				background-color:lightblue;	
			}
			#ponup > div > a
			{
				color:#2982d4;
			}
			
			#ponup > div > a:last-child
			{
				float:right;
				color:white;
				background-color:#2982d4;
			}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		
		
		
		<div id="body">
			<img id="image1" src=".${mainImage[0] }">
			<img id="image2" src=".${mainImage[1] }" style="display:none;">
			<img id="image3" src=".${mainImage[2] }" style="display:none;">
			<a href="#"><span id="leftBtn">◁</span></a>
			<a href="#"><span id="rightBtn">▷</span></a>
			
		</div>
		<script>
			var i=1002;
			var j=0;
			
			
			
			$("#leftBtn").click(function(){
				i--;
				rightHide();
				rightFadeIn();
			});
			

			$("#rightBtn").click(function(){
				i++;
				rightHide();
				rightFadeIn();
				
			});
			$(document).ready(function(){
				
				setInterval("auto()", 5000);
				

			});
			
			function auto()
			{
				j++;
				if(j==9)
				{
					i=1002;	
				}
				i++;
				rightHide();
				rightFadeIn();

			}
			
			function rightHide()
			{
				if(i%3==0)
				{
					$("#image1").hide();
					$("#image2").hide();
					$("#image3").hide();
				}
				else if(i%3==1)
				{
					$("#image2").hide();
					$("#image3").hide();
					$("#image1").hide();
				}
				else if(i%3==2)
				{
					$("#image3").hide();
					$("#image2").hide();
					$("#image1").hide();
				}
			}
			function rightFadeIn()
			{
				if(i%3==0)
				{
					$("#image1").fadeIn();
				}
				else if(i%3==1)
				{
					$("#image2").fadeIn();
				}
				else if(i%3==2)
				{
					$("#image3").fadeIn();
				}
			}

		</script>
		
		
		<!-- 판업창 -->
		<c:if test="${notice != null }">
			<div id="ponup">
				<img src=".${notice }">
				<div>
					<a href="#" onclick="todaycloseWin()">오늘 하루동안 열지 않기</a>
					<a href="#" onclick="closeWin()">닫기</a>
				</div>
			</div>
		</c:if>

		<script>
			var ponup=document.getElementById("ponup");
		
			function setCookie(name, value, expirehours)
			{
				var todayDate=new Date();
				todayDate.setHours(todayDate.getHours()+expirehours);
				document.cookie = name +"="+escape(value)+"; path=/; expires="+todayDate.toGMTString()+";";
			}
			
			function todaycloseWin()
			{
				setCookie("ncookie", "done", 24);
				ponup.style.display="none";
			}
			
			function closeWin()
			{
				ponup.style.display="none";
			}
			
			cookiedata=document.cookie;
			if(cookiedata.indexOf("ncookie=done") <0)
			{
				ponup.style.display = "block";
			}
			else
			{
				ponup.style.display="none";
			}
		</script>
		<!-- 판업창 끝-->
		
		
		<div id="wrap">
			<c:choose>
				<c:when test="${search ==null }">
					<h1 style="text-align:center;">신규 상품</h1>
				</c:when>
				<c:otherwise>
					<h1 style="text-align:center;">${search }</h1>
				</c:otherwise>
			</c:choose>
			
			<div>
				<c:forEach items="${productList }" var="product">
					<div class="pro">
						<a href="startEachProduct.do?num=${product.getNum() }">
							<c:choose>
								<c:when test="${product.getPictureurl()== '/files/null'}">
									이미지가 없습니다.
								</c:when>
								<c:otherwise>
									<img id="productImg" src=".${product.getPictureurl() }">
								</c:otherwise>
							</c:choose>		
							<br>
							${product.getName() }
							<br>
							<fmt:formatNumber type="number" value="${product.getPrice() }"/>원		
							<br>
							<c:choose>
								<c:when test="${product.getQuantity() <1 }">
									<span class="sell">품절</span>
								</c:when>
								<c:otherwise>
									<span class="soldOut">판매</span>
								</c:otherwise>
							</c:choose>
						</a>	
					</div>
				</c:forEach>
			</div>
			
			
			<ul id="pageBtn">
				<c:if test="${startPage != 1 }">
					<li><a href="start.do?startPage=${1 }&&lastPage=20">처음</a></li>
					<li><a href="start.do?startPage=${startPage-1 }&&lastPage=20">이전 페이지</a></li>
				</c:if>
				<c:forEach begin="1" end="${pageBtn }" var="i">
					<c:choose>
						<c:when test="${startPage eq i }">
							<li><a>현재 ${i }</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="start.do?startPage=${i }&&lastPage=20">${i }</a></li>	
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${startPage < pageBtn }">
					<li><a href="start.do?startPage=${startPage+1 }&&lastPage=20">다음 페이지</a></li>
					<li><a href="start.do?startPage=${pageBtn }&&lastPage=20">끝</a></li>
				</c:if>
			</ul>
		</div>
		
		
		<jsp:include page="footer.jsp"/>

	</body>
</html>




