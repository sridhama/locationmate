<?php
require("./functions.php");
$phone = $_GET['phone'];
$friend_code = strtoupper(mysqli_real_escape_string($GLOBALS['connection'],$_GET['friend_code']));
add_friend($phone,$friend_code);
