# YouTube Thumbnail Search App

## Problem Statement: 

There are times when many of us just want to browse thumbnails and 'not' play the videos that are shown in the search-list in YouTube.
So here is an individual solution for the same.

## Proposed Solution:

1. This Project's main goal is to give the user, a list of YouTube content matching the relevance of entered search query.

2. The Client app searches YouTube's database of over 1 Lakh results related to the query and presents them page-by-page or in small chunks of data, so the system's resources are    not wasted.

3. Implements In-memory caching.

![Image 1](https://github.com/DivS-15/SearchYouTubeClone/blob/main/YT%201.png)
![Image 2](https://github.com/DivS-15/SearchYouTubeClone/blob/main/YT%202.png)


## Concepts Used to Implement the above functionalities:

### 1. Pagination using Repository pattern: 

 ![This is an image](https://google-developer-training.github.io/android-developer-advanced-course-concepts/images/14-1-c-architecture-components/dg_repository.png)
 
 #### The Repository implements the logic to communicate with YouTube data api which includes ->
 1. Query the datasource (YouTube's database) for a search-entry
 2. Retreive the first Page of results and the "next page token",
 3. Update the GET Request on the basis of "next-page-token" and receive further results. And So on.

 4. Store the received data items in a mutable list and emit distinct results which matches the entered query.
 5. **SharedFlow is used to divide the emitted-result-flow into two streams which are then collected as follows ->** 
      - Search stream is collected in viewModel for a given search-query
      - Scroll flow is collected to check whether the search-query is same as scroll-query( used to check if user has scrolled for a given query)
     
### 2. Coroutines for Image Loading and network-requsts
- Coil library is used to load images off the main thread.
- Retrofit comes with first-class support for co-routines and is hence used for querying the network data-source.

### 3. LiveData and ViewModel are used to handle Ui Logic.
 - search-query is defined as **MutableLiveData of type SavedStateHandle** to listen to user's action in the ui-layer
 - Search and Scroll actions are governed by logic implemented by respective **sealed classes in ViewModel** 
       
  
## Application Link and Future Scopes:
Clone the Repo and supply your YouTube api key to get started.
We are planning to implement a fragment that displays most-popular video-images in India.
Also a Login-module will be implemented for the benifit of authorised users.

This App is featured in the Google Developers Blog.

Check this out: 
https://developers.googleblog.com/2022/04/gdsc-indias-android-study-jams-boost.html
     


 
 
 
