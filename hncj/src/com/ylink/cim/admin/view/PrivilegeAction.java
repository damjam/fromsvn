package com.ylink.cim.admin.view;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.service.PrivilegeService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.etc.Assert;
import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class PrivilegeAction extends BaseAction implements
		ModelDriven<Privilege> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private PrivilegeDao privilegeDao;

	public String queryPopUpPrivilege() throws Exception {
		Paginater paginater = this.privilegeDao.getPrivilegePageList(model,
				getPager(request));
		request.setAttribute(Paginater.PAGINATER, paginater);
		request.setAttribute("privilegeList", paginater.getList());
		String msg = MsgUtils.r("Ȩ�޹����ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "popUp";
	}

	// Ȩ�޹����ѯ
	public String listPrivs() throws Exception {
		Paginater paginater = privilegeDao.findPrivs(model, getPager(request));
		setHasSubPri(paginater);
		saveQueryResult(request, paginater);
		String msg = MsgUtils.r("Ȩ�޹����ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "toListPage";
		// "/pages/admin/privilege/privList.jsp");
	}

	private void setHasSubPri(Paginater paginater) {
		for (int i = 0; i < paginater.getList().size(); i++) {
			Privilege privilege = (Privilege) paginater.getList().get(i);
			boolean hasSubPris = privilegeDao
					.hasSubPris(privilege.getLimitId());
			if (hasSubPris) {
				privilege.setHasSubPris(Symbol.YES);
			}
		}
	}

	// �����Ȩ��
	public String toEditPrivilege() throws Exception {
		if (StringUtils.isNotEmpty(model.getLimitId())) {
			// Privilege privilege = privilegeDao.findById(Privilege.class,
			// model.getLimitId());
			// BeanUtils.copyProperties(model, privilege);
		}
		return "toAddPage";
		// "/pages/admin/privilege/privEdit.jsp";
	}

	// �����Ȩ��
	public String editPrivilege() throws Exception {
		try {
			Privilege p = privilegeDao.findById(Privilege.class,
					model.getLimitId());
			Assert.isNull(p, "Ȩ��ID�Ѵ���");
			Integer menuOrder = privilegeDao.getNextIndex(model.getParent());
			model.setMenuOrder(menuOrder);
			model.setIfAudit(Symbol.NO);
			privilegeService.savePrivilege(model);
			setResult(true, "�����ɹ�", request);
			String msg = MsgUtils
					.r("�����Ȩ�޳ɹ�,�������Ϊ��{?}", FeildUtils.toString(p));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��:" + e.getMessage(), request);
			String msg = MsgUtils.r("�����Ȩ��ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
		}
		return toEditPrivilege();
	}

	// ɾ����Ȩ��
	public String delPri() throws Exception {
		try {
			privilegeService.delete(model.getLimitId());
			setResult(true, "�����ɹ�", request);
			String msg = MsgUtils.r("ɾ����Ȩ�޳ɹ�,ɾ��������Ϊ��{?}",
					FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "����ʧ��:" + e.getMessage(), request);
			// e.printStackTrace();
			String msg = MsgUtils.r("ɾ����Ȩ��ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
		}
		return listPrivs();
	}

	private Privilege model = new Privilege();

	@Override
	public Privilege getModel() {
		return model;
	}

}
