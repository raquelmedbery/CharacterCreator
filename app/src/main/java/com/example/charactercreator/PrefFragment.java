/* Description: This fragment allows the user to switch between night mode
   and day mode theme. It also keeps track of the preference the user chooses
   for the classes they want to pick from.
 */

package com.example.charactercreator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrefFragment extends android.support.v4.app.Fragment{
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private PrefFragmentListener listener;
    private Switch nightSwitch;
    private Boolean classToggle = true;
    private int ans = 1;
    private TextView textTest;

    public PrefFragment() {
        // Required empty public constructor
    }

    public interface PrefFragmentListener{
        void onInputPrefSent(int input);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pref, container, false);
        textTest = view.findViewById(R.id.textTest);
        ToggleButton toggleBtn = view.findViewById(R.id.toggleBtn);
        nightSwitch = view.findViewById(R.id.nightSwitch);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            nightSwitch.setChecked(true);
        }

        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mPreferences.edit();

        mEditor.putInt("key", ans);
        mEditor.commit();

        //textTest.setText(classToggle.toString());
        classToggle = true;
        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                classToggle = isChecked;
                //textTest.setText(classToggle.toString());
                if(isChecked){
                    ans = 1;
                    String userAns = "Pathfinder";
                    mEditor.putString(getString(R.string.classes), userAns);
                    mEditor.commit();
                }else{
                    ans = 2;
                    String userAns = "Dungeons and Dragons";
                    mEditor.putString(getString(R.string.classes), userAns);
                    mEditor.commit();
                }
            }
        });
        /*if(classToggle == null){
            classToggle = false;
        }else{
            listener.onInputPrefSent(classToggle);
        }*/

        mEditor.putInt("key", ans);
        mEditor.commit();
        mPreferences.getInt("key", 1);
        //checkSharedPreferences();

        /*btnChoose.setOnClickListener(){
            @Override
            public void onClick(View view){
                if(whatever.equals("Dungeons and Dragons")){
                    String userAns = raceSpinner.getSelectedItem().toString();
                    mEditor.putString(getString(R.id.classes), userAns);
                    mEditor.commit();
                }else{

                }
            }
        }*/


        listener.onInputPrefSent(ans);

        /*Bundle bundle = new Bundle();
        bundle.putBoolean("chooseClass", classToggle);

        CreatorFragment creatorFragment = new CreatorFragment();
        creatorFragment.setArguments(bundle);*/

        /*Intent intentToggle = new Intent(getActivity(), CreatorFragment.class);
        intentToggle.putExtra("classToggle", classToggle);
        getActivity().startActivity(intentToggle);*/

        return view;
    }

    /*private void checkeSharedPreference(){
        String name = mPreferences.getString(getString(R.string.name), "Dungeons and Dragons");
    }*/

    public void updateClasses(int newClass){
        ans = newClass;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof PrefFragmentListener){
            listener = (PrefFragmentListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement PrefFragmentListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        listener = null;
    }

    public void restartApp(){
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

}
