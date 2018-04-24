package ai.chainproof.theqteam.knowyourbudget.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static ai.chainproof.theqteam.knowyourbudget.data.TransactionContract.TransactionsEntry.TABLE_NAME;

/**
 * Created by Konstantinos Tsiounis on 24-Apr-18.
 */
public class TransactionProvider extends ContentProvider {

    public static final int TRANSACTIONS = 100;
    public static final int TRANSACTIONS_WITHID = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //directory
        uriMatcher.addURI(TransactionContract.AUTHORITY, TransactionContract.PATH_TRANSACTIONS, TRANSACTIONS);

        //single item
        uriMatcher.addURI(TransactionContract.AUTHORITY, TransactionContract.PATH_TRANSACTIONS + "/#", TRANSACTIONS_WITHID);

        return uriMatcher;
    }

    TransactionDBHelper mTransactionDBHelper;

    @Override
    public boolean onCreate() {

        mTransactionDBHelper = new TransactionDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mTransactionDBHelper.getReadableDatabase();

        Cursor returnCursor;

        switch (sUriMatcher.match(uri)) {
            case TRANSACTIONS:
                returnCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mTransactionDBHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case TRANSACTIONS:
                // Inserting values into favorites table
                long id = db.insert(TABLE_NAME, null, values);
                if( id > 0 ){
                    returnUri = ContentUris.withAppendedId(TransactionContract.TransactionsEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mTransactionDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int favoritesDeleted; // starts as 0

        switch (match) {
            case TRANSACTIONS:
                favoritesDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return favoritesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
