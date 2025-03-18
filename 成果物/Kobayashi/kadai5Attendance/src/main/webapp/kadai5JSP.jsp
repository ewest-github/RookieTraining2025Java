<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="num" scope="request" class="bean.Kadai5Bean"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="kadai5CSS.css"> <!-- css使用 -->
	<title>課題5</title>
</head>
<body>
	<h1>勤怠管理システム</h1>
	
	<form action="Kadai5Servlet" method="POST"> <!-- サーブレットにデータを送信 -->
    <input type="submit" value="登録" />
	
	<!-- 実働総計の表示 -->
    <p>実働総計:<jsp:getProperty name="num" property="totalWorkTime"/> h</p>
	
	<!-- 表(フォーマット)の表示 -->
	<table border="1">
        <tr>
            <th>日付</th>
            <th>開始時刻</th>
            <th>終了時刻</th>
        </tr>

		<!-- 31日分繰り返す -->
        <% for (int i = 0; i < 31; i++) { %>
            <tr>
                <td>
                	<%= i + 1 %>日
                </td> <!-- 日付を1日から始める -->
                <td>
                   	<!-- プルダウン:0から24 -->
                    <select name="startHour<%= i + 1 %>" size="1">
                        <% for (int h = 0; h < 24; h++) { %>
                            <option value="<%= h %>"><%= h %></option>
                        <% } %>
                    </select> :
                    <!-- プルダウン:0から45 -->
                    <select name="startMinute<%= i + 1 %>" size="1">
                    	<option value="00">00</option>
                        <option value="15">15</option>
                        <option value="30">30</option>
                        <option value="45">45</option>
                    </select>
        	    </td>

                <td>
                    <select name="endHour<%= i + 1 %>" size="1">
                        <% for (int h = 0; h < 24; h++) { %>
                            <option value="<%= h %>"><%= h %></option>
                        <% } %>
                    </select> :
                    <select name="endMinute<%= i + 1 %>" size="1">
                        <option value="00">00</option>
                        <option value="15">15</option>
                        <option value="30">30</option>
                        <option value="45">45</option>
                    </select>
                </td>
            </tr>
        <% } %>
    </table>
</body>
</html>