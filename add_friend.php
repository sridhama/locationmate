<?php
require("./functions.php");
$username = $_GET['username'];
$friend_code = strtoupper(mysqli_real_escape_string($GLOBALS['connection'],$_GET['friend_code']));
add_friend($username,$friend_code);
