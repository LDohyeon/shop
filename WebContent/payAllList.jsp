<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>연간 매출 검색</title>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
		<link rel="stylesheet" href="style.css">
		<style>
			*
			{
				margin:0;
				padding:0;
				
			}
			#payWrap
			{
				width:1168px;
				
				margin:0 auto;
				border:1px solid lightgray;
			}
			#payWrap > div
			{
				margin:30px;
			}

			#sub
			{
				text-align:center;
				display: inline-block;
				width:80px;
				height:30px;
				line-height: 30px;
				background-color:#d40c0c;
				color:white;
				margin-left:10px;
			}
			#footer3
			{
				margin-top:110px;
			}
		</style>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		
		
		<br>
		<br>
		<div id="payWrap">
			<div>
				<h1>연간 매출 검색</h1>
				
						<span>연도 검색 : </span>
						<select onchange="sel()" id="year" name="year">
							<c:set var="years" value="2021"/>
							<c:forEach var="i" begin="0" end="10" step="1">
								<option value="${years }">${years }</option>
								<c:set var="years" value="${years-1 }"/>
							</c:forEach>
						</select>
						년		
					<div id="sub" onclick="subin()">결제하기</div>	
				<div id="chartWrap">
				  <canvas id="myChart"></canvas>
				</div>	
			</div>
		</div>
		


		

	
		
		<script>


			var year=document.getElementById("year").value;//onChange() 되기 전의 값
			
			function sel()
			{
				year=document.getElementById("year").value;//onChange() 됐을 때의 값
				
			}
			
	

			function subin()
			{
				
				$.ajax("payAllList.do",
						{
							type:"POST",
							data:
							{
								year
							},
							success:function(response, status, xhr)
							{
									
								var chartWrap=document.getElementById("chartWrap");
									
									
								while ( chartWrap.hasChildNodes() ) 
								{ 
									chartWrap.removeChild(chartWrap.firstChild);
								}
	
								var canvas=document.createElement("canvas");
									
								chartWrap.appendChild(canvas);
								canvas.setAttribute("id", "myChart");
									
									
								var priceYear =response.split(",");
									
									
								//여기서부턴 그래프
								const labels = 
									[
										'1월',
										'2월',
										'3월',
										'4월',
										'5월',
										'6월',
										'7월',
										'8월',
										'9월',
										'10월',
										'11월',
										'12월'
									];
									
								const data=
								{
									labels: labels,
									datasets:
									[{
										label: '연간 결제 내역',
										backgroundColor:  'rgb(255, 99, 132)',
									    borderColor: 'rgb(255, 99, 132)',
									    data:priceYear,
								
									}]
								}
								const config =
								{
									type:'line',
									data:data,
										
									options:{}
								}
									
								var myChart = new Chart
								(
									document.getElementById('myChart'),
									config
								);
									
							},
							error:function(response, status, xhr)
							{
									
							}
						});
					
			}//subin() 끝
			
			
			
		</script>
		
		<jsp:include page="footer.jsp"/>

	</body>
</html>


