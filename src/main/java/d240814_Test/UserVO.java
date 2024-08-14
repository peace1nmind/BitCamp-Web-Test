package d240814_Test;
// W D 

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserVO {

	// Field
	private String name;
	private String gender;
	private String birth;
	private String calendarType;
	private String edu;
	private String job;
	private String dept;
	private String marriage;
	private String child;
	private String tel;
	private String telPlace;
	private String phone;
	private String telCompany;
	private String addr;

	
	// Constructor
	public UserVO() {
	}

	public UserVO(String name, String gender, String birth, String calendarType, String edu, String job, String dept,
			String marriage, String child, String tel, String telPlace, String phone, String telCompany, String addr) {
		super();
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.calendarType = calendarType;
		this.edu = edu;
		this.job = job;
		this.dept = dept;
		this.marriage = marriage;
		this.child = child;
		this.tel = tel;
		this.telPlace = telPlace;
		this.phone = phone;
		this.telCompany = telCompany;
		this.addr = addr;
	}
	
	// Servlet에서 getParamNames를 통해 필드의 순서에 맞게 List를 만들어 인자로 받아 상태값 세팅
	public UserVO(List<String> valueList) {
		name = valueList.get(0);
		gender = valueList.get(1);
		birth = valueList.get(2);
		calendarType = valueList.get(3);
		edu = valueList.get(4);
		job = valueList.get(5);
		dept = valueList.get(6);
		marriage = valueList.get(7);
		child = valueList.get(8);
		tel = valueList.get(9);
		telPlace = valueList.get(10);
		phone = valueList.get(11);
		telCompany = valueList.get(12);
		addr = valueList.get(13);
	}
	
	public UserVO(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		req.setCharacterEncoding("euc-kr");
		
		String[] births = req.getParameterValues("birth");
		String birth = "";
		String[] tels = req.getParameterValues("tel");
		String tel = "";
		String[] phones = req.getParameterValues("phone");
		String phone = "";
		
		for (int i = 0; i < births.length; i++) {
			if (i<births.length-1) {
				birth += births[i] + "/";
			} else {
				birth += births[i];
			}
		}
		
		for (int i = 0; i < tels.length; i++) {
			if (i<tels.length-1) {
				tel += tels[i] + "-";
			} else {
				tel += tels[i];
			}
		}
		
		for (int i = 0; i < phones.length; i++) {
			if (i<phones.length-1) {
				phone += phones[i] + "-";
			} else {
				phone += phones[i];
			}
		}
		
		new UserVO(req.getParameter("name"), 
				req.getParameter("gender"), 
				birth, 
				req.getParameter("calendarType"), 
				req.getParameter("edu"), 
				req.getParameter("job"), 
				req.getParameter("dept"), 
				req.getParameter("marriage"), 
				req.getParameter("child"), 
				tel, 
				req.getParameter("telPlace"), 
				phone, 
				req.getParameter("telCompany"),
				req.getParameter("addr"));
	}

	
	// Getter Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getbirth() {
		return birth;
	}

	public void setbirth(String birth) {
		this.birth = birth;
	}

	public String getCalendarType() {
		return calendarType;
	}

	public void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTelPlace() {
		return telPlace;
	}

	public void setTelPlace(String telPlace) {
		this.telPlace = telPlace;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelCompany() {
		return telCompany;
	}

	public void setTelCompany(String telCompany) {
		this.telCompany = telCompany;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	// Method
	public List<String> getInfo() {
		
		String[] a = {name, gender, birth, calendarType, edu, job, dept, marriage, child, tel, telPlace, phone, telCompany, addr};
		ArrayList<String> info = new ArrayList<String>(Arrays.asList(a));
		
		return info;
	}
	
	public List<String> getFields() {
		ArrayList<String> fields = new ArrayList<String>(Arrays.asList("name", "gender", "birth", "calendarType", "edu", "job", "dept", "marriage", "child", "tel", "telPlace", "phone", "telCompany", "addr"));
		return fields;
	}
	
}
// class end
