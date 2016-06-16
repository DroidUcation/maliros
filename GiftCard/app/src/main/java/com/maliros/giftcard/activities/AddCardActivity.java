package com.maliros.giftcard.activities;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.maliros.giftcard.R;
import com.maliros.giftcard.dbhelpers.entries.CardEntry;
import com.maliros.giftcard.dbhelpers.entries.CardTypeEntry;
import com.maliros.giftcard.entities.CardType;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TRANSITION = "EXTRA_TRANSITION";
    public static final String TRANSITION_FADE_FAST = "FADE_FAST";
    private int typeIndex = 1;
    private String typeAppend = "";
    TextView tvTypeOfCard;
    private static final int SELECT_PICTURE = 0;
    private ImageView imageView;
    private EditText etCardType, etCount, etDate;
    Spinner typesSpinner;
    public static boolean isFirstFocus1 = false, isFirstFocus2 = false, isFirstFocus3 = false;
    private List<CardType> typeSpinnerElements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        imageView = (ImageView) findViewById(android.R.id.icon);
        ButterKnife.bind(this);
        /***************************/
        /*String selection = FactsDbContract.FACT_NAME + " = ?";*/
        String[] projection = new String[]{CardTypeEntry.NAME,CardTypeEntry.KEY};
        Cursor cursor = getContentResolver().query(CardTypeEntry.CONTENT_URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //typeSpinnerElements.add(cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME)));
                typeSpinnerElements.add(new CardType( false,cursor.getInt(cursor.getColumnIndex(CardTypeEntry.KEY)),cursor.getString(cursor.getColumnIndex(CardTypeEntry.NAME))));
            } while (cursor.moveToNext());
        }


        /***************************/
        typesSpinner = (Spinner) findViewById(R.id.type_spinner);
        tvTypeOfCard = (TextView) findViewById(R.id.tv_type_of_card);
        etCardType = (EditText) findViewById(R.id.et_card_type);
        etCount = (EditText) findViewById(R.id.et_count);
        etDate = (EditText) findViewById(R.id.et_date);
        final ArrayAdapter<CardType> typesAdapter = new ArrayAdapter<CardType>(this,
                android.R.layout.simple_spinner_item, typeSpinnerElements);

        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(typesAdapter);

        etCardType.setOnClickListener(this);
        etCount.setOnClickListener(this);
        etDate.setOnClickListener(this);
/*
        etCount.setOnFocusChangeListener(null);
                etDate.setOnFocusChangeListener(null);
        etCardType.setOnFocusChangeListener(null);*/
        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                typeIndex = position + 1;
                typeAppend = parent.getItemAtPosition(position).toString();
                CardType cardType = (CardType) parent.getItemAtPosition(position);
                Log.d("key is:: ", String.valueOf(cardType.getKey()));
                tvTypeOfCard.setText(typeAppend);
                Log.d("**********typeAppend : ", typeAppend);
                if (typeAppend.equalsIgnoreCase("Other")) {
                    etCardType.setVisibility(View.VISIBLE);
                    typesSpinner.setVisibility(View.INVISIBLE);
                    tvTypeOfCard.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
       /* Intent intent = new Intent(this, DisplayCardsActivity.class);
        this.startActivity(intent);*/
       /* etCardType.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {String  etCardTypeText = etCardType.getText().toString();
                if (hasFocus == true)
                      etCardType.setText("");

                    etCardType.setText(etCardTypeText);
            }
        });*/
       /* etCount.setOnFocusChangeListener(new View.OnFocusChangeListener()
        { String  etCountText = etCount.getText().toString();
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus == true)
                    etCount.setText("");
                etCardType.setText("");
            }
        });
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            { String  etDateText = etDate.getText().toString();

                if (hasFocus == true)
                    etDate.setText("");
                else
                    etDate.setText(etDateText);

            }
        });*/
    /*    etDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                etDate.setText("");
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == etCardType && !isFirstFocus1) {
            etCardType.setText("");
            Log.d("Michal", " if( v == etCardType)");
            isFirstFocus1 = true;
        }

        if (v == etCount && !isFirstFocus2) {
            etCount.setText("");
            Log.d("Michal", " if( v == etCount)");
            isFirstFocus2 = true;
        }

        if (v == etDate && !isFirstFocus3) {
            etDate.setText("");
            Log.d("Michal", " if( v == etDate)");
            isFirstFocus3 = true;
        }

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

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void uploadPhoto(View view) {
        try {
            executeMultipartPost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*  class RetrieveFeedTask extends AsyncTask<String, Void, AddCardActivity> {

          private Exception exception;

          protected RSSFeed doInBackground(String... urls) {
              try {
                  URL url= new URL(urls[0]);
                  SAXParserFactory factory =SAXParserFactory.newInstance();
                  SAXParser parser=factory.newSAXParser();
                  XMLReader xmlreader=parser.getXMLReader();
                  RssHandler theRSSHandler=new RssHandler();
                  xmlreader.setContentHandler(theRSSHandler);
                  InputSource is=new InputSource(url.openStream());
                  xmlreader.parse(is);
                  return theRSSHandler.getFeed();
              } catch (Exception e) {
                  this.exception = e;
                  return null;
              }
          }

          protected void onPostExecute(RSSFeed feed) {
              // TODO: check this.exception
              // TODO: do something with the feed
          }
      }*/
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

            // Log.e(e.getClass().getName(), e.getMessage());

        }

    }

    public void AddCard(View view) {

        ContentValues values = new ContentValues();
        values.put(CardEntry.CARD_TYPE,((CardType)typesSpinner.getSelectedItem()).getKey());
       /* values.put(CardEntry.IS_FOR_UNIQUE_STORE, 1);*/
//        values.put(CardEntry.UNIQUE_STORE_NAME, "ZERZ");
        values.put(CardEntry.BALANCE, String.valueOf(etCount.getText()));
//        values.put(CardEntry.EXPIRATION_DATE, DateUtil.DATE_FORMAT_YYYYMMDDHHMMSS.format(String.valueOf(etDate.getText())));
        Uri insert = getContentResolver().insert(CardEntry.CONTENT_URI, values);

//        values.put(CardEntry.USER_KEY, 1);

        Intent intent = new Intent(this, DisplayCardsActivity.class);
        startActivity(intent);
    }
}

       /* String transition = getIntent().getStringExtra(EXTRA_TRANSITION);
        switch (transition) {

            case TRANSITION_FADE_FAST:
                Transition transitionFadeFast =
                        TransitionInflater.from(this).inflateTransition(R.transition.fade_fast);
                getWindow().setEnterTransition(transitionFadeFast);
                break;
        }*/

       /* String selection = CardTypeEntry.NAME + " = ?";
        String[] selectionArgs = new String[]{ CardTypeEntry.NAME};
        Cursor cursor = getContentResolver().query(GiftCardProvider.CARD_TYPE, null, selection, selectionArgs, null);
        cursor.moveToFirst();
        // fact text

        if( cursor != null && cursor.moveToFirst() ){
            textView.setText(cursor.getString(cursor.getColumnIndex(FactsDbContract.TEXT)));
            cursor.close();
        }
        TextView linkMoreInformation = (TextView) findViewById(R.id.coffee_overview13);
        linkMoreInformation.setMovementMethod(LinkMovementMethod.getInstance());*/