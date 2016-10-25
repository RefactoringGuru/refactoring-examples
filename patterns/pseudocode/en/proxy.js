// Интерфейс удаленного сервиса.
interface ThirdPartyYoutubeLib is
    method listVideos()
    method getVideoInfo(id)
    method downloadVideo(id)

// Конкретная реализация сервиса. Методы этого класса запрашивают у ютуба
// различную информацию. Скорость запроса зависит от интернет канала
// пользователя и состояния самого ютуба. Чем больше будет вызовов к
// сервису, тем менее отзывчивой будет программа.
class ThirdPartyYoutubeClass is
    method listVideos() is
        Send API request to Youtube.
    method getVideoInfo(id)
        Get a meta information about some video.
    method downloadVideo(id)
        Download video file from Youtube.

// С другой стороны, можно кешировать запросы к ютубу и не повторять их
// какое-то время, пока кеш не устареет. Но внести этот код напрямую в
// сервисный класс нельзя, так как он находится в сторонней библиотеке.
// Поэтому мы создаем
class CachedYoutubeClass implements ThirdPartyYoutubeLib
    field youtubeService: ThirdPartyYoutubeClass
    field listCache, videoCache
    field needReset
    constructor YoutubeCache()
        youtubeService = new ThirdPartyYoutubeClass()
    method listVideos() is
        if (listCache == null && !needReset)
            listCache = youtubeService.listVideos()
        return listCache
    method getVideoInfo(id)
        if (videoCache == null && !needReset)
            videoCache = youtubeService.getVideoInfo(id)
        return videoCache
    method downloadVideo(id)
        if (!downloadExists(id) && !needReset)
            youtubeService.listVideos()

// Класс GUI, который использует сервисный объект. Вместо реального
// сервиса, мы подсунем ему объект-заместитель. Клиент ничего не заметит,
// так как заместитель имеет тот же интерфейс, что и сервис.
class YoutubeManager is
    field service: ThirdPartyYoutubeLib
    constructor YoutubeManager(service: ThirdPartyYoutubeClass)
        this.service = service
    method renderVideoPage() is
        info = service.getVideoInfo()
        Render the video page.
    method renderListPanel() is
        list = service.listVideos()
        Render the list of video thumbnails.
    method reactOnUserInput() is
        renderVideoPage()
        renderListPanel()

// Конфигурационная часть приложения создаёт и передаёт клиентам
// объект заместителя.
class Application is
    method init() is
        youtubeLib = new CachedYoutubeClass();
        manager = new YoutubeManager(youtubeLib)
        manager.reactOnUserInput()
