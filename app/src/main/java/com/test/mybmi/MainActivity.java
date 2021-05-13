package com.test.mybmi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
    private static final int ACTIVITY_REPORT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Main", "開始事件");
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
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ReportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_HEIGHT", num_height.getText().toString());
            bundle.putString("KEY_WEIGHT", num_weight.getText().toString());
            intent.putExtras(bundle);
            //呼叫另一個Activity時，改採用startActivityForResult方法
            //傳入一個Intent類別，並指定一個呼叫這個Activity的識別碼，Android框架會找出合適的Activity，並傳送Intent給這個Activity來負責處理
            startActivityForResult(intent, ACTIVITY_REPORT);

        }
    };

    //onActivityResult函式
    //在負責呼叫的Activity中，加入處理返回代碼的onActivityResult方法
    //requestCode：呼叫該Activity時使用的識別碼
    //resultCode：該Activity傳回的回傳瑪
    //intent：該Activity傳回的Intent類別資料
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        //onActivityResult函式與startActivityForResult函式是共生的關係
        //startActivityForResult函式負責呼叫其他Activity，而onActivityResult函式來處理被呼叫的Activity所傳回的資訊
        //當被呼叫的Activity完成工作時，就會通知負責呼叫的Activity，負責呼叫的Activity會使用onActivityResult函式來處理被呼叫的Activity所傳回的訊息
        super.onActivityResult(requestCode, resultCode, intent);

        //onActivityResult函式會根據收到的requestCode來判斷是哪個呼叫的Activity傳回的資料，這裡傳進來的requestCode就是我們啟動另一個Activity時使用的識別碼。

        //根據resultCode的結果來做後續處理
        //Android內建定義的有RESULT_OK、RESULT_CANCELLED兩種resultCode。
        //resultCode只是數字而已，可以自行定義，好讓接收結果的一端的程式，能根據更多的結果狀態來做出反應。
        if(resultCode == RESULT_OK)
        {
            if(requestCode == ACTIVITY_REPORT)
            {
                //從Intent中取出Bundle
                Bundle bundle = intent.getExtras();
                //從bundle中取出BMI值
                String bmi = bundle.getString("BMI");
                //在show_suggest介面元件中顯示BMI值
                show_suggest.setText(getString(R.string.advice_history) + bmi);
                //清空體重輸入欄
                num_weight.setText(R.string.input_empty);
                //把預設的遊標指向體重輸入欄，方便使用者做下一次輸入
                num_weight.requestFocus();
            }
        }
    }

    //-------------Menu-------------
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