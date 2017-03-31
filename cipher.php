<?php
// caesar cipher
// offset changes every 100 seconds
function encrypt($string,$timestamp){
$x = (string)$timestamp;
$salt = intval($x[7]);
$hash = array();
for($i = 0;$i<strlen($string);$i++){
  $ascii_val = ord($string[$i]);
  if($ascii_val<65){
    $sum = ((int)$string[$i] + $salt)%10;
  }else{
  $sum = $salt + $ascii_val;
  if($sum>91){
    $sum+=64;
  }
  $sum = chr($sum);
  }
  array_push($hash,$sum);
}
return implode($hash);
}

function decrypt($string,$timestamp){
$x = (string)$timestamp;
$salt = intval($x[7]);
$hash = array();
for($i = 0;$i<strlen($string);$i++){
  $ascii_val = ord($string[$i]);
  if($ascii_val<65){
    $sum = (int)$string[$i] - $salt;
    if($sum < 0){
      $sum = 10 + $sum;
    }
  }else{
  $sum = $ascii_val - $salt;
  if($sum<65){
    $sum=92-$salt;
  }
  $sum = chr($sum);
  }
  array_push($hash,$sum);
}
return implode($hash);
}

// echo encrypt("3BDA79",$_SERVER['REQUEST_TIME']);
// echo "\n".$_SERVER['REQUEST_TIME'];
