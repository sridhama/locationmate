<?php
require("./functions.php");
$phone = $_GET['phone'];
$friend_phone = $_GET['friend_phone'];
$sql = "DELETE FROM `friends_meta` WHERE `phone` = '$phone' AND `friend_phone` = '$friend_phone';";
$rs = mysqli_query($GLOBALS['connection'],$sql);
$sql2 = "DELETE FROM `friends_meta` WHERE `phone` = '$friend_phone' AND `friend_phone` = '$phone';";
$rs2 = mysqli_query($GLOBALS['connection'],$sql2);
