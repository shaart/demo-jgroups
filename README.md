## Description
Sample project to create self-cluster for all replicas of service.

## Notes
Run with `-Djava.net.preferIPv4Stack=true` to use IPv4 instead of IPv6

## Example ENVs
- Replica 1: `SERVER_PORT=8081;APP_REPLICAS_NAME=app-1`
- Replica 2: `SERVER_PORT=8082;APP_REPLICAS_NAME=app-2`

## Requests
### Get all
- Get all from replica 1: GET http://localhost:8081/persons
- Get all from replica 2: GET http://localhost:8082/persons
### Create new
- Create new at replica 1: POST http://localhost:8081/persons/John
- Create new at replica 2: POST http://localhost:8082/persons/Tom
### Delete all
- Delete all from replica 1: DELETE http://localhost:8081/persons
- Delete all from replica 2: DELETE http://localhost:8082/persons
### Create new via broadcast
- from replica 1: POST http://localhost:8081/broadcast/persons/SamBroadcasted
- from replica 2: POST http://localhost:8082/broadcast/persons/StanBroadcasted