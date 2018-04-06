package com.example.osamakhalid.simsadmin.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osamakhalid.simsadmin.R;
import com.example.osamakhalid.simsadmin.baseconnection.RetrofitInitialize;
import com.example.osamakhalid.simsadmin.connectioninterface.ClientAPIs;
import com.example.osamakhalid.simsadmin.consts.Values;
import com.example.osamakhalid.simsadmin.db.StudentDbHelper;
import com.example.osamakhalid.simsadmin.globalcalls.CommonCalls;
import com.example.osamakhalid.simsadmin.model.Class;
import com.example.osamakhalid.simsadmin.model.ClassesList;
import com.example.osamakhalid.simsadmin.model.LoginResponse;
import com.example.osamakhalid.simsadmin.model.Section;
import com.example.osamakhalid.simsadmin.model.SectionsList;
import com.example.osamakhalid.simsadmin.model.StudentData;
import com.example.osamakhalid.simsadmin.model.StudentDataList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImportDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ImportDataFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Button importButton;
    Spinner classesSpinner, sectionsSpinner;
    String spinnerValueClassId = "", spinnerValueSectionsId = "";
    List<Class> classListItems;
    List<StudentData> studentDataList;
    List<Section> sectionsData;
    ProgressDialog progressDialog;
    LoginResponse userData;
    StudentDbHelper studentDbHelper;
    SQLiteDatabase sqLiteDatabase;

    public ImportDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_import_data, container, false);
        classesSpinner = view.findViewById(R.id.import_classes_spinner);
        sectionsSpinner = view.findViewById(R.id.import_sections_spinner);
        importButton = view.findViewById(R.id.import_button);
        userData = CommonCalls.getUserData(getActivity());
        getClassesList();
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfoInDatabase();
                Toast.makeText(getActivity(), "Data imported successfully.", Toast.LENGTH_SHORT).show();
            }
        });
        classesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValueClassId = classListItems.get(i).getClassesID();
                getSections(spinnerValueClassId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sectionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerValueSectionsId = sectionsData.get(i).getSectionID();
                getStudents(spinnerValueClassId, spinnerValueSectionsId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void saveInfoInDatabase() {
        studentDbHelper = new StudentDbHelper(getActivity().getApplicationContext());
        sqLiteDatabase = studentDbHelper.getWritableDatabase();
        for (StudentData data : studentDataList) {
            studentDbHelper.addInformation(data.getStudentID(), data.getName(), data.getPhone(), data.getClassesID()
                    , data.getSectionID(), sqLiteDatabase);
        }
    }

    public void getSections(String classId) {
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.WAIT_MSG);
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<SectionsList> call = clientAPIs.getSections(classId, authHeader);
        call.enqueue(new Callback<SectionsList>() {
            @Override
            public void onResponse(Call<SectionsList> call, Response<SectionsList> response) {
                if (response.isSuccessful()) {
                    SectionsList sectionsList = response.body();
                    progressDialog.dismiss();
                    if (sectionsList != null && sectionsList.getSectionData() != null) {
                        sectionsData = sectionsList.getSectionData();
                        getSectionsName(sectionsData);
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SectionsList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getSectionsName(List<Section> sectionsLists) {
        List<String> sectionsNames = new ArrayList<>();
        for (Section data : sectionsLists) {
            sectionsNames.add(data.getSection());
        }
        android.widget.ArrayAdapter<String> ArrayAdapter = new ArrayAdapter
                (getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, sectionsNames);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionsSpinner.setAdapter(ArrayAdapter);
    }

    public void getStudents(final String classId, final String sectionsId) {
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.WAIT_MSG);
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<StudentDataList> call = clientAPIs.getStudents(classId, sectionsId, 5, 0, authHeader);
        call.enqueue(new Callback<StudentDataList>() {
            @Override
            public void onResponse(Call<StudentDataList> call, Response<StudentDataList> response) {
                if (response.isSuccessful()) {
                    StudentDataList studentsList = response.body();
                    progressDialog.dismiss();
                    if (studentsList != null && studentsList.getStudentData() != null) {
                        studentDataList = studentsList.getStudentData();
                        Toast.makeText(getActivity(), "Students data fetched!", Toast.LENGTH_SHORT).show();
                        importButton.setEnabled(true);
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StudentDataList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getClassesNames(List<Class> classes) {
        List<String> classesNames = new ArrayList<>();
        for (Class data : classes) {
            classesNames.add(data.getClasses());
        }
        android.widget.ArrayAdapter<String> ArrayAdapter = new ArrayAdapter
                (getActivity(), android.R.layout.simple_spinner_item, classesNames);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classesSpinner.setAdapter(ArrayAdapter);
    }

    public void getClassesList() {
        progressDialog = CommonCalls.createDialouge(getActivity(), "", Values.WAIT_MSG);
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<ClassesList> call = clientAPIs.getClasses(authHeader);
        call.enqueue(new Callback<ClassesList>() {
            @Override
            public void onResponse(Call<ClassesList> call, Response<ClassesList> response) {
                if (response.isSuccessful()) {
                    ClassesList classesList = response.body();
                    progressDialog.dismiss();
                    if (classesList != null && classesList.getClassesData() != null) {
                        classListItems = classesList.getClassesData();
                        getClassesNames(classListItems);
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassesList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onImportFragmentInteraction(uri);
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
        void onImportFragmentInteraction(Uri uri);
    }
}
