<?php
require("./config.php");
$phone = $_GET['phone'];
$sql = "DELETE FROM `users` WHERE `phone` = '$phone'";
$rs = mysqli_query($GLOBALS['connection'],$sql);
$sql = "DELETE FROM `friends_meta` WHERE `phone` = '$phone';";
$rs = mysqli_query($GLOBALS['connection'],$sql);
$sql = "DELETE FROM `friends_meta` WHERE `friend_phone` = '$phone';";
$rs = mysqli_query($GLOBALS['connection'],$sql);
echo "SUCCESS";
