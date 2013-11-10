package com.example.greenvoicedialer;

import java.util.ArrayList;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.speech.RecognizerIntent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;




@TargetApi(Build.VERSION_CODES.JELLY_BEAN) public class MainActivity extends FragmentActivity {

	public static String EXTRA_MESSAGE = "PIYUSH";
	public static final int RESULT_SPEECH = 1234;
	public static String message;
	

    @Override//Start of Activity Life Cycle
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    
        /** Help button onclick listener */    		
	final Button help=(Button)findViewById(R.id.help);
	
	help.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				Intent help_activity=new Intent(getApplicationContext(), HelpActivity.class);
				startActivity(help_activity);
			}
		});
    }//end of oncreate

    @Override//Application Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem help=menu.add("Help");
        OnMenuItemClickListener listn=new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				
				// TODO Auto-generated method stub
				Intent helpActivity= new Intent(getApplicationContext(), HelpActivity.class);
				startActivity(helpActivity);
				return false;
			}
		};
        help.setOnMenuItemClickListener(listn);
        
        return true;
    }
    
    
  //on click record button
    
    public void RecordClick(View v)  
    {
    	
    	//Changing the background image of first activity button which is first set to recorder1 in XML file
    	Resources res=getResources();
    	Drawable d=res.getDrawable(R.drawable.recorder2);
    	Button recButton=(Button)findViewById(R.id.fa_record);
    	recButton.setBackground(d);
    	callToSpeechRecognizer();

    }
    
    
//starting activity for recording voice
//default google activity    
    
    public void callToSpeechRecognizer()
	{
    	
		Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
		try
		{
			startActivityForResult(speech, RESULT_SPEECH);
			
		}
		catch(ActivityNotFoundException e)
		{
			Toast.makeText(getApplicationContext(), "please check the net connection and try again", Toast.LENGTH_SHORT).show();
			
		}
	}
    
    
    
    
    
	protected void onActivityResult(int requestcode,int resultcode,Intent data)
	{
		int flag=0;
		super.onActivityResult(requestcode, resultcode, data);
	
		Resources rs=getResources();
		Button recBtn=(Button)findViewById(R.id.fa_record);
    	Drawable e=rs.getDrawable(R.drawable.recorder1);
    	recBtn.setBackground(e);

    if(1234==requestcode)
    {
		switch(resultcode)
		{
		case  RESULT_OK:
			if(null != data)
			{
				ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				message = text.get(0);
				
				
				
				Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
				
				
				 while (phones.moveToNext())
			        {
					 String Name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					 String Number=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					 	
					 	if(Name.compareToIgnoreCase(message)== 0)
					 		{
					 			flag=1;
					 			String url = "tel:"+Number;
					 			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
					 			startActivity(intent);
					 		}
			        }
				 
				 
				 if(flag==0)
				 {
					 Toast.makeText(getApplicationContext(), "Exact match not found", Toast.LENGTH_SHORT).show();
					 DialogFragment df=new DialogTrial();
					 df.show(getSupportFragmentManager(), "MyDialog"); 

				 }
			}// end of inner if
			break;
			
		
		}//end of switch
		
		
      }//end of if
    
	}
    

 
    
    
    
}
