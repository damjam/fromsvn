package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.MaterialState;
import com.ylink.cim.manage.dao.MaterialDao;
import com.ylink.cim.manage.domain.Material;
import com.ylink.cim.manage.service.MaterialService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.CRUDAction;

@Scope("prototype")
@Component
public class MaterialAction extends CRUDAction implements ModelDriven<Material> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MaterialDao materialDao;
	@Autowired
	private MaterialService materialService;
	private Material model = new Material();

	@Override
	public Material getModel() {
		return model;
	}

	@Override
	public String list() throws Exception {
		Map<String, Object> map = getParaMap();
		Paginater paginater = materialDao.findPaginater(map, getPager(request));
		saveQueryResult(request, paginater);
		initSelect();
		return LIST;
	}

	@Override
	public String toAdd() throws Exception {
		initSelect();
		return ADD;
	}

	private void initSelect() {
		MaterialState.setInReq(request);
	}

	@Override
	public String doAdd() throws Exception {
		try {
			materialService.add(model, getSessionUser(request));
			setSucResult(request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "²Ù×÷Ê§°Ü", request);
			return toAdd();
		}
		return "toMain";
	}

	@Override
	public String toEdit() throws Exception {
		Material material = materialDao.findById(model.getId());
		BeanUtils.copyProperties(model, material);
		initSelect();
		return EDIT;
	}

	@Override
	public String doEdit() throws Exception {
		try {
			materialService.update(model, getSessionUser(request));
			setSucResult(request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
		} catch (Exception e) {
			setResult(false, "²Ù×÷Ê§°Ü", request);
		}
		return "toMain";
	}

	@Override
	public String delete() throws Exception {
		try {
			materialDao.deleteById(model.getId());
			setSucResult(request);
		} catch (Exception e) {
			setFailResult("²Ù×÷Ê§°Ü", request);
		}
		return "toMain";
	}

	@Override
	public String detail() throws Exception {
		Material material = materialDao.findById(model.getId());
		BeanUtils.copyProperties(model, material);
		return DETAIL;
	}
	public String toImport() throws Exception {
		
		return "import";
	}
	public String doImport() throws Exception {
		try {
			
		} catch (Exception e) {
			setFailResult("²Ù×÷Ê§°Ü", request);
		}
		return "toMain";
	}
	public String export() throws Exception {
		try {
			
		} catch (Exception e) {
			setFailResult("²Ù×÷Ê§°Ü", request);
		}
		return list();
	}
}
