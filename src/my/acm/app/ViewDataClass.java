package my.acm.app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class ViewDataClass extends Activity{

	SQLiteDatabase MemoryDB;
	
	public String DB_NAME="MEMORYDB3";
    public String Table_Name="AccessoryData";
    String[] Accessories_array_string = null;
    public int position=0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewdatascreen);
		
		ArrayList<String> Accessories_array_list=new ArrayList<String>();
		ArrayList<Integer> Accessories_id_list=new ArrayList<Integer>();
		
		MemoryDB=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
		Cursor c=MemoryDB.rawQuery("SELECT * from AccessoryData", null);
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
		Accessories_array_list.add(c.getString(c.getColumnIndex("NAME")));
		Accessories_id_list.add(c.getInt(c.getColumnIndex("ACCESSORYID")));
		}
		MemoryDB.close();
		
		Accessories_array_string=(String[]) Accessories_array_list.toArray(new String[0]);
		
		int size_of_id_array=Accessories_id_list.size();
		 final int[] Accessories_id_array=new int[size_of_id_array];
		
		for(int i=0;i<size_of_id_array;i++)
		{
			Accessories_id_array[i]=Accessories_id_list.get(i);
		}
		
		
		ListAdapter la=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,Accessories_array_string);
		
		ListView lv=(ListView) findViewById(R.id.listView1);
		lv.setAdapter(la);
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// TODO Auto-generated method stub
				try
				{
				Intent i=new Intent("my.acm.app.AccessoryDetails");
				i.putExtra("position", arg2);
				i.putExtra("Accessories_array_string", Accessories_array_string);
				i.putExtra("Accessories_id_array", Accessories_id_array);
				startActivity(i);
				}
				catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}	
		});
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onCreate(null);
		
	}
	
	
}