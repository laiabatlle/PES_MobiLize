package com.app.mobilize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sqliteopenhelper.AdminSQLiteOpenHelper;
import com.app.sqliteopenhelper.Rutina;

import java.io.Serializable;
import java.util.ArrayList;

public class Seleccionar_rutina extends AppCompatActivity  {

    ListView lv;
    LinearLayout la;
    ArrayList<Rutina> RutinaArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_rutina);



        int dificultat = getIntent().getIntExtra("dificultat", 0);
        String modalitat = getIntent().getStringExtra("modalitat");


        lv = (ListView) findViewById(R.id.Items);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDades = admin.getWritableDatabase();


        String[] args = new String[] {modalitat};


        //Cursor fila = BaseDeDades.rawQuery("select nom, info from Rutines  where nivell =" + dificultat + " and modalitat =" + modalitat ,  null);

        Cursor fila = BaseDeDades.rawQuery("select nom, info from Rutines where modalitat=? AND nivell =" + dificultat, args);

        RutinaArrayList = new ArrayList<>();
        while (fila.moveToNext()) {
            String nom = fila.getString(0);
            String info = fila.getString(1);
            Rutina r = new Rutina(nom, info, dificultat, modalitat);
            RutinaArrayList.add(r);
        }


        RutinaListAdapter adapter = new RutinaListAdapter(this, R.layout.item, RutinaArrayList);
        lv.setAdapter(adapter);





    }


    public void crear(View view) {

        int dificultat = getIntent().getIntExtra("dificultat",0);
        String modalitat = getIntent().getStringExtra("modalitat");

        Intent intent = new Intent(view.getContext(), AfegirRutina.class);
        intent.putExtra("modalitat", modalitat);
        intent.putExtra("dificultat", dificultat);
        startActivityForResult(intent, 0);
    }


}
