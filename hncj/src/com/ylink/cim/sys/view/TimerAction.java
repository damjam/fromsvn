package com.ylink.cim.sys.view;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.sys.dao.TimerDao;
import com.ylink.cim.sys.domain.Timer;
import com.ylink.cim.sys.service.TimerService;

import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class TimerAction extends BaseAction implements ModelDriven<Timer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	TimerService timerService;
	@Autowired
	TimerDao timerDao;

	public String delete() throws Exception {
		try {
			timerService.delete(model.getId());
			setResult(true, "操作成功", request);
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
		}
		return query();
	}

	public String query() throws Exception {
		Map<String, Object> params = getParaMap();
		params.put("beanNameCh", model.getBeanNameCh());
		Paginater paginater = timerDao.getTimerList(getPager(request), params);
		saveQueryResult(request, paginater);
		return "list";
	}

	public String toEdit() throws Exception {
		if (!StringUtils.isEmpty(model.getId())) {
			Timer timer = timerDao.findById(model.getId());
			BeanUtils.copyProperties(model, timer);
		}
		return "edit";
	}

	public String toTimerMng() throws Exception {

		return "timerManager";
	}

	public String doEdit() throws Exception {
		try {
			Timer timer = null;
			if (StringUtils.isEmpty(model.getId())) {
				timer = new Timer();
				BeanUtils.copyProperties(timer, model);
				timerService.save(timer, getSessionUser(request));
			} else {
				timer = timerDao.findById(model.getId());
				String createUser = timer.getCreateUser();
				Date createTime = timer.getCreateTime();
				BeanUtils.copyProperties(timer, model);
				timer.setCreateTime(createTime);
				timer.setCreateUser(createUser);
				timerService.update(timer, getSessionUser(request));
			}
			setSucResult("操作成功", request);
			return "toMain";
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return "edit";
		}
	}

	@Override
	public Timer getModel() {
		return model;
	}

	private Timer model = new Timer();
}
