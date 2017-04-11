namespace RefactoringGuru.Iterator.Example.Iterators
{
    interface ProfileIterator
    {
        bool HasNext();

        Profile.Profile GetNext();

        void Reset();
    }
}
