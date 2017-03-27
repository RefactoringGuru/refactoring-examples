function printProperties($users) {
  for ($i = 0; $i < $users->size(); $i++) {
    $result = "";
    $result += $users->get($i)->getName();
    $result += $users->get($i)->getAge();
    echo $result;
	  
	// ...
    }
}
