package attendance;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.WorkingHoursBean;

/*
 * attendanceクラスのサーブレットはtime_manager.jspから送信されたデータを取得し、
 * それを処理・計算してデータベースに保存するクラスです
 * 
 * */

//attendanceクラスはHttpServletを継承する
public class attendance extends HttpServlet {
   
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GETメソッドはサポートされていません。");
    }
	
	/*
     * doPostメソッドはtime_manager.jspから送信されたパラメータを受け取り
     * 各日の開始時刻、終了時刻を取得するメソッドです
     * 
     * */
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//WorkingHoursBeanクラスのオブジェクトの設定
        WorkingHoursBean workingHours = new WorkingHoursBean();
        
        //実働総計の初期値設定
        double totalHours = 0.0;
        
        
        // 31日分のデータを処理
        for (int i = 1; i <= 31; i++) {
            
        	//各日の 時間と分を取得する
            String startHour = request.getParameter("startHour" + i);
            String startMinute = request.getParameter("startMinute" + i);
            String endHour = request.getParameter("endHour" + i);
            String endMinute = request.getParameter("endMinute" + i);

            // 時刻が有効かをチェック
            if (startHour != null && !startHour.isEmpty() && startMinute != null && !startMinute.isEmpty() && 
                endHour != null && !endHour.isEmpty() && endMinute != null && !endMinute.isEmpty()) {

                
            	// 開始時刻と終了時刻を結合して代入する
                String startTime = startHour + ":" + startMinute;
                String endTime = endHour + ":" + endMinute;

                // 各日の実働時間の計算をするメソッドを呼び出して、dailyHoursに代入する
                double dailyHours = calculateWorkingHours(startTime, endTime);
                
                // Beanに時間をセット
                workingHours.setStartTime(i, startHour,startMinute);
                workingHours.setEndTime(i, endHour,endMinute);
                
                //実働総計の計算
                totalHours += dailyHours;
                
                
            }
        }  
        
        // Beanに実働総計を設定
        workingHours.setTotalHours(totalHours);
        
     // Bean をセッションに保存して、新しいサーブレットに転送
        HttpSession session = request.getSession();
        session.setAttribute("workingHours", workingHours);

     // POSTリクエストを次のサーブレットへフォワード
        request.getRequestDispatcher("insertAttendTime").forward(request, response);
        
        
        

    }

    /*
     * 実働時間を計算するメソッド
     * @param 開始時刻
     * @param 終了時刻
     * @return 計算した時間
     * */
    private double calculateWorkingHours(String start, String end) {
        double totalHours = 0.0;
        
        try {
            
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
            
        } 
     // エラーが発生した場合、実働時間を0に設定
        catch (NumberFormatException e) {
            
            totalHours = 0.0;
        }

        return totalHours;
    }
}
