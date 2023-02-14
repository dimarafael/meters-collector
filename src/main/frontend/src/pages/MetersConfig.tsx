import {meterConfiguration} from '../types'
import {useEffect, useState} from "react";
import {MdDeleteForever, MdFileCopy, MdEdit, MdAddCircleOutline} from 'react-icons/md'
import {ModalPopUp} from "../components/ModalPopUp";
import {PopUpAddNewMeter} from "../components/PopUpAddNewMeter";

export function MetersConfig() {
    const [data, setData] = useState<meterConfiguration[]|null>(null)
    const [deletePopUp, setDeletePopUp] = useState<{show: boolean, id:number|null, titleEn: string, titleHu: string}>({
        show:false, id:null ,titleEn:'', titleHu:''
    })
    const [showAddMeterPopUp, setShowAddMeterPopUp] = useState(false)
    const [meterForPopUp, setMeterForPopUp] = useState<meterConfiguration|null>(null)
    const [isNewMeterPopUp, setIsNewMeterPopUp] = useState(false)
    const [access, setAccess] = useState(true) //show message "Access denied!"

    function getMeters(){
        return fetch('/api/meter_config')
            .then(response => {
                if (!response.ok) {
                    setAccess(false)
                    throw new Error(response.statusText)
                }
                setAccess(true)
                return response.json().then(data => data as meterConfiguration)
            }).then(response => {
                // @ts-ignore
                setData(response)
            } )
    }

    function deleteMeter(id:number){
        fetch(`/api/meter_config/${id}`,{method:'DELETE'})
            .then(()=>getMeters())
        setDeletePopUp({show:false, id:null, titleEn:'', titleHu:''})
    }

    function addMeter(data:meterConfiguration){
        return fetch('/api/meter_config', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(()=>getMeters())
    }

    function editMeter(data:meterConfiguration){
        return fetch(`/api/meter_config/${data.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(()=>getMeters())
    }

    useEffect(() => {
        getMeters().then()
    }, [])

    // @ts-ignore
    return (
        <div className='flex flex-wrap mx-auto xl:max-w-screen-xl'>
            {!access &&
                <div className='flex mx-auto text-3xl font-semibold text-red-600'>
                    <p>Access denied!</p>
                </div>
            }
            <div className='flex w-full justify-center my-3'>

                <div className='flex justify-center items-center w-52 text-3xl py-1 box-border border rounded
                transition ease-in-out hover:shadow hover:text-[#377dff] active:bg-neutral-200'
                    onClick={()=>{
                        setMeterForPopUp(null)
                        setIsNewMeterPopUp(true)
                        setShowAddMeterPopUp(true)
                    }}>
                        <MdAddCircleOutline/>
                </div>
            </div>
            {data && data.map((item:meterConfiguration) => (
                <div key={item.id} className='accent-neutral-700 w-[49%] flex flex-col
                                               border box-border rounded mb-3 mx-auto hover:shadow'>
                    <div className={`h-1 rounded-t ${item.pollingEnable ? 'bg-[#046a38]' : 'bg-[#e1251b]'}`}></div>
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
                    <div className='flex'>
                        <div className='flex flex-col flex-auto'>
                            <div className='flex'>
                                <div className='w-1/12 text-right'>
                                    IP:
                                </div>
                                <div className='w-3/12 text-left pl-2'>
                                    {item.ipAddress }
                                </div>
                                <div className='w-2/12 text-right'>
                                    Modbus ID:
                                </div>
                                <div className='w-1/12 text-left pl-1'>
                                    {item.unitId}
                                </div>
                                <div className='w-2/12 text-right'>
                                    data in kilo:
                                </div>
                                <div className='w-1/12 text-left pl-2'>
                                    <input type='checkbox' checked={item.dataInKilo} readOnly={true}/>
                                </div>
                                <div className='w-1/12 text-right'>
                                    Poll:
                                </div>
                                <div className='w-1/12 text-left pl-2'>
                                    <input type='checkbox' checked={item.pollingEnable} readOnly={true}/>
                                </div>
                            </div>
                                {/*Register addresses*/}
                            <div className='flex'>
                                <div className='flex w-1/2 flex-col'>
                                    <div className='flex'>
                                        <div className='w-9/12 text-right'>Active power:</div>
                                        <div className='w-1/6 text-center'><input type='checkbox' checked={item.addrPEnable} readOnly={true}/></div>
                                        <div className='w-3/12 text-left'>{item.addrP}</div>
                                    </div>
                                    <div className='flex'>
                                        <div className='w-9/12 text-right'>Reactive power:</div>
                                        <div className='w-1/6 text-center'><input type='checkbox' checked={item.addrQEnable} readOnly={true}/></div>
                                        <div className='w-3/12 text-left'>{item.addrQ}</div>
                                    </div>
                                    <div className='flex'>
                                        <div className='w-9/12 text-right'>Apparent power:</div>
                                        <div className='w-1/6 text-center'><input type='checkbox' checked={item.addrSEnable} readOnly={true}/></div>
                                        <div className='w-3/12 text-left'>{item.addrS}</div>
                                    </div>
                                </div>
                                <div className='flex w-1/2 flex-col'>
                                    <div className='flex'>
                                        <div className='w-9/12 text-right'>Active energy:</div>
                                        <div className='w-1/6 text-center'><input type='checkbox' checked={item.addrEaEnable} readOnly={true}/></div>
                                        <div className='w-3/12 text-left'>{item.addrEa}</div>
                                    </div>
                                    <div className='flex'>
                                        <div className='w-9/12 text-right'>Reactive energy:</div>
                                        <div className='w-1/6 text-center'><input type='checkbox' checked={item.addrErEnable} readOnly={true}/></div>
                                        <div className='w-3/12 text-left'>{item.addrEr}</div>
                                    </div>
                                    <div className='flex'>
                                        <div className='w-9/12 text-right'>Generated energy:</div>
                                        <div className='w-1/6 text-center'><input type='checkbox' checked={item.addrEgEnable} readOnly={true}/></div>
                                        <div className='w-3/12 text-left'>{item.addrEg}</div>
                                    </div>
                                    <div className='flex'>
                                        <div className='w-9/12 text-right'>Apparent energy:</div>
                                        <div className='w-2/12 text-center'><input type='checkbox' checked={item.addrEsEnable} readOnly={true}/></div>
                                        <div className='w-3/12 text-left'>{item.addrEs}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className='w-1/12 flex flex-col border-l box-border'>
                            <div className='flex h-1/3 m-1 text-2xl items-center justify-center rounded
                                            transition ease-in-out hover:border hover:text-[#377dff] active:bg-neutral-200'
                                 onClick={()=>{
                                     setMeterForPopUp(item)
                                     setIsNewMeterPopUp(false)
                                     setShowAddMeterPopUp(true)
                                 }}>
                                <MdEdit/>
                            </div>
                            <div className='flex h-1/3 m-1 text-1xl items-center justify-center rounded
                                            transition ease-in-out hover:border hover:text-[#046a38] active:bg-neutral-200'
                                 onClick={()=>{
                                     setMeterForPopUp(item)
                                     setIsNewMeterPopUp(true)
                                     setShowAddMeterPopUp(true)
                                 }}>
                                <MdFileCopy/>
                            </div>
                            <div className='flex h-1/3 m-1 text-2xl items-center justify-center rounded
                                            transition ease-in-out hover:border hover:text-[#e1251b] active:bg-neutral-200'
                                 onClick={()=>{
                                     setDeletePopUp({show:true, id:item.id ,titleEn:item.titleEn, titleHu:item.titleHu})
                                    }}>
                                <MdDeleteForever/>
                            </div>
                        </div>
                    </div>
                </div>
            ))
            }
            {deletePopUp.show && <ModalPopUp title='Delete?' message1={deletePopUp.titleEn} message2={deletePopUp.titleHu}
                                             onCancel={()=>{setDeletePopUp({show:false, id:null, titleEn:'', titleHu:''})}}
                                             onOk={()=>{deletePopUp.id && deleteMeter(deletePopUp.id)}}
            />}
            {showAddMeterPopUp && <PopUpAddNewMeter
                onCancel={()=>{
                    setShowAddMeterPopUp(false)
                }}
                onOk={(meter)=>{
                    setShowAddMeterPopUp(false)
                    isNewMeterPopUp? addMeter(meter):editMeter(meter)
                }}
                meter={meterForPopUp}
                isNewMeter={isNewMeterPopUp}
            />}
        </div>
    )

}