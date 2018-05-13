package com.atm.example.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.atm.example.Account;
import com.atm.example.TransactionType;
import com.atm.example.model.Transaction;

public class AccountImpl implements Account {

	private Double balanceAmount = 0.0;
	private List<Transaction> txnHistory = new ArrayList<>();

	@Override
	public Double getBalanceAmount() {
		return balanceAmount!=null?balanceAmount:0.0;
	}

	@Override
	public synchronized Boolean credit(Double amount) {
		balanceAmount = balanceAmount + amount;
		updateTxnHistory(new Transaction(TransactionType.CREDIT, System.currentTimeMillis(), amount, balanceAmount));
		return true;
	}

	@Override
	public synchronized Boolean debit(Double amount) {
		balanceAmount = balanceAmount - amount;
		updateTxnHistory(new Transaction(TransactionType.DEBIT, System.currentTimeMillis(), amount, balanceAmount));
		return true;
	}

	@Override
	public List<Transaction> getTxnHistory(Integer count) {
		if(txnHistory.size()==0){
			return Collections.EMPTY_LIST;
		}
		count = count > txnHistory.size() ? txnHistory.size() : count;
		int fromIndex = txnHistory.size()-count;
		int toIndex = fromIndex + count;
		return txnHistory.subList(fromIndex,toIndex);
	}

	private void updateTxnHistory(Transaction transaction) {
		txnHistory.add(transaction);
	}

	@Override
	public Boolean checkForSufficientFund(Double amount) {
		if (amount < balanceAmount) {
			return true;
		} else {
			return false;
		}

	}
}
