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
 * Servlet implementation class AttendTimeInsertServlet
 */
//@WebServlet("/insertAttendTime")
public class AttendTimeInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GETメソッドはサポートされていません。");
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AttendtimeDao attendTimeDao = new AttendtimeDao();

        try {
            attendTimeDao.connect();
            HttpSession session = request.getSession();
            WorkingHoursBean workingHours = (WorkingHoursBean) session.getAttribute("workingHours");

            if (workingHours == null) {
                request.setAttribute("message", "エラー: WorkingHoursBean が見つかりませんでした。");
                request.getRequestDispatcher("time_manager.jsp").forward(request, response);
                return;
            }

            for (int i = 1; i <= 31; i++) {
                String dateParam = request.getParameter("attendDate" + i);
                if (dateParam != null && !dateParam.isEmpty()) {
                    Date sqlDate = Date.valueOf(dateParam);
                    String startTime = workingHours.getStartTime(i);
                    String endTime = workingHours.getEndTime(i);

                    if (!startTime.isEmpty() && !endTime.isEmpty()) {
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