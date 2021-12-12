package http;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import resources.Strings;
import utils.Logger;

public class FoundationWallet {
	public enum ERROR_CODE { SUCCESS, FAILED_LOAD_API }
	
	@SuppressWarnings("unchecked")
	public static  double getBalance() {
		try {
			Map<?, ?> apiResult = new ObjectMapper().readValue(new URL(Strings.FOUNDATION_WALLET_API_URL), Map.class);
			
			for (LinkedHashMap<?, ?> field : ((ArrayList<LinkedHashMap<?, ?>>)apiResult.get("tokenBalances"))) {
				if ("TF5Bn4cJCT6GVeUgyCN4rBhDg42KBrpAjg".equals(field.get("owner_address"))) {
					return Long.parseLong(field.get("balance").toString()) / 1000000d;
				}
			}
		} catch (Exception e) {
			Logger.foundationWalletLogging(ERROR_CODE.FAILED_LOAD_API);
			return 0;
		}
		
		return 0;
	}
	
}
