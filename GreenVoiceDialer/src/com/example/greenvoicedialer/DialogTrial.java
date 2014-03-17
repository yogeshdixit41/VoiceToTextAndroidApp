package com.example.greenvoicedialer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class DialogTrial extends DialogFragment {
	
	
    
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
	
		//final String my="nothing";//just to check
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setMessage("Press more to proceed else try again").setTitle("My Dialog")
		.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				// TODO Auto-generated method stub
				
			}
		}).setNeutralButton("More", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Intent displayList = new Intent(getActivity(), ViList.class);
				//passing message value as extra message to intent
				displayList.putExtra(MainActivity.EXTRA_MESSAGE,MainActivity.splitMessage[1]);
				startActivity(displayList);
				// TODO Auto-generated method stub
				
			}
		});
	     
		return builder.create();
		
	}

}
