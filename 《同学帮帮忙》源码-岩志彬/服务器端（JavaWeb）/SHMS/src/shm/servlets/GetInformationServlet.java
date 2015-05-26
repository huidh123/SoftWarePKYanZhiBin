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

public class GetInformationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public GetInformationServlet() {
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
		
		String sql = "select id,name,picid,signature,academe,profession,sex,qqnumber,points from users where username = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				int userid = rs.getInt(1);
				String name = rs.getString(2);
				int picid = rs.getInt(3);
				String signature = rs.getString(4);
				String academe = rs.getString(5);
				String profession = rs.getString(6);
				int sex = rs.getInt(7);
				String qqnumber = rs.getString(8);
				int points = rs.getInt(9);
				
				success_back = success_back + "9%!0#0" + userid + "9%!0#0" + name + "9%!0#0" + picid
						+ "9%!0#0" + signature + "9%!0#0" + academe + "9%!0#0" + profession + "9%!0#0" 
						+ sex + "9%!0#0" + qqnumber + "9%!0#0" + points;
				
				out.write(success_back);
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
