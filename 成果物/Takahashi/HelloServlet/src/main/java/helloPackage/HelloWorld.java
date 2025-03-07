package helloPackage;

// 必要なライブラリのインポート
import java.io.IOException; // 入出力（I/O）のエラーを処理するための例外クラス
import java.io.PrintWriter; // ブラウザへの出力にテキストを書き込むために使用

import jakarta.servlet.ServletException; // サーブレット固有のエラーを処理する例外クラス
import jakarta.servlet.http.HttpServlet; // サーブレットの基本クラス
import jakarta.servlet.http.HttpServletRequest; // クライアントから送信されたリクエスト情報を扱うため。
import jakarta.servlet.http.HttpServletResponse; // サーバーからクライアントへ返すレスポンスを設定するため。

// サーブレットクラスの定義
@WebServlet("/HelloServlet")
public class HelloWorld extends HttpServlet {

	// doGet メソッドの実装
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    	// レスポンスの設定
        response.setContentType("text/html; charset=Windows-31J");
        
        //レスポンスの出力
        PrintWriter out = response.getWriter();
        out.println("<body>Hello World!!</body>");

    }

}
