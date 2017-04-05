<?php
require("./config.php");
// $time = $_SERVER['REQUEST_TIME'];
// echo $time."\n";
// echo 100-$time%100;
if(isset($_POST['submit'])){
$k = base64_encode($_POST['temp']);
$sql = "INSERT INTO users (`password`) VALUES ('$k');";
$rs = mysqli_query($GLOBALS['connection'],$sql);
// $out = "\n\n".base64_decode($k);
if($rs){
  echo "YES";
}
}
echo "<pre style=font-family:Times New Roman>".base64_decode("MTIzDQpuZXdsaW5lDQogICAzc3BhY2U=")."</pre>";
?>

<html>
<form method=post>
  <textarea name=temp></textarea>
  <button type=submit name=submit>Submit</button></form></html>
