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

public class ProblemsDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ProblemsDeleteServlet() {
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
		String username = request.getParameter("username");
		int givedpoints = Integer.valueOf(request.getParameter("givedpoints"));
		int id_deleted = Integer.valueOf(request.getParameter("id_deleted"));
		String IsSolved_solved = request.getParameter("IsSolved_solved");
		
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql_select = "select points from users where username = ?";
		String sql_update = "update users set points = ? where username = ?";
		String sql_select_donot = "select username,title,content,name,sex,givedpoints,picid,time from problems where id = ?";
		String sql_select_have = "select solve_username,solve_name,username,title,content,name,sex,givedpoints,picid,time from solve where id = ?";
		String sql_insert_donot = "insert into the_deleted_problems(title,content,username,name,sex,givedpoints,picid,time)values(?,?,?,?,?,?,?,?)";
		String sql_insert_have = "insert into the_deleted_problems(solve_username,solve_name,title,content,username,name,sex,givedpoints,picid,time)values(?,?,?,?,?,?,?,?,?,?)";
		String sql_delete_donot = "delete from problems where id = ?";
		String sql_delete_have = "delete from solve where id = ?";
		
		try {
			PreparedStatement pstmt_select = conn.prepareStatement(sql_select);
			pstmt_select.setString(1, username);
			ResultSet rs_select = pstmt_select.executeQuery();
			if(rs_select.next()){
				int points_self = rs_select.getInt(1);
				int points_after = givedpoints + points_self;
				PreparedStatement pstmt_update = conn.prepareStatement(sql_update);
				pstmt_update.setInt(1, points_after);
				pstmt_update.setString(2, username);
				pstmt_update.executeUpdate();
				
				if(IsSolved_solved.equals("donothave")){
					PreparedStatement pstmt_select_donot = conn.prepareStatement(sql_select_donot);
					pstmt_select_donot.setInt(1, id_deleted);
					ResultSet rs_select_donot = pstmt_select_donot.executeQuery();
					if(rs_select_donot.next()){
						String username_donot = rs_select_donot.getString(1);
						String title_donot = rs_select_donot.getString(2);
						String content_donot = rs_select_donot.getString(3);
						String name_donot = rs_select_donot.getString(4);
						int sex_donot = rs_select_donot.getInt(5);
						int givedpoints_donot = rs_select_donot.getInt(6);
						int picid_donot = rs_select_donot.getInt(7);
						String time_donot = rs_select_donot.getString(8);
						
						PreparedStatement pstmt_insert_donot = conn.prepareStatement(sql_insert_donot);
						pstmt_insert_donot.setString(1, title_donot);
						pstmt_insert_donot.setString(2, content_donot);
						pstmt_insert_donot.setString(3, username_donot);
						pstmt_insert_donot.setString(4, name_donot);
						pstmt_insert_donot.setInt(5, sex_donot);
						pstmt_insert_donot.setInt(6, givedpoints_donot);
						pstmt_insert_donot.setInt(7, picid_donot);
						pstmt_insert_donot.setString(8, time_donot);
						pstmt_insert_donot.executeUpdate();
						
						PreparedStatement pstmt_delete_donot = conn.prepareStatement(sql_delete_donot);
						pstmt_delete_donot.setInt(1, id_deleted);
						pstmt_delete_donot.execute();
					}
				}else{
					PreparedStatement pstmt_select_have = conn.prepareStatement(sql_select_have);
					pstmt_select_have.setInt(1, id_deleted);
					ResultSet rs_select_have = pstmt_select_have.executeQuery();
					if(rs_select_have.next()){
						String solve_username_have = rs_select_have.getString(1);
						String solve_name_have = rs_select_have.getString(2);
						String username_have = rs_select_have.getString(3);
						String title_have = rs_select_have.getString(4);
						String content_have = rs_select_have.getString(5);
						String name_have = rs_select_have.getString(6);
						int sex_have = rs_select_have.getInt(7);
						int givedpoints_have = rs_select_have.getInt(8);
						int picid_have = rs_select_have.getInt(9);
						String time_have = rs_select_have.getString(10);
						
						PreparedStatement pstmt_insert_have = conn.prepareStatement(sql_insert_have);
						pstmt_insert_have.setString(1, solve_username_have);
						pstmt_insert_have.setString(2, solve_name_have);
						pstmt_insert_have.setString(3, title_have);
						pstmt_insert_have.setString(4, content_have);
						pstmt_insert_have.setString(5, username_have);
						pstmt_insert_have.setString(6, name_have);
						pstmt_insert_have.setInt(7, sex_have);
						pstmt_insert_have.setInt(8, givedpoints_have);
						pstmt_insert_have.setInt(9, picid_have);
						pstmt_insert_have.setString(10, time_have);
						pstmt_insert_have.executeUpdate();
						
						PreparedStatement pstmt_delete_have = conn.prepareStatement(sql_delete_have);
						pstmt_delete_have.setInt(1, id_deleted);
						pstmt_delete_have.execute();
					}
				}
				
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
