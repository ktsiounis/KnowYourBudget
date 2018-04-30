package ai.chainproof.theqteam.knowyourbudget.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import ai.chainproof.theqteam.knowyourbudget.R;
import ai.chainproof.theqteam.knowyourbudget.adapters.SectionsPagerAdapter;
import ai.chainproof.theqteam.knowyourbudget.data.TransactionContract;
import ai.chainproof.theqteam.knowyourbudget.fragments.MonthFragment;
import ai.chainproof.theqteam.knowyourbudget.model.Transaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int TRANSACTIONS_LOADER_ID = 0;

    @BindView(R.id.tabs)
    public TabLayout tabs;
    @BindView(R.id.container)
    public ViewPager mViewPager;
    @BindView(R.id.addNewTransactionBtn)
    public FloatingActionButton fab;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        tabs.setupWithViewPager(mViewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTransactionActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        getSupportLoaderManager().initLoader(TRANSACTIONS_LOADER_ID, null, MainActivity.this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mTransactionsData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTransactionsData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTransactionsData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    Log.d("AsyncTaskLoader", "asynchronously load data.");
                    return getContentResolver().query(TransactionContract.TransactionsEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e("AsyncTaskLoader", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTransactionsData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Bundle infoForFragment = new Bundle();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        if(data.getCount()!=0){

            int amountIndex = data.getColumnIndex(TransactionContract.TransactionsEntry.COLUMN_AMOUNT);
            int categoryIndex = data.getColumnIndex(TransactionContract.TransactionsEntry.COLUMN_CATEGORY);
            int dayIndex = data.getColumnIndex(TransactionContract.TransactionsEntry.COLUMN_DAY);
            int monthIndex = data.getColumnIndex(TransactionContract.TransactionsEntry.COLUMN_MONTH);
            int yearIndex = data.getColumnIndex(TransactionContract.TransactionsEntry.COLUMN_YEAR);
            int imageIndex = data.getColumnIndex(TransactionContract.TransactionsEntry.COLUMN_IMAGE);
            int notesIndex = data.getColumnIndex(TransactionContract.TransactionsEntry.COLUMN_NOTES);

            data.moveToFirst();
            while (!data.isAfterLast()){
                transactions.add(new Transaction(data.getString(imageIndex),
                        data.getString(amountIndex),
                        data.getString(categoryIndex),
                        data.getString(dayIndex)+"/"+data.getString(monthIndex)+"/"+data.getString(yearIndex),
                        data.getString(notesIndex)));
                data.moveToNext();
            }

            infoForFragment.putParcelableArrayList("transactions", transactions);
            //Log.d("MainActivity", "onLoadFinished: " + transactions.get(1).getCategory());

            mSectionsPagerAdapter.addFragment(new MonthFragment(), "January", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "February", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "March", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "April", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "May", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "June", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "Jule", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "August", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "September", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "October", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "November", infoForFragment);
            mSectionsPagerAdapter.addFragment(new MonthFragment(), "December", infoForFragment);
            mViewPager.setAdapter(mSectionsPagerAdapter);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 3){
            getSupportLoaderManager().restartLoader(TRANSACTIONS_LOADER_ID, null,this);
        }
    }

}
