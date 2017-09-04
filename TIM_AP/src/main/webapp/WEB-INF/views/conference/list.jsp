<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tim.ap.entity.ConferenceEntity"%>

<table>
	<tr>
		<td style="text-align:center; width:300px;">No.</td>
		<td style="text-align:center; width:300px;">회의제목</td>
		<td style="text-align:center; width:300px;">회의일자</td>
		<td style="text-align:center; width:300px;">참여권한</td>
		<td style="text-align:center; width:300px;">참여자수</td>
	</tr>

	<c:forEach items="${result}" var="conferenceEntity">
		<tr>
			<td style="text-align:center;">${conferenceEntity.id}</td>
			<td style="text-align:center;">${conferenceEntity.title}</td>
			<td style="text-align:center;">${conferenceEntity.date}</td>
			<td style="text-align:center;">${conferenceEntity.role}</td>
			<td style="text-align:center;">${conferenceEntity.entry}</td>
		</tr>
	</c:forEach>
</table>
