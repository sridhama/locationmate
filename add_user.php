<?php
require("./config.php");
$name = ucwords(mysqli_real_escape_string($GLOBALS['connection'],$_GET['name']));
// $lname = ucfirst(mysqli_real_escape_string($GLOBALS['connection'],$_GET['lname']));
$gender = intval($_GET['gender']);
$bssid = $_GET['bssid'];
$username = strtolower(mysqli_real_escape_string($GLOBALS['connection'],$_GET['username']));

if(strlen($name) < 2 || strlen($name)>32){
  echo "Invalid Name.";
}
else if(preg_match('/[\'^£$%&*()}{@#~?><>,|=_+¬-]/', $name)){
  echo "Invalid Username.";
}

// else if(strlen($lname) < 1 || strlen($lname)>32){
//   echo "Invalid Last Name.";
// }

else if(strlen($gender)!=1){
  echo "Select Gender.";
}else if(strlen($username) > 32){
  echo "Username too long.";
}else if(strlen($username) < 2){
  echo "Username too short.";
}else if(preg_match('/[\'^£$%&*()}{@#~?><>,|=_+¬-]/', $username)){
  echo "Invalid Username.";
}

else{
$sql = "SELECT * FROM `users` WHERE `username`='$username';";
$rs1 = mysqli_query($GLOBALS['connection'],$sql);
$count = mysqli_num_rows($rs1);
if($count != 0){
  echo "USERNAME_EXISTS";
}else{
do{
$rows = 100;
$secret_key = substr(strtoupper(uniqid()), 4,6);
$search_sql = "SELECT * FROM `users` WHERE `secret_key` = '$secret_key';";
$search_result = mysqli_query($GLOBALS['connection'], $search_sql);
$rows = mysqli_num_rows($search_result);
}while($rows != 0);
$time = $_SERVER['REQUEST_TIME'];
$sql = "INSERT INTO `users` (`name`,`gender`,`username`,`secret_key`, `last_bssid`, `last_seen`) VALUES ('$name',$gender,'$username','$secret_key','$bssid','$time');";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if($rs){
  echo "SUCCESS".$secret_key;
}else{
  echo "Error. Try again later.";
}
}
}
