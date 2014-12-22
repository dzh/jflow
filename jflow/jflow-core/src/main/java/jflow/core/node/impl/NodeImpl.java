/**
 * 
 */
package jflow.core.node.impl;

import jflow.core.node.Node;
import jflow.core.node.NodeChain;
import jflow.core.node.NodeConfig;

/**
 * @author dzh
 * @param <V>
 * @date Apr 25, 2014 11:24:54 AM
 * @since 1.0
 */
public class NodeImpl<K, V> implements Node<K, V> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#init(jflow.core.node.NodeConfig)
	 */
	public void init(NodeConfig config) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#run(jflow.core.node.NodeChain,
	 * java.lang.Object)
	 */
	public V run(NodeChain chain, K properties) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#finish()
	 */
	public void finish() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#getConfig()
	 */
	public NodeConfig getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}
