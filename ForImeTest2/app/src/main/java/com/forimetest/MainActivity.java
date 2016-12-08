package com.forimetest;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.forimetest.ContactsManager;

import junit.framework.Test;

public class MainActivity extends Activity
{
	private static final String TAG = "ContactsManager";

	EditText edit, number, mail,contactname,contactnumber;
	Button play, go, jump,delcon,addcon,insertdefault;
	public static MediaPlayer player;
	MediaPlayer mediaPlayer;
	private int mediaposition;

	boolean isPlaying = false;
	String[] pkgName = new String[] { "com.iflytek.inputmethod",
			"com.iflytek.inputmethod.oem", "com.iflytek.inputmethod.google" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		edit = (EditText) findViewById(R.id.text);
		number = (EditText) findViewById(R.id.number);
		mail = (EditText) findViewById(R.id.mail);
		contactname = (EditText) findViewById(R.id.contactname);
		contactnumber = (EditText) findViewById(R.id.contactnumber);
		player = new MediaPlayer();

		final ContactsManager cm = new ContactsManager(this.getContentResolver());


		try
		{
			player.setDataSource("/data/local/tmp/video.mp3");
			player.prepare();
		}
		catch (IllegalArgumentException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (SecurityException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalStateException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		player.setOnCompletionListener(new OnCompletionListener()
		{

			@Override
			public void onCompletion(MediaPlayer arg0)
			{
				isPlaying = false;
				play.setText("Play");

			}
		});
		go = (Button) findViewById(R.id.go);
		go.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				for (String name : pkgName)
				{
					Intent startApp = getPackageManager()
							.getLaunchIntentForPackage(name);
					if (startApp != null)
					{
						startActivity(startApp);
						break;
					}
				}
			}
		});
		play = (Button) findViewById(R.id.play);
		// play.setAlpha(0);
		// play.setBackgroundColor(Color.WHITE);
		play.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (!isPlaying)
				{
					player.start();
					isPlaying = true;
					play.setText("Playing");
				}
			}
		});
		jump = (Button) findViewById(R.id.jump);
		jump.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,SecondActivity.class);
				startActivity(intent);
				
				// TODO Auto-generated method stub
				
			}
		});
		delcon = (Button) findViewById(R.id.delcontact);
		delcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Contact contact = new Contact();
				String name = "";
				name = String.valueOf(contactname.getText());
				if (name != null) {
					contact.setName(name);
				}
				else{return;}
				String searchresult = cm.searchContact(name).getId();
				if ("0".equals(searchresult)) {
					contactname.setText("");
					contactnumber.setText("");
					Toast toast = Toast.makeText(getApplicationContext(),"联系人不存在",Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				else {
					cm.deleteContact(contact);
					searchresult = cm.searchContact(name).getId();
					if ("0".equals(searchresult)) {
						contactname.setText("");
						contactnumber.setText("");
						Toast toast = Toast.makeText(getApplicationContext(),"删除联系人成功",Toast.LENGTH_LONG);
						toast.show();
					}
					else{
						contactname.setText("");
						contactnumber.setText("");
						Toast toast = Toast.makeText(getApplicationContext(),"删除联系人失败",Toast.LENGTH_LONG);
						toast.show();
					}

				}

			}
		});
		addcon = (Button) findViewById(R.id.addcontact);
		addcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Contact contact = new Contact();
				String name = "";
				String number = "";
				name = String.valueOf(contactname.getText());
				number = String.valueOf(contactnumber.getText());
				Log.d(TAG, "input name: " + name);
				Log.d(TAG, "input number: " + number);
				if ("".equals(name)) {
					Toast toast = Toast.makeText(getApplicationContext(),"请填写联系人姓名",Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				else if ("".equals(number)) {
					Toast toast = Toast.makeText(getApplicationContext(),"请填写联系人号码",Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				else {

					contact.setName(name);
					contact.setNumber(number);
					contact.setEmail("test@tester.com");
					String searchresult = cm.searchContact(name).getId();
					if ("0".equals(searchresult)) {
						cm.addContact(contact);
						searchresult = cm.searchContact(name).getId();
						if ("0".equals(searchresult)) {
							contactname.setText("");
							contactnumber.setText("");
							Toast toast = Toast.makeText(getApplicationContext(),"联系人添加失败",Toast.LENGTH_LONG);
							toast.show();
							return;
						}
						else {
							Toast toast = Toast.makeText(getApplicationContext(),"联系人添加成功",Toast.LENGTH_LONG);
							toast.show();
							contactname.setText("");
							contactnumber.setText("");
							return;
						}

					}
					else {
						Toast toast = Toast.makeText(getApplicationContext(),"联系人已存在",Toast.LENGTH_LONG);
						toast.show();
						return;
					}

				}

			}
		});
		insertdefault = (Button) findViewById(R.id.insertdefault);
		insertdefault.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Contact contact = new Contact();
				contact.setName("小明");
				contact.setNumber("13912345678");
				contact.setEmail("test@tester.com");
				cm.addContact(contact);
				Toast toast = Toast.makeText(getApplicationContext(),"联系人<小明>添加成功",Toast.LENGTH_LONG);
				toast.show();

			}
		});



	}

	@Override
	protected void onPause()
	{
		super.onPause();
		edit.setText("");
		number.setText("");
		mail.setText("");
//		if (mediaPlayer.isPlaying()) {
//			mediaposition = mediaPlayer.getCurrentPosition();
//			mediaPlayer.stop();
//		}

	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		edit.setText("");
		number.setText("");
		mail.setText("");
		contactname.setText("");
		contactnumber.setText("");
//		if(mediaposition>0)
//		{
//			try
//			{
//				play();
//				mediaPlayer.seekTo(mediaposition);
//				mediaposition = 0;
//			}
//			catch (IOException e)
//			{
//				Log.e(TAG, e.toString());
//			}
//		}
	}

	@Override
	protected void onStop() {
//		if(mediaPlayer.isPlaying()) {
//			mediaPlayer.stop();
//		}
		super.onStop();

	}

	@Override
	protected void onDestroy() {
//		mediaPlayer.release();//释放资源
		super.onDestroy();

	}



}
