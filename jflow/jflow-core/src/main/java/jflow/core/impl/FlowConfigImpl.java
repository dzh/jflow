/**
 * 
 */
package jflow.core.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jflow.core.Flow;
import jflow.core.FlowConfig;
import jflow.core.node.NodeConfig;

/**
 * @ThreadSafe
 * @author dzh
 * @date Apr 24, 2014 5:18:25 PM
 * @since 1.0
 */
public class FlowConfigImpl implements FlowConfig {

	private String id;

	private Class<? extends Flow> clazz;

	public FlowConfigImpl(String id) {
		this(id, null);
	}

	public FlowConfigImpl(String id, Class<? extends Flow> clazz) {
		this.id = id;
		this.clazz = clazz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowConfig#getID()
	 */
	public String getID() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowConfig#getClazz()
	 */
	public Class<? extends Flow> getClazz() {
		return clazz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowConfig#getNodeConfig()
	 */
	public Collection<NodeConfig> getNodeConfig() {
		return Collections.unmodifiableCollection(nodes.values());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowConfig#getNodeConfig(java.lang.String)
	 */
	public NodeConfig getNodeConfig(String nodeID) {
		return nodes.get(nodeID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowConfig#dispose()
	 */
	public void dispose() {
		nodes.clear();
	}

	private Map<String, NodeConfig> nodes = Collections
			.synchronizedMap(new HashMap<String, NodeConfig>());

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowConfig#addNodeConfig(jflow.core.node.NodeConfig)
	 */
	public void addNodeConfig(NodeConfig nc) {
		if (nc != null)
			nodes.put(nc.getID(), nc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowConfig#removeNodeConfig(jflow.core.node.NodeConfig)
	 */
	public void removeNodeConfig(NodeConfig nc) {
		if (nc != null)
			nodes.remove(nc.getID());
	}

}
