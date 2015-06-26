<%@ page language="java"  pageEncoding="utf-8"%>
<script type="text/javascript">
	function goback()
	{
	    var nBacklevel=certificateForm.gobacklevel.value;
		var backlevel = -1*nBacklevel;
		history.go(backlevel);
	}	
</script>
<input name="gobackbutton"  type="button" value="返回" onclick="goback()"/>

