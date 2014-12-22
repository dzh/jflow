/**
 * 
 */
package jflow.core.node;

/**
 * <p>
 * function:
 * <li>node configuration</li>
 * <li>object scope store, use getNode and fnkNode.</li>
 * </p>
 * 
 * <p>
 * reading scope and config order:
 * <li>definition in file ,for example flow.properties</li>
 * <li>class annotation</li>
 * <li>default value</li>
 * </p>
 * 
 * @author dzh
 * @date Apr 22, 2014 12:55:05 PM
 * @since 1.0
 */
public interface NodeConfig {

	String getID();

	/**
	 * @return
	 */
	String getName();

	// NodeType getType();

	/**
	 * TODO how to decide scope
	 * 
	 * @return
	 */
	NodeScope getScope();

	Class<? extends Node<?, ?>> getClazz();

	/**
	 * node property defined by self
	 */
	String KeyPrefix_node = "node";

	/**
	 * node property defined by flow
	 */
	String KeyPrefix_flow = "flow";

	/**
	 * <p>
	 * get key's config steps:
	 * <li>flow key : flow.flowId.key</li>
	 * <li>node key : node.key</li>
	 * <li>key</li>
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	Object getConfig(Object key);

	Object getConfig(Object key, Object defVal);
	
	void putConfig(Object key, Object val);

	void removeConfig(Object key);

	void setClassLoader(ClassLoader cl);

	ClassLoader getClassLoader();

	void putNode(String scope, Node<?, ?> node);

	/**
	 * 
	 * @param scope
	 *            node's scope path,
	 * @return If not existed, return null
	 */
	Node<?, ?> getNode(String scope);

	void removeNode(String scope);

	/**
	 * 
	 * @param scope
	 *            node's scope path, if scope is null ,finish all nodes
	 */
	void fnhNode(String scope);
}
