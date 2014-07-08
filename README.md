Tunnel HTTP over AMQP.

# Simple Demo

1. start any app on port 8080 (e.g. a simple Spring Boot Actuator)

2. make sure rabbit MQ is running locally

3. start the `TestServerApplication` from this project (comes up on port 9000)

4. curl a path on `localhost:9000/tunnel/*` and see the response as if it was from port 8080 
with the `/tunnel` prefix removed.

The HTTP request in step 4 went over AMQP and was relayed to the app.

# More Realistic Scenario

Realistically it doesn't achieve much of interest to proxy requests with a single server. It's
more interesting to use the AMQP broker as a tunnel:

```

-------------   HTTP   -------------   AMQP    -------------   AMQP   -------------   HTTP   --------------
| local app | <------> |   client  | <------>  |   broker  | <------> |   server  | <------> | target app | 
-------------          -------------           -------------          -------------          --------------

```

In this picture the "client" and "server" are instances of the `TunnelApplication` running in different
Spring profiles ("client" and "server"). The "target app" has an HTTP interface that "local app" wishes 
to consume. Instead of sending HTTP requests directly to the target the local app sends them to the 
"client" half of the tunnel. The tunnel is symmetric if configured carefully, i.e. if the "target" 
(or any other app) wants to send HTTP requests to the "local" app, it can send them to the "server"
instead.

You can run this configuration locally using the `TunnelApplication` ("client" profile on port 9000) 
and the `TunnelServerApplication` (runs in the "server" profile on port 9001). With those two
processes running you can curl from `http://localhost:9000/tunnel` or `http://localhost:9001/tunnel` and both
return results from the same target on port 8080.

# Configuration

| key          |    default              | description                  |
|--------------|-------------------------|------------------------------|
| targetUrl    | http://localhost:8080   | `tunnel -> target` URL address base (also for `client -> local`) |
| outboundQueue | client                  | AMQP queue name for `client->broker` (in the "test" profile defaults to 'request') |
| inboundQueue  | server                  | AMQP queue name for `broker->server` (in the "test" profile defaults to 'request') |
