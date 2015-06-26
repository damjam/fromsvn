package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
/**
 * 
 *	�ӿڴ�����
 */
public class InvestErrCodeType extends AbstractType {
	public static Map<String, InvestErrCodeType> ALL = new LinkedHashMap<String, InvestErrCodeType>();

	//����������
	public static final InvestErrCodeType DT0000 = new InvestErrCodeType("���׳ɹ�", "DT0000");
	public static final InvestErrCodeType DT0002 = new InvestErrCodeType("�����쳣��ʱ�����Ժ�����", "DT0002");
	public static final InvestErrCodeType DT0003 = new InvestErrCodeType("��¼������", "DT0003");
	public static final InvestErrCodeType DT0004 = new InvestErrCodeType("��ѯ�����������̫�������̲�ѯ��ʱ���", "DT0004");
	public static final InvestErrCodeType DT0016 = new InvestErrCodeType("��ȡϵͳ״̬ʧ��", "DT0016");
	public static final InvestErrCodeType DT0017 = new InvestErrCodeType("�޷�ȡ�ý�����ϵͳ״̬��Ϣ", "DT0017");
	public static final InvestErrCodeType DT0018 = new InvestErrCodeType("��ȡ��������ʧ��", "DT0018");
	public static final InvestErrCodeType DT9997 = new InvestErrCodeType("�����������ĳ�ʱ", "DT9997");
	public static final InvestErrCodeType DT9999 = new InvestErrCodeType("�����쳣��ʱ�����Ժ�����", "DT9999");
	
