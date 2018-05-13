package com.atm.example.model;

import com.atm.example.TransactionType;

public class Transaction {

	private Long txnTime;
	private TransactionType txnType;
	private Double amount;
	private Double balanceAmount;

	public Transaction(TransactionType txnType, Long txnTime, Double amount, Double balanceAmount) {
		this.txnType = txnType;
		this.txnTime = txnTime;
		this.amount = amount;
		this.balanceAmount = balanceAmount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(Long txnTime) {
		this.txnTime = txnTime;
	}

	public TransactionType getTxnType() {
		return txnType;
	}

	public void setTxnType(TransactionType txnType) {
		this.txnType = txnType;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	/*@Override
	public boolean equals(Object txn){
		return this.txnTime.equals(((Transaction)txn).getTxnTime());		
	}*/
}
