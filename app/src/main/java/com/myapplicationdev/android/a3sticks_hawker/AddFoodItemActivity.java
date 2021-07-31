package com.myapplicationdev.android.a3sticks_hawker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class AddFoodItemActivity extends AppCompatActivity {

    Button btnAddItem;
    AsyncHttpClient client;
    EditText etName, etPrice, etWaitTime;
    ArrayList<FoodItem> alItems;
    ListView lvItems;
    ArrayList<String> alIncluded;
    TextView tvAddIncluded;
    SetItemAdapter itemAdapter;
    Toolbar toolbar;
    ImageView foodImg;
    Button btnUpload, btnRemoveImage;
    boolean imageSet;

    int foodID;
    // permission count for camera permission
    int cameraPermissionCount = 0;

    // For uploading of image
    Bitmap bitmap;
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        TextView tb = findViewById(R.id.toolbar_title1);
        tb.setText("Add Food Item");

        toolbar = findViewById(R.id.top_toolbar);
        client = new AsyncHttpClient();
        lvItems = findViewById(R.id.lvItems);
        alIncluded = new ArrayList<String>();
        etName = findViewById(R.id.editTextTextPersonName3);
        etPrice = findViewById(R.id.etPrice2);
        etWaitTime = findViewById(R.id.etWaitTime);
        foodImg = findViewById(R.id.foodImage);
        btnUpload = findViewById(R.id.btnUpload);
        btnRemoveImage = findViewById(R.id.btnRemoveImg);

        btnAddItem = findViewById(R.id.btnAdd);
        tvAddIncluded = findViewById(R.id.tvAddIncluded);
        tvAddIncluded.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        itemAdapter = new SetItemAdapter(getApplicationContext(), R.layout.set_includes_row, alIncluded);
        lvItems.setAdapter(itemAdapter);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
        foodImg.setImageBitmap(bitmap);
        filePath = Uri.parse("android.resource://com.myapplicationdev.android.a3sticks_hawker/"
                + R.drawable.no_image);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
                foodImg.setImageBitmap(bitmap);
                filePath = Uri.parse("android.resource://com.myapplicationdev.android.a3sticks_hawker/"
                        + R.drawable.no_image);
                btnRemoveImage.setVisibility(View.GONE);
            }
        });

        // Adding items included in set
        tvAddIncluded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodItemActivity.this);
                View layout = getLayoutInflater().inflate(R.layout.dialog_set_included, null);

                EditText etName = layout.findViewById(R.id.name);
                EditText etQty = layout.findViewById(R.id.quantity);

                builder.setView(layout);
                builder.setCancelable(false);
                builder.setNeutralButton("Cancel", null);
                builder.setPositiveButton("Confirm", null);

                AlertDialog dialog = builder.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button btnPositive = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);

                        btnPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Boolean cName = isBlank(etName);
                                Boolean cQty = isBlank(etQty);

                                if (!cName && !cQty) {
                                    String name = etName.getText().toString();
                                    int qty = Integer.parseInt(etQty.getText().toString());

                                    if (qty > 0) {
                                        String items = qty + " x " + name;

                                        alIncluded.add(items);
                                        itemAdapter.notifyDataSetChanged();

                                        dialogInterface.dismiss();
                                    } else {
                                        etQty.setError("Please enter more than 0");
                                    }
                                }
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();

                String name = etName.getText().toString();
                String price = etPrice.getText().toString();
                String waitTime = etWaitTime.getText().toString();

                if (name.isEmpty()) {
                    etName.setError("Please enter name");
                }
                if (price.isEmpty()) {
                    etPrice.setError("Please enter price");
                }
                if (waitTime.isEmpty()){
                    etWaitTime.setError("Please enter estimated waiting time");
                }

                if (!name.isEmpty() && !price.isEmpty() && !waitTime.isEmpty()) {
                    addItem();
                }

