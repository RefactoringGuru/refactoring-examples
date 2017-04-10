using System;
using RefactoringGuru.Proxy.Example.Downloader;
using RefactoringGuru.Proxy.Example.Proxy;
using RefactoringGuru.Proxy.Example.SomeCoolMediaLibrary;

namespace RefactoringGuru.Proxy
{
    class Program
    {
        private static readonly DateTime Jan1st1970 = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);

        static void Main(string[] args)
        {
            YoutubeDownloader naiveDownloader = new YoutubeDownloader(new ThirdPartyYoutubeClass());
            YoutubeDownloader smartDownloader = new YoutubeDownloader(new YoutubeCacheProxy());

            long naive = Test(naiveDownloader);
            long smart = Test(smartDownloader);
            Console.WriteLine("Time saved by caching proxy: " + (naive - smart) + "ms");
            Console.ReadKey();
        }

        public static long Test(YoutubeDownloader downloader)
        {
            long startTime = CurrentTimeMillis();

            // User behavior in our app:
            downloader.RenderPopularVideos();
            downloader.RenderVideoPage("catzzzzzzzzz");
            downloader.RenderPopularVideos();
            downloader.RenderVideoPage("dancesvideoo");
            // Looks like out users visit same pages very often.
            downloader.RenderVideoPage("catzzzzzzzzz");
            downloader.RenderVideoPage("someothervid");

            long estimatedTime = CurrentTimeMillis() - startTime;
            Console.WriteLine("Time elapsed: " + estimatedTime + "ms\n");
            return estimatedTime;
        }

        public static long CurrentTimeMillis()
        {
            return (long)(DateTime.UtcNow - Jan1st1970).TotalMilliseconds;
        }
    }
}
