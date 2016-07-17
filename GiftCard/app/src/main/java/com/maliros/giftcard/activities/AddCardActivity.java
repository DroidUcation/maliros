package com.maliros.giftcard.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

    private int typeIndex = 1;
    private String typeAppend = "";
    private static final int SELECT_PICTURE = 0;
    private ImageView imageView;
    private boolean isCreateMode = true;
    private Button btnOpenPopup;
    private int cardId;
    private Cursor cardCursor;

    //UI References
    private EditText cvv;
    private EditText cardNumber;
    private EditText balance;
    private EditText expirationDateET;
    private DatePickerDialog expirationDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        btnOpenPopup = (Button) findViewById(R.id.btn_update_balance);
        ButterKnife.bind(this);

        // create/update mode
        handleCreateUpdateMode();
        // init card type spinner
        setCardTypesSpinner();
        // init expiration date
        setExpirationDate();
        // balance
        setBalanceEditTxt();
        // cardNumber & cvv
        handleCardNumberAndCvv();
    }

    private void handleCreateUpdateMode() {
        this.cardId = (getIntent() != null && getIntent().getExtras() != null) ? getIntent().getExtras().getInt(CardEntry._ID, -1) : 0;
        if (this.cardId != -1) {
            Log.d("**id", String.valueOf(this.cardId));
            this.isCreateMode = false;
            setTitle(getString(R.string.update_card_title));
            Button addButton = (Button) findViewById(R.id.add_card_btn);
            // handle buttons
            addButton.setText(R.string.update_button_text);
            cardCursor = getContentResolver().query(ContentUris.withAppendedId(CardEntry.CONTENT_URI, this.cardId), null, null, null, null);
            cardCursor.moveToFirst();
        } else {
            setTitle(getString(R.string.add_card_title));
        }
        Button updateBalanceBtn = (Button) findViewById(R.id.btn_update_balance);
        updateBalanceBtn.setVisibility(this.isCreateMode ? View.GONE : View.VISIBLE);
    }

    private void setBalanceEditTxt() {
        balance = (EditText) findViewById(R.id.edt_view_balance);
        balance.setOnFocusChangeListener(getFocusChangeListener());

        balance.setEnabled(this.isCreateMode);
        // init values in update mode
        if (isUpdateWithCardRecord()) {
            balance.setText(cardCursor.getString(cardCursor.getColumnIndex(CardEntry.BALANCE)));
        }
    }

    private View.OnFocusChangeListener getFocusChangeListener() {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        };
    }

    private void setExpirationDate() {
        expirationDateET = (EditText) findViewById(R.id.expiration_date);
        expirationDateET.setInputType(InputType.TYPE_NULL);
        expirationDateET.requestFocus();

        expirationDateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    expirationDatePickerDialog.show();
                }/* else if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
                }*/
            }
        });

        expirationDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expirationDatePickerDialog.show();
             /*   if (!expirationDateET.hasFocus()) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
                }*/
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        // init values in update mode
        if (isUpdateWithCardRecord()) {
            newCalendar = DateUtil.getDate(cardCursor.getString(cardCursor.getColumnIndex(CardEntry.EXPIRATION_DATE)));
            expirationDateET.setText(DateUtil.DATE_FORMAT_DD_MM_YYYY.format(newCalendar.getTime()));
        }

        expirationDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                expirationDateET.setText(DateUtil.DATE_FORMAT_DD_MM_YYYY.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private boolean isUpdateWithCardRecord() {
        return (!isCreateMode && cardCursor != null && cardCursor.moveToFirst());
    }

    private void setCardTypesSpinner() {
        final Spinner typesSpinner = (Spinner) findViewById(R.id.type_spinner);
        typesSpinner.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
                return false;
            }
        });

        String selection = null;
        String[] selectionArgs = null;
        // init values in update mode- retrieve only this card type
        if (isUpdateWithCardRecord()) {
            selection = CardTypeEntry._ID + " = ?";
            selectionArgs = new String[]{String.valueOf(cardCursor.getInt(cardCursor.getColumnIndex(CardEntry.CARD_TYPE_ID)))};
        }

        // query _id and name
        String[] projection = {CardTypeEntry._ID, CardTypeEntry.NAME};
        Cursor cardTypeCursor = getContentResolver().query(CardTypeEntry.CONTENT_URI, projection, selection, selectionArgs, null);
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
        typesSpinner.setEnabled(this.isCreateMode);
        typesSpinner.setClickable(this.isCreateMode);
    }

    private void handleCardNumberAndCvv() {
        this.cardNumber = (EditText) findViewById(R.id.edt_card_number);
        this.cvv = (EditText) findViewById(R.id.edt_cvv);
        cardNumber.setOnFocusChangeListener(getFocusChangeListener());
        cardNumber.setEnabled(this.isCreateMode);
        cvv.setOnFocusChangeListener(getFocusChangeListener());
        cvv.setEnabled(this.isCreateMode);
        if (isUpdateWithCardRecord()) {
            cardNumber.setText(cardCursor.getString(cardCursor.getColumnIndex(CardEntry.CARD_NUMBER)));
            cvv.setText(cardCursor.getString(cardCursor.getColumnIndex(CardEntry.CVV)));
        }
    }

    public void showInputDialog(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(AddCardActivity.this);
        View promptView = layoutInflater.inflate(R.layout.popup, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddCardActivity.this)
                .setTitle("Update Balance");
        alertDialogBuilder.setView(promptView);
        final EditText countUsed = (EditText) promptView.findViewById(R.id.et_count_used);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Defines an object to contain the updated values
                        Double balanceDiff = (Double.valueOf(balance.getText().toString())) - (Double.valueOf(countUsed.getText().toString()));
                        Log.d("diff", balanceDiff.toString());
                        if (balanceDiff < 0) {
                            balance.setError("Balance can not be less than 0");
                        } else {
                            balance.setText(balanceDiff.toString());
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
    public void updateOrAddCard(View view) {
        if (validateRequiredFields()) {
            // general values for insert & update
            // expiration date
            String format = DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS.format(getDateFromDatePicker(expirationDatePickerDialog.getDatePicker()));
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(CardEntry.BALANCE, Double.parseDouble(balance.getText().toString()));
            contentValues.put(CardEntry.EXPIRATION_DATE, format);

            if (!isCreateMode) {
                // Defines selection criteria for the rows you want to update
                String mSelectionClause = CardEntry._ID + " = ?";
                String[] mSelectionArgs = {String.valueOf(this.cardId)};//card id
                getContentResolver().update(CardEntry.CONTENT_URI, contentValues, mSelectionClause, mSelectionArgs);
            } else {
                // card type
                Spinner typesSpinner = (Spinner) findViewById(R.id.type_spinner);
                Cursor c = (Cursor) typesSpinner.getSelectedItem();
                String cardTypeId = c.getString(c.getColumnIndex(CardTypeEntry._ID));
                contentValues.put(CardEntry.CARD_TYPE_ID, cardTypeId);
                // card number & cvv
                contentValues.put(CardEntry.CARD_NUMBER, this.cardNumber.getText().toString());
                contentValues.put(CardEntry.CVV, this.cvv.getText().toString());

                getContentResolver().insert(CardEntry.CONTENT_URI, contentValues);
            }
            // start cards display
            Intent intent = new Intent(this, DisplayCardsActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateRequiredFields() {
        boolean isValid = true;

        // expiration date
        if (expirationDateET.getText() == null || expirationDateET.getText().toString().trim().equals("")) {
            expirationDateET.setError("Expiration date is required!");
            isValid = false;
        } else if (getDateFromDatePicker(expirationDatePickerDialog.getDatePicker()).compareTo(new Date()) < 0) {
            expirationDateET.setError("Expiration date has to be greater than today!");
        }

        // balance
        if (balance.getText() == null || balance.getText().toString().trim().equals("")) {
            balance.setError("Balance is required!");
            isValid = false;
        } else if (isCreateMode && "0".equals(balance.getText())) {
            balance.setError("Balance has to be greater than 0!");
            isValid = false;
        }

        if (isCreateMode) {
            // card number
            if (cardNumber.getText() == null || cardNumber.getText().toString().trim().equals("")) {
                cardNumber.setError("Card Number is required!");
                isValid = false;
            } else if ("0".equals(cardNumber.getText())) {
                cardNumber.setError("Card Number has to be greater than 0!");
                isValid = false;
            }
            // cvv
            if (cvv.getText() == null || cvv.getText().toString().trim().equals("")) {
                cvv.setError("Card Verification Valid is required!");
                isValid = false;
            } else if ("0".equals(cvv.getText())) {
                cvv.setError("Card Verification Valid has to be greater than 0!");
                isValid = false;
            } else if (cvv.getText().length() > 3) {
                cvv.setError("Card Verification Valid is limited to 3 chars!");
                isValid = false;
            }

            if (isValid) {
                // check cardNumber & cvv uniqueness
                String selection = CardEntry.CARD_NUMBER + " = ? or " + CardEntry.CVV + " = ?";
                String[] selectionArgs = {cardNumber.getText().toString(), cvv.getText().toString()};
                Cursor cursor = getContentResolver().query(CardEntry.CONTENT_URI, null, selection, selectionArgs, null);
                if (cursor.moveToFirst()) {
                    isValid = false;
                    if (cardNumber.getText().toString().equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(CardEntry.CARD_NUMBER))))
                        cardNumber.setError("Card Number already exists!");
                    if (cvv.getText().toString().equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(CardEntry.CVV))))
                        cvv.setError("Cvv already exists!");
                }
            }
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