package Info;
/*
 * 老师表
 */
public class Teacher {
	//老师工号，也就是登陆账号
	String id_teacher;
	//老师姓名
	String name_t;
	//老师的登陆密码
	String password_t;
	//老师的电话
	String phonenumber_t;
	//老师的电子邮箱
	String email_t;
	//老师的办公室
	String office_t;
	public String getId_teacher() {
		return id_teacher;
	}
	public void setId_teacher(String id_teacher) {
		this.id_teacher = id_teacher;
	}
	public String getName_t() {
		return name_t;
	}
	public void setName_t(String name_t) {
		this.name_t = name_t;
	}
	public String getPassword_t() {
		return password_t;
	}
	public void setPassword_t(String password_t) {
		this.password_t = password_t;
	}
	public String getPhonenumber_t() {
		return phonenumber_t;
	}
	public void setPhonenumber_t(String phonenumber_t) {
		this.phonenumber_t = phonenumber_t;
	}
	public String getEmail_t() {
		return email_t;
	}
	public void setEmail_t(String email_t) {
		this.email_t = email_t;
	}
	public String getOffice_t() {
		return office_t;
	}
	public void setOffice_t(String office_t) {
		this.office_t = office_t;
	}
	@Override
	public String toString() {
		return "Teacher [id_teacher=" + id_teacher + ", name_t=" + name_t
				+ ", password_t=" + password_t + ", phonenumber_t="
				+ phonenumber_t + ", email_t=" + email_t + ", office_t="
				+ office_t + "]";
	}
	public Teacher(String id_teacher, String name_t, String password_t,
			String phonenumber_t,  String office_t,String email_t) {
		super();
		this.id_teacher = id_teacher;
		this.name_t = name_t;
		this.password_t = password_t;
		this.phonenumber_t = phonenumber_t;
		this.email_t = email_t;
		this.office_t = office_t;
	}
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
