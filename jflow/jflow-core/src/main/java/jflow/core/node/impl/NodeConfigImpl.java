/**
 * 
 */
package jflow.core.node.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jflow.core.node.Node;
import jflow.core.node.NodeConfig;
import jflow.core.node.NodeScope;

/**
 * @ThreadSafe
 * @author dzh
 * @date Apr 24, 2014 11:05:46 AM
 * @since 1.0
 */
public class NodeConfigImpl implements NodeConfig {

	private String id;

	private String name;

	private NodeScope scope;

	private Class<? extends Node<?, ?>> clazz;

	public NodeConfigImpl(String id, String name,
			Class<? extends Node<?, ?>> clazz, NodeScope scope) {
		this.id = id;
		this.name = name;
		this.scope = scope;
		this.clazz = clazz;
	}

	public NodeConfigImpl(String id, String name,
			Class<? extends Node<?, ?>> clazz) {
		this(id, name, clazz, NodeScope.Flow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getID()
	 */
	public String getID() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getScope()
	 */
	public NodeScope getScope() {
		return scope;
	}

	private Map<Object, Object> properties = Collections
			.synchronizedMap(new HashMap<Object, Object>());

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getConfig(java.lang.Object)
	 */
	public Object getConfig(Object key) {
		return properties.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getConfig(java.lang.Object,
	 * java.lang.Object)
	 */
	public Object getConfig(Object key, Object defVal) {
		Object val = properties.get(key);
		return val == null ? defVal : val;
	}

	private ClassLoader _cl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#setClassLoader(java.lang.ClassLoader)
	 */
	public void setClassLoader(ClassLoader cl) {
		this._cl = cl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getClassLoader()
	 */
	public ClassLoader getClassLoader() {
		return _cl;
	}

	@Override
	public String toString() {
		return "Node, id=" + getID() + ", name=" + getName() + ", scope="
				+ getScope().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#putConfig(java.lang.Object,
	 * java.lang.Object)
	 */
	public void putConfig(Object key, Object val) {
		properties.put(key, val);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#removeConfig(java.lang.Object)
	 */
	public void removeConfig(Object key) {
		properties.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getClazz()
	 */
	public Class<? extends Node<?, ?>> getClazz() {
		return this.clazz;
	}

	private ConcurrentMap<String, Node<?, ?>> nodes = new ConcurrentHashMap<String, Node<?, ?>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#getNode(java.lang.String)
	 */
	public Node<?, ?> getNode(String scope) {
		return nodes.get(scope);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#fnhNode(java.lang.String)
	 */
	public void fnhNode(String scope) {
		Node<?, ?> node = getNode(scope);
		if (node != null) {
			node.finish();
			removeNode(scope);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#putNode(java.lang.String,
	 * jflow.core.node.Node)
	 */
	public void putNode(String scope, Node<?, ?> node) {
		nodes.putIfAbsent(scope, node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeConfig#removeNode(java.lang.String)
	 */
	public void removeNode(String scope) {
		nodes.remove(scope);
	}

}
