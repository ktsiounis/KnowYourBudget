package ai.chainproof.theqteam.knowyourbudget.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Konstantinos Tsiounis on 24-Apr-18.
 */
public class TransactionContract {

    // The authority, which is how my code know which Content Provider to access
    public static final String AUTHORITY = "ai.chainproof.theqteam.knowyourbudget";

    // The base URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // This is the path for the "favorites" directory
    public static final String PATH_TRANSACTIONS = "transactions";

    public static final class TransactionsEntry implements BaseColumns{

        // FavoritesEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACTIONS).build();

        // Favorites table and column names
        public static final String TABLE_NAME = "transactions";

        // Since FavoritesEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_NOTES = "notes";

    }


}
