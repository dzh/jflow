/**
 * 
 */
package jflow.core.node;

/**
 * @author dzh
 * @date Apr 22, 2014 10:47:03 AM
 * @since 1.0
 */
public interface Node<K, V> {

	void init(NodeConfig config);

	NodeConfig getConfig();

	V run(NodeChain chain, K properties);

	/**
	 * This node finish work,dispose its' resources
	 */
	void finish();

}
