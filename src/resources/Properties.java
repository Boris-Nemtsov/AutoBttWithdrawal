package resources;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import utils.Logger;

public class Properties {
	
	public enum PROPERTY_KEY 
	{
		wallet_password, 
		withdrawal_waiting_strength, 
		browser, 
		withdrawal_unit, 
		foundation_min_balance, 
		scheduler_waiting_second 
	}
	
	public enum ERROR_CODE { SUCCESS, FAILED_LOAD_PROPERTIES, FILE_NOT_EXISTS }
	
	public static final HashMap<PROPERTY_KEY, String> Settings = new HashMap<PROPERTY_KEY, String>(); 
	
	public static ERROR_CODE load() {
		try {
			Settings.clear();
			
			if (new File(Strings.PROPERTIES_FILE_PATH).exists() == false) {
				Logger.propertiesLogging(ERROR_CODE.FILE_NOT_EXISTS);
				return ERROR_CODE.FILE_NOT_EXISTS;
			}
			
			Map<?, ?> jsonMap = new ObjectMapper().readValue(new File(Strings.PROPERTIES_FILE_PATH), Map.class);
			
			jsonMap.entrySet().forEach(entry -> {
					String key = entry.getKey().toString();
					String value = entry.getValue().toString();
					
					try
					{
						PROPERTY_KEY keyEnum = Enum.valueOf(PROPERTY_KEY.class, key);
						Settings.put(keyEnum, value);
						if (keyEnum != PROPERTY_KEY.wallet_password)
						{
							Logger.normalFormatLogging(String.format(Strings.PROPERTIES_ALERT_LOADED, key, value));
						}
					} catch (IllegalArgumentException e) {
						Logger.normalFormatLogging(String.format(Strings.PROPERTIES_ALERT_INVAILD, key, value));
					}
				});
		} catch (Exception e) {
			e.printStackTrace();
			Logger.propertiesLogging(ERROR_CODE.FAILED_LOAD_PROPERTIES);
			return ERROR_CODE.FAILED_LOAD_PROPERTIES;
		}
		
		return ERROR_CODE.SUCCESS;
	}
}
