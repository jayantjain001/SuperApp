
## Http Rest APIs : 

#### API Request headers

---
#### API Request Params

---

#### API Request Body


---

#### API Response Codes 


#### _5XX : Server Errors_
#### _4XX : Client Errors_

#### 408 :    
Client timeout

#### 404 :    
Rest API resource with matching url and request body cant be found 

#### 401 :   
unauthorized means either token is not provided or token is expired and 
auth failed

#### 403 :  Forbidden:  
The client does not have access rights to the content; 
that is, it is unauthorized, so the server is refusing to give the requested
resource. Unlike 401 Unauthorized, the client's identity is known to the server.

#### 400 :  
The request message framing is invalid from client.

#### 500 :   
a generic error response that indicates that a server encountered
an unexpected condition while trying to fulfill a request.

#### 503 :   
Unavailable , The server is not ready to handle the request. 
Common causes are a server that is down for maintenance or that is overloaded

#### 504 :  
server error response code indicates that the server,
while acting as a gateway or proxy, did not get a response in time
from the upstream server that it needed in order to complete the request


#### 200 :  
General Success Response status

#### 201 : 
Success  status for POST/ PUT apis when a new resource is created by
successful execution

url : 
https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#successful_responses

---
