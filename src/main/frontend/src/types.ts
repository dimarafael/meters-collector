 export const enum PAGE {
    main,
     config,
     logs
}

export interface meterConfiguration {
    "id": number,
    "position":string,
    "titleEn": string,
    "titleHu": string,
    "pollingEnable": boolean,
    "ipAddress": string,
    "unitId": number,
    "addrP": number,
    "addrPEnable": boolean,
    "addrQ": number,
    "addrQEnable": boolean,
    "addrS": number,
    "addrSEnable": boolean,
    "addrEa": number,
    "addrEaEnable": boolean,
    "addrEad": number,
    "addrEadEnable": boolean,
    "addrEr": number,
    "addrErEnable": boolean,
    "addrEg": number,
    "addrEgEnable": boolean,
    "addrEs": number,
    "addrEsEnable": boolean,
    "dataInKilo": boolean,

    "addrI1": number,
    "addrI2": number,
    "addrI3": number,
    "addrU1": number,
    "addrU2": number,
    "addrU3": number,
    "addrU12": number,
    "addrU23": number,
    "addrU31": number,
    "addrUIEnable": boolean
}

export interface meterData{
    "position":string,
    "titleEn": string,
    "titleHu": string,
    "p": number,
    "q": number,
    "s": number,
    "ea": number,
    "ead": number,
    "er": number,
    "eg": number,
    "es": number,
    "id": number,
    pollTime: number,
    online: boolean
}
