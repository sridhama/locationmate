<?php
require("./config.php");
$fname = $_GET['fname'];
$lname = $_GET['lname'];
$gender = $_GET['gender'];
$username = $_GET['username'];

do{
$rows = 100;
$secret_key = substr(strtoupper(uniqid()), 4,8);
$search_sql = "SELECT * FROM `users` WHERE `secret_key` = '$secret_key';";
$search_result = mysqli_query($GLOBALS['connection'], $search_sql);
$rows = mysqli_num_rows($search_result);
}while($rows != 0);

$sql = "INSERT INTO `users` (`fname`,`lname`,`gender`,`username`,`secret_key`) VALUES ('$fname','$lname',$gender,'$username','$secret_key');";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if($rs){
  echo "SUCCESS";
}else{
  echo "ERROR";
}
