package browser;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import resources.Strings;
import utils.Logger;

public class Chrome implements Browser{
	
	private static boolean isConnected = false;
	private static ChromeDriver DRIVER = null;
	
	@Override
	public ERROR_CODE connectDriver(String options, int connectionTimeout) {
		try {
			System.setProperty(Strings.CHROME_WEB_DRIVER_ID, Strings.CHROME_WEB_DRIVER_PATH);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.browserLogging(ERROR_CODE.FAILED_LOAD_DRIVER);
			return ERROR_CODE.FAILED_LOAD_DRIVER;
		}
		
		ChromeOptions driverOption = new ChromeOptions();
		try {
			for (String option : options.split("--")) {
				if (option.isEmpty() == true) {
					continue;
				}
				
				driverOption.addArguments("--" + option.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.browserLogging(ERROR_CODE.INVALID_DRIVER_OPTION);
			return ERROR_CODE.INVALID_DRIVER_OPTION;
		}
		
		try {
			DRIVER = new ChromeDriver(driverOption);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.browserLogging(ERROR_CODE.FAILED_CONNECT_DRIVER);
			return ERROR_CODE.FAILED_CONNECT_DRIVER;
		}
		
		isConnected = true;
		
		return ERROR_CODE.SUCCESS;
	}

	@Override
	public ERROR_CODE closeDriver() {
		try {
			if (DRIVER == null) {
				Logger.browserLogging(ERROR_CODE.NOT_CONNECT_DRIVER);
				return ERROR_CODE.NOT_CONNECT_DRIVER;
			}
			
			DRIVER.close();
			DRIVER.quit();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.browserLogging(ERROR_CODE.FAILED_CLOSE_DRIVER);
			return ERROR_CODE.FAILED_CLOSE_DRIVER;
		}
		
		return ERROR_CODE.SUCCESS;
	}

	@Override
	public ERROR_CODE connectUrl(String url) {
		if (DRIVER == null) {
			Logger.browserLogging(ERROR_CODE.NOT_CONNECT_DRIVER);
			return ERROR_CODE.NOT_CONNECT_DRIVER;
		}
		
		try
		{
			URI.create(url);
		} catch (Exception e) {
			Logger.browserLogging(ERROR_CODE.INVALID_URL);
			return ERROR_CODE.INVALID_URL;
		}
		
		try {
			DRIVER.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.browserLogging(ERROR_CODE.REDIRECTION_FAIL);
			return ERROR_CODE.REDIRECTION_FAIL;
		}
		
		return ERROR_CODE.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findElements(FIND_ELEMENT_BY_TYPE type, String name, FIND_ELEMENT_WAITING waiting, int waitingTimeout) {
		By byType;
		
		switch (type) {
			case CLASS:
				byType = By.className(name);
				break;
			case ID:
				byType = By.id(name);
				break;
			case XPATH:
				byType = By.xpath(name);
				break;
			default:
				return new ArrayList<>();
		}
		
		List<WebElement> elements = new ArrayList<>();
		int currentTime = 0;
		while (waiting == FIND_ELEMENT_WAITING.TRUE && currentTime <= waitingTimeout) {
			if (currentTime >= waitingTimeout) {
				return new ArrayList<>();
			}
			
			elements = DRIVER.findElements(byType);
			
			if (elements.size() > 0) {
				break;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			currentTime += 100;
		}
		
		try {
			return (List<T>) elements;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

	@Override
	public ERROR_CODE clickElement(FIND_ELEMENT_BY_TYPE type, String name, FIND_ELEMENT_WAITING waiting, int waitingTimeout) {
		 List<WebElement> elements = findElements(type, name, waiting, waitingTimeout);
		 
		 if (elements.size() == 0) {
			 Logger.browserLogging(ERROR_CODE.NOT_FOUND_ELEMENT);
			 return ERROR_CODE.NOT_FOUND_ELEMENT;
		 }
		 try {
			 elements.get(0).click();
		 } catch (Exception e) {
			 Logger.browserLogging(ERROR_CODE.FAILED_EVENT);
			 return ERROR_CODE.FAILED_EVENT;
		 }
		 
		return ERROR_CODE.SUCCESS;
	}

	@Override
	public ERROR_CODE inputElement(FIND_ELEMENT_BY_TYPE type, String name, String inputText, FIND_ELEMENT_WAITING waiting, int waitingTimeout) {
		 List<WebElement> elements = findElements(type, name, waiting, waitingTimeout);
		 
		 if (elements.size() == 0) {
			 Logger.browserLogging(ERROR_CODE.NOT_FOUND_ELEMENT);
			 return ERROR_CODE.NOT_FOUND_ELEMENT;
		 }
		 try {
			 elements.get(0).sendKeys(inputText);
		 } catch (Exception e) {
			 Logger.browserLogging(ERROR_CODE.FAILED_EVENT);
			 return ERROR_CODE.FAILED_EVENT;
		 }
		 
		return ERROR_CODE.SUCCESS;
	}

	@Override
	public String getAttributeElement(FIND_ELEMENT_BY_TYPE type, String name, String tag, FIND_ELEMENT_WAITING waiting, int waitingTimeout) {
		List<WebElement> elements = findElements(type, name, waiting, waitingTimeout);
		
		if (elements.size() == 0) {
			return "";
		} else {
			return elements.get(0).getAttribute("outerHTML");
		}
	}
	
}
