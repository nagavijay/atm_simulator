package com.atm.example.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.atm.example.ATMService;
import com.atm.example.Account;
import com.atm.example.exception.InsufficientCashException;
import com.atm.example.exception.InsufficientFundException;
import com.atm.example.impl.ATMServiceImpl;
import com.atm.example.impl.AccountImpl;
import com.atm.example.model.Transaction;

public class ATMTests {

	@Test
	public void testATMServiceDeposit() {
		ATMService service = new ATMServiceImpl();
		Account account = new AccountImpl();
		List<Integer> billsList = new ArrayList<Integer>();
		billsList.add(10);
		billsList.add(20);
		billsList.add(50);
		service.deposit(account, billsList);
		assertEquals(new Double(80),account.getBalanceAmount());
		billsList.clear();
		billsList.add(10);
		billsList.add(20);
		billsList.add(30);
		service.deposit(account, billsList);
		assertEquals(new Double(110), account.getBalanceAmount());
	}
	
	@Test(expected = InsufficientFundException.class)
	public void testATMServiceWithdrawInsufficientFund() throws InsufficientCashException, InsufficientFundException{
		ATMServiceImpl service = new ATMServiceImpl();
		Account account = new AccountImpl();
		List<Integer> billsList = new ArrayList<Integer>();
		billsList.add(10);
		billsList.add(20);
		billsList.add(50);
		service.deposit(account, billsList);
		service.getSystemAvailableCash().put(50, 2);
		service.getSystemAvailableCash().put(20, 2);
		service.withdraw(account, 90);
	}
	
	@Test(expected = InsufficientCashException.class)
	public void testATMServiceWithdrawInsufficientCash() throws InsufficientCashException, InsufficientFundException{
		ATMServiceImpl service = new ATMServiceImpl();
		Account account = new AccountImpl();
		List<Integer> billsList = new ArrayList<Integer>();
		billsList.add(10);
		billsList.add(20);
		billsList.add(50);
		service.deposit(account, billsList);
		service.getSystemAvailableCash().clear();
		service.withdraw(account, 50);
		
	}

	@Test
	public void testATMServiceWithdrawSuccess() throws InsufficientCashException, InsufficientFundException{
		ATMServiceImpl service = new ATMServiceImpl();
		Account account = new AccountImpl();
		List<Integer> billsList = new ArrayList<Integer>();
		billsList.add(10);
		billsList.add(20);
		billsList.add(50);
		service.deposit(account, billsList);
		service.withdraw(account, 50);
	}
	
	@Test
	public void testMiniStatement(){
		ATMServiceImpl service = new ATMServiceImpl();
		Account account = new AccountImpl();
		List<Integer> billsList = new ArrayList<Integer>();
		billsList.add(10);
		service.deposit(account, billsList);
		billsList.clear();
		billsList.add(20);
		service.deposit(account, billsList);
		billsList.clear();
		billsList.add(50);
		service.deposit(account, billsList);
		List<Transaction> txnList = service.showMiniStatement(account);
		assertEquals(3, txnList.size());
		assertEquals(new Double(50),txnList.get(0).getAmount());
		billsList.clear();
		billsList.add(10);
		service.deposit(account, billsList);
		billsList.clear();
		billsList.add(20);
		service.deposit(account, billsList);
		billsList.clear();
		billsList.add(50);
		service.deposit(account, billsList);
		txnList = service.showMiniStatement(account);
		assertEquals(5, txnList.size());
		assertEquals(new Double(20),txnList.get(4).getAmount());
	}
}
