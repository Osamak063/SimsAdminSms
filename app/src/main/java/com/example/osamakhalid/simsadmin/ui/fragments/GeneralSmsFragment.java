package com.example.osamakhalid.simsadmin.ui.fragments;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.simsadmin.baseconnection.RetrofitInitialize;
import com.example.osamakhalid.simsadmin.connectioninterface.ClientAPIs;
import com.example.osamakhalid.simsadmin.consts.Values;
import com.example.osamakhalid.simsadmin.db.StudentDbHelper;
import com.example.osamakhalid.simsadmin.globalcalls.CommonCalls;
import com.example.osamakhalid.simsadmin.R;
import com.example.osamakhalid.simsadmin.model.Class;
import com.example.osamakhalid.simsadmin.model.ClassDataProvider;
import com.example.osamakhalid.simsadmin.model.ClassesList;
import com.example.osamakhalid.simsadmin.model.LoginResponse;
import com.example.osamakhalid.simsadmin.model.Section;
import com.example.osamakhalid.simsadmin.model.SectionDataProvider;
import com.example.osamakhalid.simsadmin.model.SectionsList;
import com.example.osamakhalid.simsadmin.model.StudentData;
import com.example.osamakhalid.simsadmin.model.StudentDataList;
import com.example.osamakhalid.simsadmin.model.StudentDataProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GeneralSmsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GeneralSmsFragment extends Fragment {
    List<String> classesNames;
    EditText messageContent;
    Button sendButton;
    android.widget.ArrayAdapter<String> ArrayAdapter;
    List<Class> classListItems;
    List<StudentData> studentDataList;
    String spinnerValueClassId = "", spinnerValueSectionsId = "";
    ProgressDialog progressDialog;
    LoginResponse userData;
    private OnFragmentInteractionListener mListener;
    List<Section> sectionsData;
    int num = 0;
    Cursor cursor;
    List<StudentDataProvider> studentDataProviderList;
    StudentDbHelper studentDbHelper;
    SQLiteDatabase sqLiteDatabase;
    List<ClassDataProvider> classDataProviderList;
    List<SectionDataProvider> sectionDataProviderList;
    Spinner classesSpinner, sectionsSpinner;
    StudentDataProvider studentDataForMsg;
    PendingIntent sentPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_general_sms, container, false);
        messageContent = view.findViewById(R.id.message_content);
        classesSpinner = view.findViewById(R.id.import_classes_spinner);
        sectionsSpinner = view.findViewById(R.id.import_sections_spinner);
        sendButton = view.findViewById(R.id.send_button);
        studentDbHelper = new StudentDbHelper(getActivity().getApplicationContext());
        classesNames = new ArrayList<>();
        studentDataProviderList = new ArrayList<>();
        classDataProviderList = new ArrayList<>();
        sectionDataProviderList = new ArrayList<>();
        userData = CommonCalls.getUserData(getActivity());
        sendButton.setText("Send");
        getClassesList();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessagesToStudents(studentDataProviderList);
                sendButton.setEnabled(false);
                sendButton.setText("Sending...");
                if (studentDataProviderList.size() == 0) {
                    sendButton.setEnabled(true);
                    sendButton.setText("Send");
                }

            }
        });

        classesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValueClassId = classDataProviderList.get(i).getClassId();
                getSections(spinnerValueClassId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sectionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValueSectionsId = sectionDataProviderList.get(i).getSectionId();
                getStudents(spinnerValueClassId, spinnerValueSectionsId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void getStudents(String classId, String sectionId) {
        sqLiteDatabase = studentDbHelper.getReadableDatabase();
        studentDataProviderList.clear();
        cursor = studentDbHelper.getInformationFromStudentTable(sqLiteDatabase, classId, sectionId);
        if (cursor.moveToFirst()) {
            do {
                studentDataProviderList.add(new StudentDataProvider(String.valueOf(cursor.getInt(0))
                        , cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        , cursor.getString(4), cursor.getString(5), cursor.getString(6)
                        , cursor.getString(7), cursor.getString(8), cursor.getString(9)
                        , cursor.getString(10), cursor.getString(11)));
            } while (cursor.moveToNext());
        }
        if (studentDataProviderList.size() == 0) {
            Toast.makeText(getActivity(), "Students not available", Toast.LENGTH_SHORT).show();
        }
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
        classesSpinner.setAdapter(ArrayAdapter);
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
        sectionsSpinner.setAdapter(ArrayAdapter);
    }


    public void sendMessagesToStudents(final List<StudentDataProvider> dataProviderList) {
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
        } else if (TextUtils.isEmpty(messageContent.getText().toString())) {
            messageContent.setError("Please type message.");
            return;
        }
        Toast.makeText(getActivity(), "Sending messages to imported students.", Toast.LENGTH_SHORT).show();
        for (StudentDataProvider data : dataProviderList) {
            final String phone=data.getMob();
            ++delayMultiplier;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        SmsManager sms = SmsManager.getDefault();
                        System.out.println("logg sending msg=" + messageContent.getText().toString() + " number=" + phone);
                        sms.sendTextMessage(phone, null, messageContent.getText().toString(), sentPI, null);
                    } catch (Exception e) {
                        System.out.println("logg exception=" + e.getLocalizedMessage());
                    }
                }
            }, delayMultiplier*5000);

        }
        dataProviderList.clear();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onGeneralFragmentInteraction(uri);
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
        void onGeneralFragmentInteraction(Uri uri);
    }
}