	//�˻���ش����� DT1XXXX
	public static final InvestErrCodeType DT1001 = new InvestErrCodeType("�����˺�δǩԼ", "DT1001");
	public static final InvestErrCodeType DT1004 = new InvestErrCodeType("�����������Ϊ��", "DT1004");
	public static final InvestErrCodeType DT1005 = new InvestErrCodeType("�ͻ�������Ϊ��", "DT1005");
	public static final InvestErrCodeType DT1008 = new InvestErrCodeType("�ͻ�������Ϊ��", "DT1008");
	public static final InvestErrCodeType DT1014 = new InvestErrCodeType("���п��Ų���Ϊ��", "DT1014");
	public static final InvestErrCodeType DT1015 = new InvestErrCodeType("��Ͷģʽ����Ϊ��", "DT1015");
	public static final InvestErrCodeType DT1016 = new InvestErrCodeType("�����˻��Ѵ���", "DT1016");
	public static final InvestErrCodeType DT1017 = new InvestErrCodeType("��Ͷģʽ����ȷ", "DT1017");
	public static final InvestErrCodeType DT1018 = new InvestErrCodeType("���Ų�����", "DT1018");
	public static final InvestErrCodeType DT1019 = new InvestErrCodeType("�������˺��ڶ�Ͷϵͳ���ڶ���û�", "DT1019");
	public static final InvestErrCodeType DT1022 = new InvestErrCodeType("�ͻ���Ʋ���Ϊ��", "DT1022");
	public static final InvestErrCodeType DT1024 = new InvestErrCodeType("֤�����벻��Ϊ��", "DT1024");
	public static final InvestErrCodeType DT1028 = new InvestErrCodeType("���˻��Ѷ���", "DT1028");
	public static final InvestErrCodeType DT1030 = new InvestErrCodeType("���˻��ѹ�ʧ", "DT1030");
	public static final InvestErrCodeType DT1032 = new InvestErrCodeType("���˻��ѽ�Լ", "DT1032");
	public static final InvestErrCodeType DT1035 = new InvestErrCodeType("�����н���ί��,������Ԥ����", "DT1035");
	public static final InvestErrCodeType DT1036 = new InvestErrCodeType("�п�棬�������Լ", "DT1036");
	public static final InvestErrCodeType DT1039 = new InvestErrCodeType("��δ����Ĳִ��ѣ��������Լ", "DT1039");
	public static final InvestErrCodeType DT1046 = new InvestErrCodeType("��Ч�Ĵ������", "DT1046");
	public static final InvestErrCodeType DT1050 = new InvestErrCodeType("���˻�״̬������", "DT1050");
	public static final InvestErrCodeType DT1056 = new InvestErrCodeType("�¿�����ԭ����֤�����Ͳ���", "DT1056");
	public static final InvestErrCodeType DT1057 = new InvestErrCodeType("��Ч�Ŀͻ�", "DT1057");
	public static final InvestErrCodeType DT1059 = new InvestErrCodeType("�ͻ�δ����", "DT1059");
	public static final InvestErrCodeType DT1062 = new InvestErrCodeType("�Ƿ�˳�Ӳ���Ϊ��", "DT1062");
	public static final InvestErrCodeType DT1063 = new InvestErrCodeType("�Ƿ�ۿ���һԪ����Ϊ��", "DT1063");
	public static final InvestErrCodeType DT1064 = new InvestErrCodeType("�ͻ��������Ͳ���Ϊ��", "DT1064");
	public static final InvestErrCodeType DT1065 = new InvestErrCodeType("�����˺�״̬������", "DT1065");
	public static final InvestErrCodeType DT1066 = new InvestErrCodeType("δ���ù�����ͻ�����", "DT1066");
	public static final InvestErrCodeType DT1067 = new InvestErrCodeType("δ������ͨ���ͻ�����", "DT1067");
	public static final InvestErrCodeType DT1068 = new InvestErrCodeType("��ǰ�ͻ�״̬�������޸���Ϣ", "DT1068");
	public static final InvestErrCodeType DT1069 = new InvestErrCodeType("��Ƿ��ܽ�Լ", "DT1069");
	public static final InvestErrCodeType DT1070 = new InvestErrCodeType("�ж�Ͷ�ƻ����ܽ�Լ", "DT1070");
	public static final InvestErrCodeType DT1071 = new InvestErrCodeType("���������ͻ�����鲻����Ӧ�Ŀͻ�����", "DT1071");
	public static final InvestErrCodeType DT1072 = new InvestErrCodeType("����������û������", "DT1072");
	public static final InvestErrCodeType DT1073 = new InvestErrCodeType("��Ч���ʻ�����", "DT1073");
	public static final InvestErrCodeType DT1074 = new InvestErrCodeType("�ؿ���ԭ֤�����벻��ȷ", "DT1074");
	public static final InvestErrCodeType DT1075 = new InvestErrCodeType("�ؿ���ԭ֤�����Ͳ���ȷ", "DT1075");
	public static final InvestErrCodeType DT1076 = new InvestErrCodeType("�ؿ���ԭ�ʻ����Ͳ���ȷ", "DT1076");
	public static final InvestErrCodeType DT1077 = new InvestErrCodeType("�û�������ֱ�ӿ���", "DT1077");
	public static final InvestErrCodeType DT1078 = new InvestErrCodeType("����֤�����Ͳ���ȷ", "DT1078");
	public static final InvestErrCodeType DT1079 = new InvestErrCodeType("���������Ѳ���ȷ", "DT1079");
	public static final InvestErrCodeType DT1080 = new InvestErrCodeType("����֤�����Ͳ���ȷ", "DT1080");
	public static final InvestErrCodeType DT1084 = new InvestErrCodeType("�ֻ��Ų���Ϊ��", "DT1084");
	public static final InvestErrCodeType DT1086 = new InvestErrCodeType("֤�����벻��ȷ", "DT1086");
	public static final InvestErrCodeType DT1111 = new InvestErrCodeType("����ǩԼ�ͻ�������������", "DT1111");
	public static final InvestErrCodeType DT1117 = new InvestErrCodeType("�������벻��Ϊ��", "DT1117");
	public static final InvestErrCodeType DT1122 = new InvestErrCodeType("ȡ�÷ֶμ�Ϣ����ʱ�����������ô���", "DT1122");
	public static final InvestErrCodeType DT1130 = new InvestErrCodeType("֤����������������", "DT1130");
	public static final InvestErrCodeType DT1131 = new InvestErrCodeType("֤����������������", "DT1131");
	public static final InvestErrCodeType DT1132 = new InvestErrCodeType("�û�������������", "DT1132");
	public static final InvestErrCodeType DT1133 = new InvestErrCodeType("���˺�֤����������˺Ų���", "DT1133");
	public static final InvestErrCodeType DT1134 = new InvestErrCodeType("���˺�֤����������˺Ų���", "DT1134");
	public static final InvestErrCodeType DT1135 = new InvestErrCodeType("���˺��û�������˺Ų���", "DT1135");
	public static final InvestErrCodeType DT1136 = new InvestErrCodeType("�ͻ��˻���������״̬����������", "DT1136");
	public static final InvestErrCodeType DT1138 = new InvestErrCodeType("�ͻ�����Ų�����֧�����ڷ���", "DT1138");
	public static final InvestErrCodeType DT1139 = new InvestErrCodeType("���˺�֤����������������", "DT1139");
	public static final InvestErrCodeType DT1140 = new InvestErrCodeType("���˺�֤����������������", "DT1140");
	public static final InvestErrCodeType DT1141 = new InvestErrCodeType("��Ч��֤������", "DT1141");
	public static final InvestErrCodeType DT1142 = new InvestErrCodeType("��������������ظ�", "DT1142");
	public static final InvestErrCodeType DT1143 = new InvestErrCodeType("�¿ͻ�������˻�������ͻ����˻����Ͳ�һ��", "DT1143");
	public static final InvestErrCodeType DT1144 = new InvestErrCodeType("SASB�ʻ����������ڸ��˿���", "DT1144");
	public static final InvestErrCodeType DT1145 = new InvestErrCodeType("�ǶԹ��˻����������ڷ��˿���", "DT1145");
	public static final InvestErrCodeType DT1146 = new InvestErrCodeType("���˿ͻ������ڿ�����������", "DT1146");
	public static final InvestErrCodeType DT1147 = new InvestErrCodeType("����ʱ�䲻��������", "DT1147");
	public static final InvestErrCodeType DT1148 = new InvestErrCodeType("��Ϣ�����ѳɹ��������������ʧ�ܣ������³��Ի���ϵϵͳ����Ա", "DT1148");
	public static final InvestErrCodeType DT1149 = new InvestErrCodeType("�ͻ���������ʧ��", "DT1149");
	public static final InvestErrCodeType DT1150 = new InvestErrCodeType("ɾ���������˺���Ϣʧ��", "DT1150");
	public static final InvestErrCodeType DT1151 = new InvestErrCodeType("�������˺���Ϣ������ʷ��ʧ��", "DT1151");
	public static final InvestErrCodeType DT1152 = new InvestErrCodeType("��Ͷ�ƻ������ظ��趨", "DT1152");
	public static final InvestErrCodeType DT1153 = new InvestErrCodeType("��Ͷ�����Ϊ��", "DT1153");
	public static final InvestErrCodeType DT1154 = new InvestErrCodeType("��Ͷģʽ����Ϊ��", "DT1154");
	public static final InvestErrCodeType DT1155 = new InvestErrCodeType("��Ͷ���/�����������0", "DT1155");
	public static final InvestErrCodeType DT1156 = new InvestErrCodeType("��Ͷ���ڲ���Ϊ��", "DT1156");
	public static final InvestErrCodeType DT1157 = new InvestErrCodeType("������������Ϊ��", "DT1157");
	public static final InvestErrCodeType DT1158 = new InvestErrCodeType("������������", "DT1158");
	public static final InvestErrCodeType DT1159 = new InvestErrCodeType("����ֵ����Ϊ��", "DT1159");
	public static final InvestErrCodeType DT1160 = new InvestErrCodeType("����Ķ�Ͷ����", "DT1160");
	public static final InvestErrCodeType DT1161 = new InvestErrCodeType("�������ڷǷ�", "DT1161");
	public static final InvestErrCodeType DT1162 = new InvestErrCodeType("�������ڲ�����", "DT1162");
	public static final InvestErrCodeType DT1163 = new InvestErrCodeType("�������ڲ���С���״�Ͷ������", "DT1163");
	public static final InvestErrCodeType DT1164 = new InvestErrCodeType("���˻�״̬Ϊ����״̬", "DT1164");
	public static final InvestErrCodeType DT1165 = new InvestErrCodeType("����Ķ�Ͷ����", "DT1165");
	public static final InvestErrCodeType DT1166 = new InvestErrCodeType("����Ķ�Ͷģʽ", "DT1166");
	public static final InvestErrCodeType DT1167 = new InvestErrCodeType("������״�Ͷ������", "DT1167");
	public static final InvestErrCodeType DT1168 = new InvestErrCodeType("�������������", "DT1168");
	public static final InvestErrCodeType DT1169 = new InvestErrCodeType("�����޸���ֹ�Ķ�Ͷ�ƻ�", "DT1169");
	public static final InvestErrCodeType DT1170 = new InvestErrCodeType("������ˮ�Ų���Ϊ��", "DT1170");
	public static final InvestErrCodeType DT1171 = new InvestErrCodeType("�ö�Ͷ�ƻ��ѳ���", "DT1171");
	public static final InvestErrCodeType DT1172 = new InvestErrCodeType("�������ʺŲ�����", "DT1172");
	public static final InvestErrCodeType DT1173 = new InvestErrCodeType("�������ʺ�״̬������", "DT1173");
	public static final InvestErrCodeType DT1174 = new InvestErrCodeType("��Ͷ������������СͶ�ʶ�", "DT1174");
	public static final InvestErrCodeType DT1175 = new InvestErrCodeType("����������������СͶ�ʶ�", "DT1175");
	public static final InvestErrCodeType DT1176 = new InvestErrCodeType("û�����ö�Ͷ��СͶ�ʶ�", "DT1176");
	public static final InvestErrCodeType DT1177 = new InvestErrCodeType("û�����ý�����СͶ�ʶ�", "DT1177");
	public static final InvestErrCodeType DT1178 = new InvestErrCodeType("û�����ö�ͶͶ�ʶ��", "DT1178");
	public static final InvestErrCodeType DT1179 = new InvestErrCodeType("û�����ý���Ͷ�ʶ��", "DT1179");
	public static final InvestErrCodeType DT1180 = new InvestErrCodeType("��Ͷ�������Ͷ�ʶ��Ҫ��", "DT1180");
	public static final InvestErrCodeType DT1181 = new InvestErrCodeType("�����������Ͷ�ʶ��Ҫ��", "DT1181");
	public static final InvestErrCodeType DT1182 = new InvestErrCodeType("���˻��ѿ�ͨ�ƽ�Ͷҵ����Ը�ҵ���Լ��������", "DT1182");
	public static final InvestErrCodeType DT1183 = new InvestErrCodeType("���˻��޿�ͨ�ƽ�Ͷҵ��", "DT1183");
	public static final InvestErrCodeType DT1184 = new InvestErrCodeType("��֤���������֤������һ��", "DT1184");
	
