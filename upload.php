<?php
require("./config.php");
$location = mysqli_real_escape_string($GLOBALS['connection'],$_GET['location']);
$bssid = $_GET['bssid'];
$sql = "INSERT INTO `bssid_location_meta` (`bssid`, `location`) VALUES ('$bssid','$location');";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if($rs){
  echo "SUCCESS";
}else{
  $sql = "SELECT * FROM `bssid_location_meta` WHERE `bssid` = '$bssid';";
  $rs1 = mysqli_query($GLOBALS['connection'],$sql);
  while($row=mysqli_fetch_assoc($rs1)){
    $location = $row['location'];
  }
  echo "ERROR: LOCATION ALREADY MAPPED AS:\n".$location;
}
