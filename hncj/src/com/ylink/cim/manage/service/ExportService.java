package com.ylink.cim.manage.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ylink.cim.manage.domain.CommonServiceBill;

public interface ExportService {

	void exportCSB(List<CommonServiceBill> list, String branchNo, HttpServletResponse response) throws Exception;
}
