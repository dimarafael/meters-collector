import {useEffect, useState} from "react";
import {meterConfiguration} from "../types";

interface PupUpAddNewMeterProps{
    onCancel: ()=>void
    onOk: (meter: meterConfiguration)=>void
    meter: meterConfiguration|null
    isNewMeter: boolean
}

export function PopUpAddNewMeter({onCancel, onOk, meter, isNewMeter}:PupUpAddNewMeterProps){
    const [position, setPosition] = useState(meter?.position || '')
    const [positionErr, setPositionErr] = useState(false)
    const [titleEn, setTitleEn] = useState(meter?.titleEn || '')
    const [titleEnErr, setTitleEnErr] = useState(false)
    const [titleHu, setTitleHu] = useState(meter?.titleHu || '')
    const [titleHuErr, setTitleHuErr] = useState(false)
    const [ipAddress, setIpAddress] = useState(meter?.ipAddress || '')
    const [ipAddressErr, setIpAddressErr] = useState(false)
    const [unitId, setUnitId] = useState(meter?.unitId)
    const [unitIdErr, setUnitIdErr] = useState(false)
    const [pollingEnable, setPollingEnable] = useState<boolean>(meter?.pollingEnable || false)
    const [addrPEnable, setAddrPEnable] = useState(meter?.addrPEnable || false)
    const [addrP, setAddrP] = useState(meter?.addrP)
    const [addrPErr, setAddrPErr] = useState(false)
    const [addrQEnable, setAddrQEnable] = useState(meter?.addrQEnable || false)
    const [addrQ, setAddrQ] = useState(meter?.addrQ)
    const [addrQErr, setAddrQErr] = useState(false)
    const [addrSEnable, setAddrSEnable] = useState(meter?.addrSEnable || false)
    const [addrS, setAddrS] = useState(meter?.addrS)
    const [addrSErr, setAddrSErr] = useState(false)
    const [addrEaEnable, setAddrEaEnable] = useState(meter?.addrEaEnable || false)
    const [addrEa, setAddrEa] = useState(meter?.addrEa)
    const [addrEaErr, setAddrEaErr] = useState(false)
    const [addrEadEnable, setAddrEadEnable] = useState(meter?.addrEadEnable || false)
    const [addrEad, setAddrEad] = useState(meter?.addrEad)
    const [addrEadErr, setAddrEadErr] = useState(false)
    const [addrErEnable, setAddrErEnable] = useState(meter?.addrErEnable || false)
    const [addrEr, setAddrEr] = useState(meter?.addrEr)
    const [addrErErr, setAddrErErr] = useState(false)
    const [addrEgEnable, setAddrEgEnable] = useState(meter?.addrEgEnable || false)
    const [addrEg, setAddrEg] = useState(meter?.addrEg)
    const [addrEgErr, setAddrEgErr] = useState(false)
    const [addrEsEnable, setAddrEsEnable] = useState(meter?.addrEsEnable || false)
    const [addrEs, setAddrEs] = useState(meter?.addrEs)
    const [addrEsErr, setAddrEsErr] = useState(false)
    const [dataInKilo, setDataInKilo] = useState(meter?.dataInKilo|| false)

    const [addrI1, setAddrI1] = useState(meter?.addrI1)
    const [addrI2, setAddrI2] = useState(meter?.addrI2)
    const [addrI3, setAddrI3] = useState(meter?.addrI3)
    const [addrU1, setAddrU1] = useState(meter?.addrU1)
    const [addrU2, setAddrU2] = useState(meter?.addrU2)
    const [addrU3, setAddrU3] = useState(meter?.addrU3)
    const [addrU12, setAddrU12] = useState(meter?.addrU12)
    const [addrU23, setAddrU23] = useState(meter?.addrU23)
    const [addrU31, setAddrU31] = useState(meter?.addrU31)
    const [addrUIEnable, setAddrUIEnable] = useState(meter?.addrUIEnable || false)

    const [addrI1Err, setAddrI1Err] = useState(false)
    const [addrI2Err, setAddrI2Err] = useState(false)
    const [addrI3Err, setAddrI3Err] = useState(false)
    const [addrU1Err, setAddrU1Err] = useState(false)
    const [addrU2Err, setAddrU2Err] = useState(false)
    const [addrU3Err, setAddrU3Err] = useState(false)
    const [addrU12Err, setAddrU12Err] = useState(false)
    const [addrU23Err, setAddrU23Err] = useState(false)
    const [addrU31Err, setAddrU31Err] = useState(false)

    let newMeter:meterConfiguration
    // const [newMeter, setNewMeter] = useState<meterConfiguration>()

    useEffect(()=>{
        formValidate()
    },[])

    function formValidate(){
        setPositionErr(position=='' || position == undefined || position.match("^[A-Za-z0-9_]+$")==null)
        setTitleEnErr(titleEn=='' || titleEn == undefined)
        setTitleHuErr(titleHu=='' || titleHu == undefined)
        setIpAddressErr(ipAddress =='' || ipAddress == undefined)
        setUnitIdErr(unitId == undefined)
        setAddrPErr(addrP == undefined)
        setAddrQErr(addrQ == undefined)
        setAddrSErr(addrS == undefined)
        setAddrEaErr(addrEa == undefined)
        setAddrEadErr(addrEad == undefined)
        setAddrErErr(addrEr == undefined)
        setAddrEgErr(addrEg == undefined)
        setAddrEsErr(addrEs == undefined)
        setAddrI1Err(addrI1 == undefined)
        setAddrI2Err(addrI2 == undefined)
        setAddrI3Err(addrI3 == undefined)
        setAddrU1Err(addrU1 == undefined)
        setAddrU2Err(addrU2 == undefined)
        setAddrU3Err(addrU3 == undefined)
        setAddrU12Err(addrU12 == undefined)
        setAddrU23Err(addrU23 == undefined)
        setAddrU31Err(addrU31 == undefined)
   }

    function formValidateAndSubmit(){
        formValidate()
        let isValid = (
            !positionErr &&
            !titleEnErr &&
            !titleHuErr &&
            !ipAddressErr &&
            !unitIdErr &&
            !addrPErr &&
            !addrQErr &&
            !addrSErr &&
            !addrEaErr &&
            !addrEadErr &&
            !addrErErr &&
            !addrEgErr &&
            !addrEsErr &&
            !addrI1Err &&
            !addrI2Err &&
            !addrI3Err &&
            !addrU1Err &&
            !addrU2Err &&
            !addrU3Err &&
            !addrU12Err &&
            !addrU23Err &&
            !addrU31Err)
        if (isValid){
            newMeter = {
                    addrEa: addrEa || 0,
                    addrEaEnable: addrEaEnable,
                    addrEad: addrEad || 0,
                    addrEadEnable: addrEadEnable,
                    addrEg: addrEg || 0,
                    addrEgEnable: addrEgEnable,
                    addrEr: addrEr || 0,
                    addrErEnable: addrErEnable,
                    addrEs: addrEs || 0,
                    addrEsEnable: addrEsEnable,
                    addrP: addrP || 0,
                    addrPEnable: addrPEnable,
                    addrQ: addrQ || 0,
                    addrQEnable: addrQEnable,
                    addrS: addrS || 0,
                    addrSEnable: addrSEnable,
                    id: meter?.id || 0,
                    ipAddress: ipAddress,
                    pollingEnable: pollingEnable,
                    position: position,
                    titleEn: titleEn,
                    titleHu: titleHu,
                    unitId: unitId || 0,
                    dataInKilo: dataInKilo,

                    addrI1: addrI1 || 0,
                    addrI2: addrI2 || 0,
                    addrI3: addrI3 || 0,
                    addrU1: addrU1 || 0,
                    addrU2: addrU2 || 0,
                    addrU3: addrU3 || 0,
                    addrU12: addrU12 || 0,
                    addrU23: addrU23 || 0,
                    addrU31: addrU31 || 0,
                    addrUIEnable: addrUIEnable
                }
        }
        return isValid
    }

    function getInt(val:string){
        return(
            val != '' || val == undefined?
                parseInt(val):
                undefined
        )
    }

    return(
        <div>
            <div className='fixed top-0 left-0 z-10 h-full w-full bg-gray-500 opacity-80'
                 onClick={onCancel}></div>
            <div className='flex flex-col fixed z-10 w-[800px] top-28 left-1/2 ml-[-400px]
                            rounded bg-white shadow'>
                <div className='bg-[#046a38] rounded-t text-white py-3 text-2xl'>
                    {`${isNewMeter?'Add':'Edit'} meter`}
                </div>

                <div className='flex text-left bg-neutral-200 px-1 py-1'>
                    <div className=' w-1/4 text-right'>Position:</div>
                    <div className=' flex-auto pl-1'>
                        <input type='text' className={`w-full rounded px-1 outline-none
                        border border-solid transition ease-in-out
                        focus:border-[#046a38]
                        ${positionErr ? 'border-red-700':'border-gray-300'}`}
                               value={position}
                               onChange={(val)=>{setPosition(val.target.value)}}
                               onBlur={()=>{setPositionErr(position=='' || position == undefined || position.match("^[A-Za-z0-9_]+$")==null)}}
                        />
                    </div>
                </div>

                <div className='flex text-left bg-neutral-200 px-1 py-1'>
                    <div className=' w-1/4 text-right'>Title English:</div>
                    <div className=' flex-auto pl-1'>
                        <input type='text' className={`w-full rounded px-1 outline-none
                        border border-solid transition ease-in-out
                        focus:border-[#046a38]
                        ${titleEnErr ? 'border-red-700':'border-gray-300'}`}
                                value={titleEn}
                               onChange={(val)=>{setTitleEn(val.target.value)}}
                               onBlur={()=>{setTitleEnErr(titleEn=='' || titleEn == undefined)}}
                        />
                    </div>
                </div>

                <div className='flex text-left bg-neutral-200 px-1 py-1'>
                    <div className='w-1/4 text-right'>Cím Magyarul:</div>
                    <div className='flex-auto pl-1'>
                        <input type='text' className={`w-full rounded px-1 outline-none
                        border border-solid transition ease-in-out
                        focus:border-[#046a38]
                        ${titleHuErr ? 'border-red-700':'border-gray-300'}`}
                               value={titleHu}
                               onChange={(val)=>{setTitleHu(val.target.value)}}
                               onBlur={()=>{setTitleHuErr(titleHu=='' || titleHu == undefined)}}
                        />
                    </div>
                </div>

                <div className='flex flex-col flex-auto py-1'>
                    <div className='flex py-1'>
                        <div className='w-1/12 text-right'>
                            IP:
                        </div>
                        <div className='w-3/12 text-left pl-2'>
                            <input type='text' className={`w-full rounded px-1 outline-none
                        border border-solid transition ease-in-out
                        focus:border-[#046a38]
                        ${ipAddressErr ? 'border-red-700':'border-gray-300'}`}
                                   value={ipAddress}
                                   onChange={(val)=>{setIpAddress(val.target.value)}}
                                   onBlur={()=>{setIpAddressErr(ipAddress=='' || ipAddress == undefined)}}
                            />
                        </div>

                        <div className='w-2/12 text-right'>
                            Modbus ID:
                        </div>
                        <div className='w-1/12 text-left pl-1'>
                            <input type='number' className={`w-full rounded px-1 outline-none
                            border border-solid transition ease-in-out
                            focus:border-[#046a38]
                            ${unitIdErr ? 'border-red-700':'border-gray-300'}`}
                                   value={unitId || ''}
                                   onChange={(val)=>{
                                       setUnitId(getInt(val.target.value))
                                   }}
                                   onBlur={(val)=>{
                                       setUnitIdErr(val.target.value == '' || unitId == undefined)}}
                            />
                        </div>

                        <div className='w-2/12 text-right'>
                            swapped/kilo:
                        </div>

                        <div className='w-1/12 text-left pl-2'>
                            <input type='checkbox' checked={dataInKilo}
                                   onChange={(val)=>{setDataInKilo(val.target.checked)}}
                            />
                        </div>

                        <div className='w-1/12 text-right'>
                            Polling:
                        </div>

                        <div className='w-1/12 text-left pl-2'>
                            <input type='checkbox' checked={pollingEnable}
                                   onChange={(val)=>{setPollingEnable(val.target.checked)}}
                            />
                        </div>
                    </div>
                    {/*Register addresses*/}
                    <div className='flex px-1'>
                        <div className='flex w-1/2 flex-col'>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Active power:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrPEnable}
                                           onChange={(val)=>{setAddrPEnable(val.target.checked)}}
                                    /></div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38] 
                                    ${addrPErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrP}
                                           onChange={(val)=>{
                                               setAddrP(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrPErr(val.target.value == '' || addrP == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Reactive power:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrQEnable}
                                           onChange={(val)=>{setAddrQEnable(val.target.checked)}}
                                    />
                                </div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrQErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrQ}
                                           onChange={(val)=>{
                                               setAddrQ(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrQErr(val.target.value == '' || addrQ == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Apparent power:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrSEnable}
                                           onChange={(val)=>{setAddrSEnable(val.target.checked)}}
                                    />
                                </div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrSErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrS}
                                           onChange={(val)=>{
                                               setAddrS(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrSErr(val.target.value == '' || addrS == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Voltage and Current:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrUIEnable}
                                           onChange={(val)=>{setAddrUIEnable(val.target.checked)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-1/12 text-right'>I1:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrI1Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrI1}
                                           onChange={(val)=>{
                                               setAddrI1(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrI1Err(val.target.value == '' || addrI1 == undefined)}}
                                    />
                                </div>

                                <div className='w-1/12 text-right'>I2:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrI2Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrI2}
                                           onChange={(val)=>{
                                               setAddrI2(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrI2Err(val.target.value == '' || addrI2 == undefined)}}
                                    />
                                </div>

                                <div className='w-1/12 text-right'>I3:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrI3Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrI3}
                                           onChange={(val)=>{
                                               setAddrI3(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrI3Err(val.target.value == '' || addrI3 == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-1/12 text-right'>U1:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrU1Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrU1}
                                           onChange={(val)=>{
                                               setAddrU1(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrU1Err(val.target.value == '' || addrU1 == undefined)}}
                                    />
                                </div>

                                <div className='w-1/12 text-right'>U2:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrU2Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrU2}
                                           onChange={(val)=>{
                                               setAddrU2(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrU2Err(val.target.value == '' || addrU2 == undefined)}}
                                    />
                                </div>

                                <div className='w-1/12 text-right'>U3:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrU3Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrU3}
                                           onChange={(val)=>{
                                               setAddrU3(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrU3Err(val.target.value == '' || addrU3 == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-1/12 text-right'>U12:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrU12Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrU12}
                                           onChange={(val)=>{
                                               setAddrU12(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrU12Err(val.target.value == '' || addrU12 == undefined)}}
                                    />
                                </div>

                                <div className='w-1/12 text-right'>U23:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrU23Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrU23}
                                           onChange={(val)=>{
                                               setAddrU23(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrU23Err(val.target.value == '' || addrU23 == undefined)}}
                                    />
                                </div>

                                <div className='w-1/12 text-right'>U31:</div>
                                <div className='w-3/12 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrU31Err ? 'border-red-700':'border-gray-300'}`}
                                           value={addrU31}
                                           onChange={(val)=>{
                                               setAddrU31(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrU31Err(val.target.value == '' || addrU31 == undefined)}}
                                    />
                                </div>
                            </div>

                        </div>
                        <div className='flex w-1/2 flex-col'>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Active energy import:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrEaEnable}
                                           onChange={(val)=>{setAddrEaEnable(val.target.checked)}}
                                    />
                                </div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrEaErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrEa}
                                           onChange={(val)=>{
                                               setAddrEa(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrEaErr(val.target.value == '' || addrEa == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Active energy export:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrEadEnable}
                                           onChange={(val)=>{setAddrEadEnable(val.target.checked)}}
                                    />
                                </div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrEadErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrEad}
                                           onChange={(val)=>{
                                               setAddrEad(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrEadErr(val.target.value == '' || addrEad == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Reactive energy import:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrErEnable}
                                           onChange={(val)=>{setAddrErEnable(val.target.checked)}}
                                    />
                                </div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrErErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrEr}
                                           onChange={(val)=>{
                                               setAddrEr(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrErErr(val.target.value == '' || addrEr == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Reactive energy export:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrEgEnable}
                                           onChange={(val)=>{setAddrEgEnable(val.target.checked)}}
                                    />
                                </div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrEgErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrEg}
                                           onChange={(val)=>{
                                               setAddrEg(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrEgErr(val.target.value == '' || addrEg == undefined)}}
                                    />
                                </div>
                            </div>

                            <div className='flex py-1'>
                                <div className='w-3/6 text-right'>Apparent energy:</div>
                                <div className='w-1/6 text-center'>
                                    <input type='checkbox' checked={addrEsEnable}
                                           onChange={(val)=>{setAddrEsEnable(val.target.checked)}}
                                    />
                                </div>
                                <div className='w-2/6 text-left'>
                                    <input type='number' className={`w-full rounded px-1 outline-none
                                    border border-solid transition ease-in-out
                                    focus:border-[#046a38]
                                    ${addrEsErr ? 'border-red-700':'border-gray-300'}`}
                                           value={addrEs}
                                           onChange={(val)=>{
                                               setAddrEs(getInt(val.target.value))
                                           }}
                                           onBlur={(val)=>{
                                               setAddrEsErr(val.target.value == '' || addrEs == undefined)}}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='text-base italic'>Holding registers, Float32, addr: 0...</div>
                <div className='flex justify-evenly py-3 box-border border-t'>
                    <div className='flex items-center justify-center w-1/4 h-10 text-2xl rounded box-border
                    transition ease-in-out hover:border hover:text-[#e1251b] active:bg-neutral-200'
                         onClick={onCancel}
                    >Cancel</div>
                    <div className='flex items-center justify-center w-1/4 h-10 text-2xl rounded box-border
                    transition ease-in-out hover:border hover:text-[#046a38] active:bg-neutral-200'
                         onClick={()=>{
                             formValidateAndSubmit() && onOk(newMeter)
                         }}
                    >Ok</div>
                </div>
            </div>

        </div>
    )
}