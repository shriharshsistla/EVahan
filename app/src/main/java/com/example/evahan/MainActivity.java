package com.example.evahan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int FRONT_REQUEST_CODE = 22;
    private static final int CHASIS_REQUEST_CODE = 23;

    private static final int ENGINE_REQUEST_CODE = 24;

    private static final int BACK_REQUEST_CODE = 25;
    Button frontBtn;
    Button chasisbtn;

    Button engineBtn;

    Button backVbtn;
    ImageView imageView;
    ImageView engineImage;
    ImageView chasisImage;

    TextView vehicleText;
    Button submitButton;

    EditText vehicleNo;
    EditText engineNoText;

    EditText chasisNoText;

    Spinner serviceSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        frontBtn = findViewById(R.id.buttonCamera);
        frontBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButton1));

        chasisbtn = findViewById(R.id.chasisButton);
        chasisbtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButton2));

        backVbtn = findViewById(R.id.backVbtn);
        backVbtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorButton3));

        engineBtn = findViewById(R.id.engineButton);
        engineBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, com.google.android.material.R.color.background_material_dark));

        imageView = findViewById(R.id.frontImage);
        chasisImage = findViewById(R.id.chasisImage);
        engineImage = findViewById(R.id.engineImage);

        vehicleText = findViewById(R.id.vehicleNo);
        engineNoText = findViewById(R.id.editTextEngineNo);
        chasisNoText = findViewById(R.id.editTextChassisNo);

        submitButton = findViewById(R.id.submitButton);
        serviceSpinner = findViewById(R.id.serviceSpinner);

        //Adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.service_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processdata(vehicleText.getText().toString(), engineNoText.getText().toString(), chasisNoText.getText().toString());

                Toast.makeText(MainActivity.this, "Saved to Server ", Toast.LENGTH_SHORT).show();
            }
        });


        frontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent, FRONT_REQUEST_CODE);
            }
        });

        chasisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CHASIS_REQUEST_CODE);
            }
        });

        engineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CHASIS_REQUEST_CODE);
            }
        });

        backVbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CHASIS_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FRONT_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap originalPhoto = (Bitmap) data.getExtras().get("data");

            // Compress the image before setting it to the ImageView
            Bitmap compressedPhoto = compressBitmap(originalPhoto, 800); // Change the desired width here

            imageView.setImageBitmap(compressedPhoto);
        } else if (requestCode == CHASIS_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap chasisPhoto = (Bitmap) data.getExtras().get("data");
            chasisImage.setImageBitmap(chasisPhoto);
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void processdata(String vehicle_no, String engine_no, String chasis_no) {
        Call<model> call = ApiController.getInstance().getapi().getdata(vehicle_no, engine_no, chasis_no);

        call.enqueue(new Callback<model>() {
            @Override
            public void onResponse(Call<model> call, Response<model> response) {
                model obj = response.body();
                vehicleText.setText("");
                engineNoText.setText("");
                chasisNoText.setText("");
            }

            @Override
            public void onFailure(Call<model> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Compression
    private Bitmap compressBitmap(Bitmap originalBitmap, int maxWidth) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        if (width <= maxWidth) {
            return originalBitmap;
        }

        float aspectRatio = (float) width / height;
        int newHeight = (int) (maxWidth / aspectRatio);


        return Bitmap.createScaledBitmap(originalBitmap, maxWidth, newHeight, true);
    }
}
