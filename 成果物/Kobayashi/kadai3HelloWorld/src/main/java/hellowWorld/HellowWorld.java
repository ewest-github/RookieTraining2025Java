package hellowWorld;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HellowWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HellowWorld() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Hello Worldを表示
		PrintWriter pw = response.getWriter();
		pw.println("Hello World");
		pw.flush();
		pw.close();
	}
}
