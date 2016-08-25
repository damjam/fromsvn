<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
	    <link href="https://cdn.bootcss.com/jqueryui/1.11.3/jquery-ui.css" rel="stylesheet">
		<f:css href="/macadmin/css/style.css"/>
		<f:css href="/macadmin/css/widgets.css"/>
		<f:css href="/macadmin/css/jquery.onoff.css"/>
		<f:css href="/macadmin/css/jquery.cleditor.css"/>
		<f:css href="/macadmin/css/jquery.dataTables.css"/>
		
		<f:css href="/macadmin/css/bootstrap-datetimepicker.min.css"/>
		<f:css href="/macadmin/css/fullcalendar.css"/>
		<f:css href="/macadmin/css/prettyPhoto.css"/>
		<script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.js"></script>
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script src="https://cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.js"></script>
		<f:js src="/js/common.js"/>
		<f:js src="/macadmin/js/jquery.dataTables.min.js"/>
		<script type="text/javascript">
			
		</script>
		<style type="text/css">
		.form-inline div{
			margin-bottom:10px;
			margin-left:0px;
			margin-right:0px;
			
		}
		.nomargin {
			margin-left:0px;
			margin-right:0px;
		}
		</style>
	</head>

	<body>
		<div class="mainbar">
		<div class="page-head">
	      <h2 class="pull-left"><i class="fa fa-home"></i> 欢迎使用</h2>

        <!-- Breadcrumb -->
        <div class="bread-crumb pull-right">
          <a href="index.html"><i class="fa fa-home"></i> Home</a> 
          <!-- Divider -->
          <span class="divider">/</span> 
          <a href="index.html#" class="bread-current">Dashboard</a>
        </div>

        <div class="clearfix"></div>

	    </div>
	    <div class="matter">
        <div class="container">
			<div class="row">

            <div class="col-md-12">


              <div class="widget wgreen">
                
                <div class="widget-head">
                  <div class="pull-left">Forms</div>
                  <div class="widget-icons pull-right">
                    <a href="#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>
                  <div class="clearfix"></div>
                </div>

                <div class="widget-content">
                  <div class="padd">

                    <!-- Form starts.  -->
                     <form class="form-horizontal form-inline" role="form">
                             
                             <div class="form-group" style="margin:0;width: 33%">
                               <label class="control-label col-md-4">Input Box</label>
                               <div class="col-md-8">
                                <input type="text" class="form-control input-group" placeholder="Input Box">
                             	</div>
                             </div>
                             <div class="form-group" style="margin:0;width: 33%">
                               <label class="control-label col-md-4">Input Box</label>
                               <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="Input Box">
                             	</div>
                             </div>
                             <div class="form-group" style="margin:0;width: 33%">
                               <label class="control-label col-md-4">Input Box</label>
                               <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="Input Box">
                             	</div>
                             </div>
                              <div class="form-group" style="margin:0;width: 33%">
                               <label class="control-label col-md-4">Input Box</label>
                               <div class="col-md-8">
                                <input type="text" class="form-control input-group" placeholder="Input Box">
                             	</div>
                             </div>
                             <div class="form-group" style="margin:0;width: 33%">
                               <label class="control-label col-md-4">Input Box</label>
                               <div class="col-md-8">
                                <input type="text" class="form-control" placeholder="Input Box">
                             	</div>
                             </div>
                             <div class="form-group" style="margin:0;width: 33%">
                               <label class="control-label col-md-4" style="width: 33%">Input Box</label>
                               <div class="col-md-8" style="width:67%">
                                <input type="text" class="form-control" placeholder="Input Box" style="width: 48%;">-
                                <input type="text" class="form-control" placeholder="Input Box" style="width: 48%;">
                             	</div>
                             </div>
                             <div class="form-group" style="margin:0;width: 33%">
                             	<div style="width: 33%;float: left"></div>
                             	<div class="col-md-offset-4 col-md-8" style="width:67%">
                             		<button type="button" class="btn btn-sm btn-primary">查询</button>
                             		<button type="button" class="btn btn-sm btn-primary">导入</button>
                             		<button type="button" class="btn btn-sm btn-primary">导出</button>
                             	</div>
                             </div>
                             <div class="page-tables">
								<!-- Table -->
								<div class="table-responsive">
									<div id="data-table-1_wrapper" class="dataTables_wrapper"><div class="dataTables_length" id="data-table-1_length"><label>Show <select name="data-table-1_length" aria-controls="data-table-1" class=""><option value="10">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> entries</label></div><div id="data-table-1_filter" class="dataTables_filter"><label>Search:<input type="search" class="" placeholder="" aria-controls="data-table-1"></label></div><table cellpadding="0" cellspacing="0" border="0" id="data-table-1" width="100%" class="dataTable" role="grid" aria-describedby="data-table-1_info" style="width: 100%;">
										<thead>
											<tr role="row"><th class="sorting_desc" tabindex="0" aria-controls="data-table-1" rowspan="1" colspan="1" aria-label="Rendering engine: activate to sort column ascending" style="width: 161px;" aria-sort="descending">Rendering engine</th><th class="sorting" tabindex="0" aria-controls="data-table-1" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending" style="width: 241px;">Browser</th><th class="sorting" tabindex="0" aria-controls="data-table-1" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending" style="width: 220px;">Platform(s)</th><th class="sorting" tabindex="0" aria-controls="data-table-1" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 135px;">Engine version</th><th class="sorting" tabindex="0" aria-controls="data-table-1" rowspan="1" colspan="1" aria-label="CSS grade: activate to sort column ascending" style="width: 103px;">CSS grade</th></tr>
										</thead>
										<tbody>
										<tr role="row" class="odd">
												<td class="sorting_1">Webkit</td>
												<td>Safari 1.2</td>
												<td>OSX.3</td>
												<td>125.5</td>
												<td>A</td>
											</tr><tr role="row" class="even">
												<td class="sorting_1">Webkit</td>
												<td>Safari 1.3</td>
												<td>OSX.3</td>
												<td>312.8</td>
												<td>A</td>
											</tr><tr role="row" class="odd">
												<td class="sorting_1">Webkit</td>
												<td>Safari 2.0</td>
												<td>OSX.4+</td>
												<td>419.3</td>
												<td>A</td>
											</tr><tr role="row" class="even">
												<td class="sorting_1">Webkit</td>
												<td>Safari 3.0</td>
												<td>OSX.4+</td>
												<td>522.1</td>
												<td>A</td>
											</tr><tr role="row" class="odd">
												<td class="sorting_1">Webkit</td>
												<td>OmniWeb 5.5</td>
												<td>OSX.4+</td>
												<td>420</td>
												<td>A</td>
											</tr><tr role="row" class="even">
												<td class="sorting_1">Webkit</td>
												<td>iPod Touch / iPhone</td>
												<td>iPod</td>
												<td>420.1</td>
												<td>A</td>
											</tr><tr role="row" class="odd">
												<td class="sorting_1">Webkit</td>
												<td>S60</td>
												<td>S60</td>
												<td>413</td>
												<td>A</td>
											</tr><tr role="row" class="even">
												<td class="sorting_1">Trident</td>
												<td>Internet Explorer 4.0</td>
												<td>Win 95+</td>
												<td> 4</td>
												<td>X</td>
											</tr><tr role="row" class="odd">
												<td class="sorting_1">Trident</td>
												<td>Internet Explorer 5.0</td>
												<td>Win 95+</td>
												<td>5</td>
												<td>C</td>
											</tr><tr role="row" class="even">
												<td class="sorting_1">Trident</td>
												<td>Internet Explorer 5.5</td>
												<td>Win 95+</td>
												<td>5.5</td>
												<td>A</td>
											</tr></tbody>
										<tfoot>
											<tr><th rowspan="1" colspan="1">Rendering engine</th><th rowspan="1" colspan="1">Browser</th><th rowspan="1" colspan="1">Platform(s)</th><th rowspan="1" colspan="1">Engine version</th><th rowspan="1" colspan="1">CSS grade</th></tr>
										</tfoot>
									</table><div class="dataTables_info" id="data-table-1_info" role="status" aria-live="polite">Showing 1 to 10 of 57 entries</div><div class="dataTables_paginate paging_full_numbers" id="data-table-1_paginate"><a class="paginate_button first disabled" aria-controls="data-table-1" data-dt-idx="0" tabindex="0" id="data-table-1_first">First</a><a class="paginate_button previous disabled" aria-controls="data-table-1" data-dt-idx="1" tabindex="0" id="data-table-1_previous">Previous</a><span><a class="paginate_button current" aria-controls="data-table-1" data-dt-idx="2" tabindex="0">1</a><a class="paginate_button " aria-controls="data-table-1" data-dt-idx="3" tabindex="0">2</a><a class="paginate_button " aria-controls="data-table-1" data-dt-idx="4" tabindex="0">3</a><a class="paginate_button " aria-controls="data-table-1" data-dt-idx="5" tabindex="0">4</a><a class="paginate_button " aria-controls="data-table-1" data-dt-idx="6" tabindex="0">5</a><a class="paginate_button " aria-controls="data-table-1" data-dt-idx="7" tabindex="0">6</a></span><a class="paginate_button next" aria-controls="data-table-1" data-dt-idx="8" tabindex="0" id="data-table-1_next">Next</a><a class="paginate_button last" aria-controls="data-table-1" data-dt-idx="9" tabindex="0" id="data-table-1_last">Last</a></div></div>
									<div class="clearfix"></div>
								</div>
								</div>
                           </form>
                  </div>
                </div>
                  <div class="widget-foot">
                    <!-- Footer goes here -->
                  </div>
              </div>  

            </div>

          </div>
          <!-- Today status. jQuery Sparkline plugin used. -->
		  <div class="row">
            <div class="col-md-12">

              <!-- Widget -->
              <div class="widget">
                <!-- Widget head -->
                <div class="widget-head">
                  <div class="pull-left">Dashboard</div>
                  <div class="widget-icons pull-right">
                    <a href="index.html#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="index.html#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>  
                  <div class="clearfix"></div>
                </div>              

                <!-- Widget content -->
                <div class="widget-content">
                  <div class="padd">

                    <!-- Curve chart (Blue color). jQuery Flot plugin used. -->
                    <div id="curve-chart" style="padding: 0px; position: relative;"><canvas class="flot-base" width="627" height="250" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 627px; height: 250px;"></canvas><div class="flot-text" style="position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; font-size: smaller; color: rgb(84, 84, 84);"><div class="flot-x-axis flot-x1-axis xAxis x1Axis" style="position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; display: block;"><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 23px; text-align: center;">0</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 110px; text-align: center;">2</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 198px; text-align: center;">4</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 286px; text-align: center;">6</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 374px; text-align: center;">8</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 458px; text-align: center;">10</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 546px; text-align: center;">12</div></div><div class="flot-y-axis flot-y1-axis yAxis y1Axis" style="position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; display: block;"><div class="flot-tick-label tickLabel" style="position: absolute; top: 194px; left: 0px; text-align: right;">-1.0</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 150px; left: 0px; text-align: right;">-0.5</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 106px; left: 4px; text-align: right;">0.0</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 62px; left: 4px; text-align: right;">0.5</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 18px; left: 4px; text-align: right;">1.0</div></div></div><canvas class="flot-overlay" width="627" height="250" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 627px; height: 250px;"></canvas><div class="legend"><div style="position: absolute; width: 47px; height: 44px; top: 16px; right: 13px; opacity: 0.85; background-color: rgb(255, 255, 255);"> </div><table style="position:absolute;top:16px;right:13px;;font-size:smaller;color:#545454"><tbody><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(30,175,237);overflow:hidden"></div></div></td><td class="legendLabel">sin(x)</td></tr><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(30,175,237);overflow:hidden"></div></div></td><td class="legendLabel">cos(x)</td></tr></tbody></table></div></div>

                    <hr>
                    <!-- Hover location -->
                    <div id="hoverdata">Mouse hovers at
                    (<span id="x">5.87</span>, <span id="y">0.80</span>). <span id="clickdata"></span></div>          

                    <!-- Skil this line. <div class="uni"><input id="enableTooltip" type="checkbox">Enable tooltip</div> -->

                  </div>
                </div>
                <!-- Widget ends -->

              </div>
            </div>

          </div>
           <div class="row">

            <div class="col-md-12">


              <div class="widget wgreen">
                
                <div class="widget-head">
                  <div class="pull-left">Forms</div>
                  <div class="widget-icons pull-right">
                    <a href="#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>
                  <div class="clearfix"></div>
                </div>

                <div class="widget-content">
                  <div class="padd">

                    <br>
                    <!-- Form starts.  -->
                     <form class="form-horizontal" role="form">
                              
                                <div class="form-group">
                                  <label class="col-lg-2 control-label">Input Box</label>
                                  <div class="col-lg-5">
                                    <input type="text" class="form-control" placeholder="Input Box">
                                  </div>
                                </div>
                                
                                <div class="form-group">
                                  <label class="col-lg-2 control-label">Password</label>
                                  <div class="col-lg-5">
                                    <input type="password" class="form-control" placeholder="Password Box">
                                  </div>
                                </div>
                                
                                <div class="form-group">
                                  <label class="col-lg-2 control-label">Textarea</label>
                                  <div class="col-lg-5">
                                    <textarea class="form-control" rows="5" placeholder="Textarea"></textarea>
                                  </div>
                                </div>    
                                
                                <div class="form-group">
                                  <label class="col-lg-2 control-label">Checkbox</label>
                                  <div class="col-lg-5">
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="inlineCheckbox1" value="option1"> 1
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="inlineCheckbox2" value="option2"> 2
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="inlineCheckbox3" value="option3"> 3
                                    </label>
                                  </div>
                                </div>

                                <div class="form-group">
                                  <label class="col-lg-2 control-label">Radio Box</label>
                                  <div class="col-lg-5">
                                    <div class="radio">
                                      <label>
                                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">
                                        Option one
                                      </label>
                                    </div>
                                    <div class="radio">
                                      <label>
                                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                                        Option two
                                      </label>
                                    </div>
                                  </div>
                                </div>
                                
                                <div class="form-group">
                                  <label class="col-lg-2 control-label">Select Box</label>
                                  <div class="col-lg-2">
                                    <select class="form-control">
                                      <option>1</option>
                                      <option>2</option>
                                      <option>3</option>
                                      <option>4</option>
                                      <option>5</option>
                                    </select>
                                  </div>
                                </div>     

                                <div class="form-group">
                                  <label class="col-lg-2 control-label">Select Box</label>
                                  <div class="col-lg-2">
                                    <select multiple="" class="form-control">
                                      <option>1</option>
                                      <option>2</option>
                                      <option>3</option>
                                      <option>4</option>
                                      <option>5</option>
                                    </select>
                                  </div>
                                </div>   

                                <div class="form-group">
                                  <label class="col-lg-2 control-label">CLEditor</label>
                                  <div class="col-lg-6">
                                    <div class="cleditorMain" style="width: auto; height: auto;"><div class="cleditorToolbar" style="height: 53px;"><div class="cleditorGroup" style="width: 145px;"><div class="cleditorButton cleditorDisabled" title="Bold" disabled="disabled"></div><div class="cleditorButton cleditorDisabled" title="Italic" disabled="disabled" style="background-position: -24px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Underline" disabled="disabled" style="background-position: -48px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Strikethrough" disabled="disabled" style="background-position: -72px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Subscript" disabled="disabled" style="background-color: transparent; background-position: -96px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Superscript" disabled="disabled" style="background-position: -120px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 73px;"><div class="cleditorButton cleditorDisabled" title="Font" disabled="disabled" style="background-position: -144px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Font Size" disabled="disabled" style="background-position: -168px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Style" disabled="disabled" style="background-position: -192px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 73px;"><div class="cleditorButton cleditorDisabled" title="Font Color" disabled="disabled" style="background-position: -216px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Text Highlight Color" disabled="disabled" style="background-position: -240px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Remove Formatting" disabled="disabled" style="background-position: -264px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 49px;"><div class="cleditorButton cleditorDisabled" title="Bullets" disabled="disabled" style="background-position: -288px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Numbering" disabled="disabled" style="background-position: -312px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 49px;"><div class="cleditorButton cleditorDisabled" title="Outdent" disabled="disabled" style="background-position: -336px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Indent" disabled="disabled" style="background-position: -360px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 97px;"><div class="cleditorButton cleditorDisabled" title="Align Text Left" disabled="disabled" style="background-position: -384px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Center" disabled="disabled" style="background-position: -408px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Align Text Right" disabled="disabled" style="background-position: -432px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Justify" disabled="disabled" style="background-position: -456px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 49px;"><div class="cleditorButton cleditorDisabled" title="Undo" disabled="disabled" style="background-position: -480px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Redo" disabled="disabled" style="background-position: -504px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 97px;"><div class="cleditorButton cleditorDisabled" title="Insert Horizontal Rule" disabled="disabled" style="background-position: -528px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Insert Image" disabled="disabled" style="background-position: -552px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Insert Hyperlink" disabled="disabled" style="background-position: -576px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Remove Hyperlink" disabled="disabled" style="background-position: -600px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 97px;"><div class="cleditorButton cleditorDisabled" title="Cut" disabled="disabled" style="background-position: -624px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Copy" disabled="disabled" style="background-position: -648px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Paste" disabled="disabled" style="background-position: -672px 50%;"></div><div class="cleditorButton cleditorDisabled" title="Paste as Text" disabled="disabled" style="background-position: -696px 50%;"></div><div class="cleditorDivider"></div></div><div class="cleditorGroup" style="width: 49px;"><div class="cleditorButton" title="Print" style="background-position: -720px 50%;"></div><div class="cleditorButton" title="Show Source" style="background-position: -744px 50%;"></div></div></div><textarea class="cleditor" name="input" style="border: none; margin: 0px; padding: 0px; display: none; width: 462px;"></textarea><iframe frameborder="0" src="javascript:true;" style="width: 462px;"></iframe></div>
                                  </div>
                                </div>                                 
                                
                                <div class="form-group">
                                  <div class="col-lg-offset-2 col-lg-6">
                                    <button type="button" class="btn btn-sm btn-default">Default</button>
                                    <button type="button" class="btn btn-sm btn-primary">Primary</button>
                                    <button type="button" class="btn btn-sm btn-success">Success</button>
                                    <button type="button" class="btn btn-sm btn-info">Info</button>
                                    <button type="button" class="btn btn-sm btn-warning">Warning</button>
                                    <button type="button" class="btn btn-sm btn-danger">Danger</button>
                                  </div>
                                </div>
                              </form>
                  </div>
                </div>
                  <div class="widget-foot">
                    <!-- Footer goes here -->
                  </div>
              </div>  

            </div>

          </div>
          <div class="row">
            <div class="col-md-12"> 
              <!-- List starts -->
              <ul class="today-datas">
                <!-- List #1 -->
                <li>
                  <!-- Graph -->
                  <div><span id="todayspark1" class="spark"><canvas width="77" height="30" style="display: inline-block; width: 77px; height: 30px; vertical-align: top;"></canvas></span></div>
                  <!-- Text -->
                  <div class="datas-text">12,000 visitors/day</div>
                </li>
                <li>
                  <div><span id="todayspark2" class="spark"><canvas width="77" height="30" style="display: inline-block; width: 77px; height: 30px; vertical-align: top;"></canvas></span></div>
                  <div class="datas-text">30,000 Pageviews</div>
                </li>
                <li>
                  <div><span id="todayspark3" class="spark"><canvas width="77" height="30" style="display: inline-block; width: 77px; height: 30px; vertical-align: top;"></canvas></span></div>
                  <div class="datas-text">15.66% Bounce Rate</div>
                </li>
                <li>
                  <div><span id="todayspark4" class="spark"><canvas width="77" height="30" style="display: inline-block; width: 77px; height: 30px; vertical-align: top;"></canvas></span></div>
                  <div class="datas-text">$12,000 Revenue/Day</div>
                </li> 
                <li>
                  <div><span id="todayspark5" class="spark"><canvas width="250" height="30" style="display: inline-block; width: 250px; height: 30px; vertical-align: top;"></canvas></span></div>
                  <div class="datas-text">15,000000 visitors till date</div>
                </li>                                                                                                              
              </ul> 
            </div>
          </div>

          <!-- Today status ends -->

          <!-- Dashboard Graph starts -->

          <div class="row">
            <div class="col-md-8">

              <!-- Widget -->
              <div class="widget">
                <!-- Widget head -->
                <div class="widget-head">
                  <div class="pull-left">Dashboard</div>
                  <div class="widget-icons pull-right">
                    <a href="index.html#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="index.html#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>  
                  <div class="clearfix"></div>
                </div>              

                <!-- Widget content -->
                <div class="widget-content">
                  <div class="padd">

                    <!-- Curve chart (Blue color). jQuery Flot plugin used. -->
                    <div id="curve-chart" style="padding: 0px; position: relative;"><canvas class="flot-base" width="627" height="250" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 627px; height: 250px;"></canvas><div class="flot-text" style="position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; font-size: smaller; color: rgb(84, 84, 84);"><div class="flot-x-axis flot-x1-axis xAxis x1Axis" style="position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; display: block;"><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 23px; text-align: center;">0</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 110px; text-align: center;">2</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 198px; text-align: center;">4</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 286px; text-align: center;">6</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 374px; text-align: center;">8</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 458px; text-align: center;">10</div><div class="flot-tick-label tickLabel" style="position: absolute; max-width: 78px; top: 228px; left: 546px; text-align: center;">12</div></div><div class="flot-y-axis flot-y1-axis yAxis y1Axis" style="position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; display: block;"><div class="flot-tick-label tickLabel" style="position: absolute; top: 194px; left: 0px; text-align: right;">-1.0</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 150px; left: 0px; text-align: right;">-0.5</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 106px; left: 4px; text-align: right;">0.0</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 62px; left: 4px; text-align: right;">0.5</div><div class="flot-tick-label tickLabel" style="position: absolute; top: 18px; left: 4px; text-align: right;">1.0</div></div></div><canvas class="flot-overlay" width="627" height="250" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 627px; height: 250px;"></canvas><div class="legend"><div style="position: absolute; width: 47px; height: 44px; top: 16px; right: 13px; opacity: 0.85; background-color: rgb(255, 255, 255);"> </div><table style="position:absolute;top:16px;right:13px;;font-size:smaller;color:#545454"><tbody><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(30,175,237);overflow:hidden"></div></div></td><td class="legendLabel">sin(x)</td></tr><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid rgb(30,175,237);overflow:hidden"></div></div></td><td class="legendLabel">cos(x)</td></tr></tbody></table></div></div>

                    <hr>
                    <!-- Hover location -->
                    <div id="hoverdata">Mouse hovers at
                    (<span id="x">5.87</span>, <span id="y">0.80</span>). <span id="clickdata"></span></div>          

                    <!-- Skil this line. <div class="uni"><input id="enableTooltip" type="checkbox">Enable tooltip</div> -->

                  </div>
                </div>
                <!-- Widget ends -->

              </div>
            </div>

            <div class="col-md-4">

              <div class="widget">

                <div class="widget-head">
                  <div class="pull-left">Today Status</div>
                  <div class="widget-icons pull-right">
                    <a href="index.html#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="index.html#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>  
                  <div class="clearfix"></div>
                </div>             

                <div class="widget-content">
                  <div class="padd">

                    <!-- Visitors, pageview, bounce rate, etc., Sparklines plugin used -->
                    <ul class="current-status">
                      <li>
                        <span id="status1"><canvas width="80" height="20" style="display: inline-block; width: 80px; height: 20px; vertical-align: top;"></canvas></span> <span class="bold">Visits : 2000</span>
                      </li>
                      <li>
                        <span id="status2"><canvas width="80" height="20" style="display: inline-block; width: 80px; height: 20px; vertical-align: top;"></canvas></span> <span class="bold">Unique Visitors : 1,345</span>
                      </li>
                      <li>
                        <span id="status3"><canvas width="80" height="20" style="display: inline-block; width: 80px; height: 20px; vertical-align: top;"></canvas></span> <span class="bold">Pageviews : 2000</span>
                      </li>
                      <li>
                        <span id="status4"><canvas width="80" height="20" style="display: inline-block; width: 80px; height: 20px; vertical-align: top;"></canvas></span> <span class="bold">Pages / Visit : 2000</span>
                      </li>
                      <li>
                        <span id="status5"><canvas width="80" height="20" style="display: inline-block; width: 80px; height: 20px; vertical-align: top;"></canvas></span> <span class="bold">Avg. Visit Duration : 2000</span>
                      </li>
                      <li>
                        <span id="status6"><canvas width="80" height="20" style="display: inline-block; width: 80px; height: 20px; vertical-align: top;"></canvas></span> <span class="bold">Bounce Rate : 2000</span>
                      </li>   
                      <li>
                        <span id="status7"><canvas width="80" height="20" style="display: inline-block; width: 80px; height: 20px; vertical-align: top;"></canvas></span> <span class="bold">% New Visits : 2000</span>
                      </li>                                                                                                            
                    </ul>

                  </div>
                </div>

              </div>

            </div>
          </div>
          <!-- Dashboard graph ends -->

          <!-- Chats, File upload and Recent Comments -->
          <div class="row">

            <div class="col-md-4">
              <!-- Widget -->
              <div class="widget">
                <!-- Widget title -->
                <div class="widget-head">
                  <div class="pull-left">Chats</div>
                  <div class="widget-icons pull-right">
                    <a href="index.html#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="index.html#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>  
                  <div class="clearfix"></div>
                </div>

                <div class="widget-content">
                  <!-- Widget content -->
				  
				  <!-- Below class "scroll-chat" will add nice scroll bar. It uses Slim Scroll jQuery plugin. Check custom.js for the code -->
                  <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 350px;"><div class="padd scroll-chat" style="overflow: hidden; width: auto; height: 350px;">
                    
                    <ul class="chats">

                      <!-- Chat by us. Use the class "by-me". -->
                      <li class="by-me">
                        <!-- Use the class "pull-left" in avatar -->
                        <div class="avatar pull-left">
                          <img src="img/user.jpg" alt="">
                        </div>

                        <div class="chat-content">
                          <!-- In meta area, first include "name" and then "time" -->
                          <div class="chat-meta">Ashok <span class="pull-right">3 hours ago</span></div>
                          Vivamus diam elit diam, consectetur dapibus adipiscing elit.
                          <div class="clearfix"></div>
                        </div>
                      </li> 

                      <!-- Chat by other. Use the class "by-other". -->
                      <li class="by-other">
                        <!-- Use the class "pull-right" in avatar -->
                        <div class="avatar pull-right">
                          <img src="img/user.jpg" alt="">
                        </div>

                        <div class="chat-content">
                          <!-- In the chat meta, first include "time" then "name" -->
                          <div class="chat-meta">3 hours ago <span class="pull-right">Ravi</span></div>
                          Vivamus diam elit diam, consectetur fconsectetur dapibus adipiscing elit.
                          <div class="clearfix"></div>
                        </div>
                      </li>   

                      <li class="by-me">
                        <div class="avatar pull-left">
                          <img src="img/user.jpg" alt="">
                        </div>

                        <div class="chat-content">
                          <div class="chat-meta">Ashok <span class="pull-right">4 hours ago</span></div>
                          Vivamus diam elit diam, consectetur fermentum sed dapibus eget, Vivamus consectetur dapibus adipiscing elit.
                          <div class="clearfix"></div>
                        </div>
                      </li>  

                      <li class="by-other">
                        <!-- Use the class "pull-right" in avatar -->
                        <div class="avatar pull-right">
                          <img src="img/user.jpg" alt="">
                        </div>

                        <div class="chat-content">
                          <!-- In the chat meta, first include "time" then "name" -->
                          <div class="chat-meta">3 hours ago <span class="pull-right">Ravi</span></div>
                          Vivamus diam elit diam, consectetur fermentum sed dapibus eget, Vivamus consectetur dapibus adipiscing elit.
                          <div class="clearfix"></div>
                        </div>
                      </li>                                                                                  

                    </ul>

                  </div><div class="slimScrollBar" style="width: 5px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 242.094861660079px; background: rgba(0, 0, 0, 0.298039);"></div><div class="slimScrollRail" style="width: 5px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(51, 51, 51);"></div></div>
                  <!-- Widget footer -->
                  <div class="widget-foot">
				  
					<div class="input-group">
					  <input type="text" class="form-control">
					  <span class="input-group-btn">
						<button class="btn btn-default" type="button">Send</button>
					  </span>
					</div>
					
                  </div>
                </div>


              </div> 
            </div>


            <!-- File Upload widget -->
            <div class="col-md-4">
              <div class="widget">
                <!-- Widget title -->
                <div class="widget-head">
                  <div class="pull-left">File Upload</div>
                  <div class="widget-icons pull-right">
                    <a href="index.html#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="index.html#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>  
                  <div class="clearfix"></div>
                </div>

                <div class="widget-content">
                  <!-- Widget content -->
                  <ul class="file-upload">

                    <li>
                      <strong><i class="fa fa-upload"></i> File_Name_Here.Mp3</strong>
                      <div class="file-meta">3.3 of 5MB - 5 mins - 1MB/Sec</div>

                      <div class="progress progress-animated progress-striped">
						  <div class="progress-bar progress-bar-success" data-percentage="100" style="width: 100%;">100%</div>
					  </div>
                    </li>

                    <li>
                      <strong><i class="fa fa-check"></i> Third_File_Here.Mp3</strong>
                      <div class="file-meta">5MB - 5 mins - Stopped</div>
                    </li>                        

                    <li>
                      <strong><i class="fa fa-check"></i> Third_File_Here.Mp3</strong>
                      <div class="file-meta">5MB - 5 mins - Stopped</div>
                    </li>                                                                                                                      
                  </ul>

                  <div class="widget-foot">
                  </div>

                </div>
              </div>

              <div class="widget">
                <!-- Widget title -->
                <div class="widget-head">
                  <div class="pull-left">Browsers</div>
                  <div class="widget-icons pull-right">
                    <a href="index.html#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="index.html#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>  
                  <div class="clearfix"></div>
                </div>
                <div class="widget-content referrer">
                  <!-- Widget content -->
                  
				  <div class="table-responsive">
					  <table class="table table-striped table-bordered table-hover">
						<tbody><tr>
						  <th></th>
						  <th>Browsers</th>
						  <th>Visits</th>
						</tr>
						<tr>
						  <td><img src="chrome.png" alt="">
						  </td><td>Google Chrome</td>
						  <td>3,005</td>
						</tr> 
						<tr>
						  <td><img src="img/firefox.png" alt="">
						  </td><td>Mozilla Firefox</td>
						  <td>2,505</td>
						</tr> 
						<tr>
						  <td><img src="img/index_files/ie.png" alt="">
						  </td><td>Internet Explorer</td>
						  <td>1,405</td>
						</tr> 
						<tr>
						  <td><img src="img/index_files/opera.png" alt="">
						  </td><td>Opera</td>
						  <td>4,005</td>
						</tr> 
						<tr>
						  <td><img src="img/safari.png" alt="">
						  </td><td>Safari</td>
						  <td>505</td>
						</tr>                                                                    
					  </tbody></table>
				  </div>

                  <div class="widget-foot">
                  </div>
                </div>
              </div>

            </div>


            <div class="col-md-4">
              <!-- Widget -->
              <div class="widget">
                <!-- Widget title -->
                <div class="widget-head">
                  <div class="pull-left">Recent Comments</div>
                  <div class="widget-icons pull-right">
                    <a href="index.html#" class="wminimize"><i class="fa fa-chevron-up"></i></a> 
                    <a href="index.html#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>  
                  <div class="clearfix"></div>
                </div>
                <div class="widget-content">
                  <!-- Widget content -->
                  <div class="padd">

                    <ul class="recent">


                      <li>

                        <div class="recent-content">
                          <div class="recent-meta">Posted on 25/1/2120 by Ashok</div>
                          <div>Vivamus diam elit diam, consectetur fermentum sed dapibus eget, Vivamus consectetur dapibus adipiscing elit.
                          </div>

                          <div class="btn-group">
                            <button class="btn btn-xs btn-default"><i class="fa fa-check"></i> </button>
                            <button class="btn btn-xs btn-default"><i class="fa fa-pencil"></i> </button>
                            <button class="btn btn-xs btn-default"><i class="fa fa-times"></i> </button>
                          </div>

                          <button class="btn btn-xs btn-danger pull-right">Spam</button>

                          <div class="clearfix"></div>
                        </div>
                      </li>



                      <li>

                        <div class="recent-content">
                          <div class="recent-meta">Posted on 25/1/2120 by Ashok</div>
                          <div>Vivamus diam elit diam, consectetur fermentum sed dapibus eget, Vivamus consectetur dapibus adipiscing elit.
                          </div>

                          <div class="btn-group">
                            <button class="btn btn-xs btn-default"><i class="fa fa-check"></i> </button>
                            <button class="btn btn-xs btn-default"><i class="fa fa-pencil"></i> </button>
                            <button class="btn btn-xs btn-default"><i class="fa fa-times"></i> </button>
                          </div>

                          <button class="btn btn-xs btn-danger pull-right">Spam</button>

                          <div class="clearfix"></div>
                        </div>
                      </li>



                      <li>

                        <div class="recent-content">
                          <div class="recent-meta">Posted on 25/1/2120 by Ashok</div>
                          <div>Vivamus diam elit diam, consectetur fermentum sed dapibus eget, Vivamus consectetur dapibus adipiscing elit.
                          </div>

                          <div class="btn-group">
                            <button class="btn btn-xs btn-default"><i class="fa fa-check"></i> </button>
                            <button class="btn btn-xs btn-default"><i class="fa fa-pencil"></i> </button>
                            <button class="btn btn-xs btn-default"><i class="fa fa-times"></i> </button>
                          </div>

                          <button class="btn btn-xs btn-danger pull-right">Spam</button>

                          <div class="clearfix"></div>
                        </div>
                      </li>                                        


                    </ul>

                  </div>
                  <!-- Widget footer -->
                  <div class="widget-foot">
                    
                    
                      <ul class="pagination pagination-sm pull-right">
                        <li><a href="index.html#">Prev</a></li>
                        <li><a href="index.html#">1</a></li>
                        <li><a href="index.html#">2</a></li>
                        <li><a href="index.html#">3</a></li>
                        <li><a href="index.html#">4</a></li>
                        <li><a href="index.html#">Next</a></li>
                      </ul>
                   
                    <div class="clearfix"></div>
                  </div>
                </div>  
              </div> 
            </div>
          </div>
        </div>
		</div>
	</div>
		  
	</body>
</html>
