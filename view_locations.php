<?php
require("./config.php");
$phone = $_GET['phone'];
$sql = "SELECT a.location, a.bssid FROM bssid_location_meta a WHERE a.bssid IN (SELECT c.last_bssid FROM users c WHERE c.phone IN (SELECT b.friend_phone FROM friends_meta b WHERE b.phone = '$phone'));";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if(mysqli_num_rows($rs) == 0){
  echo "NO_FRIENDS";
  return;
}
$locations = array();
$bssids = array();
while($row = mysqli_fetch_assoc($rs)){
  array_push($locations,$row['location']);
  array_push($bssids,$row['bssid']);
}
$res = array('locations' => $locations, 'bssids' => $bssids);
echo json_encode($res);
