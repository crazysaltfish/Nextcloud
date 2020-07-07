package URLAndInterface;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

import Info.Course;
import Info.Student;
import Info.Teacher;

/*
 * 封装Volley框架
 * 使用步骤：new创建新的对象，然后调用相应方法，并实现onSuccess方法，在方法内实现成功访问网络的相关操作,
 *                                          实现onerror方法，在方法内部实现访问失败的相关操作。
 * Interface interface1=new Interface(MainActivity.this);
 interface1.loading(String ID_t,String password_t,new VolleyCallback() {
 @Override
 public void onSuccess(String result) {
 //根据返回的result进行相关的操作
 }
 @Override
 public void onerror(String error) {

 }
 });	
 */
public class Interface {
	private Context context;

	public Interface(Context c) {
		context = c;
	}

	public interface VolleyCallback {
		void onSuccess(String result);

		void onerror(String error);
	}
	public interface VolleyCallback_course {
		void onSuccess(Course course);

		void onerror(String error);
	}
	public interface VolleyCallback_student {
		void onSuccess(Student student);

		void onerror(String error);
	}
	public interface VolleyCallback_student_stopsignin {
		void onSuccess(String all, String signined, String notsigned, String[] idofnotsigned);

		void onerror(String error);
	}
	public interface VolleyCallback_Stringshuzu {
		void onSuccess(String[] result);

		void onerror(String error);
	}
	public interface VolleyCallback_Teacher {
		void onSuccess(Teacher teacher);

		void onerror(String error);
	}

	//通过老师id来获取个人信息
	public void getTeacherInfoById(String id_teacher,final VolleyCallback_Teacher callback_Teacher){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.getTeacherInfoById+"?id_teacher="+id_teacher;
		StringRequest request=new StringRequest(url,new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					response =new String(response.getBytes("iso8859-1"),"gbk");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String[] info=response.split("\\s+");
				Teacher teacher=new Teacher(info[0], info[1], info[2], info[3], info[4], info[5]);
				callback_Teacher.onSuccess(teacher);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback_Teacher.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//返回这个老师教的所有课程的id
	public void getAllTeachClass(String id_teacher,final VolleyCallback_Stringshuzu callback_Stringshuzu){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.getAllTeachClass+"?id_teacher="+id_teacher;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback_Stringshuzu.onSuccess(response.split("\\s+"));
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback_Stringshuzu.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//老师开始点名
	public void TeacherStartSignIn(String mac,String id_course,String id_teacher,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.TeacherStartSignIn+"?mac="+mac+"&id_course="+id_course+"&id_teacher="+id_teacher;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//老师停止点名
	public void TeacherStopSignIn(String id_course,final VolleyCallback_student_stopsignin callback_student_stopsignin){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.TeacherStopSignIn+"?id_course="+id_course;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				String[] info=response.split("\\s+");
				String[] id_studnets=new String[info.length-3];
				for(int i=0;i<id_studnets.length;i++){
					id_studnets[i]=info[i+3];
				}
				callback_student_stopsignin.onSuccess(info[0],info[1],info[2],id_studnets);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback_student_stopsignin.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//学生开始点名
	public void StudentSignIn(String mac,String id_course,String id_student,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.StudentSignIn+"?mac="+mac+"&id_course="+id_course+"&id_student="+id_student;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//学生通过老师进行签到
	public void StudentSignInByTeacher(String id_course,String id_student,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.StudentSignInByTeacher+"?id_course="+id_course+"&id_student="+id_student;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//	得到学生的全部信息
	public void getStudentInfoByID(String id_student,final VolleyCallback_student callback_student){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.getStudentInfoByID+"?id_student="+id_student;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					response =new String(response.getBytes("iso8859-1"),"gbk");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String[] info=response.split("\\s+");
				Student student=new Student(info[0], info[1], info[2], info[3], info[4], info[5]);
				callback_student.onSuccess(student);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback_student.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//得到某一个学生的所有选的课程ID
	public void getAllSelectCourse(String id_student,final VolleyCallback_Stringshuzu callback_Stringshuzu){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.getAllSelectCourse+"?id_student="+id_student;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback_Stringshuzu.onSuccess(response.split("\\s+"));
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback_Stringshuzu.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//根据课程id来获得课程信息
	public void getCourseInfoByID(String id_course,final VolleyCallback_course callback_course){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.getCourseInfoByID+"?id_course="+id_course;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					response =new String(response.getBytes("iso8859-1"),"gbk");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String []info=response.split("&");
				callback_course.onSuccess(new Course(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9], info[10], info[11],info[12],info[13]));
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback_course.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//老师登陆
	public void TeacherLoading(String id_teacher,String password,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.TeacherLoading+"?id_teacher="+id_teacher+"&password_t="+password;
		StringRequest request=new StringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//学生登陆
		public void StudentLoading(String id_student,String password,final VolleyCallback callback){
			RequestQueue queue=Volley.newRequestQueue(context);
			String url=URLInfo.StudentLoading+"?id_student="+id_student+"&password_s="+password;
			StringRequest request=new StringRequest(url, new Listener<String>() {
				@Override
				public void onResponse(String response) {
					callback.onSuccess(response);
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					callback.onerror(error.toString());
				}
			});
			queue.add(request);
		}
	//根据课程id获得老师id
	public void getTeacherIdByCourseId(String id_course,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url=URLInfo.getTeacherIdByCourseId+"?id_course="+id_course;
		StringRequest request=new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}
	//发送短信给指定号码
	public void sendMessage(String teachername,String coursename,String phonenumber,String time,String location,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url="http://47.100.103.17:8080/Dx/duanxin?teachername="+teachername+"&coursename="+coursename+"&phonenumber="+phonenumber+"&time="+time+"&location="+location;
		StringRequest request=new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}

	//服务外包登录
	public void SignIn(String username,String password,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url1="http://101.132.121.32:8080/ServletTest/LoginServlet"+"?username="+username+"&password="+password;
		StringRequest request=new StringRequest(url1, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}

	//人脸识别
	public void facecheck(String face1,String face2,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url1="http://101.132.121.32:8080/ServletTest/LinuxTest?cmd1="+face1+"&cmd2="+face2;
		StringRequest request=new StringRequest(url1, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}

	//图片转码
	public void transcoding(String picturename,final VolleyCallback callback){
		RequestQueue queue=Volley.newRequestQueue(context);
		String url1="http://101.132.121.32:8080/ServletTest/Linuxffmpeg?cmd1="+picturename;
		StringRequest request=new StringRequest(url1, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				callback.onSuccess(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onerror(error.toString());
			}
		});
		queue.add(request);
	}
}