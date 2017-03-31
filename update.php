<?php
require("./config.php");
$username = $_GET['username'];
$bssid = $_GET['bssid'];
$time = $_SERVER['REQUEST_TIME'];
$sql = "UPDATE `users` SET `last_bssid` = '$bssid', `last_seen` = $time WHERE `username` = '$username';";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if($rs){
  echo "SUCCESS";
}else{
  echo "ERROR";
}
