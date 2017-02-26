package my.acm.app;

import java.io.ByteArrayInputStream;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AccessoryDetails extends Activity {
	
	SQLiteDatabase MemoryDB;
	
	public String DB_NAME="MEMORYDB3";
    public String Table_Name="AccessoryData";
    
    public int ACCID=0;
    
    public int position=0;
    String[] Accessories_array_string=null;
    int[] Accessories_id_array=null;
    
    @Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accessorydata);

		Bundle extra=getIntent().getExtras();
		position=extra.getInt("position");
		Accessories_array_string=(String[]) extra.get("Accessories_array_string");
		Accessories_id_array=extra.getIntArray("Accessories_id_array");
		
		Button btn=(Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			try
			{
			MemoryDB=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
			String where="ACCESSORYID='" + Accessories_id_array[position] + "'";
			MemoryDB.delete(Table_Name, where, new String[0]);
			MemoryDB.close();
			finish();
			}
			catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			}
		});
		
		
		try
		{
		MemoryDB=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
		
		TextView itemAccessoryNameTV=(TextView) findViewById(R.id.itemAccessoryNameTV);
		TextView itemPlaceDetailsTV=(TextView) findViewById(R.id.itemPlaceDetailsTV);
		TextView itemTimeTV=(TextView) findViewById(R.id.itemTimeTV);
		TextView itemDateTV=(TextView) findViewById(R.id.itemDateTV);
		TextView itemExtraDetailsTV=(TextView) findViewById(R.id.itemExtraDetailsTV);
		ImageView itemPreviewIV=(ImageView) findViewById(R.id.itemPreviewIV);
		
		
		Cursor TempCursor=MemoryDB.rawQuery("Select * from AccessoryData WHERE NAME='"+ Accessories_array_string[position] + "'", null);
		TempCursor.moveToFirst();
		
		byte[] tempbmpbyte=null;
		Bitmap lastbmp;
		
		tempbmpbyte=TempCursor.getBlob(TempCursor.getColumnIndex("PHOTO"));
		itemAccessoryNameTV.setText(TempCursor.getString(TempCursor.getColumnIndex("NAME")));
		itemPlaceDetailsTV.setText("Place You kept the accessory: "+TempCursor.getString(TempCursor.getColumnIndex("PLACE")));
		itemDateTV.setText("Date of keeping: "+TempCursor.getString(TempCursor.getColumnIndex("DATE")));
		itemTimeTV.setText("Time of keeping: "+TempCursor.getString(TempCursor.getColumnIndex("TIME")));
		itemExtraDetailsTV.setText("Extra Details: " + TempCursor.getString(TempCursor.getColumnIndex("DETAILS")));
		
		
		TempCursor.close();
	    ByteArrayInputStream imageStream = new ByteArrayInputStream(tempbmpbyte);
		
		lastbmp=BitmapFactory.decodeByteArray(tempbmpbyte, 0, tempbmpbyte.length);
		
		itemPreviewIV.setImageBitmap(lastbmp);
		MemoryDB.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

}
