package com.jf.datadict.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.DataBaseVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.http.HttpSession;
import java.sql.*;

/**
 * 通用数据库工具类,基于Druid连接池实现
 * 包含以下功能
 * 1.获取连接
 * 2.关闭资源
 * 3.执行通用的更新操作
 * 4.执行通用的查询列表操作
 * 5.执行通用的查询单条记录操作
 * @author Luo
 * https://blog.csdn.net/LxxImagine/article/details/81604408
 */
@PropertySource("classpath:application-dev.properties")
public class DBUtil {

	/**初始连接数**/
	@Value("${spring.datasource.initialSize}")
	private static int initialSize = 5;
	/**最大活动连接数**/
	@Value("${spring.datasource.maxActive}")
	private static int maxActive = 100;
	/**最小闲置连接数**/
	@Value("${spring.datasource.maxIdle}")
	private static int maxIdle = 20;
	/**最小闲置连接数**/
	@Value("${spring.datasource.minIdle}")
	private static int minIdle = 8;
	/**连接耗尽时最大等待获取连接时间**/
	@Value("${spring.datasource.maxWait}")
	private static long maxWait = 60000;
	/**配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒*/
	private static long timeBetweenEvictionRunsMillis = 60000;
	/**配置一个连接在池中最小生存的时间，单位是毫秒*/
	private static long minEvictableIdleTimeMillis = 60000;

	static {
	}
	
	private static DruidDataSource init(HttpSession session) {
		// 获取内存中的表单提交过来的配置内容，将其设置给连接信息
		String url = (String)session.getAttribute("url");
		String username = (String)session.getAttribute("username");
		String password = (String)session.getAttribute("password");

		if (session.getAttribute("pool") != null) {
			return (DruidDataSource) session.getAttribute("pool");
		}

		// 声明druid连接池对象
		DruidDataSource pool;
		pool = new DruidDataSource();
		pool.setUrl(url);
		pool.setUsername(username);
		pool.setPassword(password);
		
		//设置连接池中初始连接数
		pool.setInitialSize(initialSize);
		//设置最大连接数
		pool.setMaxActive(maxActive);
		//设置最小的闲置链接数
		pool.setMinIdle(minIdle);
		//设置最大的等待时间(等待获取链接的时间)
		pool.setMaxWait(maxWait);

		pool.setTestOnBorrow(false);
		pool.setTestOnReturn(false);
		// 在获取连接后，确定是否要进行连接空间时间的检查
		pool.setTestWhileIdle(true);
		pool.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		pool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		// 标记是否删除泄露的连接,如果他们超过了removeAbandonedTimout的限制
		pool.setRemoveAbandoned(true);
		// 设置120秒为超时时间，超时强制回收。感觉小场景可以这么用，但实际并不建议。
		pool.setRemoveAbandonedTimeout(120);

		session.setAttribute("pool", pool);
		return pool;
	}
	
