package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class CarSnCityType extends AbstractType {

	public static Map<String, CarSnCityType> ALL = new LinkedHashMap<String, CarSnCityType>();
	public static final CarSnCityType BJ_A = new CarSnCityType("����", "��A");
	public static final CarSnCityType BJ_B = new CarSnCityType("����", "��B");
	public static final CarSnCityType BJ_C = new CarSnCityType("����", "��C");
	public static final CarSnCityType BJ_D = new CarSnCityType("����", "��D");
	public static final CarSnCityType BJ_E = new CarSnCityType("����", "��E");
	public static final CarSnCityType BJ_F = new CarSnCityType("����", "��F");
	public static final CarSnCityType BJ_G = new CarSnCityType("����(����)", "��G");

	public static final CarSnCityType SH_A = new CarSnCityType("�Ϻ�", "��A");
	public static final CarSnCityType SH_B = new CarSnCityType("�Ϻ�", "��B");
	public static final CarSnCityType SH_C = new CarSnCityType("�Ϻ�", "��C");
	public static final CarSnCityType SH_D = new CarSnCityType("�Ϻ�", "��D");

	public static final CarSnCityType TJ_A = new CarSnCityType("���", "��A");
	public static final CarSnCityType TJ_B = new CarSnCityType("���", "��B");
	public static final CarSnCityType TJ_C = new CarSnCityType("���", "��C");
	public static final CarSnCityType TJ_D = new CarSnCityType("���", "��D");
	public static final CarSnCityType TJ_E = new CarSnCityType("���", "��E");
	public static final CarSnCityType TJ_F = new CarSnCityType("���", "��F");
	public static final CarSnCityType TJ_G = new CarSnCityType("���", "��G");
	public static final CarSnCityType TJ_H = new CarSnCityType("���", "��H");

	public static final CarSnCityType CQ_A = new CarSnCityType("����(����)", "��A");
	public static final CarSnCityType CQ_B = new CarSnCityType("����(����)", "��B");
	public static final CarSnCityType CQ_C = new CarSnCityType("��������", "��C");
	public static final CarSnCityType CQ_F = new CarSnCityType("��������", "��F");
	public static final CarSnCityType CQ_G = new CarSnCityType("���츢��", "��G");
	public static final CarSnCityType CQ_H = new CarSnCityType("����ǭ��", "��H");

	public static final CarSnCityType HE_A = new CarSnCityType("�ӱ�ʯ��ׯ", "��A");
	public static final CarSnCityType HE_B = new CarSnCityType("�ӱ���ɽ", "��B");
	public static final CarSnCityType HE_C = new CarSnCityType("�ӱ��ػʵ�", "��C");
	public static final CarSnCityType HE_D = new CarSnCityType("�ӱ�����", "��D");
	public static final CarSnCityType HE_E = new CarSnCityType("�ӱ���̨", "��E");
	public static final CarSnCityType HE_F = new CarSnCityType("�ӱ�����", "��F");
	public static final CarSnCityType HE_G = new CarSnCityType("�ӱ��żҿ�", "��G");
	public static final CarSnCityType HE_H = new CarSnCityType("�ӱ��е�", "��H");
	public static final CarSnCityType HE_J = new CarSnCityType("�ӱ�����", "��J");
	public static final CarSnCityType HE_R = new CarSnCityType("�ӱ��ȷ�", "��R");
	public static final CarSnCityType HE_T = new CarSnCityType("�ӱ���ˮ", "��T");

	public static final CarSnCityType NM_A = new CarSnCityType("���ɹź��ͺ���", "��A");
	public static final CarSnCityType NM_B = new CarSnCityType("���ɹŰ�ͷ", "��B");
	public static final CarSnCityType NM_C = new CarSnCityType("���ɹ��ں�", "��C");
	public static final CarSnCityType NM_D = new CarSnCityType("���ɹų��", "��D");
	public static final CarSnCityType NM_E = new CarSnCityType("���ɹź��ױ���", "��E");
	public static final CarSnCityType NM_F = new CarSnCityType("���ɹ��˰�", "��F");
	public static final CarSnCityType NM_G = new CarSnCityType("���ɹ�ͨ��", "��G");
	public static final CarSnCityType NM_H = new CarSnCityType("���ɹ����ֹ���", "��H");
	public static final CarSnCityType NM_J = new CarSnCityType("���ɹ������첼", "��J");
	public static final CarSnCityType NM_K = new CarSnCityType("���ɹŶ�����˹", "��K");
	public static final CarSnCityType NM_L = new CarSnCityType("���ɹŰ����׶�", "��L");
	public static final CarSnCityType NM_M = new CarSnCityType("���ɹŰ�����", "��M");

	public static final CarSnCityType LN_A = new CarSnCityType("��������", "��A");
	public static final CarSnCityType LN_B = new CarSnCityType("��������", "��B");
	public static final CarSnCityType LN_C = new CarSnCityType("������ɽ", "��C");
	public static final CarSnCityType LN_D = new CarSnCityType("������˳", "��D");
	public static final CarSnCityType LN_E = new CarSnCityType("������Ϫ", "��E");
	public static final CarSnCityType LN_F = new CarSnCityType("��������", "��F");
	public static final CarSnCityType LN_G = new CarSnCityType("��������", "��G");
	public static final CarSnCityType LN_H = new CarSnCityType("����Ӫ��", "��H");
	public static final CarSnCityType LN_J = new CarSnCityType("��������", "��J");
	public static final CarSnCityType LN_K = new CarSnCityType("��������", "��K");
	public static final CarSnCityType LN_L = new CarSnCityType("�����̽�", "��L");
	public static final CarSnCityType LN_M = new CarSnCityType("��������", "��M");
	public static final CarSnCityType LN_N = new CarSnCityType("��������", "��N");
	public static final CarSnCityType LN_P = new CarSnCityType("������«��", "��P");
	public static final CarSnCityType LN_V = new CarSnCityType("��������", "��V");

	public static final CarSnCityType JL_A = new CarSnCityType("���ֳ���", "��A");
	public static final CarSnCityType JL_B = new CarSnCityType("���ּ���", "��B");
	public static final CarSnCityType JL_C = new CarSnCityType("������ƽ", "��C");
	public static final CarSnCityType JL_D = new CarSnCityType("������Դ", "��D");
	public static final CarSnCityType JL_E = new CarSnCityType("����ͨ��", "��E");
	public static final CarSnCityType JL_F = new CarSnCityType("���ְ�ɽ", "��F");
	public static final CarSnCityType JL_G = new CarSnCityType("���ְ׳�", "��G");
	public static final CarSnCityType JL_H = new CarSnCityType("�����ӱ�", "��H");
	public static final CarSnCityType JL_J = new CarSnCityType("������ԭ", "��J");

	public static final CarSnCityType HL_A = new CarSnCityType("������������", "��A");
	public static final CarSnCityType HL_B = new CarSnCityType("�������������", "��B");
	public static final CarSnCityType HL_C = new CarSnCityType("������ĵ����", "��C");
	public static final CarSnCityType HL_D = new CarSnCityType("��������ľ˹", "��D");
	public static final CarSnCityType HL_E = new CarSnCityType("����������", "��E");
	public static final CarSnCityType HL_F = new CarSnCityType("����������", "��F");
	public static final CarSnCityType HL_G = new CarSnCityType("����������", "��G");
	public static final CarSnCityType HL_H = new CarSnCityType("�������׸�", "��H");
	public static final CarSnCityType HL_J = new CarSnCityType("������˫Ѽɽ", "��J");
	public static final CarSnCityType HL_K = new CarSnCityType("��������̨��", "��K");
	public static final CarSnCityType HL_L = new CarSnCityType("������������", "��L");// �ɻ����������Ѳ���������У�
	public static final CarSnCityType HL_M = new CarSnCityType("�������绯", "��M");
	public static final CarSnCityType HL_N = new CarSnCityType("�������ں�", "��N");
	public static final CarSnCityType HL_P = new CarSnCityType("���������˰���", "��P");
	public static final CarSnCityType HL_R = new CarSnCityType("������ũ��ϵͳ", "��R");

	public static final CarSnCityType JS_A = new CarSnCityType("�����Ͼ�", "��A");
	public static final CarSnCityType JS_B = new CarSnCityType("��������", "��B");
	public static final CarSnCityType JS_C = new CarSnCityType("��������", "��C");
	public static final CarSnCityType JS_D = new CarSnCityType("���ճ���", "��D");
	public static final CarSnCityType JS_E = new CarSnCityType("��������", "��E");
	public static final CarSnCityType JS_F = new CarSnCityType("������ͨ", "��F");
	public static final CarSnCityType JS_G = new CarSnCityType("�������Ƹ�", "��G");
	public static final CarSnCityType JS_H = new CarSnCityType("���ջ���", "��H");
	public static final CarSnCityType JS_J = new CarSnCityType("�����γ�", "��J");
	public static final CarSnCityType JS_K = new CarSnCityType("��������", "��K");
	public static final CarSnCityType JS_L = new CarSnCityType("������", "��L");
	public static final CarSnCityType JS_M = new CarSnCityType("����̩��", "��M");
	public static final CarSnCityType JS_N = new CarSnCityType("������Ǩ", "��N");

	public static final CarSnCityType ZJ_A = new CarSnCityType("�㽭����", "��A");
	public static final CarSnCityType ZJ_B = new CarSnCityType("�㽭����", "��B");
	public static final CarSnCityType ZJ_C = new CarSnCityType("�㽭����", "��C");
	public static final CarSnCityType ZJ_D = new CarSnCityType("�㽭����", "��D");
	public static final CarSnCityType ZJ_E = new CarSnCityType("�㽭����", "��E");
	public static final CarSnCityType ZJ_F = new CarSnCityType("�㽭 ����", "��F");
	public static final CarSnCityType ZJ_G = new CarSnCityType("�㽭��", "��G");
	public static final CarSnCityType ZJ_H = new CarSnCityType("�㽭����", "��H");
	public static final CarSnCityType ZJ_J = new CarSnCityType("�㽭̨��", "��J");
	public static final CarSnCityType ZJ_K = new CarSnCityType("�㽭��ˮ", "��K");
	public static final CarSnCityType ZJ_L = new CarSnCityType("�㽭��ɽ", "��L");

	public static final CarSnCityType AH_A = new CarSnCityType("���պϷ�", "��A");
	public static final CarSnCityType AH_B = new CarSnCityType("���� �ߺ�", "��B");
	public static final CarSnCityType AH_C = new CarSnCityType("���հ���", "��C");
	public static final CarSnCityType AH_D = new CarSnCityType("���ջ���", "��D");
	public static final CarSnCityType AH_E = new CarSnCityType("������ɽ", "��E");
	public static final CarSnCityType AH_F = new CarSnCityType("���ջ���", "��F");
	public static final CarSnCityType AH_G = new CarSnCityType("����ͭ��", "��G");
	public static final CarSnCityType AH_H = new CarSnCityType("���հ���", "��H");
	public static final CarSnCityType AH_J = new CarSnCityType("���ջ�ɽ", "��J");
	public static final CarSnCityType AH_K = new CarSnCityType("���ո���", "��K");
	public static final CarSnCityType AH_L = new CarSnCityType("��������", "��L");
	public static final CarSnCityType AH_M = new CarSnCityType("���ճ���", "��M");
	public static final CarSnCityType AH_N = new CarSnCityType("��������", "��N");
	public static final CarSnCityType AH_P = new CarSnCityType("��������", "��P");
	public static final CarSnCityType AH_Q = new CarSnCityType("���ճ���", "��Q");
	public static final CarSnCityType AH_R = new CarSnCityType("���ճ���", "��R");
	public static final CarSnCityType AH_S = new CarSnCityType("��������", "��S");

	public static final CarSnCityType FJ_A = new CarSnCityType("��������", "��A");
	public static final CarSnCityType FJ_B = new CarSnCityType("��������", "��B");
	public static final CarSnCityType FJ_C = new CarSnCityType("����Ȫ��", "��C");
	public static final CarSnCityType FJ_D = new CarSnCityType("��������", "��D");
	public static final CarSnCityType FJ_E = new CarSnCityType("��������", "��E");
	public static final CarSnCityType FJ_F = new CarSnCityType("��������", "��F");
	public static final CarSnCityType FJ_G = new CarSnCityType("��������", "��G");
	public static final CarSnCityType FJ_H = new CarSnCityType("������ƽ", "��H");
	public static final CarSnCityType FJ_J = new CarSnCityType("��������", "��J");
	public static final CarSnCityType FJ_K = new CarSnCityType("��������", "��K");

	public static final CarSnCityType JX_A = new CarSnCityType("�����ϲ�", "��A");
	public static final CarSnCityType JX_B = new CarSnCityType("��������", "��B");
	public static final CarSnCityType JX_C = new CarSnCityType("�����˴�", "��C");
	public static final CarSnCityType JX_D = new CarSnCityType("��������", "��D");
	public static final CarSnCityType JX_E = new CarSnCityType("��������", "��E");
	public static final CarSnCityType JX_F = new CarSnCityType("��������", "��F");
	public static final CarSnCityType JX_G = new CarSnCityType("���� �Ž�", "��G");
	public static final CarSnCityType JX_H = new CarSnCityType("����������", "��H");
	public static final CarSnCityType JX_J = new CarSnCityType("���� Ƽ��", "��J");
	public static final CarSnCityType JX_K = new CarSnCityType("��������", "��K");
	public static final CarSnCityType JX_L = new CarSnCityType("����ӥ̶", "��L");
	public static final CarSnCityType JX_M = new CarSnCityType("�����ϲ�", "��M");

	public static final CarSnCityType SD_A = new CarSnCityType("ɽ������", "³A");
	public static final CarSnCityType SD_B = new CarSnCityType("ɽ���ൺ", "³B");
	public static final CarSnCityType SD_C = new CarSnCityType("ɽ���Ͳ�", "³C");
	public static final CarSnCityType SD_D = new CarSnCityType("ɽ����ׯ", "³D");
	public static final CarSnCityType SD_E = new CarSnCityType("ɽ����Ӫ", "³E");
	public static final CarSnCityType SD_F = new CarSnCityType("ɽ����̨", "³F");
	public static final CarSnCityType SD_G = new CarSnCityType("ɽ��Ϋ��", "³G");
	public static final CarSnCityType SD_H = new CarSnCityType("ɽ������", "³H");
	public static final CarSnCityType SD_J = new CarSnCityType("ɽ��̩��", "³J");
	public static final CarSnCityType SD_K = new CarSnCityType("ɽ������", "³K");
	public static final CarSnCityType SD_L = new CarSnCityType("ɽ������", "³L");
	public static final CarSnCityType SD_M = new CarSnCityType("ɽ������", "³M");
	public static final CarSnCityType SD_N = new CarSnCityType("ɽ������", "³N");
	public static final CarSnCityType SD_P = new CarSnCityType("ɽ�� �ĳ�", "³P");
	public static final CarSnCityType SD_Q = new CarSnCityType("ɽ������", "³Q");
	public static final CarSnCityType SD_R = new CarSnCityType("ɽ������", "³R");
	public static final CarSnCityType SD_S = new CarSnCityType("ɽ������", "³S");
	public static final CarSnCityType SD_U = new CarSnCityType("ɽ���ൺ", "³U");// ����
	public static final CarSnCityType SD_V = new CarSnCityType("ɽ��Ϋ��", "³V");

	public static final CarSnCityType HA_A = new CarSnCityType("����֣��", "ԥA");
	public static final CarSnCityType HA_B = new CarSnCityType("���Ͽ���", "ԥB");
	public static final CarSnCityType HA_C = new CarSnCityType("��������", "ԥC");
	public static final CarSnCityType HA_D = new CarSnCityType("����ƽ��ɽ", "ԥD");
	public static final CarSnCityType HA_E = new CarSnCityType("���ϰ���", "ԥE");
	public static final CarSnCityType HA_F = new CarSnCityType("���Ϻױ�", "ԥF");
	public static final CarSnCityType HA_G = new CarSnCityType("��������", "ԥG");
	public static final CarSnCityType HA_H = new CarSnCityType("���Ͻ���", "ԥH");
	public static final CarSnCityType HA_J = new CarSnCityType("�������", "ԥJ");
	public static final CarSnCityType HA_K = new CarSnCityType("�������", "ԥK");
	public static final CarSnCityType HA_L = new CarSnCityType("�������", "ԥL");
	public static final CarSnCityType HA_M = new CarSnCityType("��������Ͽ", "ԥM");
	public static final CarSnCityType HA_N = new CarSnCityType("��������", "ԥN");
	public static final CarSnCityType HA_P = new CarSnCityType("�����ܿ�", "ԥP");
	public static final CarSnCityType HA_Q = new CarSnCityType("����פ���", "ԥQ");
	public static final CarSnCityType HA_R = new CarSnCityType("��������", "ԥR");
	public static final CarSnCityType HA_S = new CarSnCityType("��������", "ԥS");
	public static final CarSnCityType HA_U = new CarSnCityType("���ϼ�Դ", "ԥU");

	public static final CarSnCityType HB_A = new CarSnCityType("�����人", "��A");
	public static final CarSnCityType HB_B = new CarSnCityType("������ʯ", "��B");
	public static final CarSnCityType HB_C = new CarSnCityType("����ʮ��", "��C");
	public static final CarSnCityType HB_D = new CarSnCityType("��������", "��D");
	public static final CarSnCityType HB_E = new CarSnCityType("�����˲�", "��E");
	public static final CarSnCityType HB_F = new CarSnCityType("�����差", "��F");
	public static final CarSnCityType HB_G = new CarSnCityType("��������", "��G");
	public static final CarSnCityType HB_H = new CarSnCityType("��������", "��H");
	public static final CarSnCityType HB_J = new CarSnCityType("�����Ƹ�", "��J");
	public static final CarSnCityType HB_K = new CarSnCityType("����Т��", "��K");
	public static final CarSnCityType HB_L = new CarSnCityType("��������", "��L");
	public static final CarSnCityType HB_M = new CarSnCityType("��������", "��M");
	public static final CarSnCityType HB_N = new CarSnCityType("����Ǳ��", "��N");
	public static final CarSnCityType HB_P = new CarSnCityType("������ũ��", "��P");
	public static final CarSnCityType HB_Q = new CarSnCityType("������ʩ", "��Q");
	public static final CarSnCityType HB_R = new CarSnCityType("��������", "��R");
	public static final CarSnCityType HB_S = new CarSnCityType("��������", "��S");

	public static final CarSnCityType HN_A = new CarSnCityType("���ϳ�ɳ", "��A");
	public static final CarSnCityType HN_B = new CarSnCityType("��������", "��B");
	public static final CarSnCityType HN_C = new CarSnCityType("������̶", "��C");
	public static final CarSnCityType HN_D = new CarSnCityType("���Ϻ���", "��D");
	public static final CarSnCityType HN_E = new CarSnCityType("��������", "��E");
	public static final CarSnCityType HN_F = new CarSnCityType("��������", "��F");
	public static final CarSnCityType HN_G = new CarSnCityType("�����żҽ�", "��G");
	public static final CarSnCityType HN_H = new CarSnCityType("��������", "��H");
	public static final CarSnCityType HN_J = new CarSnCityType("���ϳ���", "��J");
	public static final CarSnCityType HN_K = new CarSnCityType("����¦��", "��K");
	public static final CarSnCityType HN_L = new CarSnCityType("���ϳ���", "��L");
	public static final CarSnCityType HN_M = new CarSnCityType("��������", "��M");
	public static final CarSnCityType HN_N = new CarSnCityType("���ϻ���", "��N");
	public static final CarSnCityType HN_U = new CarSnCityType("��������", "��U");

	public static final CarSnCityType GD_A = new CarSnCityType("�㶫����", "��A");
	public static final CarSnCityType GD_B = new CarSnCityType("�㶫����", "��B");
	public static final CarSnCityType GD_C = new CarSnCityType("�㶫�麣", "��C");
	public static final CarSnCityType GD_D = new CarSnCityType("�㶫��ͷ", "��D");
	public static final CarSnCityType GD_E = new CarSnCityType("�㶫��ɽ", "��E");
	public static final CarSnCityType GD_F = new CarSnCityType("�㶫�ع�", "��F");
	public static final CarSnCityType GD_G = new CarSnCityType("�㶫տ��", "��G");
	public static final CarSnCityType GD_H = new CarSnCityType("�㶫����", "��H");
	public static final CarSnCityType GD_J = new CarSnCityType("�㶫����", "��J");
	public static final CarSnCityType GD_K = new CarSnCityType("�㶫ï��", "��K");
	public static final CarSnCityType GD_L = new CarSnCityType("�㶫����", "��L");
	public static final CarSnCityType GD_M = new CarSnCityType("�㶫÷��", "��M");
	public static final CarSnCityType GD_N = new CarSnCityType("�㶫��β", "��N");
	public static final CarSnCityType GD_P = new CarSnCityType("�㶫��Դ", "��P");
	public static final CarSnCityType GD_Q = new CarSnCityType("�㶫����", "��Q");
	public static final CarSnCityType GD_R = new CarSnCityType("�㶫��Զ", "��R");
	public static final CarSnCityType GD_S = new CarSnCityType("�㶫��ݸ", "��S");
	public static final CarSnCityType GD_T = new CarSnCityType("�㶫��ɽ", "��T");
	public static final CarSnCityType GD_U = new CarSnCityType("�㶫����", "��U");
	public static final CarSnCityType GD_V = new CarSnCityType("�㶫����", "��V");
	public static final CarSnCityType GD_W = new CarSnCityType("�㶫�Ƹ�", "��W");
	public static final CarSnCityType GD_X = new CarSnCityType("�㶫��ɽ˳��", "��X");
	public static final CarSnCityType GD_Y = new CarSnCityType("�㶫��ɽ�Ϻ�", "��Y");
	public static final CarSnCityType GD_Z = new CarSnCityType("�۰ĵ���", "��Z");

	public static final CarSnCityType GX_A = new CarSnCityType("��������", "��A");
	public static final CarSnCityType GX_B = new CarSnCityType("��������", "��B");
	public static final CarSnCityType GX_C = new CarSnCityType("��������", "��C");
	public static final CarSnCityType GX_D = new CarSnCityType("��������", "��D");
	public static final CarSnCityType GX_E = new CarSnCityType("��������", "��E");
	public static final CarSnCityType GX_F = new CarSnCityType("��������", "��F");
	public static final CarSnCityType GX_G = new CarSnCityType("��������", "��G");
	public static final CarSnCityType GX_H = new CarSnCityType("��������", "��H");
	public static final CarSnCityType GX_J = new CarSnCityType("��������", "��J");
	public static final CarSnCityType GX_K = new CarSnCityType("��������", "��K");
	public static final CarSnCityType GX_L = new CarSnCityType("������ɫ", "��L");
	public static final CarSnCityType GX_M = new CarSnCityType("�����ӳ�", "��M");
	public static final CarSnCityType GX_N = new CarSnCityType("�������Ǹ�", "��N");
	public static final CarSnCityType GX_P = new CarSnCityType("��������", "��P");
	public static final CarSnCityType GX_R = new CarSnCityType("�������", "��R");

	public static final CarSnCityType HI_A = new CarSnCityType("���Ϻ���", "��A");
	public static final CarSnCityType HI_B = new CarSnCityType("��������", "��B");
	public static final CarSnCityType HI_C = new CarSnCityType("������", "��C");
	public static final CarSnCityType HI_D = new CarSnCityType("��������", "��D");
	public static final CarSnCityType HI_E = new CarSnCityType("��������", "��E");

	public static final CarSnCityType SC_A = new CarSnCityType("�Ĵ��ɶ�", "��A");
	public static final CarSnCityType SC_B = new CarSnCityType("�Ĵ�����", "��B");
	public static final CarSnCityType SC_C = new CarSnCityType("�Ĵ��Թ�", "��C");
	public static final CarSnCityType SC_D = new CarSnCityType("�Ĵ���֦��", "��D");
	public static final CarSnCityType SC_E = new CarSnCityType("�Ĵ�����", "��E");
	public static final CarSnCityType SC_F = new CarSnCityType("�Ĵ� ����", "��F");
	public static final CarSnCityType SC_G = new CarSnCityType("�Ĵ���Ԫ", "��H");
	public static final CarSnCityType SC_H = new CarSnCityType("�Ĵ�����", "��J");
	public static final CarSnCityType SC_J = new CarSnCityType("�Ĵ��ڽ�", "��K");
	public static final CarSnCityType SC_K = new CarSnCityType("�Ĵ���ɽ", "��L");
	public static final CarSnCityType SC_L = new CarSnCityType("�Ĵ�����", "��M");
	public static final CarSnCityType SC_M = new CarSnCityType("�Ĵ� �˱�", "��N");
	public static final CarSnCityType SC_N = new CarSnCityType("�Ĵ��ϳ�", "��R");
	public static final CarSnCityType SC_S = new CarSnCityType("�Ĵ�����", "��S");
	public static final CarSnCityType SC_T = new CarSnCityType("�Ĵ��Ű�", "��T");
	public static final CarSnCityType SC_U = new CarSnCityType("�Ĵ�����", "��U");
	public static final CarSnCityType SC_V = new CarSnCityType("�Ĵ�����", "��V");
	public static final CarSnCityType SC_W = new CarSnCityType("�Ĵ���ɽ", "��W");
	public static final CarSnCityType SC_X = new CarSnCityType("�Ĵ��㰲", "��X");
	public static final CarSnCityType SC_Y = new CarSnCityType("�Ĵ�����", "��Y");
	public static final CarSnCityType SC_Z = new CarSnCityType("�Ĵ�üɽ", "��Z");

	public static final CarSnCityType GZ_A = new CarSnCityType("���ݹ���", "��A");
	public static final CarSnCityType GZ_B = new CarSnCityType("��������ˮ", "��B");
	public static final CarSnCityType GZ_C = new CarSnCityType("��������", "��C");
	public static final CarSnCityType GZ_D = new CarSnCityType("����ͭ��", "��D");
	public static final CarSnCityType GZ_E = new CarSnCityType("����ǭ����", "��E");
	public static final CarSnCityType GZ_F = new CarSnCityType("���ݱϽ�", "��F");
	public static final CarSnCityType GZ_G = new CarSnCityType("���ݰ�˳", "��G");
	public static final CarSnCityType GZ_H = new CarSnCityType("����ǭ����", "��H");
	public static final CarSnCityType GZ_J = new CarSnCityType("����ǭ��", "��J");

	public static final CarSnCityType YN_A = new CarSnCityType("����", "��A");
	public static final CarSnCityType YN_B = new CarSnCityType("����", "��B");
	public static final CarSnCityType YN_C = new CarSnCityType("����", "��C");
	public static final CarSnCityType YN_D = new CarSnCityType("����", "��D");
	public static final CarSnCityType YN_E = new CarSnCityType("����", "��E");
	public static final CarSnCityType YN_F = new CarSnCityType("����", "��F");
	public static final CarSnCityType YN_G = new CarSnCityType("����", "��G");
	public static final CarSnCityType YN_H = new CarSnCityType("����", "��H");
	public static final CarSnCityType YN_J = new CarSnCityType("����", "��J");
	public static final CarSnCityType YN_K = new CarSnCityType("����", "��K");
	public static final CarSnCityType YN_L = new CarSnCityType("����", "��L");
	public static final CarSnCityType YN_M = new CarSnCityType("����", "��M");
	public static final CarSnCityType YN_N = new CarSnCityType("����", "��N");
	public static final CarSnCityType YN_P = new CarSnCityType("����", "��P");
	public static final CarSnCityType YN_Q = new CarSnCityType("����", "��Q");
	public static final CarSnCityType YN_R = new CarSnCityType("����", "��R");
	public static final CarSnCityType YN_S = new CarSnCityType("����", "��S");

	public static final CarSnCityType XZ_A = new CarSnCityType("��������", "��A");
	public static final CarSnCityType XZ_B = new CarSnCityType("���ز���", "��B");
	public static final CarSnCityType XZ_C = new CarSnCityType("����ɽ��", "��C");
	public static final CarSnCityType XZ_D = new CarSnCityType("�����տ���", "��D");
	public static final CarSnCityType XZ_E = new CarSnCityType("��������", "��E");
	public static final CarSnCityType XZ_F = new CarSnCityType("���ذ���", "��F");
	public static final CarSnCityType XZ_G = new CarSnCityType("������֥", "��G");
	public static final CarSnCityType XZ_H = new CarSnCityType("����פ�Ĵ�ʡ��ȫ��",
			"��H");
	public static final CarSnCityType XZ_J = new CarSnCityType("����פ�ຣʡ���ľ��",
			"��J");

	public static final CarSnCityType QH_A = new CarSnCityType("�ຣ����", "��A");
	public static final CarSnCityType QH_B = new CarSnCityType("�ຣ����", "��B");
	public static final CarSnCityType QH_C = new CarSnCityType("�ຣ����", "��C");
	public static final CarSnCityType QH_D = new CarSnCityType("�ຣ����", "��D");
	public static final CarSnCityType QH_E = new CarSnCityType("�ຣ����", "��E");
	public static final CarSnCityType QH_F = new CarSnCityType("�ຣ����", "��F");
	public static final CarSnCityType QH_G = new CarSnCityType("�ຣ����", "��G");
	public static final CarSnCityType QH_H = new CarSnCityType("�ຣ����", "��H");

	public static final CarSnCityType NX_A = new CarSnCityType("��������", "��A");
	public static final CarSnCityType NX_B = new CarSnCityType("����ʯ��ɽ", "��B");
	public static final CarSnCityType NX_C = new CarSnCityType("��������", "��C");
	public static final CarSnCityType NX_D = new CarSnCityType("���Ĺ�ԭ", "��D");

	public static final CarSnCityType XJ_A = new CarSnCityType("�½���³ľ��", "��A");
	public static final CarSnCityType XJ_B = new CarSnCityType("�½�����", "��B");
	public static final CarSnCityType XJ_C = new CarSnCityType("�½�ʯ����", "��C");
	public static final CarSnCityType XJ_D = new CarSnCityType("�½�����", "��D");
	public static final CarSnCityType XJ_E = new CarSnCityType("�½���������", "��E");
	public static final CarSnCityType XJ_F = new CarSnCityType("�½�����", "��F");
	public static final CarSnCityType XJ_G = new CarSnCityType("�½�����", "��G");
	public static final CarSnCityType XJ_H = new CarSnCityType("�½�����̩", "��H");
	public static final CarSnCityType XJ_J = new CarSnCityType("�½���������", "��J");
	public static final CarSnCityType XJ_K = new CarSnCityType("�½���³��", "��K");
	public static final CarSnCityType XJ_L = new CarSnCityType("�½�����", "��L");
	public static final CarSnCityType XJ_M = new CarSnCityType("�½���������", "��M");
	public static final CarSnCityType XJ_N = new CarSnCityType("�½�������", "��N");
	public static final CarSnCityType XJ_P = new CarSnCityType("�½��������տ¶�����",
			"��P");
	public static final CarSnCityType XJ_Q = new CarSnCityType("�½���ʲ", "��Q");
	public static final CarSnCityType XJ_R = new CarSnCityType("�½�����", "��R");

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
