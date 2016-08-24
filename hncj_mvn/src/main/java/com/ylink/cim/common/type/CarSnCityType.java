package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class CarSnCityType extends AbstractType {

	public static Map<String, CarSnCityType> ALL = new LinkedHashMap<String, CarSnCityType>();
	public static final CarSnCityType BJ_A = new CarSnCityType("北京", "京A");
	public static final CarSnCityType BJ_B = new CarSnCityType("北京", "京B");
	public static final CarSnCityType BJ_C = new CarSnCityType("北京", "京C");
	public static final CarSnCityType BJ_D = new CarSnCityType("北京", "京D");
	public static final CarSnCityType BJ_E = new CarSnCityType("北京", "京E");
	public static final CarSnCityType BJ_F = new CarSnCityType("北京", "京F");
	public static final CarSnCityType BJ_G = new CarSnCityType("北京(郊区)", "京G");

	public static final CarSnCityType SH_A = new CarSnCityType("上海", "沪A");
	public static final CarSnCityType SH_B = new CarSnCityType("上海", "沪B");
	public static final CarSnCityType SH_C = new CarSnCityType("上海", "沪C");
	public static final CarSnCityType SH_D = new CarSnCityType("上海", "沪D");

	public static final CarSnCityType TJ_A = new CarSnCityType("天津", "津A");
	public static final CarSnCityType TJ_B = new CarSnCityType("天津", "津B");
	public static final CarSnCityType TJ_C = new CarSnCityType("天津", "津C");
	public static final CarSnCityType TJ_D = new CarSnCityType("天津", "津D");
	public static final CarSnCityType TJ_E = new CarSnCityType("天津", "津E");
	public static final CarSnCityType TJ_F = new CarSnCityType("天津", "津F");
	public static final CarSnCityType TJ_G = new CarSnCityType("天津", "津G");
	public static final CarSnCityType TJ_H = new CarSnCityType("天津", "津H");

	public static final CarSnCityType CQ_A = new CarSnCityType("重庆(江南)", "渝A");
	public static final CarSnCityType CQ_B = new CarSnCityType("重庆(江北)", "渝B");
	public static final CarSnCityType CQ_C = new CarSnCityType("重庆永川", "渝C");
	public static final CarSnCityType CQ_F = new CarSnCityType("重庆万州", "渝F");
	public static final CarSnCityType CQ_G = new CarSnCityType("重庆涪陵", "渝G");
	public static final CarSnCityType CQ_H = new CarSnCityType("重庆黔江", "渝H");

	public static final CarSnCityType HE_A = new CarSnCityType("河北石家庄", "冀A");
	public static final CarSnCityType HE_B = new CarSnCityType("河北唐山", "冀B");
	public static final CarSnCityType HE_C = new CarSnCityType("河北秦皇岛", "冀C");
	public static final CarSnCityType HE_D = new CarSnCityType("河北邯郸", "冀D");
	public static final CarSnCityType HE_E = new CarSnCityType("河北邢台", "冀E");
	public static final CarSnCityType HE_F = new CarSnCityType("河北保定", "冀F");
	public static final CarSnCityType HE_G = new CarSnCityType("河北张家口", "冀G");
	public static final CarSnCityType HE_H = new CarSnCityType("河北承德", "冀H");
	public static final CarSnCityType HE_J = new CarSnCityType("河北沧州", "冀J");
	public static final CarSnCityType HE_R = new CarSnCityType("河北廊坊", "冀R");
	public static final CarSnCityType HE_T = new CarSnCityType("河北衡水", "冀T");

	public static final CarSnCityType NM_A = new CarSnCityType("内蒙古呼和浩特", "蒙A");
	public static final CarSnCityType NM_B = new CarSnCityType("内蒙古包头", "蒙B");
	public static final CarSnCityType NM_C = new CarSnCityType("内蒙古乌海", "蒙C");
	public static final CarSnCityType NM_D = new CarSnCityType("内蒙古赤峰", "蒙D");
	public static final CarSnCityType NM_E = new CarSnCityType("内蒙古呼伦贝尔", "蒙E");
	public static final CarSnCityType NM_F = new CarSnCityType("内蒙古兴安", "蒙F");
	public static final CarSnCityType NM_G = new CarSnCityType("内蒙古通辽", "蒙G");
	public static final CarSnCityType NM_H = new CarSnCityType("内蒙古锡林郭勒", "蒙H");
	public static final CarSnCityType NM_J = new CarSnCityType("内蒙古乌兰察布", "蒙J");
	public static final CarSnCityType NM_K = new CarSnCityType("内蒙古鄂尔多斯", "蒙K");
	public static final CarSnCityType NM_L = new CarSnCityType("内蒙古巴彦淖尔", "蒙L");
	public static final CarSnCityType NM_M = new CarSnCityType("内蒙古阿拉善", "蒙M");

	public static final CarSnCityType LN_A = new CarSnCityType("辽宁沈阳", "辽A");
	public static final CarSnCityType LN_B = new CarSnCityType("辽宁大连", "辽B");
	public static final CarSnCityType LN_C = new CarSnCityType("辽宁鞍山", "辽C");
	public static final CarSnCityType LN_D = new CarSnCityType("辽宁抚顺", "辽D");
	public static final CarSnCityType LN_E = new CarSnCityType("辽宁本溪", "辽E");
	public static final CarSnCityType LN_F = new CarSnCityType("辽宁丹东", "辽F");
	public static final CarSnCityType LN_G = new CarSnCityType("辽宁锦州", "辽G");
	public static final CarSnCityType LN_H = new CarSnCityType("辽宁营口", "辽H");
	public static final CarSnCityType LN_J = new CarSnCityType("辽宁阜新", "辽J");
	public static final CarSnCityType LN_K = new CarSnCityType("辽宁辽阳", "辽K");
	public static final CarSnCityType LN_L = new CarSnCityType("辽宁盘锦", "辽L");
	public static final CarSnCityType LN_M = new CarSnCityType("辽宁铁岭", "辽M");
	public static final CarSnCityType LN_N = new CarSnCityType("辽宁朝阳", "辽N");
	public static final CarSnCityType LN_P = new CarSnCityType("辽宁葫芦岛", "辽P");
	public static final CarSnCityType LN_V = new CarSnCityType("辽宁沈阳", "辽V");

	public static final CarSnCityType JL_A = new CarSnCityType("吉林长春", "吉A");
	public static final CarSnCityType JL_B = new CarSnCityType("吉林吉安", "吉B");
	public static final CarSnCityType JL_C = new CarSnCityType("吉林四平", "吉C");
	public static final CarSnCityType JL_D = new CarSnCityType("吉林辽源", "吉D");
	public static final CarSnCityType JL_E = new CarSnCityType("吉林通话", "吉E");
	public static final CarSnCityType JL_F = new CarSnCityType("吉林白山", "吉F");
	public static final CarSnCityType JL_G = new CarSnCityType("吉林白城", "吉G");
	public static final CarSnCityType JL_H = new CarSnCityType("吉林延边", "吉H");
	public static final CarSnCityType JL_J = new CarSnCityType("吉林松原", "吉J");

	public static final CarSnCityType HL_A = new CarSnCityType("黑龙江哈尔滨", "黑A");
	public static final CarSnCityType HL_B = new CarSnCityType("黑龙江齐齐哈尔", "黑B");
	public static final CarSnCityType HL_C = new CarSnCityType("黑龙江牡丹江", "黑C");
	public static final CarSnCityType HL_D = new CarSnCityType("黑龙江佳木斯", "黑D");
	public static final CarSnCityType HL_E = new CarSnCityType("黑龙江大庆", "黑E");
	public static final CarSnCityType HL_F = new CarSnCityType("黑龙江伊春", "黑F");
	public static final CarSnCityType HL_G = new CarSnCityType("黑龙江鸡西", "黑G");
	public static final CarSnCityType HL_H = new CarSnCityType("黑龙江鹤岗", "黑H");
	public static final CarSnCityType HL_J = new CarSnCityType("黑龙江双鸭山", "黑J");
	public static final CarSnCityType HL_K = new CarSnCityType("黑龙江七台河", "黑K");
	public static final CarSnCityType HL_L = new CarSnCityType("黑龙江哈尔滨", "黑L");// 松花江地区（已并入哈尔滨市）
	public static final CarSnCityType HL_M = new CarSnCityType("黑龙江绥化", "黑M");
	public static final CarSnCityType HL_N = new CarSnCityType("黑龙江黑河", "黑N");
	public static final CarSnCityType HL_P = new CarSnCityType("黑龙江大兴安岭", "黑P");
	public static final CarSnCityType HL_R = new CarSnCityType("黑龙江农垦系统", "黑R");

	public static final CarSnCityType JS_A = new CarSnCityType("江苏南京", "苏A");
	public static final CarSnCityType JS_B = new CarSnCityType("江苏无锡", "苏B");
	public static final CarSnCityType JS_C = new CarSnCityType("江苏徐州", "苏C");
	public static final CarSnCityType JS_D = new CarSnCityType("江苏常州", "苏D");
	public static final CarSnCityType JS_E = new CarSnCityType("江苏苏州", "苏E");
	public static final CarSnCityType JS_F = new CarSnCityType("江苏南通", "苏F");
	public static final CarSnCityType JS_G = new CarSnCityType("江苏连云港", "苏G");
	public static final CarSnCityType JS_H = new CarSnCityType("江苏淮安", "苏H");
	public static final CarSnCityType JS_J = new CarSnCityType("江苏盐城", "苏J");
	public static final CarSnCityType JS_K = new CarSnCityType("江苏扬州", "苏K");
	public static final CarSnCityType JS_L = new CarSnCityType("江苏镇江", "苏L");
	public static final CarSnCityType JS_M = new CarSnCityType("江苏泰州", "苏M");
	public static final CarSnCityType JS_N = new CarSnCityType("江苏宿迁", "苏N");

	public static final CarSnCityType ZJ_A = new CarSnCityType("浙江杭州", "浙A");
	public static final CarSnCityType ZJ_B = new CarSnCityType("浙江宁波", "浙B");
	public static final CarSnCityType ZJ_C = new CarSnCityType("浙江温州", "浙C");
	public static final CarSnCityType ZJ_D = new CarSnCityType("浙江绍兴", "浙D");
	public static final CarSnCityType ZJ_E = new CarSnCityType("浙江湖州", "浙E");
	public static final CarSnCityType ZJ_F = new CarSnCityType("浙江 嘉兴", "浙F");
	public static final CarSnCityType ZJ_G = new CarSnCityType("浙江金华", "浙G");
	public static final CarSnCityType ZJ_H = new CarSnCityType("浙江衢州", "浙H");
	public static final CarSnCityType ZJ_J = new CarSnCityType("浙江台州", "浙J");
	public static final CarSnCityType ZJ_K = new CarSnCityType("浙江丽水", "浙K");
	public static final CarSnCityType ZJ_L = new CarSnCityType("浙江舟山", "浙L");

	public static final CarSnCityType AH_A = new CarSnCityType("安徽合肥", "皖A");
	public static final CarSnCityType AH_B = new CarSnCityType("安徽 芜湖", "皖B");
	public static final CarSnCityType AH_C = new CarSnCityType("安徽蚌埠", "皖C");
	public static final CarSnCityType AH_D = new CarSnCityType("安徽淮南", "皖D");
	public static final CarSnCityType AH_E = new CarSnCityType("安徽马鞍山", "皖E");
	public static final CarSnCityType AH_F = new CarSnCityType("安徽淮北", "皖F");
	public static final CarSnCityType AH_G = new CarSnCityType("安徽铜陵", "皖G");
	public static final CarSnCityType AH_H = new CarSnCityType("安徽安庆", "皖H");
	public static final CarSnCityType AH_J = new CarSnCityType("安徽黄山", "皖J");
	public static final CarSnCityType AH_K = new CarSnCityType("安徽阜阳", "皖K");
	public static final CarSnCityType AH_L = new CarSnCityType("安徽宿州", "皖L");
	public static final CarSnCityType AH_M = new CarSnCityType("安徽滁州", "皖M");
	public static final CarSnCityType AH_N = new CarSnCityType("安徽六安", "皖N");
	public static final CarSnCityType AH_P = new CarSnCityType("安徽宣城", "皖P");
	public static final CarSnCityType AH_Q = new CarSnCityType("安徽巢湖", "皖Q");
	public static final CarSnCityType AH_R = new CarSnCityType("安徽池州", "皖R");
	public static final CarSnCityType AH_S = new CarSnCityType("安徽亳州", "皖S");

	public static final CarSnCityType FJ_A = new CarSnCityType("福建福州", "闽A");
	public static final CarSnCityType FJ_B = new CarSnCityType("福建莆田", "闽B");
	public static final CarSnCityType FJ_C = new CarSnCityType("福建泉州", "闽C");
	public static final CarSnCityType FJ_D = new CarSnCityType("福建厦门", "闽D");
	public static final CarSnCityType FJ_E = new CarSnCityType("福建漳州", "闽E");
	public static final CarSnCityType FJ_F = new CarSnCityType("福建龙岩", "闽F");
	public static final CarSnCityType FJ_G = new CarSnCityType("福建三明", "闽G");
	public static final CarSnCityType FJ_H = new CarSnCityType("福建南平", "闽H");
	public static final CarSnCityType FJ_J = new CarSnCityType("福建宁德", "闽J");
	public static final CarSnCityType FJ_K = new CarSnCityType("福建福州", "闽K");

	public static final CarSnCityType JX_A = new CarSnCityType("江西南昌", "赣A");
	public static final CarSnCityType JX_B = new CarSnCityType("江西赣州", "赣B");
	public static final CarSnCityType JX_C = new CarSnCityType("江西宜春", "赣C");
	public static final CarSnCityType JX_D = new CarSnCityType("江西吉安", "赣D");
	public static final CarSnCityType JX_E = new CarSnCityType("江西上饶", "赣E");
	public static final CarSnCityType JX_F = new CarSnCityType("江西抚州", "赣F");
	public static final CarSnCityType JX_G = new CarSnCityType("江西 九江", "赣G");
	public static final CarSnCityType JX_H = new CarSnCityType("江西景德镇", "赣H");
	public static final CarSnCityType JX_J = new CarSnCityType("江西 萍乡", "赣J");
	public static final CarSnCityType JX_K = new CarSnCityType("江西新余", "赣K");
	public static final CarSnCityType JX_L = new CarSnCityType("江西鹰潭", "赣L");
	public static final CarSnCityType JX_M = new CarSnCityType("江西南昌", "赣M");

	public static final CarSnCityType SD_A = new CarSnCityType("山东济南", "鲁A");
	public static final CarSnCityType SD_B = new CarSnCityType("山东青岛", "鲁B");
	public static final CarSnCityType SD_C = new CarSnCityType("山东淄博", "鲁C");
	public static final CarSnCityType SD_D = new CarSnCityType("山东枣庄", "鲁D");
	public static final CarSnCityType SD_E = new CarSnCityType("山东东营", "鲁E");
	public static final CarSnCityType SD_F = new CarSnCityType("山东烟台", "鲁F");
	public static final CarSnCityType SD_G = new CarSnCityType("山东潍坊", "鲁G");
	public static final CarSnCityType SD_H = new CarSnCityType("山东济宁", "鲁H");
	public static final CarSnCityType SD_J = new CarSnCityType("山东泰安", "鲁J");
	public static final CarSnCityType SD_K = new CarSnCityType("山东威海", "鲁K");
	public static final CarSnCityType SD_L = new CarSnCityType("山东日照", "鲁L");
	public static final CarSnCityType SD_M = new CarSnCityType("山东滨州", "鲁M");
	public static final CarSnCityType SD_N = new CarSnCityType("山东德州", "鲁N");
	public static final CarSnCityType SD_P = new CarSnCityType("山东 聊城", "鲁P");
	public static final CarSnCityType SD_Q = new CarSnCityType("山东临沂", "鲁Q");
	public static final CarSnCityType SD_R = new CarSnCityType("山东菏泽", "鲁R");
	public static final CarSnCityType SD_S = new CarSnCityType("山东莱芜", "鲁S");
	public static final CarSnCityType SD_U = new CarSnCityType("山东青岛", "鲁U");// 增补
	public static final CarSnCityType SD_V = new CarSnCityType("山东潍坊", "鲁V");

	public static final CarSnCityType HA_A = new CarSnCityType("河南郑州", "豫A");
	public static final CarSnCityType HA_B = new CarSnCityType("河南开封", "豫B");
	public static final CarSnCityType HA_C = new CarSnCityType("河南洛阳", "豫C");
	public static final CarSnCityType HA_D = new CarSnCityType("河南平顶山", "豫D");
	public static final CarSnCityType HA_E = new CarSnCityType("河南安阳", "豫E");
	public static final CarSnCityType HA_F = new CarSnCityType("河南鹤壁", "豫F");
	public static final CarSnCityType HA_G = new CarSnCityType("河南新乡", "豫G");
	public static final CarSnCityType HA_H = new CarSnCityType("河南焦作", "豫H");
	public static final CarSnCityType HA_J = new CarSnCityType("河南濮阳", "豫J");
	public static final CarSnCityType HA_K = new CarSnCityType("河南许昌", "豫K");
	public static final CarSnCityType HA_L = new CarSnCityType("河南漯河", "豫L");
	public static final CarSnCityType HA_M = new CarSnCityType("河南三门峡", "豫M");
	public static final CarSnCityType HA_N = new CarSnCityType("河南商丘", "豫N");
	public static final CarSnCityType HA_P = new CarSnCityType("河南周口", "豫P");
	public static final CarSnCityType HA_Q = new CarSnCityType("河南驻马店", "豫Q");
	public static final CarSnCityType HA_R = new CarSnCityType("河南南阳", "豫R");
	public static final CarSnCityType HA_S = new CarSnCityType("河南信阳", "豫S");
	public static final CarSnCityType HA_U = new CarSnCityType("河南济源", "豫U");

	public static final CarSnCityType HB_A = new CarSnCityType("湖北武汉", "鄂A");
	public static final CarSnCityType HB_B = new CarSnCityType("湖北黄石", "鄂B");
	public static final CarSnCityType HB_C = new CarSnCityType("湖北十堰", "鄂C");
	public static final CarSnCityType HB_D = new CarSnCityType("湖北荆州", "鄂D");
	public static final CarSnCityType HB_E = new CarSnCityType("湖北宜昌", "鄂E");
	public static final CarSnCityType HB_F = new CarSnCityType("湖北襄樊", "鄂F");
	public static final CarSnCityType HB_G = new CarSnCityType("湖北鄂州", "鄂G");
	public static final CarSnCityType HB_H = new CarSnCityType("湖北荆门", "鄂H");
	public static final CarSnCityType HB_J = new CarSnCityType("湖北黄冈", "鄂J");
	public static final CarSnCityType HB_K = new CarSnCityType("湖北孝感", "鄂K");
	public static final CarSnCityType HB_L = new CarSnCityType("湖北咸宁", "鄂L");
	public static final CarSnCityType HB_M = new CarSnCityType("湖北仙桃", "鄂M");
	public static final CarSnCityType HB_N = new CarSnCityType("湖北潜江", "鄂N");
	public static final CarSnCityType HB_P = new CarSnCityType("湖北神农架", "鄂P");
	public static final CarSnCityType HB_Q = new CarSnCityType("湖北恩施", "鄂Q");
	public static final CarSnCityType HB_R = new CarSnCityType("湖北天门", "鄂R");
	public static final CarSnCityType HB_S = new CarSnCityType("湖北随州", "鄂S");

	public static final CarSnCityType HN_A = new CarSnCityType("湖南长沙", "湘A");
	public static final CarSnCityType HN_B = new CarSnCityType("湖南株洲", "湘B");
	public static final CarSnCityType HN_C = new CarSnCityType("湖南湘潭", "湘C");
	public static final CarSnCityType HN_D = new CarSnCityType("湖南衡阳", "湘D");
	public static final CarSnCityType HN_E = new CarSnCityType("湖南邵阳", "湘E");
	public static final CarSnCityType HN_F = new CarSnCityType("湖南岳阳", "湘F");
	public static final CarSnCityType HN_G = new CarSnCityType("湖南张家界", "湘G");
	public static final CarSnCityType HN_H = new CarSnCityType("湖南益阳", "湘H");
	public static final CarSnCityType HN_J = new CarSnCityType("湖南常德", "湘J");
	public static final CarSnCityType HN_K = new CarSnCityType("湖南娄底", "湘K");
	public static final CarSnCityType HN_L = new CarSnCityType("湖南郴州", "湘L");
	public static final CarSnCityType HN_M = new CarSnCityType("湖南永州", "湘M");
	public static final CarSnCityType HN_N = new CarSnCityType("湖南怀化", "湘N");
	public static final CarSnCityType HN_U = new CarSnCityType("湖南湘西", "湘U");

	public static final CarSnCityType GD_A = new CarSnCityType("广东广州", "粤A");
	public static final CarSnCityType GD_B = new CarSnCityType("广东深圳", "粤B");
	public static final CarSnCityType GD_C = new CarSnCityType("广东珠海", "粤C");
	public static final CarSnCityType GD_D = new CarSnCityType("广东汕头", "粤D");
	public static final CarSnCityType GD_E = new CarSnCityType("广东佛山", "粤E");
	public static final CarSnCityType GD_F = new CarSnCityType("广东韶关", "粤F");
	public static final CarSnCityType GD_G = new CarSnCityType("广东湛江", "粤G");
	public static final CarSnCityType GD_H = new CarSnCityType("广东肇庆", "粤H");
	public static final CarSnCityType GD_J = new CarSnCityType("广东江门", "粤J");
	public static final CarSnCityType GD_K = new CarSnCityType("广东茂名", "粤K");
	public static final CarSnCityType GD_L = new CarSnCityType("广东惠州", "粤L");
	public static final CarSnCityType GD_M = new CarSnCityType("广东梅州", "粤M");
	public static final CarSnCityType GD_N = new CarSnCityType("广东汕尾", "粤N");
	public static final CarSnCityType GD_P = new CarSnCityType("广东河源", "粤P");
	public static final CarSnCityType GD_Q = new CarSnCityType("广东阳江", "粤Q");
	public static final CarSnCityType GD_R = new CarSnCityType("广东清远", "粤R");
	public static final CarSnCityType GD_S = new CarSnCityType("广东东莞", "粤S");
	public static final CarSnCityType GD_T = new CarSnCityType("广东中山", "粤T");
	public static final CarSnCityType GD_U = new CarSnCityType("广东潮州", "粤U");
	public static final CarSnCityType GD_V = new CarSnCityType("广东揭阳", "粤V");
	public static final CarSnCityType GD_W = new CarSnCityType("广东云浮", "粤W");
	public static final CarSnCityType GD_X = new CarSnCityType("广东佛山顺德", "粤X");
	public static final CarSnCityType GD_Y = new CarSnCityType("广东佛山南海", "粤Y");
	public static final CarSnCityType GD_Z = new CarSnCityType("港澳地区", "粤Z");

	public static final CarSnCityType GX_A = new CarSnCityType("广西南宁", "桂A");
	public static final CarSnCityType GX_B = new CarSnCityType("广西柳州", "桂B");
	public static final CarSnCityType GX_C = new CarSnCityType("广西桂林", "桂C");
	public static final CarSnCityType GX_D = new CarSnCityType("广西梧州", "桂D");
	public static final CarSnCityType GX_E = new CarSnCityType("广西北海", "桂E");
	public static final CarSnCityType GX_F = new CarSnCityType("广西崇左", "桂F");
	public static final CarSnCityType GX_G = new CarSnCityType("广西来宾", "桂G");
	public static final CarSnCityType GX_H = new CarSnCityType("广西桂林", "桂H");
	public static final CarSnCityType GX_J = new CarSnCityType("广西贺州", "桂J");
	public static final CarSnCityType GX_K = new CarSnCityType("广西玉林", "桂K");
	public static final CarSnCityType GX_L = new CarSnCityType("广西百色", "桂L");
	public static final CarSnCityType GX_M = new CarSnCityType("广西河池", "桂M");
	public static final CarSnCityType GX_N = new CarSnCityType("广西防城港", "桂N");
	public static final CarSnCityType GX_P = new CarSnCityType("广西钦州", "桂P");
	public static final CarSnCityType GX_R = new CarSnCityType("广西贵港", "桂R");

	public static final CarSnCityType HI_A = new CarSnCityType("海南海口", "琼A");
	public static final CarSnCityType HI_B = new CarSnCityType("海南三亚", "琼B");
	public static final CarSnCityType HI_C = new CarSnCityType("海南琼北", "琼C");
	public static final CarSnCityType HI_D = new CarSnCityType("海南琼南", "琼D");
	public static final CarSnCityType HI_E = new CarSnCityType("海南洋浦", "琼E");

	public static final CarSnCityType SC_A = new CarSnCityType("四川成都", "川A");
	public static final CarSnCityType SC_B = new CarSnCityType("四川绵阳", "川B");
	public static final CarSnCityType SC_C = new CarSnCityType("四川自贡", "川C");
	public static final CarSnCityType SC_D = new CarSnCityType("四川攀枝花", "川D");
	public static final CarSnCityType SC_E = new CarSnCityType("四川泸州", "川E");
	public static final CarSnCityType SC_F = new CarSnCityType("四川 德阳", "川F");
	public static final CarSnCityType SC_G = new CarSnCityType("四川广元", "川H");
	public static final CarSnCityType SC_H = new CarSnCityType("四川遂宁", "川J");
	public static final CarSnCityType SC_J = new CarSnCityType("四川内江", "川K");
	public static final CarSnCityType SC_K = new CarSnCityType("四川乐山", "川L");
	public static final CarSnCityType SC_L = new CarSnCityType("四川资阳", "川M");
	public static final CarSnCityType SC_M = new CarSnCityType("四川 宜宾", "川N");
	public static final CarSnCityType SC_N = new CarSnCityType("四川南充", "川R");
	public static final CarSnCityType SC_S = new CarSnCityType("四川达州", "川S");
	public static final CarSnCityType SC_T = new CarSnCityType("四川雅安", "川T");
	public static final CarSnCityType SC_U = new CarSnCityType("四川阿坝", "川U");
	public static final CarSnCityType SC_V = new CarSnCityType("四川甘孜", "川V");
	public static final CarSnCityType SC_W = new CarSnCityType("四川凉山", "川W");
	public static final CarSnCityType SC_X = new CarSnCityType("四川广安", "川X");
	public static final CarSnCityType SC_Y = new CarSnCityType("四川巴中", "川Y");
	public static final CarSnCityType SC_Z = new CarSnCityType("四川眉山", "川Z");

	public static final CarSnCityType GZ_A = new CarSnCityType("贵州贵阳", "贵A");
	public static final CarSnCityType GZ_B = new CarSnCityType("贵州六盘水", "贵B");
	public static final CarSnCityType GZ_C = new CarSnCityType("贵州遵义", "贵C");
	public static final CarSnCityType GZ_D = new CarSnCityType("贵州铜仁", "贵D");
	public static final CarSnCityType GZ_E = new CarSnCityType("贵州黔西南", "贵E");
	public static final CarSnCityType GZ_F = new CarSnCityType("贵州毕节", "贵F");
	public static final CarSnCityType GZ_G = new CarSnCityType("贵州安顺", "贵G");
	public static final CarSnCityType GZ_H = new CarSnCityType("贵州黔东南", "贵H");
	public static final CarSnCityType GZ_J = new CarSnCityType("贵州黔南", "贵J");

	public static final CarSnCityType YN_A = new CarSnCityType("云南", "云A");
	public static final CarSnCityType YN_B = new CarSnCityType("云南", "云B");
	public static final CarSnCityType YN_C = new CarSnCityType("云南", "云C");
	public static final CarSnCityType YN_D = new CarSnCityType("云南", "云D");
	public static final CarSnCityType YN_E = new CarSnCityType("云南", "云E");
	public static final CarSnCityType YN_F = new CarSnCityType("云南", "云F");
	public static final CarSnCityType YN_G = new CarSnCityType("云南", "云G");
	public static final CarSnCityType YN_H = new CarSnCityType("云南", "云H");
	public static final CarSnCityType YN_J = new CarSnCityType("云南", "云J");
	public static final CarSnCityType YN_K = new CarSnCityType("云南", "云K");
	public static final CarSnCityType YN_L = new CarSnCityType("云南", "云L");
	public static final CarSnCityType YN_M = new CarSnCityType("云南", "云M");
	public static final CarSnCityType YN_N = new CarSnCityType("云南", "云N");
	public static final CarSnCityType YN_P = new CarSnCityType("云南", "云P");
	public static final CarSnCityType YN_Q = new CarSnCityType("云南", "云Q");
	public static final CarSnCityType YN_R = new CarSnCityType("云南", "云R");
	public static final CarSnCityType YN_S = new CarSnCityType("云南", "云S");

	public static final CarSnCityType XZ_A = new CarSnCityType("西藏拉萨", "藏A");
	public static final CarSnCityType XZ_B = new CarSnCityType("西藏昌都", "藏B");
	public static final CarSnCityType XZ_C = new CarSnCityType("西藏山南", "藏C");
	public static final CarSnCityType XZ_D = new CarSnCityType("西藏日喀则", "藏D");
	public static final CarSnCityType XZ_E = new CarSnCityType("西藏那曲", "藏E");
	public static final CarSnCityType XZ_F = new CarSnCityType("西藏阿里", "藏F");
	public static final CarSnCityType XZ_G = new CarSnCityType("西藏林芝", "藏G");
	public static final CarSnCityType XZ_H = new CarSnCityType("西藏驻四川省天全县",
			"藏H");
	public static final CarSnCityType XZ_J = new CarSnCityType("西藏驻青海省格尔木市",
			"藏J");

	public static final CarSnCityType QH_A = new CarSnCityType("青海西宁", "青A");
	public static final CarSnCityType QH_B = new CarSnCityType("青海海东", "青B");
	public static final CarSnCityType QH_C = new CarSnCityType("青海海北", "青C");
	public static final CarSnCityType QH_D = new CarSnCityType("青海黄南", "青D");
	public static final CarSnCityType QH_E = new CarSnCityType("青海海南", "青E");
	public static final CarSnCityType QH_F = new CarSnCityType("青海果洛", "青F");
	public static final CarSnCityType QH_G = new CarSnCityType("青海玉树", "青G");
	public static final CarSnCityType QH_H = new CarSnCityType("青海海西", "青H");

	public static final CarSnCityType NX_A = new CarSnCityType("宁夏银川", "宁A");
	public static final CarSnCityType NX_B = new CarSnCityType("宁夏石嘴山", "宁B");
	public static final CarSnCityType NX_C = new CarSnCityType("宁夏吴忠", "宁C");
	public static final CarSnCityType NX_D = new CarSnCityType("宁夏固原", "宁D");

	public static final CarSnCityType XJ_A = new CarSnCityType("新疆乌鲁木齐", "疆A");
	public static final CarSnCityType XJ_B = new CarSnCityType("新疆昌吉", "疆B");
	public static final CarSnCityType XJ_C = new CarSnCityType("新疆石河子", "疆C");
	public static final CarSnCityType XJ_D = new CarSnCityType("新疆奎屯", "疆D");
	public static final CarSnCityType XJ_E = new CarSnCityType("新疆博尔塔拉", "疆E");
	public static final CarSnCityType XJ_F = new CarSnCityType("新疆伊犁", "疆F");
	public static final CarSnCityType XJ_G = new CarSnCityType("新疆塔城", "疆G");
	public static final CarSnCityType XJ_H = new CarSnCityType("新疆阿勒泰", "疆H");
	public static final CarSnCityType XJ_J = new CarSnCityType("新疆克拉玛依", "疆J");
	public static final CarSnCityType XJ_K = new CarSnCityType("新疆吐鲁番", "疆K");
	public static final CarSnCityType XJ_L = new CarSnCityType("新疆哈密", "疆L");
	public static final CarSnCityType XJ_M = new CarSnCityType("新疆巴音郭楞", "疆M");
	public static final CarSnCityType XJ_N = new CarSnCityType("新疆阿克苏", "疆N");
	public static final CarSnCityType XJ_P = new CarSnCityType("新疆克孜勒苏柯尔克孜",
			"疆P");
	public static final CarSnCityType XJ_Q = new CarSnCityType("新疆喀什", "疆Q");
	public static final CarSnCityType XJ_R = new CarSnCityType("新疆和田", "疆R");

	protected CarSnCityType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static CarSnCityType valueOf(String value) {

		CarSnCityType type = ALL.get(value);
		if (type == null) {
			return new CarSnCityType("", value);
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("carSnCitys", CarSnCityType.ALL.values());
	}

}
