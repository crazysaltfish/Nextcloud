package Info;
/*
 * 签到表
 */
public class SignIn {
	//mac地址
	String mac;
	//课程的id
	String id_course;
	//学生的id
	String id_student;
	//该学生是否签到
	String yesorno;
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getId_course() {
		return id_course;
	}
	public void setId_course(String id_course) {
		this.id_course = id_course;
	}
	public String getId_student() {
		return id_student;
	}
	public void setId_student(String id_student) {
		this.id_student = id_student;
	}
	public String getYesorno() {
		return yesorno;
	}
	public void setYesorno(String yesorno) {
		this.yesorno = yesorno;
	}
	public SignIn(String mac, String id_course, String id_student,
			String yesorno) {
		super();
		this.mac = mac;
		this.id_course = id_course;
		this.id_student = id_student;
		this.yesorno = yesorno;
	}
	public SignIn() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SignIn [mac=" + mac + ", id_course=" + id_course
				+ ", id_student=" + id_student + ", yesorno=" + yesorno + "]";
	}

}
