package d240814_Test;
// W4 D19 Task 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jw.common.pool.OracleConnectionPool;

public class UserDao {

	// Field
	boolean dbResult;
	
	
	// Constructor
	public UserDao() {
	}
	

	// Method
	public void addUser(UserVO userVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		List<String> infos = userVO.getInfo();
		
		String sql = "INSERT INTO users_test "
				+ "VALUES (";
		
		for (int i = 0; i < infos.size(); i++) {
			sql = (i<infos.size()-1)? sql+"?,": sql+"?)";
		}
		
		try {
			con = OracleConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(sql);
			
			for (int i = 0; i < infos.size(); i++) {
				pstmt.setString(i+1, infos.get(i));
			}
			
			rs = pstmt.executeUpdate();
			
			if (rs==1) {
				System.out.println("addUser Success");
				dbResult = true;
				
			} else {
				System.out.print("addUser Fail");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				
				if (con!=null) {
					con.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}// method end
	
	
	/* 
	  테이블 생성 SQL문
	  ** HTML의 input의 name도 동일해야한다 **
	  
		CREATE TABLE users_test (
			name VARCHAR2(30),
			gender VARCHAR2(30),
			birth VARCHAR2(30),
			calendarType VARCHAR2(30),
			edu VARCHAR2(30),
			job VARCHAR2(30),
			dept VARCHAR2(60),
			marriage VARCHAR2(30),
			child VARCHAR2(30),
			tel VARCHAR2(30),
			telPlace VARCHAR2(30),
			phone VARCHAR2(30),
			telCompany VARCHAR2(30),
			addr VARCHAR2(200));
		COMMIT;
	
	*/
	// Html의 parameter 이름과 DB의 column 이름이 동일해야함
	// service 또는 doPost에서 reqest를 Dao에 넘기기만 하면 제작 가능 (생성자로 또는 메서드 인수로)
	public void addUser(HttpServletRequest req) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		// SQL문을 앞뒤로 나눔 (front, back)
		String sqlf = "INSERT INTO users_test (";
		String sqlb = "VALUES (";
		
		// getParameterNames를 ArrayList로 변경
		Enumeration<String> params = req.getParameterNames();
		List<String> paramList = new ArrayList<String>();
		
		while (params.hasMoreElements()) {
			String param =  params.nextElement();
			paramList.add(param);
		}
		
		// parameter 이름들이 들어간 ArrayList를 for문 돌리며 SQL문 완성
		for (int i = 0; i < paramList.size(); i++) {
			
			// 파라미터 이름, 값, NullString 여부 확인
			System.out.println(String.format("paramName= %-15s\tparamValue= %-15s\t inNull= %b", paramList.get(i), req.getParameter(paramList.get(i)), req.getParameter(paramList.get(i)).equals("")));
			
			sqlf += paramList.get(i) + ((i<paramList.size()-1) ? ", " : "");
			
			if (!req.getParameter(paramList.get(i)).equals("")) {
				sqlb += "\'" + req.getParameter(paramList.get(i)) + "\'" + ((i<paramList.size()-1) ? ", " : "");
			} else {
				sqlb += "NULL" + ((i<paramList.size()-1) ? ", " : "");
			}
			
		}
		
		// 완성된 SQL문
		String sql = sqlf + ") " +sqlb + ")";
		
		System.out.println("\nSQL : "+sql+"\n");
		
		// 이하 기존과 동일하게 SQL문 실행
		try {
			con = OracleConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeUpdate();
			
			if (rs==1) {
				System.out.println("addUser Success");
				dbResult = true;
				
			} else {
				System.out.print("addUser Fail");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				
				if (con!=null) {
					con.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	public boolean isDbResult() {
		return dbResult;
	}

}
// class end
