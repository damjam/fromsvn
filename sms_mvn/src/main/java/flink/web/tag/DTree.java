package flink.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

public class DTree extends SimpleTagSupport {

	private String jsSrc = "/dtree/wtree.js";

	private String imgSrc = "/dtree/images/system/menu/";

	private String cssSrc = "/dtree/dtree.css";

	private String displayDivId;

	private String target = null;

	private boolean folderLinks = true;

	private boolean useSelection = true;

	private boolean useCookies = true;

	private boolean userLines = true;

	private boolean useIcons = true;

	private boolean useStatusText = false;

	private boolean closeSameLevel = false;

	private boolean inOrder = false;

	private boolean check = false;

	private String dataListName;

	private boolean buffer;

	private String initCheckDataName = "initCheck";

	/**
	 * 获取JS的路径
	 * 
	 * @return
	 */
	public String getJsSrc() {
		return jsSrc;
	}

	/**
	 * 设置JS的路径
	 * 
	 * @param jsSrc
	 */
	public void setJsSrc(String jsSrc) {
		this.jsSrc = jsSrc;
	}

	/**
	 * 获取图片路径
	 * 
	 * @return
	 */
	public String getImgSrc() {
		return imgSrc;
	}

	/**
	 * 设置图片路径
	 * 
	 * @param imgSrc
	 */
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	/**
	 * 获取树显示的DIV
	 * 
	 * @return
	 */
	public String getDisplayDivId() {
		return displayDivId;
	}

	/**
	 * 设置树显示的DIV
	 * 
	 * @param displayDivId
	 */
	public void setDisplayDivId(String displayDivId) {
		this.displayDivId = displayDivId;
	}

	/**
	 * 获取打开位置
	 * 
	 * @return
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 设置打开位置
	 * 
	 * @param target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 获取是否有初始化值
	 * 
	 * @return
	 */
	public boolean isUseSelection() {
		return useSelection;
	}

	/**
	 * 设置是否有初始化值
	 * 
	 * @param useSelection
	 */
	public void setUseSelection(boolean useSelection) {
		this.useSelection = useSelection;
	}

	/**
	 * 获取COOKIES
	 * 
	 * @return
	 */
	public boolean isUseCookies() {
		return useCookies;
	}

	/**
	 * 设置cookies
	 * 
	 * @param useCookies
	 */
	public void setUseCookies(boolean useCookies) {
		this.useCookies = useCookies;
	}

	/**
	 * 获取是否使用图片
	 * 
	 * @return
	 */
	public boolean isUseIcons() {
		return useIcons;
	}

	/**
	 * 设置是否使用图片
	 * 
	 * @param useIcons
	 */
	public void setUseIcons(boolean useIcons) {
		this.useIcons = useIcons;
	}

	/**
	 * 是否使用状态文本
	 * 
	 * @return
	 */
	public boolean isUseStatusText() {
		return useStatusText;
	}

	/**
	 * 设置是否使用静态文本
	 * 
	 * @param useStatusText
	 */
	public void setUseStatusText(boolean useStatusText) {
		this.useStatusText = useStatusText;
	}

	/**
	 * 获取是否关闭同一层
	 * 
	 * @return
	 */
	public boolean isCloseSameLevel() {
		return closeSameLevel;
	}

	/**
	 * 设置是否关闭同一层
	 * 
	 * @param closeSameLevel
	 */
	public void setCloseSameLevel(boolean closeSameLevel) {
		this.closeSameLevel = closeSameLevel;
	}

	/**
	 * 是否排序
	 * 
	 * @return
	 */
	public boolean isInOrder() {
		return inOrder;
	}

	/**
	 * 设置是否排序
	 * 
	 * @param inOrder
	 */
	public void setInOrder(boolean inOrder) {
		this.inOrder = inOrder;
	}

	/**
	 * 获取是否有复选框
	 * 
	 * @return
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * 设置是否有复选框
	 * 
	 * @param check
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * 获取是否有超链接
	 * 
	 * @return
	 */
	public boolean isFolderLinks() {
		return folderLinks;
	}

	/**
	 * 设置是否有超链接
	 * 
	 * @param folderLinks
	 */
	public void setFolderLinks(boolean folderLinks) {
		this.folderLinks = folderLinks;
	}

	/**
	 * 获取是否有下划线
	 * 
	 * @return
	 */
	public boolean isUserLines() {
		return userLines;
	}

	/**
	 * 设置是否有下划线
	 * 
	 * @param userLines
	 */
	public void setUserLines(boolean userLines) {
		this.userLines = userLines;
	}

	/**
	 * 获取树形结构数据存放request名称
	 * 
	 * @return
	 */
	public String getDataListName() {
		return dataListName;
	}

	/**
	 * 设置数据存放时的request名称
	 * 
	 * @param dataListName
	 */
	public void setDataListName(String dataListName) {
		this.dataListName = dataListName;
	}

	/**
	 * 获取是否出现图片缓存效果
	 * 
	 * @return
	 */
	public boolean isBuffer() {
		return buffer;
	}

	/**
	 * 设置是否出现图片缓存效果
	 * 
	 * @param buffer
	 */
	public void setBuffer(boolean buffer) {
		this.buffer = buffer;
	}

