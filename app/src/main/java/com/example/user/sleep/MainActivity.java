package com.example.user.sleep;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    //Button testBtn;
    FloatingActionButton addBtn;
    ListView listView;
    ArrayList<StudentVO> datas;

    double initTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testBtn = (Button)findViewById(R.id.main_test_btn);
        addBtn = (FloatingActionButton)findViewById(R.id.fab);
        listView = (ListView)findViewById(R.id.main_list);

//        testBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);



    }


    @Override
    public void onClick(View v){

        if(v == addBtn){
            Intent intent = new Intent(this, addstudentactivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Readstudentactivity.class);
        intent.putExtra("id",datas.get(position).id);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_student order by name", null);

        datas = new ArrayList<>();
        while(cursor.moveToNext()){
            StudentVO vo = new StudentVO();
            vo.id=cursor.getInt(0);
            vo.name=cursor.getString(1);
            vo.email=cursor.getString(2);
            vo.phone=cursor.getString(3);
            vo.photo=cursor.getString(4);
            datas.add(vo);
        }
        db.close();

        MainListAdapter adapter = new MainListAdapter(this,R.layout.main_list_item,datas);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                Toast t = Toast.makeText(this,R.string.main_back_end,Toast.LENGTH_SHORT);
                t.show();
            }
            else{
                finish();
            }
            initTime=System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
