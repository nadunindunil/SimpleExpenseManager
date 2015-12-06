package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by NADUN on 12/5/2015.
 */
public class InDBTransactionDAO implements TransactionDAO{

    DBHelper helper = null;

    public InDBTransactionDAO(DBHelper helper) {
        this.helper = helper;
    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        ContentValues values = new ContentValues();
        values.put(helper.COLUMN_ACCOUNT_NO,accountNo);
        values.put(helper.COLUMN_EXPENSE_TYPE,expenseType.toString());
        values.put(helper.COLUMN_AMOUNT,amount);

        SimpleDateFormat newDate = new SimpleDateFormat("dd-MM-yyyy");
        String modifyDate = newDate.format(date);
        values.put(helper.COLUMN_DATE,modifyDate);

        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(helper.TABLE_TRANSACTION,null,values);

        db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new ArrayList<>();

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ helper.TABLE_TRANSACTION,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Date newDate = null;
            String accountno= cursor.getString(0);
            String date= cursor.getString(1);
            String expenseType= cursor.getString(2);
            double amount =cursor.getDouble(3);
            SimpleDateFormat simpledate = new SimpleDateFormat("dd-MM-yyyy");
            try {
                newDate = simpledate.parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Transaction transaction =new Transaction(newDate,accountno,ExpenseType.valueOf(expenseType),amount);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * FROM " + helper.TABLE_TRANSACTION + " ORDER BY " + helper.COLUMN_DATE + " LIMIT " + limit, null);


        List<Transaction> transactions =  new ArrayList<>();

        if (cursor.moveToFirst()) {
            do{
                Transaction transaction = null;

                Date date = null;
                String dateString = cursor.getString(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    date = simpleDateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String accountNo = cursor.getString(0);
                ExpenseType expenseType = ExpenseType.valueOf(cursor.getString(2));
                Double amount = cursor.getDouble(3);
                transaction= new Transaction(date, accountNo ,expenseType , amount);
                transactions.add(transaction);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactions;
    }
}
