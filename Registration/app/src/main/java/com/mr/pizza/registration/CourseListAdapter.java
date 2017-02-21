package com.mr.pizza.registration;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PIZZA on 2017-02-18.
 */

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    private Schedule schedule = new Schedule();
    private List<Integer> courseIDList;
    public static int totalCredit = 0;

    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        new BackgroundTask().execute();
        totalCredit = 0;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v= View.inflate(context, R.layout.course, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        if(courseList.get(i).getCourseGrade().equals("")){
            courseGrade.setText("all grade");
        }else{
            courseGrade.setText(courseList.get(i).getCourseGrade()+"grade");
        }
        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseCredit.setText(courseList.get(i).getCourseCredit()+"credit");
        courseDivide.setText(courseList.get(i).getCourseDivide()+"class");
        if(courseList.get(i).getCoursePersonnel()==0){
            coursePersonnel.setText("no limit");
        }else{
            coursePersonnel.setText("personnel limit:"+courseList.get(i).getCoursePersonnel());
        }
        courseProfessor.setText(courseList.get(i).getCourseProfessor());
        courseTime.setText(courseList.get(i).getCourseTime()+"");


        v.setTag(courseList.get(i).getCourseID());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                boolean validate = false;
                validate = schedule.validate(courseList.get(i).getCourseTime());
                if(!alreadyIn(courseIDList, courseList.get(i).getCourseID())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("The course is already added")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                }else if(totalCredit + courseList.get(i).getCourseCredit()>24){
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("Total credits can not surpass 24.")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                }else if(validate == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("The course time is coincided")
                            .setPositiveButton("OK", null)
                            .create();
                    dialog.show();
                }else{
                    Response.Listener<String> responseListener = new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response) {
                            try{
                                System.out.println(response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("The course is added")
                                            .setPositiveButton("OK", null)
                                            .create();
                                    dialog.show();
                                    courseIDList.add(courseList.get(i).getCourseID());
                                    schedule.addSchedule(courseList.get(i).getCourseTime());
                                    totalCredit += courseList.get(i).getCourseCredit();
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = builder.setMessage("Fail to add this course")
                                            .setNegativeButton("OK", null)
                                            .create();
                                    dialog.show();
                                }

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    AddRequest addRequest = new AddRequest(userID, courseList.get(i).getCourseID()+"", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }
            }
        });
        return v;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected  void onPreExecute(){
            try{
                target = "http://yiyobaby.dothome.co.kr/android/ScheduleList.php?userID="+
                        URLEncoder.encode(userID, "UTF-8");

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result){
            try{
                System.out.println(result);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor;
                String courseTime;
                int courseID;
                totalCredit=0;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    totalCredit += object.getInt("courseCredit");
                    courseIDList.add(courseID);
                    schedule.addSchedule(courseTime);
                    count++;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    public boolean alreadyIn(List<Integer> courseIDList, int item){
        for(int i=0; i<courseIDList.size(); i++){
            if(courseIDList.get(i)==item){
                return false;
            }
        }
        return true;
    }
}
