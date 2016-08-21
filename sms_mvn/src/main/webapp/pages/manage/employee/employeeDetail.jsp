<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/layer/layer.js"/>
		<script type="text/javascript">
			function showBigImag(){
				layer.open({
				    type: 1,
				    title: false,
				    closeBtn: false,
				    area: ['auto','auto'],
//				    border: [0],
				    skin: 'layui-layer-nobg', //没有背景色
				    shadeClose: true,
				    move:'#img1',
				    moveOut:true,
				    closeBtn:1,
				    content: $('#img1')
				});
			}
		</script> 
	</head>
	<body>
		<f:msg styleClass="msg"/>
		<div class="userbox" >
			<table class="detail_grid" border="1">
				<caption></caption>
				<tr>
					<td width="20%">姓名</td>
					<td width="30%">${name}</td>
					<td width="20%">性别</td>
					<td width="30%"><f:type className="SexType" value="${gender}"/></td>
				</tr>
				<tr>
					<td>别名</td>
					<td>${alias}</td>
					<td>所属机构</td>
					<td>${branchName}</td>
				</tr>
				<tr>
					<td>入职日期</td>
					<td>${hireDate}</td>
					<td>职位</td>
					<td>${positionName}</td>
				</tr>
				<tr>
					<td>证件号码</td>
					<td>${idCard}</td>
					<td>出生日期</td>
					<td>${birthday}</td>
				</tr>
				<tr>
					<td>联系电话</td>
					<td>${tel}</td>
					<td>备用电话</td>
					<td>${spareTel}</td>
				</tr>
				<tr>
					<td>家庭住址</td>
					<td>${nativePlace}</td>
					<td>现居住地址</td>
					<td>${livePlace}</td>
				</tr>
				<tr>
					<td>紧急联系人</td>
					<td>${instancyPerson}</td>
					<td>紧急联系人电话</td>
					<td>${instancyTel}</td>
				</tr>
				<tr>
					<td>学历</td>
					<td><f:type className="DegreeType" value="${degree }"/></td>
					<td>毕业院校/专业</td>
					<td>${school }${major }</td>
				</tr>
				<tr>
					<td>邮箱</td>
					<td>${email}</td>
					<td>QQ</td>
					<td>${qq}</td>
				</tr>
				<tr>
					<td>微博</td>
					<td>${weibo}</td>
					<td>兴趣爱好</td>
					<td>${hobby}</td>
				</tr>
				<tr>
					<td>创建时间</td>
					<td colspan="3"><fmt:formatDate value="${createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				</tr>
				<tr>
					<td>简评</td>
					<td colspan="3"> 
						${review}
					</td>
				</tr>
				<tr>
					<td>最后更新时间</td>
					<td colspan="3"><fmt:formatDate value="${createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				</tr>
				<tr>
					<c:if test="${transferSize > 1}">
						<td>异动记录</td>
					</c:if>
					<c:if test="${transferSize <= 1}">
						<td>异动记录</td>
					</c:if>
					<td colspan="3" rowspan="3">
						<c:forEach items="${transfers}" var="element">
						${element.transferDetail }
							<c:if test="${element.reason != null}">原因:${element.reason }</c:if><br>
						</c:forEach>
					</td>
				</tr>
			</table>
		</div>
		<!-- 
		<img src="/upload/${poster}" id="img1" style="display:none"/> -->
		 
		<p align="center">
			<input type="button" onclick="history.go(-1);" class="inp_L3" value="返回"/>
		</p>
	</body>
</html>
