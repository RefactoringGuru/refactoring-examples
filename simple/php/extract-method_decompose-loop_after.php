<?php
function printProperties($users) {
  foreach ($users as $user) {
    echo $this->getProperties($user);

    // ...
  }
}

function getProperties($user) {
  return $user->getName() + " " + $user->getAge(); 
}
