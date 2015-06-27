package com.ylink.cim.admin.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.LimitGroupDao;
import com.ylink.cim.admin.dao.LimitGroupInfoDao;
import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.LimitGroup;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;
import com.ylink.cim.admin.domain.RoleInfo;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.domain.SysDictId;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.admin.service.LimitGroupInfoService;
import com.ylink.cim.admin.service.LimitGroupService;
import com.ylink.cim.admin.service.RoleInfoService;
import com.ylink.cim.admin.service.SysDictService;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.consant.Constants;
import flink.util.ExceptionUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;
import flink.web.tag.DTreeObj;

/**
 * 权限分组管理
 * 
 */
@Scope("prototype")
@Component
public class LimitGroupInfoAction extends BaseAction implements
		ModelDriven<LimitGroupInfo> {

	/**
	 * 
	 */

	private static final long serialVersionUID = -3917589214310429849L;
	@Autowired
	private LimitGroupInfoService limitGroupInfoService;
	@Autowired
	private LimitGroupService limitGroupService;
	@Autowired
	private PrivilegeDao privilegeDao;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private LimitGroupDao limitGroupDao;
	@Autowired
	private LimitGroupInfoDao limitGroupInfoDao;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private IdFactoryService idFactoryService;

	public String listLimitGroupInfo() throws Exception {
		try {

			Paginater paginater = this.limitGroupInfoService
					.getLimitGroupInfoPageList(info, super.getPager(request));
			saveQueryResult(request, paginater);
			String msg = MsgUtils.r("权限分组管理查询成功");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (Exception e) {
			String msg = MsgUtils.r("权限分组管理查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			throw new Exception(e);
		}

		return ActionConstant.TO_LIST_PAGE;
	}

	public String deleteLimitGroupInfo() throws Exception {
		try {
			List<RoleInfo> list = roleInfoService
					.queryRoleInfoByLimitGroupId(info.getLimitGroupId());
			if (list != null && list.size() > 0) {
				setResult(false, "删除失败,失败原因:该权限组已被某一角色使用，不能删除！", request);
				String msg = MsgUtils
						.r("删除权限失败,失败原因:{?}", "该权限组已被某一角色使用，不能删除！");
				super.logError(request, UserLogType.DELETE.getValue(), msg);
				return this.listLimitGroupInfo();
			} else {
				this.limitGroupInfoService.deleteLimitGroupInfo(info
						.getLimitGroupId());
				info.setLimitGroupId(null);
				String msg = MsgUtils.r("删除权限组成功，删除的权限组id为:{?}"
						+ info.getLimitGroupId());
				super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			}
		} catch (Exception e) {
			setResult(false, "删除失败,失败原因:" + e.getMessage(), request);
			String msg = MsgUtils.r("删除权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
		}
		return this.listLimitGroupInfo();
	}

	public String toAddPage() throws Exception {

		try {
			this.initUserTypeCollections(request);
			this.initPrivilegeTree(request);

			return ActionConstant.TO_ADD_PAGE;
		} catch (Exception e) {
			setResult(false, ActionMessageConstant.OPER_FAIL_NO_LIMIT_GROUP,
					request);

			return this.listLimitGroupInfo();
		}
	}

	public void initUserTypeCollections(HttpServletRequest request)
			throws Exception {

		List<SysDict> list = this.limitGroupInfoService
				.getSysDictNoLimitGroup();

		if (null == list || list.size() == 0) {
			throw new Exception("没用需要创建的权限组");
		}

		saveQueryResult(request, list, "userTypeCollections");

	}

	public void initPrivilegeTree(HttpServletRequest request) throws Exception {

		// 初始化权限树
		List<Privilege> privilegeList = this.privilegeDao
				.getPrivilegeList(new Privilege());
		List<PrivilegeTreeNode> list = privilegeDao.getRoleTree();
		saveQueryResult(request, list);
		List<DTreeObj> dtreeLs = new ArrayList<DTreeObj>();
		convertToDtree(dtreeLs, privilegeList);

		saveQueryResult(request, dtreeLs, "dTree");

	}

	public void initPrivilegeSelect(HttpServletRequest request,
			String limitGroupId) throws Exception {

		List<LimitGroup> list = this.limitGroupService
				.getByLimitGroupId(limitGroupId);
		if (null == list || list.size() == 0) {
			return;
		}

		StringBuffer sb = new StringBuffer();
		for (LimitGroup limitGroup : list) {
			sb.append("$").append(limitGroup.getId().getLimitId());
		}

		StringBuffer inititSelect = sb.deleteCharAt(0);

		request.setAttribute("initCheck", inititSelect.toString());
	}

	private void convertToDtree(List<DTreeObj> dtreeLs,
			List<Privilege> privilegeList) {

		if (null == privilegeList) {
			return;
		}

		for (Privilege p : privilegeList) {

			DTreeObj dTreeObj = new DTreeObj();
			dTreeObj.setNodeId(p.getLimitId());
			;
			dTreeObj.setNodePid(p.getParent());
			dTreeObj.setNodeLabel(p.getLimitName());
			dTreeObj.setNodeName("limitIds");
			dTreeObj.setNodeValue(p.getLimitId());
			dtreeLs.add(dTreeObj);
		}

	}

	public String addLimitGroupInfo() throws Exception {

		try {

			if (ArrayUtils.isEmpty(info.getLimitIds())) {
				setResult(false, ActionMessageConstant.OPER_FAIL_NO_LIMIT,
						request);
				return ActionConstant.TO_ADD_PAGE;
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("limitGroupName", info.getLimitGroupName());
			Long count = limitGroupDao.findCountByParam(LimitGroupInfo.class,
					params, null, null);
			if (count > 0) {
				setResult(false,
						MsgUtils.r("权限组名称[{?}]已存在", info.getLimitGroupName()),
						request);
				return this.toAddPage();
			}
			info.setLimitGroupId(idFactoryService
					.generateId(Constants.LIMIT_GROUP_INFO_ID));
			this.limitGroupInfoService.saveLimitGroup(info);

			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("添加权限组成功,添加内容为：{?}",
					FeildUtils.toString(info));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return this.listLimitGroupInfo();
		} catch (Exception e) {
			String msg = MsgUtils.r("添加权限组失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			ExceptionUtils.logException(LimitGroupInfoAction.class,
					e.getMessage());
			throw e;
		}

	}

	public void clearForm(LimitGroupInfo info) {
		info.setLimitGroupId(null);
		info.setLimitGroupName(null);
	}

	public String toUpdatePage() throws Exception {
		String limitGroupId = info.getLimitGroupId();
		LimitGroupInfo limitGroupInfo = this.limitGroupInfoService
				.getLimitGroupInfoById(info.getLimitGroupId());
		info.setLimitGroupId(limitGroupInfo.getLimitGroupId());
		info.setLimitGroupName(limitGroupInfo.getLimitGroupName());
		info.setUserType(limitGroupInfo.getUserType());

		SysDictId id = new SysDictId();
		id.setDictType(SysDictType.UserType.getValue());
		id.setDictValue(limitGroupInfo.getUserType());
		SysDict sysDict = this.sysDictService.getSysDict(id);

		info.setUserTypeName(sysDict.getDictName());

		List<SysDict> userTypeCollections = new ArrayList<SysDict>();
		userTypeCollections.add(sysDict);
		saveQueryResult(request, userTypeCollections, "userTypeCollections");

		this.initPrivilegeTree(request);
		this.initPrivilegeSelect(request, info.getLimitGroupId());
		LimitGroupInfo gr = (LimitGroupInfo) limitGroupInfoDao
				.findById(limitGroupId);

		List<LimitGroup> lgList = limitGroupDao.getByLimitGroupId(limitGroupId);
		request.setAttribute("groupPrivilegeList", lgList);
		request.setAttribute("element", gr);
		return ActionConstant.TO_UPDATE_PAGE;
	}

	public String updateLimitGroupInfo() throws Exception {

		try {
			if (!isValidKey(request)) {
				return this.toUpdatePage();
			}

			if (null == info.getLimitIds() || info.getLimitIds().length == 0) {
				setResult(false, ActionMessageConstant.OPER_FAIL_NO_LIMIT,
						request);
				return this.toUpdatePage();
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("limitGroupName", info.getLimitGroupName());
			Long count = limitGroupDao.findCountByParam(LimitGroupInfo.class,
					params, "limitGroupId", info.getLimitGroupId());
			if (count > 0) {
				setResult(false,
						MsgUtils.r("权限组名称[{?}]已存在", info.getLimitGroupName()),
						request);
				return this.toUpdatePage();
			}
			this.limitGroupInfoService.updateLimitGroupInfo(info);
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("修改权限组成功，修改的权限组内容为:{?}",
					FeildUtils.toString(info));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return this.toUpdatePage();
		} catch (Exception e) {
			setResult(false, "修改失败,失败原因:" + e.getMessage(), request);
			String msg = MsgUtils.r("修改权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			ExceptionUtils.logException(LimitGroupInfoAction.class,
					e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public LimitGroupInfo getModel() {
		return info;
	}

	private LimitGroupInfo info = new LimitGroupInfo();

}
