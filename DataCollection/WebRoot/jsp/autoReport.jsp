<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div class="ui segment" style="background-color: #F0F0F0;">
	<div class="ui center aligned vertical segment">
		<form method="POST" enctype="multipart/form-data" action=""
			id="ajax-form">
			<div class="ui form">
				<div class="inline field">
					<label> 上传PDF： </label><input class="ui button" type="file"
						name="file"><input class="ui primary button" type="submit"
						value="开始上传">
					<div class="ui header" id="message"
						style="margin-top: 10px; margin-bottom: 10px;"></div>
					<div id="flag"></div>
				</div>
			</div>
		</form>
	</div>
	<div class="ui left aligned vertical segment" style="padding-left: 30px; padding-right: 30px;">
		<input class="ui primary button" type="button" value="开始解析" style="margin-right: 30px; font-size: 15px;"
			id="analysis"><div class="ui active inline loader" style="display: none;" id="loader"></div>
			<h2 class="ui header">资产负债表</h2>
			<div id="fuzhai"></div>
			<h2 class="ui header">利润表</h2>
			<div id="lirun"></div>
			<h2 class="ui header">现金流量表</h2>
			<div id="xianjin"></div>
	</div>
</div>
<script src="/DataCollection/js/jquery/jquery.form.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#analysis').click(function() {
							$('#loader').removeAttr("style");
							$.ajax({
								type: 'GET',
  								url: '/DataCollection/util/analysisPdf',
  								success: function(data) {
  									$('#loader').css('display', 'none');
  									
  									if (data != "") {
  										var json = $.parseJSON(data);
									
										addTable(json.fuzhai.内容[0].表格列顺序, json.fuzhai.内容, 'fuzhai');
										addTable(json.lirun.内容[0].表格列顺序, json.lirun.内容, 'lirun');
										addTable(json.xianjin.内容[0].表格列顺序, json.xianjin.内容, 'xianjin');
  									}
  								}
							});
						});

						$('#ajax-form').submit(function(e) {
							$('#message').html("正在上传");
							$('#flag').html('<div class="ui active inline loader"></div>');
							e.preventDefault();
							$('#ajax-form').ajaxSubmit({
								type : "POST",
								url : "/DataCollection/uploadFile/tmp",
								dataType : "text",
								success : function(data) {
									if (data == 'yes!') {
										$('#message').html("上传成功");
										$('#flag').html('<i class="checkmark icon"></i>');
									} else {
										$('#message').html("上传失败");
										$('#flag').html('<i class="remove icon"></i>');
									}
								}
							});
						});

					});
		function addTable(header, content, name) {
			var table = '<table class="ui celled definition table"><thead>';
			table += '<tr>';
			$.each(header, function(index, element) {
				table += '<th>' + element + '</th>';
			});
			table += '</tr></thead><tbody>';
			$.each(content, function(index, row) {
				table += '<tr>';
				if (index > 0) {
					$.each(header, function(num, col) {
						table += '<td>' + row[col] + '</td>';
					});
				}
				table += '</tr>';
			});
			table += '</tbody></table>';
			$('#' + name).html(table);
		}
</script>
