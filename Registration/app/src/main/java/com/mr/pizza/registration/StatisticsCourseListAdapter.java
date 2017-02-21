package com.mr.pizza.registration;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by PIZZA on 2017-02-18.
 */

public class StatisticsCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;

    public StatisticsCourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
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
        View v= View.inflate(context, R.layout.statistics, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseRate = (TextView) v.findViewById(R.id.courseRate);

        if(courseList.get(i).getCourseGrade().equals("")){
            courseGrade.setText("all grade");
        }else{
            courseGrade.setText(courseList.get(i).getCourseGrade()+"grade");
        }
        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseDivide.setText(courseList.get(i).getCourseDivide()+"class");
        if(courseList.get(i).getCoursePersonnel()==0){
            coursePersonnel.setText("no limit");
            courseRate.setText("no competition rate");
        }else{
            coursePersonnel.setText("number of applicant: "+courseList.get(i).getCourseRival()+"/"+courseList.get(i).getCoursePersonnel());
            int rate = ((int) (((double) courseList.get(i).getCourseRival()*100/ courseList.get(i).getCoursePersonnel())+0.5));
            courseRate.setText("competition rate: "+ rate +"%" );
            if(rate < 20){
            courseRate.setTextColor(parent.getResources().getColor(R.color.colorSafe));
            }else if(rate <= 50){
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
            }else if(rate <= 100){
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorDanger));
            }else if(rate <= 150){
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorWarning));
            }else{
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorRed));
            }
        }

        v.setTag(courseList.get(i).getCourseID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            System.out.println(response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("The course is deleted")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                StatisticsFragment.totalCredit -= courseList.get(i).getCourseCredit();
                                StatisticsFragment.credit.setText(StatisticsFragment.totalCredit+"credit");
                                courseList.remove(i);
                                notifyDataSetChanged();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("Fail to delete this course")
                                        .setNegativeButton("OK", null)
                                        .create();
                                dialog.show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(userID, courseList.get(i).getCourseID()+"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(deleteRequest);
            }
        });

        return v;
    }

}
