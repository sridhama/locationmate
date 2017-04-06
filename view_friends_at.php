<?php
require("./config.php");
$phone = $_GET['phone'];
$bssid = $_GET['bssid'];
$sql = "SELECT c.name, c.phone, c.gender FROM users c WHERE c.phone IN (SELECT b.friend_phone FROM friends_meta b WHERE b.phone = '$phone') AND c.last_bssid = '$bssid';";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if(mysqli_num_rows($rs) == 0){
  echo "NO_FRIENDS";
  return;
}
$names = array();
$phones = array();
$genders = array();
while($row = mysqli_fetch_assoc($rs)){
  $friend_phone = $row['phone'];
  $name = $row['name'];
  $gender = $row['gender'];
  array_push($names,$name);
  array_push($phones,$friend_phone);
  array_push($genders,$gender);
}
$res = array('names' => $names, 'phones' => $phones, 'genders'=>$genders);
echo json_encode($res);
