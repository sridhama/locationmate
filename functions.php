<?php
require("./config.php");
require("./cipher.php");
function get_location($phone, $friend_phone){
  // check friendship
  $sql = "SELECT * FROM `friends_meta` WHERE `phone` = '$phone' AND `friend_phone` = '$friend_phone';";
  $res = mysqli_query($GLOBALS['connection'],$sql);
  $status = mysqli_num_rows($res);
  if($status == 1){
  // get location
  $sql = "SELECT `location` FROM `bssid_location_meta` WHERE `bssid` = (SELECT `last_bssid` FROM `users` WHERE `phone` = '$friend_phone');";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  while($row = mysqli_fetch_assoc($rs)){
    $location = $row['location'];
  }
  if($location == ""){
    return "Unmapped Location";
  }
  return $location;
  }else{
  return "ERROR";
  }
}

function get_time($phone, $friend_phone){
  // check friendship
  $sql = "SELECT * FROM `friends_meta` WHERE `phone` = '$phone' AND `friend_phone` = '$friend_phone';";
  $res = mysqli_query($GLOBALS['connection'],$sql);
  $status = mysqli_num_rows($res);
  if($status == 1){
  // get location
  $sql = "SELECT `last_seen` FROM `users` WHERE `phone` = '$friend_phone';";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  while($row = mysqli_fetch_assoc($rs)){
    $time = $row['last_seen'];
  }
  return $time;
  }else{
  return "ERROR";
  }
}

// function days_since($date){
//   // $daysleft = $_SERVER['REQUEST_TIME'] - $date;
//   $date = $_SERVER['REQUEST_TIME'];
  // echo date("d");
//   $days = floor($daysleft/86400);
//   return $days;
// }

function days_since($timestamp){
  $given_day = date("d", $timestamp);
  $today = date("d");
  if($today == $given_day) return "today";
  $days_since = floor(($_SERVER['REQUEST_TIME']-$timestamp)/86400);
  if($days_since == 0){
    return "yesterday";
  }else{
    return $days_since + 1;
  }
}


function minutes_since($timestamp){
  return floor(($_SERVER['REQUEST_TIME'] - $timestamp)/60);
}

function get_date($timestamp){
  $days_since = days_since($timestamp);
  switch($days_since){
    case "today":
      $minutes_since = minutes_since($timestamp);
      if($minutes_since == 0){
            return "Last seen just now";
      }else if($minutes_since == 1){
        return "Last seen today at ".strtoupper(date("g:i a", $timestamp))." (1 minute ago)";
      }else if($minutes_since > 0 && $minutes_since < 59){
        return "Last seen today at ".strtoupper(date("g:i a", $timestamp))." ($minutes_since minutes ago)";
      }else{
            return "Last seen today at ".strtoupper(date("g:i a", $timestamp));
      }
      break;
    case "yesterday":
      return "Last seen yesterday at ".strtoupper(date("g:i a", $timestamp));
      break;
    case "yesterday":
      return "Last seen yesterday at ".strtoupper(date("g:i a", $timestamp));
      break;
    default:
      return "Last seen $days_since days ago at ".strtoupper(date("g:i a", $timestamp));
  }
}



function add_friend($phone, $friend_code){
  $friend_secret = decrypt($friend_code,$_SERVER['REQUEST_TIME']);
  $friend_find_sql = "SELECT * FROM `users` WHERE `secret_key` = '$friend_secret';";
  $rs1 = mysqli_query($GLOBALS['connection'],$friend_find_sql);
  if($rs1){
    $num_res = mysqli_num_rows($rs1);
    if($num_res == 1){
      while($row = mysqli_fetch_assoc($rs1)){
        $friend_phone = $row['phone'];
      }
    }else{
      echo "ERROR";
      return;
    }
  }else{
    echo "ERROR";
    return;
  }
  if(are_friends($phone,$friend_phone)){
  $sql = "INSERT INTO `friends_meta` (`phone`,`friend_phone`) VALUES ('$phone', '$friend_phone'), ('$friend_phone', '$phone');";
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

function are_friends($phone, $friend_phone){
  if($phone == $friend_phone){
    return false;
  }
  $sql = "SELECT * FROM `friends_meta` WHERE `phone` = '$phone' AND `friend_phone` = '$friend_phone';";
  $rs = mysqli_query($GLOBALS['connection'], $sql);
  $num_rows = mysqli_num_rows($rs);
  if($num_rows == 0){
    return true;
  }else{
    return false;
  }
}
