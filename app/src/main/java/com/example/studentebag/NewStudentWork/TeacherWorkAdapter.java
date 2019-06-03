package com.example.studentebag.NewStudentWork;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentebag.Class.TeacherWorkItem;
import com.example.studentebag.R;

import java.util.List;

public class TeacherWorkAdapter extends RecyclerView.Adapter<TeacherWorkAdapter.ViewHolder>{
    private List<TeacherWorkItem> mTeacherWorkItem;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View teacherWorkItemView;
        TextView teacherWorkItemTitle;
        TextView teacherWorkItemCourseid;

        public ViewHolder(View view){
            super(view);
            teacherWorkItemView = view;
            teacherWorkItemTitle = (TextView)view.findViewById(R.id.teacher_work_title);
            teacherWorkItemCourseid = (TextView)view.findViewById(R.id.teacher_work_courseid);
        }
    }

    public TeacherWorkAdapter(List<TeacherWorkItem> teacherWorkItemList){
        mTeacherWorkItem = teacherWorkItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_work_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.teacherWorkItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TeacherWorkItem teacherWorkItem = mTeacherWorkItem.get(position);

                Intent intent = new Intent(parent.getContext(), NewStudentWorkActivity.class);
                intent.putExtra("Title", teacherWorkItem.getTitle());
                intent.putExtra("TeacherWorkId", teacherWorkItem.getTeacherWorkId());
                intent.putExtra("DecContent", teacherWorkItem.getDecContent());
                parent.getContext().startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        TeacherWorkItem teacherWorkItem = mTeacherWorkItem.get(position);
        holder.teacherWorkItemTitle.setText(teacherWorkItem.getTitle());
        holder.teacherWorkItemCourseid.setText(teacherWorkItem.getCourseName());
    }

    @Override
    public int getItemCount(){
        return mTeacherWorkItem.size();
    }
}
