/*Name: Raquel Medbery
  Date: 11/12/2018
  Description: This app allows a user to create and store multiple characters created for RPG games.
  There is a navigation bar that the user can use to navigate between the character creator, the about
  page, and the preferences page.
 */

package com.example.charactercreator;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements PrefFragment.PrefFragmentListener, CreatorFragment.CreatorFragmentListener{
    private PrefFragment pref;
    private CreatorFragment creatorFrag;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle dToggle;
    private TextView welcomeTxt;
    private static final String CHOICES = "pref_numberOfChoices";
    private boolean preferencesChanged = true;
    Typeface myFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTxt = (TextView) findViewById(R.id.welcomeTxt);
        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Dalelands Uncial Condensed Bold.otf");
        welcomeTxt.setTypeface(myFont);

        drawer = findViewById(R.id.drawer);
        dToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(dToggle);
        NavigationView nvDrawer = findViewById(R.id.nvDrawer);
        dToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpDrawerContent(nvDrawer);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        //PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferencesChangeListener);

        creatorFrag = new CreatorFragment();
        pref = new PrefFragment();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //used to be public boolean onOptionsItemSelected
    public void selectItemDrawer(MenuItem item) {
        switch(item.getItemId()){
            case R.id.creator:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragFrame, (new CreatorFragment()), "fragment_creator").commit();
                welcomeTxt.setText("");
                break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragFrame, (new AboutFragment()), "fragment_about").commit();
                welcomeTxt.setText("");
                break;
            case R.id.pref:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragFrame, (new PrefFragment()), "fragment_pref").commit();
                welcomeTxt.setText("");
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragFrame, (new CreatorFragment()), "fragment_creator").commit();
        }
        item.setChecked(true);
        setTitle(item.getTitle());
        drawer.closeDrawers();
    }

    private void setUpDrawerContent(NavigationView navView){
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
    }

    @Override
    public void onInputPrefSent(int input) {
        /*Intent intent = new Intent(getApplicationContext(), CreatorFragment.class);
        intent.putExtra("input", input);
        startActivity(intent);*/
        //Bundle b = new Bundle();
        //b.putInt("input", input);
        CreatorFragment creatorFrag = new CreatorFragment();
        creatorFrag.updateClasses(input);
        //Message.message(getApplicationContext(), "This is the input" + input);
    }

    @Override
    public void onInputCreateSent(int input) {
        pref.updateClasses(input);
        Message.message(getApplicationContext(), "This is the input from create " + input);
    }
}
