package servelt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Kadai5Bean;

@WebServlet("/Kadai5Servlet")
public class Kadai5Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kadai5Beanのインスタンスを取得
    	Kadai5Bean bean = new Kadai5Bean();

        // 実働時間を計算してBeanに
        for (int i = 1; i <= 31; i++) {
            int startHour = Integer.parseInt(request.getParameter("startHour" + i));
            int startMinute = Integer.parseInt(request.getParameter("startMinute" + i));
            int endHour = Integer.parseInt(request.getParameter("endHour" + i));
            int endMinute = Integer.parseInt(request.getParameter("endMinute" + i));

            // 実働時間の計算(分単位に変換)
            int startTotalMinutes = startHour * 60 + startMinute;
            int endTotalMinutes = endHour * 60 + endMinute;
            double workTime = (endTotalMinutes - startTotalMinutes) / 60.0;
            
            // 12:00から13:00を跨ぐ時-1時間
            if (startHour <= 12 && endHour >= 13) {
                workTime -= 1.0;
            }

            // Beanに実働時間をセット
            bean.setWorkingTime(i, workTime);
            // 実働総計に加算
            bean.addtotalWorkTime(i);
        }
        
        // Kadai5Beanをリクエストスコープに保存
        request.setAttribute("num", bean);

        // JSP指定
        request.getRequestDispatcher("/kadai5JSP.jsp").forward(request, response);
    }

}