// EN: Common collections interface defines factory method for producing
// iterators. Several methods can be defined if you'd like to offer different
// kinds of iteration over the same collection.
// 
// RU: Общий интерфейс коллекций должен определить фабричный метод для
// производства итератора. Можно определить сразу несколько методов, чтобы дать
// пользователям различные варианты обхода одной и той же коллекции.
interface SocialNetwork is
    method getFriendsIterator(profileId): ProfileIterator
    method getCoworkerIterator(profileId): ProfileIterator

// EN: Each concrete collection should know which type of iterator it
// should return.
// 
// RU: Конкретная коллекция знает объекты каких итераторов нужно создавать.
class Facebook implements SocialNetwork is
    // EN: Iterator creation code.
    // 
    // RU: Код получения нужного итератора.
    method getFriendsIterator(profileId) is
        return new FacebookIterator(this, profileId, "friends")
    method getCoworkerIterator(profileId) is
        return new FacebookIterator(this, profileId, "coworkers")
    // EN: Rest of collection's code...
    // 
    // RU: Но помните, что коллекция имеет и другой код...


// EN: Common interface for all iterators.
// 
// RU: Общий интерфейс итераторов.
interface ProfileIterator is
    method hasNext(): bool
    method getNext(): Profile

// EN: Concrete iterator.
// 
// RU: Конкретный итератор.
class FacebookIterator implements ProfileIterator is
    // EN: Iterator needs a reference to the collection which it
    // traverses through.
    // 
    // RU: Итератору нужна ссылка на коллекцию, которую он обходит.
    field facebook: Facebook
    field profileId, type: string

    // EN: An iterator object traverses collection independently from other
    // iterators. Therefore it has to store the iteration state.
    // 
    // RU: Но каждый итератор обходит коллекцию независимо от остальных, поэтому
    // он содержит информацию о текущей позиции обхода.
    field currentPosition
    field cache: array of Profile

    constructor FacebookIterator(facebook, profileId, type) is
        this.facebook = network
        this.profileId = profileId
        this.type = type

    private method initIfNeeded() is
        if (cache == null)
            cache = facebook.sendSophisticatedSocialGraphRequest(profileId, type)

    // EN: Each concrete iterator has its own implementation of the
    // common interface.
    // 
    // RU: Итератор реализует методы базового интерфейса по-своему.
    method hasNext() is
        initIfNeeded()
        return cache.length < currentPosition

    method getNext() is
        if (hasNext())
            currentPosition++;
            return cache[currentPosition]


// EN: Here's another useful trick: you can pass an iterator instead of a
// collection to a client class. This way, you don't expose a collection. But
// there's another benefit: since client works with iterators through the common
// interface, you can change its behavior at run time by passing different
// iterator objects.
// 
// RU: Вот ещё полезная тактика: мы можем передавать объект итератора вместо
// коллекции в клиентские классы. При таком подходе, клиентский код не будет
// иметь доступа к коллекциям, а значит его не будет волновать подробности их
// реализаций. Ему будет доступен только общий интерфейс итераторов.
class SocialSpammer is
    method send(iterator: ProfileIterator, message: string) is
        while (iterator.hasNext())
            profile = iterator.getNext()
            sendSingle(profile.email, message)

    method sendSingle(email: string, message: string) is
        // EN: Send VERY IMPORTANT MESSAGE to one email address.
        // 
        // RU: Выслать ОЧЕНЬ ВАЖНОЕ СООБЩЕНИЕ на один email.


// EN: Application class configures collections and iterators and then passes
// them to the client code.
// 
// RU: Класс приложение конфигурирует классы как захочет.
class Application is
    field network: SocialNetwork
    field spammer: SocialSpammer

    method config() is
        if working with Facebook
            this.network = new Facebook()
        if working with LinkedIn
            this.network = new LinkedIn()
        this.spammer = new SocialSpammer()

    method sendSpamToFriends() is
        iterator = network.getFriendsIterator(user.profileId)
        spammer.send(iterator)

    method sendSpamToCoworkers() is
        iterator = network.getCoworkerIterator(user.profileId)
        spammer.send(iterator)
