package com.atm.example.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.atm.example.ATMService;
import com.atm.example.Account;
import com.atm.example.exception.InsufficientCashException;
import com.atm.example.exception.InsufficientFundException;
import com.atm.example.exception.InvalidDenominationException;
import com.atm.example.model.Transaction;

public class ATMServiceImpl implements ATMService {

	private Map<Integer, Integer> systemAvailableCash = new TreeMap<>(Collections.reverseOrder());
	
	public Map<Integer, Integer> getSystemAvailableCash() {
		return systemAvailableCash;
	}

	public void setSystemAvailableCash(Map<Integer, Integer> systemAvailableCash) {
		this.systemAvailableCash = systemAvailableCash;
	}

	private static final Integer[] acceptedDenominations = { 10, 20, 50 };

	@Override
	public Boolean loadCash(List<Integer> billsList) {
		for (Integer bill : billsList) {
			try {
				updateSystemCash(bill);
			} catch (InvalidDenominationException e) {
				System.out.println("Invalid denomination: "+bill+"$");
			}
		}
		return true;
	}

	@Override
	public List<String> deposit(Account account, List<Integer> billsList){
		
		List<String> ackList = new ArrayList<String>();
		Integer amount = 0;
		
		for (Integer bill : billsList) {
			try {
				updateSystemCash(bill);
				ackList.add("Accepted: "+bill+"$");
				amount = amount + bill;
			} catch (InvalidDenominationException e) {
				ackList.add("Invalid denomination: "+bill+"$");
			}
		}
		account.credit(new Double(amount));
		return ackList;
	}

	@Override
	public Map<Integer,Integer> withdraw(Account account, Integer amount) throws InsufficientCashException, InsufficientFundException {

		Map<Integer, Integer> dispenseCashMap = new HashMap<>();

		if (!account.checkForSufficientFund(new Double(amount))) {
			throw new InsufficientFundException("Requested amount not available in Account");
		} else if (amount > getSystemCash()) {
			throw new InsufficientCashException("Requested amount not available in ATM");
		}
		Integer remainingAmount = amount;
		Integer countOfADenomination;

		for (Map.Entry<Integer, Integer> entry : systemAvailableCash.entrySet()) {
			if (remainingAmount == 0) {
				break;
			}

			countOfADenomination = remainingAmount / entry.getKey();

			if (countOfADenomination > 0 && countOfADenomination <= entry.getValue()) {
				dispenseCashMap.put(entry.getKey(), countOfADenomination);
				remainingAmount = remainingAmount - entry.getKey() * countOfADenomination;
				systemAvailableCash.put(entry.getKey(), entry.getValue() - countOfADenomination);
			}
		}

		account.debit(new Double(amount));

		//dispenseCash(dispenseCashMap);

		return dispenseCashMap;
	}

	@Override
	public List<Transaction> showMiniStatement(Account account) {
		
		List<Transaction> reverseList = new ArrayList<Transaction>(); 
		reverseList.addAll(account.getTxnHistory(5));
		Collections.reverse(reverseList);
		return reverseList;

	}

	@Override
	public Double displayBalance(Account account) {
		return account.getBalanceAmount();

	}

	private void updateSystemCash(Integer bill) throws InvalidDenominationException {
		
		if (!Arrays.asList(acceptedDenominations).contains(bill)) {
			throw new InvalidDenominationException(	"Invalid denomination: "+ bill + "$");
		}
		if (systemAvailableCash.get(bill) != null) {
			systemAvailableCash.put(bill, systemAvailableCash.get(bill) + 1);
		} else {
			systemAvailableCash.put(bill, 1);
		}
	
	}

	private Integer getSystemCash() {
		Integer sum = 0;
		for (Map.Entry<Integer, Integer> entry : systemAvailableCash.entrySet()) {
			sum = sum + entry.getKey() * entry.getValue();
		}
		return sum;
	}

}
