protected function printProperties($users)
{
    for ($i = 0; $i < $users->size(); ++$i)
    {
        $result = NULL;
        $result += $users->get($i)->getName();
        $result += $users->get($i)->getAge();
        echo $result;
    }
}
