package d240814_Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddUser() {
        super();
    }
	
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		// �ѱ� ���ڵ�
		req.setCharacterEncoding("euc-kr");
		res.setContentType("text/html;charset=euc-kr");
		PrintWriter out = res.getWriter();
		
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
		
//		UserVO userVO = new UserVO(req.getParameter("name"), 
//									req.getParameter("gender"), 
//									birth, 
//									req.getParameter("calendarType"), 
//									req.getParameter("edu"), 
//									req.getParameter("job"), 
//									req.getParameter("dept"), 
//									req.getParameter("marriage"), 
//									req.getParameter("child"), 
//									tel, 
//									req.getParameter("telPlace"), 
//									phone, 
//									req.getParameter("telCompany"),
//									req.getParameter("addr"));
		
		UserVO userVO = new UserVO();
		List<String> fields = userVO.getFields();
		
		// 
		Enumeration<String> params = req.getParameterNames();
		List<String> valueList = new ArrayList<String>();
		
		// parameter �̸��� �߿��� �ʵ尪 ������ ��������ġ�� �־ valueList�� ����
		while (params.hasMoreElements()) {
			String param =  params.nextElement();
			
			for (String field : fields) {
				if (param.equals(field)) {
					valueList.add(fields.indexOf(field), req.getParameter(param));
				}
			}
		}
		
		System.out.println("valueList = "+valueList+"\n");
		
		userVO = new UserVO(valueList);
		
		System.out.println("userVO Info = "+userVO.getInfo()+"\n");
		
		// ���1 userVO�� ���ڷ� addUser
		UserDao userDao = new UserDao();
//		userDao.addUser(userVO);
		
		// ���2 
		// service�� �Ű������� ���� request�� �״�� �����Ͽ� �޼������
		userDao.addUser(req);
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>  </title>");
		out.println("</head>");
		
		out.println(String.format("<h1>%s</h1>", 
				(userDao.isDbResult())? "���� �����Ͽ����ϴ�":"���� �����Ͽ����ϴ�"));
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>  </title>");
		out.println("</head>");
		
	}// service end
	
}// class end
