Instructions to create a new job

1. We create the Job Json in the command:
    ```
    curl -v -X POST -H "Content-Type: application/json" \
    -d '{
    "company": "Swissborg",
    "title": "Software Engineer",
    "description": "best job",
    "externalUrl": "swissborg.com",
    "remote": true,
    "location": "Switzerland"
    }' \
    localhost:4041/api/jobs/create
    ```

2. We create a .json and take the content from it: 
    ```
    curl -v -X POST -H "Content-Type: application/json" 
    -d @src/main/resources/payloads/jobinfo.json 
    localhost:4041/api/jobs/create
    ```