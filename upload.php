<?php
require("./config.php");
$location = $_GET['location'];
$bssid = $_GET['bssid'];
$sql = "INSERT INTO `bssid_location_meta` (`bssid`, `location`) VALUES ('$bssid','$location');";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if($rs){
  echo "SUCCESS";
}else{
  echo "ERROR, BSSID MAY EXIST";
}
