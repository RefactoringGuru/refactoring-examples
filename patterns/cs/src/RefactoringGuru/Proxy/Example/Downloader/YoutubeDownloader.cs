using System;
using System.Collections.Generic;
using RefactoringGuru.Proxy.Example.SomeCoolMediaLibrary;

namespace RefactoringGuru.Proxy.Example.Downloader
{
    class YoutubeDownloader
    {
        private ThirdPartyYoutubeLib api;

        public YoutubeDownloader(ThirdPartyYoutubeLib api)
        {
            this.api = api;
        }

        public void RenderVideoPage(String videoId)
        {
            Video video = api.GetVideo(videoId);
            Console.WriteLine("\n-------------------------------");
            Console.WriteLine("Video page (imagine fancy HTML)");
            Console.WriteLine("ID: " + video.id);
            Console.WriteLine("Title: " + video.title);
            Console.WriteLine("Video: " + video.data);
            Console.WriteLine("-------------------------------\n");
        }

        public void RenderPopularVideos()
        {
            Dictionary<String, Video> list = api.PopularVideos();
            Console.WriteLine("\n-------------------------------");
            Console.WriteLine("Most popular videos on Youtube (imagine fancy HTML)");
            foreach (var VARIABLE in list)
            {
                Video video = VARIABLE.Value;
                Console.WriteLine("ID: " + video.id + " / Title: " + video.title);
            }
            Console.WriteLine("-------------------------------\n");
        }
    }
}
