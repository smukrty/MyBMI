package com.test.mybmi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得控制項物件
        initViews();
        //設定監聽事件
        setListensers();

    }

    private Button button_calc;
    private EditText num_height;
    private EditText num_weight;
    private TextView show_result;
    private TextView show_suggest;

    //取得控制項物件
    private void initViews()
    {
        button_calc = (Button)findViewById(R.id.button);
        num_height = (EditText)findViewById(R.id.height);
        num_weight = (EditText)findViewById(R.id.weight);
        show_result = (TextView)findViewById(R.id.result);
        show_suggest = (TextView)findViewById(R.id.suggest);
    }

    //設定監聽事件
    private void setListensers()
    {
        button_calc.setOnClickListener(calcBMI);
    }

    private OnClickListener calcBMI = new OnClickListener() {
        @Override
        public void onClick(View v) {
            try {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ReportActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("KEY_HEIGHT", num_height.getText().toString());
                bundle.putString("KEY_WEIGHT", num_weight.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }catch (Exception obj){
                Toast.makeText(MainActivity.this, "要先輸入身高體重喔!", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // 設置要用哪個menu檔做為選單
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        switch(id)
        {
            case R.id.action_settings:
                // 按下「設定」要做的事
                //Toast訊息
                Toast.makeText(this, "設定", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_help:
                // 按下「使用說明」要做的事
                Toast.makeText(this, "使用說明", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_about:
                // 按下「關於」要做的事
                openOptionsDialog();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void openOptionsDialog()
    {
        //顯示出一個簡單的對話框,用的方法是Android內建的AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("關於Android BMI")
                .setMessage("Android BMI 計算")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .show();
    }

}