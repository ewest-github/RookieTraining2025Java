package servlets;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import beans.WorkTimeBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/WorkTimeServlet")
public class WorkTimeServlet extends HttpServlet {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;
    
    /** 昼休憩開始時刻 */
    private static final LocalTime LUNCH_START = LocalTime.of(12, 0);
    
    /** 昼休憩終了時刻 */
    private static final LocalTime LUNCH_END = LocalTime.of(13, 0);
       
    public WorkTimeServlet() {
        super();
    }

    /**
     * GETリクエスト処理.
     * 初期表示時に空のBeanを作成しJSPに転送する.
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @throws ServletException サーブレット処理例外
     * @throws IOException 入出力例外
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 初期表示の場合は空のBeanを作成
        WorkTimeBean workTime = new WorkTimeBean();
        request.setAttribute("workTime", workTime);
        
        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/workTimeEntry.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * POSTリクエスト処理.
     * 入力された開始時刻と終了時刻から実働時間を計算し、結果をJSPに転送する.
     * 
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @throws ServletException サーブレット処理例外
     * @throws IOException 入出力例外
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");
        
        // 現在の月の最終日を取得
        Calendar cal = Calendar.getInstance();
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // 日付フォーマッタ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        // WorkTimeBeanの作成
        WorkTimeBean workTime = new WorkTimeBean();
        
        // 合計実働時間
        double totalWorkHours = 0.0;
        
        // 各日の開始時刻と終了時刻を取得し、実働時間を計算
        for (int day = 1; day <= lastDay; day++) {
            // 開始時刻の取得
            String startHour = request.getParameter("startHour" + day);
            String startMinute = request.getParameter("startMinute" + day);
            
            // 終了時刻の取得
            String endHour = request.getParameter("endHour" + day);
            String endMinute = request.getParameter("endMinute" + day);
            
            // 時刻が選択されていない場合はスキップ
            if (startHour == null || startHour.isEmpty() || endHour == null || endHour.isEmpty()) {
                continue;
            }
            
            // 開始時刻と終了時刻の文字列を作成
            String startTimeStr = startHour + ":" + startMinute;
            String endTimeStr = endHour + ":" + endMinute;
            
            // Beanに時刻を設定
            workTime.setStartTime(day, startTimeStr);
            workTime.setEndTime(day, endTimeStr);
            
            // 文字列からLocalTimeに変換
            LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
            LocalTime endTime = LocalTime.parse(endTimeStr, formatter);
            
            // 実働時間を計算（終了時刻 - 開始時刻）
            double workHours = calculateWorkHours(startTime, endTime);
            
            // 合計実働時間に加算
            totalWorkHours += workHours;
        }
        
        // 合計実働時間をBeanに設定
        workTime.setTotalWorkHours(totalWorkHours);
        
        // リクエスト属性にBeanを設定
        request.setAttribute("workTime", workTime);
        request.setAttribute("registrationComplete", true);
        
        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("workTimeEntry.jsp");
        dispatcher.forward(request, response);
    }
    
    /**
     * 実働時間を計算するメソッド.
     * 昼休憩時間（12:00-13:00）を跨ぐ場合は1時間を減算する.
     * 
     * @param startTime 開始時刻
     * @param endTime 終了時刻
     * @return 実働時間（時間単位）
     */
    private double calculateWorkHours(LocalTime startTime, LocalTime endTime) {
        // 開始時刻が終了時刻より後の場合（次の日に跨ぐ場合）は0時間とする
        if (startTime.isAfter(endTime)) {
            return 0.0;
        }
        
        // 時間の差を分単位で計算
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        
        // 昼休憩時間を跨いでいるか確認
        boolean lunchBreakIncluded = false;
        
        // 開始時刻が昼休憩開始時刻より前で、終了時刻が昼休憩終了時刻より後の場合
        if (startTime.isBefore(LUNCH_START) && endTime.isAfter(LUNCH_END)) {
            lunchBreakIncluded = true;
        }
        // 開始時刻が昼休憩時間内の場合
        else if (startTime.equals(LUNCH_START) || 
                (startTime.isAfter(LUNCH_START) && startTime.isBefore(LUNCH_END))) {
            // 終了時刻が昼休憩終了時刻より後の場合
            if (endTime.isAfter(LUNCH_END)) {
                lunchBreakIncluded = true;
                // 昼休憩開始から開始時刻までの時間を調整
                minutes += ChronoUnit.MINUTES.between(LUNCH_START, startTime);
            }
        }
        // 終了時刻が昼休憩時間内の場合
        else if (endTime.equals(LUNCH_END) || 
                (endTime.isAfter(LUNCH_START) && endTime.isBefore(LUNCH_END))) {
            // 開始時刻が昼休憩開始時刻より前の場合
            if (startTime.isBefore(LUNCH_START)) {
                lunchBreakIncluded = true;
                // 終了時刻から昼休憩終了までの時間を調整
                minutes += ChronoUnit.MINUTES.between(endTime, LUNCH_END);
            }
        }
        
        // 昼休憩時間を跨いでいる場合は1時間（60分）を減算
        if (lunchBreakIncluded) {
            minutes -= 60;
        }
        
        // 分を時間に変換（小数点第1位まで）
        return Math.round((minutes / 60.0) * 10.0) / 10.0;
    }
}
