<?php
require("./functions.php");
if(isset($_GET['username']) && isset($_GET['friend_username'])){
$username = $_GET['username'];
$friend_username = $_GET['friend_username'];
echo get_location($username,$friend_username);
echo "<br>";
echo get_date(get_time($username,$friend_username));
}else{
  echo "ERROR";
}
