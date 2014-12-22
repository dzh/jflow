/**
 * 
 */
package jflow.example.analysis.lack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jflow.core.FlowEvent;
import jflow.core.FlowListener;
import jflow.core.util.FlowExecutorException;
import jflow.core.util.FlowExecutors;
import jflow.core.util.FlowParseException;
import junit.framework.Assert;

/**
 * @author dzh
 * @date Apr 25, 2014 1:58:50 PM
 * @since 1.0
 */
public class ExampleMain {

	/**
	 * @param args
	 * @throws FlowParseException
	 * @throws FlowExecutorException
	 */
	public static void main(String[] args) {
		try {
			File flows = new File(ExampleMain.class.getResource("flows.xml")
					.getFile());
			File nodes = new File(ExampleMain.class.getResource("nodes.xml")
					.getFile());
			final FlowExecutors fe = FlowExecutors.getInstance();
			fe.loadConfig(nodes, flows);
			fe.<Map<String, String>> submitFlow("StationLackFlow",
					new HashMap<String, String>(), "HardwareAnalysis",
					new FlowListener() {
						public void flowChanged(FlowEvent e) {
							if (e.getEvent() == FlowEvent.Event.ChainFinish) {
								if (e.getChain().parent() == null) {
									e.getChain().getFlow().finish();
									fe.finish();
								}
							}
						}
					});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	static class FlowCallback implements FlowListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see jflow.core.FlowListener#flowChanged(jflow.core.FlowEvent)
		 */
		public void flowChanged(FlowEvent e) {
			if (e.getEvent() == FlowEvent.Event.ChainFinish) {
				if (e.getChain().parent() == null) {
					e.getChain().getFlow().finish();
				}
			}
		}

	}

	public void testPath() {
		File f = new File(ExampleMain.class.getResource("flows.xml").getFile());
		Assert.assertEquals(true, f.exists());
		f = new File(ExampleMain.class.getResource("nodes.xml").getFile());
		Assert.assertEquals(true, f.exists());
		// if (f.exists()) {
		// System.out.println(f.getAbsolutePath());
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.FlowListener#flowChanged(jflow.core.FlowEvent)
	 */
	public void flowChanged(FlowEvent e) {
		// TODO Auto-generated method stub

	}

}
