<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>     
<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>

	<script> 
		jQuery(function($){             
			$("#DataTable").DataTable({
				info:false,
				order:[[0,"desc"]],
				"language": {
				     "emptyTable": "거래 내역이 존재하지 않습니다.",
			     	 "paginate" :{
			     	 "previous" : "<<",
			     	 "next" : ">>"
			     		}
			     },
				"pagingType": "simple_numbers" 	
			});        
		});     
	</script>

	<style>
		th{background-color:skyblue;}
		img{width:200px; height:200px;}
		tr:hover{background-color:#edf2ef!important;}
	</style>
	
</head>
<body>
	<h1>PicSell 회원님들의 거래 내역 확인</h1>

	<table id="DataTable" class="table table-bordered">
	<thead> 
		<tr>
			<th>거래 날짜</th>
			<th>거래 금액 </th>
			<th>구매자 닉네임 </th>
			<th>작가 닉네임</th>
			<th>거래 이미지 번호</th>
			<th>이미지</th>
		</tr> 
	</thead>
	<tbody>
	<c:forEach items="${list}" var="list">
		
		<tr onclick="window.open('${pageContext.request.contextPath}/DetailImage.do?img_seq=${list.dealImgSeq}&nickname=${adminInfo}',
			'','scrollbars=yes,resizable=yes,top=0, width=1360, height=950'); return false;" 
			style="cursor:hand; width: 336px; height: 224.07px; top: 2146.92px; left: 347px;" class="jg-entry entry-visible" >
			<td>${list.dealDate}</td>
			<td>${list.dealPrice} </td>
			<td>${list.buyerNickname}</td>
			<td>${list.writerNickname}</td>
			<td>${list.dealImgSeq}</td>
			<td><img src="/writeruploadfiles/${list.sysName}"></td>
		</tr>
		
	</c:forEach>
	</tbody>

</body>
</html>