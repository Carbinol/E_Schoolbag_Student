package com.example.studentebag.CheckStudentWork;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentebag.Class.StudentWorkItem;
import com.example.studentebag.R;

import java.util.List;

public class StudentWorkAdapter extends RecyclerView.Adapter<StudentWorkAdapter.ViewHolder>{
    private List<StudentWorkItem> mStudentWorkItem;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View studentWorkItemView;
        TextView studentWorkItemTitle;
        TextView studentWorkItemTime;

        public ViewHolder(View view){
            super(view);
            studentWorkItemView = view;
            studentWorkItemTitle = (TextView)view.findViewById(R.id.student_work_title);
            studentWorkItemTime = (TextView)view.findViewById(R.id.student_work_time);
        }
    }

    public StudentWorkAdapter(List<StudentWorkItem> studentWorkItemList){
        mStudentWorkItem = studentWorkItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_work_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.studentWorkItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                StudentWorkItem studentWorkItem = mStudentWorkItem.get(position);

                Intent intent = new Intent(parent.getContext(), EditStudentWorkActivity.class);
                intent.putExtra("Title", studentWorkItem.getTitle());
                intent.putExtra("TeacherWorkId", studentWorkItem.getTeacherWorkId());
                intent.putExtra("StudentWorkId", studentWorkItem.getStudentWorkId());
                intent.putExtra("Content", studentWorkItem.getContent());
                    intent.putExtra("Score", studentWorkItem.getScore());
                    intent.putExtra("Remark", studentWorkItem.getRemark());
                parent.getContext().startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        StudentWorkItem teacherWorkItem = mStudentWorkItem.get(position);
        holder.studentWorkItemTitle.setText(teacherWorkItem.getTitle());
        holder.studentWorkItemTime.setText(teacherWorkItem.getCommitDate());
    }

    @Override
    public int getItemCount(){
        return mStudentWorkItem.size();
    }
}
