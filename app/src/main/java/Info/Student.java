package Info;
/*
 * 学生表
 */
public class Student {
	//学生学号，也就是登陆账号
	String ID_s;
	//学生姓名
	String name_s;
	//学生的登陆密码
	String password_s;
	//学生的手机号
	String phone_s;
	//学生的班级
	String class_s;
	//该学生目前正在点名的课程
	String nowclass;
	public String getID_s() {
		return ID_s;
	}
	public void setID_s(String iD_s) {
		ID_s = iD_s;
	}
	public String getName_s() {
		return name_s;
	}
	public void setName_s(String name_s) {
		this.name_s = name_s;
	}
	public String getPassword_s() {
		return password_s;
	}
	public void setPassword_s(String password_s) {
		this.password_s = password_s;
	}
	public String getPhone_s() {
		return phone_s;
	}
	public void setPhone_s(String phone_s) {
		this.phone_s = phone_s;
	}
	public String getClass_s() {
		return class_s;
	}
	public void setClass_s(String class_s) {
		this.class_s = class_s;
	}
	public String getNowclass() {
		return nowclass;
	}
	public void setNowclass(String nowclass) {
		this.nowclass = nowclass;
	}
	@Override
	public String toString() {
		return "Student [ID_s=" + ID_s + ", name_s=" + name_s + ", password_s="
				+ password_s + ", phone_s=" + phone_s + ", class_s=" + class_s
				+ ", nowclass=" + nowclass + "]";
	}
	public Student(String iD_s, String name_s, String password_s,
			String phone_s, String class_s, String nowclass) {
		super();
		ID_s = iD_s;
		this.name_s = name_s;
		this.password_s = password_s;
		this.phone_s = phone_s;
		this.class_s = class_s;
		this.nowclass = nowclass;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

}
