using System;
using System.Collections.Generic;
using RefactoringGuru.Proxy.Example.SomeCoolMediaLibrary;

namespace RefactoringGuru.Proxy.Example.Proxy
{
    class YoutubeCacheProxy : ThirdPartyYoutubeLib
    {
        private ThirdPartyYoutubeLib youtubeService;
        private Dictionary<String, Video> cachePopular = new Dictionary<String, Video>();
        private Dictionary<String, Video> cacheAll = new Dictionary<String, Video>();

        public YoutubeCacheProxy()
        {
            this.youtubeService = new ThirdPartyYoutubeClass();
        }

        public Dictionary<string, Video> PopularVideos()
        {
            if (cachePopular.Count == 0)
            {
                cachePopular = youtubeService.PopularVideos();
            }
            else
            {
                Console.WriteLine("Retrieved list from cache.");
            }
            return cachePopular;
        }

        public Video GetVideo(string videoId)
        {
            Video video;
            try
            {
                video = cacheAll[videoId];
            }
            catch (Exception ex)
            {
                video = null;
            }
            if (video == null)
            {
                video = youtubeService.GetVideo(videoId);
                cacheAll.Add(videoId, video);
            }
            else
            {
                Console.WriteLine("Retrieved video '" + videoId + "' from cache.");
            }
            return video;
        }

        public void Reset()
        {
            cachePopular.Clear();
            cacheAll.Clear();
        }
    }
}
