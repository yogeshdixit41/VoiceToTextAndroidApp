package com.example.greenvoicedialer;


import java.util.ArrayList;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class ViList extends ListActivity{
	
	/** Items entered by the user is stored in this ArrayList variable */
	ArrayList<String> list = new ArrayList<String>();
	
	/** Declaring an ArrayAdapter to set items to ListView */
	ArrayAdapter<String> adapter;
	
	 
	   String Temp_name;
	   String Name;
	   String Temp_pName;
	   Cursor phones;
	   ListView listview ;
	   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        //get the extramessage from intent passed
        Intent displayList =getIntent();
        final String[] NAME=displayList.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);
        
        setContentView(R.layout.activity_vi_list);
        Toast.makeText(getApplicationContext(), NAME[1], Toast.LENGTH_SHORT).show();
        display_list( NAME[1], (NAME[1].length())/2, NAME[0] );
        /** Setting a custom layout for the list activity */
        
        
        /** text changed listener start */
        
        EditText edit_text=(EditText)findViewById(R.id.txtItem);
        
        edit_text.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable changed_text) {
				// TODO Auto-generated method stub
				//String search_value=getText(R.id.txtItem).toString();
				//Toast.makeText(getApplicationContext(), changed_text,Toast.LENGTH_SHORT).show();
				display_list(changed_text.toString(),changed_text.length(), NAME[0]);
				
			}
		});
       		
	}
    
    
    
    /** displaying the list of the best match contacts */
    
    public void display_list(final String searchString,int searchStringLength, final String actionTOPerform)
    {
    	  
         listview =(ListView)findViewById(android.R.id.list);
         
         /** Defining the ArrayAdapter to set items to ListView */
         adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
         
         adapter.clear();
         
 				try {
 						
 					//Temp_name=value.substring(0, 2);
 					//Temp_name= new String(searchString.substring(0, searchStringLength));
 					//Toast.makeText(getApplicationContext(),searchStringLength,Toast.LENGTH_SHORT).show();
 	 				 phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
 	 				 phones.moveToFirst();
 	 					while(phones.moveToNext())
 	 					{
 	 						Name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
 	 						//String Temp_pName=Name.substring(0, 2);
 	 						//Temp_pName=new String(Name.substring(0,searchStringLength));
 	 						//Toast.makeText(getApplicationContext(), searchString, Toast.LENGTH_SHORT).show();
 	 						//Toast.makeText(getApplicationContext(), Name, Toast.LENGTH_LONG).show();
 	 						if(	(Name.toLowerCase()).contains(searchString.toLowerCase()) )//if(Temp_name.compareToIgnoreCase(Temp_pName)==0)
 	 						{
 	 							//Toast.makeText(getApplicationContext(), Name, Toast.LENGTH_SHORT).show();
 	 							list.add(Name);
 	 							adapter.notifyDataSetChanged();
 	 						
 	 						}
 	 						
 	 					}
					
 					} catch (StringIndexOutOfBoundsException e) {
 						//Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
					// TODO: handle exception
 					}
 				 				
 		 
         /** Setting the adapter to the ListView */
         setListAdapter(adapter);      
         
         /** Setting up the call on item click from the list */
    	OnItemClickListener selectedItem = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View selectedContact, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				String callcontact =  ((TextView)selectedContact).getText().toString();
				phones.moveToFirst();
				while (phones.moveToNext())
		      {
		        String Name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		        String Number=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		        if(Name.compareToIgnoreCase(callcontact)== 0)
		        {
		        	
		        	if(actionTOPerform.equalsIgnoreCase("call"))
		        	{
		        	//Intent call = new Intent(Intent.ACTION_CALL);
		        	String url = "tel:"+Number;
		            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
		        	//call.setData(Uri.parse(no));
		        	startActivity(intent);
		        	}
		        	else if(actionTOPerform.equalsIgnoreCase("message"))
		        	{
		        		String url = "sms:"+Number;
			            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			        	//call.setData(Uri.parse(no));
			        	startActivity(intent);
		        		
		        	}
		        }
			}
    		
			}
		};
		
		listview.setOnItemClickListener(selectedItem);
		
    }//end of display_list
   
    
	

    

}