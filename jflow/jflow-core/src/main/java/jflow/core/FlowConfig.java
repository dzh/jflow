/**
 * 
 */
package jflow.core;

import java.util.Collection;

import jflow.core.node.NodeConfig;

/**
 * <p>
 * <li>TODO add or remove NodeConfig</li>
 * </p>
 * 
 * @author dzh
 * @date Apr 22, 2014 1:38:26 PM
 * @since 1.0
 */
public interface FlowConfig {

	/**
	 * flow configuration id
	 * 
	 * @return
	 */
	String getID();

	Class<? extends Flow> getClazz();

	Collection<NodeConfig> getNodeConfig();

	void addNodeConfig(NodeConfig nc);

	void removeNodeConfig(NodeConfig nc);

	NodeConfig getNodeConfig(String nodeID);

	/**
	 * release all loaded resources
	 */
	void dispose();

}
