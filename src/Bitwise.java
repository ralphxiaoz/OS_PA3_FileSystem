/**
 * Manage bitwise operations in a byte or array of bytes.
 *
 * Unit tests are in {@see test/TestBitwise}.
 */
public class Bitwise {
    private static final int bitmasks[] = {128, 64, 32, 16, 8, 4, 2, 1};
	
    /**
     * Check to see if bit i is set in byte. Returns true if it is
     * set, false otherwise.
     */
    public static boolean isset(int i, byte b) {
    	if ((byte)(bitmasks[i] | b) != b)
    			return false;
    	return true;
    }

    /**
     * Check to see if bit i is set in array of bytes. Returns true if
     * it is set, false otherwise.
     */
    public static boolean isset(int i, byte bytes[]) {
    	return isset(i%8, bytes[i/8]);
    }

    /**
     * Set bit i in byte and return the new byte.
     */
    public static byte set(int i, byte b) {
    	b = (byte)(bitmasks[i] | b);
    	return b;
    }

    /**
     * Set bit i in array of bytes.
     */
    public static void set(int i, byte bytes[]) {
    	bytes[i/8] = set(i%8, bytes[i/8]);
    }

    /**
     * Clear bit i in byte and return the new byte.
     */
    public static byte clear(int i, byte b) {
    	return (byte)((~bitmasks[i]) & b);
    }

    /**
     * Clear bit i in array of bytes and return true if the bit was 1
     * before clearing, false otherwise.
     */
    public static boolean clear(int i, byte bytes[]) {
    	if (isset(i%8, bytes[i/8])) {
			bytes[i/8] = clear(i%8, bytes[i/8]);
			return true;
		}
    	return false;
    }

    /**
     * Clear every bit in array of bytes.
     *
     * There is no clearAll for a single byte, you can just get a new
     * byte for that.
     */
    public static void clearAll(byte bytes[]) {
        for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = 0;
        }
    }

    /**
     * Convert byte to a string of bits. Each bit is represented as
     * "0" if it is clear, "1" if it is set.
     */
    public static String toString(byte b) {
    	String bBinary;
		if (b >= 0) {
			bBinary = Integer.toBinaryString(b); // Is it OK to solve in this way?? -Ralph 11.20
		}else {
			bBinary = Integer.toBinaryString(b).substring(24);			
		}
    	int zeroNeeded = 8 - bBinary.length();
    	String result = bBinary;
    	for (int i = 0; i < zeroNeeded; i++) {
			result = "0" + result;
		}
    	return result;
    }

    /**
     * Convert array of bytes to string of bits (each byte converted
     * to a string by calling {@link #byteToString(byte b)}, every
     * byte separated by sep, every "every" bytes separated by lsep.
     */
    public static String toString(byte bytes[], String sep,
                                  String lsep, int every) {
        String s = "";
        for(int i = 0; i < bytes.length * 8; ++i) {
            if(i > 0)
                if(every > 0 && i % (8 * every) == 0)
                    s += lsep;
                else if(i % 8 == 0)
                    s += sep;
            s += isset(i, bytes) ? "1" : "0";
        }
        return s;
    }

    /**
     * Convert array of bytes to string of bits, each byte separated
     * by sep. See {@link #byteToString(byte bytes[], String sep)}.
     */
    public static String toString(byte bytes[], String sep) {
        return toString(bytes, sep, null, 0);
    }

    /**
     * Convert array of bytes to string of bits, each byte separated
     * by a comma, and every 8 bytes separated by a newline. See
     * {@link #byteToString(byte bytes[], String sep)}.
     */
    public static String toString(byte bytes[]) {
        return toString(bytes, ",", "\n", 8);
    }
}