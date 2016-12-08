package com.forimetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondActivity extends Activity {
	Button goback;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		goback = (Button) findViewById(R.id.button_hello);
		goback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				onBackPressed();
				Intent intent_goback = new Intent(SecondActivity.this,MainActivity.class);
				startActivity(intent_goback);
				
				
			}
		});
	}

}