	/**
	 * 获取设置初始化节点ID的request存放名称
	 * 
	 * @return
	 */
	public String getInitCheckDataName() {
		return initCheckDataName;
	}

	/**
	 * 获取样式加载
	 * 
	 * @return
	 */
	public String getCssSrc() {
		return cssSrc;
	}

	/**
	 * 设置样式
	 * 
	 * @param cssSrc
	 */
	public void setCssSrc(String cssSrc) {
		this.cssSrc = cssSrc;
	}

	/**
	 * 设置初始化节点ID的request存放名称
	 * 
	 * @param initCheckDataName
	 */
	public void setInitCheckDataName(String initCheckDataName) {
		this.initCheckDataName = initCheckDataName;
	}

	@Override
	public void doTag() throws JspException, IOException {

		PageContext pageContext = (PageContext) super.getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		String contextPath = request.getContextPath();
		JspWriter out = pageContext.getOut();

		Object data = request.getAttribute(this.dataListName);
		if (null == data) {
			out.print("没有数据");
		} else {

			// 先缓存头像
			StringBuffer imageLoading = new StringBuffer();
			imageLoading.append("<script type='text/javascript'>");
			imageLoading.append(" document.getElementById('"
					+ this.getDisplayDivId() + "').innerHTML=LOAD_IMAGE;");
			imageLoading.append("</script>");
			out.print(imageLoading);

			// 样式加载
			StringBuffer styleLoading = new StringBuffer();
			styleLoading.append("<link rel='stylesheet' type='text/css' href="
					+ contextPath + this.getCssSrc() + "/>");

			StringBuffer sb = new StringBuffer();
			sb.append(" <script src='" + contextPath + this.getJsSrc()
					+ "' type='text/javascript' ></script> ");
			sb.append(" <script type='text/javascript'>");
			sb.append(" var d = new dTree('d','" + contextPath
					+ this.getImgSrc() + "');");
			initDtreeConfig(sb);
			initDtreeNode(sb, (List<DTreeObj>) data);
			createBufferFunction(sb, request);
			this.showDtree(sb);
			sb.append(" </script> ");

			out.print(sb);
		}

		super.doTag();
	}

	/**
	 * 初始化选择树节点
	 * 
	 * @param sb
	 */
	private void initDtree(StringBuffer sb, HttpServletRequest request) {

		String initCheckIds = (String) request
				.getAttribute(this.initCheckDataName);
		if (null == initCheckIds || initCheckIds.length() == 0) {
			return;
		}

		sb.append("d.initCheckBoxNodes('" + initCheckIds + "');d.openAll();");

	}

	private void createBufferFunction(StringBuffer sb,
			HttpServletRequest request) {

		sb.append("function buf(){");
		sb.append("document.getElementById('" + this.displayDivId
				+ "').innerHTML=d;");
		this.initDtree(sb, request);
		sb.append("}");

	}

	private void showDtree(StringBuffer sb) {
		if (buffer) {
			sb.append("setTimeout('buf()',3000);");
		} else {
			sb.append("buf();");
		}

		sb.append("d.closeAll();");
	}

	private void initDtreeNode(StringBuffer sb, List<DTreeObj> data) {

		for (int i = 0; i < data.size(); i++) {
			DTreeObj dTreeObj = data.get(i);
			sb.append(" d.add(");
			sb.append("'" + dTreeObj.getNodeId() + "',");
			sb.append("'" + dTreeObj.getNodePid() + "',");
			sb.append("'" + dTreeObj.getNodeLabel() + "',");
			sb.append("'" + dTreeObj.getNodeUrl() + "',");
			sb.append("'" + dTreeObj.getNodeLabel() + "',");
			sb.append("'" + dTreeObj.getNodeTarget() + "',");
			sb.append("'" + dTreeObj.getNodeName() + "',");
			sb.append("'" + dTreeObj.getNodeValue() + "',");
			sb.append("" + dTreeObj.getNodeIcon() + ",");
			sb.append("" + dTreeObj.isNodeIconOpen() + ",");
			sb.append("" + dTreeObj.isOpen() + "");
			sb.append(");");
		}
	}

	private void initDtreeConfig(StringBuffer sb) {

		if (!StringUtils.isEmpty(this.getTarget())) {
			sb.append(" d.config.target=" + this.getTarget() + ";");
		}

		if (!this.isFolderLinks()) {
			sb.append(" d.config.folderLinks= false ;");
		}

		if (!this.isUseSelection()) {
			sb.append(" d.config.useSelection = false ;");
		}

		if (!this.isUseCookies()) {
			sb.append(" d.config.useCookies = false ;");
		}

		if (!this.isUserLines()) {
			sb.append(" d.config.useLines = false ;");
		}

		if (!this.isUseIcons()) {
			sb.append(" d.config.useIcons = false ;");
		}

		if (this.isUseStatusText()) {
			sb.append(" d.config.useStatusText = true ;");
		}

		if (this.isCloseSameLevel()) {
		}
		{
			sb.append(" d.config.closeSameLevel = true ;");
		}

		if (this.isCheck()) {
			sb.append(" d.config.check = true ;");
		}
	}

}
