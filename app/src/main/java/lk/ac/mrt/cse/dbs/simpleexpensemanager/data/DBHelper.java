package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NADUN on 12/6/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper helper1 = null;

    private static final String DATABASE_NAME = "130217b.db";
    private static final int DATABASE_VERSION = 1;



    public static final String TABLE_ACCOUNT = "accounts";
    public static final String COLUMN_ACCOUNT_NO = "accountNo";
    public static final String COLUMN_BANK_NAME = "bankName";
    public static final String COLUMN_ACCOUNT_HOLDER = "accountHolderName";
    public static final String COLUMN_BALANCE = "balance";


    public static final String TABLE_TRANSACTION = "transactions";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EXPENSE_TYPE = "expenseType";
    public static final String COLUMN_AMOUNT = "amount";

    //queries

    String CREATE_ACCOUNT_TABLE = "Create Table accounts(accountNo varchar(6),bankName varchar(50),accountHolderName varchar(30),balance double);";
    String CREATE_TRANSACTION_TABLE = "Create Table transactions(accountNo varchar(6),date varchar(10),expenseType varchar(10),amount double);";

    public static DBHelper doSingleton(Context context){
        if(helper1 == null){

            helper1 = new DBHelper(context);
        }

        return helper1;

    }

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRANSACTION_TABLE);
        db.execSQL(CREATE_ACCOUNT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }
}
