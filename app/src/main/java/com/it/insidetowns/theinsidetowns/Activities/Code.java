package com.it.insidetowns.theinsidetowns.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;

import net.glxn.qrgen.android.QRCode;

import com.google.zxing.common.BitMatrix;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.objects.QrObject;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.lang.reflect.Type;

public class Code extends AppCompatActivity {
    LinearLayout BackArrow,Home;
    ImageView img;
    ImageView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        img = (ImageView)findViewById(R.id.img);
        BackArrow = findViewById(R.id.BackArrow);
        qrcode = findViewById(R.id.qrcode);

        Home = findViewById(R.id.Home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Code.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                finish();
            }
        });

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Code.this);
        String ProductDiscount = sharedPrefs.getString("ProductDiscount", "");
        String ID = sharedPrefs.getString("ID", "");
        String imgUrl = sharedPrefs.getString("Product_Image", "");
        String Product_Id = sharedPrefs.getString("Product_Id", "");
        String Shop_id = sharedPrefs.getString("Shop_id", "");
        String username = sharedPrefs.getString("username", "");
     //   String Description = sharedPrefs.getString("Description", "");
        String Description = sharedPrefs.getString("Product_Title", "");
        String stype = sharedPrefs.getString("type", "");

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .build();

        Picasso.with(Code.this)
                .load(""+imgUrl)
                .fit()
                .transform(transformation)
                .into(img);


       // int v1 = Integer.parseInt(CatId);
    //    QrObject u = new QrObject(v1,Integer.parseInt("55"),55,Integer.parseInt("55"));
        QrObject u = new QrObject(ProductDiscount,ID,username,Product_Id,Shop_id,Description,stype);


        Gson gson = new Gson();
        Type type = new TypeToken<QrObject>() {}.getType();
        String json = gson.toJson(u, type);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(json, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            Bitmap myBitmap= QRCode.from(json)
                    .withColor(0x0381C8, 0xFFFFFFFF).bitmap();
            qrcode.setImageBitmap(myBitmap);

        }
        catch (WriterException e){
            e.printStackTrace();
        }

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Code.super.onBackPressed();
            }
        });
    }
}
