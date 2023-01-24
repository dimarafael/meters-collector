import {
    StompSessionProvider,
    useSubscription,
} from "react-stomp-hooks";
import {useEffect, useState} from "react";

export function MainPage(){
    useEffect(()=>{
        fetch('/api/getdatatosocket').then()
    },[])
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

    useSubscription("/topic/meters", (message) => setLastMessage(message.body));

    return (
        <div>Message: {lastMessage}</div>
    );
}