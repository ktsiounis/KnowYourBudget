package ai.chainproof.theqteam.knowyourbudget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ai.chainproof.theqteam.knowyourbudget.data.TransactionContract.TransactionsEntry;

/**
 * Created by Konstantinos Tsiounis on 24-Apr-18.
 */
public class TransactionDBHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public static final String DATABASE_NAME = "transactions.db";

    public TransactionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE " + TransactionsEntry.TABLE_NAME + " (" +
                TransactionsEntry._ID               + " INTEGER PRIMARY KEY, "  +
                TransactionsEntry.COLUMN_AMOUNT     + " TEXT NOT NULL, "        +
                TransactionsEntry.COLUMN_CATEGORY   + " TEXT NOT NULL, "        +
                TransactionsEntry.COLUMN_DAY        + " TEXT NOT NULL, "        +
                TransactionsEntry.COLUMN_MONTH      + " TEXT NOT NULL, "        +
                TransactionsEntry.COLUMN_YEAR       + " TEXT NOT NULL, "        +
                TransactionsEntry.COLUMN_IMAGE      + " TEXT NOT NULL , "       +
                TransactionsEntry.COLUMN_NOTES      + " TEXT NOT NULL);"        ;
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TransactionsEntry.TABLE_NAME);
        onCreate(db);
    }
}
