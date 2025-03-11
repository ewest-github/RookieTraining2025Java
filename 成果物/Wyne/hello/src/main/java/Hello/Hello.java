package Hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//HelloクラスはHttpServletを継承する
public class Hello extends HttpServlet 
{ 
   //GETリクエストを処理するメソッド
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//クライアントに送信するレスポンスのコンテンツタイプを設定する
		response.setContentType("text/html;charset=UTF-8");
		
		//レスポンスを出力するためのPrintWriterのオブジェクトを取得する
		PrintWriter out=response.getWriter();
		
		//Htmlのコンテンツの出力
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>");
		out.println("Hello,World!");
		out.println("</h1>");
		out.println("</body>");
		out.println("</html>");
		
		
		}

}
