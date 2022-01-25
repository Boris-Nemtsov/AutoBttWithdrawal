package grpc;

import java.security.MessageDigest;

public class Utils {
	private static final int[] BASE58_INDEXES = new int[128];
	private static final char[] BASE58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
	
	static {
		for (int i = 0; i < BASE58_INDEXES.length; i++) {
			BASE58_INDEXES[i] = -1;
		}
		for (int i = 0; i < BASE58_ALPHABET.length; i++) {
	    	BASE58_INDEXES[BASE58_ALPHABET[i]] = i;
    	}
	}
	
	public static byte[] encodeSha256(byte[] input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(input, 0, input.length);
			
			return digest.digest();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static byte[] decodeBase58(String input) throws IllegalArgumentException {
	    if (input.length() == 0) {
	      return new byte[0];
	    }
	    
	    byte[] input58 = new byte[input.length()];
	    
	    // Transform the String to a base58 byte sequence
	    for (int i = 0; i < input.length(); ++i) {
	    	char c = input.charAt(i);
	    	int digit58 = -1;
	    	
	    	if (c >= 0 && c < 128) {
		    	digit58 = BASE58_INDEXES[c];
		    }
	    	
	    	if (digit58 < 0) {
		    	throw new IllegalArgumentException("Illegal character " + c + " at " + i);
		    }

	    	input58[i] = (byte) digit58;
	    }
	    
	    // Count leading zeroes
    	int zeroCount = 0;
    	while (zeroCount < input58.length && input58[zeroCount] == 0) {
	    	++zeroCount;
	    }
    	
	    // The encoding
    	byte[] temp = new byte[input.length()];
    	int j = temp.length;

    	int startAt = zeroCount;
	    while (startAt < input58.length) {
	    	byte mod = divmod256(input58, startAt);
	    	if (input58[startAt] == 0) {
		    	++startAt;
		    }

	    	temp[--j] = mod;
    	}
	    
    	// Do no add extra leading zeroes, move j to first non null byte.
    	while (j < temp.length && temp[j] == 0) {
	    	++j;
	    }

    	byte[] range = new byte[temp.length - (j - zeroCount) - 4];
    	System.arraycopy(temp, j - zeroCount, range, 0, range.length);
    	encodeSha256(encodeSha256(range));
        
        return range;
    }
	
	private static byte divmod256(byte[] number58, int startAt) {
		int remainder = 0;
		
    	for (int i = startAt; i < number58.length; i++) {
	    	int digit58 = number58[i] & 0xFF;
	    	int temp = remainder * 58 + digit58;

	    	number58[i] = (byte)(temp / 256);
	    	remainder = temp % 256;
	    }

    	return (byte)remainder;
	}
}
