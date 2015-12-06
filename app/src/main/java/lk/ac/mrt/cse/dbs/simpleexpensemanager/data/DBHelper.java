package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NADUN on 12/6/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "130217b.db";
    private static final int DATABASE_VERSION = 1;



    public static final String TABLE_ACCOUNT = "account";
    public static final String COLUMN_ACCOUNT_NO = "accountNo";
    public static final String COLUMN_BANK_NAME = "bankName";
    public static final String COLUMN_ACCOUNT_HOLDER = "accountHolderName";
    public static final String COLUMN_BALANCE = "balance";


    public static final String TABLE_TRANSACTION = "transaction";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EXPENSE_TYPE = "expenseType";
    public static final String COLUMN_AMOUNT = "amount";

    //queries
    String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "("
            + COLUMN_ACCOUNT_NO + " VARCHAR(6) PRIMARY KEY," + COLUMN_BANK_NAME
            + " VARCHAR(50)," + COLUMN_ACCOUNT_HOLDER + " VARCHAR(30)," + COLUMN_BALANCE + " DOUBLE "+ ")";

    String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
            + COLUMN_ACCOUNT_NO + " VARCHAR(6)," + COLUMN_DATE
            + " VARCHAR(10)," + COLUMN_EXPENSE_TYPE + " VARCHAR(10), " + COLUMN_AMOUNT +
            " DOUBLE " + " , FOREIGN KEY(" + COLUMN_ACCOUNT_NO + ") REFERENCES "
            + TABLE_ACCOUNT+"("+ COLUMN_ACCOUNT_NO +")"+")";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }
}
