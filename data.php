<?php
require("./functions.php");
if(isset($_GET['phone']) && isset($_GET['friend_phone'])){
$phone = $_GET['phone'];
$friend_phone = $_GET['friend_phone'];
echo get_location($phone,$friend_phone);
echo "<br>";
echo get_date(get_time($phone,$friend_phone));
}else{
  echo "ERROR";
}
