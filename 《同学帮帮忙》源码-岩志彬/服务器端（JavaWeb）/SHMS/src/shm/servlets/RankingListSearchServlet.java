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

public class RankingListSearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public RankingListSearchServlet() {
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
		String search_rank = "";
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String username_search = request.getParameter("username");
		
		PrintWriter out = response.getWriter();
		Connection conn = DBUtil.open();
		
		String sql = "select id,username,name,picid,signature,academe,profession,sex,qqnumber,points from users order by points desc";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int i = 0;
			while(rs.next()){
				int userid = rs.getInt(1);
				String username = rs.getString(2);
				String name = rs.getString(3);
				int picid = rs.getInt(4);
				String signature = rs.getString(5);
				String academe = rs.getString(6);
				String profession = rs.getString(7);
				int sex = rs.getInt(8);
				String qqnumber = rs.getString(9);
				int points = rs.getInt(10);
				
				if(username.equals(username_search)){
					search_rank = userid + "9%!0#0" + username + "9%!0#0" + name + "9%!0#0" + picid
							+ "9%!0#0" + signature + "9%!0#0" + academe + "9%!0#0" + profession + "9%!0#0" 
							+ sex + "9%!0#0" + qqnumber + "9%!0#0" + points + "9%!0#0" + i;
					if(i >= 20){
						break;
					}
				}
				if(i < 20){
					success_back = success_back + "9%!0#0" + userid + "9%!0#0" + username + "9%!0#0" + name + "9%!0#0" + picid
							+ "9%!0#0" + signature + "9%!0#0" + academe + "9%!0#0" + profession + "9%!0#0" 
							+ sex + "9%!0#0" + qqnumber + "9%!0#0" + points;
					if(!search_rank.equals("") && i >= 19){
						break;
					}
				}
				
				i++;
			}
			if(success_back.equals("success")){
				out.write("fail");
				DBUtil.close(conn);
			}else{
				out.write(success_back + "9%!0#0" + search_rank);
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
