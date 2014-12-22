/**
 * 
 */
package jflow.core.util;

import java.util.UUID;

/**
 * @author dzh
 * @date Apr 25, 2014 10:54:02 AM
 * @since 1.0
 */
public class IDUtil {

	public synchronized static String generateFlowID() {
		return UUID.randomUUID().toString();
	}

	public synchronized static String generateNodeChainID() {
		return UUID.randomUUID().toString();
	}

}
