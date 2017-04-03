<?php
require("./config.php");

$phone = $_GET['phone'];
$password = $_GET['password'];

$sql = "SELECT * FROM `users` WHERE `phone` = '$phone' AND `password` = '$password';";
$rs = mysqli_query($GLOBALS['connection'],$sql);
$count = mysqli_num_rows($rs);
if($count != 1){
  echo "Invalid Credentials. Try again.";
  return;
}else{
  while($row = mysqli_fetch_assoc($rs)){
    $secret_key = $row['secret_key'];
  }
  echo "SUCCESS".$secret_key;
}
