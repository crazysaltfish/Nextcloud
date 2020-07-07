package Info;
/*
 * 老师教课表
 */
public class TeachCourse {
	//老师的id
	String id_teacher;
	//课程的id
	String id_course;
	public String getId_teacher() {
		return id_teacher;
	}
	public void setId_teacher(String id_teacher) {
		this.id_teacher = id_teacher;
	}
	public String getId_course() {
		return id_course;
	}
	public void setId_course(String id_course) {
		this.id_course = id_course;
	}
	public TeachCourse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TeachCourse(String id_teacher, String id_course) {
		super();
		this.id_teacher = id_teacher;
		this.id_course = id_course;
	}
	@Override
	public String toString() {
		return "TeachCourse [id_teacher=" + id_teacher + ", id_course="
				+ id_course + "]";
	}
}
