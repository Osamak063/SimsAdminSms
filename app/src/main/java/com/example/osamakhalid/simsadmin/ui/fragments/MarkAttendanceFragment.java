package com.example.osamakhalid.simsadmin.ui.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamakhalid.simsadmin.R;
import com.example.osamakhalid.simsadmin.baseconnection.RetrofitInitialize;
import com.example.osamakhalid.simsadmin.connectioninterface.ClientAPIs;
import com.example.osamakhalid.simsadmin.consts.Values;
import com.example.osamakhalid.simsadmin.db.StudentDbHelper;
import com.example.osamakhalid.simsadmin.globalcalls.CommonCalls;
import com.example.osamakhalid.simsadmin.model.ClassDataProvider;
import com.example.osamakhalid.simsadmin.model.LoginResponse;
import com.example.osamakhalid.simsadmin.model.MarkAttendance;
import com.example.osamakhalid.simsadmin.model.MarkAttendanceResponse;
import com.example.osamakhalid.simsadmin.model.SectionDataProvider;
import com.example.osamakhalid.simsadmin.model.StudentAttendance;
import com.example.osamakhalid.simsadmin.model.StudentAttendanceList;
import com.example.osamakhalid.simsadmin.ui.adapters.StudentAttendanceListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MarkAttendanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MarkAttendanceFragment extends Fragment implements com.example.osamakhalid.simsadmin.interfaces.Callback {

    private OnFragmentInteractionListener mListener;
    TextView dateTextView;
    Button selectDateButton, submitButton;
    Spinner classSpinner, sectionSpinner;
    RecyclerView studentsListRecyclerView;
    RadioGroup selectAllRadioGroup;
    RadioButton presentRadioButton, absentRadioButton;
    StudentDbHelper studentDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    List<ClassDataProvider> classDataProviderList;
    List<SectionDataProvider> sectionDataProviderList;
    String spinnerValueClassId = "", spinnerValueSectionsId = "";
    int day, month, year;
    Calendar mCurrentDate;
    ProgressDialog progressDialog;
    LoginResponse userData;
    List<StudentAttendance> studentAttendanceList;
    StudentAttendanceListAdapter adapter;
    List<MarkAttendance> markAttendance;
    String attendanceParameter;
    LinearLayoutManager manager;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems, offset = 0;
    PendingIntent sentPI;
    int num = 0;

    public MarkAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mark_attendance, container, false);
        dateTextView = view.findViewById(R.id.mark_attendance_date_text);
        selectDateButton = view.findViewById(R.id.mark_attendance_select_date_button);
        submitButton = view.findViewById(R.id.mark_attendance_submit_button);
        classSpinner = view.findViewById(R.id.mark_attendance_classes_spinner);
        sectionSpinner = view.findViewById(R.id.mark_attendance_sections_spinner);
        studentsListRecyclerView = view.findViewById(R.id.mark_attendance_students_list_recycler_view);
        selectAllRadioGroup = view.findViewById(R.id.mark_attendance_select_all_radio_group);
        presentRadioButton = view.findViewById(R.id.mark_attendance_radio_button_present);
        absentRadioButton = view.findViewById(R.id.mark_attendance_radio_button_absent);
        userData = CommonCalls.getUserData(getActivity());
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        studentAttendanceList = new ArrayList<>();
        classDataProviderList = new ArrayList<>();
        sectionDataProviderList = new ArrayList<>();
        markAttendance = new ArrayList<>();
        studentsListRecyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        studentsListRecyclerView.setLayoutManager(manager);
        studentsListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                offset = totalItems - 1;
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    submitButton.setVisibility(View.VISIBLE);
                }
            }
        });

        selectAllRadioGroup.clearCheck();
        studentDbHelper = new StudentDbHelper(getActivity().getApplicationContext());
        getClassesList();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMarkAttendanceData();
                sendMessagesToStudents(studentAttendanceList);
            }
        });
        selectAllRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (presentRadioButton.getId() == i) {
                    System.out.println("logg select all present value");
                    setAttendanceToList(Values.ATTENDANCE_PRESENT_CODE);
                } else if (absentRadioButton.getId() == i) {
                    System.out.println("logg select all absent value");
                    setAttendanceToList(Values.ATTENDANCE_ABSENT_CODE);
                }
            }
        });
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValueClassId = classDataProviderList.get(i).getClassId();
                getSections(spinnerValueClassId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValueSectionsId = sectionDataProviderList.get(i).getSectionId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        selectAllRadioGroup.clearCheck();
                        int selectedYear = i;
                        int selectedMonth = i1 + 1;
                        int selectedDay = i2;
                        if (selectedDay < 10 && selectedMonth < 10) {
                            dateTextView.setText("0" + selectedDay + "-0" + selectedMonth + "-" + selectedYear);
                        } else if (selectedMonth < 10) {
                            dateTextView.setText(selectedDay + "-0" + selectedMonth + "-" + selectedYear);
                        } else if (selectedDay < 10) {
                            dateTextView.setText("0" + selectedDay + "-" + selectedMonth + "-" + selectedYear);
                        } else {
                            dateTextView.setText(selectedDay + "-" + selectedMonth + "-" + selectedYear);
                        }
                        getStudentsAttendance(spinnerValueClassId, spinnerValueSectionsId);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        return view;
    }

    public void getMarkAttendanceData() {
        for (StudentAttendance attendance : studentAttendanceList) {
            if (!attendance.getAttendance().contains("L") && !attendance.getAttendance().equals("null")) {
                markAttendance.add(new MarkAttendance(attendance.getAttendanceID(), attendance.getDay(), attendance.getAttendance()));
            }
        }
        attendanceParameter = new Gson().toJson(markAttendance);
        markAttendance();
    }

    public void markAttendance() {
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.WAIT_MSG);
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<MarkAttendanceResponse> call = clientAPIs.markAttendance(attendanceParameter, authHeader);
        call.enqueue(new Callback<MarkAttendanceResponse>() {
            @Override
            public void onResponse(Call<MarkAttendanceResponse> call, Response<MarkAttendanceResponse> response) {
                if (response.isSuccessful()) {
                    MarkAttendanceResponse responseOfApi = response.body();
                    progressDialog.dismiss();
                    if (responseOfApi.getSuccess().equals("1")) {
                        Toast.makeText(getActivity(), Values.SUCCESS_MARKING_ATTENDANCE, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), Values.ERROR_MARKING_ATTENDANCE, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MarkAttendanceResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAttendanceToList(String value) {
        int size = studentAttendanceList.size();
        for (int i = 0; i < size; i++) {
            studentAttendanceList.get(i).setAttendance(value);
        }
        adapter.notifyDataSetChanged();
    }

    public void getStudentsAttendance(String classId, String sectionId) {
        if (dateTextView.getText().toString().equals(Values.DATE_FORMAT)) {
            dateTextView.setError("Please select date.");
            return;
        }
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.WAIT_MSG);
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<StudentAttendanceList> call = clientAPIs.getAttendance(classId, dateTextView.getText().toString()
                , userData.getUsername(), sectionId, authHeader);
        call.enqueue(new Callback<StudentAttendanceList>() {
            @Override
            public void onResponse(Call<StudentAttendanceList> call, Response<StudentAttendanceList> response) {
                if (response.isSuccessful()) {
                    StudentAttendanceList attendanceList = response.body();
                    progressDialog.dismiss();
                    if (attendanceList != null && attendanceList.getStudentData() != null) {
                        studentAttendanceList = attendanceList.getStudentData();
                        adapter = new StudentAttendanceListAdapter(getActivity().getApplicationContext(), studentAttendanceList);
                        adapter.setOnCheckedChangeListener(MarkAttendanceFragment.this);
                        studentsListRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Attendance data fetched!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StudentAttendanceList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getClassesList() {
        sqLiteDatabase = studentDbHelper.getReadableDatabase();
        cursor = studentDbHelper.getInformationFromClassTable(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {
                classDataProviderList.add(new ClassDataProvider(String.valueOf(cursor.getInt(0))
                        , cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        setValuesToClassSpinner();
    }

    public void setValuesToClassSpinner() {
        List<String> classesNames = new ArrayList<>();
        for (ClassDataProvider data : classDataProviderList) {
            classesNames.add(data.getClassName());
        }
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<>
                (getActivity(), R.layout.spinner_text_view, classesNames);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(ArrayAdapter);
    }

    public void getSections(String classId) {
        sectionDataProviderList.clear();
        sqLiteDatabase = studentDbHelper.getReadableDatabase();
        cursor = studentDbHelper.getInformationFromSectionTableWithClassId(sqLiteDatabase, classId);
        if (cursor.moveToFirst()) {
            do {
                sectionDataProviderList.add(new SectionDataProvider(String.valueOf(cursor.getInt(0))
                        , cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        setValueToSectionSpinner();
    }

    public void setValueToSectionSpinner() {
        List<String> sectionsNames = new ArrayList<>();
        for (SectionDataProvider data : sectionDataProviderList) {
            sectionsNames.add(data.getSectionName());
        }
        android.widget.ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<>
                (getActivity().getApplicationContext(), R.layout.spinner_text_view, sectionsNames);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(ArrayAdapter);
    }

    public void sendMessagesToStudents(final List<StudentAttendance> dataProviderList) {

        Handler handler = new Handler();
        int delayMultiplier = 0;
        String SENT = "SMS_SENT";
        sentPI = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, new Intent(SENT), 0);
        getActivity().getApplicationContext().registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context arg0, Intent arg1) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                ++num;
                                Toast.makeText(getActivity(), "Sms sent " + num, Toast.LENGTH_SHORT).show();
                                if (num >= dataProviderList.size()) {
                                    Toast.makeText(getActivity(), "All sms sent", Toast.LENGTH_SHORT).show();
                                    num = 0;
                                }
                                break;
                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                Toast.makeText(getActivity(), "Generic failure", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                Toast.makeText(getActivity(), "No service.", Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NULL_PDU:
                                break;
                            case SmsManager.RESULT_ERROR_RADIO_OFF:

                                break;
                        }
                    }
                }, new IntentFilter(SENT));
        if (dataProviderList == null || dataProviderList.size() == 0) {
            Toast.makeText(getActivity(), "Students data not available", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getActivity(), "Sending messages of attendance to students.", Toast.LENGTH_SHORT).show();
        for (StudentAttendance data : dataProviderList) {
            final String phone = data.getPhone();
            final String messageContent = Values.getAttendanceMessage(data.getName(), data.getAtteValue());
            ++delayMultiplier;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        SmsManager sms = SmsManager.getDefault();
                        System.out.println("logg sending msg=" + messageContent + " number=" + phone);
                        sms.sendTextMessage(phone, null, messageContent, sentPI, null);
                    } catch (Exception e) {
                        System.out.println("logg exception=" + e.getLocalizedMessage());
                    }
                }
            }, delayMultiplier * 5000);

        }
        dataProviderList.clear();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMarkFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void invoke(String attendance, String attendanceId) {
        int size = studentAttendanceList.size();
        for (int i = 0; i < size; i++) {
            if (studentAttendanceList.get(i).getAttendanceID().equals(attendanceId)) {
                if (attendance.equals(Values.ATTENDANCE_PRESENT_CODE)) {
                    studentAttendanceList.get(i).setAttendance(attendance);
                    studentAttendanceList.get(i).setAtteValue(Values.ATTENDANCE_PRESENT_VALUE);
                } else if (attendance.equals(Values.ATTENDANCE_ABSENT_CODE)) {
                    studentAttendanceList.get(i).setAttendance(attendance);
                    studentAttendanceList.get(i).setAtteValue(Values.ATTENDANCE_ABSENT_VALUE);
                }
            }
        }
        //adapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMarkFragmentInteraction(Uri uri);
    }
}
