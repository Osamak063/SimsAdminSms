package com.example.osamakhalid.simsadmin.ui.adapters;

import android.content.Context;
import android.renderscript.Sampler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;

import com.example.osamakhalid.simsadmin.R;
import com.example.osamakhalid.simsadmin.consts.Values;
import com.example.osamakhalid.simsadmin.interfaces.Callback;
import com.example.osamakhalid.simsadmin.model.StudentAttendance;

import java.util.List;


/**
 * Created by Osama Khalid on 4/13/2018.
 */

public class StudentAttendanceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<StudentAttendance> list;
    private Callback mCallback;

    public StudentAttendanceListAdapter(Context context, List<StudentAttendance> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_attendance_leave_list_item, parent, false);
            return new StudentAttendanceListAdapter.MyViewHolder2(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_attendance_list_item, parent, false);
            return new StudentAttendanceListAdapter.MyViewHolder1(v);
        }
    }

    public void setOnCheckedChangeListener(Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final StudentAttendance mark = list.get(position);
        if (mark.getAttendance().contains("L")) {
            System.out.println("logg leave type view");
            ((MyViewHolder2) holder).studentName.setText(mark.getName());
            ((MyViewHolder2) holder).rollNo.setText(mark.getRoll());
            ((MyViewHolder2) holder).leaveValue.setText(mark.getAtteValue());
            if (mark.getAtteValue().equals(Values.ATTENDANCE_SICK_LEAVE)) {
                ((MyViewHolder2) holder).indicator.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_sick));

            } else if (mark.getAtteValue().equals(Values.ATTENDANCE_CASUAL_LEAVE)) {
                ((MyViewHolder2) holder).indicator.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_casual));

            } else if (mark.getAtteValue().equals(Values.ATTENDANCE_SHORT_LEAVE)) {
                ((MyViewHolder2) holder).indicator.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_short));

            } else if (mark.getAtteValue().equals(Values.ATTENDANCE_EMERGENCY_LEAVE)) {
                ((MyViewHolder2) holder).indicator.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_emergency));
            }
        } else {
            ((MyViewHolder1) holder).studentName.setText(mark.getName());
            ((MyViewHolder1) holder).rollNo.setText(mark.getRoll());
            System.out.println("logg roll no=" + mark.getRoll() + " attendance=" + mark.getAttendance());
            ((MyViewHolder1) holder).attendanceRadioGroup.clearCheck();
            if (mark.getAttendance().equals(Values.ATTENDANCE_PRESENT_CODE)) {
                System.out.println("logg roll no=" + mark.getRoll() + " attendance=" + mark.getAttendance() + " check true");
                ((MyViewHolder1) holder).presentRadioButton.setChecked(true);
            } else if (mark.getAttendance().equals(Values.ATTENDANCE_ABSENT_CODE)) {
                ((MyViewHolder1) holder).absentRadioButton.setChecked(true);
            } else {
                ((MyViewHolder1) holder).absentRadioButton.setChecked(false);
                ((MyViewHolder1) holder).presentRadioButton.setChecked(false);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getAttendance().contains("L")) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        TextView rollNo, studentName;
        RadioGroup attendanceRadioGroup;
        RadioButton presentRadioButton, absentRadioButton;

        MyViewHolder1(View itemView) {
            super(itemView);
            rollNo = itemView.findViewById(R.id.mark_attendance_roll_no1);
            studentName = itemView.findViewById(R.id.mark_attendance_student_name1);
            attendanceRadioGroup = itemView.findViewById(R.id.mark_attendance_radio_group_item);
            presentRadioButton = itemView.findViewById(R.id.mark_attendance_radio_button_present_item);
            absentRadioButton = itemView.findViewById(R.id.mark_attendance_radio_button_absent_item);
            attendanceRadioGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (radioGroup.isPressed()) {
                if (this.presentRadioButton.getId() == i) {
                    System.out.println("logg attendance " + Values.ATTENDANCE_PRESENT_CODE
                            + " roll no=" + list.get(getAdapterPosition()).getRoll());
                    mCallback.invoke(Values.ATTENDANCE_PRESENT_CODE, list.get(getAdapterPosition()).getAttendanceID());
                } else if (this.absentRadioButton.getId() == i) {
                    System.out.println("logg attendance " + Values.ATTENDANCE_ABSENT_CODE
                            + " roll no=" + list.get(getAdapterPosition()).getRoll());
                    mCallback.invoke(Values.ATTENDANCE_ABSENT_CODE, list.get(getAdapterPosition()).getAttendanceID());
                }
            }
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView rollNo, studentName, leaveValue;
        ImageView indicator;

        MyViewHolder2(View itemView) {
            super(itemView);
            rollNo = itemView.findViewById(R.id.mark_attendance_roll_no2);
            studentName = itemView.findViewById(R.id.mark_attendance_student_name2);
            indicator = itemView.findViewById(R.id.mark_attendance_leave_indicator);
            leaveValue = itemView.findViewById(R.id.mark_attendance_leave_value);
        }
    }
}
