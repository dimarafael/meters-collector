 export const enum PAGE {
    main,
     config,
     logs
}

export interface meterConfiguration {
    "id": number,
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
    "addrEr": number,
    "addrErEnable": boolean,
    "addrEg": number,
    "addrEgEnable": boolean,
    "addrEs": number,
    "addrEsEnable": boolean
}

export interface meterData{
    "titleEn": string,
    "titleHu": string,
    "p": number,
    "q": number,
    "s": number,
    "ea": number,
    "er": number,
    "eg": number,
    "es": number,
    "id": number
}
