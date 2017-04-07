<?php
require("./config.php");
$name = ucwords(trim(mysqli_real_escape_string($GLOBALS['connection'],$_GET['name'])));
$gender = intval($_GET['gender']);
$bssid = $_GET['bssid'];
$phone = (mysqli_real_escape_string($GLOBALS['connection'],$_GET['phone']));
$password = mysqli_real_escape_string($GLOBALS['connection'],$_GET['password']);
if(strlen($name) < 2 || strlen($name)>32){
  echo "Invalid Name.";
}
else if(strlen($gender)!=1){
  echo "Select Gender.";
}else if(strlen($phone) != 10){
  echo "Invalid Mobile Number";
}
else if($password == "d41d8cd98f00b204e9800998ecf8427e"){
  echo "Enter a password";
}
//password check
else{
$sql = "SELECT * FROM `users` WHERE `phone`='$phone';";
$rs1 = mysqli_query($GLOBALS['connection'],$sql);
$count = mysqli_num_rows($rs1);
if($count != 0){
  echo "There is already an account associated with this mobile number.";
}else{
do{
$rows = 100;
$secret_key = substr(strtoupper(uniqid()), 4,6);
$search_sql = "SELECT * FROM `users` WHERE `secret_key` = '$secret_key';";
$search_result = mysqli_query($GLOBALS['connection'], $search_sql);
$rows = mysqli_num_rows($search_result);
}while($rows != 0);
$time = $_SERVER['REQUEST_TIME'];
$sql = "INSERT INTO `users` (`name`,`gender`,`phone`,`password`,`secret_key`, `last_bssid`, `last_seen`) VALUES ('$name',$gender,'$phone','$password','$secret_key','$bssid','$time');";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if($rs){
  echo "SUCCESS".$secret_key;
}else{
  echo "Error. Try again later.";
}
}
}
