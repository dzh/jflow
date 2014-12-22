/**
 * 
 */
package jflow.core.xml;

/**
 * @author dzh
 * @date Apr 24, 2014 10:07:07 AM
 * @since 1.0
 */
public interface FlowXml {
	String E_flows = "flows";
	String E_nodes = "nodes";
	String E_node = "node";
	String E_properties = "properties";
	String E_property = "property";
	String E_flow = "flow";
	String E_ref_node = "ref_node";

	String A_id = "id";
	String A_name = "name";
	String A_clazz = "clazz";
	String A_scope = "scope";
	String A_key = "key";
	String A_value = "value";
}
