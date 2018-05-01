package ai.chainproof.theqteam.knowyourbudget.activities;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ai.chainproof.theqteam.knowyourbudget.R;
import ai.chainproof.theqteam.knowyourbudget.data.TransactionContract;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewTransactionActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public String mCurrentPhotoPath;

    @BindView(R.id.imageView)
    public ImageView mImageView;
    @BindView(R.id.spinner)
    public Spinner spinner;
    @BindView(R.id.amountEditText)
    public EditText amountEditText;
    @BindView(R.id.dateTextView)
    public TextView dateTextView;
    @BindView(R.id.notesEditText)
    public EditText notesEditText;
    @BindView(R.id.promptTV)
    public TextView promptTV;

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        ButterKnife.bind(this);

        myCalendar = Calendar.getInstance();

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        updateLabel();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewTransactionActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTextView.setText(sdf.format(myCalendar.getTime()));

        Log.d("DAYCAL", "updateLabel: " + dateTextView.getText().toString().split("/")[0]);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Error in Image Capture", "dispatchTakePictureIntent: ", ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d("ImagePath", "dispatchTakePictureIntent: " + photoFile.getAbsolutePath());
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                Log.d("PhotoURI", "dispatchTakePictureIntent: " + photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            promptTV.setVisibility(View.INVISIBLE);
            File image = new File(mCurrentPhotoPath);
            Picasso.with(this)
                    .load(Uri.fromFile(image))
                    .error(R.drawable.ic_launcher_background)
                    .into(mImageView);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            ContentValues values = new ContentValues();

            values.put(TransactionContract.TransactionsEntry.COLUMN_AMOUNT, amountEditText.getText().toString());
            values.put(TransactionContract.TransactionsEntry.COLUMN_CATEGORY, spinner.getSelectedItem().toString());
            values.put(TransactionContract.TransactionsEntry.COLUMN_IMAGE, mCurrentPhotoPath);
            values.put(TransactionContract.TransactionsEntry.COLUMN_DAY, dateTextView.getText().toString().split("/")[0]);
            values.put(TransactionContract.TransactionsEntry.COLUMN_MONTH, dateTextView.getText().toString().split("/")[1]);
            values.put(TransactionContract.TransactionsEntry.COLUMN_YEAR, dateTextView.getText().toString().split("/")[2]);
            values.put(TransactionContract.TransactionsEntry.COLUMN_NOTES, notesEditText.getText().toString());

            Uri uri = getContentResolver().insert(TransactionContract.TransactionsEntry.CONTENT_URI, values);

            if(uri != null){
                Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
                finish();
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
