package com.yamankod.webservice_3_json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String URL = "http://10.2.10.10/webservice2/index.php?isim=";
	
	 TextView  tv;
	 ImageView imageView;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
          
      
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        Button giris_butonu=(Button)findViewById(R.id.giris_button);  
        final EditText editText=(EditText)findViewById(R.id.editText1);
        
        tv = (TextView) findViewById(R.id.textView1);
        
        imageView=(ImageView) findViewById(R.id.imageView1);
        
        giris_butonu.setOnClickListener(new View.OnClickListener() {  
              
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                String isim=editText.getText().toString();  
                fetchJsonTask a = new fetchJsonTask();  
                a.execute(URL+isim);  
            }  
        });  
	}

	public static String connect(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		try {
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						instream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return sb.toString();
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	
	class fetchJsonTask extends AsyncTask<String, Integer, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... params) {
			try {  
	            String ret = connect(params[0]);  
	            ret = ret.trim();  
	            JSONObject jsonObj = new JSONObject(ret);  
	            return jsonObj;  
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        return null;  
		}
		protected void onPostExecute(JSONObject result) {  
	        // TODO Auto-generated method stub  
	        super.onPostExecute(result);  
	        if (result != null) {  
	            parseJson(result);  
	        } else {  
	           
	        	imageView.setImageResource(R.drawable.flse);
	            tv.setText("Veri alınmadı");  
	            tv.setTextColor(Color.RED);  
	            Toast.makeText(getApplicationContext(), "Json null",  
	                    Toast.LENGTH_LONG).show();  
	        }  
	    }
		private void parseJson(JSONObject result) {	
			try {
				String isim=result.getString("isim");
				
				tv.setText(isim);
				
				if(isim.contains("Bulunmadı")){
					imageView.setImageResource(R.drawable.flse);
				}else {
					imageView.setImageResource(R.drawable.okewy);;
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}  
		
	}
	
	
	
	

}


