package ru.pks.pizzatime;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends MainActivity {

    private static final String TAG = "WelcomeActivity";
    private static final String ORDER_FULL = "order_full";

    private String order;
    private String orderFull;
    private String bonusFull;

    private TextView orderView;
    private FloatingActionButton sendOrder;
    private CardView raphael;
    private CardView april;
    private CardView splinter;
    private CardView casey;

    int test1;
    int test3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Log.d(TAG, "onCreate Started");

        if (savedInstanceState != null) {
            orderFull = savedInstanceState.getString(ORDER_FULL);
        } else {
            orderFull = getString(R.string.empty);
        }

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFromDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Started");
        outState.putString(ORDER_FULL, orderFull);
    }

    protected void raphaelStarted() {
        Intent raphael = new Intent(WelcomeActivity.this, RaphaelActivity.class);
        startActivity(raphael);
    }

    private void initUI() {
        order = getString(R.string.order_text_view);

        raphael = findViewById(R.id.raphael);
        raphael.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raphaelStarted();
            }
        });

        april = findViewById(R.id.april);
        april.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inDevelop();
            }
        });

        splinter = findViewById(R.id.splinter);
        splinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inDevelop();
            }
        });

        casey = findViewById(R.id.casey);
        casey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inDevelop();
            }
        });

        sendOrder = findViewById(R.id.sendOrder);
        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.setType("text/plain");
                mail.putExtra(Intent.EXTRA_TEXT, orderFull);
                String[] to = {getString(R.string.e_mail)};
                mail.putExtra(Intent.EXTRA_EMAIL, to);
                mail.putExtra(Intent.EXTRA_SUBJECT, "Order");
                Intent chosenIntent = Intent.createChooser(mail, getString(R.string.send_order));
                startActivity(chosenIntent);
            }
        });

        orderView = findViewById(R.id.orderView);

        updateFromDB();

//            DatabaseUtils.dumpCursorToString(cursor);
        //TODO Read about dumpCursorToString

        updateUI();
    }

    private void orderFull() {
        orderFull = order + "\n";
    }

    private void updateFromDB() {
        connectDBRead();
        if (isConnectedRead) {
            Cursor cursor = db.query("PTIME",
                    new String[]{"TYPE", "TYPE_BONUS", "ORDER_QUANTITY"},
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                test1 = cursor.getInt(2);
            }
            if (cursor.moveToNext()) {
                test3 = cursor.getInt(2);
            }
            TextView textView = findViewById(R.id.textView);
            textView.setText(String.valueOf(test1));

            TextView textView2 = findViewById(R.id.textView2);
            textView2.setText(String.valueOf(test3));

            cursor.close();
        }
    }

    private void updateUI() {

        if (orderFull.equals(getString(R.string.empty))) {
            orderView.setText(order);
        } else {
            orderView.setText(orderFull);

        }
    }
}
