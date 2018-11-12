package com.example.logingithub;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button login;
	private EditText user,pass;
	private CheckBox recuerdame;
	private TextView msgError;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		user = (EditText)findViewById(R.id.etUser);
		pass = (EditText)findViewById(R.id.etPassword);
		recuerdame = (CheckBox)findViewById(R.id.cbRecuerdame);
		msgError = (TextView)findViewById(R.id.tvMsg);
		login = (Button)findViewById(R.id.btnLogin);
		
		habilitarBoton();
		
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				validarUser();
			}
		});
	}

	
	private void habilitarBoton(){
		user.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				pass.addTextChangedListener( new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						login.setEnabled(true);
					}
				});
			}
		});
		
		pass.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				user.addTextChangedListener( new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						login.setEnabled(true);
					}
				});
			}
		});

	}
	
	private void validarUser(){
		
		if(user.getText().toString().contains("@")){
			if (user.getText().toString().matches("[a-z0-9]+@[a-z0-9]+.[a-z]{2,4}"))
	        {
				validarCuenta();
	        }
	        else
	        {
	        	msgError.setText("Correo Invalido");
	        }
		} else {
			validarCuenta();
		}
	}
	
	private void validarCuenta(){
		
    	String ruta="https://api.github.com/user";
    	RequestQueue qq = Volley.newRequestQueue(LoginActivity.this);
    	StringRequest stringRequest;
    	stringRequest = new StringRequest(Request.Method.GET,ruta, new Response.Listener<String>() {
    		@Override
    		public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response.toString());
                    	SharedPreferences settings = getSharedPreferences("Datos", MODE_PRIVATE);
                        SharedPreferences.Editor editor;
                        editor = settings.edit();
                        editor.putBoolean("Recuerdame",recuerdame.isChecked());
                        editor.putString("cuenta", response);
                        editor.commit();
                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(i);
                        finish();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"1err",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				msgError.setText("Error de credenciales");
				//Toast.makeText(getApplicationContext(),arg0+"2errr",Toast.LENGTH_SHORT).show();	
			}
        })
        {
            /*-----PARAMETROS-----*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> headers = new HashMap<String, String>();
                	
                String loginEncoded = new String(Base64.encode((user.getText().toString().trim()+ ":" + pass.getText().toString().trim()).getBytes(), Base64.NO_WRAP));
                headers.put("Authorization", "Basic " + loginEncoded);
                return headers;
            }
        };
        qq.add(stringRequest);
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
	    	deleteCache(LoginActivity.this);
	    } catch (Exception ex) {
	    }
	}
}
