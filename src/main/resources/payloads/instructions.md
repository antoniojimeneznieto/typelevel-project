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

Instructions for playing with users:

    Create a user:
    ```
    curl -v -X POST -H "Content-Type: application/json" 
    -d '{
    "email":"antonio@gmail.com", "password":"rockthejvm"
    }' 
    http://localhost:4041/api/auth/users
    ```

    login: 
    ```
    curl -v -X POST -H "Content-Type: application/json" -d '{"email":"antonio@gmail.com", "password":"rockthejvm"}' http://localhost:4041/api/auth/login
    ```

    change password:
    ```
    curl -v -X PUT \
    -H "Content-Type: application/json" \
    -H ""Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTI2NDMwNjQsImlhdCI6MTY5MjU1NjY2NCwianRpIjoiODgyODk2YTFhNmE0NTMzNDgwM2E0OTgwYjBhN2MwMmIifQ.79aty-lX88QJQl_0P3rMnEQGFR4vY0NNdChnah-hlYQ" \
    -d '{"oldPassword":"rockthejvm", "newPassword":"scalarocks"}' \
    http://localhost:4041/api/auth/users/password
    ```    

    logout:
    ```
    curl -v -X POST \
    -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTI2NDM0NTIsImlhdCI6MTY5MjU1NzA1MiwianRpIjoiYWQxNjIyOTIwODk4NjUzZGQxYTdmNzQ5NDZkMDQ2M2QifQ.HCgG22fE8tzZIBRMtzDVatW9qCIaMzPsTHD1_amCYyw" \
    http://localhost:4041/api/auth/logout
    ```

    delete (exclusive for Role.ADMIN || user 'owns' the user to delete):
    ```
    curl -v -X DELETE \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTI2NDQ2MDIsImlhdCI6MTY5MjU1ODIwMiwianRpIjoiNzdjNWJkMWNiMmE4NTA4OGQ4NjQ0NjIwOGVjYWVjNzcifQ.ts2dxra876-LAHCCK0XOZ5VWceFaX-XK0hnTyiUpSEI" \
    -d '{"email":"antonio@gmail.com"}' \
    http://localhost:4041/api/auth/users/
    ```



