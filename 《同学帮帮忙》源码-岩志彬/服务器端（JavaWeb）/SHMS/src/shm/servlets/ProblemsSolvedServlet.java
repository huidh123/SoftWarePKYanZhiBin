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

public class ProblemsSolvedServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ProblemsSolvedServlet() {
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
		String username_solved = request.getParameter("username");
		int givedpoints = Integer.valueOf(request.getParameter("givedpoints"));
		int id_solved = Integer.valueOf(request.getParameter("id_solved"));
		
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql_select = "select points from users where username = ?";
		String sql_update = "update users set points = ? where username = ?";
		String sql_delete = "delete from solve where id = ?";
		
		try {
			PreparedStatement pstmt_select = conn.prepareStatement(sql_select);
			pstmt_select.setString(1, username_solved);
			ResultSet rs_select = pstmt_select.executeQuery();
			if(rs_select.next()){
				int points_self = rs_select.getInt(1);
				int points_after = givedpoints + points_self;
				PreparedStatement pstmt_update = conn.prepareStatement(sql_update);
				pstmt_update.setInt(1, points_after);
				pstmt_update.setString(2, username_solved);
				pstmt_update.executeUpdate();
				
				PreparedStatement pstmt_delete = conn.prepareStatement(sql_delete);
				pstmt_delete.setInt(1, id_solved);
				pstmt_delete.execute();
				
				out.write("success");
				DBUtil.close(conn);
			}else{
				out.write("fail");
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
