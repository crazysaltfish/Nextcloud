package URLAndInterface;

public class URLInfo {
	public static String IP="http://47.100.103.17:8080";
	public static String servlet=IP+"/Service_final";
	public static String teacherloadingurl=servlet+"/teacherloading";
	public static String studentloadingurl=servlet+"/studentloading";
	public static String studentregisterurl=servlet+"/studentregister";
	public static String teacherregisterurl=servlet+"/teacherregister";

	//http://101.132.121.32:8080/ServletTest/LinuxTest?cmd1=imga&cmd2=imgb  人脸识别
	//http://101.132.121.32:8080/ServletTest/Linuxffmpeg?cmd1=ac            转码

	//得到老师的全部信息
	public static String getTeacherInfoById=servlet+"/getTeacherInfoById";
	//得到这个老师教的全部课程
	public static String getAllTeachClass=servlet+"/getAllTeachClass";
	//老师点击开始点名
	public static String TeacherStartSignIn=servlet+"/TeacherStartSignIn";
	//老师停止点名
	public static String TeacherStopSignIn=servlet+"/TeacherStopSignIn";
	//学生开始签到
	public static String StudentSignIn=servlet+"/StudentSignIn";
	//学生通过老师进行签到
	public static String StudentSignInByTeacher=servlet+"/StudentSignInByTeacher";
	//得到学生的全部信息
	public static String getStudentInfoByID=servlet+"/getStudentInfoByID";
	//得到某一个学生的选课信息
	public static String getAllSelectCourse=servlet+"/getAllSelectCourse";
	//根据课程id来获得课程的所有信息
	public static String getCourseInfoByID=servlet+"/getCourseInfoByID";
	//老师登陆
	public static String TeacherLoading=servlet+"/TeacherLoading";
	//学生登陆
	public static String StudentLoading=servlet+"/StudentLoading";
	//根据课程id获得老师id
	public static String getTeacherIdByCourseId=servlet+"/getTeacherIdByCourseId";
	//发送短信
	public static String senMessage=IP+"/Dx/duanxin";

}
//http://47.100.103.17:8080/Service/studentloading
