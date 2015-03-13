package com.ylink.cim.admin.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.dao.LimitGroupDao;
import com.ylink.cim.admin.dao.LimitGroupInfoDao;
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
import com.ylink.cim.admin.service.PrivilegeService;
import com.ylink.cim.admin.service.RoleInfoService;
import com.ylink.cim.admin.service.SysDictService;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.consant.Constants;
import flink.util.ExceptionUtils;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;
import flink.web.tag.DTreeObj;
/**
 * 权限分组管理
 *
 */
public class LimitGroupInfoAction extends BaseDispatchAction{
	
	private LimitGroupInfoService limitGroupInfoService=(LimitGroupInfoService)getService("limitGroupInfoService");
	private LimitGroupService limitGroupService=(LimitGroupService)getService("limitGroupService");
	private PrivilegeService privilegeService=(PrivilegeService)getService("privilegeService");
	private SysDictService  sysDictService=(SysDictService)getService("sysDictService");
	private LimitGroupDao limitGroupDao = (LimitGroupDao)getService("limitGroupDao");
	private LimitGroupInfoDao limitGroupInfoDao = (LimitGroupInfoDao)getService("limitGroupInfoDao");
	private RoleInfoService roleInfoService=(RoleInfoService)getService("roleInfoService");
	private IdFactoryService idFactoryService = (IdFactoryService)getService("idFactoryService");
	public ActionForward listLimitGroupInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			LimitGroupInfoActionForm limitGroupInfoActionForm=(LimitGroupInfoActionForm)form;
			
			LimitGroupInfo limitGroupInfo = this.getLimitGroupInfoBy(limitGroupInfoActionForm);
			Paginater paginater = this.limitGroupInfoService.getLimitGroupInfoPageList(limitGroupInfo
					, super.getPager(request));
			
