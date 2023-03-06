import {StompSessionProvider, useSubscription} from "react-stomp-hooks";
import {meterData} from "../types";
import {useState} from "react";

export function MainPage(){

    function getData(){
        fetch('/api/getdatatosocket').then()
    }

    return(
        <div>
            {/*<StompSessionProvider url={"http://localhost:8080/websocket" } onConnect={getData}>*/}
            <StompSessionProvider url={"/websocket"} onConnect={getData}>
                <SubscribingComponent/>
            </StompSessionProvider>
        </div>
    )
}

function SubscribingComponent() {
    const [metersData, setMetersData] = useState<meterData[]|null>(null);

    useSubscription("/topic/meters", (message) => setMetersData(JSON.parse(message.body)));


    const getRealStr = function (data: number): string {
        if (data !== undefined) {
            return (data.toFixed(2).toString())
        } else return('0')
    }

    return (
        <div className='flex flex-wrap mx-auto py-2 xl:max-w-screen-xl'>
            {metersData &&
                metersData.map((item:meterData)=>(
                    <div key={item.id} className='accent-neutral-700 w-[49%] min-w-[600px] flex flex-col
                                               border box-border rounded mb-3 mx-auto hover:shadow'>
                        <div className={`h-1 rounded-t ${item.online ? 'bg-[#046a38]' : 'bg-[#e1251b]'}`}></div>
                        <div className='flex text-left bg-neutral-200'>
                            <div className='w-1/4 text-right'>Position:</div>
                            <div className='pl-1 font-semibold'>{item.position}</div>
                        </div>
                        <div className='flex text-left bg-neutral-200'>
                            <div className='w-1/4 text-right'>Title English:</div>
                            <div className='pl-1 font-semibold'>{item.titleEn}</div>
                        </div>
                        <div className='flex text-left bg-neutral-200'>
                            <div className='w-1/4 text-right'>Cím Magyarul:</div>
                            <div className='pl-1 font-semibold'>{item.titleHu}</div>
                        </div>

                        <div className='flex py-1'>
                            <div className='flex w-1/2 flex-col border-r box-border'>
                                <div className='font-semibold border-b mx-1'>Power</div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Active</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.p)} kW</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Reactive</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.q)} kvar</div>
                                </div>
                                <div className='flex mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Apparent</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.s)} kVA</div>
                                </div>
                                <div className='text-sm text-left pl-1 pt-9 text-neutral-300'>
                                    {item.id}:Polling time: {item.pollTime/1000000}s
                                </div>
                            </div>
                            <div className='flex w-1/2 flex-col'>
                                <div className='font-semibold border-b mx-1'>Energy</div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Active import</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.ea)} kWh</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Active export</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.ead)} kWh</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Reactive import</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.er)} kvarh</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Reactive export</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.eg)} kvarh</div>
                                </div>
                                <div className='flex mx-1'>
                                    <div className='w-1/2 text-left pl-4'>Apparent</div>
                                    <div className='w-1/2 text-left'>{getRealStr(item.es)} kVAh</div>
                                </div>
                            </div>
                        </div>

                    </div>
                ))
            }
        </div>
    );
}
