<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.WorkTimeBean" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>勤怠管理システム</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>勤怠管理システム</h1>
        
        <% 
            // Beanの取得
            WorkTimeBean workTime = (WorkTimeBean) request.getAttribute("workTime");
            if (workTime == null) {
                workTime = new WorkTimeBean();
            }
            
            // 現在の月の最終日を取得
            Calendar cal = Calendar.getInstance();
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            // 時と分の選択肢を準備
            String[] hours = new String[24];
            for (int i = 0; i < 24; i++) {
                hours[i] = String.format("%02d", i);
            }
            
            List<String> minutes = Arrays.asList("00", "15", "30", "45");
        %>
        
        <form action="WorkTimeServlet" method="post">
            <div class="top-section">
                <div class="total-work-hours">
                    <span class="label">実働総計：</span>
                    <span class="value"><%= String.format("%.1f", workTime.getTotalWorkHours()) %>h</span>
                </div>
                <button type="submit" class="register-button">登録</button>
            </div>
            
            <div class="attendance-table">
                <table>
                    <thead>
                        <tr>
                            <th>日付</th>
                            <th>開始時刻</th>
                            <th>終了時刻</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (int day = 1; day <= lastDay; day++) { %>
                            <tr>
                                <td class="day"><%= day %>日</td>
                                <td class="time-input">
                                    <select name="startHour<%= day %>">
                                        <% for (String hour : hours) { %>
                                            <% 
                                                String startTime = workTime.getStartTime(day);
                                                String selectedHour = startTime.length() >= 2 ? startTime.substring(0, 2) : "";
                                            %>
                                            <option value="<%= hour %>" <%= hour.equals(selectedHour) ? "selected" : "" %>><%= hour %></option>
                                        <% } %>
                                    </select>
                                    ：
                                    <select name="startMinute<%= day %>">
                                        <% for (String minute : minutes) { %>
                                            <% 
                                                String startTime = workTime.getStartTime(day);
                                                String selectedMinute = startTime.length() >= 5 ? startTime.substring(3, 5) : "";
                                            %>
                                            <option value="<%= minute %>" <%= minute.equals(selectedMinute) ? "selected" : "" %>><%= minute %></option>
                                        <% } %>
                                    </select>
                                </td>
                                <td class="time-input">
                                    <select name="endHour<%= day %>">
                                        <% for (String hour : hours) { %>
                                            <% 
                                                String endTime = workTime.getEndTime(day);
                                                String selectedHour = endTime.length() >= 2 ? endTime.substring(0, 2) : "";
                                            %>
                                            <option value="<%= hour %>" <%= hour.equals(selectedHour) ? "selected" : "" %>><%= hour %></option>
                                        <% } %>
                                    </select>
                                    ：
                                    <select name="endMinute<%= day %>">
                                        <% for (String minute : minutes) { %>
                                            <% 
                                                String endTime = workTime.getEndTime(day);
                                                String selectedMinute = endTime.length() >= 5 ? endTime.substring(3, 5) : "";
                                            %>
                                            <option value="<%= minute %>" <%= minute.equals(selectedMinute) ? "selected" : "" %>><%= minute %></option>
                                        <% } %>
                                    </select>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </form>
        
        <% if (request.getAttribute("registrationComplete") != null) { %>
            <script>
                alert("登録が完了しました。");
            </script>
        <% } %>
    </div>
</body>
</html>