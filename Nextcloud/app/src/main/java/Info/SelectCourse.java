package Info;
/*
 * 学生选课表
 */
public class SelectCourse {
	//课程编号
	String ic_course;
	//老师编号
	String id_teacher;
	//学生编号
	String id_student;
	public String getIc_course() {
		return ic_course;
	}
	public void setIc_course(String ic_course) {
		this.ic_course = ic_course;
	}
	public String getId_teacher() {
		return id_teacher;
	}
	public void setId_teacher(String id_teacher) {
		this.id_teacher = id_teacher;
	}
	public String getId_student() {
		return id_student;
	}
	public void setId_student(String id_student) {
		this.id_student = id_student;
	}
	public SelectCourse(String ic_course, String id_teacher, String id_student) {
		super();
		this.ic_course = ic_course;
		this.id_teacher = id_teacher;
		this.id_student = id_student;
	}
	public SelectCourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SelectCourse [ic_course=" + ic_course + ", id_teacher="
				+ id_teacher + ", id_student=" + id_student + "]";
	}
	
}
