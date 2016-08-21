package com.ylink.cim.admin.view;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.PrivilegeResourceDao;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.service.PrivilegeResourceService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.etc.Assert;
import flink.etc.Symbol;
import flink.util.ExceptionUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class PrivilegeResourceAction extends BaseAction implements
		ModelDriven<PrivilegeResource> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5193680658557648162L;
	@Autowired
	private PrivilegeResourceService privilegeResourceService;
	@Autowired
	private PrivilegeResourceDao privilegeResourceDao;
	private PrivilegeResource model = new PrivilegeResource();

	public String doAdd() throws Exception {

		try {
			// privilegeResource.setId(Long.valueOf(IdFactoryHelper.getId(IdFactoryConstant.PRIVILEG_ERESOURCE_ID.getValue())));
			this.privilegeResourceService.savePrivilegeResource(model);
			setResult(true, "�����ɹ�", request);
			return ActionConstant.TO_ADD_PAGE;
		} catch (Exception e) {
			ExceptionUtils
					.logException(PrivilegeResource.class, e.getMessage());
			throw e;
		}

	}

	public String delete() throws Exception {
		try {
			PrivilegeResource p = privilegeResourceDao.findById(
					PrivilegeResource.class, model.getId());
			privilegeResourceService.deletePrivilegeResource(p);
			setResult(true, "�����ɹ�", request);
			String msg = MsgUtils.r("ɾ����Ȩ�޳ɹ�,ɾ��������Ϊ��{?}",
					FeildUtils.toString(p));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "����ʧ��:" + e.getMessage(), request);
			// e.printStackTrace();
			String msg = MsgUtils.r("ɾ����Ȩ��ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
		}
		return listPriRes();
	}

	// ���Ȩ����Դ
	public String doEdit() throws Exception {
		try {
			if (Symbol.YES.equals(model.getIsEntry())) {
				boolean exist = privilegeResourceDao.existPriRes(
						model.getLimitId(), model.getId());
				Assert.notTrue(exist, "����Ѵ���");
			}
			PrivilegeResource p = null;
			if (model.getId() != null) {
				p = privilegeResourceDao.findById(PrivilegeResource.class,
						model.getId());
			} else {
				p = new PrivilegeResource();
			}
			BeanUtils.copyProperties(p, model);
			privilegeResourceService.savePrivilegeResource(p);
			setResult(true, "�����ɹ�", request);
			String msg = MsgUtils
					.r("�޸���Ȩ�޳ɹ�,�޸�����Ϊ��{?}", FeildUtils.toString(p));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "����ʧ��:" + e.getMessage(), request);
			e.printStackTrace();
			String msg = MsgUtils.r("�޸���Ȩ��ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
		}
		return toEdit();
	}

	@Override
	public PrivilegeResource getModel() {
		return model;
	}

	// Ȩ����Դ��ѯ
	public String listPriRes() throws Exception {
		Paginater paginater = privilegeResourceDao.findPrivRes(model,
				getPager(request));
		saveQueryResult(request, paginater);
		String msg = MsgUtils.r("Ȩ����Դ��ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "privResList";
	}
	public String toAdd() throws Exception {
		Long id = model.getId();
		if (id != null && id != 0) {
			PrivilegeResource privilegeResource = privilegeResourceDao.findById(PrivilegeResource.class, id);
			BeanUtils.copyProperties(model, privilegeResource);
		}
		return "privResAdd";
		//"/pages/admin/privilege/privResEdit.jsp";
	}
	// �޸�Ȩ����Դ
	public String toEdit() throws Exception {
		Long id = model.getId();
		if (id != null && id != 0) {
			PrivilegeResource privilegeResource = privilegeResourceDao
					.findById(PrivilegeResource.class, id);
			BeanUtils.copyProperties(model, privilegeResource);
		}
		return "privResEdit";
	}
}
