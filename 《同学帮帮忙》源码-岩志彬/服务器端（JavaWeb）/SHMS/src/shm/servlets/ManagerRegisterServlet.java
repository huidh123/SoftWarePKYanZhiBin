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

public class ManagerRegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ManagerRegisterServlet() {
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
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		int picid = Integer.valueOf(request.getParameter("picid"));
		String signature = request.getParameter("signature");
		String academe = request.getParameter("academe");
		String profession = request.getParameter("profession");
		int sex = Integer.valueOf(request.getParameter("sex"));
		String qqnumber = request.getParameter("qqnumber");
		int points = Integer.valueOf(request.getParameter("points"));
		
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql_insert = "insert into users(username,password,name,picid,signature,academe,profession,sex,qqnumber,points)values(?,?,?,?,?,?,?,?,?,?)";
		String sql_select = "select id from users where username = ?";
		
		try {
			PreparedStatement pstmt_select = conn.prepareStatement(sql_select);
			pstmt_select.setString(1, username);
			ResultSet rs_select = pstmt_select.executeQuery();
			if(rs_select.next()){
				out.write("havetheuseryet");
				DBUtil.close(conn);
			}else{
				PreparedStatement pstmt_insert = conn.prepareStatement(sql_insert);
				pstmt_insert.setString(1, username);
				pstmt_insert.setString(2, password);
				pstmt_insert.setString(3, name);
				pstmt_insert.setInt(4, picid);
				pstmt_insert.setString(5, signature);
				pstmt_insert.setString(6, academe);
				pstmt_insert.setString(7, profession);
				pstmt_insert.setInt(8, sex);
				pstmt_insert.setString(9, qqnumber);
				pstmt_insert.setInt(10, points);
				pstmt_insert.executeUpdate();
				out.write("success");
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
