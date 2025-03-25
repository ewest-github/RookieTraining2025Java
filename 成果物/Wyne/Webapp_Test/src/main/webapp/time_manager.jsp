<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Bean.WorkingHoursBean" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>勤怠入力</title>
	
	<!-- 外部CSSファイルをリンク -->
 <link rel="stylesheet" type="text/css" href="style.css"> 
</head>
<div class="container">
<body>
	<h2>勤怠管理システム</h2>
	
	

    <!-- 勤怠入力フォーム -->
    <form action="attendance" method="post">
    
    
    <!-- 登録ボタン -->
    <div class="button-container">
        <input type="submit" onclick="alert('登録完了しました');" value="登録">
	</div>
    <!-- 実働総計の計算結果 -->
    <p>実働総計:
        <% 
        WorkingHoursBean workingHours = (WorkingHoursBean) request.getAttribute("workingHours");
        if (workingHours != null) { 
        %>
            <%= workingHours.getTotalHours() %> h
        <% } else { %> 
            0.0 h
        <% } %>
    </p>

        <table border="1">
            <tr>
                <th bgcolor="yellow">日付</th>
                <th bgcolor="yellow">開始時刻</th>
                <th bgcolor="yellow">終了時刻</th>
            </tr>
			<!-- 日付の生成 -->
            <% for (int i = 1; i <= 31; i++) 
            { %>
                <tr>
                    
                    <td><%= i %>日
                    <!-- 日付を送信するための隠しフィールド -->
                     <input type="hidden" name="attendDate<%= i %>" value="<%=String.format("2025-03-%02d", i )%>">
                    </td> 
                    
                    <!-- 開始時刻の生成 -->   
                    <td>
                   	  	<!-- 時間の選択 -->
	                        <select name="startHour<%= i %>">
	                            <% for (int h = 0; h < 24; h++) {
	                    
	                                       String hour = String.format("%02d", h);
	                            %>
	                             <option value="<%= hour %>"><%= hour %></option>
	                                <% } %>
	                        </select> : 
	                            
                            <!-- 分の選択 -->
                            <select name="startMinute<%= i %>">
                                <% for (int m = 0; m < 60; m += 15) { 
                                   
                                		String minute = String.format("%02d", m);
                                %>
                                <option value="<%= minute %>"><%= minute %></option>
                                <% } %>
                            </select>
                            
                
                    </td>  

                    <!-- 終了時刻の生成 -->
                    <td>
                   	 <!-- 時間の選択 -->
	                        <select name="endHour<%= i %>">
	                            <% for (int h = 0; h < 24; h++) {
	                                   
	                                       String hour = String.format("%02d",h);
	                            %>
	                            <option value="<%= hour %>"><%= hour %></option>
	                            <% }%>
	                        </select> :
                       <!-- 分の選択 -->
                       		<select name="endMinute<%= i %>">
                                <% for (int m = 0; m < 60; m += 15) { 
                                    String minute = String.format("%02d", m);
                                %>
                                <option value="<%= minute %>"><%= minute %></option>
                                <% } %>
                            </select>
                        
                    </td>  
                </tr>
            <% } %>
        </table>

        
    </form>
    

</body>
</div>
</html>
