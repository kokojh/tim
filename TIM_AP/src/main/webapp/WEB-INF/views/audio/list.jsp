<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tim.ap.entity.AudioEntity"%>

<table>
	<tr>
		<td style="text-align:center; width:100px;">ID</td>
		<td style="text-align:center; width:100px;">C_ID</td>
		<td style="text-align:center; width:100px;">M_EMAIL</td>
		<td style="text-align:center; width:100px;">TIME_BEG</td>
		<td style="text-align:center; width:100px;">TIME_END</td>
		<td style="text-align:center; width:100px;">AD_TEXT</td>
		<td style="text-align:center; width:100px;">AD_WAV_FILEPATH</td>
		<td style="text-align:center; width:100px;">AD_DOWNLOAD_CNT</td>
	</tr>

	<c:forEach items="${result}" var="audioEntity">
		<tr>
			<td style="text-align:center;">${audioEntity.id}</td>
			<td style="text-align:center;">${audioEntity.c_id}</td>
			<td style="text-align:center;">${audioEntity.m_email}</td>
			<td style="text-align:center;">${audioEntity.time_beg}</td>
			<td style="text-align:center;">${audioEntity.time_end}</td>
			<td style="text-align:center;">${audioEntity.ad_text}</td>
			<td style="text-align:center;">${audioEntity.ad_wav_filepath}</td>
			<td style="text-align:center;">${audioEntity.ad_download_cnt}</td>
		</tr>
	</c:forEach>
</table>
