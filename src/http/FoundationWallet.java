package http;

import utils.Logger;

public class FoundationWallet {
	public enum ERROR_CODE { SUCCESS, FAILED_LOAD_API, CHANGE_ADDRESS }
	
	public static double getBalance() {
		try {
			String walletInfo = grpc.RpcMain.getWalletInfo();
			
			if ("CHANGE_ADDRESS".equals(walletInfo) == true) {
				Logger.foundationWalletLogging(ERROR_CODE.CHANGE_ADDRESS);
				
				return 0;
			}
			
			if (walletInfo.contains("assetV2") == false) {
				Logger.foundationWalletLogging(ERROR_CODE.FAILED_LOAD_API);
				
				return 0;
			}
			
			walletInfo = walletInfo.substring(walletInfo.indexOf("key: \"1002000\""));
			walletInfo = walletInfo.substring(walletInfo.indexOf("value:") + "value: ".length(), walletInfo.indexOf("}"));
			
			return Long.parseLong(walletInfo.trim()) / 1000000d;
		} catch (Exception e) {
			Logger.foundationWalletLogging(ERROR_CODE.FAILED_LOAD_API);
			
			return 0;
		}
	}
}
