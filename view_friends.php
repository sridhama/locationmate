<?php
require("./config.php");
$username = $_GET['username'];
  $sql = "SELECT a.fname, a.lname, a.username, a.gender FROM users a, friends_meta b WHERE a.username = b.friend_username AND b.username = '$username';";
$body = "";
$rs = mysqli_query($GLOBALS['connection'],$sql);
while($row = mysqli_fetch_assoc($rs)){
  $friend_username = $row['username'];
  $fname = $row['fname'];
  $lname = $row['lname'];
  $gender = $row['gender'];
  $body .= "NAME:".$fname." ".$lname."<br>USERNAME:".$friend_username."<br>GENDER:".$gender."<br>";
}
$body .= "END";
echo $body;
