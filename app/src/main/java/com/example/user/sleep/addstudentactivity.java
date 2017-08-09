package com.example.user.sleep;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addstudentactivity extends AppCompatActivity implements View.OnClickListener{

    EditText nameView;
    EditText emailView;
    EditText phoneView;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudentactivity);

        nameView = (EditText)findViewById(R.id.add_name);
        emailView = (EditText)findViewById(R.id.add_email);
        phoneView = (EditText)findViewById(R.id.add_phone);
        addBtn = (Button) findViewById(R.id.add_btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View V){
        String name = nameView.getText().toString();
        String email= emailView.getText().toString();
        String phone = phoneView.getText().toString();

        if(name == null || name.equals("")){
            Toast t = Toast.makeText(this, R.string.add_name_null, Toast.LENGTH_SHORT);
            t.show();
        }else{
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into tb_student (name, email, phone) values (?,?,?)",
                    new String[]{name,email,phone});
            db.close();

            finish();
        }

    }
}
