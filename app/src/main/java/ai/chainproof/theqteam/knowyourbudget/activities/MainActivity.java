package ai.chainproof.theqteam.knowyourbudget.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ai.chainproof.theqteam.knowyourbudget.R;
import ai.chainproof.theqteam.knowyourbudget.adapters.SectionsPagerAdapter;
import ai.chainproof.theqteam.knowyourbudget.fragments.MonthFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new MonthFragment(), "Current Month", new Bundle());
        mSectionsPagerAdapter.addFragment(new MonthFragment(), "Previous Month", new Bundle());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabs.setupWithViewPager(mViewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTransactionActivity.class);
                startActivity(intent);
            }
        });

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

}
