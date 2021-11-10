package com.example.mydbapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.view.Menu;
import  android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
DatabaseHelper myDb;
EditText editName,editSurname,editMarks,edirTextId;
Button btnAddData,btnviewAll,btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName=findViewById(R.id.editText_name);
        editSurname=findViewById(R.id.editText_surname);
        editMarks=findViewById(R.id.editText_Marks);
        btnAddData=findViewById(R.id.button_add);
        btnviewAll=findViewById(R.id.btnviewAll);
        btnUpdate= findViewById(R.id.btn_update);
        edirTextId=findViewById(R.id.editTextId);
        btnDelete=findViewById(R.id.btn_delete);
        addData();
        viewAll();
        updateData();
        deleteData();
    }
    public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = myDb.deleteData(edirTextId.getText().toString());
                if (deleteRows > 0)
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate= myDb.updateData(edirTextId.getText().toString(),editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not updated",Toast.LENGTH_LONG).show();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
       /* if (id == R.id.action_settings)*/

        return false;
    }
    public void addData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isInserted=myDb.insertData(editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());
                if(isInserted = true)
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public  void viewAll(){
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0)
                {
                    showMessage("Error","Nothing found");
                    return;
                }
                else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Id :" + res.getString(0) + "\n");
                        buffer.append("Name :" + res.getString(1) + "\n");
                        buffer.append("SurName :" + res.getString(2) + "\n");
                        buffer.append("Marks :" + res.getString(3) + "\n\n");
                    }
                    showMessage("Data", buffer.toString());
                }
            }
        });
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
