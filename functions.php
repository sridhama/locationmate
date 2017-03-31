<?php
require("./config.php");
require("./cipher.php");
function get_location($username, $friend_username){
  // check friendship
  $sql = "SELECT * FROM `friends_meta` WHERE `username` = '$username' AND `friend_username` = '$friend_username';";
  $res = mysqli_query($GLOBALS['connection'],$sql);
  $status = mysqli_num_rows($res);
  if($status == 1){
  // get location
  $sql = "SELECT `location` FROM `bssid_location_meta` WHERE `bssid` = (SELECT `last_bssid` FROM `users` WHERE `username` = '$friend_username');";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  while($row = mysqli_fetch_assoc($rs)){
    $location = $row['location'];
  }
  return $location;
  }else{
  return "ERROR";
  }
}

function get_time($username, $friend_username){
  // check friendship
  $sql = "SELECT * FROM `friends_meta` WHERE `username` = '$username' AND `friend_username` = '$friend_username';";
  $res = mysqli_query($GLOBALS['connection'],$sql);
  $status = mysqli_num_rows($res);
  if($status == 1){
  // get location
  $sql = "SELECT `last_seen` FROM `users` WHERE `username` = '$friend_username';";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  while($row = mysqli_fetch_assoc($rs)){
    $time = $row['last_seen'];
  }
  return $time;
  }else{
  return "ERROR";
  }
}

function days_since($date){
  $daysleft = $date - $_SERVER['REQUEST_TIME'];
  return $days = floor($daysleft/86400);
}

function get_date($timestamp){
  if(days_since($timestamp) != 0){
    return date("M d, H:i:s", $timestamp);
  }else{
    return date("H:i", $timestamp);
  }
}

function add_friend($username, $friend_code){
  $friend_secret = decrypt($friend_code,$_SERVER['REQUEST_TIME']);
  $friend_find_sql = "SELECT * FROM `users` WHERE `secret_key` = '$friend_secret';";
  $rs1 = mysqli_query($GLOBALS['connection'],$friend_find_sql);
  if($rs1){
    $num_res = mysqli_num_rows($rs1);
    if($num_res == 1){
      while($row = mysqli_fetch_assoc($rs1)){
        $friend_username = $row['username'];
      }
    }else{
      echo "ERROR";
      return;
    }
  }else{
    echo "ERROR";
    return;
  }
  if(are_friends($username,$friend_username)){
  $sql = "INSERT INTO `friends_meta` (`username`,`friend_username`) VALUES ('$username', '$friend_username'), ('$friend_username', '$username');";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  if($rs){
    echo "SUCCESS";
    return;
  }
  }else{
    echo "ERROR";
    return;
  }
}

function are_friends($username, $friend_username){
  $sql = "SELECT * FROM `friends_meta` WHERE `username` = '$username' AND `friend_username` = '$friend_username';";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  $num_rows = mysqli_num_rows($rs);
  if($num_rows == 0){
    return true;
  }else{
    return false;
  }
}
