<?php
require("./config.php");
$phone = $_GET['phone'];
  $sql = "SELECT a.name, a.phone, a.gender FROM users a, friends_meta b WHERE a.phone = b.friend_phone AND b.phone = '$phone' ORDER BY a.name ASC;";
$body = "";
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
  // $body .= "NAME:".$fname." ".$lname."<br>phone:".$friend_phone."<br>GENDER:".$gender."<br>";
  // $body .= $fname."\n".$lname."\n".$friend_phone."\n".$gender."\n";
  array_push($names,$name);
  array_push($phones,$friend_phone);
  array_push($genders,$gender);
}
$res = array('names' => $names, 'phones' => $phones, 'genders'=>$genders);
echo json_encode($res);
