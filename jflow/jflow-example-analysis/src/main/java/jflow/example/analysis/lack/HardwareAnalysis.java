/**
 * 
 */
package jflow.example.analysis.lack;

import java.util.Map;

import jflow.core.node.AbstractNode;
import jflow.core.node.NodeChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dzh
 * @date Apr 25, 2014 2:15:24 PM
 * @since 1.0
 */
public class HardwareAnalysis extends
		AbstractNode<Map<String, String>, Boolean> {
	static final Logger LOG = LoggerFactory.getLogger(HardwareAnalysis.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#run(jflow.core.node.NodeChain,
	 * java.lang.Object)
	 */
	public Boolean run(NodeChain chain, Map<String, String> properties) {
		LOG.info("HardwareAnalysis is running");
		properties.put("HardwareAnalysis", "正常");
		chain.getFlow().invoke(chain, properties, "MonitorAnalysis");
		LOG.info("HardwareAnalysis is done");
		return false;
	}

}
