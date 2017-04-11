using RefactoringGuru.Iterator.Example.Profiles;

namespace RefactoringGuru.Iterator.Example.Iterators
{
    interface ProfileIterator
    {
        bool HasNext();

        Profile GetNext();

        void Reset();
    }
}