			saveQueryResult(request, paginater);
			String msg = LogUtils.r("权限分组管理查询成功");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		}catch(Exception e){
			String msg = LogUtils.r("权限分组管理查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			throw new Exception(e);
		}
		
		return mapping.findForward(ActionConstant.TO_LIST_PAGE);
	}
	
	
	public LimitGroupInfo getLimitGroupInfoBy(LimitGroupInfoActionForm limitGroupInfoActionForm)
			throws Exception{
		
		LimitGroupInfo limitGroupInfo=new LimitGroupInfo();
		BeanUtils.copyProperties(limitGroupInfo, limitGroupInfoActionForm);
		
		return limitGroupInfo;
	
	};
	
	public ActionForward deleteLimitGroupInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			LimitGroupInfoActionForm limitGroupInfoActionForm=(LimitGroupInfoActionForm)form;
			List<RoleInfo> list=roleInfoService.queryRoleInfoByLimitGroupId(limitGroupInfoActionForm.getLimitGroupId());
			if(list!=null && list.size()>0){
				setResult(false, "删除失败,失败原因:该权限组已被某一角色使用，不能删除！", request);
				limitGroupInfoActionForm.setLimitGroupId(null);
				String msg = LogUtils.r("删除权限失败,失败原因:{?}", "该权限组已被某一角色使用，不能删除！");
				super.logError(request, UserLogType.DELETE.getValue(), msg);
				return this.listLimitGroupInfo(mapping, form, request, response);
			}else{
			this.limitGroupInfoService.deleteLimitGroupInfo(limitGroupInfoActionForm.getLimitGroupId());
				limitGroupInfoActionForm.setLimitGroupId(null);
				String msg = LogUtils.r("删除权限组成功，删除的权限组id为:{?}"+limitGroupInfoActionForm.getLimitGroupId());
				super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			}
		}catch(Exception e){
			setResult(false, "删除失败,失败原因:"+e.getMessage(), request);
			String msg = LogUtils.r("删除权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
		}
		return this.listLimitGroupInfo(mapping, form, request, response);
	}
	
	public ActionForward toAddPage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			this.initUserTypeCollections(request);
			this.initPrivilegeTree(request);

			return mapping.findForward(ActionConstant.TO_ADD_PAGE);
		}catch (Exception e) {
			setResult(false, ActionMessageConstant.OPER_FAIL_NO_LIMIT_GROUP, request);
			
			return this.listLimitGroupInfo(mapping, form, request, response);
		}
	}
	
	public void initUserTypeCollections(HttpServletRequest request) throws Exception{
		
		List<SysDict> list = this.limitGroupInfoService.getSysDictNoLimitGroup();
		 
		if(null==list || list.size()==0){
			throw new Exception("没用需要创建的权限组");
		}
		
		saveQueryResult(request, list, "userTypeCollections");
		  
	}
	
	public void initPrivilegeTree(HttpServletRequest request) throws Exception{
		
		//初始化权限树
		  List<Privilege> privilegeList = this.privilegeService.getPrivilegeList(new Privilege());
		  List<PrivilegeTreeNode> list = privilegeService.getRoleTree();
		  saveQueryResult(request, list);
		  List<DTreeObj> dtreeLs=new ArrayList<DTreeObj>();
		  convertToDtree(dtreeLs,privilegeList);
		  
		  saveQueryResult(request, dtreeLs,"dTree");
		  
	}

	public void initPrivilegeSelect(HttpServletRequest request,String limitGroupId) throws Exception{
		
		List<LimitGroup> list = this.limitGroupService.getByLimitGroupId(limitGroupId);
		if(null==list || list.size()==0){
			return;
		}
		
		StringBuffer sb=new StringBuffer();
		for(LimitGroup limitGroup:list){
			sb.append("$").append(limitGroup.getId().getLimitId());
		}
		
		StringBuffer inititSelect = sb.deleteCharAt(0);
		
		request.setAttribute("initCheck", inititSelect.toString());
	}

	private void convertToDtree(List<DTreeObj> dtreeLs,
			List<Privilege> privilegeList) {
		
		if(null==privilegeList){
			return;
		}
		
		for(Privilege p :privilegeList){
			
			DTreeObj dTreeObj=new DTreeObj();
			dTreeObj.setNodeId(p.getLimitId());;
			dTreeObj.setNodePid(p.getParent());
			dTreeObj.setNodeLabel(p.getLimitName());
			dTreeObj.setNodeName("limitIds");
			dTreeObj.setNodeValue(p.getLimitId());
			dtreeLs.add(dTreeObj);
		}
		
	}


	public ActionForward addLimitGroupInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
		
			LimitGroupInfoActionForm limitGroupInfoActionForm=(LimitGroupInfoActionForm)form;
			
			if(ArrayUtils.isEmpty(limitGroupInfoActionForm.getLimitIds())){
				setResult(false, ActionMessageConstant.OPER_FAIL_NO_LIMIT, request);
				return mapping.findForward(ActionConstant.TO_ADD_PAGE);
			}
			
			LimitGroupInfo limitGroupInfo = this.getLimitGroupInfoBy(limitGroupInfoActionForm);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("limitGroupName", limitGroupInfoActionForm.getLimitGroupName());
			Long count = limitGroupDao.findCountByParam(LimitGroupInfo.class, params, null, null);
			if (count > 0) {
				setResult(false, LogUtils.r("权限组名称[{?}]已存在", limitGroupInfoActionForm.getLimitGroupName()), request);
				return this.toAddPage(mapping, limitGroupInfoActionForm, request, response);
			}
			limitGroupInfo.setLimitGroupId(idFactoryService.generateId(Constants.LIMIT_GROUP_INFO_ID));
			this.limitGroupInfoService.saveLimitGroup(limitGroupInfo);
			
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			this.clearForm(limitGroupInfoActionForm);
			String msg = LogUtils.r("添加权限组成功,添加内容为：{?}",FeildUtils.toString(limitGroupInfo));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return this.listLimitGroupInfo(mapping, limitGroupInfoActionForm, request, response);
		}catch (Exception e) {
			String msg = LogUtils.r("添加权限组失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			ExceptionUtils.logException(LimitGroupInfoAction.class, e.getMessage());
			throw e;
		}
		
	}

	public void clearForm(LimitGroupInfoActionForm limitGroupInfoActionForm){
			limitGroupInfoActionForm.setLimitGroupId(null);
			limitGroupInfoActionForm.setLimitGroupName(null);
	}
	
	public ActionForward toUpdatePage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
			LimitGroupInfoActionForm limitGroupInfoActionForm=(LimitGroupInfoActionForm)form;
			String limitGroupId = limitGroupInfoActionForm.getLimitGroupId();
			LimitGroupInfo limitGroupInfo = this.limitGroupInfoService.getLimitGroupInfoById(limitGroupInfoActionForm.getLimitGroupId());
			limitGroupInfoActionForm.setLimitGroupId(limitGroupInfo.getLimitGroupId());
			limitGroupInfoActionForm.setLimitGroupName(limitGroupInfo.getLimitGroupName());
			limitGroupInfoActionForm.setUserType(limitGroupInfo.getUserType());
			
			SysDictId id=new SysDictId();
			id.setDictType(SysDictType.UserType.getValue());
			id.setDictValue(limitGroupInfo.getUserType());
			SysDict sysDict = this.sysDictService.getSysDict(id);
			
			limitGroupInfoActionForm.setUserTypeName(sysDict.getDictName());
			
			List<SysDict> userTypeCollections=new ArrayList<SysDict>();
			userTypeCollections.add(sysDict);
			saveQueryResult(request, userTypeCollections , "userTypeCollections");
			
			this.initPrivilegeTree(request);
			this.initPrivilegeSelect(request, limitGroupInfoActionForm.getLimitGroupId());
			LimitGroupInfo gr = (LimitGroupInfo) limitGroupInfoDao.findById(limitGroupId);
			
			List<LimitGroup> lgList = limitGroupDao.getByLimitGroupId(limitGroupId);
			request.setAttribute("groupPrivilegeList", lgList);
			request.setAttribute("element", gr);
			return mapping.findForward(ActionConstant.TO_UPDATE_PAGE);
	}
	
	public ActionForward updateLimitGroupInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			if (!isValidKey(request)) {
				return this.toUpdatePage(mapping, form, request, response);
			}
			LimitGroupInfoActionForm limitGroupInfoActionForm=(LimitGroupInfoActionForm)form;
			LimitGroupInfo limitGroupInfo = this.getLimitGroupInfoBy(limitGroupInfoActionForm);
			
			if(null==limitGroupInfoActionForm.getLimitIds() || limitGroupInfoActionForm.getLimitIds().length==0){
				setResult(false, ActionMessageConstant.OPER_FAIL_NO_LIMIT, request);
				return this.toUpdatePage(mapping, limitGroupInfoActionForm, request, response);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("limitGroupName", limitGroupInfoActionForm.getLimitGroupName());
			Long count = limitGroupDao.findCountByParam(LimitGroupInfo.class, params, "limitGroupId", limitGroupInfoActionForm.getLimitGroupId());
			if (count > 0) {
				setResult(false, LogUtils.r("权限组名称[{?}]已存在", limitGroupInfoActionForm.getLimitGroupName()), request);
				return this.toUpdatePage(mapping, limitGroupInfoActionForm, request, response);
			}
			this.limitGroupInfoService.updateLimitGroupInfo(limitGroupInfo);
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("修改权限组成功，修改的权限组内容为:{?}",FeildUtils.toString(limitGroupInfo));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return  this.toUpdatePage(mapping, limitGroupInfoActionForm, request, response);
		}catch (Exception e) {
			setResult(false, "修改失败,失败原因:"+e.getMessage(), request);
			String msg = LogUtils.r("修改权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			ExceptionUtils.logException(LimitGroupInfoAction.class, e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
