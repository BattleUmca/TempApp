package com.example.umca.tempapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;



public class MainActivity extends Activity {

    ShowBarCode SHBC=new ShowBarCode();
    Bitmap bitmap=null;
    SharedPreferences sPref;
    String CardCode="BonusNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);






        // barcode image

        final ImageView iv=(ImageView) findViewById(R.id.imageView2);
        final EditText tv =(EditText) findViewById(R.id.editText);
        String barcode_data=loadPref(); // загружаем номер карты из файла


        tv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        (i == KeyEvent.KEYCODE_ENTER))
                {
                    // сохраняем текст, введенный до нажатия Enter в переменную
                    String strCatName = tv.getText().toString();
                    try {

                        bitmap = SHBC.encodeAsBitmap(strCatName,BarcodeFormat.CODE_128,600,300);//код ,формат,размеры штрих кода
                        iv.setImageBitmap(bitmap);// отображаим штрихкод



                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    savePerf(strCatName);



                    return true;
                }
                return false;
            }
        });




        try {

            bitmap = SHBC.encodeAsBitmap(barcode_data,BarcodeFormat.CODE_128,600,300); //код ,формат,размеры штрих кода
            iv.setImageBitmap(bitmap); // отображаим штрихкод

        } catch (WriterException e) {
            e.printStackTrace();
        }



        //barcode text

        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setText(barcode_data);



    }


    String loadPref()
    {
        sPref=getSharedPreferences("Test2",MODE_PRIVATE); // модификатор
        String barcode_data = sPref.getString(CardCode,""); // получить поле, по значению
        return barcode_data;
    }


    void savePerf(String strCatName)
    {
        sPref=getSharedPreferences("Test2",MODE_PRIVATE); // модификатор-Константа MODE_PRIVATE используется для настройки доступа и означает, что после сохранения, данные будут видны только этому приложению.
        SharedPreferences.Editor editor=sPref.edit(); //чтобы редактировать данные, необходим объект Editor – получаем его из sPref
        editor.putString(CardCode,strCatName); // Изменить пол и значение
        editor.commit(); // записать
    }






}

