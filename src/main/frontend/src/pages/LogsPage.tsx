import {useEffect, useState} from "react";
import {RxUpdate} from 'react-icons/rx'

export function LogsPage(){
    const [log, setLog] = useState('')

    function getLog(){
        fetch('/actuator/logfile')
            .then(response => response.text())
            .then(text => setLog(text))
    }

    useEffect(()=>{
        // fetch('/actuator/logfile')
        //     .then(response => response.text())
        //     .then(text => setLog(text))
        getLog()
    },[])
    return(
        <>
            <div className='flex w-full text-left text-sm whitespace-pre-wrap px-2'>
                {log}
            </div>
            <div className='fixed top-24 right-10 text-4xl p-2 text-neutral-400
                            hover:shadow active:text-neutral-600'
                onClick={getLog}
            >
                <RxUpdate/>
            </div>
        </>
    )
}