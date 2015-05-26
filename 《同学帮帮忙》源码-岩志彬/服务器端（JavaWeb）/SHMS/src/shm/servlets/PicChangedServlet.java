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

public class PicChangedServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public PicChangedServlet() {
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
		
		String username =request.getParameter("username");
		int pic_changed =Integer.valueOf(request.getParameter("pic_changed"));
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql_update_users = "update users set picid = ? where username = ? ";
		String sql_update_problems = "update problems set picid = ? where username = ? ";
		String sql_update_solve = "update solve set picid = ? where username = ? ";
		String sql_select = "select picid from users where username = ?";
		
		try {
			PreparedStatement pstmt_update_users = conn.prepareStatement(sql_update_users);
			pstmt_update_users.setInt(1, pic_changed);
			pstmt_update_users.setString(2, username);
			pstmt_update_users.executeUpdate();
			
			PreparedStatement pstmt_update_problems = conn.prepareStatement(sql_update_problems);
			pstmt_update_problems.setInt(1, pic_changed);
			pstmt_update_problems.setString(2, username);
			pstmt_update_problems.executeUpdate();
			
			PreparedStatement pstmt_update_solve = conn.prepareStatement(sql_update_solve);
			pstmt_update_solve.setInt(1, pic_changed);
			pstmt_update_solve.setString(2, username);
			pstmt_update_solve.executeUpdate();
			
			PreparedStatement pstmt_select = conn.prepareStatement(sql_select);
			pstmt_select.setString(1, username);
			ResultSet rs = pstmt_select.executeQuery();
			if(rs.next()){
				int picid = rs.getInt(1);
				if(picid == pic_changed){
					out.write("success");
					DBUtil.close(conn);
				}else{
					out.write("fail");
					DBUtil.close(conn);
				}
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
