package com.example.users.cifri;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Options extends Activity {

    RadioGroup radioGroup_Slognost;
    RadioGroup radioGroup_Zvuk;
    EditText NameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        radioGroup_Slognost=(RadioGroup) findViewById(R.id.radioGroup_Slognost);
        NameEditText=(EditText) findViewById(R.id.editText4);

        NameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                SharedPreferences.Editor editor=NachEkr.mSettings.edit();
                String s=v.getText().toString();
                editor.putString("name",s);
                editor.apply();
                NachEkr.Name=NachEkr.mSettings.getString("name","Player1");
                return false;
            }
        });

        if (NachEkr.Slognost.equals("eeasy"))
            radioGroup_Slognost.check(R.id.radioButton_eeasy);
        if (NachEkr.Slognost.equals("normal"))
            radioGroup_Slognost.check(R.id.radioButton_normal);
        if (NachEkr.Slognost.equals("hard"))
            radioGroup_Slognost.check(R.id.radioButton_hard);
        NameEditText.setText(NachEkr.Name);

        radioGroup_Slognost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor=NachEkr.mSettings.edit();
                switch (checkedId){
                    case R.id.radioButton_eeasy:
                        editor.putString("slognost","eeasy");
                        break;
                    case R.id.radioButton_normal:
                        editor.putString("slognost","normal");
                        break;
                    case R.id.radioButton_hard:
                        editor.putString("slognost","hard");
                        break;
                }
                editor.apply();
                NachEkr.Slognost=NachEkr.mSettings.getString("slognost","eeasy");
            }
        });

        radioGroup_Zvuk=(RadioGroup) findViewById(R.id.radioGroup_Zvuk);

        if (NachEkr.Zvuk)
            radioGroup_Zvuk.check(R.id.radioButton_on);
        else
            radioGroup_Zvuk.check(R.id.radioButton_off);

        radioGroup_Zvuk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor=NachEkr.mSettings.edit();
                switch (checkedId){
                    case R.id.radioButton_on:
                        editor.putBoolean("zvuk",true);
                        break;
                    case R.id.radioButton_off:
                        editor.putBoolean("zvuk",false);
                        break;
                }
                editor.apply();
                NachEkr.Zvuk=NachEkr.mSettings.getBoolean("zvuk",true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
