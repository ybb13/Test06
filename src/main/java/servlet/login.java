package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.MongoDBJDBC;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public login() {
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = "登录失败";
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		
		

		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		if(mongoDBJDBC.queryhave("User",name, pwd)) {
			result = "登陆成功";
		}
		
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		String title = "HTTP Header 请求实例 - 菜鸟教程实例";
		String docType = "<!DOCTYPE html> \n";
		out.println(docType + "<html>\n" + "<head><meta charset=\"utf-8\"><title>" + title + "</title></head>\n"
				+ "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">" + title + "</h1>\n"
				+ "<table width=\"100%\" border=\"1\" align=\"center\">\n" + "<tr bgcolor=\"#949494\">\n" + result
				+ "</tr>\n");

		out.println("</table>\n</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
