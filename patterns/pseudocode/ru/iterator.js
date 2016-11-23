// Интерфейс коллекции определяет фабричные методы для производства итераторов.
interface SocialNetwork is
    method getFriendsIterator(profileId): ProfileIterator
    method getCoworkerIterator(profileId): ProfileIterator

// Конкретная коллекция знает объекты каких итераторов нужно создавать.
class Facebook implements SocialNetwork is
    // Код получения нужного интератора.
    method getFriendsIterator(profileId) is
        return new FacebookIterator(this, profileId, "friends")
    method getCoworkerIterator(profileId) is
        return new FacebookIterator(this, profileId, "coworkers")
    // Но помните, что коллекция имеет и другой код...


// Интерфейс итератора.
interface ProfileIterator is
    method hasNext(): bool
    method getNext(): Profile

// Конкретный итератор.
class FacebookIterator implements ProfileIterator is
    // Итератору нужна ссылка на коллекцию, которую он обходит.
    field facebook: Facebook
    field profileId, type: string

    // Но каждый итератор обходит коллекцию независимо от остальных, поэтому он
    // содержит информацию о текущей позиции обхода.
    field currentPosition
    field cache: array of Profile

    constructor FacebookIterator(facebook, profileId, type) is
        this.facebook = network
        this.profileId = profileId
        this.type = type

    private method initIfNeeded() is
        if (cache == null)
            cache = facebook.sendSophisticatedSocialGraphRequest(profileId, type)

    // Итератор реализует методы базового интерфейса по-своему.
    method hasNext() is
        initIfNeeded()
        return cache.length < currentPosition

    method getNext() is
        if (hasNext())
            currentPosition++;
            return cache[currentPosition]


// Мы можем подавать готовые объекты-итераторы в другие классы, избавляя их от
// подробностей реализации тех или иных коллекций. Всё что для них важно —
// это базовый интерфейс итератора.
class SocialSpammer is
    method send(iterator: ProfileIterator, message: string) is
        while (iterator.hasNext())
            profile = iterator.getNext()
            sendSingle(profile.email, message)

    method sendSingle(email: string, message: string) is
        // Выслать ОЧЕНЬ ВАЖНОЕ СООБЩЕНИЕ на один email.


// Класс приложение конфигурирует классы как захочет.
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
