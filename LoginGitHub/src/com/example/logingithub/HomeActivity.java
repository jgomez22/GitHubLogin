package com.example.logingithub;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private TextView saludo;
	private Button cerrarsesion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		saludo = (TextView)findViewById(R.id.etSaludo);
		cerrarsesion = (Button)findViewById(R.id.btnBorrarSesion);
		cerrarsesion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cerrarSesion();
			}
		});
		
		saludo();
	}
	
	private void cerrarSesion(){
		deleteCache(HomeActivity.this);
		SharedPreferences sharedPreferences = getSharedPreferences("Datos", MODE_PRIVATE);	
		SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
		Intent i = new Intent(HomeActivity.this,SplashActivity.class);
		startActivity(i);
		finish();
	}
	
	private void saludo(){
			
		try {
			SharedPreferences sharedPreferences = getSharedPreferences("Datos", MODE_PRIVATE);
			JSONObject jsonObject= new JSONObject(sharedPreferences.getString("cuenta",null).toString());
			saludo.setText("Hola "+jsonObject.getString("name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void deleteCache(Context context) {
	    try {
	        File dir = context.getCacheDir();
	        deleteDir(dir);
	    } catch (Exception e) { e.printStackTrace();}
	}

	public static boolean deleteDir(File dir) {
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	        return dir.delete();
	    } else if(dir!= null && dir.isFile()) {
	        return dir.delete();
	    } else {
	        return false;
	    }
	}

	public void onDestroy(){
	    super.onDestroy();
	    try {
	    	deleteCache(HomeActivity.this);
	    } catch (Exception ex) {
	    }
	}
}
