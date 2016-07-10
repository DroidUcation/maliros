package com.maliros.giftcard.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.maliros.giftcard.R;
import com.maliros.giftcard.dbhelpers.entries.CardEntry;
import com.maliros.giftcard.dbhelpers.entries.CardTypeEntry;
import com.maliros.giftcard.utils.DateUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;


public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TRANSITION = "EXTRA_TRANSITION";
    public static final String TRANSITION_FADE_FAST = "FADE_FAST";
    private int typeIndex = 1;
    private String typeAppend = "";
    private static final int SELECT_PICTURE = 0;
    private ImageView imageView;

    private EditText balance1;
    Button btnOpenPopup;

    //UI References
    private EditText expirationDateET;
    private DatePickerDialog expirationDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        //TODO: ask michal
//        imageView = (ImageView) findViewById(android.R.id.icon);
        btnOpenPopup = (Button) findViewById(R.id.btn_update);
        ButterKnife.bind(this);

        // init card type spinner
        setCardTypesSpinner();
        // init expiration date
        setExpirationDate();
        setBalanceEditTxt();
    }

    private void setBalanceEditTxt() {
        final EditText balanceEditTxt = (EditText) findViewById(R.id.edt_view_balance);
        balanceEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });
    }

    private void setExpirationDate() {
        expirationDateET = (EditText) findViewById(R.id.expiration_date);
        expirationDateET.setInputType(InputType.TYPE_NULL);
        expirationDateET.requestFocus();

        expirationDateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("has focus:", String.valueOf(hasFocus));
                if (hasFocus) {
                    expirationDatePickerDialog.show();
                } else if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });

        expirationDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expirationDatePickerDialog.show();
                if (!expirationDateET.hasFocus()) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        expirationDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                expirationDateET.setText(DateUtil.DATE_FORMAT_DD_MM_YYYY.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setCardTypesSpinner() {
        Spinner typesSpinner = (Spinner) findViewById(R.id.type_spinner);
        // query _id and name
        String[] projection = {CardTypeEntry._ID, CardTypeEntry.NAME};
        Cursor cardTypeCursor = getContentResolver().query(CardTypeEntry.CONTENT_URI, projection, null, null, null);
        // spinner fields
        String[] from = {CardTypeEntry.NAME};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter cardTypeAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cardTypeCursor, from, to, 0);
        cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(cardTypeAdapter);
        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                typeIndex = position + 1;
                typeAppend = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void updateBalanceCard(View view) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        Button btnSave = (Button) popupView.findViewById(R.id.btn_save);
        Button btnDismiss = (Button) popupView.findViewById(R.id.btn_cancel);


        btnDismiss.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btnSave.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Iwm here", "whats up");
                popupWindow.dismiss();

                Log.d("Iwm here", "whats up");
                // balance
                balance1 = (EditText) popupView.findViewById(R.id.et_count_used);
                // Defines an object to contain the updated values
                ContentValues mUpdateValues = new ContentValues();
                // Defines selection criteria for the rows you want to update
                String mSelectionClause = CardEntry._ID + " = ?";
                String[] mSelectionArgs = {getIntent().getExtras().getString(CardEntry._ID)};//card id
                Log.d("**", getIntent().getExtras().getString(CardEntry._ID));
                String[] selectionArgs = {getIntent().getExtras().getString(CardEntry._ID)};
                // Defines a variable to contain the number of updated rows
                int mRowsUpdated = 0;
                /*
                 * Sets the updated value and updates the selected words.
                 */
                mUpdateValues.putNull(CardEntry.BALANCE);
                mUpdateValues.put(CardEntry.BALANCE, Double.parseDouble(balance1.getText().toString()));
                mRowsUpdated = getContentResolver().update(
                        CardEntry.CONTENT_URI,               // the user dictionary content URI
                        mUpdateValues,                     // the columns to update
                        mSelectionClause,                   // the column to select on
                        mSelectionArgs                      // the value to compare to
                );

                Intent intent = new Intent(v.getContext(), AddCardActivity.class);
                startActivity(intent);
            }
        });
        popupWindow.showAsDropDown(btnOpenPopup, 250, 500);

    }

    public void pickPhoto(View view) {
        //TODO: launch the photo picker
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            try {
                bitmap = getBitmapFromUri(data.getData());//getPath(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private Bitmap getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        // Convert file path into bitmap image using below line.
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    public void uploadPhoto(View view) {
        try {
            executeMultipartPost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void executeMultipartPost() throws Exception {

        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

            Bitmap bitmap = drawable.getBitmap();

            bitmap.compress(CompressFormat.JPEG, 50, bos);

            byte[] data = bos.toByteArray();

            HttpClient httpClient = new DefaultHttpClient();

            HttpPost postRequest = new HttpPost(

                    "http://192.168.1.107:8888/files/upload_file.php");

            String fileName = String.format("File_%d.png", new Date().getTime());
            ByteArrayBody bab = new ByteArrayBody(data, fileName);

            // File file= new File("/mnt/sdcard/forest.png");

            // FileBody bin = new FileBody(file);

            MultipartEntity reqEntity = new MultipartEntity(

                    HttpMultipartMode.BROWSER_COMPATIBLE);

            reqEntity.addPart("file", bab);

            postRequest.setEntity(reqEntity);
            int timeoutConnection = 60000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            int timeoutSocket = 60000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpConnectionParams.setTcpNoDelay(httpParameters, true);

            HttpResponse response = httpClient.execute(postRequest);

            BufferedReader reader = new BufferedReader(new InputStreamReader(

                    response.getEntity().getContent(), "UTF-8"));

            String sResponse;

            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {

                s = s.append(sResponse);

            }

            System.out.println("Response: " + s);

        } catch (Exception e) {

            // handle exception here
            e.printStackTrace();
        }

    }

    /**
     * Validate required fields, add card, and redirect to display cards activity
     *
     * @param view
     */
    public void AddCard(View view) {
        if (validateRequiredFields()) {
            // card type
            Spinner typesSpinner = (Spinner) findViewById(R.id.type_spinner);
            Cursor c = (Cursor) typesSpinner.getSelectedItem();
            String cardTypeId = c.getString(c.getColumnIndex(CardTypeEntry._ID));
            // balance
            EditText balance = (EditText) findViewById(R.id.edt_view_balance);
            // expiration date
            String format = DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS.format(getDateFromDatePicker(expirationDatePickerDialog.getDatePicker()));

            // insert
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(CardEntry.BALANCE, Double.parseDouble(balance.getText().toString()));
            contentValues.put(CardEntry.EXPIRATION_DATE, format);
            contentValues.put(CardEntry.CARD_TYPE_ID, cardTypeId);
            getContentResolver().insert(CardEntry.CONTENT_URI, contentValues);

            // start cards display
            Intent intent = new Intent(this, DisplayCardsActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateRequiredFields() {
        boolean isValid = true;
        EditText balance = (EditText) findViewById(R.id.edt_view_balance);
        if (balance.getText() == null || balance.getText().toString().trim().equals("")) {
            balance.setError("Balance is required!");
            isValid = false;
        } else if ("0".equals(balance.getText())) {
            balance.setError("Balance has to be greater than 0!");
            isValid = false;
        }
        if (expirationDateET.getText() == null || expirationDateET.getText().toString().trim().equals("")) {
            expirationDateET.setError("Expiration date is required!");
            isValid = false;
        }
        return isValid;
    }

    /**
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public void onClick(View view) {
        if (view == expirationDateET) {
            expirationDatePickerDialog.show();
        }
    }

}