//                int lastId = alItems.size() - 1;
//                Bundle bundle = new Bundle();
//                FoodItem food = new FoodItem(lastId + 1, etName.getText().toString(), Double.parseDouble(etPrice.getText().toString()));
//                bundle.putSerializable("foodItem", food);
//
//                client.post("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/addFoodItem.php", new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        //called when response HTTP status is "200 OK"
//                        try {
//                            String result = response.getString("inserted");
//                            if (result.contains("Success")) {
//                                String msg = "Added to order";
//                                Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();
//                                getSupportFragmentManager()
//                                        .beginTransaction()
//                                        .replace(R.id.container, new MenuFragment())
//                                        .commit();
//                            } else {
//                                String msg = "Failed to add to order";
//                                Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

            }
        });
    }

    // Alicia's edited addItem function
    public void addItem() {
        AsyncHttpClient client = new AsyncHttpClient();
        double price = Double.parseDouble(etPrice.getText().toString());
        double waitTime = Double.parseDouble(etWaitTime.getText().toString());

        RequestParams params = new RequestParams();
        params.put("name", etName.getText().toString());
        params.put("price", String.format("%.2f", price));
        params.put("waitTime", String.format("%.2f", waitTime));
        params.put("included", alIncluded);

        String url = "https://3stickscustomer.000webhostapp.com/Customer/addFoodItem.php";

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean added = response.getBoolean("added");
                    String msg = response.getString("msg");
                    Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();

                    if (added) {
                        foodID = response.getInt("foodID");
                        if (imageSet) {
                            uploadImageToDatabase();
                        }

                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("AddItem error", responseString);
            }
        });
    }

    public void selectImage() {
        CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodItemActivity.this);
        builder.setTitle("Upload Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Take Photo")) {
                    requestCameraPermission();
                } else if (options[i].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public boolean isBlank(EditText editText) {
        // If user enters space, use .trim() to remove empty spaces.
        String text = editText.getText().toString().trim();

        Boolean check = false;

        if (text.isEmpty()) {
            editText.setError("please fill in " + editText.getHint());
            check = true;
        }
        return check;
    }

    private void requestCameraPermission() {
        boolean cameraDenied = ContextCompat.checkSelfPermission(AddFoodItemActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED;
        boolean readStorageDenied = ContextCompat.checkSelfPermission(AddFoodItemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
        boolean writeStorageDenied = ContextCompat.checkSelfPermission(AddFoodItemActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;

        if (cameraDenied && readStorageDenied && writeStorageDenied) {
            String[] permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(AddFoodItemActivity.this, permission, 0);
            cameraPermissionCount += 1;
        }

        boolean cameraGranted = ContextCompat.checkSelfPermission(AddFoodItemActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        boolean readStorageGranted = ContextCompat.checkSelfPermission(AddFoodItemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
        boolean writeStorageGranted = ContextCompat.checkSelfPermission(AddFoodItemActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;

        if (cameraGranted && readStorageGranted && writeStorageGranted) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Checking the request code of our request
        if (requestCode == 0) {
            //If permission is granted for camera
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(AddFoodItemActivity.this, "Permission granted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            } else {
                //Displaying another toast if permission is not granted
                if (cameraPermissionCount > 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodItemActivity.this);
                    builder.setTitle("Camera Permission was denied.");
                    builder.setMessage("To use Camera, Please grant permissions in the System Settings to use Camera.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(AddFoodItemActivity.this, "Permission denied", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                bitmap = getResizedBitmap(bitmap, 400);
                foodImg.setImageBitmap(bitmap);
                btnRemoveImage.setVisibility(View.VISIBLE);
                filePath = getImageUri(AddFoodItemActivity.this, bitmap);
                imageSet = true;
            } else if (requestCode == 2) {
                // Choose image from gallery
                if (data.getData() != null) {
                    filePath = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        bitmap = getResizedBitmap(bitmap, 400);
                        foodImg.setImageBitmap(bitmap);
                        btnRemoveImage.setVisibility(View.VISIBLE);
                        imageSet = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(AddFoodItemActivity.this, uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public Uri getImageUri(Context context, Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                imageBitmap, timeStamp, null);
        return Uri.parse(path);
    }

    public void uploadImageToDatabase() {
        // getting the actual path of the image
        String path = getPath(filePath);
        String uploadURL = "https://3stickscustomer.000webhostapp.com/Customer/uploadFoodImage.php";

        // Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            // Creating a multi part request
            new MultipartUploadRequest(AddFoodItemActivity.this, uploadId, uploadURL)
                    .addFileToUpload(path, "image")
                    .addParameter("foodID", String.valueOf(foodID))
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AddFoodItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}