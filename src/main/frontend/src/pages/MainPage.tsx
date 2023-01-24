import {
    StompSessionProvider,
    useSubscription,
} from "react-stomp-hooks";
import {useState} from "react";

export function MainPage(){
    return(
        <div>
            Main page
            <StompSessionProvider url={"http://localhost:8080/websocket"}>
                <SubscribingComponent/>
            </StompSessionProvider>
        </div>
    )
}

function SubscribingComponent() {
    const [lastMessage, setLastMessage] = useState("No message received yet");

    //Subscribe to /topic/test, and use handler for all received messages
    //Note that all subscriptions made through the library are automatically removed when their owning component gets unmounted.
    //If the STOMP connection itself is lost they are however restored on reconnect.
    //You can also supply an array as the first parameter, which will subscribe to all destinations in the array
    useSubscription("/topic/meters", (message) => setLastMessage(message.body));

    return (
        <div>Message: {lastMessage}</div>
    );
}