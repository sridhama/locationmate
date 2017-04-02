<?php
require("./config.php");
$username = $_GET['username'];
  $sql = "SELECT a.fname, a.lname, a.username, a.gender FROM users a, friends_meta b WHERE a.username = b.friend_username AND b.username = '$username';";
$body = "";
$rs = mysqli_query($GLOBALS['connection'],$sql);
if(mysqli_num_rows($rs) == 0){
  echo "NO_FRIENDS";
  return;
}
$names = array();
$usernames = array();
$genders = array();
while($row = mysqli_fetch_assoc($rs)){
  $friend_username = $row['username'];
  $fname = $row['fname'];
  $lname = $row['lname'];
  $gender = $row['gender'];
  // $body .= "NAME:".$fname." ".$lname."<br>USERNAME:".$friend_username."<br>GENDER:".$gender."<br>";
  // $body .= $fname."\n".$lname."\n".$friend_username."\n".$gender."\n";
  $name = $fname." ".$lname;
  array_push($names,$name);
  array_push($usernames,$friend_username);
  array_push($genders,$gender);
}
$res = array('names' => $names, 'usernames' => $usernames, 'genders'=>$genders);
echo json_encode($res);
