package shm.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shm.tools.DBUtil;

public class PublishProblemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public PublishProblemServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		int sex = Integer.valueOf(request.getParameter("sex"));
		int givedpoints = Integer.valueOf(request.getParameter("givedpoints"));
		int picid = Integer.valueOf(request.getParameter("picid"));
		String time = request.getParameter("time");
		int points = Integer.valueOf(request.getParameter("points"));
		
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql = "insert into problems(title,content,username,name,sex,givedpoints,picid,time)values(?,?,?,?,?,?,?,?)";
		String sql_update = "update users set points = ? where username = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, username);
			pstmt.setString(4, name);
			pstmt.setInt(5, sex);
			pstmt.setInt(6, givedpoints);
			pstmt.setInt(7, picid);
			pstmt.setString(8, time);
			pstmt.executeUpdate();
			
			PreparedStatement pstmt_update = conn.prepareStatement(sql_update);
			pstmt_update.setInt(1, points);
			pstmt_update.setString(2, username);
			pstmt_update.executeUpdate();
			out.write("success");
			DBUtil.close(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBUtil.close(conn);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
