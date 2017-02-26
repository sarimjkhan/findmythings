package my.acm.app;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MemoryActivity extends Activity {
    /** Called when the activity is first created. */
	
	//declaring database
    SQLiteDatabase MemoryDB;
    //declaring database
    
    public Bitmap PreviewPhotoBMP=null;  	 //Bitmap to store photo
    
    public String PlaceDetailsTXT=null;  //String to store Place details text
    public String DateTXT=null;			 //String to store Date text
    public String TimeTXT=null;			 //String to store Time text
    public String ExtraDetailsTXT=null;	 //String to store Extra Details Text
    public String AccessoryNameTXT=null;	 //String to store Accessory Name Text
    
    public String DB_NAME="MEMORYDB3";
    public String Table_Name="AccessoryData";
    
    EditText PlaceDetailsET;
    EditText AccessoryNameET;
    EditText DateET;
    EditText TimeET;
    EditText ExtraDetailsET;
    ImageView PreviewIV;
    
    Boolean check_pic_taken=false;
    Boolean check_date_entered=false;
    Boolean check_time_entered=false;
    Boolean check_details_entered=false;
    Boolean check_accessoryname_entered=false;
    Boolean check_place_entered=false;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
              
        MemoryDB=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        MemoryDB.execSQL("CREATE TABLE IF NOT EXISTS AccessoryData(ACCESSORYID INTEGER PRIMARY KEY,PHOTO BLOB,NAME TEXT,PLACE TEXT,DATE TEXT,TIME TEXT,DETAILS TEXT);");
        MemoryDB.close();
        
        //Finding All Views Here
        	PlaceDetailsET=(EditText) findViewById(R.id.PlaceDetailsET);
        	DateET=(EditText) findViewById(R.id.DateET);
        	TimeET=(EditText) findViewById(R.id.TimeET);
        	AccessoryNameET=(EditText) findViewById(R.id.AccesoryNameET);
        	ExtraDetailsET=(EditText) findViewById(R.id.ExtraDetailsET);
        	
        	PreviewIV=(ImageView) findViewById(R.id.PreviewIV);
				
			//Finding All Views Here
        
        
        //Take Pic Image Button coding
        ImageButton TakePicIB=(ImageButton) findViewById(R.id.TakePicIB);
        TakePicIB.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, 0);
				
			}
		});
        //Take Pic Image Button coding
        
        // Save Data Button Coding
        Button SaveDataBTN=(Button) findViewById(R.id.SaveDataBTN);
        SaveDataBTN.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				AccessoryNameTXT=AccessoryNameET.getText().toString();
				DateTXT=DateET.getText().toString();
				TimeTXT=TimeET.getText().toString();
				ExtraDetailsTXT=ExtraDetailsET.getText().toString();
				PlaceDetailsTXT=PlaceDetailsET.getText().toString();
				
				
				// check whether any of the fields is empty or not 
				if(AccessoryNameTXT.length()!=0)
					{check_accessoryname_entered=true;}
				if(DateTXT.length()!=0);
					check_date_entered=true;
				if(TimeTXT.length()!=0)
					check_time_entered=true;
				if(ExtraDetailsTXT.length()!=0)
					check_details_entered=true;
				if(PlaceDetailsTXT.length()!=0)
					check_place_entered=true;
				// check whether any of the fields is empty or not
				
			
				if(check_pic_taken && check_accessoryname_entered && check_date_entered && check_details_entered && check_time_entered && check_place_entered)
				{
				MemoryDB=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
				ContentValues cv=new ContentValues();
				cv.put("NAME", AccessoryNameTXT );
				cv.put("PLACE", PlaceDetailsTXT);
				cv.put("DATE", DateTXT);
				cv.put("TIME", TimeTXT);
				cv.put("DETAILS",ExtraDetailsTXT);
				
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				PreviewPhotoBMP.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				final byte[] bmpbyte=baos.toByteArray();
				
				cv.put("PHOTO", bmpbyte);
				MemoryDB.insert(Table_Name, null, cv);
				MemoryDB.close();
				Toast.makeText(getApplicationContext(), "Data Saved To Memory", Toast.LENGTH_SHORT).show();
			    check_date_entered=false;
			    check_time_entered=false;
			    check_details_entered=false;
			    check_accessoryname_entered=false;
			    check_place_entered=false;
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Add the entire data in the form, may be any field is empty or picture is not taken!", Toast.LENGTH_LONG).show();
					
				}
				
				
				
			}
		});
        
        // Save Data Button Coding
    
        
     // View Data Button Coding
        Button ViewDataBTN=(Button) findViewById(R.id.ViewBTN);
        ViewDataBTN.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				MemoryDB=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
				
				try
				{
				Intent newIntent=new Intent("my.acm.app.ViewDataClass");
				startActivity(newIntent);
				}
				catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
							MemoryDB.close();
				
			}
		});
        
     // View Data Button Coding
        
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK)
		{
			if(resultCode==RESULT_OK )
			{
			check_pic_taken=true;
			Bundle extra=data.getExtras();
			PreviewPhotoBMP=(Bitmap) extra.get("data");
			ImageView PreviewIV=(ImageView) findViewById(R.id.PreviewIV);
			PreviewIV.setImageBitmap(PreviewPhotoBMP);
			
		}
	}
 }

	
	
}