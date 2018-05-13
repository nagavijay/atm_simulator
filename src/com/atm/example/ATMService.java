package com.atm.example;

import java.util.List;
import java.util.Map;

import com.atm.example.exception.InsufficientCashException;
import com.atm.example.exception.InsufficientFundException;
import com.atm.example.model.Transaction;

public interface ATMService {

	Boolean loadCash(List<Integer> billsList);

	List<String> deposit(Account account, List<Integer> billsList);

	Map<Integer,Integer> withdraw(Account account, Integer amount) throws InsufficientCashException, InsufficientFundException;

	List<Transaction> showMiniStatement(Account account);

	Double displayBalance(Account account);

}
