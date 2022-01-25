package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import browser.Browser;
import http.FoundationWallet;
import resources.Properties;
import resources.Strings;

public class Logger {

	public static void browserLogging(Browser.ERROR_CODE errorCode) {
		switch (errorCode) {
		case SUCCESS:
			normalFormatLogging(Strings.BROWSER_ERROR_SUCCESS);
			break;
		case FAILED_CLOSE_DRIVER:
			normalFormatLogging(Strings.BROWSER_ERROR_FAILED_CLOSE_DRIVER);
			break;
		case FAILED_CONNECT_DRIVER:
			normalFormatLogging(Strings.BROWSER_ERROR_FAILED_CONNECT_DRIVER);
			break;
		case FAILED_EVENT:
			normalFormatLogging(Strings.BROWSER_ERROR_FAILED_EVENT);
			break;
		case FAILED_LOAD_DRIVER:
			normalFormatLogging(Strings.BROWSER_ERROR_FAILED_LOAD_DRIVER);
			break;
		case INVALID_DRIVER_OPTION:
			normalFormatLogging(Strings.BROWSER_ERROR_INVAILD_DRIVER_OPTION);
			break;
		case INVALID_URL:
			normalFormatLogging(Strings.BROWSER_ERROR_INVAILD_URL);
			break;
		case NOT_CONNECT_DRIVER:
			normalFormatLogging(Strings.BROWSER_ERROR_NOT_CONNECT_DRIVER);
			break;
		case NOT_FOUND_ELEMENT:
			normalFormatLogging(Strings.BROWSER_ERROR_NOT_FOUND_ELEMENT);
			break;
		case REDIRECTION_FAIL:
			normalFormatLogging(Strings.BROWSER_ERROR_REDIRECTION_FAIL);
			break;
		default:
			break;
		}
	}
	
	public static void propertiesLogging(Properties.ERROR_CODE errorCode) {
		switch (errorCode) {
		case FAILED_LOAD_PROPERTIES:
			normalFormatLogging(Strings.PROPERTIES_ERROR_FAILED_LOAD_PROPERTIES);
			break;
		case FILE_NOT_EXISTS:
			normalFormatLogging(Strings.PROPERTIES_ERROR_FILE_NOT_EXISTS);
			break;
		case SUCCESS:
			normalFormatLogging(Strings.PROPERTIES_ERROR_SUCCESS);
			break;
		default:
			break;
		}
	}
	
	public static void foundationWalletLogging(FoundationWallet.ERROR_CODE errorCode) {
		switch (errorCode) {
		case CHANGE_ADDRESS:
			normalFormatLogging(Strings.FOUNDATION_WALLET_ERROR_CHANGE_ADDRESS);
			break;
		case FAILED_LOAD_API:
			normalFormatLogging(Strings.FOUNDATION_WALLET_ERROR_FAILED_LOAD_API);
			break;
		case SUCCESS:
			normalFormatLogging(Strings.FOUNDATION_WALLET_ERROR_SUCCESS);
			break;
		default:
			break;
		}
	}
	
	public static void normalFormatLogging(String message) {
		System.out.println(String.format("[%s] %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), message));
	}
}