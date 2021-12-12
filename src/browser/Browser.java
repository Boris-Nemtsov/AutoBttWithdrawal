package browser;

import java.util.List;

public interface Browser {
	public enum ERROR_CODE { SUCCESS, NOT_CONNECT_DRIVER, FAILED_LOAD_DRIVER, FAILED_CLOSE_DRIVER, FAILED_CONNECT_DRIVER, INVALID_DRIVER_OPTION , INVALID_URL, REDIRECTION_FAIL, NOT_FOUND_ELEMENT, FAILED_EVENT };
	public enum FIND_ELEMENT_BY_TYPE  { CLASS, ID, XPATH }; 
	public enum FIND_ELEMENT_WAITING  { TRUE, FALSE };
	
	/**
	 * Preparing the driver to connect web,
	 * @param option
	 * @param connectionTimeout
	 * @return
	 */
	public ERROR_CODE connectDriver(String options, int connectionTimeout);
	
	/**
	 * Disposing the driver 
	 * @return
	 */
	public ERROR_CODE closeDriver();
	
	/**
	 * Move to specific url on Browser,
	 * @param url
	 * @return
	 */
	public ERROR_CODE connectUrl(String url);
	
	/**
	 * Find elements from Browser,
	 * @param type
	 * @param name
	 * @param waiting
	 * @param waitingTimeout
	 * @return
	 */
	public <T> List<T> findElements(FIND_ELEMENT_BY_TYPE type, String name, FIND_ELEMENT_WAITING  waiting, int waitingTimeout);
	
	/**
	 * Invoke the click event.
	 * @param type
	 * @param name
	 * @param waiting
	 * @param waitingTimeout
	 * @return
	 */
	public ERROR_CODE clickElement(FIND_ELEMENT_BY_TYPE type, String name, FIND_ELEMENT_WAITING  waiting, int waitingTimeout);
	
	/**
	 * Input string into the element.
	 * @param type
	 * @param name
	 * @param inputText
	 * @param waiting
	 * @param waitingTimeout
	 * @return
	 */
	public ERROR_CODE inputElement(FIND_ELEMENT_BY_TYPE type, String name, String inputText, FIND_ELEMENT_WAITING  waiting, int waitingTimeout);
	
	/**
	 * Get the string from the element
	 * @param type
	 * @param name
	 * @param tag
	 * @param waiting
	 * @param waitingTimeout
	 * @return
	 */
	public String getAttributeElement(FIND_ELEMENT_BY_TYPE type, String name, String tag, FIND_ELEMENT_WAITING  waiting, int waitingTimeout);
	
	/**
	 * Whether to be prepared the driver,
	 */
	public boolean isConnected();
}
