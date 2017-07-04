package com.whitehinge.androidwarehouse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    Button DoSomethingButton=null;
    TextView HWText=null;
    EditText LocID=null;
    Activity ThisActivity=null;
    ListView ezlist=null;
    GenericMethods meth = new GenericMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ThisActivity=this;

        //Set designer vars
        DoSomethingButton = findViewById(R.id.DoSomethingButton);
        HWText =  findViewById(R.id.HWText);
        LocID =  findViewById(R.id.locIDBox);
        ezlist = findViewById(R.id.ezlist);

        //Adding listemers

        DoSomethingButton.setOnClickListener(RequestThingy);
        meth.AllowBadCoding();
    }




     View.OnClickListener RequestThingy = new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             try {
                 String Aids = LocID.getText().toString().replace("qlo","");
                 JSONObject tempdata = meth.getJSON("http://10.20.0.153/sd/Location/Search?searchtype=0&searchquery=" + Aids);
                 JSONArray datas = tempdata.getJSONArray("data");
                 List<View> SetList = Collections.emptyList();
                 ArrayAdapter<String> Adapter = new ArrayAdapter<String>(ezlist.getContext(), R.layout.bigtextview);
                 int i;
                 for(i=0; i<datas.length(); i++){
                     JSONObject jobj = datas.getJSONObject(i);
                     String SkuTitle = jobj.getString("Sku");
                     String SkuStock = "Stock at " + jobj.getString("ShelfName") + ": " + jobj.getString("StockLevel");
                     Adapter.add(SkuTitle);
                     Adapter.add(SkuStock);

                 }


                 ezlist.setAdapter(Adapter);

                 //HWText.setText(tempdata.toString(4));
                 LocID.setText("");
             } catch (Exception e) {
                 meth.GenericAlert("An unexpected error occured: " + e.toString(), ThisActivity, "Unexpected Error");
             }
     }
};
}

