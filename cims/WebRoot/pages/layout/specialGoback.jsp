<%@ page language="java"  pageEncoding="GBK"%>
<script type="text/javascript">
	function goback()
	{
	    var nBacklevel=certificateForm.gobacklevel.value;
		var backlevel = -1*nBacklevel;
		history.go(backlevel)
	}	
</script>
<input name="gobackbutton"  type="button" value="·µ»Ø" onclick="goback()"/>

