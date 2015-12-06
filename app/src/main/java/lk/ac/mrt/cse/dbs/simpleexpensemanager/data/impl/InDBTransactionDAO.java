package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by NADUN on 12/5/2015.
 */
public class InDBTransactionDAO implements TransactionDAO{

    private final SQLiteDatabase myDB;

    public InDBTransactionDAO() {
        this.myDB = SQLiteDatabase.openOrCreateDatabase("130217b.db" ,null);
        this.myDB.execSQL("CREATE TABLE IF NOT EXISTS Transactions(Date DATE,AccountNO VARCHAR,expenseType VARCHAR,amount NUMERIC(7,2));");
    }
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        myDB.execSQL("INSERT INTO Transactions VALUES('" + date +"','"+ accountNo +"','"+ expenseType +"','"+ amount +"');");
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {

        return null;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        return null;
    }
}
