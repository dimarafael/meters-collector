import {useEffect, useState} from "react";
import {text} from "stream/consumers";

export function LogsPage(){
    const [log, setLog] = useState('')

    useEffect(()=>{
        fetch('/actuator/logfile')
            .then(response => response.text())
            .then(text => setLog(text))
    },[])
    return(
        <div className='flex w-full text-left text-sm whitespace-pre-wrap'>
            {log}
        </div>
    )
}