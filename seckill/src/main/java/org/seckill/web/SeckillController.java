package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import flink.util.DateUtil;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	@Autowired
	private SeckillService seckillService;
	@Autowired
	private SeckillDao seckillDao;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillDao.getSeckillList();
		model.addAttribute("list", list);
		return "seckill/seckillList";
	}
	
	@RequestMapping(value="/{seckillId}",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Integer id, Model model) {
		if(id == null){
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillDao.findById(id);
		if(seckill == null){
			return "redirect:/seckill/list";
		}
		model.addAttribute(seckill);
		return "seckill/seckillDetail";
	}
	@RequestMapping(value="/{id}/exposer",method=RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("id")Integer id) {
		SeckillResult<Exposer> result = null;
		try{
			Exposer exposer = seckillService.exportSeckillUrl(id);
			result = new SeckillResult<Exposer>(true, exposer);
		}catch(Exception e){
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/{seckillId}/{md5}/execution",method=RequestMethod.POST,
			produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Integer seckillId, 
			@PathVariable("md5")String md5, @CookieValue(value="userPhone", required=false)String userPhone) {
		SeckillResult<SeckillExecution> result = null;
		try {
			if(StringUtils.isEmpty(userPhone)){
				return new SeckillResult<SeckillExecution>(false, "Î´×¢²á");
			}
			SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
			result = new SeckillResult<SeckillExecution>(true, execution);
		} catch (Exception e) {
			if (e instanceof RepeatKillException) {
				SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT);
				result = new SeckillResult<SeckillExecution>(false, execution);
			}else if (e instanceof SeckillCloseException) {
				SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
				result = new SeckillResult<SeckillExecution>(false, execution);
			}else {
				result = new SeckillResult<SeckillExecution>(false, "ÄÚ²¿´íÎó"+e.getMessage());
			}
		} 
		return result;
	}
	
	@RequestMapping(value="/time/now", method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = DateUtil.getCurrent();
		return new SeckillResult<Long>(true, now.getTime());
	}
}
