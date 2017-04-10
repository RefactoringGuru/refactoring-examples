using System;
using System.Collections.Generic;

namespace RefactoringGuru.Proxy.Example.SomeCoolMediaLibrary
{
    interface ThirdPartyYoutubeLib
    {
        Dictionary<String, Video> PopularVideos();

        Video GetVideo(String videoId);
    }
}
