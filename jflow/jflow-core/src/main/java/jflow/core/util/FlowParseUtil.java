/**
 * 
 */
package jflow.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jflow.core.Flow;
import jflow.core.FlowConfig;
import jflow.core.impl.FlowConfigImpl;
import jflow.core.impl.FlowImpl;
import jflow.core.node.Node;
import jflow.core.node.NodeConfig;
import jflow.core.node.NodeScope;
import jflow.core.node.impl.NodeConfigImpl;
import jflow.core.xml.FlowXml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dzh
 * @date Apr 23, 2014 4:36:36 PM
 * @since 1.0
 */
public class FlowParseUtil implements FlowXml {

	static final Logger LOG = LoggerFactory.getLogger(FlowParseUtil.class);

	public static final NodeConfig createNodeConfig(Element node,
			ClassLoader ncl) throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Class<? extends Node<?, ?>> clazz = (Class<? extends Node<?, ?>>) ncl
				.loadClass(node.attributeValue(A_clazz));
		// TODO annotation
		String scope = node.attributeValue(A_scope, NodeScope.Flow.name());
		NodeConfig nc = new NodeConfigImpl(node.attributeValue(A_id, ""),
				node.attributeValue(A_name, ""), clazz,
				NodeScope.getScope(scope));
		nc.setClassLoader(ncl); // TODO not need

		Element properties = node.element(E_properties);
		if (properties != null) {
			@SuppressWarnings("unchecked")
			List<Element> props = properties.elements(E_property);
			for (Element prop : props) {
				nc.putConfig(
						getNodeConfigPropertyKey(NodeConfig.KeyPrefix_node,
								prop.attributeValue(A_key, "")), prop
								.attributeValue(A_value, ""));
			}
		}
		return nc;
	}

	public static final List<NodeConfig> parseNodesXml(File xml, ClassLoader ncl)
			throws FlowParseException {
		SAXReader reader = new SAXReader();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(xml);
			Document doc = reader.read(xml);
			@SuppressWarnings("unchecked")
			List<Element> nodes = doc.getRootElement().elements(E_node);
			List<NodeConfig> list = new ArrayList<NodeConfig>(nodes.size());
			NodeConfig nc = null;
			for (Element node : nodes) {
				nc = createNodeConfig(node, ncl);
				// TODO validate nc
				if ("".equals(nc.getID())) {
					LOG.error("Node {} config error!", nc.toString());
					continue;
				}
				list.add(nc);
			}

			doc.clearContent();
			return list;
		} catch (Exception e) {
			throw new FlowParseException("Read nodes file "
					+ xml.getAbsolutePath() + " errror! Exception: "
					+ e.getLocalizedMessage());
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
			reader.resetHandlers();
		}
	}

	public static final List<FlowConfig> parseFlowsXml(File xml,
			ClassLoader fcl, List<NodeConfig> nodes) throws FlowParseException {
		SAXReader reader = new SAXReader();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(xml);
			Document doc = reader.read(xml);
			@SuppressWarnings("unchecked")
			List<Element> flows = doc.getRootElement().elements(E_flow);
			List<FlowConfig> list = new ArrayList<FlowConfig>(flows.size());
			FlowConfig fc = null;
			for (Element flow : flows) {
				fc = createFlowConfig(flow, fcl, nodes);
				// TODO validate fc
				if ("".equals(fc.getID())) {
					LOG.error("Flow {} config error!", fc.toString());
					continue;
				}
				list.add(fc);
			}

			doc.clearContent();
			return list;
		} catch (Exception e) {
			throw new FlowParseException("Read nodes file "
					+ xml.getAbsolutePath() + " errror! Exception: "
					+ e.getLocalizedMessage());
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
			reader.resetHandlers();
		}
	}

	/**
	 * @param node
	 * @param fcl
	 * @param nodes
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static FlowConfig createFlowConfig(Element flow, ClassLoader fcl,
			List<NodeConfig> nodes) throws ClassNotFoundException {
		fcl = fcl == null ? Thread.currentThread().getContextClassLoader()
				: fcl;
		@SuppressWarnings("unchecked")
		Class<? extends Flow> clazz = (Class<? extends Flow>) fcl
				.loadClass(flow.attributeValue(A_clazz,
						FlowImpl.class.getName()));
		FlowConfig fc = new FlowConfigImpl(flow.attributeValue(A_id, ""), clazz);
		@SuppressWarnings("unchecked")
		List<Element> ref_nodes = flow.elements(E_ref_node);

		for (Element ref : ref_nodes) {
			NodeConfig nc = getNodeConfig(ref.attributeValue(A_id, ""), nodes);
			if (nc != null) {
				fc.addNodeConfig(nc);
				Element props = ref.element(E_properties);
				if (props != null) {
					@SuppressWarnings("unchecked")
					List<Element> ps = props.elements(E_property);
					for (Element p : ps) {
						nc.putConfig(
								getNodeConfigPropertyKey(
										NodeConfig.KeyPrefix_flow,
										p.attributeValue(A_key, ""), fc.getID()),
								p.attributeValue(A_value, ""));
					}
				}
			}
		}
		return fc;
	}

	public static String getNodeConfigPropertyKey(String prefix, String key,
			String... ids) {
		if (prefix.equals(NodeConfig.KeyPrefix_node)) {
			return prefix + "." + key;
		}
		if (prefix.equals(NodeConfig.KeyPrefix_flow)) {
			return prefix + "." + ids[0] + "." + key;
		}
		return key;
	}

	public static final NodeConfig getNodeConfig(String id,
			List<NodeConfig> nodes) {
		for (NodeConfig nc : nodes) {
			if (nc.getID().equals(id))
				return nc;
		}
		return null;
	}

}
