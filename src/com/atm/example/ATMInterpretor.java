package com.atm.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.atm.example.exception.InsufficientCashException;
import com.atm.example.exception.InsufficientFundException;
import com.atm.example.exception.InvalidDenominationException;
import com.atm.example.impl.ATMServiceImpl;
import com.atm.example.impl.AccountImpl;
import com.atm.example.model.Transaction;

public class ATMInterpretor {

	public static void main(String args[]) {

		Account account = new AccountImpl();
		ATMService service = new ATMServiceImpl();

		while (true) {

			final String newLine = System.getProperty("line.separator");
			String displayOptions = "\n 1. Deposit \n 2. Withdraw \n 3. Display Balance \n 4. Mini Statement \n 5. Exit \n Select an option:";
			System.out.print(displayOptions);
			Scanner in = new Scanner(System.in);

			Integer option = in.nextInt();
			System.out.println(" You entered option " + option);
			List<Integer> billsList = new ArrayList<Integer>();
			List<String> ackList = new ArrayList<String>();
			Map<Integer, Integer> cashWithDenominations = new HashMap<>();
			int denomination;
			Integer sum = 0;
			switch (option) {
			case 1:
				System.out.println("Enter ccy to deposit terminated by. e.g. 10 20 50 .");
				while (in.hasNextInt()) {
					denomination = in.nextInt();
					billsList.add(denomination);
				}

				ackList = service.deposit(account, billsList);
				for (String ack : ackList) {
					System.out.println(ack);
				}
				break;
			case 2:
				System.out.println("Enter amount to withdraw: ");
				while (in.hasNextInt()) {
					sum = in.nextInt();
					try {
						cashWithDenominations = service.withdraw(account, sum);

						for (Map.Entry<Integer, Integer> entry : cashWithDenominations.entrySet()) {
							System.out.println("Dispensing " + entry.getValue() + " " + entry.getKey() + "$ Bills");
						}
					} catch (InsufficientCashException | InsufficientFundException e) {
						System.out.println(e.getMessage());
					}
				}
				break;
			case 3:
				System.out.println("Available balance: " + service.displayBalance(account));
				break;
			case 4:
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------");
				System.out.println("Date Time				Transaction			Amount			Closing Balance");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------");
				for (Transaction txn : service.showMiniStatement(account)) {
					System.out.println(new Date(txn.getTxnTime()) + "		" + txn.getTxnType() + "				"
							+ txn.getAmount() + "			" + txn.getBalanceAmount());
				}
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------");
				break;
			case 5:
				System.out.println("Have a good day");
				System.exit(0);
			}

		}

	}

}
