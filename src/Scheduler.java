import java.math.BigDecimal;

import http.FoundationWallet;
import http.MyWallet;
import resources.Properties;
import resources.Properties.PROPERTY_KEY;
import resources.Strings;
import utils.Logger;

public class Scheduler {
	
	private static long lastAttempt = 0;
	private static double lastBalance = 0;
	
	public static void start() {
		MyWallet.ready();
		
		while (true) {
			if (rules() == false) {
				if (MyWallet.getIsReady() == false) {
					MyWallet.ready();
				}
				
				threadSleep(1000);
				continue;
			}
			
			MyWallet.withdrawal();
			
			lastAttempt = System.currentTimeMillis();
		}
	}
	
	private static boolean rules() {
		//Sleep
		if (System.currentTimeMillis() - lastAttempt <  Integer.parseInt(Properties.Settings.get(PROPERTY_KEY.scheduler_waiting_second)) * 1000) {
			return false;
		}
		
		//Balance
		double balance = FoundationWallet.getBalance();
		if (lastBalance != balance) {
			lastBalance = balance;
			 Logger.normalFormatLogging(String.format(Strings.SCHEDULER_ALERT_BALANCE, new BigDecimal(String.valueOf(balance).toString()).toPlainString()));
		}
		
		if (balance < Integer.parseInt(Properties.Settings.get(PROPERTY_KEY.foundation_min_balance))) {
			return false;
		}
		
		return true;
	}
	
	private static void threadSleep(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
