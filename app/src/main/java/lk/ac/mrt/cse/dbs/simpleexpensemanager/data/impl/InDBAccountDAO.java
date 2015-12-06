package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
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
public class InDBAccountDAO implements AccountDAO  {

    private final SQLiteDatabase myDB;

    public InDBAccountDAO() {
        this.myDB = SQLiteDatabase.openOrCreateDatabase("130217b.db",null);
        this.myDB.execSQL("CREATE TABLE IF NOT EXISTS Accounts(AccountNO VARCHAR,BankName VARCHAR,HolderName VARCHAR,balance NUMERIC(7,2));");
    }

    @Override
    public List<String> getAccountNumbersList() {

        List<String> anums  = null;
        anums.add("sedfr4");
        System.out.println(anums.size());
        System.out.println("in the accounts number list");
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

        List<Account> accountList = new ArrayList<>();
        Cursor details = myDB.rawQuery("SELECT * FROM Accounts",null);
        details.moveToFirst();
        while (!details.isAfterLast()) {
            String accountNo= details.getString(0);
            String bankName= details.getString(1);
            String accountHolderName= details.getString(2);
            double balance =details.getDouble(3);
            Account acnt =new Account(accountNo,bankName,accountHolderName,balance);
            accountList.add(acnt);
            details.moveToNext();
        }
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account ac = new Account("12123213U","Kottawa","Nadun",2345.0);
        return ac;
    }

    @Override
    public void addAccount(Account account) {
        //myDB.execSQL("INSERT INTO Accounts VALUES('" + account.getAccountHolderName() +"','"+account.getAccountNo()+"','"+account.getBankName()+"','"+account.getBalance()+"');");
        myDB.execSQL("INSERT INTO Accounts VALUES('"+account.getAccountNo()+"', '"+ account +"');");

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
