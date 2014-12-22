/**
 * 
 */
package jflow.core.node;

/**
 * @author dzh
 * @date Apr 25, 2014 1:51:25 PM
 * @since 1.0
 */
public abstract class AbstractNode<K, V> implements Node<K, V> {

	private NodeConfig config;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#init(jflow.core.node.NodeConfig)
	 */
	public void init(NodeConfig config) {
		this.config = config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#getConfig()
	 */
	public NodeConfig getConfig() {
		return config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#finish()
	 */
	public void finish() {
		config = null;
	}

}
