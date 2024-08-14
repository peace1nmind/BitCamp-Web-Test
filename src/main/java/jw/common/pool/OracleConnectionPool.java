package jw.common.pool;

import java.sql.Connection;

import oracle.jdbc.pool.OracleConnectionCacheImpl;

/*
* Oracle API에서 제공하는 connectionPool기능 이용 구현 
* OracleConnectionCacheImpl.class을 이용하여 ConnectionPool 구현
* << Singleton pattern 이용 >>
*/
public class OracleConnectionPool {
	/// Field
	//==>singleTon pattern 규칙 1 : 자신의 객체를 static 으로 선언
	private static OracleConnectionPool instance;
	private OracleConnectionCacheImpl occi;
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private String user = "scott";
	private String password = "tiger";
	private int minConn = 5;				//보유할 connnection의 최소수
	private int maxConn = 10;			//보유할 connnection의 최대수
	
	/// Constructor
	//==>singleTon pattern 규칙 2 : 생성자는 private 선언 ::외부에서 생성불가
	private OracleConnectionPool() {
		try {
			occi = new OracleConnectionCacheImpl();
			occi.setURL(url);
			occi.setUser(user);
			occi.setPassword(password);
			occi.setMinLimit(minConn);
			occi.setMaxLimit(maxConn);
		} catch(Exception e) {			e.printStackTrace();			}	
	}
	
	/// Method  For only one instance 생성
	//==>singleTon pattern 규칙 3 : 자신의 인스턴스 return 하는 static metod
	public static synchronized OracleConnectionPool getInstance() {		
		if(instance==null) instance = new OracleConnectionPool();		
		return instance;
	}
	
	// occi가 가지고 있는 con을 넘긴다(5 ~ 10)
	public synchronized Connection getConnection() {
		Connection conn = null;
		try{	conn = occi.getConnection();	}
		catch(Exception e) {	e.printStackTrace();	}
		return conn;
	}
	
	// occi가 가지고 있는 con을 닫는다.(5 ~ 10)
	public synchronized void close() {
		try {	occi.close();		}
		catch(Exception e) {	e.printStackTrace();	}
	}
	
	//////////////////////////// 아래의 두 method는 test용이다. //////////////////
	//만들어진 모든 connection수
	public int getCacheSize()		{		return occi.getCacheSize();	 	}
	//실제로 Client에서 빌려간(사용중인) Connection 수	
	public int getActiveSize()		{		return occi.getActiveSize();		}
	
}//end of class