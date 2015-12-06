package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.*;



/**
 * Created by NADUN on 12/5/2015.
 */
public class InDBAccountDAO implements AccountDAO  {


    private DBHelper helper = null;

    public InDBAccountDAO(DBHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<String> getAccountNumbersList() {

        List<String> AccNumberList  = null;

        System.out.println(AccNumberList.size());
        System.out.println("in the accounts number list");

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT accountNO FROM"+ helper.TABLE_ACCOUNT, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String num = cursor.getString(cursor.getColumnIndex("AccountNO"));
                    AccNumberList.add(num);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return AccNumberList;
    }

    @Override
    public List<Account> getAccountsList() {

        List<Account> accountList = new ArrayList<>();

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM"+ helper.TABLE_ACCOUNT,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String accountNo= cursor.getString(0);
            String bankName= cursor.getString(1);
            String accountHolderName= cursor.getString(2);
            double balance =cursor.getDouble(3);
            Account acnt =new Account(accountNo,bankName,accountHolderName,balance);
            accountList.add(acnt);
            cursor.moveToNext();
        }
        cursor.close();
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        SQLiteDatabase db = helper.getWritableDatabase();


        Cursor cursor = db.rawQuery("Select * FROM " + helper.TABLE_ACCOUNT + " WHERE " + helper.COLUMN_ACCOUNT_NO
                + " = " + "'" + accountNo + "'" , null);

        Account account = null;

        if (cursor.moveToFirst()) {
            account= new Account(cursor.getString(0), cursor.getString(1) , cursor.getString(2), cursor.getDouble(3));
        }

        cursor.close();
        db.close();

        return account;
    }

    @Override
    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(helper.COLUMN_ACCOUNT_NO,account.getAccountNo());
        values.put(helper.COLUMN_ACCOUNT_HOLDER,account.getAccountHolderName());
        values.put(helper.COLUMN_BALANCE,account.getBalance());
        values.put(helper.COLUMN_BANK_NAME,account.getBankName());

        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(DBHelper.TABLE_ACCOUNT,null,values);
        db.close();

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        List<String> AccountList = this.getAccountNumbersList();
        if (AccountList.contains(accountNo)){
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("delete from accounts where accountNo='"+accountNo+"'");
            db.close();

        }

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        Account account = getAccount(accountNo);

        ContentValues contentValues = new ContentValues();
        contentValues.put(helper.COLUMN_ACCOUNT_NO , account.getAccountNo());
        contentValues.put(helper.COLUMN_BANK_NAME , account.getBankName());
        contentValues.put(helper.COLUMN_ACCOUNT_HOLDER , account.getBankName());


        if (expenseType == ExpenseType.INCOME){

            contentValues.put(helper.COLUMN_BALANCE , account.getBalance()+ amount);

        }else if (expenseType == ExpenseType.EXPENSE){
            contentValues.put(helper.COLUMN_BALANCE , account.getBalance()- amount);
        }


        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(helper.TABLE_ACCOUNT , contentValues , helper.COLUMN_ACCOUNT_NO + " = " + "'" + accountNo + "'" , null);


    }
}