	//�ʽ���ش����� DT2XXXX
	public static final InvestErrCodeType DT2167 = new InvestErrCodeType("�ʺ����Ͳ���Ϊ��", "DT2167");
	public static final InvestErrCodeType DT2168 = new InvestErrCodeType("�ʺ����벻��Ϊ��", "DT2168");
	public static final InvestErrCodeType DT2169 = new InvestErrCodeType("����ȷ���˺�����", "DT2169");
	public static final InvestErrCodeType DT2170 = new InvestErrCodeType("���˺Ų�����Ч�������˺�", "DT2170");
	public static final InvestErrCodeType DT2171 = new InvestErrCodeType("��������ʱ����ȷ���ʽ�״�����������ʣ�����ϵ����Ӫҵ����", "DT2171");
	
	//�ִ���ش����� DT3XXXX
	public static final InvestErrCodeType DT3002 = new InvestErrCodeType("����ʵ���治��", "DT3002");
	public static final InvestErrCodeType DT3015 = new InvestErrCodeType("�����쳣��ʱ�����Ժ�����", "DT3015");
	public static final InvestErrCodeType DT3016 = new InvestErrCodeType("�����������쳣", "DT3016");
	public static final InvestErrCodeType DT3101 = new InvestErrCodeType("���Ʒ�ֲ���Ϊ��", "DT3101");
	public static final InvestErrCodeType DT3107 = new InvestErrCodeType("�����֤�����벻��Ϊ��", "DT3107");
	public static final InvestErrCodeType DT3108 = new InvestErrCodeType("������벻��Ϊ��", "DT3108");
	public static final InvestErrCodeType DT3109 = new InvestErrCodeType("��Ʒ�ֲ�֧�����", "DT3109");
	public static final InvestErrCodeType DT3110 = new InvestErrCodeType("������в�����", "DT3110");
	public static final InvestErrCodeType DT3111 = new InvestErrCodeType("������㲻����", "DT3111");
	public static final InvestErrCodeType DT3112 = new InvestErrCodeType("δ���ø�Ʒ�ֵĶ�Ͷ���", "DT3112");
	public static final InvestErrCodeType DT3113 = new InvestErrCodeType("�����Ϣ������", "DT3113");
	public static final InvestErrCodeType DT3115 = new InvestErrCodeType("���������ˮ�Ų�����", "DT3115");
	public static final InvestErrCodeType DT3126 = new InvestErrCodeType("��ǰϵͳ״̬�����������", "DT3126");
	public static final InvestErrCodeType DT3135 = new InvestErrCodeType("����������������غͲ���Ҫ��", "DT3135");
	
	
	//������ش����� DT4XXXX
	public static final InvestErrCodeType DT4005 = new InvestErrCodeType("ί�е��Ų���Ϊ��", "DT4005");
	public static final InvestErrCodeType DT4011 = new InvestErrCodeType("ί�������Ƿ�", "DT4011");
	public static final InvestErrCodeType DT4012 = new InvestErrCodeType("��δ��ͨ�ý���Ʒ��", "DT4012");
	public static final InvestErrCodeType DT4015 = new InvestErrCodeType("�����Ų�����", "DT4015");
	public static final InvestErrCodeType DT4111 = new InvestErrCodeType("���ÿ�治��", "DT4111");
	public static final InvestErrCodeType DT4112 = new InvestErrCodeType("���¿���ʧ��", "DT4112");
	public static final InvestErrCodeType DT4125 = new InvestErrCodeType("�����������־", "DT4125");
	public static final InvestErrCodeType DT4126 = new InvestErrCodeType("ֻ�ܰ���������", "DT4126");
	public static final InvestErrCodeType DT4127 = new InvestErrCodeType("������־����Ϊ��", "DT4127");
	public static final InvestErrCodeType DT4128 = new InvestErrCodeType("�޷�ȡ�ý�����ϵͳ״̬��Ϣ", "DT4128");
	public static final InvestErrCodeType DT4129 = new InvestErrCodeType("�ʽ�鼯�л����ʽ�鼯����ɣ����ܳ���", "DT4129");
	public static final InvestErrCodeType DT4130 = new InvestErrCodeType("���ҵ����δ��ͨ", "DT4130");
	public static final InvestErrCodeType DT4131 = new InvestErrCodeType("�ƻ�����ղ���Ϊ��", "DT4131");
	public static final InvestErrCodeType DT4132 = new InvestErrCodeType("������в���Ϊ��", "DT4132");
	public static final InvestErrCodeType DT4133 = new InvestErrCodeType("������㲻��Ϊ��", "DT4133");
	public static final InvestErrCodeType DT4134 = new InvestErrCodeType("�Ƿ�����������Ϊ��", "DT4134");
	public static final InvestErrCodeType DT4135 = new InvestErrCodeType("������ڴ���", "DT4135");
	public static final InvestErrCodeType DT4136 = new InvestErrCodeType("�������ڲ���Ϊ��", "DT4136");
	public static final InvestErrCodeType DT4137 = new InvestErrCodeType("�ʽ�鼯�л����ʽ�鼯����ɣ����ܳ����������", "DT4137");
	public static final InvestErrCodeType DT4138 = new InvestErrCodeType("�ƻ�����ղ���Ϊ��", "DT4138");
	public static final InvestErrCodeType DT4139 = new InvestErrCodeType("ԭ�����ˮ�Ų���Ϊ��", "DT4139");
	public static final InvestErrCodeType DT4140 = new InvestErrCodeType("ԭ�������ڲ���Ϊ��", "DT4140");
	public static final InvestErrCodeType DT4141 = new InvestErrCodeType("�������ڲ�����", "DT4141");
	public static final InvestErrCodeType DT4142 = new InvestErrCodeType("��Ͷҵ����δ��ͨ", "DT4142");
	public static final InvestErrCodeType DT4143 = new InvestErrCodeType("����ί��ҵ����δ��ͨ", "DT4143");
	public static final InvestErrCodeType DT4144 = new InvestErrCodeType("ί����ҵ����δ��ͨ", "DT4144");
	public static final InvestErrCodeType DT4145 = new InvestErrCodeType("ί����ҵ����δ��ͨ", "DT4145");
	public static final InvestErrCodeType DT4146 = new InvestErrCodeType("������������Ѵ�����", "DT4146");
	public static final InvestErrCodeType DT4147 = new InvestErrCodeType("�����������Ѵ�����", "DT4147");
	public static final InvestErrCodeType DT4148 = new InvestErrCodeType("������������Ѵ�����", "DT4148");
	
	
	//ϵͳ��ش����� DT5XXXX
	public static final InvestErrCodeType DT5001 = new InvestErrCodeType("ϵͳ�������������в��������", "DT5001");
	public static final InvestErrCodeType DT5014 = new InvestErrCodeType("�����кŲ�����", "DT5014");
	
