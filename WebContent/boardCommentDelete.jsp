<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="utf-8">
		<title>댓글 삭제하기 전에 물어보기</title>
		<style>
			#wrap
			{
				width:300px;
			}
			div
			{
				text-align:right;
			}
		</style>
	</head>
	<body>
		<c:if test="${aTag==0 }">
			<a id="atag" href="javascript:at()"></a>
		</c:if>
		<div id="wrap">
			<h3>정말로 댓글을 삭제하시겠습니까?</h3>
			<form name="frm" method="post">
				<div>		
					<input type="hidden" value="${pk }">
					<input type="button" value="취소" onclick="cancel()">
					<input type="submit" value="삭제">
				</div>
			</form>
		</div>
		<script>
		
			function cancel()
			{
				self.close();
			}
			
			function at()	//데이터 삭제 후 list를 초기화 해주기 위해 만듬
			{
				opener.document.panupDelete.submit();
				self.close();
			}
			
			
			var atag= document.getElementById("atag");//submit을 해서 데이터를 삭제하고 판업창을 닫기 위해 이러한 장치를 만들었다.
			atag.click();
		</script>

	</body>
</html>