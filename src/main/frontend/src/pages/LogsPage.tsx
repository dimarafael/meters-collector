import {useEffect, useState} from "react";

export function LogsPage(){
    const [log, setLog] = useState('')

    useEffect(()=>{
        fetch('/actuator/logfile')
            .then(response => response.text())
            .then(text => setLog(text))
    },[])
    return(
        <div className='flex w-full text-left text-sm whitespace-pre-wrap px-2'>
            {log}
        </div>
    )
}