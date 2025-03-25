package attendance;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.WorkingHoursBean;
import dao.AttendtimeDao;

/**
 * AttendTimeInsertServletサーブレットはattendanceサーブレットから受け渡されたデータを
 * データベースに登録する
 */

public class AttendTimeInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GETメソッドはサポートされていません。");
    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AttendtimeDao attendTimeDao = new AttendtimeDao();

        try {
        	//データベースに接続する
            attendTimeDao.connect();
            
            //セッションからWorkingHoursBeanを取得
            HttpSession session = request.getSession();
            WorkingHoursBean workingHours = (WorkingHoursBean) session.getAttribute("workingHours");
            
           // WorkingHoursBean がセッションに存在しない場合、エラーメッセージを設定して JSP にフォワード
            
            if (workingHours == null) {
                request.setAttribute("message", "エラー: WorkingHoursBean が見つかりませんでした。");
                request.getRequestDispatcher("time_manager.jsp").forward(request, response);
                return;
            }
            
             // 1か月の日数
             int MAX_DAYS_IN_MONTH = 31; 
            
            /*
             * 1月分の日付を処理するための for ループ。
             * ループ内で以下の操作を実行:
             * - attendDate（勤怠日付）を取得
             * - startTime（開始時刻）と endTime（終了時刻）を取得
             * - 取得した情報をデータベースに挿入
             */
            
            for (int i = 1; i <= MAX_DAYS_IN_MONTH; i++) {
            	
            	// リクエストパラメータから日付情報を取得
                String dateParam = request.getParameter("attendDate" + i);
                if (dateParam != null && !dateParam.isEmpty()) {
                	
                	
                	// データベース用の日付オブジェクトを作成
                    Date sqlDate = Date.valueOf(dateParam);
                    
                 // 開始時刻と終了時刻を WorkingHoursBean から取得
                    String startTime = workingHours.getStartTime(i);
                    String endTime = workingHours.getEndTime(i);
                    
                    //開始時刻と終了時刻が有効な場合、データベースに挿入
                    if (!startTime.isEmpty() && !endTime.isEmpty()) {
                    	 // 時刻をデータベース用の Time 型に変換
                        Time sqlStartTime = Time.valueOf(startTime + ":00");
                        Time sqlEndTime = Time.valueOf(endTime + ":00");

                        // データベースに挿入
                        attendTimeDao.insert(sqlDate, sqlStartTime, sqlEndTime);
                    }
                }
            }
         // データ登録が成功した場合にリクエストスコープにセット
            request.setAttribute("workingHours", workingHours);
            request.setAttribute("message", "入力完了しました。");
            
            // フォワードでJSPに遷移
            request.getRequestDispatcher("time_manager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // エラーメッセージをリクエストスコープにセット
            request.setAttribute("message", "データの登録中にエラーが発生しました: " + e.getMessage());
            
            // フォワードでJSPに遷移
            request.getRequestDispatcher("time_manager.jsp").forward(request, response);
        }
    }
}