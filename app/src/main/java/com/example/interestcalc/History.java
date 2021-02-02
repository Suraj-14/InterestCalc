package com.example.interestcalc;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    private static final String TAG = History.class.getSimpleName();

    private HistorySQLiteConnection mDatabase;
    private ArrayList<Calc_data> allHistory=new ArrayList<>();
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);

        RecyclerView contactView = (RecyclerView) findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);
        mDatabase = new HistorySQLiteConnection(this);
        allHistory = mDatabase.listContacts();

        if (allHistory.size() > 0) {
            contactView.setVisibility(View.VISIBLE);
            mAdapter = new HistoryAdapter(this, allHistory);
            contactView.setAdapter(mAdapter);

        } else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no History Available. ", Toast.LENGTH_LONG).show();
        }
    }


@Override
protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
        mDatabase.close();
        }
        }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all) {
            mDatabase.deleteAll();

            //refresh the activity page.
            this.finish();
            this.startActivity(this.getIntent());
            Toast.makeText(this, "Clicked " , Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
