<!DOCTYPE html>
<head>
    <meta charset="UTF-8" />
    <link rel='stylesheet' href="<?php echo AppConfig::$base_url; ?>/css/back.css">
    <title>玉龙轩后台管理</title>
</head>
<body>
	<div id="header">
		<span>玉龙轩后台管理</span>
		<div class="header_right">
			<a href="index">返回网站</a>
			<a href="logout">退出</a>
		</div>
	</div>
	<div id="context">
		<table border="1">
            <tr>
              <th>id</th>
              <th>姓名</th>
              <th>电话</th>
              <th>型号</th>
              <th>颜色</th>
              <th>地址</th>
              <th>时间</th>
            </tr>
            <?php foreach ($result as $model){
                $tmp = '';
                $tmp .= "<tr>";
                $tmp .= "<td>$model->id</td>";
                $tmp .= "<td>$model->name</td>";
                $tmp .= "<td>$model->phone</td>";
                $tmp .= "<td>$model->type</td>";
                $tmp .= "<td>$model->color</td>";
                $tmp .= "<td>$model->province-$model->city-$model->area-$model->detail</td>";
                $tmp .= "<td>$model->date</td>";
                $tmp .= "</tr>";
                echo $tmp;
            }?>
    </table>
	</div>
  <div id="change">
      <ul>
        <li><a href="<?php echo AppConfig::$base_url.'/index.php/back/?page=1';?>" title="跳到第一页"><<</a></li>
        <li><a href="<?php echo AppConfig::$base_url.'/index.php/back/?page='.($pages->currentPage-1);?>" title="上一页"><</a></li>
        <?php for ($i=1; $i<=$pages->pageCount; $i++){ 
            $ch = '';
            if($i==$pages->currentPage) 
                $ch = 'class="change_select"';
            echo "<li><a $ch href='".AppConfig::$base_url."/index.php/back/?page=$i'>$i</a></li>";
        }?>
        <li style="background-color:#F6F6F3;line-height: 34px;">&nbsp...&nbsp</li>
        <li><a href="<?php echo AppConfig::$base_url.'/index.php/back/?page='.($pages->currentPage+1);?>" title="下一页">></a></li>
        <li><a href="<?php echo AppConfig::$base_url.'/index.php/back/?page='.$pages->pageCount;?>" title="最后一页">>></a></li>
      </ul>
      <div class="export">
        <a href="#">导出当前页</a>
        <a href="#">导出全部</a>
      </div>
    </div>
	<div id="footer">
	</div>
</body>
