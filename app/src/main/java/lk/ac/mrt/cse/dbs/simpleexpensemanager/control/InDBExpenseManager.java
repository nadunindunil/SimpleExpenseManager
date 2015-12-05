package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InDBAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InDBTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by NADUN on 12/5/2015.
 */
public class InDBExpenseManager extends ExpenseManager {
    @Override
    public void setup() throws ExpenseManagerException {
        TransactionDAO inDBTransactionDAO = new InDBTransactionDAO();
        setTransactionsDAO(inDBTransactionDAO);

        AccountDAO inDBAccountDAO = new InDBAccountDAO();
        setAccountsDAO(inDBAccountDAO);

        // dummy data
        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        Account dummyAcct3 = new Account("78990Z", "Deathstar BC", "Han Solo", 10000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);
        getAccountsDAO().addAccount(dummyAcct3);
    }
}
