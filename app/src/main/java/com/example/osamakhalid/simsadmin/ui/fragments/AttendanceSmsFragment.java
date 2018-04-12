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
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.osamakhalid.simsadmin.model.SectionDataProvider;
import com.example.osamakhalid.simsadmin.model.StudentAttendance;
import com.example.osamakhalid.simsadmin.model.StudentAttendanceList;
import com.example.osamakhalid.simsadmin.model.StudentDataList;
import com.example.osamakhalid.simsadmin.model.StudentDataProvider;

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
 * {@link AttendanceSmsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AttendanceSmsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Spinner classSpinner, sectionSpinner;
    Button selectDateButton, sendButton;
    TextView dateTextView;
    String spinnerValueClassId = "", spinnerValueSectionsId = "";
    List<ClassDataProvider> classDataProviderList;
    List<SectionDataProvider> sectionDataProviderList;
    int day, month, year;
    Calendar mCurrentDate;
    ProgressDialog progressDialog;
    LoginResponse userData;
    PendingIntent sentPI;
    int num = 0;
    StudentDbHelper studentDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    List<StudentAttendance> studentAttendanceList;

    public AttendanceSmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_attendance_sms, container, false);
        classSpinner = view.findViewById(R.id.attendance_classes_spinner);
        sectionSpinner = view.findViewById(R.id.attendance_sections_spinner);
        selectDateButton = view.findViewById(R.id.attendance_select_date_button);
        sendButton = view.findViewById(R.id.attendance_send_button);
        dateTextView = view.findViewById(R.id.attendance_date_text);
        userData = CommonCalls.getUserData(getActivity());
        mCurrentDate = Calendar.getInstance();
        classDataProviderList = new ArrayList<>();
        sectionDataProviderList = new ArrayList<>();
        studentDbHelper = new StudentDbHelper(getActivity().getApplicationContext());
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);
        getClassesList();
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
                        int selectedYear = i;
                        int selectedMonth = i1 + 1;
                        int selectedDay = i2;
                        if (selectedDay < 10) {
                            dateTextView.setText("0" + selectedDay + "-" + selectedMonth + "-" + selectedYear);
                        }
                        else if(selectedMonth<10){
                            dateTextView.setText(selectedDay + "-0" + selectedMonth + "-" + selectedYear);
                        }
                        else {
                            dateTextView.setText(selectedDay + "-" + selectedMonth + "-" + selectedYear);
                        }
                        getStudentsAttendance(spinnerValueClassId, spinnerValueSectionsId);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessagesToStudents(studentAttendanceList);
            }
        });
        return view;
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
        android.widget.ArrayAdapter<String> ArrayAdapter = new ArrayAdapter
                (getActivity(), R.layout.spinner_text_view, classesNames);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(ArrayAdapter);
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
        System.out.println("logg sections data provider list coming from database size=" + sectionDataProviderList.size());
        setValueToSectionSpinner();
    }

    public void setValueToSectionSpinner() {
        List<String> sectionsNames = new ArrayList<>();
        for (SectionDataProvider data : sectionDataProviderList) {
            sectionsNames.add(data.getSectionName());
        }
        android.widget.ArrayAdapter<String> ArrayAdapter = new ArrayAdapter
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
                                    sendButton.setEnabled(true);
                                    sendButton.setText("Send");
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

        Toast.makeText(getActivity(), "Sending messages to imported students.", Toast.LENGTH_SHORT).show();
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
            mListener.onAttendanceFragmentInteraction(uri);
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
        void onAttendanceFragmentInteraction(Uri uri);
    }
}
