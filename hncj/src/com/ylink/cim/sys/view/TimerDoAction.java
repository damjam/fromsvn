package com.ylink.cim.sys.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;

import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class TimerDoAction extends BaseAction implements ModelDriven<TimerDo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	TimerDoService timerDoService;
	@Autowired
	TimerDoDao timerDoDao;

	public String list() throws Exception {
		Paginater paginater = this.timerDoDao.getPagerList(model,
				getPager(request));
		saveQueryResult(request, paginater);
		return "list";
	}

	@Override
	public TimerDo getModel() {
		return model;
	}

	private TimerDo model = new TimerDo();
}
