/**
 * 
 */
package jflow.core.util;

/**
 * parse configuration file Exception
 * 
 * @author dzh
 * @date Apr 23, 2014 4:45:38 PM
 * @since 1.0
 */
public class FlowParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlowParseException(String message) {
		super(message);
	}

	public FlowParseException(Throwable cause) {
		super(cause);
	}

}
