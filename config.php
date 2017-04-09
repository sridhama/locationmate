<?php
define("DB_HOST", "localhost");
define("DB_USER", "root");
define("DB_PASS", "root");
define("DB_NAME", "LM_DB");
$connection = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_NAME);
if(!$connection){
  die("Connection error" . mysqli_error($connection));
}
$GLOBALS['connection'] = $connection;
date_default_timezone_set("Asia/Kolkata");
$cronsql = "UPDATE `users` SET `last_bssid` = 'unknown' WHERE `last_seen` < (UNIX_TIMESTAMP() - 120000)";
mysqli_query($connection, $cronsql);
