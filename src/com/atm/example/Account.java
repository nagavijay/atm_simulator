package com.atm.example;

import java.util.List;

import com.atm.example.model.Transaction;

public interface Account {

	Boolean credit(Double amount);

	Boolean debit(Double amount);

	List<Transaction> getTxnHistory(Integer count);
	
	Boolean checkForSufficientFund(Double amount);
	
	Double getBalanceAmount();
}
