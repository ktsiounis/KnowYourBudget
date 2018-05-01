package ai.chainproof.theqteam.knowyourbudget.activities;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ai.chainproof.theqteam.knowyourbudget.R;
import ai.chainproof.theqteam.knowyourbudget.data.TransactionContract;
import ai.chainproof.theqteam.knowyourbudget.model.Transaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowTransactionActivity extends AppCompatActivity {

    Transaction transaction;
    @BindView(R.id.imageViewShow) public ImageView imageView;
    @BindView(R.id.amountTVshow) public TextView amount;
    @BindView(R.id.dateTVshow) public TextView date;
    @BindView(R.id.categoryTVshow) public TextView category;
    @BindView(R.id.notesTVshow) public TextView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transaction);

        ButterKnife.bind(this);

        transaction = getIntent().getParcelableExtra("transaction");

        Picasso.with(this)
                .load(Uri.parse("file:///"+transaction.getImagePath()))
                .error(R.mipmap.no_image)
                .into(imageView);
        if(transaction.getCategory().equals("Salary") || transaction.getCategory().equals("Other Income")){
            amount.setText("+ " + transaction.getAmount());
            amount.setTextColor(Color.GREEN);
        } else {
            amount.setText("- " + transaction.getAmount());
            amount.setTextColor(Color.RED);
        }
        date.setText(transaction.getDate());
        category.setText(transaction.getCategory());
        notes.setText(transaction.getNotes());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_delete){
            int itemsDeleted = getContentResolver().delete(TransactionContract.TransactionsEntry.CONTENT_URI,
                    "_ID=?",
                    new String[]{String.valueOf(transaction.getId())});

            if(itemsDeleted != 0) {
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