	/**
	 * 链接获取
	 */
	private static Connection getConn(HttpSession session) {
		try {
			DruidDataSource pool = init(session);
			// 此处使用不好的解决方式。可能会比较耗费性能吧，不是很懂毕竟能力有限，不知道要如何设置。。。暂且就写成再次强制初始化吧
			// System.out.println("连接数："+pool.getActiveCount());
			if (pool.getActiveCount() == maxActive-1) {
				pool.close();
			}
			//如果连接池为空或者被异常关闭,则重新初始化一个
			if(pool.isClosed()) {
				init(session);
			}
			return pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 根据给定的带参数占位符的SQL语句，创建 PreparedStatement 对象
	 *
	 * @param SQL
	 *            带参数占位符的SQL语句
	 * @return 返回相应的 PreparedStatement 对象
	 */
	private static PreparedStatement prepare(HttpSession session, String SQL, boolean autoGeneratedKeys) {
		Connection conn = getConn(session);
		if (conn == null) {
			return null;
		}
		PreparedStatement ps;
		/* 设置事务的提交方式 */
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException("设置事务的提交方式为:手动提交时失败: " + e.getMessage());
		}
		try {
			if (autoGeneratedKeys) {
				ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			} else {
				ps = conn.prepareStatement(SQL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException("创建 PreparedStatement 对象失败: " + e.getMessage());
		}

		return ps;

	}

	private static Statement statement(HttpSession session) {
		Connection conn = getConn(session);
		if (conn == null) {
			return null;
		}
		Statement st = null;
		/* 设置事务的提交方式 */
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException("设置事务的提交方式为:手动提交时失败: " + e.getMessage());
		}
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			throw new ServiceException("创建 Statement 对象失败: " + e.getMessage());
		}

		return st;
	}

	public static ResultSet query(HttpSession session, String SQL, Object... params) {
		if (SQL == null || SQL.trim().isEmpty() || !SQL.trim().toLowerCase().startsWith("select")) {
			throw new RuntimeException("你的SQL语句为空或不是查询语句");
		}
		ResultSet rs = null;
		if (params.length > 0) {
			/* 说明 有参数 传入，就需要处理参数 */
			PreparedStatement ps = prepare(session, SQL, false);
			try {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
				rs = ps.executeQuery();
			} catch (SQLException e) {
				throw new ServiceException("执行SQL失败: " + e.getMessage());
			}
		} else {
			/* 说明没有传入任何参数 */
			Statement st = statement(session);
			try {
				rs = st.executeQuery(SQL); // 直接执行不带参数的 SQL 语句
			} catch (SQLException e) {
				throw new ServiceException("执行SQL失败: " + e.getMessage());
			}
		}

		return rs;
	}

	private static Object typeof(Object o) {
		Object r = o;

		if (o instanceof java.sql.Timestamp) {
			return r;
		}
		// 将 java.util.Date 转成 java.sql.Date
		if (o instanceof java.util.Date) {
			java.util.Date d = (java.util.Date) o;
			r = new java.sql.Date(d.getTime());
			return r;
		}
		// 将 Character 或 char 变成 String
		if (o instanceof Character || o.getClass() == char.class) {
			r = String.valueOf(o);
			return r;
		}
		return r;
	}


	public static boolean execute(HttpSession session, String SQL, Object... params) {
		if (SQL == null || SQL.trim().isEmpty() || SQL.trim().toLowerCase().startsWith("select")) {
			throw new RuntimeException("你的SQL语句为空或有错");
		}
		boolean r = false;
		/* 表示 执行 DDL 或 DML 操作是否成功的一个标识变量 */

		/* 获得 被执行的 SQL 语句的 前缀 */
		SQL = SQL.trim();
		SQL = SQL.toLowerCase();
		String prefix = SQL.substring(0, SQL.indexOf(" "));
		String operation = ""; // 用来保存操作类型的 变量
		// 根据前缀 确定操作
		switch (prefix) {
			case "create":
				operation = "create table";
				break;
			case "alter":
				operation = "update table";
				break;
			case "drop":
				operation = "drop table";
				break;
			case "truncate":
				operation = "truncate table";
				break;
			case "insert":
				operation = "insert :";
				break;
			case "update":
				operation = "update :";
				break;
			case "delete":
				operation = "delete :";
				break;
		}
		if (params.length > 0) { // 说明有参数
			PreparedStatement ps = prepare(session, SQL, false);
			Connection c = null;
			try {
				c = ps.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				for (int i = 0; i < params.length; i++) {
					Object p = params[i];
					p = typeof(p);
					ps.setObject(i + 1, p);
				}
				ps.executeUpdate();
				commit(c);
				r = true;
			} catch (SQLException e) {
				rollback(c);
				throw new ServiceException(operation + " 失败: " + e.getMessage());
			}

		} else { // 说明没有参数

			Statement st = statement(session);
			Connection c = null;
			try {
				c = st.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 执行 DDL 或 DML 语句，并返回执行结果
			try {
				st.executeUpdate(SQL);
				commit(c); // 提交事务
				r = true;
			} catch (SQLException e) {
				rollback(c); // 回滚事务
				throw new ServiceException(operation + " 失败: " + e.getMessage());
			}
		}
		return r;
	}

	/*
	 *
	 * @param SQL
	 *            需要执行的 INSERT 语句
	 * @param autoGeneratedKeys
	 *            指示是否需要返回由数据库产生的键
	 * @param params
	 *            将要执行的SQL语句中包含的参数占位符的 参数值
	 * @return 如果指定 autoGeneratedKeys 为 true 则返回由数据库产生的键； 如果指定 autoGeneratedKeys
	 *         为 false 则返回受当前SQL影响的记录数目
	 */
	public static int insert(HttpSession session, String SQL, boolean autoGeneratedKeys, Object... params) {
		int var = -1;
		if (SQL == null || SQL.trim().isEmpty()) {
			throw new RuntimeException("你没有指定SQL语句，请检查是否指定了需要执行的SQL语句");
		}
		// 如果不是 insert 开头开头的语句
		if (!SQL.trim().toLowerCase().startsWith("insert")) {
			// System.out.println(SQL.toLowerCase());
			throw new RuntimeException("你指定的SQL语句不是插入语句，请检查你的SQL语句");
		}
		// 获得 被执行的 SQL 语句的 前缀 ( 第一个单词 )
		SQL = SQL.trim();
		SQL = SQL.toLowerCase();
		if (params.length > 0) { // 说明有参数
			PreparedStatement ps = prepare(session, SQL, autoGeneratedKeys);
			Connection c = null;
			try {
				c = ps.getConnection(); // 从 PreparedStatement 对象中获得 它对应的连接对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				for (int i = 0; i < params.length; i++) {
					Object p = params[i];
					p = typeof(p);
					ps.setObject(i + 1, p);
				}
				int count = ps.executeUpdate();
				if (autoGeneratedKeys) { // 如果希望获得数据库产生的键
					ResultSet rs = ps.getGeneratedKeys(); // 获得数据库产生的键集
					if (rs.next()) { // 因为是保存的是单条记录，因此至多返回一个键
						var = rs.getInt(1); // 获得值并赋值给 var 变量
					}
				} else {
					var = count; // 如果不需要获得，则将受SQL影像的记录数赋值给 var 变量
				}
				commit(c);
			} catch (SQLException e) {
				rollback(c);
				throw new ServiceException("数据保存失败: " + e.getMessage());
			}
		} else { // 说明没有参数
			Statement st = statement(session);
			Connection c = null;
			try {
				c = st.getConnection(); // 从 Statement 对象中获得 它对应的连接对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 执行 DDL 或 DML 语句，并返回执行结果
			try {
				int count = st.executeUpdate(SQL);
				if (autoGeneratedKeys) { // 如果企望获得数据库产生的键
					ResultSet rs = st.getGeneratedKeys(); // 获得数据库产生的键集
					if (rs.next()) { // 因为是保存的是单条记录，因此至多返回一个键
						var = rs.getInt(1); // 获得值并赋值给 var 变量
					}
				} else {
					var = count; // 如果不需要获得，则将受SQL影像的记录数赋值给 var 变量
				}
				commit(c); // 提交事务
			} catch (SQLException e) {
				rollback(c); // 回滚事务
				throw new ServiceException("数据保存失败: " + e.getMessage());
			}
		}
		return var;
	}

	/**
	 * 提交事务
	 */
	private static void commit(Connection c) {
		if (c != null) {
			try {
				c.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 回滚事务
	 */
	private static void rollback(Connection c) {
		if (c != null) {
			try {
				c.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 资源关闭
	 */
	public static void close(Statement stmt, Connection conn) {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Boolean validauteMySqlConnection(DataBaseVO vo){
		String driverClassName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverClassName);
			String url = "jdbc:mysql://" + vo.getIp() + ":" + vo.getPort() + "/mysql?useSSL=false";
			DriverManager.getConnection(url,vo.getUserName(),vo.getPassword());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
