<?php
function foundPerson(array $people){
  foreach (array("Don", "John", "Kent") as $needle) {
    $id = array_search($needle, $people);
    if ($id !== FALSE)
      return $people[$id];
  }
  return "";
}