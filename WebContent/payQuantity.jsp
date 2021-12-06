<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>남녀 대비 구매수량 검색</title>
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
			#divNone
			{
				display:none;
			}
		</style>
	</head>
	<body>
			
		<jsp:include page="header.jsp"/>
		
		
		<br>
		<br>
		<div id="payWrap">
			<div>
				<h1>남녀 대비 구매수량 검색</h1>
				
						<span>연도 검색 : </span>
						<select onchange="sel()" id="year" name="year">
							<c:set var="years" value="2021"/>
							<c:forEach var="i" begin="0" end="10" step="1">
								<option value="${years }">${years }</option>
								<c:set var="years" value="${years-1 }"/>
							</c:forEach>
						</select>
						년	
						
						<input onclick="mon()" id="monthChecked" name="monthChecked" value="0" type="checkbox">
						<div id="divNone">
							<span>월별로 검색 : </span>
								<select onchange="monChange()" id="month" name="month">
									<c:forEach var="i" begin="1" end="12" step="1">
										<option value="${i }">${i }</option>
									</c:forEach>
								</select>
							월	
						</div>
							
					<div id="sub" onclick="subin()">검색하기</div>	
				<div id="chartWrap">
				  <canvas id="myChart"></canvas>
				</div>	
			</div>
		</div>
		


		

	
		
		<script>
			var divNone=document.getElementById("divNone");//체크박스
			
			var monthChecked=document.getElementById("monthChecked");
			
			
			function mon()
			{
				
				if(monthChecked.checked==true)
				{
					divNone.style.display="inline-block";
					monthChecked.value=1;//서블렛으로 넘기면 null되는데 자바스트립트는 또 전달이 된다.
				}
				else
				{
					monthChecked.value=0;
					divNone.style.display="none";
				}
				
			}
			
			
			var monthSelect=document.getElementById("month").value;
			
			function monChange()
			{
				monthSelect=document.getElementById("month").value;
			}

			var year=document.getElementById("year").value;//onChange() 되기 전의 값
			
			function sel()
			{
				year=document.getElementById("year").value;//onChange() 됐을 때의 값
				
			}
			

			function subin()
			{
				 	
				$.ajax("payQuantity.do",
					{
						type:"POST",
						data:
						{
							year,
							monCheck:monthChecked.value,
							monthSelect
						},
						success:function(response, status, xhr)
						{
							
							var chartWrap=document.getElementById("chartWrap");
							
							while(chartWrap.hasChildNodes())
							{
								chartWrap.removeChild(chartWrap.firstChild);
							}
							
							var canvas=document.createElement("canvas");
							
							chartWrap.appendChild(canvas);
							canvas.setAttribute("id", "myChart");
							
							
							var chart=response.split(",");
							var chart1 =new Array();
							var chart2 =new Array();
							
							for(var i=0; i<10; i++)
							{
								if(i<5)
								{
									chart1[i]=chart[i];
								}
								else
								{
									chart2[i-5]=chart[i];
								}

							}

							//그래프 시작
							const labels=
							[
								"10대",
								"20대",
								"30대",
								"40대",
								"50대 이상"

							];
							
							const data =
							{
								labels:labels,
								datasets:
								[
									{
										label:"남자",
										backgroundColor:'rgb(99, 104, 255)',
										borderColor:'rgb(99, 104, 255)',
										data:chart1
									},
									{
										label:"여자",
										backgroundColor:'rgb(255, 99, 234)',
										borderColor:'rgb(255, 99, 234)',
										data:chart2
									}
								]
							}
							const config=
							{
								type: "bar",
								data:data,
								option:{}
							}
							
							var myChart = new Chart
							(
								document.getElementById("myChart"),
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
