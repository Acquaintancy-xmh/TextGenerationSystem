package action;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.regexp.internal.recompile;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.print.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.catalina.util.URLEncoder;
import org.apache.struts2.ServletActionContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class UserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	public static String toUtf8(String str) throws UnsupportedEncodingException {
		return new String(str.getBytes("UTF-8"), "UTF-8");
	}

	public String welcome() {
		return "success";
	}

	public int findAuthorID(String Author) {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			// 创建Connection连接
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement(); // 获取Statement
			String sql = "select* from Author where name= \"" + Author + "\""; // 添加图书信息的SQL语句
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql); // 执行查询
			List<BookBean> list = new ArrayList<>(); // 实例化List对象
			while (rs.next()) {
				BookBean book = new BookBean(); // 实例化Book对象
				book.setAuthorID(rs.getInt("AuthorID")); // 对bookCount属性赋值
				list.add(book); // 将图书对象添加到集合中
			}
			if (list.isEmpty()) {
				return -1;
			}
			int authorid = list.get(0).getAuthorID();
			stmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection
			rs.close();
			return authorid;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public String findAuthor(int AuthorID) {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			// 创建Connection连接
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement(); // 获取Statement
			String sql = "select* from Author where AuthorID= \"" + AuthorID + "\""; // 添加图书信息的SQL语句
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql); // 执行查询
			List<BookBean> list = new ArrayList<>(); // 实例化List对象
			while (rs.next()) {
				BookBean book = new BookBean(); // 实例化Book对象
				rs.getString("name");
				book.setName(rs.getString("name")); // 对bookCount属性赋值
				list.add(book); // 将图书对象添加到集合中
			}
			if (list.isEmpty()) {
				return "";
			}
			String author = list.get(0).getName();
			stmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection
			rs.close();
			return author;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String findBook() throws UnsupportedEncodingException {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			// 创建Connection连接
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement(); // 获取Statement
			String sql = "select* from Author where Name = \"" + request.getParameter("Author") + "\""; // 添加图书信息的SQL语句
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql); // 执行查询
			List<Integer> idlist = new ArrayList<Integer>();
			while (rs.next()) {
				idlist.add(rs.getInt("AuthorID")); // 将图书对象添加到集合中
			}
			stmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection

			List<BookBean> list = new ArrayList<>(); // 实例化List对象
			for (int id : idlist) {
				Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
				Connection conn1 = DriverManager.getConnection(url, username, password);
				Statement stmt1 = conn1.createStatement(); // 获取Statement
				String sql1 = "select* from book where AuthorID= \"" + id + "\""; // 添加图书信息的SQL语句
				System.out.println(sql1);
				ResultSet rs1 = stmt1.executeQuery(sql1); // 执行查询

				while (rs1.next()) {
					BookBean book = new BookBean(); // 实例化Book对象
					book.setISBN(rs1.getString("ISBN")); // 对id属性赋值
					book.setTitle(rs1.getString("Title")); // 对name属性赋值
					book.setPrice(rs1.getDouble("price")); // 对price属性赋值
					book.setAuthorID(rs1.getInt("AuthorID")); // 对bookCount属性赋值
					book.setPublisher(rs1.getString("Publisher"));// 对author属性赋值
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = new java.util.Date();
					String str = sdf.format(rs1.getDate("PublishDate"));
					book.setPublishDate(str);
					list.add(book); // 将图书对象添加到集合中
				}
			}
			if (findAuthorID(request.getParameter("Author")) == -1) { // 查无此作者
				BookBean book = new BookBean();
				book.setPrice(-2);
				list.add(book);
				session.setAttribute("result", list);
				System.out.println(book.getPrice());
				return "success";
			}
			if (list.isEmpty()) { // 查无图书
				BookBean book = new BookBean();
				book.setPrice(-1);
				list.add(book);
				session.setAttribute("result", list);
				System.out.println(book.getPrice());
				return "success";
			}
			System.out.println(list.size());
			session.setAttribute("result", list);
			rs.close(); // 关闭ResultSet
			stmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String details() throws UnsupportedEncodingException {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			// 创建Connection连接
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement(); // 获取Statement
			String sql = "select* from book where ISBN= \"" + request.getParameter("ISBN") + "\""; // 添加图书信息的SQL语句
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql); // 执行查询
			List<BookBean> list = new ArrayList<>(); // 实例化List对象
			while (rs.next()) {
				BookBean book = new BookBean(); // 实例化Book对象
				book.setISBN(rs.getString("ISBN")); // 对id属性赋值
				book.setTitle(rs.getString("Title")); // 对name属性赋值
				book.setPrice(rs.getDouble("price")); // 对price属性赋值
				book.setAuthorID(rs.getInt("AuthorID")); // 对bookCount属性赋值
				book.setPublisher(rs.getString("Publisher"));// 对author属性赋值
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(rs.getDate("PublishDate"));
				book.setPublishDate(str);
				list.add(book); // 将图书对象添加到集合中
			}
			rs.close(); // 关闭ResultSet
			stmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection
			Connection conn1 = DriverManager.getConnection(url, username, password);
			Statement stmt1 = conn1.createStatement(); // 获取Statement
			String sql1 = "select* from Author where AuthorID= \"" + list.get(0).getAuthorID() + "\""; // 添加图书信息的SQL语句
			System.out.println(sql1);
			ResultSet rs1 = stmt1.executeQuery(sql1); // 执行查询
			while (rs1.next()) {
				BookBean book = new BookBean(); // 实例化Book对象
				book.setAge(rs1.getInt("Age"));
				book.setName(rs1.getString("name")); // 对name属性赋值
				book.setAuthorID(rs1.getInt("AuthorID")); // 对bookCount属性赋值
				book.setCountry(rs1.getString("Country"));// 对author属性赋值
				list.add(book); // 将图书对象添加到集合中
			}
			System.out.println(list.get(1).getName());
			session.setAttribute("result", list);
			rs1.close(); // 关闭ResultSet
			stmt1.close(); // 关闭Statement
			conn1.close(); // 关闭Connection
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String addBook() {

		try {
			ServletRequest request = ServletActionContext.getRequest();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			Connection conn = DriverManager.getConnection(url, username, password); // 创建Connection连接
			String sql = "insert into book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(?,?,?,?,?,?)"; // 添加图书信息的SQL语句
			PreparedStatement ps = conn.prepareStatement(sql); // 获取PreparedStatement
			BookBean book = new BookBean();

			java.sql.Date sd;
			java.util.Date ud;
			ud = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("publishdate"));
			sd = new java.sql.Date(ud.getTime());
			if (findAuthor(Integer.parseInt(request.getParameter("authorid"))) == "") {
				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
				String str = "authorerror";
				session.setAttribute("result", str);
				return "authorerror";
			}
			ps.setString(1, request.getParameter("ISBN")); // 对SQL语句中的第1个参数赋值
			ps.setString(2, request.getParameter("title")); // 对SQL语句中的第2个参数赋值
			ps.setInt(3, Integer.parseInt(request.getParameter("authorid"))); // 对SQL语句中的第3个参数赋值
			ps.setString(4, request.getParameter("publisher")); // 对SQL语句中的第4个参数赋值
			ps.setDate(5, sd);
			ps.setDouble(6, Double.parseDouble(request.getParameter("price")));

			int row = ps.executeUpdate(); // 执行更新操作，返回所影响的行数
			if (row > 0) { // 判断是否更新成功
				return "success"; // 更新成输出信息
			}
			ps.close(); // 关闭PreparedStatement，释放资源
			conn.close(); // 关闭Connection，释放资源
		} catch (Exception e) {
			e.printStackTrace();
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String str = "somethingerror";
			session.setAttribute("result", str);
			return "somethingerror";
		}
		return "success";
	}

	public String deleteBook() {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			Connection conn = DriverManager.getConnection(url, username, password); // 创建Connection连接
			String sql = "delete from book where ISBN= \"" + request.getParameter("ISBN") + "\""; // 添加图书信息的SQL语句
			System.out.println(sql);
			PreparedStatement ps = conn.prepareStatement(sql); // 获取PreparedStatement
			int row = ps.executeUpdate(); // 执行更新操作，返回所影响的行数
			if (row > 0) { // 判断是否更新成功
				return "success"; // 更新成输出信息
			}
			ps.close(); // 关闭PreparedStatement，释放资源
			conn.close(); // 关闭Connection，释放资源
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String editBook() {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			// 创建Connection连接
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement(); // 获取Statement
			String sql = "select* from book where ISBN= \"" + request.getParameter("ISBN") + "\""; // 添加图书信息的SQL语句
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql); // 执行查询
			List<BookBean> list = new ArrayList<>(); // 实例化List对象
			while (rs.next()) {
				BookBean book = new BookBean(); // 实例化Book对象
				book.setISBN(rs.getString("ISBN")); // 对id属性赋值
				book.setTitle(rs.getString("Title")); // 对name属性赋值
				book.setPrice(rs.getDouble("price")); // 对price属性赋值
				book.setAuthorID(rs.getInt("AuthorID")); // 对bookCount属性赋值
				book.setPublisher(rs.getString("Publisher"));// 对author属性赋值
				System.out.println(rs.getString("Publisher")+"函数");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(rs.getDate("PublishDate"));
				book.setPublishDate(str);
				list.add(book); // 将图书对象添加到集合中
			}
			session.setAttribute("result", list);
			rs.close(); // 关闭ResultSet
			stmt.close(); // 关闭Statement
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String addAuthor() {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			Connection conn = DriverManager.getConnection(url, username, password); // 创建Connection连接
			String sql = "insert into Author(AuthorID,name,Age,Country) values(?,?,?,?)"; // 添加图书信息的SQL语句
			PreparedStatement ps = conn.prepareStatement(sql); // 获取PreparedStatement
			ps.setString(1, request.getParameter("authorid")); // 对SQL语句中的第1个参数赋值
			ps.setString(2, request.getParameter("name")); // 对SQL语句中的第2个参数赋值
			ps.setInt(3, Integer.parseInt(request.getParameter("age"))); // 对SQL语句中的第3个参数赋值
			ps.setString(4, request.getParameter("country")); // 对SQL语句中的第4个参数赋值

			int row = ps.executeUpdate(); // 执行更新操作，返回所影响的行数
			if (row > 0) { // 判断是否更新成功
				return "success"; // 更新成输出信息
			}
			ps.close(); // 关闭PreparedStatement，释放资源
			conn.close(); // 关闭Connection，释放资源
		} catch (Exception e) {
			e.printStackTrace();
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String str = "authorexist";
			session.setAttribute("result", str);
			return "Authorexist";
		}
		return "success";
	}

	public String deleteandadd() {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			if (findAuthor(Integer.parseInt(request.getParameter("authorid"))) == "") {
				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
				String str = "authorerror_toedit";
				session.setAttribute("result", str);
				return "authorerror_toedit";
			}
			Connection conn = DriverManager.getConnection(url, username, password); // 创建Connection连接
			String sql1 = "delete from book where ISBN= \"" + request.getParameter("ISBN") + "\""; // 添加图书信息的SQL语句
			PreparedStatement ps = conn.prepareStatement(sql1); // 获取PreparedStatement
			int row = ps.executeUpdate(); // 执行更新操作，返回所影响的行数
			String sql = "insert into book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(?,?,?,?,?,?)"; // 添加图书信息的SQL语句
			PreparedStatement ps1 = conn.prepareStatement(sql); // 获取PreparedStatement
			BookBean book = new BookBean();
			java.sql.Date sd;
			java.util.Date ud;
			ud = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("publishdate"));
			sd = new java.sql.Date(ud.getTime());
			ps1.setString(1, request.getParameter("ISBN")); // 对SQL语句中的第1个参数赋值
			ps1.setString(2, request.getParameter("title")); // 对SQL语句中的第2个参数赋值
			ps1.setInt(3, Integer.parseInt(request.getParameter("authorid"))); // 对SQL语句中的第3个参数赋值
			ps1.setString(4, request.getParameter("publisher")); // 对SQL语句中的第4个参数赋值
			ps1.setDate(5, sd);
			ps1.setDouble(6, Double.parseDouble(request.getParameter("price")));

			int row1 = ps1.executeUpdate(); // 执行更新操作，返回所影响的行数
			if (row1 > 0) { // 判断是否更新成功
				return "success"; // 更新成输出信息
			}
			ps.close(); // 关闭PreparedStatement，释放资源
			conn.close(); // 关闭Connection，释放资源
		} catch (Exception e) {
			e.printStackTrace();
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String str = "somethingerror_toedit";
			session.setAttribute("result", str);
			return "somethingerror_toedit";
		}
		return "success";
	}
	
	
	public String showBook() {
		try {
			ServletRequest request = ServletActionContext.getRequest();
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动，注册到驱动管理器
			String url = "jdbc:mysql://118.193.168.225:3306/xmhdatabase?characterEncoding=utf8";// 数据库连接字符串
			String username = "xmh"; // 数据库用户名
			String password = "xmhmysql"; // 数据库密码
			// 创建Connection连接
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement(); // 获取Statement
			String sql = "select* from book"; // 添加图书信息的SQL语句
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql); // 执行查询
			List<BookBean> list = new ArrayList<>(); // 实例化List对象
			while (rs.next()) {
				BookBean book = new BookBean(); // 实例化Book对象
				book.setISBN(rs.getString("ISBN")); // 对id属性赋值
				book.setTitle(rs.getString("Title")); // 对name属性赋值
				book.setPrice(rs.getDouble("price")); // 对price属性赋值
				book.setAuthorID(rs.getInt("AuthorID")); // 对bookCount属性赋值
				book.setPublisher(rs.getString("Publisher"));// 对author属性赋值
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = new java.util.Date();
				String str = sdf.format(rs.getDate("PublishDate"));
				book.setPublishDate(str);
				list.add(book); // 将图书对象添加到集合中
			}
			session.setAttribute("result", list);
			rs.close(); // 关闭ResultSet
			stmt.close(); // 关闭Statement
			conn.close(); // 关闭Connection
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}

}
