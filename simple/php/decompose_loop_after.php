protected function printProperties2($users)
{
    foreach ($users as $user)
    {
        echo $this->getProperties($user);
    }
}

protected function getProperties($user)
{
    return $user->getName() + " " + $user->getAge(); 
}
