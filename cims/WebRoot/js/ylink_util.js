var ClientBrower = {};
var ua = navigator.userAgent.toLowerCase();
var type;
(type = ua.match(/msie ([\d.]+)/)) ? ClientBrower.ie = type[1] :
(type = ua.match(/firefox\/([\d.]+)/)) ? ClientBrower.firefox = type[1] :
(type = ua.match(/chrome\/([\d.]+)/)) ? ClientBrower.chrome = type[1] :
(type = ua.match(/opera.([\d.]+)/)) ? ClientBrower.opera = type[1] :
(type = ua.match(/version\/([\d.]+).*safari/)) ? ClientBrower.safari = type[1] : 0;

function SystemConstant() {}
SystemConstant.style = new Array();
SystemConstant.style.display = new Array();
SystemConstant.style.display.none = "none";
SystemConstant.style.display.inherit = ClientBrower.ie?"":"inherit";

/**
 * 通过id获取表单对象。
 * @param {string} id 表单id
 * @return {Object} 表单对象
 */
function getFormById(id) {
	return document.getElementById(id);
}
/**
 * 把对象从布局中移除。
 * @param {string} id 对象id
 */
function removeFromLayout(id) {
	var ele = document.getElementById(id);
	ele.style.display=SystemConstant.style.display.none;
}
/**
 * 把对象加入布局。
 * @param {string} id 对象id
 */
function resumeToLayout(id) {
	var ele = document.getElementById(id);
  	ele.style.display=SystemConstant.style.display.inherit;
}

/**
 * <b>注意：</b>使用了CONTEXT_PATH，使用前需要引用/pages/common/sys.jsp。<br/>
 * 初始化一个三态树，默认大小是充满父框架。
 * @param {string} elementId 父框架ID
 * @param {string} rootElement 根节点值
 * @return {dhtmlXTreeObject} 构造好的树对象
 */
function initXTree(elementId, rootElement) {
	var tree = new dhtmlXTreeObject(elementId,"100%","100%",rootElement);
	tree.enableCheckBoxes(true);
	tree.enableThreeStateCheckboxes(true);
	tree.setImagePath(CONTEXT_PATH+"/js/tree/imgs/");
	return tree;
}
/**
 * 获取三态树中选中的节点。
 * 原生的获取方法可能在选择一个没有子树的根节点下一级项目时返回一个最后元素为空字符串的数组。
 * @param {dhtmlXTreeObject} tree 要获取节点的树
 * @return {Array} 包含选中节点的数组
 */
function getSelectTreeNode(tree){
	var acb = tree.getAllCheckedBranches();
	var ids = acb.split(tree.dlmtr);
	if (ids[ids.length-1] == "") {
		ids.pop();
	}
	return ids;
}