# Image_Search_Client
This Client provides ability to search images by using existing google API's. It allows you to filter the images based on their size, color, type etc.

Submitted by: Akshay Kulkarni

Time spent: 20 hours spent in total

## User Stories

The following user stories are completed:

* [x] User can enter a search query that will display a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of advanced search options to filter results
* [x] User can configure advanced search filters such as:
        Size (small, medium, large, extra-large)
        Color filter (black, blue, brown, gray, green, etc...)
        Type (faces, photo, clip art, line art)
        Site (espn.com)
        Subsequent searches will have any filters applied to the search results
* [x] User can tap on any image in results to see the image full-screen
* [x] User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

The following advanced user stories are optional:

* [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
* [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* [ ] Advanced: User can share an image to their friends or email it to themselves
* [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
* [x] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
* [ ] Bonus: Use the StaggeredGridView to display improve the grid of image results
* [ ] Bonus: User can zoom or pan images displayed in full-screen detail view


## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![Video Walkthrough](Google_Image_Search_App.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

I figured out a known issue in android emulator where if your physical keyboard is an active input and if you submit search text by pressing enter key the method onSubmitQueryText() was getting called twiced. This time I spent sufficient time on learning and understanding layout and UI related aspect. Also, I was not able to find the gradle version for Zoom and Pan image library. 

## License

    Copyright [2015] [Akshay Kulkarni]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
