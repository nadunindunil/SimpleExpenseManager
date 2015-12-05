package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.*;



/**
 * Created by NADUN on 12/5/2015.
 */
public class InDBAccountDAO implements AccountDAO {

    private SQLiteDatabase myDB;

    public InDBAccountDAO() {
        myDB = SQLiteDatabase.openOrCreateDatabase("db1",null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS Accounts(Username VARCHAR,AccountNO VARCHAR,BankName VARCHAR,Balance NUMERIC(7,2));");
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> anums = null;
        Cursor cursor = myDB.rawQuery("SELECT AccountNO FROM Accounts", null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    String num = cursor.getString(cursor.getColumnIndex("AccountNO"));

                    anums.add(num);

                } while (cursor.moveToNext());

            }

        }
        return anums;
    }

    @Override
    public List<Account> getAccountsList() {
        List<String> accnames = null;
        Cursor cursor = myDB.rawQuery("SELECT Username FROM Accounts", null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    String num = cursor.getString(cursor.getColumnIndex("Username"));

                    accnames.add(num);

                } while (cursor.moveToNext());

            }

        }
        return null;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }

    @Override
    public void addAccount(Account account) {
        myDB.execSQL("INSERT INTO Accounts VALUES('" + account.getAccountHolderName() +"','"+account.getAccountNo()+"','"+account.getBankName()+"','"+account.getBalance()+"');");


    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
