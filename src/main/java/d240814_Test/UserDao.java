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
	  ���̺� ���� SQL��
	  ** HTML�� input�� name�� �����ؾ��Ѵ� **
	  
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
	// Html�� parameter �̸��� DB�� column �̸��� �����ؾ���
	// service �Ǵ� doPost���� reqest�� Dao�� �ѱ�⸸ �ϸ� ���� ���� (�����ڷ� �Ǵ� �޼��� �μ���)
	public void addUser(HttpServletRequest req) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		// SQL���� �յڷ� ���� (front, back)
		String sqlf = "INSERT INTO users_test (";
		String sqlb = "VALUES (";
		
		// getParameterNames�� ArrayList�� ����
		Enumeration<String> params = req.getParameterNames();
		List<String> paramList = new ArrayList<String>();
		
		while (params.hasMoreElements()) {
			String param =  params.nextElement();
			paramList.add(param);
		}
		
		// parameter �̸����� �� ArrayList�� for�� ������ SQL�� �ϼ�
		for (int i = 0; i < paramList.size(); i++) {
			
			// �Ķ���� �̸�, ��, NullString ���� Ȯ��
			System.out.println(String.format("paramName= %-15s\tparamValue= %-15s\t inNull= %b", paramList.get(i), req.getParameter(paramList.get(i)), req.getParameter(paramList.get(i)).equals("")));
			
			sqlf += paramList.get(i) + ((i<paramList.size()-1) ? ", " : "");
			
			if (!req.getParameter(paramList.get(i)).equals("")) {
				sqlb += "\'" + req.getParameter(paramList.get(i)) + "\'" + ((i<paramList.size()-1) ? ", " : "");
			} else {
				sqlb += "NULL" + ((i<paramList.size()-1) ? ", " : "");
			}
			
		}
		
		// �ϼ��� SQL��
		String sql = sqlf + ") " +sqlb + ")";
		
		System.out.println("\nSQL : "+sql+"\n");
		
		// ���� ������ �����ϰ� SQL�� ����
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
