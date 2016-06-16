package com.maliros.giftcard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.maliros.giftcard.R;

public class UpdateBalanceActivity extends AppCompatActivity implements View.OnClickListener  {
    private String typeAppend = "";
    TextView tvTypeOfCard;
    private EditText etCountUsed;
    Spinner typesSpinner;
    private Button add;
    private EditText speedText;
    ArrayAdapter<String> adapter;
    private ListView AddValue;
    public static boolean isFirstFocus = false;
    private int typeIndex = 1;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_balance);

    typesSpinner = (Spinner) findViewById(R.id.type_spinner);
    tvTypeOfCard = (TextView) findViewById(R.id.tv_type_of_card);
        etCountUsed = (EditText) findViewById(R.id.et_count_used);

    final ArrayAdapter<CharSequence> typesAdapter = ArrayAdapter.createFromResource(this,
            R.array.type_spinner_elements, android.R.layout.simple_spinner_item);
    typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    typesSpinner.setAdapter(typesAdapter);
        etCountUsed.setOnClickListener(this);

    typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            typeIndex = position + 1;
            typeAppend = parent.getItemAtPosition(position).toString();
            tvTypeOfCard.setText(typeAppend);
            Log.d("**********typeAppend : ", typeAppend);


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    });
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        AddValue=(ListView)findViewById(R.id.ListView);
        AddValue.setAdapter(adapter);

        add = (Button) findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String val = tvTypeOfCard.getText().toString();
                adapter.add(val);
                val = etCountUsed.getText().toString();
                adapter.add(val);

                ((ArrayAdapter<Object>) AddValue.getAdapter()).notifyDataSetChanged();
            }
        });
}

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if( v == etCountUsed && !isFirstFocus)
        {  etCountUsed.setText("");
            Log.d("Michal", " if( v == etCountUsed)");
            isFirstFocus = true;}
    }

}
