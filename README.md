<h2>Web Crawling</h2>
This project is for you to search for a word in a website and its child pages.

You pass the word you want the application to do the search, and the URL is in a environment variable.

It will return all the URLs under the root URL that have the word.

curl --location 'http://localhost:4567/crawl' \
--header 'Content-Type: application/json' \
--data '{
    "keyword": "Rezende"
}'
