<?php
require("./functions.php");
$username = $_GET['username'];
$friend_username = $_GET['friend_code'];
add_friend($username,$friend_username);
