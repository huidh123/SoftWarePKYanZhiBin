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

public class IsSolvedServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public IsSolvedServlet() {
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
		int problemid = Integer.valueOf(request.getParameter("problemid"));
		String solve_username = request.getParameter("solve_username");
		String solve_name = request.getParameter("solve_name");
		
		PrintWriter out = response.getWriter();
		Connection conn = DBUtil.open();
		
		String sqlFromProblems = "select username from problems where id = ?";
		String sqlFromSolve = "select id from solve where problemid = ?";
		String sqlInsert = "insert into solve(solve_username,solve_name,problemid,title,content,username,name,sex,givedpoints,picid,time)values(?,?,?,?,?,?,?,?,?,?,?)";
		String sqlFromProblemsALL = "select title,content,username,name,sex,givedpoints,picid,time from problems where id = ?";
		String sqlDelete = "delete from problems where id = ?";
		
		try {
			PreparedStatement pstmtFromProblems = conn.prepareStatement(sqlFromProblems);
			pstmtFromProblems.setInt(1, problemid);
			ResultSet rsFromProblems = pstmtFromProblems.executeQuery();
			if(rsFromProblems.next()){
				PreparedStatement pstmtFromSolve = conn.prepareStatement(sqlFromSolve);
				pstmtFromSolve.setInt(1, problemid);
				ResultSet rsFromSolve = pstmtFromSolve.executeQuery();
				if(rsFromSolve.next()){
					out.write("fail");
					DBUtil.close(conn);
				}else{
					PreparedStatement pstmtFromProblemsALL = conn.prepareStatement(sqlFromProblemsALL);
					pstmtFromProblemsALL.setInt(1, problemid);
					ResultSet rsFromProblemsALL = pstmtFromProblemsALL.executeQuery();
					if(rsFromProblemsALL.next()){
						String title = rsFromProblemsALL.getString(1);
						String content = rsFromProblemsALL.getString(2);
						String username = rsFromProblemsALL.getString(3);
						String name = rsFromProblemsALL.getString(4);
						int sex = rsFromProblemsALL.getInt(5);
						int givedpoints = rsFromProblemsALL.getInt(6);
						int picid = rsFromProblemsALL.getInt(7);
						String time = rsFromProblemsALL.getString(8);
						
						PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
						pstmtInsert.setString(1, solve_username);
						pstmtInsert.setString(2, solve_name);
						pstmtInsert.setInt(3, problemid);
						pstmtInsert.setString(4, title);
						pstmtInsert.setString(5, content);
						pstmtInsert.setString(6, username);
						pstmtInsert.setString(7, name);
						pstmtInsert.setInt(8, sex);
						pstmtInsert.setInt(9, givedpoints);
						pstmtInsert.setInt(10, picid);
						pstmtInsert.setString(11, time);
						
						pstmtInsert.executeUpdate();
						
						PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete);
						pstmtDelete.setInt(1, problemid);
						
						pstmtDelete.executeUpdate();
						out.write("success");
						DBUtil.close(conn);
					}
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
