package Info;
/*
 * 课程表
 */
public class Course {
	//课程的编号
	String ID_c;
	//课程的名称
	String name_c;
	//课程性质
	String character_c;
	//学分
	String credit_c;
	//考核方式
	String textmethed_c;
	//开课学院
	String academy_c;
	//课程开始时间（周）
	String startweek;
	//课程结束时间（周）
	String endweek;
	//该课程是周几（1-7）
	String date;
	//开始上课
	String starttime;
	//结束上课
	String endtime;
	//当前课程进度
	String proceeding;
	//课程作业
	String homework;
	//课程的上课地点
	String location;
	public String getID_c() {
		return ID_c;
	}
	public void setID_c(String iD_c) {
		ID_c = iD_c;
	}
	public String getName_c() {
		return name_c;
	}
	public void setName_c(String name_c) {
		this.name_c = name_c;
	}
	public String getCharacter_c() {
		return character_c;
	}
	public void setCharacter_c(String character_c) {
		this.character_c = character_c;
	}
	public String getCredit_c() {
		return credit_c;
	}
	public void setCredit_c(String credit_c) {
		this.credit_c = credit_c;
	}
	public String getTextmethed_c() {
		return textmethed_c;
	}
	public void setTextmethed_c(String textmethed_c) {
		this.textmethed_c = textmethed_c;
	}
	public String getAcademy_c() {
		return academy_c;
	}
	public void setAcademy_c(String academy_c) {
		this.academy_c = academy_c;
	}
	public String getStartweek() {
		return startweek;
	}
	public void setStartweek(String startweek) {
		this.startweek = startweek;
	}
	public String getEndweek() {
		return endweek;
	}
	public void setEndweek(String endweek) {
		this.endweek = endweek;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getProceeding() {
		return proceeding;
	}
	public void setProceeding(String proceeding) {
		this.proceeding = proceeding;
	}
	public String getHomework() {
		return homework;
	}
	public void setHomework(String homework) {
		this.homework = homework;
	}
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Course(String iD_c, String name_c, String character_c,
			String credit_c, String textmethed_c, String academy_c,
			String startweek, String endweek, String date, String starttime,
			String endtime, String proceeding, String homework, String location) {
		super();
		ID_c = iD_c;
		this.name_c = name_c;
		this.character_c = character_c;
		this.credit_c = credit_c;
		this.textmethed_c = textmethed_c;
		this.academy_c = academy_c;
		this.startweek = startweek;
		this.endweek = endweek;
		this.date = date;
		this.starttime = starttime;
		this.endtime = endtime;
		this.proceeding = proceeding;
		this.homework = homework;
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
