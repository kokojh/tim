<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tim.ap.entity.MemberEntity"%>

<table>
	<tr>
		<td style="text-align:center; width:300px;">No.</td>
		<td style="text-align:center; width:300px;">이메일</td>
		<td style="text-align:center; width:300px;">성</td>
		<td style="text-align:center; width:300px;">이름</td>
	</tr>

	<c:forEach items="${result}" var="memberEntity">
		<tr>
			<td style="text-align:center;">${memberEntity.id}</td>
			<td style="text-align:center;">${memberEntity.email}</td>
			<td style="text-align:center;">${memberEntity.name_last}</td>
			<td style="text-align:center;">${memberEntity.name_first}</td>
		</tr>
	</c:forEach>
</table>
