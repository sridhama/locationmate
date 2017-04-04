<?php
$time = $_SERVER['REQUEST_TIME'];
echo $time."\n";
echo 100-$time%100;
