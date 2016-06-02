Price Compare System
==
# Apis
## Base Url
http://172.16.7.27:8080/test

## Projects
* cn
* hk
* us

## Update price
* url
http://172.16.7.27:8080/test/{projectName}/price

* http method
`PUT` or `POST`

* json data
```
{
    "Symbol1": {
        "ask": "19,900",
        "bid": "19.900",
        "timestamp": 1464864251 // [opt]
    },
    "Symbol2": {
        "ask": "19,900",
        "bid": "19.900",
        "timestamp": 1464864251 // [opt]
    },
    "Symbol3": {
        "ask": "19,900",
        "bid": "19.900",
        "timestamp": 1464864251 // [opt]
    }
}
```

If timestamp is not sended in http request, the default timestamp is system timestamp.

* return
`ok` is ok, other json format string is error.

## Add order
* url
http://172.16.7.27:8080/test/{projectName}/order

* http method
`PUT` or `POST`

* json data
```
{
    "orderId": {
        "orderId": "orderId",
        "symbol": "symbol",
        "wait": "0", // 0 for now price, 1 wait for next price
        "sid": "1", // user sid
        "type": "buy", // buy or sell
        "amount": "1", // order amount
        "price": "20.000", // order price
        "timestamp": 1464864251 // [opt]
    }
}
```

If timestamp is not sended in http request, the default timestamp is system timestamp.

This structure could contain more than one order detail.

* return
`ok` is ok, other json format string is error.

## Data receive
* url
http://172.16.7.27:8080/test/{projectName}/result

* procotol
websocket

* json data
```
{
    "canDeal":false,
    "deal":true,
    "detail":{
        "amount":879,
        "orderId":"0b420964ea7c7a5ed037744cd22a5bef",
        "sid":"9499786",
        "timestamp":1464863505
    },
    "orderPrice":{
        "price":26.82
    },
    "price":{
        "$ref":"$.orderPrice"
    },
    "project":{
        "name":"us"
    },
    "succSymbol":{
        "ask":{
            "price":14.82
        },
        "bid":{
            "price":14.8
        },
        "project":{
            "$ref":"$.project"
        },
        "symbol":{
            "name":"WB"
        },
        "symbolName":"WB",
        "timestamp":1464863506
    },
    "symbol":{
        "ask":{
            "price":13.15
        },
        "bid":{
            "price":13.13
        },
        "project":{
            "$ref":"$.project"
        },
        "symbol":{
            "name":"WB"
        },
        "symbolName":"WB",
        "timestamp":1464863504
    },
    "type":{
        "buyType":false,
        "sellType":true,
        "type":-1
    }
}
```

One piece.