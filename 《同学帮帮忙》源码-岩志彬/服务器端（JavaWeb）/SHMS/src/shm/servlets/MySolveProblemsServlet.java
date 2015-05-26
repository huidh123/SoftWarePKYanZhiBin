package shm.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shm.tools.DBUtil;

public class MySolveProblemsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public MySolveProblemsServlet() {
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

		String success_back = "success";
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String solve_username = request.getParameter("solve_username");
		
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql_select = "select id,username,title,content,name,sex,givedpoints,picid,time from solve where solve_username = ?";
		
		try {
			PreparedStatement pstmt_select = conn.prepareStatement(sql_select);
			pstmt_select.setString(1, solve_username);
			ResultSet rs_select = pstmt_select.executeQuery();
			while(rs_select.next()){
				int id = rs_select.getInt(1);
				String username = rs_select.getString(2);
				String title = rs_select.getString(3);
				String content = rs_select.getString(4);
				String name = rs_select.getString(5);
				int sex = rs_select.getInt(6);
				int givedpoints = rs_select.getInt(7);
				int picid = rs_select.getInt(8);
				String time = rs_select.getString(9);
				
				success_back =  success_back + "9%!0#0" + id + "9%!0#0" + username + "9%!0#0" +  title + "9%!0#0" +content
						+ "9%!0#0" + name + "9%!0#0" + sex + "9%!0#0" + givedpoints + "9%!0#0" + picid +  "9%!0#0" + time;
			}
			if(success_back.equals("success")){
				out.write("failed");
				DBUtil.close(conn);
			}else{
				out.write(success_back);
				DBUtil.close(conn);
			}
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
