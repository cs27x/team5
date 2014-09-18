#Assignment 1 Spec

###People in class 9/16/2014
- Andrew Bachman
- Justin Paul
- Jeremey Key
- Xiaochen Yang
- Clark Perkins
- Alex Wan

##Overview
Create an application to find the nearest place to buy beer and offer a place to
store your favorite beers that were available at that place.

This should use the Dataset available at <a href="https://data.nashville.gov/dataset/Beer-Permit-Locations/3wb6-xy3j">
Beer Permit Locations</a> for the city of Nashville and be implemented as a mobile application.

##User Stories
0. The user should open the application and view a list of the closest locations to get beer.
1. The user should be able to filter by places to buy beer for consumption in the location vs. to go beer.
2. The user should be able to view a map of closest places to buy beer
  - This can be accomplished by clicking list view to launch google maps.
3. The user should be able to log the types of beer that are sold at a given location by typing in the beer.
  - Initially stored locally, so it is available only to users, but could be extended in the future.
4. The user should be able to remove the beer from under a given store.
5. A user can store multiple beers under a given store.
6. The user can search for a place and find the information they have saved about the place.

##Possibly Helpful Technical Methods
0. To access the JSON api for the Beer Permit Locations use the endpoint <a href="http://data.nashville.gov/resource/3wb6-xy3j.json">http://data.nashville.gov/resource/3wb6-xy3j.json</a>
1. If you are unfamiliar with the Javascript Object Notation format that data.nashville.gov
   can provide data in, you can read this tutorial:
   http://www.copterlabs.com/blog/json-what-it-is-how-it-works-how-to-use-it/
2. The API docs for the Nashville Open Data api can be found <a href="http://dev.socrata.com/docs/filtering.html">here</a>
3. If you include the Apache Commons IOUtils in your project (http://commons.apache.org/proper/commons-io/),
   you can download data from a URL as follows:
   ```java
   URL url = new URL("http://data.nashville.gov/resource/n37w-5mq8.json");
   String contents = IOUtils.toString(url.openStream());
   ```
4. You can use the Jackson library to convert JSON Strings into Java Objects as
   described in their 1-minute tutorial: https://github.com/FasterXML/jackson-databind/

##Evaluation Rubric
This project will be graded out of a possible 150 points. The breakdown of points is as follows:

0. (50pts) - Uses a consistent code style and applies Java best practices - Evaluation process:
   - Checkout the source code for the application
   - If all classes are in packages with full lower case names, no non-alphanumeric
     characters, and at least 2 dotted package components (e.g., "com.foo" vs. just "com"),
     award 10pts.
   - If all classes have only private member variables that are accessed from other classes
     via getter/setter methods, award 10pts.
   - If all class names are title cased, award 10pts.
   - If all classes have the same variable naming / spacing scheme, award 10pts.
   - If all methods have been broken up into code chunks of 15 lines or less, award
     10pts
1. (30pts) - Downloads the Beer Location list from data.nashville.gov - Evaluation process:
   - Checkout the source code for the application and find the locations in the code
      where it is downloading data from nashville.data.gov. If it is not downloading
      data from data.nashville.gov, award 0pts, otherwise award 30pts.
2. (20pts) - Uses the Jackson library to parse JSON - Evaluation process:
   - Checkout the source code for the application and find the locations in the code
      where it is downloading data from nashville.data.gov. Check and see if it is
     using the Jackson library's ObjectMapper to convert the JSON to objects. If so,
     award 25pts.
3. (30pts) As a user I can open the app and see a sorted list of the closest places to get beer.
  - Uses the specified API
  - Locations are correctly sorted based on current location
4. (10pts) From the list view I can open a map with the location of the store in google maps.
5. (20pts) As a beer enthusiast I can store beers that are available at a location and some information about the beer.
  - The user is able to update/remove beer notes
  - Multiple beers can be stored under a given location
