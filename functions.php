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
  $daysleft = $_SERVER['REQUEST_TIME'] - $date;
  $days = floor($daysleft/86400);
  return $days;
}

function minutes_since($timestamp){
  return floor(($_SERVER['REQUEST_TIME'] - $timestamp)/60);
}

function get_date($timestamp){
  $days_since = days_since($timestamp);
  if($days_since != 0){
    return "Last seen ".$days_since." days ago at ".date("g:i a", $timestamp);
  }else if($days_since == 1){
      return "Last seen yesterday at ".date("g:i a", $timestamp);
  }else{
    $minutes_since = minutes_since($timestamp);
    if($minutes_since > 0 && $minutes_since< 59){
    return "Last seen today at ".date("g:i a", $timestamp)." ($minutes_since minutes ago)";
  }else if($minutes_since == 0){
    return "Last seen just now";
  }else{
    return "Last seen today at ".date("g:i a", $timestamp);
  }
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
  if($username == $friend_username){
    return false;
  }
  $sql = "SELECT * FROM `friends_meta` WHERE `username` = '$username' AND `friend_username` = '$friend_username';";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  $num_rows = mysqli_num_rows($rs);
  if($num_rows == 0){
    return true;
  }else{
    return false;
  }
}