	//���ݿ���ش����� DT6XXXX
	public static final InvestErrCodeType DT6002 = new InvestErrCodeType("���ݿ⴦���", "DT6002");
	
	//�����쳣��ش����� DT7XXXX
	public static final InvestErrCodeType DT7001 = new InvestErrCodeType("ͨѶ�쳣", "DT7001");
	
	
	//��ѯ��ش����� DT8XXXX
	public static final InvestErrCodeType DT8001 = new InvestErrCodeType("��ʼ���ڲ��ܴ��ڽ�������", "DT8001");
	public static final InvestErrCodeType DT8006 = new InvestErrCodeType("��ǰҳ�벻��Ϊ��", "DT8006");
	public static final InvestErrCodeType DT8007 = new InvestErrCodeType("ÿҳ��¼������Ϊ��", "DT8007");
	public static final InvestErrCodeType DT8008 = new InvestErrCodeType("��ѯ��������", "DT8008");
	public static final InvestErrCodeType DT8009 = new InvestErrCodeType("���д��벻��Ϊ��", "DT8009");
	public static final InvestErrCodeType DT8010 = new InvestErrCodeType("����·ݲ���Ϊ��", "DT8010");
	public static final InvestErrCodeType DT8011 = new InvestErrCodeType("��ˮ�Ų���Ϊ��", "DT8011");
	public static final InvestErrCodeType DT8012 = new InvestErrCodeType("ί�����Ͳ���Ϊ��", "DT8012");
	

	protected InvestErrCodeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static InvestErrCodeType valueOf(String value) {

		InvestErrCodeType type = ALL.get(value);
		
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("investErrCodes", InvestErrCodeType.ALL.values());
	}
}
