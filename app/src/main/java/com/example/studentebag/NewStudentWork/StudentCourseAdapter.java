package com.example.studentebag.NewStudentWork;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentebag.Class.CourseItem;
import com.example.studentebag.R;

import java.util.List;

public class StudentCourseAdapter extends RecyclerView.Adapter<StudentCourseAdapter.ViewHolder>{
    private List<CourseItem> mCourseItem;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View studentCourseView;
        TextView studentCourseTitle;
        TextView studentCourseId;

        public ViewHolder(View view){
            super(view);
            studentCourseView = view;
            studentCourseTitle = (TextView)view.findViewById(R.id.course_title);
            studentCourseId = (TextView)view.findViewById(R.id.course_id);
        }
    }

    public StudentCourseAdapter(List<CourseItem> studentCourseList){
        mCourseItem =studentCourseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.studentCourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CourseItem courseItem = mCourseItem.get(position);

                Intent intent = new Intent(parent.getContext(), TeacherWorkActivity.class);
                intent.putExtra("CourseId", courseItem.getId());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CourseItem courseItem = mCourseItem.get(position);
        holder.studentCourseTitle.setText(courseItem.getTitle());
        holder.studentCourseId.setText(courseItem.getId());
    }

    @Override
    public int getItemCount(){
        return mCourseItem.size();
    }
}
