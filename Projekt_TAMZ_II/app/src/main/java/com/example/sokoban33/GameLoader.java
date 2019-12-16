package com.example.sokoban33;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class GameLoader {
    private Activity activity;
    private final String FILE_NAME = "game.json";

    public GameLoader(Activity activity){
        this.activity = activity;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void writeToFile(String data, Context context) {
        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }
        catch (Exception e){}
    }

    public ArrayList<String> getAllMaps() {
        ArrayList<String> mapList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            for(int i = 0; i < jsonObject.getInt("act_num"); i++){
                mapList.add(jsonObject.getJSONObject("act_" + i).getString("name"));
            }
        }
        catch(JSONException e){}
        return mapList;
    }

    public int[] getMap(int mapId){
        int[] map = new int[100];
        String json = loadJSONFromAsset();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            String mapStr = jsonObject.getJSONObject("act_" + mapId).getString("map");
            int i = 0;
            for(String objNum: mapStr.split(",")){
                map[i] = getHero(Integer.parseInt(objNum));
                i++;
            }
        }
        catch (Exception e){
            return null;
        }
        return map;
    }

    public int getActiveMap(){
        String json = loadJSONFromAsset();
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(json);
            for(int i = 0; i < jsonObject.getInt("act_num"); i++){
                if(jsonObject.getJSONObject("act_" + i).getBoolean("active")){
                    return i;
                }
            }
        }
        catch (Exception e){
            return -2;
        }
        return -1;
    }

    public void setActiveMap(int mapId){
        String json = loadJSONFromAsset();
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(json);
            for(int i = 0; i < jsonObject.getInt("act_num"); i++){
                jsonObject.getJSONObject("act_" + i).put("active", false);
            }
            jsonObject.getJSONObject("act_" + mapId).put("active", true);
            writeToFile(/*jsonObject.toString()*/"weeeee", activity.getApplicationContext());
        }
        catch (Exception e){}
    }

    private int getHero(int objectId){
        if(objectId == 99){
            SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("HeroPref", 0);
            String heroClass = pref.getString("class", null);
            Toast.makeText(activity.getBaseContext(), heroClass, Toast.LENGTH_LONG).show();
            if(heroClass.equals("warrior")){
                return 6;
            }
            else if(heroClass.equals("mage")){
                return 4;
            }
        }
        return objectId;
    }
}
