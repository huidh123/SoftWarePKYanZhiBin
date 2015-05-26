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

public class MyPublishProblemsShowServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public MyPublishProblemsShowServlet() {
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
		String username = request.getParameter("username");
		
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql_select_solve = "select id,username,title,content,name,sex,givedpoints,picid,time,solve_username,solve_name from solve where username = ?";
		String sql_select_problems = "select id,username,title,content,name,sex,givedpoints,picid,time from problems where username = ?";
		
		try {
			PreparedStatement pstmt_select_problems = conn.prepareStatement(sql_select_problems);
			pstmt_select_problems.setString(1, username);
			ResultSet rs_select_problems = pstmt_select_problems.executeQuery();
			while(rs_select_problems.next()){
				int id = rs_select_problems.getInt(1);
				String username_solve = rs_select_problems.getString(2);
				String title = rs_select_problems.getString(3);
				String content = rs_select_problems.getString(4);
				String name = rs_select_problems.getString(5);
				int sex = rs_select_problems.getInt(6);
				int givedpoints = rs_select_problems.getInt(7);
				int picid = rs_select_problems.getInt(8);
				String time = rs_select_problems.getString(9);
				
				success_back =  success_back + "9%!0#0" + id + "9%!0#0" + username_solve + "9%!0#0" +  title + "9%!0#0" +content
						+ "9%!0#0" + name + "9%!0#0" + sex + "9%!0#0" + givedpoints + "9%!0#0" + picid +
						"9%!0#0" + time + "9%!0#0" + "donothave";
			}
			
			PreparedStatement pstmt_select_solve = conn.prepareStatement(sql_select_solve);
			pstmt_select_solve.setString(1, username);
			ResultSet rs_select_solve = pstmt_select_solve.executeQuery();
			while(rs_select_solve.next()){
				int id = rs_select_solve.getInt(1);
				String username_solve = rs_select_solve.getString(2);
				String title = rs_select_solve.getString(3);
				String content = rs_select_solve.getString(4);
				String name = rs_select_solve.getString(5);
				int sex = rs_select_solve.getInt(6);
				int givedpoints = rs_select_solve.getInt(7);
				int picid = rs_select_solve.getInt(8);
				String time = rs_select_solve.getString(9);
				String solve_username = rs_select_solve.getString(10);
				String solve_name = rs_select_solve.getString(11);
				
				success_back =  success_back + "9%!0#0" + id + "9%!0#0" + username_solve + "9%!0#0" +  title + "9%!0#0" +content
						+ "9%!0#0" + name + "9%!0#0" + sex + "9%!0#0" + givedpoints + "9%!0#0" + picid +
						"9%!0#0" + time + "9%!0#0" + solve_name + "42!!2!0" + solve_username;
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
