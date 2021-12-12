package http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import browser.Browser;
import browser.Browser.FIND_ELEMENT_BY_TYPE;
import browser.Browser.FIND_ELEMENT_WAITING;
import browser.Chrome;
import resources.Properties;
import resources.Strings;
import utils.Logger;
import resources.Properties.PROPERTY_KEY;

public class MyWallet {
	private enum BROWSER_TYPE { CHROME, IE, FIREFOX }
	
	private static Browser getBrowser() {
		BROWSER_TYPE type = BROWSER_TYPE.CHROME;
		
		switch (type) {
		case CHROME:
			return new Chrome();
		case IE:
		case FIREFOX:
		default:
			return null;
		}
	}
	
	public static void withdrawal() {		
		Browser browser = getBrowser();

		if (browser == null) {
			return;
		}
		
		Logger.normalFormatLogging(Strings.MY_WALLET_ALERT_WITHDRAWAL_START);
		
		int waitingStrength = 1;
		
		try {
			waitingStrength = Integer.parseInt(Properties.Settings.get(Properties.PROPERTY_KEY.withdrawal_waiting_strength));
		} catch (Exception e) {
		}
		
		if (browser.isConnected() == false) {
			browser.connectDriver("--headless", 1000 * waitingStrength);
		}
		
		browser.connectUrl("http://127.0.0.1:5001/hostui/#/wallet");
		
		browser.inputElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div/div[1]/div[1]/input",
				Properties.Settings.get(PROPERTY_KEY.wallet_password),
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * waitingStrength);
		
		browser.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div/div[2]/button", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * waitingStrength);
		
		browser.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div/div[2]/button", 
				FIND_ELEMENT_WAITING.TRUE, 
				5000 * waitingStrength);
		
		threadSleep(2000 * waitingStrength);
		
		browser.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[2]/div", 
				FIND_ELEMENT_WAITING.TRUE, 
				5000 * waitingStrength);
		
		browser.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[2]/div[2]/div[1]", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * waitingStrength);
		
		browser.inputElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[1]/div[1]/input",
				 Properties.Settings.get(PROPERTY_KEY.withdrawal_unit),
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * waitingStrength);

		threadSleep(2000);
		
		browser.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/button", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * waitingStrength);
		
		browser.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/div/button[2]", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * waitingStrength);
		
		browser.findElements(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/div/button",
				FIND_ELEMENT_WAITING.TRUE, 
				60000 * waitingStrength);
		
		String result = browser.getAttributeElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/p[1]",
				"outerHTML",
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * waitingStrength);

		if (result.isEmpty() == true) {
			 Logger.normalFormatLogging(String.format(Strings.MY_WALLET_ALERT_WITHDRAWAL_END, "연결 실패"));
		} else {
			Matcher findResult = Pattern.compile("\">(.*?)</p>").matcher(result);
			if (findResult.find())
			{
				 Logger.normalFormatLogging(String.format(Strings.MY_WALLET_ALERT_WITHDRAWAL_END, findResult.group(1)));
			}
		}
	}
	
	public static void dispose() {
		Browser browser  = getBrowser();
		
		if (browser != null)
		{
			browser.closeDriver();
		}
	}
	
	private static void threadSleep(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
