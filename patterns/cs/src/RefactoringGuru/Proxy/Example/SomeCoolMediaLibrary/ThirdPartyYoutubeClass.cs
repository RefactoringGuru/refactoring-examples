using System;
using System.Collections.Generic;
using System.Threading;

namespace RefactoringGuru.Proxy.Example.SomeCoolMediaLibrary
{
    class ThirdPartyYoutubeClass : ThirdPartyYoutubeLib
    {
        public Dictionary<string, Video> PopularVideos()
        {
            ConnectToServer("http://www.youtube.com");
            return GetRandomVideos();
        }

        public Video GetVideo(string videoId)
        {
            ConnectToServer("http://www.youtube.com/" + videoId);
            return GetSomeVideo(videoId);
        }

        // -----------------------------------------------------------------------
        // Fake methods to simulate network activity. They as slow as a
        // real life.

        private int Random(int min, int max)
        {
            Random rnd = new Random();
            return rnd.Next(min, max);
        }

        private void ExperienceNetworkLatency()
        {
            int randomLatency = Random(5, 10);
            for (int i = 0; i < randomLatency; i++)
            {
                Thread.Sleep(100);
            }
        }

        private void ConnectToServer(String server)
        {
            Console.WriteLine("Connecting to " + server + "... ");
            ExperienceNetworkLatency();
            Console.WriteLine("Connected!" + "\n");
        }

        private Dictionary<String, Video> GetRandomVideos()
        {
            Console.WriteLine("Downloading populars... ");

            ExperienceNetworkLatency();
            Dictionary<String, Video> dictionary = new Dictionary<string, Video>();
            dictionary.Add("catzzzzzzzzz", new Video("sadgahasgdas", "Catzzzz.avi"));
            dictionary.Add("mkafksangasj", new Video("mkafksangasj", "Dog play with ball.mp4"));
            dictionary.Add("dancescideoo", new Video("asdfas3ffasd", "Dancing video.mpq"));
            dictionary.Add("dlsdk5jfslaf", new Video("dlsdk5jfslaf", "Barcelona vs RealM.mov"));
            dictionary.Add("3sdfgsd1j333", new Video("3sdfgsd1j333", "Programing lesson#1.avi"));

            Console.WriteLine("Done!" + "\n");
            return dictionary;
        }

        private Video GetSomeVideo(String videoId)
        {
            Console.WriteLine("Downloading video... ");

            ExperienceNetworkLatency();
            Video video = new Video(videoId, "Some video title");

            Console.WriteLine("Done!" + "\n");
            return video;
        }
    }
}
