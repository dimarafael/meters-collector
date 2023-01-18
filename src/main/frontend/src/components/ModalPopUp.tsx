
interface ModalPopUpProps{
    title: string,
    message1?: string
    message2?: string
    onCancel: ()=>void
    onOk: ()=>void
}

export function ModalPopUp({title, message1='', message2='', onCancel, onOk}:ModalPopUpProps){
    return(
        <>
            <div className='fixed top-0 left-0 z-10 h-full w-full bg-gray-500 opacity-80'
            onClick={onCancel}></div>
            <div className='flex flex-col fixed z-10 w-96 top-32 left-1/2 ml-[-12rem]
                            rounded bg-white shadow'>
                <div className='bg-[#046a38] rounded-t text-2xl text-white py-3 font-semibold'>
                    {title}
                </div>
                <div className=' py-3'>
                    {message1}
                </div>
                <div className=' py-3'>
                    {message2}
                </div>
                <div className='flex justify-evenly py-4 box-border border-t'>
                    <div className='flex items-center justify-center w-1/4 h-10 text-2xl rounded box-border
                    transition ease-in-out hover:border hover:text-[#e1251b] active:bg-neutral-200'
                    onClick={onCancel}
                    >Cancel</div>
                    <div className='flex items-center justify-center w-1/4 h-10 text-2xl rounded box-border
                    transition ease-in-out hover:border hover:text-[#046a38] active:bg-neutral-200'
                    onClick={onOk}
                    >Ok</div>
                </div>
            </div>
        </>
    )
}