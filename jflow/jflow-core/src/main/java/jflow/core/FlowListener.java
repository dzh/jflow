/**
 * 
 */
package jflow.core;

import java.util.EventListener;

/**
 * @author dzh
 * @date Apr 22, 2014 2:35:09 PM
 * @since 1.0
 */
public interface FlowListener extends EventListener {

	void flowChanged(FlowEvent e);

}
