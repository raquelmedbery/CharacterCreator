/* Description: This class allows for the user to enter their characters into a database.
   They can view the characters inside the database, update the character values, and
   delete characters. They are also able to find characters that match a certain criteria
   based on character race or class.
 */

package com.example.charactercreator;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatorFragment extends android.support.v4.app.Fragment {
    private CreatorFragmentListener listener;
    private int ans;
    ArrayAdapter<String> strAdapter, strAdapter2, strAdapter3;
    Spinner raceSpinner;
    Spinner classSpinner, findClass, findRace;
    String races[] = {"Elf", "Human", "Half Elf", "Dwarf", "Dragonborn", "Tiefling", "Half Orc", "Halfling", "Gnome"};
    String classes[] = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger",
            "Rogue", "Sorcerer", "Warlock", "Wizard"};
    String pathClasses[] = {"Alchemist", "Cavalier", "Gunslinger", "Inquisitor", "Magus", "Oracle", "Summoner", "Vigilante", "Witch"};
    String allClasses[] = {"Alchemist", "Barbarian", "Bard", "Cavalier", "Cleric", "Druid", "Fighter", "Gunslinger", "Inquisitor", "Magus",
            "Monk", "Oracle", "Paladin", "Ranger", "Rogue", "Sorcerer", "Summoner", "Vigilante", "Warlock", "Witch", "Wizard"};
    Button btnAdd, btnViewData, btnUpdate, btnDel, btnFindClass, btnFindRace;
    EditText entName, entId;
    TextView paraQueries;
    private CharHelper charHelper;
    private SQLiteDatabase db;


    public CreatorFragment() {
        // Required empty public constructor
    }

    public interface CreatorFragmentListener{
        void onInputCreateSent(int input);
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creator, container, false);

        charHelper = new CharHelper(getActivity());

        final Animation animTran = AnimationUtils.loadAnimation(getActivity(), R.anim.incorrect_shake);

        raceSpinner = view.findViewById(R.id.raceSpinner);
        classSpinner = view.findViewById(R.id.classSpinner);
        findClass = view.findViewById(R.id.findClass);
        findRace = view.findViewById(R.id.findRace);
        entName = view.findViewById(R.id.entName);
        entId = view.findViewById(R.id.entId);
        paraQueries = view.findViewById(R.id.paraQueries);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mPreferences.edit();

        String userClass = mPreferences.getString(getString(R.string.classes), "Dungeons and Dragons");
        //paraQueries.setText(userClass);

        if(userClass.equals("Dungeons and Dragons")){
            strAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, classes);
        }else{
            strAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pathClasses);
        }

        /*Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            int ans = bundle.getInt("input");
            if (ans == 1){
                strAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pathClasses);
            }else{
                strAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, classes);
            }
        }*/

        //Intent classIntent = getActivity().getIntent();
        //boolean ans = classIntent.getBooleanExtra("classToggle", false);

        /*Bundle bundle = getArguments();
        boolean ans = false;
        if (bundle != null) {
            ans = bundle.getBoolean("chooseClass");
        }*/
        //paraQueries.setText(ans);
        /*if (ans == 1){
            strAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pathClasses);
        }else{
            strAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, classes);
        }*/

        strAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, races);
        strAdapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, allClasses);
        raceSpinner.setAdapter(strAdapter);
        classSpinner.setAdapter(strAdapter2);
        findClass.setAdapter(strAdapter3);
        findRace.setAdapter(strAdapter);

        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?>parent, View view, int pos, long id){
                parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?>parent, View view, int pos, long id){
                parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        findClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?>parent, View view, int pos, long id){
                parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        findRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?>parent, View view, int pos, long id){
                parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        btnAdd = view.findViewById(R.id.addChar);
        btnViewData = view.findViewById(R.id.btnViewData);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDel = view.findViewById(R.id.btnDel);
        btnFindClass = view.findViewById(R.id.btnFindClass);
        btnFindRace = view.findViewById(R.id.btnFindRace);

        //adds a character to the database when button clicked
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String testStr = entName.getText().toString();
                if(!testStr.isEmpty()) {
                    String name = entName.getText().toString();
                    String raceAns = raceSpinner.getSelectedItem().toString();
                    String classAns = classSpinner.getSelectedItem().toString();

                    boolean insertData = charHelper.addData(name, raceAns, classAns);

                    if(insertData == true){
                        Message.message(getActivity(), "success - added to db");
                    }else{
                        Message.message(getActivity(), "unsuccessful - no add");
                    }
                    entName.setText("");
                }else{
                    v.startAnimation(animTran);
                    Message.message(getContext(), "Enter a Name");
                }
            }
        });

        //gets all the data within the database and displays it in an AlertDialog on click
        btnViewData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Cursor data = charHelper.showData();

                if(data.getCount() == 0){
                    Message.message(getActivity(), "No Data In Database");
                    v.startAnimation(animTran);
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while(data.moveToNext()){
                        buffer.append("ID: " + data.getInt(0) + "\n");
                        buffer.append("Name: " + data.getString(1) + "\n");
                        buffer.append("Race: " + data.getString(2) + "\n");
                        buffer.append("Class: " + data.getString(3) + "\n");

                        //display("All Stored Data", buffer.toString());
                    }
                    display("All Stored Data", buffer.toString());
                }
            }
        });

        //calls the update query to update a character based on id
        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String testStr = entId.getText().toString();
                if(!testStr.isEmpty() && !(entName.getText().toString().isEmpty())) {
                    String id = entId.getText().toString();
                    String name = entName.getText().toString();
                    String raceAns = raceSpinner.getSelectedItem().toString();
                    String classAns = classSpinner.getSelectedItem().toString();
                    boolean update = charHelper.updateData(id, name, raceAns, classAns);
                    if(update == true){
                        Message.message(getActivity(), "Successfully Updated");
                        entId.setText("");
                        entName.setText("");
                    }else{
                        Message.message(getActivity(), "Something went wrong");
                        v.startAnimation(animTran);
                    }
                }else{
                    if(entId.getText().toString().isEmpty()){
                        Message.message(getActivity(), "Enter the id of the character to update");
                        v.startAnimation(animTran);
                    }else{
                        Message.message(getActivity(), "Enter the name of the character to update");
                        v.startAnimation(animTran);
                    }
                }
            }
        });

        //call the query to delete a certain character by id
        btnDel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!(entId.getText().toString().isEmpty())){
                    String id = entId.getText().toString();
                    Integer deleteRow = charHelper.deleteData(id);
                    if(deleteRow > 0){
                        Message.message(getActivity(), "Successfully Deleted");
                        entId.setText("");
                    }else{
                        Message.message(getActivity(), "Id does not exist");
                        v.startAnimation(animTran);
                    }
                }else{
                    Message.message(getActivity(), "Enter the id of the character to delete");
                    v.startAnimation(animTran);
                }
            }
        });

        //calls the parameterized query to find characters by a specific class when button clicked
        btnFindClass.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String classAns = findClass.getSelectedItem().toString();
                Cursor data = charHelper.getClassData(classAns);

                if(data.getCount() == 0){
                    Message.message(getActivity(), "No Character With That Class");
                    v.startAnimation(animTran);
                }else{
                    StringBuffer buffer = new StringBuffer();
                    paraQueries.setText("");
                    while(data.moveToNext()){
                        buffer.append("ID: " + data.getInt(0) + " ");
                        buffer.append("Name: " + data.getString(1) + " ");
                        buffer.append("Race: " + data.getString(2) + " ");
                        buffer.append("Class: " + data.getString(3) + "\n");
                    }
                    display("Characters With Selected Class", buffer.toString());
                }
            }
        });

        //calls the parameterized query to find characters by a specific race when button clicked
        btnFindRace.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String raceAns = findRace.getSelectedItem().toString();
                Cursor data = charHelper.getRaceData(raceAns);

                if(data.getCount() == 0){
                    Message.message(getActivity(), "No Character With That Race");
                    v.startAnimation(animTran);
                }else{
                    StringBuffer buffer = new StringBuffer();
                    paraQueries.setText("");
                    while(data.moveToNext()){
                        buffer.append("ID: " + data.getInt(0) + " ");
                        buffer.append("Name: " + data.getString(1) + " ");
                        buffer.append("Race: " + data.getString(2) + " ");
                        buffer.append("Class: " + data.getString(3) + "\n");
                    }
                    display("Characters With Selected Race", buffer.toString());
                }
            }
        });

        return view;
    }

    public void updateClasses(int newClass){
        //Bundle bundle = newClass;
        ans = newClass;
        //paraQueries.setText(ans);
        Log.d("Testing for newclass", "This is newClass" + newClass);
        //System.out.println(ans);
        //Message.message(getActivity(), "This is newClass ");
        /*if (newClass == 1){
            strAdapter2 = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, pathClasses);
        }else{
            strAdapter2 = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, classes);
        }*/
    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof CreatorFragmentListener){
            listener = (CreatorFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement CreatorFragmentListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        listener = null;
    }

}
