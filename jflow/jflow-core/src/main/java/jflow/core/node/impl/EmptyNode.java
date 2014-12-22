/**
 * 
 */
package jflow.core.node.impl;

import jflow.core.node.Node;
import jflow.core.node.NodeChain;
import jflow.core.node.NodeConfig;

/**
 * <p>
 * </p>
 * 
 * @author dzh
 * @date Apr 22, 2014 12:37:02 PM
 * @since 1.0
 */
@Deprecated
public class EmptyNode implements Node<Object, Object> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#init(jflow.core.node.NodeConfig)
	 */
	public void init(NodeConfig config) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#run(jflow.core.node.NodeChain,
	 * java.lang.Object)
	 */
	public Object run(NodeChain chain, Object properties) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#finish()
	 */
	public void finish() {
		// TODO Auto-generated method stub

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
