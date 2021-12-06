<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import ="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>게시판 목록</title>
		<link rel="stylesheet" href="styleBoard.css">
		<style>
			thead
			{
				background-color:lightgray;
			}
			td
			{
				background-color:white;
				border-bottom:1px solid #858585;
			}
			#noticeSpan
			{
				border-bottom:1px solid #858585;
				height: 40px;
			}
			#noticeSpan > a > span
			{
			    padding: 0 15px;
			    text-align: center;
			    cursor: pointer;
			    display: inline-block;
 				color: #858585;
			}

			.active
			{
				height: 40px;
				color: #000;
				border-bottom: 1px solid #000;
    			font-weight: bold;
			}
			.noticeOptions
			{
				color: #858585;
			}
			.fill
			{
				
				color:#428bca;
				font-weight: bold;
			}
			.fillBackColor
			{
				background-color:#BECDFF;
			}
			#pageDiv
			{
				text-align:center;
			}
			
		</style>
	
	</head>
	<body>
	
		<jsp:include page="header.jsp"/>
		
		
		<!-- 공지사항 -->
		
		<div id="one">
			<h2>공지사항</h2>
		</div>
		<hr>
		<div id="wrap">
			<div id="noticeSpan">
				<c:if test="${op==null }">
					<a href="noticeBoardSelect.do?startPage=1&&lastPage=10"><span class="active">전체 (${count[0] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=0"><span>필독 (${count[1] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=1"><span>공지 (${count[2] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=2"><span>이벤트 (${count[3] })</span></a>
				</c:if>
				<c:if test="${op==0 }">
					<a href="noticeBoardSelect.do?startPage=1&&lastPage=10"><span>전체 (${count[0] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=0"><span class="active">필독 (${count[1] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=1"><span>공지 (${count[2] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=2"><span>이벤트 (${count[3] })</span></a>
				</c:if>
				<c:if test="${op==1 }">
					<a href="noticeBoardSelect.do?startPage=1&&lastPage=10"><span>전체 (${count[0] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=0"><span>필독 (${count[1] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=1"><span class="active">공지 (${count[2] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=2"><span>이벤트 (${count[3] })</span></a>
				</c:if>
				<c:if test="${op==2 }">
					<a href="noticeBoardSelect.do?startPage=1&&lastPage=10"><span>전체 (${count[0] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=0"><span>필독 (${count[1] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=1"><span>공지 (${count[2] })</span></a>
					<a href="noticeBoardSelect2.do?startPage=1&&lastPage=10&&op=2"><span class="active">이벤트 (${count[3] })</span></a>
				</c:if>

			</div>

			<br>
			<table>
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>날짜</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${op ==null }">
						<c:forEach items="${fill }" var="fill">
							<tr>
								<td class="fillBackColor" class="fill">공지</td>
								<td class="fillBackColor">
									<a class="fill" href="noticeBoardEachSelect.do?num=${fill.getNum() }">
										<c:if test="${fill.getOptions() ==0 }"><span class="noticeOptions">[필독]</span></c:if>
										${fill.getTitle() }
									</a>
								</td>
								<td class="fillBackColor">${fill.getId() }</td>
								<td class="fillBackColor">${fill.getDate() }</td>
								<td class="fillBackColor">${fill.getHits() }</td>
							</tr>
						</c:forEach>
						<c:forEach items="${list }" var="list">
							<tr>
								<td>${list.getNum() }</td>
								<td>
									<a href="noticeBoardEachSelect.do?num=${list.getNum() }">
										<c:if test="${list.getOptions() ==1 }"><span class="noticeOptions">[공지]</span></c:if>
										<c:if test="${list.getOptions() ==2 }"><span class="noticeOptions">[이벤트]</span></c:if>
										${list.getTitle() }
									</a>
								</td>
								<td>${list.getId() }</td>
								<td>${list.getDate() }</td>
								<td>${list.getHits() }</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${op==0 }">
						<c:forEach items="${fill }" var="fill">
							<tr>
								<td class="fillBackColor" class="fill">공지</td>
								<td class="fillBackColor">
									<a class="fill" href="noticeBoardEachSelect.do?num=${fill.getNum() }">
										<c:if test="${fill.getOptions() ==0 }"><span class="noticeOptions">[필독]</span></c:if>
										${fill.getTitle() }
									</a>
								</td>
								<td class="fillBackColor">${fill.getId() }</td>
								<td class="fillBackColor">${fill.getDate() }</td>
								<td class="fillBackColor">${fill.getHits() }</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${op==1 || op==2 }">
						<c:forEach items="${list }" var="list">
							<tr>
								<td>${list.getNum() }</td>
								<td>
									<a href="noticeBoardEachSelect.do?num=${list.getNum() }">
										<c:if test="${list.getOptions() ==1 }"><span class="noticeOptions">[공지]</span></c:if>
										<c:if test="${list.getOptions() ==2 }"><span class="noticeOptions">[이벤트]</span></c:if>
										${list.getTitle() }
									</a>
								</td>
								<td>${list.getId() }</td>
								<td>${list.getDate() }</td>
								<td>${list.getHits() }</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>	
			
			<div id="pageDiv">
				<ul id="pagebtn">
					<c:if test="${startPage!=1 }">
						<li><a href="noticeBoardSelect.do?startPage=${startPage-startPage+1 }&&lastPage=${lastPage}">《</a></li>
						<li><a href="noticeBoardSelect.do?startPage=${startPage-1 }&&lastPage=${lastPage}">〈 </a></li>
					</c:if>
					<c:forEach begin="1" end="${page }" var="i">
						<c:choose>
							<c:when test="${startPage == i }">
								<li>${i }</li>
							</c:when>
							<c:otherwise>
								<li><a href="noticeBoardSelect.do?startPage=1&&lastPage=10">${i }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${startPage lt page }">
						<li><a href="noticeBoardSelect.do?startPage=${startPage+1 }&&lastPage=${lastPage}">〉</a></li>
						<li><a href="noticeBoardSelect.do?startPage=${page }&&lastPage=${lastPage}">》</a></li>
					</c:if>
				</ul>	
			</div>


			<c:if test="${loginUser.admin==0 }">
				<a href="noticeBoardWriting.do">글쓰기</a>
			</c:if>
		</div>

		<jsp:include page="footer.jsp"/>
		

	</body>
</html>


