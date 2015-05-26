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


public class SearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public SearchServlet() {
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
		
		String searchContent = request.getParameter("searchContent");
		PrintWriter out = response.getWriter();
		
		Connection conn = DBUtil.open();
		
		String sql_select_from_title = "select id,username,title,content,name,sex,givedpoints,picid,time from problems where title like '%" + searchContent +"%'";
		String sql_select_from_content = "select id,username,title,content,name,sex,givedpoints,picid,time from problems where content like '%" + searchContent +"%'";
		
		try {
			PreparedStatement pstmt_select_from_title = conn.prepareStatement(sql_select_from_title);
			ResultSet rs_select_from_title = pstmt_select_from_title.executeQuery();
			while(rs_select_from_title.next()){
				int id_from_title = rs_select_from_title.getInt(1);
				String username_from_title = rs_select_from_title.getString(2);
				String title_from_title = rs_select_from_title.getString(3);
				String content_from_title = rs_select_from_title.getString(4);
				String name_from_title = rs_select_from_title.getString(5);
				int sex_from_title = rs_select_from_title.getInt(6);
				int givedpoints_from_title = rs_select_from_title.getInt(7);
				int picid_from_title = rs_select_from_title.getInt(8);
				String time_from_title = rs_select_from_title.getString(9);
				success_back =  success_back + "9%!0#0" + id_from_title + "9%!0#0" + 
				username_from_title + "9%!0#0" +title_from_title + "9%!0#0" + content_from_title 
				+ "9%!0#0" + name_from_title + "9%!0#0" +sex_from_title + "9%!0#0" +givedpoints_from_title
				+ "9%!0#0" + picid_from_title + "9%!0#0" + time_from_title;
			}
			
			PreparedStatement pstmt_select_from_content = conn.prepareStatement(sql_select_from_content);
			ResultSet rs_select_from_content = pstmt_select_from_content.executeQuery();
			while(rs_select_from_content.next()){
				int id_from_content = rs_select_from_content.getInt(1);
				String username_from_content = rs_select_from_content.getString(2);
				String title_from_content = rs_select_from_content.getString(3);
				String content_from_content = rs_select_from_content.getString(4);
				String name_from_content = rs_select_from_content.getString(5);
				int sex_from_content = rs_select_from_content.getInt(6);
				int givedpoints_from_content = rs_select_from_content.getInt(7);
				int picid_from_content = rs_select_from_content.getInt(8);
				String time_from_content = rs_select_from_content.getString(9);
				
				success_back =  success_back + "9%!0#0" + id_from_content + "9%!0#0" + 
						username_from_content + "9%!0#0" +title_from_content + "9%!0#0" + content_from_content 
				+ "9%!0#0" + name_from_content + "9%!0#0" +sex_from_content + "9%!0#0" +givedpoints_from_content
				+ "9%!0#0" + picid_from_content + "9%!0#0" + time_from_content;
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
