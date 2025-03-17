package attendance;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.WorkingHoursBean;

//@WebServlet("/attendance")
public class attendance extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//Bean objectの作成
        WorkingHoursBean workingHours = new WorkingHoursBean();
        double totalHours = 0.0;

        // 31日分のデータを処理
        for (int i = 1; i <= 31; i++) {
            // 時間と分を取得
            String startHour = request.getParameter("startHour" + i);
            String startMinute = request.getParameter("startMinute" + i);
            String endHour = request.getParameter("endHour" + i);
            String endMinute = request.getParameter("endMinute" + i);

            // 時刻が有効かをチェック
            if (startHour != null && !startHour.isEmpty() && startMinute != null && !startMinute.isEmpty() && 
                endHour != null && !endHour.isEmpty() && endMinute != null && !endMinute.isEmpty()) {

                // 開始時刻と終了時刻を結合して計算
                String startTime = startHour + ":" + startMinute;
                String endTime = endHour + ":" + endMinute;

                // 実働時間の計算
                double dailyHours = calculateWorkingHours(startTime, endTime);
                
                // Beanに時間をセット
                workingHours.setStartTime(i, startHour,startMinute);
                workingHours.setEndTime(i, endHour,endMinute);
                totalHours += dailyHours;
            }
        }  
        
        
        // 総合計時間を設定
        workingHours.setTotalHours(totalHours);
        
        // リクエストスコープにBeanをセット
        request.setAttribute("workingHours", workingHours);
        
        // JSPにフォワード
        request.getRequestDispatcher("time_manager.jsp").forward(request, response);
    }

    // 実働時間を計算
    private double calculateWorkingHours(String start, String end) {
        double totalHours = 0.0;

        try {
            // 開始時刻と終了時刻を分解
            int startHour = Integer.parseInt(start.split(":")[0]);
            int startMinute = Integer.parseInt(start.split(":")[1]);
            int endHour = Integer.parseInt(end.split(":")[0]);
            int endMinute = Integer.parseInt(end.split(":")[1]);

            // 時間を小数点形式に変換
            double startDecimal = startHour + startMinute / 60.0;
            double endDecimal = endHour + endMinute / 60.0;
            
            // 実働時間の計算
            totalHours = endDecimal - startDecimal;

            // 12:00-13:00を跨ぐ場合1時間を引く
            if (startDecimal < 12.0 && endDecimal > 13.0) {
                totalHours -= 1.0;
            }
        } catch (NumberFormatException e) {
            // エラーが発生した場合、実働時間を0に設定
            totalHours = 0.0;
        }

        return totalHours;
    }
}
