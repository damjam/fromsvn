package flink.web.tag;

public class DTreeObj {

	private String nodeId;

	private String nodePid;

	private String nodeLabel;

	private String nodeValue;

	private String nodeName;

	private String nodeUrl;

	private String nodeTarget;

	private String nodeTitle;

	private String nodeIcon;

	private boolean nodeIconOpen;

	private boolean open;

	public String getNodeIcon() {
		return nodeIcon;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getNodeLabel() {
		return nodeLabel;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getNodePid() {
		return nodePid;
	}

	public String getNodeTarget() {
		return nodeTarget;
	}

	public String getNodeTitle() {
		return nodeTitle;
	}

	public String getNodeUrl() {
		return nodeUrl;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public boolean isNodeIconOpen() {
		return nodeIconOpen;
	}

	public boolean isOpen() {
		return open;
	}

	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
	}

	public void setNodeIconOpen(boolean nodeIconOpen) {
		this.nodeIconOpen = nodeIconOpen;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setNodePid(String nodePid) {
		this.nodePid = nodePid;
	}

	public void setNodeTarget(String nodeTarget) {
		this.nodeTarget = nodeTarget;
	}

	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
