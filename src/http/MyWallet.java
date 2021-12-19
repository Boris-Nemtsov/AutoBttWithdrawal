package http;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import browser.Browser;
import browser.Browser.FIND_ELEMENT_BY_TYPE;
import browser.Browser.FIND_ELEMENT_WAITING;
import browser.Chrome;
import resources.Properties;
import resources.Properties.PROPERTY_KEY;
import resources.Strings;
import utils.Logger;

public class MyWallet {
	private enum BROWSER_TYPE { CHROME, IE, FIREFOX }
	
	private static Browser BrowserDriver;
	private static int WaitingStrength;
	
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
	
	private static void initialize() {
		if (BrowserDriver == null) {
			BrowserDriver = getBrowser();
		} else {
			BrowserDriver.closeDriver();
		}
		
		try {
			WaitingStrength = Integer.parseInt(Properties.Settings.get(Properties.PROPERTY_KEY.withdrawal_waiting_strength));
		} catch (Exception e) {
		}
	}
	
	public static void withdrawal() {	
		Logger.normalFormatLogging(Strings.MY_WALLET_ALERT_WITHDRAWAL_START);

		if (getIsReady() == false) {
			ready();
		}
		
		BrowserDriver.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/div/button[2]", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * WaitingStrength);
		
		BrowserDriver.findElements(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/div/button",
				FIND_ELEMENT_WAITING.TRUE, 
				60000 * WaitingStrength);
		
		String result = BrowserDriver.getAttributeElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/p[1]",
				"outerHTML",
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * WaitingStrength);

		if (result.isEmpty() == true) {
			 Logger.normalFormatLogging(String.format(Strings.MY_WALLET_ALERT_WITHDRAWAL_END, "연결 실패"));
		} else {
			Matcher findResult = Pattern.compile("\">(.*?)</p>").matcher(result);
			if (findResult.find())
			{
				 Logger.normalFormatLogging(String.format(Strings.MY_WALLET_ALERT_WITHDRAWAL_END, findResult.group(1)));
			}
		}
		
		ready();
	}
	
	public static void ready() {
		initialize();
		
		BrowserDriver.connectDriver("--headless", 1000 * WaitingStrength);		

		BrowserDriver.connectUrl("http://127.0.0.1:5001/hostui/#/wallet");
		
		BrowserDriver.inputElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div/div[1]/div[1]/input",
				Properties.Settings.get(PROPERTY_KEY.wallet_password),
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * WaitingStrength);
		
		BrowserDriver.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div/div[2]/button", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * WaitingStrength);
		
		BrowserDriver.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div/div[2]/button", 
				FIND_ELEMENT_WAITING.TRUE, 
				5000 * WaitingStrength);
		
		threadSleep(2000 * WaitingStrength);
		
		BrowserDriver.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[2]/div", 
				FIND_ELEMENT_WAITING.TRUE, 
				5000 * WaitingStrength);
		
		BrowserDriver.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[2]/div[2]/div[1]", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * WaitingStrength);
		
		BrowserDriver.inputElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[1]/div[1]/input",
				 Properties.Settings.get(PROPERTY_KEY.withdrawal_unit),
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * WaitingStrength);

		threadSleep(2000);
		
		BrowserDriver.clickElement(
				FIND_ELEMENT_BY_TYPE.XPATH, 
				"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/button", 
				FIND_ELEMENT_WAITING.TRUE, 
				1000 * WaitingStrength);
	}
	
	public static boolean getIsReady() {
		try {
			if (BrowserDriver == null || BrowserDriver.isConnected() == false) {
				return false;
			}
			
			List<Object> findButton = BrowserDriver.<Object>findElements(
					FIND_ELEMENT_BY_TYPE.XPATH, 
					"/html/body/div/div[3]/div[2]/div/div[2]/div[1]/div[3]/div/div[2]/div/button[2]", 
					FIND_ELEMENT_WAITING.TRUE, 
					1000 * WaitingStrength);
			
			if (findButton.size() == 0) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
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
