package com.example.logingithub;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				cargarInicio();
			}
		}, 2000);   
    }
    
    private void cargarInicio(){
        SharedPreferences preferences = getSharedPreferences("Datos", MODE_PRIVATE);
        
        if(preferences.getBoolean("Recuerdame", false)==true){
				Intent i = new Intent(SplashActivity.this,HomeActivity.class);
	        	startActivity(i);
	        	finish();
        } else {
        	Intent i = new Intent(SplashActivity.this,LoginActivity.class);
        	startActivity(i);
        	finish();
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
	    	deleteCache(SplashActivity.this);
	    } catch (Exception ex) {
	    }
	}
}
