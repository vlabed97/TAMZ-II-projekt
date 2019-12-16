package com.example.sokoban33;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HeroActivity extends AppCompatActivity {

    public EditText editTextName;
    public TextView textViewClass;
    public String chosenClass;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
        pref = getApplicationContext().getSharedPreferences("HeroPref", 0);
        editTextName = findViewById(R.id.editTextHeroName);
        textViewClass = findViewById(R.id.textViewClass);
        if(pref.getString("name", null) != ""){
            editTextName.setText(pref.getString("name", null));
        }
        if(pref.getString("class", null) != ""){
            chosenClass = pref.getString("class", null);
            textViewClass.setText(chosenClass);
        }
        else{
            chosenClass = "warrior";
        }

    }

    public void clickWarrior(View view){
        chosenClass = "warrior";
    }

    public void  clickMage(View view){
        chosenClass = "mage";
    }

    public void clickSubmit(View view){
        Editor editor = pref.edit();
        editor.putString("name", editTextName.getText().toString());
        editor.putString("class", chosenClass);
        editor.commit();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
